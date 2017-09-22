package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 8/8/17.
 */

public class ListedAdapter extends BaseAdapter {
    private ArrayList<EmployerModel.ShortListedModel> shortlistArray;
    private Context context;

    public ListedAdapter(Context context, ArrayList<EmployerModel.ShortListedModel> shortListedModels) {
        this.shortlistArray = shortListedModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shortlistArray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lif.inflate(R.layout.listed_name_row, null);
        TextView jobTitle = (TextView) convertView.findViewById(R.id.companyNameTXT);
        jobTitle.setText(shortlistArray.get(position).getCompany_name());
        Log.i("ADA", "getView: " + shortlistArray.get(position).getCompany_name() + "+++");
        return convertView;
    }
}
