package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.ViewPagerAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.CView;
import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.DashBoard;
import com.goldenictsolutions.win.goldenictjob365.R;

/**
 * Created by nuke on 6/19/17.
 */

public class ContentHost extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private TabLayout tabHost;
    public ViewPager vPager;
    private ViewPagerAdapter vPagerAdapt;
    private FragmentManager fragManager;
    private ProgressDialog progress;
    TextView toolbar_text;
    public static String remain_time;
    public DashBoard dashBoard;
    private Activity act;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_host);
        remain_time=getIntent().getExtras().getString("remain_time");
        toolbar_text= (TextView) findViewById(R.id.toolbar_text);
        vPager = (ViewPager) findViewById(R.id.vPager);
        tabHost = (TabLayout) findViewById(R.id.tabHost);
        fragManager = getSupportFragmentManager();
        vPager.setOnPageChangeListener(this);
        vPagerAdapt= new ViewPagerAdapter(fragManager);
        //if (vPagerAdapt!=null){
        //vPager.setAdapter(vPagerAdapt);}
        //else
        //{
            vPager.setAdapter(vPagerAdapt);
        vPagerAdapt.notifyDataSetChanged();

        //}

        TabSetUp();
    }

    private void TabSetUp() {
        tabHost.setupWithViewPager(vPager);
        tabHost.getTabAt(0).setCustomView(R.layout.dash_cust);
        tabHost.getTabAt(1).setCustomView(R.layout.c_view);
        tabHost.getTabAt(2).setCustomView(R.layout.search_jobs);
        tabHost.getTabAt(3).setIcon(R.drawable.ic_menu_black_24dp);

    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    public void onPageSelected(int position) {
        CView cView=new CView();

        DashBoard dashBoard=new DashBoard();
        switch (position) {
                case 0:
                    reload();
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
                    toolbar_text.setText(getResources().getText(R.string.toolbar_dashboard));
                break;
            case 1:
                toolbar_text.setText(getResources().getText(R.string.toolbar_cv));
                break;
            case 2:
                toolbar_text.setText(getResources().getText(R.string.search_empr));
                break;
            case 3:
                toolbar_text.setText(getResources().getText(R.string.toolbar_More));
                break;
        }
    }

    public void onPageScrollStateChanged(int state) {

    }

    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.exit));
        builder.setMessage(getResources().getString(R.string.logout_exit));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }




}
