package com.goldenictsolutions.win.goldenictjob365.Employer.FragPages;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerAboutCompany;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerProfile;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.EmployerSpinnerAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.OpenJobListAdapter;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.package_Type;
import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.remain_time;


/**
 * Created by nuke on 8/2/17.
 */

public class EmployerDashboard extends android.support.v4.app.Fragment implements View.OnClickListener {
    public String EMPR_USER_ID;
    public String EMPR_COMP_L_API = "http://allreadymyanmar.com/api/all_company/";
    public String DELETE_COMP_API = "http://allreadymyanmar.com/api/delete_company/";
    public String JOB_COUNT = "http://allreadymyanmar.com/api/jobCount";
    EmployerSpinnerAdapter spinAdapt;
    ArrayList<EmployerModel.OpenJobModel> openJobList = new ArrayList<>();
    DB_Control db_control;
    ArrayList<DB_USERDATA> userData;
    TextView totalCount, ErrorText, refreshTEXT;
    RelativeLayout employer_dashboard;
    ProgressDialog pd;
    String username, userpassword;
    FetchOpenjob fetchOpenJob;
    JOB_COUNT job;
    TextView package_textview, basicTxt;
    FetchCompanyList fetchcompList;
    private String EMPR_OPENJOB_L_API = "http://allreadymyanmar.com/api/candidate_count/";
    private RecyclerView openJobRecycelList;
    private Spinner compListSpinner;
    private ProgressBar progressBar;
    private Button upgradePackageBTN;
    private Button viewBTN;
    private Button DeleteBTN;
    private Button addCompBTN;
    private ImageButton refreshButton;
    private int type;
    Button confirm, cancel;
    Spinner package_spinner;
    EditText code_edit;
    String package_text;
    private String POST_PACKAGE_API = "http://allreadymyanmar.com/api/upgrade_package";
    private String[] package_list = {"Gold", "Platinum"};
    public static String count;

