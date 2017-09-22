package com.goldenictsolutions.win.goldenictjob365;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler.LanguageHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.SignUp;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Interface;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.ServerResponse;
import com.goldenictsolutions.win.goldenictjob365.commonfile.Register_confirm;
import com.squareup.otto.Subscribe;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.goldenictsolutions.win.goldenictjob365.JLoginActivity.LANGUAGE;
import static com.goldenictsolutions.win.goldenictjob365.JLoginActivity.LOGIN;

public class JSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    private static Switch switchAutoLogin;
    private Button registrationButton;
    private EditText telephoneET;
    private EditText passwordET;
    private EditText verifyPasswordET;
    static String token;
    static String userId;
    //static int user_type;
    private String telephone;
    private String password;
    private String verifyPassword;
    private int user_type = 2;
    private Boolean autoLoginBoolean = false;
    private RadioGroup radioSignUpGroup;
    private RadioButton radioSignUpButtonEmployer;
    private RadioButton radioSignUpButtonEmployee;
    TabLayout tab_language;
    TextView sign_up;
    private String remain_time, package_type;
    private SharedPreferences sh;
    private SharedPreferences.Editor edit;
    int language;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsign_up);
        radioSignUpButtonEmployer = (RadioButton) findViewById(R.id.radio_button_sign_up_employer);
        radioSignUpButtonEmployee = (RadioButton) findViewById(R.id.radio_button_sign_up_employee);
        sign_up = (TextView) findViewById(R.id.sign_up);


        radioSignUpGroup = (RadioGroup) findViewById(R.id.radio_group_sign_up);
        telephoneET = (EditText) findViewById(R.id.input_email_sign_up);
        passwordET = (EditText) findViewById(R.id.input_password_sign_up);
        verifyPasswordET = (EditText) findViewById(R.id.input_verify_password);

        registrationButton = (Button) findViewById(R.id.btn_registration);

        tab_language = (TabLayout) findViewById(R.id.tab_layout_language_sign_up);

        sh=getSharedPreferences(JLoginActivity.LOGIN,MODE_PRIVATE);
        language=sh.getInt(JLoginActivity.LANGUAGE,0);

        if(language==1){
            TabLayout.Tab tab = tab_language.getTabAt(0);
            tab.select();
        }
        else if(language==2){
            TabLayout.Tab tab = tab_language.getTabAt(1);
            tab.select();
        }


        tab_language.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        if (tab.isSelected()) {
                            //    Toast.makeText(JSignUpActivity.this, "Myanmar", Toast.LENGTH_SHORT).show();
                            changeTextMyanmar();
                        }

                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                //  Toast.makeText(JSignUpActivity.this, "English", Toast.LENGTH_SHORT).show();
                changeTextEnglish();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        registrationButton.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    //To sign up
    public void SignUpPost(String telephone_no, final String password, final int user_type, String email, String userName) {
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS).build();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://allreadymyanmar.com/")
                    .build();

            Interface service = retrofit.create(Interface.class);

            //Call<ServerResponse> call = service.get("login",username,password);
            Call<ServerResponse> call = service.postSignUp(new SignUp(telephone_no, password, user_type, userName, email));
            Log.i("Sign Up", "SignUpPost: " + telephone_no + " :::::" + password + ":::::::");
            call.enqueue(new Callback<ServerResponse>() {
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    progressDialog.dismiss();
                    Log.e("Body", "" + response.body().getStatusCode());
                    Log.e("Body", "" + response.body().getStatus());
                    if (response.body().getStatusCode() == 210) {
                        Toast.makeText(JSignUpActivity.this, "Duplicate User", Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 200) {
                        //startActivity
                        token = response.body().getToken();
                        userId = response.body().getUserList().get(0).getId();
                        int type = response.body().getUserList().get(0).getUserType();
                        remain_time = response.body().getUserList().get(0).getRemain_time();
                        package_type = response.body().getUserList().get(0).getPackage_type();
                        //Toast.makeText(JSignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JSignUpActivity.this, Register_confirm.class);
//                    Log.e("Package Type",package_type);
                        //                  Log.e("Remain Time",remain_time);
                        intent.putExtra("telephone", telephone);
                        intent.putExtra("password", password);
                        intent.putExtra("userType", type);
                        intent.putExtra("u_id", userId);
                        intent.putExtra("u_token", token);
                        intent.putExtra("package_type", package_type);
                        intent.putExtra("remain_time", remain_time);
                        startActivity(intent);
                        finish();
                    }
                    Log.e("Sign Up", "Success Sign Up" + response.code());
                    Log.e("Sign Up", "Success Sign Up" + response.body());
                    Log.e("Sign Up", "Success Sign Up" + response.message());
                    // BusProvider.getInstance().post(new ServerEvent(response.body()));
                    Log.e("Sign Up", "Success Sign Up");
                }

                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    // handle execution failures like no internet connectivity
                    progressDialog.dismiss();
                    Toast.makeText(JSignUpActivity.this, "Connection Lost...", Toast.LENGTH_SHORT).show();
                    Log.e("Sign Up", "Failure " + t.getMessage());
                    //     BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
                }
            });
        }
    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        if (serverEvent.getServerResponse() == null) {
            progressDialog.dismiss();
            //  Toast.makeText(getApplicationContext(), "invalid credentials", Toast.LENGTH_SHORT).show();
        }
        else if (serverEvent.getServerResponse() != null) {

            if (serverEvent.getServerResponse().getStatusCode() == 210) {
                progressDialog.dismiss();
                Toast.makeText(this, "Duplicate User", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                token = serverEvent.getServerResponse().getToken();
                userId = serverEvent.getServerResponse().getUserList().get(0).getId();
                user_type = serverEvent.getServerResponse().getUserList().get(0).getUserType();
                remain_time = serverEvent.getServerResponse().getUserList().get(0).getRemain_time();
                package_type = serverEvent.getServerResponse().getUserList().get(0).getPackage_type();

                if (userId != null) {
                    Intent intent = new Intent(JSignUpActivity.this,Register_confirm.class);
                    Log.e("Package Type", package_type);
                    Log.e("Remain Time", remain_time);
                    intent.putExtra("telephone", telephone);
                    intent.putExtra("password", password);
                    intent.putExtra("userType", user_type);
                    intent.putExtra("u_id", userId);
                    intent.putExtra("u_token", token);
                    intent.putExtra("package_type", package_type);
                    intent.putExtra("remain_time", remain_time);
                    startActivity(intent);
                    finish();
                } else {
                    telephone = telephoneET.getText().toString();
                    password = passwordET.getText().toString();
                    verifyPassword = verifyPasswordET.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Connection Lost");
                    builder.setMessage("Plz retry");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //   useSignUpPost(telephone, password, user_type, " ", " ");
                            SignUpPost(telephone, password, user_type, "", "");
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }


        }

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        progressDialog.dismiss();

        Toast.makeText(getApplicationContext(), "Check your Connection ", Toast.LENGTH_LONG).show();
    }

    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_button_sign_up_employer:
                if (checked)
                    // Pirates are the best
                    user_type = 1;
                break;
            case R.id.radio_button_sign_up_employee:
                if (checked)
                    // Ninjas rule
                    user_type = 2;
                break;
        }
    }

    public void changeTextMyanmar() {
        LanguageHandler.changeLocale(getResources(), "my");
        sh = getSharedPreferences(LOGIN, MODE_PRIVATE);
        edit = sh.edit();
        edit.putInt(LANGUAGE, 2);
        Log.e("IOP", "OP" + sh.getInt(LANGUAGE, 0));
        edit.commit();
        registrationButton.setText(getResources().getString(R.string.Registration));
        telephoneET.setHint(getResources().getString(R.string.Phone_Number));
        passwordET.setHint(getResources().getString(R.string.Password));
        verifyPasswordET.setHint(getResources().getString(R.string.Verify_Password));
        radioSignUpButtonEmployee.setText(getResources().getString(R.string.Employee));
        radioSignUpButtonEmployer.setText(getResources().getString(R.string.Employer));
        sign_up.setText(getResources().getString(R.string.Sing_up));
    }

    public void onBackPressed() {
        Intent i = new Intent(JSignUpActivity.this, JLoginActivity.class);
        startActivity(i);
        finish();

    }

    public void changeTextEnglish() {
        LanguageHandler.changeLocale(getResources(), "en");

        sh = getSharedPreferences(LOGIN, MODE_PRIVATE);
        edit = sh.edit();
        edit = sh.edit();
        edit.putInt(JLoginActivity.LANGUAGE, 1);
        edit.commit();

        registrationButton.setText(getResources().getString(R.string.Registration));
        telephoneET.setHint(getResources().getString(R.string.Phone_Number));
        passwordET.setHint(getResources().getString(R.string.Password));
        verifyPasswordET.setHint(getResources().getString(R.string.Verify_Password));
        radioSignUpButtonEmployee.setText(getResources().getString(R.string.Employee));
        radioSignUpButtonEmployer.setText(getResources().getString(R.string.Employer));
        sign_up.setText(getResources().getString(R.string.Sing_up));

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registration:
                telephone = telephoneET.getText().toString();
                password = passwordET.getText().toString();
                verifyPassword = verifyPasswordET.getText().toString();

                if ((!(telephone.isEmpty()) & (!password.isEmpty()))) {
                    if (!(password.equals(verifyPassword))) {
                        Toast.makeText(getApplicationContext(), "Verify your password again", Toast.LENGTH_SHORT).show();
                    }
                    else {
                          if (telephone.length() >= 9 && telephone.length() < 14 ) {
                              if (password.length() >= 6) {
                                  progressDialog = new ProgressDialog(JSignUpActivity.this);
                                  progressDialog.setMessage("Sign Up.....");
                                  progressDialog.setCancelable(false);
                                  progressDialog.show();
                                  SignUpPost(telephone, password, user_type, "", "");
                              }
                              else {
                                  Toast.makeText(JSignUpActivity.this,"Password must be at least 6",Toast.LENGTH_LONG).show();
                              }
                          }


                        else {
                              Toast.makeText(JSignUpActivity.this, "Telephone number must be at least 10.", Toast.LENGTH_SHORT).show();

                          }

                    }

                } else if (telephone.isEmpty() & password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your username and password again", Toast.LENGTH_SHORT).show();
                } else if (telephone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your username again", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your password again", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
