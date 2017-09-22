package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nuke on 8/8/17.
 */

public class JobOpenning extends AppCompatActivity implements View.OnClickListener {
    private TextView jobCategory;
    private TextView companyNmae;
    private TextView jobType;
    private TextView uniSex;
    private TextView Male;
    private TextView Female;
    private TextView SalaryRange;
    private TextView Singel;
    private TextView Food;
    private TextView Ferry;
    private TextView Accomodation;
    private TextView Trainning;
    private TextView Age;
    private TextView JobDescripiton;
    private TextView Requriement;
    private TextView Summary;
    private TextView openDate, closeDate;
    private Button backBTN, aboutComp, modifyBTN;
    private HashMap jobDataHM = new HashMap();
    private ImageView logo;
    private ProgressBar progressBar;
    TextView toolbar_text;
    String jobId;
    String remain_time, package_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_openning);

        findID();
        jobId = getIntent().getExtras().getString("job_id");
        remain_time = getIntent().getExtras().getString("remain_time");
        package_type = getIntent().getExtras().getString("package_type");
        new FetchOpenJob().execute(jobId);
        aboutComp.setOnClickListener(this);
    }

    private void findID() {
        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        toolbar_text.setText(getResources().getString(R.string.empr_Job_Opening));
        jobCategory = (TextView) findViewById(R.id.empJobCategory);
        companyNmae = (TextView) findViewById(R.id.empCompanyName);
        jobType = (TextView) findViewById(R.id.empJobType);
        uniSex = (TextView) findViewById(R.id.empUniSex);
        Male = (TextView) findViewById(R.id.empMalePost);
        Female = (TextView) findViewById(R.id.empFemalePost);
        SalaryRange = (TextView) findViewById(R.id.empSalaryRange);
        Singel = (TextView) findViewById(R.id.empSingle);
        Food = (TextView) findViewById(R.id.empFoodSupport);
        Ferry = (TextView) findViewById(R.id.empFerry);
        Accomodation = (TextView) findViewById(R.id.empAccomodation);
        Age = (TextView) findViewById(R.id.empAgeRange);
        JobDescripiton = (TextView) findViewById(R.id.empDescription);
        Requriement = (TextView) findViewById(R.id.empRequrement);
        Summary = (TextView) findViewById(R.id.empSummary);
        backBTN = (Button) findViewById(R.id.empBackBTN);
        aboutComp = (Button) findViewById(R.id.empAboutBTN);
        modifyBTN = (Button) findViewById(R.id.empModifyBTN);
        logo = (ImageView) findViewById(R.id.empOpenJobLogo);
        progressBar = (ProgressBar) findViewById(R.id.openJobProgressBar);

        openDate = (TextView) findViewById(R.id.open_set);
        closeDate = (TextView) findViewById(R.id.close_set);

        backBTN.setOnClickListener(this);
        modifyBTN.setOnClickListener(this);
        aboutComp.setOnClickListener(this);
    }

    private void setTextOpenJob(HashMap hashMap) {
        jobCategory.setText(hashMap.get("JobTitle").toString());
        companyNmae.setText(hashMap.get("CompanyName").toString());
        jobType.setText(hashMap.get("JobTitle").toString());

        SalaryRange.setText(hashMap.get("salary_type").toString());
        if (hashMap.get("Single").equals("1")) {
            Singel.setText("Yes");
        } else {
            Singel.setText("No");
        }
        Age.setText(hashMap.get("MinAge") + " - " + hashMap.get("MaxAge"));
        if (hashMap.get("Accomodation").equals("1")) {
            Accomodation.setText("Yes");
        } else {
            Accomodation.setText("No");
        }
        if (hashMap.get("FoodApply").equals("1")) {
            Food.setText("Yes");
        } else {
            Food.setText("No");
        }

        if (hashMap.get("FerryApply").equals("1")) {
            Ferry.setText("Yes");
        } else {
            Ferry.setText("No");
        }


        openDate.setText(hashMap.get("open_date").toString());
        closeDate.setText(hashMap.get("close_date").toString());
        if (Integer.parseInt(hashMap.get("unisex").toString()) != 0) {
            uniSex.setText(hashMap.get("unisex").toString());
            Male.setText("-");
            Female.setText("-");
        } else {
            if (Integer.parseInt(hashMap.get("Female").toString()) != 0) {
                Female.setText(hashMap.get("Female").toString());
            } else {
                Female.setText("-");
            }
            if (Integer.parseInt(hashMap.get("Male").toString()) != 0) {
                Male.setText(hashMap.get("Male").toString());
            } else {
                Male.setText(" ");
            }
            uniSex.setText("-");
        }
        if (hashMap.get("Requirement") != null) {
            Requriement.setText(Html.fromHtml(hashMap.get("Requirement").toString()));
        } else {
            Requriement.setText(" ");
        }
        if (hashMap.get("Discription") != null) {
            JobDescripiton.setText(Html.fromHtml(hashMap.get("Discription").toString()));
        } else {
            JobDescripiton.setText(" ");
        }
        if (hashMap.get("Summary") != null) {
            Summary.setText(Html.fromHtml(hashMap.get("Summary").toString()));
        } else {
            Summary.setText(" ");
        }
        ImageLoader imgloader = new ImageLoader(JobOpenning.this);
        imgloader.DisplayImage("http://allreadymyanmar.com/uploads/company_logo/" + hashMap.get("CompanyLogo"), logo);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empBackBTN:
                finish();
                break;
            case R.id.empAboutBTN:
                Intent inn = new Intent(JobOpenning.this, EmployerAboutCompany.class);
                inn.putExtra("company_id", jobDataHM.get("CompanyId").toString());
                inn.putExtra("remain_time", remain_time);
                inn.putExtra("package_type", package_type);
                if (inn != null) {
                    startActivity(inn);
                    finish();
                }
                break;
            case R.id.empModifyBTN:
                Intent intent = new Intent(JobOpenning.this, EmployerModifyJob.class);
                if (jobDataHM.size() != 0) {
                    intent.putExtra("data", jobDataHM);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(JobOpenning.this, "Modify", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class FetchOpenJob extends AsyncTask<Object, String, HashMap> {

        String appLink = "http://allreadymyanmar.com/api/job/";

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            jobDataHM.clear();
        }

        @Override
        protected HashMap doInBackground(Object... params) {
            OkHttpClient httpClient = new OkHttpClient();

            String job_id = (String) params[0];
            final Request request = new Request.Builder()
                    .url(appLink + job_id)
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String jobInfoResponse = response.body().string();
                JSONObject jsonObject = new JSONObject(jobInfoResponse);
                JSONArray jobList = jsonObject.getJSONArray("job_info");
                JSONObject jsonJobId = jobList.getJSONObject(0);
                jobDataHM = TransferData(jsonJobId);
                return jobDataHM;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap hashMap) {
            if (hashMap != null) {
                setTextOpenJob(jobDataHM);
                ScrollView scrollView = (ScrollView) findViewById(R.id.openJobScroll);
                scrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(JobOpenning.this);
                builder.setTitle("Connection Error");
                builder.setMessage("Downloading Data is not successed. Please Try Again Later");
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new FetchOpenJob().execute(jobId);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });
                builder.create().show();
            }
        }

        private HashMap TransferData(JSONObject reultList) {
            try {
                jobDataHM.put("Id", reultList.getString("id").toString());
                jobDataHM.put("CompanyId", reultList.getString("company_id").toString());
                jobDataHM.put("category", reultList.getString("category").toString());
                jobDataHM.put("CompanyName", reultList.getString("company_name").toString());
                jobDataHM.put("CompanyLogo", reultList.getString("company_logo").toString());
                jobDataHM.put("JobTitle", reultList.getString("job_title").toString());
                jobDataHM.put("MinSalary", reultList.getString("min_salary").toString());
                jobDataHM.put("MaxSalary", reultList.getString("max_salary").toString());

                jobDataHM.put("ContactInfo", reultList.getString("contact_info").toString());
                jobDataHM.put("Graduate", reultList.getString("graduate").toString());

                jobDataHM.put("Accomodation", reultList.getString("accomodation").toString());

                jobDataHM.put("Male", reultList.getString("male").toString());
                jobDataHM.put("Female", reultList.getString("female").toString());
                jobDataHM.put("Unisex", reultList.getString("unisex").toString());
                jobDataHM.put("MinAge", reultList.getString("min_age").toString());
                jobDataHM.put("MaxAge", reultList.getString("max_age").toString());

                jobDataHM.put("Single", reultList.getString("single").toString());
                jobDataHM.put("FoodApply", reultList.getString("food_supply").toString());
                jobDataHM.put("FerryApply", reultList.getString("ferry_supply").toString());
                jobDataHM.put("LanguageSkill", reultList.getString("language_skill").toString());


                jobDataHM.put("Training", reultList.getString("training").toString());
                jobDataHM.put("type", reultList.getString("type").toString());
                jobDataHM.put("Requirement", reultList.getString("requirement").toString());
                jobDataHM.put("Discription", reultList.getString("description").toString());
                jobDataHM.put("Summary", reultList.getString("summary").toString());
             /*   jobDataHM.put("Start_Date", reultList.getString("open_date").toString());
                jobDataHM.put("End_Date", reultList.getString("close_date").toString());*/

                jobDataHM.put("unisex", reultList.getString("unisex").toString());
                jobDataHM.put("Salary_index", reultList.getString("salary_range"));
                jobDataHM.put("salary_type", reultList.getString("salary_type").toString());
                jobDataHM.put("open_date", reultList.getString("open_date").toString());
                jobDataHM.put("close_date", reultList.getString("close_date").toString());
                Log.i("DD", "Date Check: " + jobDataHM.get("open_date"));
                Log.i("DD", "Date Check: " + jobDataHM.get("close_date"));
                return jobDataHM;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public void onBackPressed() {

    }
}