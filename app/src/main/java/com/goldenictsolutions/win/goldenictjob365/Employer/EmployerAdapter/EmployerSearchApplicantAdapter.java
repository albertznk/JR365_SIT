package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerViewApplicant;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployerSearchApplicantAdapter extends RecyclerView.Adapter<EmployerSearchApplicantAdapter.Holder> {
    public Context context;
    public ImageLoader imgLoader;
    public ArrayList<EmployerModel.Search_Applicant_Data> reultList;
    ViewGroup parent;
    int position;
    HashMap hashMap = new HashMap();

    public EmployerSearchApplicantAdapter(ArrayList<EmployerModel.Search_Applicant_Data> resultList, Context context) {
        this.reultList = resultList;
        imgLoader = new ImageLoader(context);
        this.context = context;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_search_update,parent,false);

        return new Holder(v);
    }

    public void onBindViewHolder(Holder holder, int position) {
        this.position = position;
        holder.JobType.setText(reultList.get(position).getName());
        holder.UserName.setText(reultList.get(position).getMobile_no());
        holder.Location.setText(reultList.get(position).getTownship() + " - " + reultList.get(position).getCity());
        String imageUrl = "http://allreadymyanmar.com/uploads/resume-photo/" + reultList.get(position).getPhoto();
        imgLoader.DisplayImage(imageUrl, holder.companyLogo);


    }

    public int getItemCount() {
        return reultList.size();
    }

    private void TransferData(int position) {
        hashMap.put("ID", reultList.get(position).getId());
        hashMap.put("User_id", reultList.get(position).getUser_id());
        hashMap.put("Name", reultList.get(position).getName());
        hashMap.put("FatherName", reultList.get(position).getFather_name());
        hashMap.put("martital_Status", reultList.get(position).getMarital_status());
        hashMap.put("Gender", reultList.get(position).getGender());
        hashMap.put("Expected_salary", reultList.get(position).getExpected_salary());
        hashMap.put("Date_of_Birth", reultList.get(position).getDate_of_birth());
        hashMap.put("Nrc_No", reultList.get(position).getNrc_no());
        hashMap.put("Religion", reultList.get(position).getReligion());
        hashMap.put("Place_of_Birth", reultList.get(position).getPlace_of_birth());
        hashMap.put("Photo", reultList.get(position).getPhoto());
        hashMap.put("Mobile_No", reultList.get(position).getMobile_no());
        hashMap.put("Email", reultList.get(position).getEmail());
        hashMap.put("Address", reultList.get(position).getAddress());
        hashMap.put("Nationality", reultList.get(position).getNationality());
        hashMap.put("CV_Views", reultList.get(position).getCv_views());
        hashMap.put("Attach_cv", reultList.get(position).getAttach_cv());
        hashMap.put("Current_Position", reultList.get(position).getCurrent_position());
        hashMap.put("Desired_Position", reultList.get(position).getDesired_position());
        hashMap.put("Driving_license", reultList.get(position).getDriving_license());
        hashMap.put("City", reultList.get(position).getCity());
        hashMap.put("Township", reultList.get(position).getTownship());
        hashMap.put("Country", reultList.get(position).getCountry());
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView JobType, UserName, Location;

        public Holder(View v) {
            super(v);
            companyLogo = (ImageView) v.findViewById(R.id.employer_logo);
            JobType = (TextView) v.findViewById(R.id.jobType);
            UserName = (TextView) v.findViewById(R.id.companyName);
            Location = (TextView) v.findViewById(R.id.companyLocation);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            //hashMap.clear();
            Log.e("HSH", "onClick: " + hashMap.size());
            TransferData(getLayoutPosition());
            Intent intent = new Intent(context, EmployerViewApplicant.class);
            intent.putExtra("applicant_id", hashMap.get("User_id").toString());
            Log.e("user_id",""+hashMap.get("User_id").toString());
            parent.getContext().startActivity(intent);
        }
    }
}