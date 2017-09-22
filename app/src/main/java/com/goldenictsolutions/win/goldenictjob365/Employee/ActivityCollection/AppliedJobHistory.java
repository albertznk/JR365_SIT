package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.AppliedJobHistroyAdapter;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AppliedJobHistory extends AppCompatActivity{
    RecyclerView appliedHistory;
    ProgressDialog pd;
    DB_Control db_control;
    ArrayList<DB_USERDATA> user_id;
    Toolbar tb;
    TextView apply_text;
    ArrayList<AppliedHistory> historyList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applied_job_history);
        apply_text= (TextView) findViewById(R.id.apply_text);
        tb= (Toolbar) findViewById(R.id.apply_toolbar_custom);
        setSupportActionBar(tb);
        db_control = new DB_Control(this);
        db_control.openDb();
        user_id = db_control.getUserid();
        appliedHistory = (RecyclerView) findViewById(R.id.applied_rc);
        new FetchAppliedHistory().execute(user_id.get(0).getUser_id());
    }


    //Download Applied History
    private class FetchAppliedHistory extends AsyncTask<String, Object, ArrayList<AppliedHistory>> {
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(AppliedJobHistory.this,ProgressDialog.STYLE_SPINNER);
            pd.setMessage(getResources().getText(R.string.loading));
            pd.setCancelable(false);
            pd.show();
        }

        protected void onPostExecute(ArrayList<AppliedHistory> appliedHistories) {
            if(appliedHistories.size()>0) {
                AppliedJobHistroyAdapter adapter = new AppliedJobHistroyAdapter(appliedHistories, AppliedJobHistory.this);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(AppliedJobHistory.this, LinearLayoutManager.VERTICAL, false);
                pd.setCancelable(false);
                pd.dismiss();
                apply_text.setVisibility(View.GONE);
                appliedHistory.setLayoutManager(lm);
                appliedHistory.setAdapter(adapter);
            }else if(appliedHistories.size()==0){
                pd.dismiss();
                apply_text.setText(getResources().getString(R.string.applied_text));
            }
            else {
                new FetchAppliedHistory().execute(user_id.get(0).getUser_id());
            }
        }

        protected ArrayList<AppliedHistory> doInBackground(String... params) {
            String userToken = params[0];
            HttpHandler handlerH = new HttpHandler();
            String url = "http://allreadymyanmar.com/api/getAllJobHistory/"+userToken;
            String checkResponse = handlerH.makeServiceCall(url);
            if (checkResponse != null){
                Log.e("Applied History", "doInBackground +1: "+checkResponse);
                try {
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    JSONArray jobList = jsonObject.getJSONArray("result");
                    for (int i =0;i<jobList.length();i++){
                        JSONObject c = jobList.getJSONObject(i);
                        historyList.add(new AppliedHistory(c.getString("id"),c.getString("applicant_id"),
                                c.getString("job_id"),c.getString("date_apply"),c.getString("cv_path")
                                ,c.getString("shortlisted"),c.getString("created_at"),c.getString("updated_at")
                                ,c.getString("job_title"),c.getString("job_nature_id"),c.getString("close_date")
                                ,c.getString("company_name"),c.getString("township")));

                    }
                    Log.e("Final Length", "doInBackground: Success :"+jobList.length() );
                } catch (JSONException e) {
                    Log.e("JSON Exception","JSON Error");
                    e.printStackTrace();
                }
            }
            return historyList;
        }
    }


    //Data Model for Applied History
    public class AppliedHistory{
        String id;
        String applicant_id;
        String job_id;
        String date_apply;
        String cv_path;
        String shortlisted;
        String created_at;
        String updated_at;
        String job_title;
        String job_nature_id;
        String close_date;
        String company_name;

        public String getCompany_address() {
            return company_address;
        }

        String company_address;

        public AppliedHistory(String id, String applicant_id, String job_id, String date_apply, String cv_path, String shortlisted, String created_at, String updated_at, String job_title, String job_nature_id, String close_date, String company_name,String company_address) {
            this.id = id;
            this.company_address=company_address;
            this.applicant_id = applicant_id;
            this.job_id = job_id;
            this.date_apply = date_apply;
            this.cv_path = cv_path;
            this.shortlisted = shortlisted;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.job_title = job_title;
            this.job_nature_id = job_nature_id;
            this.close_date = close_date;
            this.company_name = company_name;
        }

        public String getId_apply() {
            return id;
        }

        public String getApplicant_id() {
            return applicant_id;
        }

        public String getJob_id() {
            return job_id;
        }

        public String getDate_apply() {
            return date_apply;
        }

        public String getCv_path() {
            return cv_path;
        }

        public String getShortlisted() {
            return shortlisted;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getJob_title() {
            return job_title;
        }

        public String getJob_nature_id() {
            return job_nature_id;
        }

        public String getClose_date() {
            return close_date;
        }

        public String getCompany_name() {
            return company_name;
        }
    }
}
