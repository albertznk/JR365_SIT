package com.goldenictsolutions.win.goldenictjob365.Employee;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kurio Tetsuya on 6/30/2017.
 */

public class Check_connection {
    Context context;

    public Check_connection(Context context){
        this.context=context;
    }
    public int CheckWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected()) {
            return 1;
        }
        if (mMobile.isConnected()) {
            return 2;
        }
        return 0;
    }

}
