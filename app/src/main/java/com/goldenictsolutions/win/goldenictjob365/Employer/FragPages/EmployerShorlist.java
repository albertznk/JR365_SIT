package com.goldenictsolutions.win.goldenictjob365.Employer.FragPages;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.EmployerSpinnerAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.ListedAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.ShortlistAdapter;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.package_Type;
import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.remain_time;
import static com.goldenictsolutions.win.goldenictjob365.Employer.FragPages.EmployerDashboard.count;

/**
 * Created by nuke on 8/2/17.
 */

public class EmployerShorlist extends android.support.v4.app.Fragment implements View.OnClickListener {
    private String SHORT_LIST_COUNT_API = "http://allreadymyanmar.com/api/shortlisted_count/";
    private String All_COMPANY_API = "http://allreadymyanmar.com/api/all_company/";
    private Spinner ShortlistCompNmaeSpinner;
    private RecyclerView shortlistRecyclerView;
    private ProgressBar shortlistBar;
    private Button UpdatePackageBTN;
    private ArrayList<EmployerModel.ShortListedModel> shortListedArray = new ArrayList<>();
    private ArrayList<EmployerModel.ShortlistModel> shortJobArray = new ArrayList<>();
    private ArrayList<String> resultArray = new ArrayList<>();
    private EmployerSpinnerAdapter spinAdapt;
    FetchShorlistAllCompany FetchShorlistAllCompany;
    FetchShortListItems FetchShortListItems;
    DB_Control db_control;
    String userID;
    ArrayList<DB_USERDATA> userdata = new ArrayList<>();
    TextView remain_timee,package_Typee;
    TextView shortlist_job_count;
    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(context);
        db_control.openDb();
        userdata = db_control.getUserid();
        userID = userdata.get(0).getUser_id();
        db_control.closeDb();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.employer_frag_shortlist, null);
        shortlistRecyclerView = (RecyclerView) v.findViewById(R.id.shorlistRecyclerView);
        shortlistBar = (ProgressBar) v.findViewById(R.id.shorlistprogressBar);
        remain_timee= (TextView) v.findViewById(R.id.shortlist_remain_time);
        shortlist_job_count= (TextView) v.findViewById(R.id.shortlist_job_count);
        package_Typee= (TextView) v.findViewById(R.id.shortlist_package_type);
        ShortlistCompNmaeSpinner = (Spinner) v.findViewById(R.id.shorlistCompNmaeSpinner);
        FetchShorlistAllCompany = new FetchShorlistAllCompany();
        remain_timee.setText(remain_time);
        package_Typee.setText(package_Type);
        shortlist_job_count.setText(count);
        FetchShorlistAllCompany.execute(All_COMPANY_API + userID);
        UpdatePackageBTN = (Button) v.findViewById(R.id.packageUpgradeButton);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShortlistCompNmaeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Wait", "onItemSelected: " + shortListedArray.get(position).getId());
                FetchShortListItems = new FetchShortListItems();
                if (FetchShorlistAllCompany.getStatus() == AsyncTask.Status.FINISHED) {
                    FetchShortListItems.execute(shortListedArray.get(position).getId());
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        UpdatePackageBTN.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.packageUpgradeButton:
                break;
        }
    }

    private class FetchShorlistAllCompany extends AsyncTask<String, Object, ArrayList<EmployerModel.ShortListedModel>> {
        protected void onPreExecute() {
            shortListedArray.clear();
        }

        protected ArrayList<EmployerModel.ShortListedModel> doInBackground(String... params) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String result = response.body().string();
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray companyJsonList = jsonObject.getJSONArray("user_company");
                    for (int i = 0; i < companyJsonList.length(); i++) {
                        JSONObject listObj = companyJsonList.getJSONObject(i);
                        shortListedArray.add(new EmployerModel.ShortListedModel(listObj.getString("company_name"), listObj.getString("id")));
                    }
                    return shortListedArray;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.ShortListedModel> shortListedModels) {
            if (shortListedModels != null) {
                ListedAdapter adapter = new ListedAdapter(getContext(), shortListedModels);
                ShortlistCompNmaeSpinner.setAdapter(adapter);
            } else {

            }
        }
    }

    private class FetchShortListItems extends AsyncTask<String, Object, ArrayList<EmployerModel.ShortlistModel>> {
        private String Tag = "EmprDashLog";

        protected void onPreExecute() {
            shortlistBar.setVisibility(View.VISIBLE);
            shortlistRecyclerView.setVisibility(View.GONE);
            shortJobArray.clear();
        }

        protected ArrayList<EmployerModel.ShortlistModel> doInBackground(String... params) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(SHORT_LIST_COUNT_API + params[0])
                    .get()
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String resultRespone = response.body().string();
                if (resultRespone != null) {
                    JSONObject jsonObj = new JSONObject(resultRespone);
                    JSONArray jsonList = jsonObj.getJSONArray("candidate");
                    if (jsonList.length() > 0) {
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject jsonItem = jsonList.getJSONObject(i);
                            shortJobArray.add(new EmployerModel.ShortlistModel(jsonItem.getString("job_count"),
                                    jsonItem.getString("job_title"),
                                    jsonItem.getString("id"))
                            );
                        }
                        return shortJobArray;
                    }
                }
            } catch (IOException e) {
                Log.e(Tag, "doInBackground: response" + e);
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.ShortlistModel> openJobsModels) {
            if (openJobsModels != null) {
                shortlistRecyclerView.setVisibility(View.VISIBLE);
                ShortlistAdapter adapter = new ShortlistAdapter(getContext(), openJobsModels);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                shortlistRecyclerView.setLayoutManager(layoutManager);
                shortlistRecyclerView.setAdapter(adapter);
            } else {

            }
            shortlistBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (FetchShortListItems.getStatus() == AsyncTask.Status.RUNNING) {
                FetchShortListItems.cancel(true);

            }
        }catch (Exception e){
            Log.e("FetchShortListItems","");
        }
        try{
            if (FetchShorlistAllCompany.getStatus() == AsyncTask.Status.RUNNING) {
                FetchShorlistAllCompany.cancel(true);
            }
        }catch (Exception e){
            Log.e("FetchShortListCompany","");
        }



    }
}