    public ArrayList<EmployerModel.companyListModel> compList = new ArrayList<>();

    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        userData = db_control.getUserid();
        EMPR_USER_ID = userData.get(0).getUser_id();
        username = userData.get(0).getUser_name();
        userpassword = userData.get(0).getUser_password();
        db_control.closeDb();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dashView = inflater.inflate(R.layout.employer_frag_dashboard, container, false);
        initializeId(dashView);
        ErrorText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        basicTxt.setText(package_Type);
        if(package_Type.equals("Basic")){
            package_textview.setText("0 Hr");
        }else{
            if(package_Type.equals("Gold")){
            basicTxt.setBackgroundColor(getResources().getColor(R.color.gold));
            }else if(package_Type.equals("Platinum")){
                basicTxt.setBackgroundColor(getResources().getColor(R.color.platinum));
            }

            package_textview.setText(remain_time);
        }
        new FetchCompanyList().execute(EMPR_COMP_L_API + EMPR_USER_ID);
        job = new JOB_COUNT();
        job.execute();
        return dashView;
    }

    private void initializeId(View v) {
        basicTxt = (TextView) v.findViewById(R.id.basicTxt);
        employer_dashboard = (RelativeLayout) v.findViewById(R.id.employer_dashboard);
        openJobRecycelList = (RecyclerView) v.findViewById(R.id.jobOpenRecyclelist);
        compListSpinner = (Spinner) v.findViewById(R.id.businessNameSpiner);
        upgradePackageBTN = (Button) v.findViewById(R.id.packageUpgradeButton);
        upgradePackageBTN = (Button) v.findViewById(R.id.packageUpgradeButton);
        viewBTN = (Button) v.findViewById(R.id.veiwButton);
        DeleteBTN = (Button) v.findViewById(R.id.deleteButton);
        package_textview = (TextView) v.findViewById(R.id.package_textview);
        addCompBTN = (Button) v.findViewById(R.id.addButton);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        totalCount = (TextView) v.findViewById(R.id.totalCount);
        ErrorText = (TextView) v.findViewById(R.id.employer_dashboard_error_text);
        refreshButton = (ImageButton) v.findViewById(R.id.dashboard_refresh_imgbtn);
        refreshTEXT = (TextView) v.findViewById(R.id.refreshTxtView);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upgradePackageBTN.setOnClickListener(this);
        viewBTN.setOnClickListener(this);
        DeleteBTN.setOnClickListener(this);
        addCompBTN.setOnClickListener(this);
        compListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new FetchOpenjob().execute(EMPR_OPENJOB_L_API + compList.get(position).getCompId());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.packageUpgradeButton:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_company_dialog, null);


                cancel = (Button) dialogView.findViewById(R.id.cancellBTN);
                confirm = (Button) dialogView.findViewById(R.id.confirmBTN);
                package_spinner = (Spinner) dialogView.findViewById(R.id.package_spinner);
                confirm = (Button) dialogView.findViewById(R.id.confirmBTN);
                code_edit = (EditText) dialogView.findViewById(R.id.code_edit);


                builder1.setView(dialogView);
                final AlertDialog dialog = builder1.create();
                dialog.show();

                ArrayAdapter a = new ArrayAdapter(getActivity(), R.layout.custom_layout_for_spinner, package_list);
                a.setDropDownViewResource(android.R.layout.simple_list_item_1);
                package_spinner.setAdapter(a);
                package_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        package_text = package_spinner.getSelectedItem().toString();
                        Log.e("Package_Text", package_text);
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                        package_text = null;
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (code_edit.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Enter Code", Toast.LENGTH_SHORT).show();
                        } else {
                            new AsyncTask<String, Void, String>() {

                                protected void onPreExecute() {
                                    pd = new ProgressDialog(getContext());
                                    pd.setMessage(getActivity().getResources().getString(R.string.loading));
                                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    pd.setCancelable(false);
                                    pd.show();
                                }

                                protected String doInBackground(String... strings) {
                                    OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(55, TimeUnit.SECONDS)
                                            .writeTimeout(55, TimeUnit.SECONDS)
                                            .connectTimeout(55, TimeUnit.SECONDS)
                                            .build();
                                    //httpClient.retryOnConnectionFailure();

                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("user_id", EMPR_USER_ID);
                                        jsonObject.put("key_code", strings[0]);
                                        jsonObject.put("pkg", strings[1]);

                                        MediaType meidaType = MediaType.parse("application/json");
                                        RequestBody body = RequestBody.create(meidaType, String.valueOf(jsonObject));
                                        Request request = new Request.Builder()
                                                .url(POST_PACKAGE_API)
                                                .post(body)
                                                .build();
                                        Log.i("POST JOB", "doInBackground: ");
                                        Response response = httpClient.newCall(request).execute();

                                        if (response != null) {
                                            Log.i("POST Package", "doInBackground: " + response.message());

                                            return response.body().string();

                                        }

                                    } catch (Exception e) {

                                        Log.i("POST_JOB", "doInBackground: post Package error  :" + e);
                                    }
                                    return null;

                                }

                                protected void onPostExecute(String s) {
                                    pd.dismiss();
                                    if (s != null) {

                                        try {
                                            dialog.dismiss();
                                            JSONObject json = new JSONObject(s);
                                            Log.e("JSON", "" + json);

                                            if(json.getString("result").equals("Success")){
                                                EmployerFragHost.remain_time = json.getString("remain_time");
                                                EmployerFragHost.package_Type = json.getString("package_type");
                                                remain_time = json.getString("remain_time");
                                                package_text = json.getString("package_type");

                                                if(package_text.equals("Gold")){
                                                    basicTxt.setBackgroundColor(getResources().getColor(R.color.gold));
                                                }else if(package_text.equals("Platinum")){
                                                    basicTxt.setBackgroundColor(getResources().getColor(R.color.platinum));
                                                }

                                                basicTxt.setText(package_text);
                                                package_textview.setText(remain_time);

                                                Toast.makeText(getActivity(), json.getString("result"), Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getActivity(), ""+json.getString("result"), Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }.execute(code_edit.getText().toString(), package_text);
                        }
                    }
                });

                break;
            case R.id.addButton:
                Intent i = new Intent(getActivity(), EmployerProfile.class);
                i.putExtra("user_id", EMPR_USER_ID);
                i.putExtra("login_username", username);
                i.putExtra("login_userpassword", userpassword);
                i.putExtra("new", 1);
                i.putExtra("remain_time", remain_time);
                i.putExtra("package_type", package_Type);
                getContext().startActivity(i);
                break;
            case R.id.deleteButton:
                if (compListSpinner.getSelectedItemPosition() >= 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getResources().getString(R.string.delete));
                    builder.setMessage(getResources().getString(R.string.delete_company));
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ii = compList.get(compListSpinner.getSelectedItemPosition()).getCompId();
                            dialog.dismiss();
                            dialog.cancel();
                            new AsyncTask<String, Object, String>() {
                                ProgressDialog pd = new ProgressDialog(getActivity());
                                protected void onPreExecute() {
                                    DeleteBTN.setEnabled(false);
                                    pd.setMessage("Deleting");
                                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    pd.setCancelable(false);
                                    pd.show();
                                }

                                protected String doInBackground(String... params) {
                                    OkHttpClient httpClient = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url(DELETE_COMP_API + params[0])
                                            .delete()
                                            .build();
                                    Log.e("id", "doInBackground: " + params[0]);
                                    try {
                                        Response response = httpClient.newCall(request).execute();
                                        return response.body().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                protected void onPostExecute(String s) {
                                    if (s != null) {
                                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                        if (compListSpinner.getSelectedItemPosition()==0 && compList.size()==1){
                                            viewBTN.setEnabled(false);
                                            DeleteBTN.setEnabled(false);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            ErrorText.setText("There is no active company");
                                            openJobRecycelList.setVisibility(View.GONE);
                                        }else {
                                            compListSpinner.setSelection(0);
                                            compListSpinner.setSelected(true);
                                        }
                                        compList.remove(compListSpinner.getSelectedItemPosition());
                                        spinAdapt.notifyDataSetChanged();
                                        openJobList.clear();
                                    }
                                    DeleteBTN.setEnabled(true);
                                    pd.dismiss();
                                    pd.cancel();
                                }
                            }.execute(ii);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                } else {
                    Toast.makeText(getContext(), "Error!...", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.veiwButton:
                Intent intent = new Intent(getContext(), EmployerAboutCompany.class);
                if (!compList.get(compListSpinner.getSelectedItemPosition()).getCompId().equals("") && compListSpinner.getSelectedItemPosition() >= 0) {
                    intent.putExtra("company_id", compList.get(compListSpinner.getSelectedItemPosition()).getCompId());
                    Log.e("Type....", "" + compList.get(compListSpinner.getSelectedItemPosition()).getType());
                    Log.e("Type....", "" + compList.get(compListSpinner.getSelectedItemPosition()).getCompId());
                    intent.putExtra("type", compList.get(compListSpinner.getSelectedItemPosition()).getType());
                    intent.putExtra("remain_time", remain_time);
                    intent.putExtra("package_type", package_Type);
                    getContext().startActivity(intent);
                }
                break;
        }
    }


    /////////////////Get Data From Post Job Fragment Through DataTransferMedium Interface ////////////////////////////
    public void GetData(Boolean successOrNot, int compPosition) {
        if (successOrNot == true) {
            if (compPosition== compListSpinner.getSelectedItemPosition()){
                new FetchOpenjob().execute(EMPR_OPENJOB_L_API+compList.get(compPosition).getCompId());
            }else {
                compListSpinner.setSelection(compPosition);
                compListSpinner.setSelected(true);
            }
        }
    }



    /////////////////////////////////////////DONWLOAD COMANYLIST FOR SHOW SPINNER //////////////////////////////
    public class FetchCompanyList extends AsyncTask<String, String, ArrayList<EmployerModel.companyListModel>> {
        protected void onPreExecute() {
            refreshTEXT.setVisibility(View.INVISIBLE);
            refreshButton.setVisibility(View.INVISIBLE);
            ErrorText.setVisibility(View.INVISIBLE);
            viewBTN.setEnabled(false);
            compList.clear();
            openJobList.clear();
        }

        @Override
        protected ArrayList<EmployerModel.companyListModel> doInBackground(String... params) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .build();
            try {
                Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   111");
                Response response = httpClient.newCall(request).execute();
                Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   222");
                String result = response.body().string();
                Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   333");
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray companyJsonList = jsonObject.getJSONArray("user_company");
                    Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   444");
                    for (int i = 0; i < companyJsonList.length(); i++) {
                        JSONObject listObj = companyJsonList.getJSONObject(i);
                        compList.add(new EmployerModel.companyListModel(listObj.getString("company_name"), listObj.getString("id"), listObj.getString("type")));
                    }
                    Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   555");
                    return compList;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.companyListModel> companyListModels) {
            //Log.i("LG", "doInBackground: ++++++++++++++++++++++++++++++++++++++++++++++++++   666");
            if (companyListModels != null) {
                if (companyListModels.size() > 0) {
                    spinAdapt = new EmployerSpinnerAdapter(getContext(), companyListModels);
                    compListSpinner.setAdapter(spinAdapt);
                    viewBTN.setEnabled(true);
                } else {
                    ErrorText.setText("There is no active company");
                    ErrorText.setVisibility(View.VISIBLE);
                }
            } else {
                refreshTEXT.setVisibility(View.VISIBLE);
                refreshButton.setVisibility(View.VISIBLE);
                ErrorText.setText(getResources().getString(R.string.Check_connection));
                refreshButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new FetchCompanyList().execute(EMPR_COMP_L_API + EMPR_USER_ID);
                    }
                });
            }
        }
    }
    private class FetchOpenjob extends AsyncTask<String, String, ArrayList<EmployerModel.OpenJobModel>> {
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            compListSpinner.setEnabled(false);
            openJobRecycelList.setVisibility(View.GONE);
            openJobList.clear();
        }

        protected ArrayList<EmployerModel.OpenJobModel> doInBackground(String... params) {
            Log.i("OPEN", "doInBackground: Open Job" + params[0]);
            OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String resultRespone = response.body().string();
                if (resultRespone != null) {
                    JSONObject jsonObj = new JSONObject(resultRespone);
                    try{
                        JSONArray jsonList = jsonObj.getJSONArray("candidate");
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject jsonItem = jsonList.getJSONObject(i);
                            openJobList.add(new EmployerModel.OpenJobModel(jsonItem.getString("job_count"),
                                    jsonItem.getString("job_title"),
                                    jsonItem.getString("id"),
                                    jsonItem.getString("job_id"))

                            );
                            Log.i("OPEN", "doInBackground: Open jOb" + i);
                        }
                        return openJobList;
                    }catch (JSONException obj){
                        return openJobList;
                    }

                }
            } catch (IOException e) {
                Log.e("OPEN", "doInBackground: IOresponse" + e);
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e("OPEN", "doInBackground: JSONresponse" + e);
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.OpenJobModel> openJobsModels) {
            progressBar.setVisibility(View.GONE);
            ErrorText.setVisibility(View.GONE);
            if (openJobsModels != null) {
                if (openJobsModels.size() > 0) {
                    openJobRecycelList.setVisibility(View.VISIBLE);
                    OpenJobListAdapter adapter = new OpenJobListAdapter(getContext(), openJobsModels);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    openJobRecycelList.setLayoutManager(layoutManager);
                    openJobRecycelList.setAdapter(adapter);
                } else {
                    ErrorText.setText("There is no active job");
                    ErrorText.setVisibility(View.VISIBLE);
                }
            } else {
                ErrorText.setText(getResources().getString(R.string.Check_connection));
                ErrorText.setVisibility(View.VISIBLE);
            }
            compListSpinner.setEnabled(true);
        }
    }


    /////////////////////////////////////////////jUST FOR JOB COUNT SHOW ONLY//////////////////////////////////////////////
    public class JOB_COUNT extends AsyncTask<Object, Void, String> {

        protected String doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();
            Log.e("JOB COUNT", "" + count);
            Request request = new Request.Builder()
                    .url(JOB_COUNT)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);

                    count = jsonObject.getString("job_count");
                    Log.e("JOB COUNT1", "" + count);
                    return count;

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String o) {
            Log.e("Post JOB COUNT2", "" + o);
            if (o != null) {
                Log.e("Post JOB COUNT", "" + o);
                totalCount.setText(o);
                Log.e("JOB COUNT SUCCESS", "");
                // Toast.makeText(getActivity(), "Job Count Success", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("JOB COUNT FAIL", "");
                // Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (job.getStatus() == AsyncTask.Status.RUNNING) {
                job.execute(cancel);
            }
        } catch (Exception ee) {
            Log.i("Thread exception", "onDestroyView: ");
        }
    }
}
