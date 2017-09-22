package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataTransferMedium;
import com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerDashboard;
import com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerMenu;
import com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerPostJob;
import com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerSearch;
import com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerShorlist;
import com.goldenictsolutions.win.goldenictjob365.R;


/*
 * Created by nuke on 8/2/17.
 */

public class EmployerFragHost extends AppCompatActivity implements ViewPager.OnPageChangeListener, DataTransferMedium {
    TextView toolbar_text;
    private TabLayout tabHostLayout;
    private ViewPager pagerHost;
    private FragmentManager fragManager;
    public static String remain_time;
    public static String package_Type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_frag_host);
        tabHostLayout = (TabLayout) findViewById(R.id.tabHostLayout);
        pagerHost = (ViewPager) findViewById(R.id.viewPagerHost);
        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        fragManager = getSupportFragmentManager();
        remain_time = getIntent().getExtras().getString("remain_time");
        package_Type = getIntent().getExtras().getString("package_type");
        PageHostAdapter pAdpt = new PageHostAdapter(fragManager);
        pagerHost.setAdapter(pAdpt);
        tabHostLayout.setupWithViewPager(pagerHost);
        tabSetPager();
        pagerHost.setOnPageChangeListener(this);
    }

    private void tabSetPager() {
        tabHostLayout.getTabAt(0).setCustomView(R.layout.employer_dashboard);
        tabHostLayout.getTabAt(1).setCustomView(R.layout.employer_post_job);
        tabHostLayout.getTabAt(2).setCustomView(R.layout.employer_shorlist);
        tabHostLayout.getTabAt(3).setCustomView(R.layout.employer_search);
        tabHostLayout.getTabAt(4).setIcon(R.drawable.ic_menu_black_24dp);
    }


    /// PostNewJobNotifier method is to get data from post job fragment to dashboard
    public void PostNewJobNotifier(Boolean successOrNot, int CompPosition) {
        String tag = "android:switcher:" + R.id.viewPagerHost + ":" + 0;
        fragManager = getSupportFragmentManager();
        EmployerDashboard employerDashboard = (EmployerDashboard) fragManager.findFragmentByTag(tag);
        employerDashboard.GetData(successOrNot, CompPosition);
        pagerHost.setCurrentItem(0);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                toolbar_text.setText(getResources().getString(R.string.Employer_Dash));
                break;
            case 1:
                toolbar_text.setText(getResources().getString(R.string.Employer_Job_post));
                break;
            case 2:
                toolbar_text.setText(getResources().getString(R.string.Employer_shortlist));
                break;
            case 3:
                toolbar_text.setText(getResources().getString(R.string.Employer_Search));
                break;
            case 4:
                toolbar_text.setText(getResources().getString(R.string.Employer_More));
                break;
        }
    }

    public void onPageSelected(int position) {

    }

    public void onPageScrollStateChanged(int state) {
        //    Log.i("KFC", "onPageScrollStateChanged: "+state);
    }


    private class PageHostAdapter extends FragmentPagerAdapter {
        public Fragment fragment;

        public PageHostAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = new EmployerDashboard();
                    break;
                case 1:
                    fragment = new EmployerPostJob();
                    break;
                case 2:
                    fragment = new EmployerShorlist();
                    break;
                case 3:
                    fragment = new EmployerSearch();
                    break;
                case 4:
                    fragment = new EmployerMenu();
                    break;
            }
            return fragment;
        }

        public int getCount() {
            return 5;
        }
    }

    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make(tabHostLayout, getResources().getString(R.string.logout_exit), Snackbar.LENGTH_LONG);
        snackbar.setAction(getResources().getString(R.string.exit), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackbar.show();
    }

}
