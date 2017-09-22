package com.goldenictsolutions.win.goldenictjob365.Employee.FragPage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ContentHost;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.RecyclerDashAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.DataList;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by nuke on 6/19/17.
 */

public class DashBoard extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    JSONArray jobList;
    TextView errorText,remaining_time;
    RecyclerView recyclerView;
    RecyclerDashAdapter dashRecycleAdapter;
    ProgressBar progressBar;
    DB_Control db_control;
    String user_id;
    ArrayList<DataList> InfoList = new ArrayList<>();
    String jobsLink = "http://allreadymyanmar.com/api/relatedjob/";
    SwipeRefreshLayout swiper;
    public static String remain_time;
    Boolean retryOrNot = false;
    ArrayList<DB_USERDATA> db_user_data;

    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        db_user_data = db_control.getUserid();
        user_id=db_user_data.get(0).getUser_id();
        db_control.closeDb();
        onDestroy();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        swiper = (SwipeRefreshLayout) v.findViewById(R.id.swiper);
        errorText = (TextView) v.findViewById(R.id.error_TextView);
        remaining_time= (TextView) v.findViewById(R.id.employee_remaining);
//        dashRecycleAdapter.notifyDataSetChanged();
        swiper.setRefreshing(false);
            new DashBoradDataFetch().execute(jobsLink+user_id);


        return v;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swiper.setOnRefreshListener(this);

//        dashRecycleAdapter.notifyDataSetChanged();
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (retryOrNot==true){
                    recyclerView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    new DashBoradDataFetch().execute(jobsLink+user_id);
                }
                swiper.setRefreshing(false);
            }
        },2500);
    }


    //////////////////////////////Download related job/////////////////////////////////
    public class DashBoradDataFetch extends AsyncTask<String, String, ArrayList<DataList>> {

        protected void onPreExecute() {
            InfoList.clear();
        }

        protected ArrayList<DataList> doInBackground(String... params) {
            Log.i("AsynTask", "Starting "+params[0]);
            OkHttpClient httpClient = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json;charset = UTF-8");
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String jsonResponse = response.body().string();
                Log.i("Response", "doInBackground: " + jsonResponse);
                if (jsonResponse != null) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    jobList = jsonObject.getJSONArray("related_job");
                    for (int i = 0; i < jobList.length(); i++) {
                        JSONObject oneJob = jobList.getJSONObject(i);
                        InfoList.add(new DataList(oneJob.getString("company_logo"), oneJob.getString("job_title"),
                                oneJob.getString("company_name"), oneJob.getString("township"),
                                oneJob.getString("city"), oneJob.getString("id")));

                    }
                    return InfoList;

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataList> dataLists) {
            progressBar.setVisibility(View.GONE);
            if (dataLists != null) {
                if (dataLists.size() > 0) {
                    remaining_time.setText(ContentHost.remain_time);
                    errorText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    dashRecycleAdapter = new RecyclerDashAdapter(dataLists, getContext());
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(dashRecycleAdapter);
                    dashRecycleAdapter.notifyDataSetChanged();
//                    InfoList.clear();


                }else {
                    remaining_time.setText(ContentHost.remain_time);
                    errorText.setText(getResources().getString(R.string.no_job_found));
                    errorText.setVisibility(View.VISIBLE);
                    retryOrNot =true;
                }

            }
            else {
                retryOrNot = true;
                errorText.setVisibility(View.VISIBLE);
            }

        }
    }
}
