package com.goldenictsolutions.win.goldenictjob365.commonfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.R;

public class Feedback_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__form);
        TextView empr_backto_search=(TextView) findViewById(R.id.empr_feed_back);
        empr_backto_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
