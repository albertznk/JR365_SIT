/*
package com.goldenictsolutions.win.jobready365_.Employee.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.goldenictsolutions.win.jobready365_.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

*/
/**
 * Created by Win on 2/28/2017.
 *//*


public class SpinnerCustomAdapter extends BaseAdapter {
    Context context;
   // int flags[];
    String [] cityNames ;
    LayoutInflater inflater;
    Activity activity;
    private static String LOG_TAG = "SpinnerAdapter";

    public SpinnerCustomAdapter(Activity activity, String [] cityNames) {
        this.activity = activity;
      //  this.flags = flags;
        this.cityNames = cityNames;
        inflater = (LayoutInflater.from(activity));
    }

    @Override
    public int getCount() {
        return cityNames.length;
    }

    @Override
    public Object getItem(int i) {
        return cityNames[i].toString();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_items, null);
       // ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView_spin);
       // icon.setImageResource(flags[i]);

        names.setText(Arrays.asList(cityNames).get(i));

        return view;
    }



}*/
