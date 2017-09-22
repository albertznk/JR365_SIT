package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.R;
import com.goldenictsolutions.win.goldenictjob365.commonfile.AddNewpswd;

/**
 * Created by Kurio Tetsuya on 6/21/2017.
 */

public class JForgetpassword extends AppCompatActivity {
    Button btn_forgetpassword,btn_forgetpswe_back;
    TextView forgetpswd_txt,forgetpswd_resend;
    EditText forgetpswd_phone,forgetpswd_fourd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        btn_forgetpassword= (Button) findViewById(R.id.btn_forgetpassword);
        forgetpswd_phone=(EditText)findViewById(R.id.forgetpswd_phone);
        forgetpswd_fourd=(EditText)findViewById(R.id.forgetpswd_fourd);
        forgetpswd_resend=(TextView) findViewById(R.id.forgetpswd_recent);
        forgetpswd_txt=(TextView)findViewById(R.id.forgetpswd_txt);
        btn_forgetpswe_back=(Button)findViewById(R.id.btn_forgetpswd_back);
        btn_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forgetpswd_phone.length()>0 && forgetpswd_phone.length()<6 )
                {
                        forgetpswd_phone.setVisibility(View.INVISIBLE);
                        forgetpswd_fourd.setVisibility(View.VISIBLE);
                        forgetpswd_resend.setVisibility(View.VISIBLE);
                        forgetpswd_txt.setText("We have been send confirmation code to your phone ");
                }
                 if (forgetpswd_fourd.length()>0 && forgetpswd_fourd.length()<6){
                    Intent toaddnewpswd=new Intent(JForgetpassword.this, AddNewpswd.class);
                    startActivity(toaddnewpswd);
                    finish();

            }
                else
                {

                }
            }
        });
        btn_forgetpswe_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onBackPressed(){

    }
}
