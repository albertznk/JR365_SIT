package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.CandidateView;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerViewApplicant;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;

/**
 * Created by nuke on 8/5/17.
 */

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.Holder> {
    private  String job_id , primary_mobile_no,CompanyMobile,CompName;
    private ArrayList<EmployerModel.CandidateListModel> candidateDataList;
    private Context context;
    private ImageLoader imgLoader;



    public CandidateListAdapter(CandidateView candidateView, ArrayList<EmployerModel.CandidateListModel> candidateListModels, String job_id,String primary_mobile_no, String CompanyMobile,String CompName) {
        this.context = candidateView;
        this.candidateDataList = candidateListModels;
        imgLoader = new ImageLoader(context);
        this.job_id = job_id;
        this.primary_mobile_no = primary_mobile_no;
        this.CompanyMobile = CompanyMobile;
        this.CompName = CompName;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lif = LayoutInflater.from(parent.getContext());
        View v = lif.inflate(R.layout.employer_candi_row, null);
        return new Holder(v);
    }

    public void onBindViewHolder(Holder holder, final int position) {
        holder.name.setText(candidateDataList.get(position).getName());
        holder.phone.setText(candidateDataList.get(position).getMobile_no());
        holder.email.setText(candidateDataList.get(position).getEmail());
        Log.e("Fuck",""+candidateDataList.get(position).getPhoto());
        imgLoader.DisplayImage("http://allreadymyanmar.com/uploads/resume-photo/"+candidateDataList.get(position).getPhoto(),holder.photo);
        holder.viewApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EmployerViewApplicant.class);
                i.putExtra("applicant_id", candidateDataList.get(position).getUser_id());
                i.putExtra("job_id",job_id);
                if (!primary_mobile_no.isEmpty()){
                   i.putExtra("CompanyName",CompName);
                   i.putExtra("CompMobile",CompanyMobile);
                   i.putExtra("PrimaryMobile",primary_mobile_no);
                }
                try {
                    if (candidateDataList.get(position).getMobile_no() != "") {
                        i.putExtra("mobile_no", candidateDataList.get(position).getMobile_no());
                    }
                    context.startActivity(i);
                } catch (Exception e) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return candidateDataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView name, phone, email;
        public ImageView photo;
        public Button viewApplicant;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.empe_name);
            phone = (TextView) itemView.findViewById(R.id.empe_phone);
            email = (TextView) itemView.findViewById(R.id.empe_email);
            photo = (ImageView) itemView.findViewById(R.id.jobseekerPhoto);
            viewApplicant = (Button) itemView.findViewById(R.id.viewApplicant);
        }
    }
}
