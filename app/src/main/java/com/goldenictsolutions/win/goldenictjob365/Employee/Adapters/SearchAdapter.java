package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ShowDetailJob;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.SearchDataList;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nuke on 6/26/17.
 */

public class SearchAdapter extends RecyclerView.Adapter <SearchAdapter.Holder> {
    Context context;
    ImageLoader imgLoader;
    public ArrayList<SearchDataList> reultList;
    ViewGroup parent;
    int position;
    HashMap hashMap = new HashMap();

    public SearchAdapter(ArrayList<SearchDataList> resultList, Context context) {
        this.reultList = resultList;
        imgLoader = new ImageLoader(context);
        this.context = context;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
//        LayoutInflater lnf = LayoutInflater.from(parent.getContext());
//        View v = lnf.inflate(R.layout.employer_search_update, null);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employer_search_update, parent, false);
    //TODO to remind (Change dash_row_layout to employer_search_update)
        return new Holder(v);
    }

    public void onBindViewHolder(Holder holder, int position) {
        this.position = position;
        holder.JobType.setText(reultList.get(position).getJobTitle());
        holder.CompanyName.setText(reultList.get(position).getCompanyName());
        holder.CompanyLocation.setText(reultList.get(position).getTownship() + " - " + reultList.get(position).getCity());
        Log.e("LOP",""+reultList.get(position).getCompanyLogo());
        String imageUrl = "http://allreadymyanmar.com/uploads/company_logo/"+reultList.get(position).getCompanyLogo();
        imgLoader.DisplayImage(imageUrl, holder.companyLogo);
    }

    public int getItemCount() {
        return reultList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView JobType, CompanyName, CompanyLocation;

        public Holder(View v) {
            super(v);
            companyLogo = (ImageView) v.findViewById(R.id.employer_logo);
            JobType = (TextView) v.findViewById(R.id.jobType);
            CompanyName = (TextView) v.findViewById(R.id.companyName);
            CompanyLocation = (TextView) v.findViewById(R.id.companyLocation);
            v.setOnClickListener(this);
        }


        public void onClick(View v) {
            hashMap.clear();
            Log.e("HSH", "onClick: "+hashMap.size());
            TransferData(getLayoutPosition());
            Intent intent = new Intent(context, ShowDetailJob.class);
            intent.putExtra("hsh", hashMap);
            intent.putExtra("SearchKey", 1);
            parent.getContext().startActivity(intent);
        }
    }

    private void TransferData(int position) {
        hashMap.put("Id", reultList.get(position).getId());
        hashMap.put("CompanyId",reultList.get(position).getCompanyId());
        hashMap.put("category",reultList.get(position).getCategory());
        hashMap.put("job_nature_id",reultList.get(position).getJobNatureId());
        hashMap.put("CompanyName",reultList.get(position).getCompanyName());
        hashMap.put("CompanyLogo",reultList.get(position).getCompanyLogo());
        hashMap.put("EmployerId", reultList.get(position).getEmployerId());
        hashMap.put("JobTitle", reultList.get(position).getJobTitle());
        hashMap.put("MinSalary", reultList.get(position).getMinSalary());
        hashMap.put("MaxSalary", reultList.get(position).getMaxSalary());
        hashMap.put("ContactInfo", reultList.get(position).getContactInfo());
        hashMap.put("Graduate", reultList.get(position).getGraduate());
        hashMap.put("Accomodation", reultList.get(position).getAccomodation());
        hashMap.put("Male", reultList.get(position).getMale());
        hashMap.put("Female", reultList.get(position).getFemale());
        hashMap.put("Unisex", reultList.get(position).getUnisex());
        hashMap.put("MinAge", reultList.get(position).getMinAge());
        hashMap.put("MaxAge", reultList.get(position).getMaxAge());
        hashMap.put("Single", reultList.get(position).getSingle());
        hashMap.put("FoodApply", reultList.get(position).getFoodApply());
        hashMap.put("FerryApply", reultList.get(position).getFerryApply());
        hashMap.put("LanguageSkill", reultList.get(position).getLanguageSkill());
        hashMap.put("Training", reultList.get(position).getTraining());
        hashMap.put("Requirement", reultList.get(position).getRequirement());
        hashMap.put("Discription", reultList.get(position).getDiscription());
        hashMap.put("Summary", reultList.get(position).getSummary());
        hashMap.put("City",reultList.get(position).getCity());
        hashMap.put("Township",reultList.get(position).getTownship());
    }
}