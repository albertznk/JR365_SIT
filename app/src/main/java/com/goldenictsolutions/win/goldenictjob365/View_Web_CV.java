package com.goldenictsolutions.win.goldenictjob365;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by kurio on 8/26/17.
 */

public class View_Web_CV extends AppCompatActivity {
    WebView view_cv_web;
    String cv_link;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cv_web);
        cv_link = getIntent().getExtras().getString("cv_link");
        Log.e("Par lar lr", "par tl" + cv_link);
        Log.e("lolz", "file");
        view_cv_web = (WebView) findViewById(R.id.view_cv_web);
        if (cv_link.contains(".pdf")) {
            view_cv_web.getSettings().setJavaScriptEnabled(true);
            view_cv_web.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + "http://allreadymyanmar.com/uploads/cv/" + cv_link);
        }
    }
}
