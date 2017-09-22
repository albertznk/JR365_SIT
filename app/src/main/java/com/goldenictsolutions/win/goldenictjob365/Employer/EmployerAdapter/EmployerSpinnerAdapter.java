package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 8/3/17.
 */

public class EmployerSpinnerAdapter extends BaseAdapter {
    private ArrayList<EmployerModel.companyListModel> compNameList;
    private Context context;

    public EmployerSpinnerAdapter(Context context, ArrayList<EmployerModel.companyListModel> companyListModels) {
        this.context = context;
        this.compNameList = companyListModels;
    }

    @Override
    public int getCount() {
        return compNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lif = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lif.inflate(R.layout.employer_spinner_row, null);
        TextView tv = (TextView) convertView.findViewById(R.id.spinnerText);
        tv.setText(compNameList.get(position).getCompName());
        return convertView;
    }
}
