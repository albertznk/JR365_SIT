package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;

import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.goldenictsolutions.win.goldenictjob365.commonfile.AddNewpswd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ShowDetailJob extends AppCompatActivity implements View.OnClickListener {
    TextView setJobCategory, setCompanyName, setJobType, setGenderPosition, setSalaryRange,
            setMaritalStatus, setAgeRange, setAccommodation, setFoodSupply, setFerrySupply,
            setQualification, setWorkExperience, setSummary, setMalePost, setFemalePost, setLanguageSkill,
            setJobLocation, setGraduate, setTraining;
    Button AboutCompanyBTN, ApplyJobBTN, BacktoHomeBTN;
    RelativeLayout showLayout;
    ProgressBar prgressBar;
    ImageView logo;
    DB_Control db_control;
    String userId;
    ArrayList<DB_USERDATA> db_user;
    ImageLoader imgloader;
    Boolean SearchOrDash = false;
    HashMap hashMap = new HashMap();
    HashMap jobDataHM = new HashMap();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_job_deatil);
        showLayout = (RelativeLayout) findViewById(R.id.showLayout);
        showLayout.setVisibility(View.GONE);
        findID();
        if (getIntent().getExtras().getInt("SearchKey")==1){
            SearchOrDash = true;
            hashMap = (HashMap) getIntent().getExtras().get("hsh");
            prgressBar.setVisibility(View.GONE);
            showLayout.setVisibility(View.VISIBLE);
            setTextForSearch(hashMap);
        }else if (getIntent().getExtras().getInt("app_key")==2){
                String job_id = getIntent().getExtras().getString("job_id");
                ApplyJobBTN.setEnabled(false);
                ApplyJobBTN.setHighlightColor(999);
                new FetchDataForJobDetail().execute(job_id,0);
        } else {
            int i = getIntent().getExtras().getInt("position");
            new FetchDataForJobDetail().execute(i,2);
        }

    }
    private void setTextForSearch(HashMap hashMap){
        Log.i("Hash", "setTextForSearch: "+hashMap.toString());
        setJobCategory.setText(hashMap.get("JobTitle").toString());
        setCompanyName.setText(hashMap.get("CompanyName").toString());
        setJobType.setText(hashMap.get("category").toString());
        if (hashMap.get("MinSalary").toString()!=null&&hashMap.get("MaxSalary").toString()!=null){
            setSalaryRange.setText(hashMap.get("MinSalary").toString()+ " - " +hashMap.get("MaxSalary").toString());
        }else {
            setSalaryRange.setText("-");
        }
        if (hashMap.get("Single").toString().equals("1")){
            setMaritalStatus.setText("Yes");
        }else {
            setMaritalStatus.setText("No");
        }
        if (!hashMap.get("Unisex").toString().equals("0")){
            setGenderPosition.setText(hashMap.get("Unisex").toString());
        }else {
            setGenderPosition.setText("-");
        }
        setAgeRange.setText(hashMap.get("MinAge")+ " - " +hashMap.get("MaxAge"));
        if (hashMap.get("Accomodation").toString().equals("1")){
            setAccommodation.setText("Yes");
        }else {
            setAccommodation.setText("No");
        }
        if (hashMap.get("FoodApply").toString().equals("1")){
            setFoodSupply.setText("Yes");
        }else {
            setFoodSupply.setText("No");
        }
        if (hashMap.get("FerryApply").toString().equals("1")){
            setFerrySupply.setText("Yes");
        }else {
            setFerrySupply.setText("No");
        }
//
//        if (hashMap.get("Training").toString().equals("1")){
//            setTraining.setText("Yes");
//        }else {
//            setTraining.setText("No");
//        }
        if (hashMap.get("Graduate").toString().equals("1")){
            setGraduate.setText("Yes");
        }else {
            setGraduate.setText("No");
        }
        setJobLocation.setText(hashMap.get("ContactInfo") + " - " + hashMap.get("Township")+ " - " + hashMap.get("City"));
        if (!hashMap.get("Female").toString().equals("0")){
            setFemalePost.setText(hashMap.get("Female").toString());
        }else {
            setFemalePost.setText("-");
        }
        setMalePost.setText(hashMap.get("Male").toString());
        if (!hashMap.get("Male").toString().equals("0")){
        }else {
            setFemalePost.setText("-");
        }
        if (!hashMap.get("LanguageSkill").equals("null")){
            setLanguageSkill.setText(hashMap.get("LanguageSkill").toString());
        }else {
            setLanguageSkill.setText("-");
        }
        if (hashMap.get("Requirement")!=null){
            setQualification.setText(Html.fromHtml(hashMap.get("Requirement").toString()));
        }else {
            setQualification.setText(" ");
        }
        if (hashMap.get("Discription")!=null){
            setWorkExperience.setText(Html.fromHtml(hashMap.get("Discription").toString()));
        }else {
            setWorkExperience.setText(" ");
        }
        if (hashMap.get("Summary")!=null){
            setSummary.setText(Html.fromHtml(hashMap.get("Summary").toString()));
        }else {
            setSummary.setText("-");
        }
        imgloader.DisplayImage("http://allreadymyanmar.com/uploads/company_logo/"+hashMap.get("CompanyLogo"),logo);
    }
    private void findID() {
        db_control = new DB_Control(getApplicationContext());
        db_control.openDb();
        db_user = db_control.getUserid();
        userId = db_user.get(0).getUser_id();
        db_control.closeDb();


        imgloader=new ImageLoader(getApplicationContext());
        logo= (ImageView) findViewById(R.id.empOpenJobLogo);
        setJobCategory = (TextView) findViewById(R.id.empJobCategory);
        setCompanyName = (TextView) findViewById(R.id.empCompanyName);
        setJobType = (TextView) findViewById(R.id.setJobType);
        setGenderPosition = (TextView) findViewById(R.id.setGenderPosition);
        setSalaryRange = (TextView) findViewById(R.id.setSalaryRange);
        setMaritalStatus = (TextView) findViewById(R.id.setMaritalStatus);
        setAgeRange = (TextView) findViewById(R.id.setAgeRange);
        setAccommodation = (TextView) findViewById(R.id.setAccomodation);
        setFoodSupply = (TextView) findViewById(R.id.setFoodSupport);
        setFerrySupply = (TextView) findViewById(R.id.setFerry);
        setQualification = (TextView) findViewById(R.id.show_Discription);
        setWorkExperience = (TextView) findViewById(R.id.show_job_requirement);
        AboutCompanyBTN = (Button) findViewById(R.id.aboutButton);
        ApplyJobBTN = (Button) findViewById(R.id.ApplyButton);
        BacktoHomeBTN = (Button) findViewById(R.id.backButton);

        setMalePost = (TextView) findViewById(R.id.setMalePost);
        setFemalePost = (TextView) findViewById(R.id.setFemalePost);
        setLanguageSkill = (TextView) findViewById(R.id.setLanguageSkill);
        setJobLocation = (TextView) findViewById(R.id.setJobLocation);
        setGraduate = (TextView) findViewById(R.id.setGraduate);
      //  setTraining = (TextView) findViewById(R.id.setTraining);
        setSummary = (TextView) findViewById(R.id.setSummary);
        prgressBar = (ProgressBar) findViewById(R.id.progressBarJobDeatil);

        AboutCompanyBTN.setOnClickListener(this);
        ApplyJobBTN.setOnClickListener(this);
        BacktoHomeBTN.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutButton:
                Toast.makeText(ShowDetailJob.this, " About Company ", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(ShowDetailJob.this, AboutCompany.class);
                if(SearchOrDash == true){
                    in.putExtra("company_id",hashMap.get("CompanyId").toString());
                    in.putExtra("Company_logo",hashMap.get("CompanyLogo").toString());
                    in.putExtra("showkey",1);
                }else {
                    in.putExtra("company_id",jobDataHM.get("CompanyId").toString());
                    in.putExtra("Company_logo",jobDataHM.get("CompanyLogo").toString());
                }
                startActivity(in);
                break;
            case R.id.backButton:
                Toast.makeText(ShowDetailJob.this, " Going Home ", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.ApplyButton:
                ShowConfirmDialog();
                break;
        }
    }

    private void ShowConfirmDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Activity activity = ShowDetailJob.this;
        builder.setCancelable(false);
        if (SearchOrDash == true){
            builder.setMessage(getResources().getString(R.string.send_cv));
            builder.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("JID", "onClick: "+hashMap.get("Id").toString()+" "+userId);
                //ExitAppDialog(activity);

                                 //TODO test changed thier class to znk -----
                    new ApplyJob().execute(userId,hashMap.get("Id").toString());

                }
            }).setNegativeButton(getResources().getString(R.string.not_send), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }else {
            builder.setMessage(getResources().getString(R.string.send_cv));
            builder.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    new ApplyJob().execute(userId,jobDataHM.get("Id").toString());

                }
            }).setNegativeButton(getResources().getString(R.string.not_send), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.create().show();
    }

    public static void ExitAppDialog(final Activity activity)
    {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
        alertbox.setTitle("Please Notice");
        alertbox.setMessage("If you want to apply you must be pay service feeds 300mmk pls input your name ");
        alertbox.setPositiveButton("Yes", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent tofourdig = new Intent(activity,AddNewpswd.class);
                            activity.startActivity(tofourdig);

                    }
                });
        alertbox.setNegativeButton("No", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertbox.show();
    }



    public void onBackPressed() {
    }


    //////////////////////////////Download Data for job detail//////////////////////
    private class FetchDataForJobDetail extends AsyncTask<Object,String,HashMap>{
        String jobsLink = "http://allreadymyanmar.com/api/relatedjob/";
        String appLink ="http://allreadymyanmar.com/api/job/";
        @Override
        protected HashMap doInBackground(Object... params) {
            OkHttpClient httpClient = new OkHttpClient();
            if ((Integer)params[1]==2){
                Request request = new Request.Builder()
                        .url(jobsLink+userId)
                        .get()
                        .build();
                try {
                    Response response = httpClient.newCall(request).execute();
                    String jobInfoResponse = response.body().string();
                    JSONObject jsonObject = new JSONObject(jobInfoResponse);
                    JSONArray jobList = jsonObject.getJSONArray("related_job");
                    JSONObject jsonJobId = jobList.getJSONObject((Integer) params[0]);
                    Log.i("Loop Out", "onResponse 55555: "+params[0]+"    return Josn  ::"+jsonJobId);
                    jobDataHM = TransferData(jsonJobId);
                    Log.i("Loop Out", "onResponse: "+params[0]+"    return length  ::"+jobDataHM.size());
                    return jobDataHM;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                String job_id = (String) params[0];
                final Request request = new Request.Builder()
                        .url(appLink+job_id)
                        .get()
                        .build();
                try {
                    Response response = httpClient.newCall(request).execute();
                    String jobInfoResponse = response.body().string();
                    JSONObject jsonObject = new JSONObject(jobInfoResponse);
                    JSONArray jobList = jsonObject.getJSONArray("job_info");
                    JSONObject jsonJobId = jobList.getJSONObject(0);
                    jobDataHM = TransferData(jsonJobId);
                    Log.i("Loop Out", "onResponse: "+params[0]+"    return Josn  ::"+jsonJobId);
                    Log.i("Loop Out", "onResponse: "+params[0]+"    return length  ::"+jobDataHM.size());
                    return jobDataHM;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap hashMap) {
            if (hashMap != null){
                setTextForSearch(jobDataHM);
                prgressBar.setVisibility(View.GONE);
                showLayout.setVisibility(View.VISIBLE);
            }else {
                android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(ShowDetailJob.this);
                builder.setTitle("Connection Error");
                builder.setMessage("Downloading Data is not successed. Please Try Again Later");
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        }

        private HashMap TransferData(JSONObject reultList) {
            try {
                jobDataHM.put("Id", reultList.          getString("id").toString());
                jobDataHM.put("CompanyId",reultList.    getString("company_id").toString());
                jobDataHM.put("category",reultList.getString("category").toString());
                jobDataHM.put("CompanyName",reultList.  getString("company_name").toString());
                jobDataHM.put("CompanyLogo",reultList.  getString("company_logo").toString());
                jobDataHM.put("JobTitle", reultList.    getString("job_title").toString());
                jobDataHM.put("MinSalary", reultList.   getString("min_salary").toString());
                jobDataHM.put("MaxSalary", reultList.   getString("max_salary").toString());
                jobDataHM.put("ContactInfo", reultList. getString("contact_info").toString());
                jobDataHM.put("Graduate", reultList.    getString("graduate").toString());
                jobDataHM.put("Accomodation", reultList.getString("accomodation").toString());
                jobDataHM.put("Male", reultList.getString("male").toString());
                Log.i("hhh", "TransferData: "+reultList.getString("male"));
                jobDataHM.put("Female", reultList.      getString("female").toString());
                jobDataHM.put("Unisex", reultList.      getString("unisex").toString());
                jobDataHM.put("MinAge", reultList.      getString("min_age").toString());
                jobDataHM.put("MaxAge", reultList.      getString("max_age").toString());
                jobDataHM.put("Single", reultList.      getString("single").toString());
                jobDataHM.put("FoodApply", reultList.   getString("food_supply").toString());
                jobDataHM.put("FerryApply", reultList.  getString("ferry_supply").toString());
                jobDataHM.put("LanguageSkill",reultList.getString("language_skill").toString());
                jobDataHM.put("Training", reultList.    getString("training").toString());
                jobDataHM.put("Requirement", reultList. getString("requirement").toString());
                jobDataHM.put("Discription", reultList. getString("description").toString());
                jobDataHM.put("Summary", reultList.     getString("summary").toString());
                jobDataHM.put("City",reultList.getString("city").toString());
                jobDataHM.put("Township",reultList.getString("township").toString());
                return jobDataHM;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //////////////////////////////////Apply job //////////////////////////////
    private class ApplyJob extends AsyncTask<String, String, String> {
        private OkHttpClient client = new OkHttpClient();
        private String ApplyLink = "http://allreadymyanmar.com/api/applyjob";
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            Toast.makeText(ShowDetailJob.this, "Connecting ...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", params[0]);
                jsonObject.put("hid", params[1]);

                client.newBuilder().connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS);
                client.newBuilder().readTimeout(30, java.util.concurrent.TimeUnit.SECONDS);
                client.newBuilder().writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS);
                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonObject));
                Request request = new Request.Builder()
                        .url(ApplyLink)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Transfer-Encoding", "chunked")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (JSONException e) {
                Log.e("JSON PUT ERROR", "doInBackground: " + e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("JSON POST ERROR", "doInBackground: " + e);
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String s) {
            if (s!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Toast.makeText(ShowDetailJob.this,jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ShowDetailJob.this, " Connection Lost...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
