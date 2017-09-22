package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.AppliedJobHistory;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ShowDetailJob;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 6/25/17.
 */

public class AppliedJobHistroyAdapter extends RecyclerView.Adapter<AppliedJobHistroyAdapter.Holder> {
    private ViewGroup parent;
    LayoutInflater lnf;
    ArrayList<AppliedJobHistory.AppliedHistory> infoList;
    ImageLoader imgLoader;
    Context context;
    public AppliedJobHistroyAdapter(ArrayList<AppliedJobHistory.AppliedHistory> info, Context context)
    {
        this.infoList = info;
        imgLoader = new ImageLoader(context);
    }
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        lnf=LayoutInflater.from(parent.getContext());
        View v=lnf.inflate(R.layout.applied_row_list,null);
        return new Holder(v);
    }


    public void onBindViewHolder(Holder holder, int position) {
        holder.JobTitel.setText(infoList.get(position).getJob_title());
        holder.CompanyName.setText(infoList.get(position).getCompany_name());
        holder.CompanyAddress.setText(infoList.get(position).getCompany_address());
        //Glide.with(context).load(infoList.get(position).get)
       // holder.AppliedDate.setText(infoList.get(position).getDate_apply());
       // holder.CloseDate.setText(infoList.get(position).getClose_date());

    }

    public int getItemCount(){
        return infoList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView JobTitel,CompanyName,AppliedDate,CloseDate,CompanyAddress;
        ImageView employer_logo;
        public Holder(View v)
        {
            super(v);
            JobTitel = (TextView) v.findViewById(R.id.ApJobTitle);
            CompanyName = (TextView) v.findViewById(R.id.ApCompanyName);
            CompanyAddress= (TextView) v.findViewById(R.id.ApCompanyAddress);
            employer_logo=(ImageView)v.findViewById(R.id.employer_logo);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(parent.getContext(), ShowDetailJob.class);
                    ii.putExtra("job_id",infoList.get(getLayoutPosition()).getJob_id());
                    ii.putExtra("app_key",2);
                    parent.getContext().startActivity(ii);
                }
            });
        }
    }
}
