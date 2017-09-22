package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.AppointmentData;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 7/13/17.
 */

public class AppointmentAdapter extends BaseAdapter {
    private ArrayList<AppointmentData> appintList;
    private Context context;
    public AppointmentAdapter(Context context, ArrayList<AppointmentData> appointList) {
        this.context = context;
        this.appintList= appointList;
    }

    public int getCount() {
        return appintList.size();
    }

    public Integer getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lif = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custRow = lif.inflate(R.layout.appoint_row,parent,false);
        TextView Description = (TextView) custRow.findViewById(R.id.appointRowDescriptionDo);
        TextView Contact_Info = (TextView) custRow.findViewById(R.id.appointRowContactInfoDo);
        TextView Contact_No = (TextView) custRow.findViewById(R.id.appointRowContactNoDo);
        Description.setText(appintList.get(position).getDiscricption());
        Contact_Info.setText(appintList.get(position).getContact_info());
        Contact_No.setText(appintList.get(position).getContact_no());
        return custRow;
    }

}
