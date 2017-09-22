package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.CandidateListAdapter;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nuke on 8/4/17.
 */

public class CandidateView extends AppCompatActivity implements View.OnClickListener {
    ArrayList<EmployerModel.CandidateListModel> candiDatalist = new ArrayList<>();
    private Button backBTN;
    private ImageView complogo;
    private TextView compname;
    private TextView jobtitle;
    private ProgressBar candidateProBar;
    private RecyclerView candidateRecycleList;
    private CardView candidateCard;
    private String CANDI_LIST_API = "http://allreadymyanmar.com/api/candidate_list/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_candidate_view);
        candidateRecycleList = (RecyclerView) findViewById(R.id.candidateRecycleList);
        jobtitle = (TextView) findViewById(R.id.candidateListJobType);
        compname = (TextView) findViewById(R.id.candidateListCompName);
        complogo = (ImageView) findViewById(R.id.img1);
        backBTN = (Button) findViewById(R.id.backBTN);
        candidateProBar = (ProgressBar) findViewById(R.id.candidateViewProBar1);
        candidateCard = (CardView) findViewById(R.id.candidateCard);
        backBTN.setOnClickListener(this);
        if (getIntent().getExtras().getString("shortlist_id") != null) {
            new FetchCandiList().execute(getIntent().getExtras().getString("shortlist_id"));
        } else {
            new FetchCandiList().execute(CANDI_LIST_API + getIntent().getExtras().getString("job_id"));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBTN:
                finish();
                break;
        }
    }

    private class FetchCandiList extends AsyncTask<String, Void, ArrayList<EmployerModel.CandidateListModel>> {
        String jobType, CompName, Complogo, primary_mobile_no,CompanyMobile;

        protected void onPreExecute() {
            candidateProBar.setVisibility(View.VISIBLE);
            candidateRecycleList.setVisibility(View.GONE);
            candidateCard.setVisibility(View.INVISIBLE);

        }

        protected ArrayList<EmployerModel.CandidateListModel> doInBackground(String... params) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                Log.i("Check", "doInBackground: " + response.toString());
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("candidate_list");
                    Log.i("Check", "doInBackground: " + jsonArray.toString());
                    JSONObject json = jsonArray.getJSONObject(0);
                    Log.i("Check", "doInBackground: " + json);
                    jobType = json.getString("job_title").toString();
                    CompName = json.getString("company_name").toString();
                    Complogo = json.getString("company_logo").toString();

                    primary_mobile_no=json.getString("primary_mobile").toString();
                    CompanyMobile = json.getString("mobile_no").toString();
                    JSONArray jsonCandi = json.getJSONArray("candidate");
                    for (int i = 0; i < jsonCandi.length(); i++) {
                        JSONObject jsonL = jsonCandi.getJSONObject(i);
                        candiDatalist.add(new EmployerModel.CandidateListModel(
                                jsonL.getString("id"),
                                jsonL.getString("user_id"),
                                jsonL.getString("name"),
                                jsonL.getString("email"),
                                jsonL.getString("mobile_no"),
                                jsonL.getString("photo")
                        ));
                    }
                    return candiDatalist;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.CandidateListModel> candidateListModels) {
            if (candidateListModels != null) {
                compname.setText(CompName);
                jobtitle.setText(jobType);
                candidateRecycleList.setVisibility(View.VISIBLE);
                candidateProBar.setVisibility(View.GONE);
                candidateCard.setVisibility(View.VISIBLE);
                CandidateListAdapter cadiAdapter = new CandidateListAdapter(CandidateView.this, candidateListModels, getIntent().getExtras().getString("job_id"),primary_mobile_no,CompanyMobile,CompName);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CandidateView.this, LinearLayoutManager.VERTICAL, false);
                candidateRecycleList.setLayoutManager(layoutManager);
                candidateRecycleList.setAdapter(cadiAdapter);
            } else {
                Toast.makeText(CandidateView.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
