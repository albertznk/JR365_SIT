package com.goldenictsolutions.win.goldenictjob365;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ContentHost;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;

import com.goldenictsolutions.win.goldenictjob365.Employee.Check_connection;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Company_Profile;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Jobseeker;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.JForgetpassword;
import com.goldenictsolutions.win.goldenictjob365.Employee.JResumeActivity;
import com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler.LanguageHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.login;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Interface;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JLoginActivity extends AppCompatActivity implements View.OnClickListener {
    // textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    public static final String LOGIN = "login";
    public static final String LANGUAGE = "0";

    public static final String USER_ID = "user_id";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_TYPE = "user_type";
    DB_Control db_control;
    SharedPreferences sh;
    SharedPreferences.Editor ed;
    TextView forget_password, tv_sign_up;
    private Communicator communicator;
    private String username, password;
    private Button btnLogin;
    private TabLayout tabLayoutLanguage;
    private EditText mEmailView;
    private EditText mPasswordView;
    private ProgressDialog progressDialog;
    static String token;
    static String userId;
    static int user_type;
    ArrayList<Company_Profile> company_profile = new ArrayList<>();
    ArrayList<Jobseeker> CV_List = new ArrayList<>();
    TextView Login;
    int con_live;
    JSONArray applicant = null;
    JSONArray company = null;
    private String remain_time;
    private String package_type;
    private String TAG = "Login HA";
    int language;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jlogin);
        communicator = new Communicator();
        db_control = new DB_Control(this);
        Login = (TextView) findViewById(R.id.Login);
        forget_password = (TextView) findViewById(R.id.forgot_password);
        tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);
        btnLogin = (Button) findViewById(R.id.btn_login);
        mEmailView = (EditText) findViewById(R.id.input_email);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        tabLayoutLanguage = (TabLayout) findViewById(R.id.tab_layout_language);



        sh=getSharedPreferences(JLoginActivity.LOGIN,MODE_PRIVATE);
        language=sh.getInt(JLoginActivity.LANGUAGE,0);

        if(language==1){
            TabLayout.Tab tab = tabLayoutLanguage.getTabAt(0);
            tab.select();
        }
        else if(language==2){
            TabLayout.Tab tab = tabLayoutLanguage.getTabAt(1);
            tab.select();
        }

        tabLayoutLanguage.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        if (tab.isSelected()) {
                            changeTextMyanmar();
                        }

                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                changeTextEnglish();
            }

            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        forget_password.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }


  /*  @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {

        progressDialog.dismiss();
        Toast.makeText(this, getResources().getText(R.string.Check_connection), Toast.LENGTH_SHORT).show();
    }*/

    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        db_control.openDb();
    }


    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        db_control.closeDb();
    }

    //thread code................... for job seeker
    public class Check_jobseeker extends AsyncTask<Object, Object, ArrayList<Jobseeker>> {
        protected void onPreExecute() {
        }


        protected ArrayList<Jobseeker> doInBackground(Object[] params) {
            JSONObject c = null;
            JSONObject app_object = null;


            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS);

            client.retryOnConnectionFailure();
            Request request = new Request.Builder()
                    .get()
                    .url("http://allreadymyanmar.com/api/jobseeker/" + userId)
                    .build();

            Response checkResponse = null;
            try {
                checkResponse = client.newCall(request).execute();
                String response = checkResponse.body().string();
                Log.e("UrlResponse", "Response from url: " + checkResponse);
                if (checkResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        applicant = jsonObject.getJSONArray("applicant");
                        if (applicant.length() != 0) {

                            c = applicant.getJSONObject(0);
                            String name = c.getString("name");
                            String father_name = c.getString("father_name");
                            String gender = c.getString("gender");
                            String nrc = c.getString("nrc_no");
                            String mobile_no = c.getString("mobile_no");
                            String email = c.getString("email");
                            String cv_form = c.getString("attach_cv");
                            CV_List.add(new Jobseeker(name, father_name, gender, nrc, mobile_no, email, cv_form));

                        }

                    } catch (JSONException e) {
                    }

                } else {
                }

            } catch (Exception e) {
            }
            return CV_List;
        }

        protected void onPostExecute(ArrayList<Jobseeker> CV_List) {
            if (CV_List != null) {
                if (CV_List.size() == 0) {
                    progressDialog.dismiss();
                    Toast.makeText(JLoginActivity.this, "Check Your Connection and Retry", Toast.LENGTH_SHORT).show();
                } else {
                    if (CV_List.get(0).getUserName().equals(" ")
                            || CV_List.get(0).getFather_Name().equals("null")
                            || CV_List.get(0).getMobile_no().equals("null")
                            || CV_List.get(0).getNRC().equals("null")
                            ) {

                        String user_name = mEmailView.getText().toString();
                        String user_password = mPasswordView.getText().toString();
                        progressDialog.dismiss();
                        Intent i = new Intent(JLoginActivity.this, JResumeActivity.class);
                        i.putExtra("U_TYPE", user_type);
                        i.putExtra("JP_username", user_name);
                        i.putExtra("JP_Password", user_password);
                        i.putExtra("JP_userID", userId);
                        i.putExtra("token", token);
                        i.putExtra("remain_time",remain_time);
                        startActivity(i);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Intent i1 = new Intent(JLoginActivity.this, ContentHost.class);
                        i1.putExtra("remain_time",remain_time);
                        db_control.openDb();
                        savestate();
                        startActivity(i1);
                        finish();
                    }
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(JLoginActivity.this, "Check Your Connection and Try again...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loginPost(final String login_name, final String password) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.retryOnConnectionFailure(true);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(35,TimeUnit.SECONDS)
                        .connectTimeout(35,TimeUnit.SECONDS)
                        .writeTimeout(35,TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://allreadymyanmar.com")
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.post(new login(login_name, password));
        call.enqueue(new Callback<ServerResponse>() {

            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                if (response.body().getStatusCode() == 210) {
                    progressDialog.dismiss();
                    Toast.makeText(JLoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus() == 200) {

                    token = response.body().getToken();
                    userId = response.body().getUserList().get(0).getId();
                    user_type = response.body().getUserList().get(0).getUserType();
                    remain_time = response.body().getUserList().get(0).getRemain_time();
                    package_type = response.body().getUserList().get(0).getPackage_type();
                    if (user_type == 2) {
                        new Check_jobseeker().execute(userId);
                    } else if (user_type == 1) {
                        new Check_Company().execute(userId);
                    } else if (user_type == 0) {
                        progressDialog.dismiss();
                        Toast.makeText(JLoginActivity.this, "Admin Account", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JLoginActivity.this, "Connection Lost", Toast.LENGTH_SHORT).show();
                //loginPost(login_name,password);
                //  BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }


    public void changeTextMyanmar() {
        LanguageHandler.changeLocale(getResources(), "my");
        sh = getSharedPreferences(LOGIN, MODE_PRIVATE);
        ed = sh.edit();
        ed.putInt(LANGUAGE, 2);
        ed.commit();
        tv_sign_up.setText(getResources().getString(R.string.Sing_up));
        mEmailView.setHint(getResources().getString(R.string.Login_Mobile));
        mPasswordView.setHint(getResources().getString(R.string.Login_Password));
        forget_password.setText(getResources().getString(R.string.Forget));
        Login.setText(getResources().getString(R.string.Login));
        btnLogin.setText(getResources().getString(R.string.Login));

    }

    public void changeTextEnglish() {
        LanguageHandler.changeLocale(getResources(), "en");
        sh = getSharedPreferences(LOGIN, MODE_PRIVATE);
        ed = sh.edit();
        ed = sh.edit();
        ed.putInt(JLoginActivity.LANGUAGE, 1);
        ed.commit();
        tv_sign_up.setText(getResources().getString(R.string.Sing_up));
        mEmailView.setHint(getResources().getString(R.string.Login_Mobile));
        mPasswordView.setHint(getResources().getString(R.string.Login_Password));
        forget_password.setText(getResources().getString(R.string.Forget));
        Login.setText(getResources().getString(R.string.Login));
        btnLogin.setText(getResources().getString(R.string.Login));

    }


    public void savestate() {
        sh = getSharedPreferences(LOGIN, MODE_PRIVATE);
        ed = sh.edit();
        ed.putBoolean(LOGIN, true);
        ed.putString(USER_ID, userId);
        // ed.putString(USER_TOKEN, token);

        ed.commit();
        String user_name = mEmailView.getText().toString();
        String user_password = mPasswordView.getText().toString();
        db_control.insertData(userId, user_name, user_password);

    }

    public void onClick(View v) {
        Check_connection check_connection = new Check_connection(JLoginActivity.this);
        con_live = check_connection.CheckWifiConnected();
        switch (v.getId()) {
            case R.id.forgot_password:
                Intent forgotIntent = new Intent(JLoginActivity.this, JForgetpassword.class);
                forgotIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(forgotIntent);
                break;
            case R.id.tv_sign_up:
                Intent sign_up_Intent = new Intent(JLoginActivity.this, JSignUpActivity.class);
                startActivity(sign_up_Intent);
                finish();
                break;
            case R.id.btn_login:
                if (con_live == 1 || con_live == 2) {
                    username = mEmailView.getText().toString();
                    password = mPasswordView.getText().toString();
                    if ((!(username.isEmpty()) & (!password.isEmpty()))) {
                        progressDialog = new ProgressDialog(JLoginActivity.this);
                        progressDialog.setMessage(getResources().getText(R.string.Log_in));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        loginPost(username, password);
                        //  usePost(username, password);
                    } else if (username.isEmpty() & password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your username and password again", Toast.LENGTH_SHORT).show();
                    } else if (username.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your username again", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter your password again", Toast.LENGTH_SHORT).show();
                    }
                    break;
                } else {
                    Toast.makeText(this, "Need to open Data or Wifi", Toast.LENGTH_SHORT).show();
                }
        }
    }
//TODO znk add new functions

//    public static void Forgetpwd_add(final Activity activity)
//    {
//      final Dialog dialog=new Dialog(activity);
//        dialog.setContentView(R.layout.dialog_addphoneno);
//        Button addphone_con=(Button)activity.findViewById(R.id.addphone_confirm);
//        addphone_con.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent forgotIntent = new Intent(activity, AddNewpswd.class);
//                forgotIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//               // forgotIntent.putExtra("phone");
//                activity.startActivity(forgotIntent);
//            }
//        });
//        Button addphone_cancel=(Button)activity.findViewById(R.id.addphone_cancel);
//        addphone_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//
//    }




    public class Check_Company extends AsyncTask {
        protected void onPreExecute() {
        }

        protected ArrayList<Company_Profile> doInBackground(Object[] params) {
            JSONObject company_p = null;
            String url = "http://allreadymyanmar.com/api/all_company/" + userId;
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                String check_response = response.body().string();
                if (check_response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(check_response);
                        company = jsonObject.getJSONArray("user_company");
                        Log.e("Company_Length", "Length" + company.length());

                    } catch (JSONException e) {
                        Log.e("Company JSON ERRROR", "ERROR" + e.getMessage());
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return company_profile;
        }

        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (o != null) {
                if (company.length() == 0) {
                    progressDialog.dismiss();
                    String user_namee = mEmailView.getText().toString();
                    String user_passwordd = mPasswordView.getText().toString();

                    Intent i0 = new Intent(JLoginActivity.this, EmployerProfile.class);
                    i0.putExtra("u_type", user_type);
                    i0.putExtra("login_username", user_namee);
                    i0.putExtra("remain_time", remain_time);
                    i0.putExtra("package_type", package_type);
                    i0.putExtra("login_userpassword", user_passwordd);
                    i0.putExtra("new_ha", 1);
                    i0.putExtra("new", 1);
                    i0.putExtra("user_id", userId);
                    startActivity(i0);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Intent i1 = new Intent(getApplicationContext(), EmployerFragHost.class);
                    i1.putExtra("remain_time", remain_time);
                    i1.putExtra("package_type", package_type);
                    savestate();
                    startActivity(i1);
                    finish();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(JLoginActivity.this, "Check Your Connection and Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
