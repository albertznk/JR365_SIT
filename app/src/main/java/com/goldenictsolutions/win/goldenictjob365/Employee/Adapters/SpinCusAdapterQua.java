/*
package com.goldenictsolutions.win.jobready365_.Employee.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.jobready365_.Employee.PojoJavaClasses.JobCategory;
import com.goldenictsolutions.win.jobready365_.Employee.PojoJavaClasses.OtherQualification;
import com.goldenictsolutions.win.jobready365_.R;

import java.util.List;

*/
/**
 * Created by Win on 6/22/2017.
 *//*


public class SpinCusAdapterQua extends BaseAdapter {
    Context context;
    int flags[];

    List<OtherQualification> qualificationList;
    LayoutInflater inflter;

    public SpinCusAdapterQua(Context applicationContext, List<OtherQualification> qualificationList) {            // int[] flags  as parameter
        this.context = applicationContext;
        //  this.flags = flags;
        this.qualificationList = qualificationList;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
   return qualificationList.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        //   ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView_spin);
        // icon.setImageResource(flags[i]);


        names.setText(qualificationList.get(i).getCenterName().toString());

        return view;
    }
}
*/
