package com.goldenictsolutions.win.goldenictjob365;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.goldenictsolutions.win.goldenictjob365.Employee.JResumeActivity;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerProfile;

import java.util.List;
import java.util.Locale;

public class JPolicy extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Button btnNext;
    //   private Switch aSwitch;
    Locale myLocale;
    int user_type = 2;
    static int counter = 1;
    static List<String> itemsCity;
    static List<String> itemsTownship;
    private Communicator communicator;
    int userType;
    String telephone, password, userId, token;
    CheckBox agree;
    String package_type,remain_time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpolicy);
        btnNext = (Button) findViewById(R.id.btn_next);
        communicator = new Communicator();

        agree = (CheckBox) findViewById(R.id.Agree);
        userType = getIntent().getExtras().getInt("userType");
        telephone = getIntent().getExtras().get("telephone").toString();
        password = getIntent().getExtras().get("password").toString();
        userId = getIntent().getExtras().get("u_id").toString();
        token=getIntent().getExtras().get("u_token").toString();
        remain_time=getIntent().getExtras().getString("remain_time");
        package_type=getIntent().getExtras().getString("package_type");
        // usePost(telephone,password);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        agree.setOnCheckedChangeListener(this);


        btnNext.setEnabled(false);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userType == 2) {
//Employee

                    Intent i = new Intent(JPolicy.this, JResumeActivity.class);
                    i.putExtra("JP_userID", userId);
                    i.putExtra("JP_username", telephone);
                    i.putExtra("JP_Password", password);
                    i.putExtra("U_TYPE", userType);
                    i.putExtra("token", token);
                    startActivity(i);
                    finish();
                } else {
                    //Employer
                    Intent i0 = new Intent(JPolicy.this, EmployerProfile.class);
                    i0.putExtra("u_type", user_type);
                    i0.putExtra("login_username", telephone);
                    i0.putExtra("login_userpassword", password);
                    i0.putExtra("user_id", userId);
                    i0.putExtra("new", 1);
                    i0.putExtra("new_ha", 1);
                    i0.putExtra("user_token", token);
                    i0.putExtra("package_type",package_type);
                    i0.putExtra("remain_time",remain_time);
                    startActivity(i0);
                    finish();
                }
            }
        });


    }

    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    public void onBackPressed() {
    }


    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
    }
}

