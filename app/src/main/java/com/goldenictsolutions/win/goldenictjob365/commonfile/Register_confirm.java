package com.goldenictsolutions.win.goldenictjob365.commonfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.R;

public class Register_confirm extends AppCompatActivity {
 TextView confirm_codetext;
 EditText confirm_code_edtxt;
    Button btn_code_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);

        confirm_code_edtxt=(EditText)findViewById(R.id.confirm_code);
        confirm_codetext=(TextView)findViewById(R.id.confirm_code_txt);
        btn_code_confirm=(Button)findViewById(R.id.btn_code_confirm);
        btn_code_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm_code_edtxt.length()>0)
                {

                }
                else
                {
                        confirm_codetext.setText("If you didn't recieved any code click resend");
                }
            }
        });
    }
}
