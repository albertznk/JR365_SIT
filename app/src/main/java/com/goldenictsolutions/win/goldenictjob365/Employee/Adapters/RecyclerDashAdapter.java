package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ShowDetailJob;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.DataList;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 6/19/17.
 */

public class RecyclerDashAdapter extends RecyclerView.Adapter<RecyclerDashAdapter.Holder> {
    private ViewGroup parent;
    LayoutInflater lnf;
    ArrayList<DataList> infoList = null;
    ImageLoader imgLoader;
    public RecyclerDashAdapter(ArrayList<DataList> info, Context context)
    {
        this.infoList = info;
         imgLoader = new ImageLoader(context);
    }
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        //lnf=LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_search_update, parent, false);
//        View v=lnf.inflate(R.layout.dash_row_layout,false);
        return new Holder(v);
    }

    public void onBindViewHolder(Holder holder, int position) {
        holder.JobType.setText(infoList.get(position).getJobTitle());
        holder.CompanyName.setText(infoList.get(position).getCompanyName());
        holder.CompanyLocation.setText(infoList.get(position).getTownship() + " - " + infoList.get(position).getCity());
        String imageUrl="http://allreadymyanmar.com/uploads/company_logo/"+infoList.get(position).getCompanyLogo();
        imgLoader.DisplayImage(imageUrl,holder.companyLogo);
    }
    public int getItemCount(){
        return infoList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView JobType,CompanyName,CompanyLocation;
        RelativeLayout dash_row_relative;
        public Holder(View v)
        {
            super(v);
            companyLogo = (ImageView) v.findViewById(R.id.employer_logo);
            JobType = (TextView) v.findViewById(R.id.jobType);
            CompanyName = (TextView) v.findViewById(R.id.companyName);
            CompanyLocation = (TextView) v.findViewById(R.id.companyLocation);
            dash_row_relative= (RelativeLayout) v.findViewById(R.id.dash_row_relative);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent i = new Intent(parent.getContext(),ShowDetailJob.class);
            i.putExtra("position",getLayoutPosition());
            parent.getContext().startActivity(i);

        }
    }
//public void clear(){
//    infoList.clear();
//    notifyDataSetChanged();
//}
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position;
    }
}
