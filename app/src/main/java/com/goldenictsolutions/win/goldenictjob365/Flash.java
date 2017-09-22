package com.goldenictsolutions.win.goldenictjob365;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ContentHost;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.Check_connection;
import com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler.LanguageHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.login;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Interface;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Flash extends AppCompatActivity {
    Intent i;
    ImageView imgViewlogo;
    TextView welcometxtView, fromTxtView;
    Animation fade_in_out, new_fade_in, new_move, blink;
    Communicator communicator;
    String c_user, c_pass, token;
    int con_live;
    DB_Control db_control;
    ArrayList<DB_USERDATA> for_token = new ArrayList<>();
    RelativeLayout flash;
    public String remain_time;
    private String package_type;
    private String TAG = "Flash HA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);
        communicator = new Communicator();
        db_control = new DB_Control(Flash.this);
        Check_connection checkConnection = new Check_connection(Flash.this);
        con_live = checkConnection.CheckWifiConnected();
        welcometxtView = (TextView) findViewById(R.id.welcometextView);
        fromTxtView = (TextView) findViewById(R.id.fromtextView);
        flash = (RelativeLayout) findViewById(R.id.flash);
        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        welcometxtView.startAnimation(blink);
        fromTxtView.startAnimation(blink);

        final SharedPreferences sh = getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
        if (con_live == 1 || con_live == 2) {
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2500);
                        boolean login = sh.getBoolean(JLoginActivity.LOGIN, false);
                        int language = sh.getInt(JLoginActivity.LANGUAGE, 0);
                        if (login) {
                            if (language == 1) {
                                LanguageHandler.changeLocale(getResources(), "en");
                            } else if (language == 2) {
                                LanguageHandler.changeLocale(getResources(), "my");
                            }
                            db_control.openDb();
                            for_token = db_control.getUserid();
                            c_user = for_token.get(0).getUser_name();
                            c_pass = for_token.get(0).getUser_password();
                            db_control.closeDb();
                            loginPost(c_user, c_pass);
                            //usePost(c_user, c_pass);

                        } else {
                            i = new Intent(Flash.this, JLoginActivity.class);
                            startActivity(i);
                            finish();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
        } else {
            Toast.makeText(this, "Need to Turn on Data or Wifi", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    public void onResume() {
        super.onResume();
    }


    public void onPause() {
        super.onPause();
    }


    public void loginPost(final String login_name, final String password) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.connectTimeout(35, TimeUnit.SECONDS)
                        .readTimeout(35, TimeUnit.SECONDS)
                        .writeTimeout(35, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://allreadymyanmar.com")
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.post(new login(login_name, password));
        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.e("Response Code", "" + response.body().getStatus());
                if (response.body().getStatusCode() == 210) {
                    //usePost(c_user,c_pass);
                    Intent i = new Intent(Flash.this, JLoginActivity.class);
                    startActivity(i);
                    Toast.makeText(Flash.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 200) {
                    token = response.body().getToken();
                    remain_time = response.body().getUserList().get(0).getRemain_time();
                    package_type = response.body().getUserList().get(0).getPackage_type();
                    Toast.makeText(Flash.this, "Success", Toast.LENGTH_SHORT).show();
                    if (response.body().getUserList().get(0).getUserType() == 2) {
                        i = new Intent(Flash.this, ContentHost.class);
                        i.putExtra("remain_time", remain_time);
                    } else {
                        i = new Intent(Flash.this, EmployerFragHost.class);
                        i.putExtra("remain_time", remain_time);
                        i.putExtra("package_type", package_type);
                    }
                    startActivity(i);
                    finish();
                }
                // response.isSuccessful() is true if the response code is 2xx

                BusProvider.getInstance().post(new ServerEvent(response.body()));
            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {

                loginPost(c_user, c_pass);
            }
        });
    }
}
