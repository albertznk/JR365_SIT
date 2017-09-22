package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.CView;
import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.DashBoard;
import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.More;
import com.goldenictsolutions.win.goldenictjob365.Employee.FragPage.SearchJOB;

/**
 * Created by nuke on 6/19/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Fragment fg = null;
    Fragment fa=new DashBoard();
    public ViewPagerAdapter(FragmentManager fragManager) {

        super(fragManager);
    }

    public Fragment getItem(int position) {
        switch (position) {

                case 0:
                fg=new DashBoard();
                break;
            case 1:
   //             fg=new DashBoard();
                fg = new CView();
                break;
            case 2:
                fg=new SearchJOB();
                break;
            case 3:
                fg=new More();
                break;

        }

        return fg;
    }

    public int getCount() {
        return 4;
    }
}
