package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nuke on 8/10/17.
 */

public class EmployerModifyJob extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    public static TextView StartDate, EndDate;
    private String POST_JOB_API = "http://allreadymyanmar.com/api/job/";
    private String[] salaryArray = {"Negotiate", "less than 100000 ks", "100000 ~ 300000 Ks", "300000 ~ 500000 Ks", "500000 ~ 1000000 Ks", "Greater than 100000 Ks"};
    private MaterialSpinner CompNameSpinner, jobTypeSpinner, jobCategorySpinner,
            salaryRangeSpinner;
    private EditText malePostion, femalePosition, uniPosition, minAge, maxAge, workExp, Qualifi, Title, Requirement;
    private CheckBox mStatus, Accomodation, food, ferry;
    private Button confirm;
    JSONObject outputJson;
    String userID;
    private ImageButton positionRefresh;
    FragmentManager fm;
    private ScrollView postScroll;
    private ProgressBar progressBarPost;
    DB_Control db_control;
    HashMap hashMap;
    ArrayList<DB_USERDATA> userData = new ArrayList<>();

    //    private ArrayList<String> jobTypeSpinList, jobCategorySpinList, JobTypeSpinList;
    private ArrayList<EmployerModel.JobCategoryModel> categoryArray = new ArrayList<>();
    private ArrayList<EmployerModel.JobTypeModel> jobTypeArray = new ArrayList<>();
    private ArrayList<EmployerModel.companyListModel> compNmaeArray = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_frag_postjob);
        ViewFindById();
        fm = getSupportFragmentManager();
        confirm.setText("Update");
        hashMap = (HashMap) getIntent().getExtras().get("data");
        /*Log.e("Category", hashMap.get("category").toString());
        Log.e("Type", hashMap.get("type").toString());
        Log.e("IDDDDDD", hashMap.get("Id").toString())*/
        ;
        salaryRangeSpinner.setAdapter(new ArrayAdapter(EmployerModifyJob.this, android.R.layout.simple_spinner_dropdown_item, salaryArray));
        new FetchSpinDataCompNameModify().execute();
        confirm.setOnClickListener(this);
        positionRefresh.setOnClickListener(this);
        malePostion.setOnTouchListener(this);
        femalePosition.setOnTouchListener(this);
        uniPosition.setOnTouchListener(this);
    }

    private void ViewFindById() {
        db_control = new DB_Control(this);
        db_control.openDb();
        userData = db_control.getUserid();
        userID = userData.get(0).getUser_id();
        db_control.closeDb();
        progressBarPost = (ProgressBar) findViewById(R.id.postProgressBar);
        postScroll = (ScrollView) findViewById(R.id.scroll_layout_post);
        //spinners
        CompNameSpinner = (MaterialSpinner) findViewById(R.id.bnpSpinner);
        jobTypeSpinner = (MaterialSpinner) findViewById(R.id.jobTypePost);
        jobCategorySpinner = (MaterialSpinner) findViewById(R.id.jobCategory);
        salaryRangeSpinner = (MaterialSpinner) findViewById(R.id.salarySpinner);
        //edit
        Title = (EditText) findViewById(R.id.titleEDT);
        Qualifi = (EditText) findViewById(R.id.qualificationEDT);
        workExp = (EditText) findViewById(R.id.workperienceEDT);
        Requirement = (EditText) findViewById(R.id.requiremnetEDT);
        malePostion = (EditText) findViewById(R.id.maleCountEDT);
        femalePosition = (EditText) findViewById(R.id.femaleCountEDT);
        uniPosition = (EditText) findViewById(R.id.maleFemaleCountEDT);
        //min and max age
        minAge = (EditText) findViewById(R.id.minAge);
        maxAge = (EditText) findViewById(R.id.maxAge);
        //start date and end_date
        StartDate = (TextView) findViewById(R.id.start_date);
        EndDate = (TextView) findViewById(R.id.end_date);
        //check_box
        mStatus = (CheckBox) findViewById(R.id.maritalStatus);
        Accomodation = (CheckBox) findViewById(R.id.accomodation);
        food = (CheckBox) findViewById(R.id.foodYes);
        ferry = (CheckBox) findViewById(R.id.ferryYes);

        positionRefresh = (ImageButton) findViewById(R.id.postitonRefresh);
        confirm = (Button) findViewById(R.id.confirmAndSave);
        StartDate.setOnClickListener(this);
        EndDate.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
                android.support.v4.app.DialogFragment a = new Modify_Start_Date();
                //DialogFragment a=new Modify_Start_Date();
                a.show(fm, "datepicker");
                break;
            case R.id.end_date:
                android.support.v4.app.DialogFragment b = new Modify_EndDate();
                b.show(fm, "datepicker");
                break;

            case R.id.postitonRefresh:
                OriginalState();
                break;
            case R.id.confirmAndSave:
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployerModifyJob.this);
                builder.setTitle(getResources().getString(R.string.update_job_title));
                builder.setMessage(getResources().getString(R.string.update_job_message));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((!malePostion.getText().toString().equals("") || !femalePosition.getText().toString().equals("") || !uniPosition.getText().toString().equals("")) &&
                                !minAge.getText().toString().equals("") &&
                                !maxAge.getText().toString().equals("") && !workExp.getText().toString().equals("") &&
                                !Qualifi.getText().toString().equals("") && !Title.getText().toString().equals("") &&
                                !Requirement.getText().toString().equals("")) {
                            if (Integer.parseInt(minAge.getText().toString()) > Integer.parseInt(maxAge.getText().toString())) {
                                Toast.makeText(EmployerModifyJob.this, "min age is greater than max age", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject outputJson = PutIntoJson();
                                if (outputJson != null) {
                                    dialog.dismiss();
                                    new PostJob().execute(outputJson);
                                } else {
                                    Toast.makeText(EmployerModifyJob.this, "Json Input Error !", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else {
                            Toast.makeText(EmployerModifyJob.this, "REQUIRE FIELD ARE MISSING !", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();
                break;
        }
    }

    //start_date for modify
    public static class Modify_Start_Date extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user


            StartDate.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    //end_date for modify
    public static class Modify_EndDate extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user


            EndDate.setText(year + "-" + (month + 1) + "-" + day);

        }
    }


    ////////////////////////////////////////// MALE FEMALE UNISEX COUNT EDIT TEXT PLAY ///////////////////////////////////////////
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.maleCountEDT:
                maleOnTouch();
                break;
            case R.id.femaleCountEDT:
                femaleOnTouch();
                break;
            case R.id.maleFemaleCountEDT:
                uniState();
                break;
        }
        return false;
    }

    private void maleOnTouch() {
        malePostion.setVisibility(View.VISIBLE);
        femalePosition.setVisibility(View.VISIBLE);
        uniPosition.setVisibility(View.INVISIBLE);
        uniPosition.setText(null);
    }

    private void femaleOnTouch() {
        malePostion.setVisibility(View.VISIBLE);
        femalePosition.setVisibility(View.VISIBLE);
        uniPosition.setVisibility(View.INVISIBLE);
        uniPosition.setText(null);
    }

    private void uniState() {
        malePostion.setText(null);
        femalePosition.setText(null);
        malePostion.setVisibility(View.INVISIBLE);
        femalePosition.setVisibility(View.INVISIBLE);
        uniPosition.setVisibility(View.VISIBLE);
    }

    private void OriginalState() {
        uniPosition.setVisibility(View.VISIBLE);
        malePostion.setVisibility(View.VISIBLE);
        femalePosition.setVisibility(View.VISIBLE);
        malePostion.setText(null);
        femalePosition.setText(null);
        uniPosition.setText(null);
    }
    ////////////////////////////////////////////////////////////////////    THE END OF THE GENDER COUNT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    //// ///////////////////////////////////////////// SPINNER DATA FETCHING /////////////////////////////////////////////////////////////////////////
    ////  there are three  spinner in modify job activity
    public class FetchSpinDataCompNameModify extends AsyncTask<Object, Object, ArrayList<String>> {
        protected void onPreExecute() {
            postScroll.setVisibility(View.GONE);
        }

        protected ArrayList<String> doInBackground(Object... params) {
            OkHttpClient client = new OkHttpClient();

            client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS);

            Request requestNmaes = new Request.Builder()
                    .url("http://allreadymyanmar.com/api/all_company/" + userID)
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .get()
                    .build();
            try {
                Response respone = client.newCall(requestNmaes).execute();
                String reslut = respone.body().string();
                if (reslut != null) {
                    JSONObject jsonName = new JSONObject(reslut);
                    JSONArray nameArray = jsonName.getJSONArray("user_company");
                    for (int i = 0; i < nameArray.length(); i++) {
                        JSONObject finalName = nameArray.getJSONObject(i);
                        compNmaeArray.add(new EmployerModel.companyListModel(finalName.getString("company_name"), finalName.getString("id"), finalName.getString("type")));
                    }
                    if (compNmaeArray.size() == nameArray.length()) {
                        ArrayList<String> compNmaeArrayList = new ArrayList<>();
                        for (int i = 0; i < compNmaeArray.size(); i++) {
                            compNmaeArrayList.add(i, compNmaeArray.get(i).getCompName());
                        }
                        return compNmaeArrayList;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (strings != null) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(EmployerModifyJob.this, android.R.layout.simple_spinner_item, strings);
                CompNameSpinner.setAdapter(arrayAdapter);
                new FetchSpinTypeJobModify().execute();
            } else {
                new FetchSpinDataCompNameModify().execute();
            }

        }
    }

    public class FetchSpinTypeJobModify extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            Log.i("POST", "doInBackground: 2");
            OkHttpClient httpClient = new OkHttpClient();
            Request requestJobType = new Request.Builder()
                    .url("http://allreadymyanmar.com/api/type")
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(requestJobType).execute();
                String typeRespone = response.body().string();
                if (typeRespone != null) {
                    JSONObject jsonType = new JSONObject(typeRespone);
                    JSONArray typeArray = jsonType.getJSONArray("jobtype");
                    for (int i = 0; i < typeArray.length(); i++) {
                        JSONObject finalJobType = typeArray.getJSONObject(i);
                        jobTypeArray.add(new EmployerModel.JobTypeModel(finalJobType.getString("id"), finalJobType.getString("type")));
                    }
                    if (typeArray.length() == jobTypeArray.size()) {
                        ArrayList<String> jobTypeArrayList = new ArrayList<>();
                        for (int i = 0; i < jobTypeArray.size(); i++) {
                            jobTypeArrayList.add(i, jobTypeArray.get(i).getType());
                        }
                        return jobTypeArrayList;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> jobTypeModels) {
            if (jobTypeModels != null) {
                ArrayAdapter adapter = new ArrayAdapter(EmployerModifyJob.this, android.R.layout.simple_spinner_item, jobTypeModels);
                jobTypeSpinner.setAdapter(adapter);
                new FetchSpinCategoryModify().execute();
            } else {
                Log.i("POST", "onPostExecute: retrying 2");
                new FetchSpinTypeJobModify().execute();
            }
        }
    }

    public class FetchSpinCategoryModify extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            Log.i("POST", "doInBackground: 3");
            OkHttpClient client = new OkHttpClient();
            Request requestCategory = new Request.Builder()
                    .url("http://allreadymyanmar.com/api/category")
                    .get()
                    .build();
            try {
                Response response = client.newCall(requestCategory).execute();
                String responseCategory = response.body().string();
                if (responseCategory != null) {
                    JSONObject jsonCategory = new JSONObject(responseCategory);
                    JSONArray categoryAray = jsonCategory.getJSONArray("jobcategory");
                    for (int i = 0; i < categoryAray.length(); i++) {
                        JSONObject finalCategroyArray = categoryAray.getJSONObject(i);
                        categoryArray.add(new EmployerModel.JobCategoryModel(finalCategroyArray.getString("id"), finalCategroyArray.getString("category")));
                    }
                    if (categoryAray.length() == categoryArray.size()) {
                        ArrayList<String> categoryArList = new ArrayList<>();
                        for (int i = 0; i < categoryArray.size(); i++) {
                            categoryArList.add(i, categoryArray.get(i).getCategory());
                        }

                        return categoryArList;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            if (strings != null) {
                ArrayAdapter adapter = new ArrayAdapter(EmployerModifyJob.this, android.R.layout.simple_spinner_item, strings);
                postScroll.setVisibility(View.VISIBLE);
                jobCategorySpinner.setAdapter(adapter);
                progressBarPost.setVisibility(View.GONE);
                Log.i("POST", "onPostExecute: Done");
                SetTextJobModify(hashMap);
            } else {
                Log.i("POST", "onPostExecute: REtying final");
                new FetchSpinCategoryModify().execute();
            }
        }
    }

    private void SetTextJobModify(HashMap hashMap) {

        for (int i = 0; i < compNmaeArray.size(); i++) {
            if (hashMap.get("CompanyName").toString().equals(compNmaeArray.get(i).getCompName())) {
                Log.i("LG", "SetTextJobModify: +++=====+++" + compNmaeArray.get(i).getCompName());
                CompNameSpinner.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < categoryArray.size(); i++) {
            if (hashMap.get("category").toString().equals(categoryArray.get(i).getCategory())) {
                jobCategorySpinner.setSelection(i);
                Log.i("LG", "SetTextJobModify: ++====++" + categoryArray.get(i).getCategory());
                break;
            }
        }
        for (int i = 0; i < jobTypeArray.size(); i++) {
            if (hashMap.get("type").toString().equals(jobTypeArray.get(i).getType())) {
                jobTypeSpinner.setSelection(i);
                Log.i("LG", "SetTextJobModify: ++====++" + jobTypeArray.get(i).getType());
                break;
            }
        }
        Title.setText(hashMap.get("JobTitle").toString());
        if (hashMap.get("Male").toString() != "0" && hashMap.get("Male").toString() != null && hashMap.get("Female").toString() != "0" && hashMap.get("Female").toString() != null) {
            if (hashMap.get("Male").toString() != "0") {
                malePostion.setText(hashMap.get("Male").toString());
            } else {
                malePostion.setText(null);
                malePostion.setHint("0");
            }
            if (hashMap.get("Female").toString() != "0") {
                femalePosition.setText(hashMap.get("Female").toString());
            } else {
                femalePosition.setText(null);
                femalePosition.setHint("0");
            }
            if (hashMap.get("Unisex").toString() != "0") {
                uniPosition.setText(hashMap.get("unisex").toString());
            } else {
                uniPosition.setHint("00");
            }
        }
        if (hashMap.get("MinSalary").toString() != null && hashMap.get("MaxSalary").toString() != null) {
            salaryRangeSpinner.setSelection(Integer.parseInt(hashMap.get("Salary_index").toString()));
        }
        if (hashMap.get("Single").toString().equals("1")) {
            mStatus.setChecked(true);
        } else {
            mStatus.setChecked(false);
        }
        minAge.setText(hashMap.get("MinAge").toString());
        maxAge.setText(hashMap.get("MaxAge").toString());
        if (hashMap.get("Accomodation") != null || !hashMap.get("Accomodation").toString().equals("")) {
            if (hashMap.get("Accomodation").toString().equals("1")) {
                Accomodation.setChecked(true);
            } else {
                Accomodation.setChecked(false);
            }
        }
        if (hashMap.get("FoodApply") != null) {
            if (hashMap.get("FoodApply").toString().equals("1")) {
                food.setChecked(true);
            } else {
                food.setChecked(false);
            }
        }
        if (hashMap.get("FerryApply") != null) {
            if (hashMap.get("FerryApply").toString().equals("1")) {
                ferry.setChecked(true);
                Log.i("COMBO", "SetTextJobModify: !=1");
            } else {
                ferry.setChecked(false);
                Log.i("COMBO", "SetTextJobModify: !=1");
            }
        } else {
            ferry.setChecked(false);
            Log.i("COMBO", "SetTextJobModify: !=1");
        }
        if (hashMap.get("Requirement") != null) {
            Requirement.setText(Html.fromHtml(hashMap.get("Requirement").toString()));
        } else {
            Requirement.setText(" ");
        }
        if (hashMap.get("Summary") != null) {
            Qualifi.setText(Html.fromHtml(hashMap.get("Summary").toString()));
        } else {
            Qualifi.setText(" ");
        }
        if (hashMap.get("Discription") != null) {
            workExp.setText(Html.fromHtml(hashMap.get("Discription").toString()));
        } else {
            workExp.setText(" ");
        }
        StartDate.setText(hashMap.get("open_date").toString());
        EndDate.setText(hashMap.get("close_date").toString());
    }
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ END OF SPINNER DATA FETCHING ///////////////////////////////////////////////////////////////////////


    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++ POST JOB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public JSONObject PutIntoJson() {
        JSONObject OutputJSON = new JSONObject();
        try {
            OutputJSON.put("employer_id", userID);
            //OutputJSON.put("employer_id", "958c2a20e23c48d38e8c5742c2f23dfe");
            OutputJSON.put("company_id", compNmaeArray.get(CompNameSpinner.getSelectedItemPosition()).getCompId());
            OutputJSON.put("job_nature", jobTypeArray.get(jobTypeSpinner.getSelectedItemPosition()).getId());
            OutputJSON.put("job_category", categoryArray.get(jobCategorySpinner.getSelectedItemPosition()).getId());
            OutputJSON.put("salary_range", salaryRangeSpinner.getSelectedItemPosition());
            OutputJSON.put("summary", Qualifi.getText().toString());
            OutputJSON.put("job_title", Title.getText().toString());
            OutputJSON.put("requirement", Requirement.getText().toString());
            if (mStatus.isChecked()) {
                OutputJSON.put("single", 1);
            } else {
                OutputJSON.put("single", 0);
            }
            if (Accomodation.isChecked()) {
                OutputJSON.put("accomodation", 1);
            } else {
                OutputJSON.put("accomodation", 0);
            }
            if (food.isChecked()) {
                OutputJSON.put("food", 1);
            } else {
                OutputJSON.put("food", 0);
            }
            if (ferry.isChecked()) {
                OutputJSON.put("transportation", 1);
            } else {
                OutputJSON.put("transportation", 0);
            }
            OutputJSON.put("open_date", StartDate.getText().toString());
            OutputJSON.put("close_date", EndDate.getText().toString());
            OutputJSON.put("description", workExp.getText().toString());
            OutputJSON.put("male", malePostion.getText().toString());
            OutputJSON.put("female", femalePosition.getText().toString());
            OutputJSON.put("unisex", uniPosition.getText().toString());
            int minn = Integer.parseInt(minAge.getText().toString());
            int maxx = Integer.parseInt(maxAge.getText().toString());
            Log.i("AGE", "PutIntoJson: " + minn + maxx);
            if (minn <= maxx) {
                OutputJSON.put("min_age", minAge.getText().toString());
                OutputJSON.put("max_age", maxAge.getText().toString());
            }
            return OutputJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class PostJob extends AsyncTask<JSONObject, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(EmployerModifyJob.this);
            pd.setMessage(getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(JSONObject... params) {
            OkHttpClient httpClient = new OkHttpClient();
            try {
                MediaType meidaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(meidaType, String.valueOf(params[0]));

                Request request = new Request.Builder()
                        .url(POST_JOB_API + hashMap.get("Id").toString())
                        .put(body)
                        .build();
                Response response = httpClient.newCall(request).execute();
                String result = response.body().string();
                return result;

            } catch (Exception e) {
                Log.i("POST_JOB", "doInBackground: post data error  :" + e);
            }
            return null;
        }

        protected void onPostExecute(String s) {
            if (s != null) {
                Log.e("JSON Return", "return" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").equals("Success")) {
                        Toast.makeText(EmployerModifyJob.this, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EmployerModifyJob.this, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(EmployerModifyJob.this, "Connection is bad", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++ POST JOB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EmployerModifyJob.this);
        alertBuilder.setTitle("Exit");
        alertBuilder.setMessage("Do you want to exit without saving !!");
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(EmployerModifyJob.this, JobOpenning.class);
                i.putExtra("job_id", hashMap.get("Id").toString());
                startActivity(i);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .create()
                .show();
    }
}
