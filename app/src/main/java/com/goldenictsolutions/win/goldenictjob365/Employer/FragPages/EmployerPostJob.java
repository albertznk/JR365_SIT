package com.goldenictsolutions.win.goldenictjob365.Employer.FragPages;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataTransferMedium;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by nuke on 8/2/17.
 */

public class EmployerPostJob extends Fragment implements View.OnClickListener, View.OnTouchListener {
    public static Boolean notifier = false;
    public static TextView StartDate, EndDate;
    JSONObject outputJson;
    FragmentManager fm;
    DB_Control db_control;
    ArrayList<DB_USERDATA> userData = new ArrayList<>();
    String userID;
    FetchSpinDataCompName fetchSpinComp;
    FetchSpinCategory fetchSpinCate;
    FetchSpinTypeJob fetchSpinType;
    RelativeLayout employer_post_job;
    private MaterialSpinner businessNameSpinner, jobTypeSpinner, jobCategorySpinner,salaryRangeSpinner;
    private EditText malePostion, femalePosition, uniPosition, minAge, maxAge, workExp, Qualifi, Title, Requirement;
    private CheckBox mStatus,Accomodation, food, ferry;
    private Button confirm;
    private ImageButton positionRefresh;
    private String[] salaryArray = {"Negotiate", "Less than 100000 Ks", "100000 ~ 300000 Ks", "300000 ~ 500000 Ks", "500000 ~ 1000000 Ks", "Greater than 100000 Ks"};
    private ArrayList<EmployerModel.JobCategoryModel> categoryArray = new ArrayList<>();
    private String POST_JOB_API = "http://allreadymyanmar.com/api/job";
    private ArrayList<EmployerModel.JobTypeModel> jobTypeArray = new ArrayList<>();
    private ArrayList<EmployerModel.companyListModel> compNmaeArray = new ArrayList<>();
    private ScrollView postScroll;
    private ProgressBar progressBarPost;

