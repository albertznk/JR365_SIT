package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.CandidateView;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 8/8/17.
 */

public class ShortlistAdapter extends RecyclerView.Adapter<ShortlistAdapter.Holder> {
    private final ArrayList<EmployerModel.ShortlistModel> shortlistArray;
    private Context context;
    private String SHORTLISTED_API = "http://allreadymyanmar.com/api/shortlisted/";

    public ShortlistAdapter(Context context, ArrayList<EmployerModel.ShortlistModel> openJobsModels) {
        this.context = context;
        this.shortlistArray = openJobsModels;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lif = LayoutInflater.from(parent.getContext());
        View v = lif.inflate(R.layout.short_list_row, null);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.jobType.setText(shortlistArray.get(position).getJob_title().toString());
        holder.candidateCount.setText(shortlistArray.get(position).getJob_count().toString());
        holder.candidateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortlistArray.get(position).getId().toString() != "") {
                    Intent i = new Intent(context, CandidateView.class);
                    i.putExtra("shortlist_id", SHORTLISTED_API + shortlistArray.get(position).getId());
                    context.startActivity(i);
                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shortlistArray.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView jobType, candidateCount;

        public Holder(View itemView) {
            super(itemView);
            jobType = (TextView) itemView.findViewById(R.id.jobTypeShort);
            candidateCount = (TextView) itemView.findViewById(R.id.candidateCountShort);
        }
    }
}