    ArrayList<String> compNmaeArrayList = new ArrayList<>();
    ArrayList<String> jobTypeArrayList = new ArrayList<>();
    ArrayList<String> categoryArList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.employer_frag_postjob, container, false);
        idFinder(v);
        postScroll.setVisibility(View.INVISIBLE);
        progressBarPost.setVisibility(View.VISIBLE);
        salaryRangeSpinner.setAdapter(new ArrayAdapter(container.getContext(), android.R.layout.simple_spinner_item, salaryArray));
        fetchSpinComp = new FetchSpinDataCompName();
        fetchSpinComp.execute();
        return v;
    }

    private void idFinder(View v) {
        db_control = new DB_Control(getContext());
        db_control.openDb();
        userData = db_control.getUserid();
        userID = userData.get(0).getUser_id();
        db_control.closeDb();
        progressBarPost = (ProgressBar) v.findViewById(R.id.postProgressBar);
        postScroll = (ScrollView) v.findViewById(R.id.scroll_layout_post);
        employer_post_job = (RelativeLayout) v.findViewById(R.id.employer_post_job);
        //spinners
        businessNameSpinner = (MaterialSpinner) v.findViewById(R.id.bnpSpinner);
        jobTypeSpinner = (MaterialSpinner) v.findViewById(R.id.jobTypePost);
        jobCategorySpinner = (MaterialSpinner) v.findViewById(R.id.jobCategory);
        salaryRangeSpinner = (MaterialSpinner) v.findViewById(R.id.salarySpinner);
        //edit
        Title = (EditText) v.findViewById(R.id.titleEDT);
        Qualifi = (EditText) v.findViewById(R.id.qualificationEDT);
        workExp = (EditText) v.findViewById(R.id.workperienceEDT);
        Requirement = (EditText) v.findViewById(R.id.requiremnetEDT);
        malePostion = (EditText) v.findViewById(R.id.maleCountEDT);
        femalePosition = (EditText) v.findViewById(R.id.femaleCountEDT);
        uniPosition = (EditText) v.findViewById(R.id.maleFemaleCountEDT);
        //min and max age
        minAge = (EditText) v.findViewById(R.id.minAge);
        maxAge = (EditText) v.findViewById(R.id.maxAge);
        //start date and end_date
        StartDate = (TextView) v.findViewById(R.id.start_date);
        EndDate = (TextView) v.findViewById(R.id.end_date);
        //check_box
        mStatus = (CheckBox) v.findViewById(R.id.maritalStatus);
        Accomodation = (CheckBox) v.findViewById(R.id.accomodation);
        food = (CheckBox) v.findViewById(R.id.foodYes);
        ferry = (CheckBox) v.findViewById(R.id.ferryYes);

        positionRefresh = (ImageButton) v.findViewById(R.id.postitonRefresh);
        confirm = (Button) v.findViewById(R.id.confirmAndSave);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        malePostion.setOnTouchListener(this);
        femalePosition.setOnTouchListener(this);
        uniPosition.setOnTouchListener(this);
        StartDate.setOnClickListener(this);
        EndDate.setOnClickListener(this);
        positionRefresh.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmAndSave:
                if ((!malePostion.getText().toString().equals("") || !femalePosition.getText().toString().equals("") || !uniPosition.getText().toString().equals("")) &&
                        !minAge.getText().toString().equals("") &&
                        !maxAge.getText().toString().equals("") && !workExp.getText().toString().equals("") &&
                        !Qualifi.getText().toString().equals("") && !Title.getText().toString().equals("") &&
                        !Requirement.getText().toString().equals("")) {
                    if (Integer.parseInt(minAge.getText().toString())>Integer.parseInt(maxAge.getText().toString())){
                        Toast.makeText(getContext(), "min age is greater than max age ", Toast.LENGTH_SHORT).show();
                    }else{
                        outputJson = PutIntoJson();
                        if (outputJson != null) {
                            new PostJob().execute(outputJson);
                        } else {
                            Toast.makeText(getContext(), "JSON input error", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "REQUIRE FIELD ARE MISSING !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_date:
                FragmentActivity activity = (FragmentActivity) getContext();
                fm = activity.getSupportFragmentManager();
                DialogFragment newFragment = new Start_Date();
                newFragment.show(fm, "datePicker");
                break;
            case R.id.end_date:
                FragmentActivity activity2 = (FragmentActivity) getContext();
                fm = activity2.getSupportFragmentManager();
                DialogFragment newFragment2 = new Start_Date2();
                newFragment2.show(fm, "datePicker");
                break;
            case R.id.postitonRefresh:
                OriginalState();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
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

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++ POST JOB +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public JSONObject PutIntoJson() {
        JSONObject OutputJSON = new JSONObject();
        try {
            OutputJSON.put("employer_id", userID);
            OutputJSON.put("company_id", compNmaeArray.get(businessNameSpinner.getSelectedItemPosition()).getCompId());
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

            Log.i("DD", "Date Check: "+StartDate.getText().toString());
            Log.i("DD", "Date Check: "+ EndDate.getText().toString());

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

    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (fetchSpinComp.getStatus() == AsyncTask.Status.RUNNING) {
                fetchSpinComp.cancel(true);
            }
        } catch (Exception e) {

        }
        try {
            if (fetchSpinComp.getStatus() == AsyncTask.Status.FINISHED) {
                if (fetchSpinType.getStatus() == AsyncTask.Status.RUNNING) {
                    fetchSpinComp.cancel(true);
                }
            }
        } catch (Exception e) {

        }
        try {
            if (fetchSpinType.getStatus() == AsyncTask.Status.FINISHED) {
                if (fetchSpinCate.getStatus() == AsyncTask.Status.RUNNING) {
                    fetchSpinCate.cancel(true);
                }
            }

        } catch (Exception e) {

        }
    }

    public void SetDefaultPostJob() {
        StartDate.setHint(getResources().getString(R.string.edu_start_Date));
        EndDate.setHint(getResources().getString(R.string.edu_end_Date));
        StartDate.setText("");
        EndDate.setText("");
        businessNameSpinner.setSelection(0);
        jobTypeSpinner.setSelection(0);
        jobCategorySpinner.setSelection(0);
        salaryRangeSpinner.setSelection(0);
        malePostion.setHint("0");
        malePostion.setText("");
        femalePosition.setHint("0");
        femalePosition.setText("");
        uniPosition.setHint("00");
        uniPosition.setText("");
        minAge.setHint(getResources().getString(R.string.Min_age));
        maxAge.setHint(getResources().getString(R.string.Max_age));
        minAge.setText("");
        maxAge.setText("");
        workExp.setHint(getResources().getString(R.string.post_work_exp));
        Qualifi.setHint(getResources().getString(R.string.post_Quantity));
        workExp.setText(" ");
        Qualifi.setText(" ");
        Title.setHint(getResources().getString(R.string.title));
        Title.setText("");
        Requirement.setHint(getResources().getString(R.string.requirement_post_job_hint));
        Requirement.setText(" ");
        mStatus.setChecked(false);
        Accomodation.setChecked(false);
        food.setChecked(false);
        ferry.setChecked(false);
        OriginalState();
        notifier = true;

    }

    public static class Start_Date extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
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

    public static class Start_Date2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
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

    private class PostJob extends AsyncTask<JSONObject, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getContext());
            pd.setMessage(getContext().getResources().getString(R.string.Uploading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(JSONObject... params) {
            OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS).build();

            try {
                MediaType meidaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(meidaType, String.valueOf(params[0]));

                Request request = new Request.Builder()
                        .url(POST_JOB_API)
                        .post(body)
                        .build();
                Log.i("POST JOB", "doInBackground: ");
                Response response = httpClient.newCall(request).execute();

                Log.i("POST JOB", "doInBackground: " + response.message());
                String result = response.body().string();
                return result;

            } catch (Exception e) {
                Log.i("POST_JOB", "doInBackground: post data error  :" + e);
            }
            return null;
        }

        protected void onPostExecute(String s) {
            if (s != null) {
                try {
                    JSONObject jsonRetrunMessage = new JSONObject(s);
                    if (jsonRetrunMessage.getString("result").equals("Success") && jsonRetrunMessage.getBoolean("error") == false) {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        DataTransferMedium dataTrain = (DataTransferMedium) getActivity();
                        dataTrain.PostNewJobNotifier(true, businessNameSpinner.getSelectedItemPosition());
                        SetDefaultPostJob();
                    }else {
                        Toast.makeText(getActivity(), ""+jsonRetrunMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Upload Error", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }

// ============================================================================= Spinner Get Data ============================================================================
    public class FetchSpinDataCompName extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            Log.i("POST", "onPreExecute: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ comp List");
            compNmaeArray.clear();
            compNmaeArrayList.clear();

        }

        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            Log.i("POST", "doInBackground: 1");
            OkHttpClient client = new OkHttpClient();
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
                    Log.i("POST", "doInBackground: " + reslut);
                    JSONObject jsonName = new JSONObject(reslut);
                    JSONArray nameArray = jsonName.getJSONArray("user_company");
                    Log.i("POST", "doInBackground: json ARRAY Length " + nameArray.length());
                    for (int i = 0; i < nameArray.length(); i++) {
                        JSONObject finalName = nameArray.getJSONObject(i);
                        compNmaeArray.add(new EmployerModel.companyListModel(finalName.getString("company_name"), finalName.getString("id"), finalName.getString("type")));
                    }
                    Log.i("POST", "doInBackground: CompName Array " + compNmaeArray.size());
                    if (compNmaeArray.size() == nameArray.length()) {

                        for (int i = 0; i < compNmaeArray.size(); i++) {
                            compNmaeArrayList.add(i, compNmaeArray.get(i).getCompName());
                        }
                        Log.i("POST", "doInBackground: final LIst" + compNmaeArrayList.size());
                        return compNmaeArrayList;
                    }
                }
            } catch (IOException e) {
                Log.i("helo", "doInBackground: " + e);
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<String> strings) {
            if (strings != null) {
                if (strings.isEmpty() == false) {
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strings);
                    businessNameSpinner.setAdapter(arrayAdapter);
                    fetchSpinType = new FetchSpinTypeJob();
                    fetchSpinType.execute();
//                    new FetchSpinTypeJob().execute();
                } else {
                    Log.i("POST", "onPostExecute: REtry 1");
                }

            } else {
//                fetchSpinComp = new FetchSpinDataCompName();
//                fetchSpinComp.execute();
            }
        }
    }

    public class FetchSpinTypeJob extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            jobTypeArray.clear();
            jobTypeArrayList.clear();
        }

        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            Log.i("POST", "doInBackground: 2 =================================================");
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
                    Log.i("POST", "doInBackground: type model length" + jobTypeArray.size());
                    if (typeArray.length() == jobTypeArray.size()) {

                        for (int i = 0; i < jobTypeArray.size(); i++) {
                            jobTypeArrayList.add(i, jobTypeArray.get(i).getType());
                        }
                        Log.i("POST", "doInBackground: final type length" + jobTypeArrayList.size());
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
                Log.i("POST", "onPostExecute: Type Final not null ");
                if (jobTypeModels.size() > 0) {
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, jobTypeModels);
                    jobTypeSpinner.setAdapter(adapter);
                    fetchSpinCate = new FetchSpinCategory();
                    fetchSpinCate.execute();

                }
            } else {
                fetchSpinType = new FetchSpinTypeJob();
                fetchSpinType.execute();

            }
        }
    }

    public class FetchSpinCategory extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            categoryArray.clear();
            categoryArList.clear();
        }

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
                try {
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strings);
                    jobCategorySpinner.setAdapter(adapter);
                    postScroll.setVisibility(View.VISIBLE);
                    progressBarPost.setVisibility(View.GONE);
                } catch (Exception e) {

                }
            } else {
              /*  Snackbar snackbar = Snackbar.make(employer_post_job, "Retry?", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Yes", new View.OnClickListener() {
                    public void onClick(View v) {
                        fetchSpinCate = new FetchSpinCategory();
                        fetchSpinCate.execute();

                    }
                });
                snackbar.show();*/


            }
        }
    }

// ============================================================================= Spinner Get Data ============================================================================
}
