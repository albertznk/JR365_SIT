package com.goldenictsolutions.win.goldenictjob365.Employer.FragPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter.EmployerSearchApplicantAdapter;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by nuke on 8/2/17.
 */

public class EmployerSearch extends android.support.v4.app.Fragment implements View.OnClickListener {
    EditText searchBox;
    ImageButton searchBTN;
    RecyclerView searchResult;
    DB_Control db_control;
    ArrayList<DB_USERDATA> db_userdata;
    ProgressDialog progress;
    ArrayList<EmployerModel.Search_Applicant_Data> applicant_result = new ArrayList<>();
    ViewGroup container;
    EmployerSearchApplicantAdapter adapter;
    ImageView search_logo;
    String userID;
    int success;

    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        db_userdata = db_control.getUserid();
        userID = db_userdata.get(0).getUser_id();
        db_control.closeDb();
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        View v = inflater.inflate(R.layout.employer_frag_search, container, false);
        searchResult = (RecyclerView) v.findViewById(R.id.searchResult);

        searchBox = (EditText) v.findViewById(R.id.job_search_box);
        searchBox.setText("");
        searchBTN = (ImageButton) v.findViewById(R.id.jobSearchButton);
        searchBTN.setOnClickListener(this);
        return v;
    }

    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onClick(View v) {
        searchResult.setAdapter(null);

        if (searchBox.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Input What you want to Find", Toast.LENGTH_SHORT).show();
        } else {
            if (EmployerFragHost.package_Type.equals("Basic")) {
                //Toast.makeText(getContext(), "Please Upgrade package", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.Upgrade));
                builder.setMessage(getResources().getString(R.string.please_upgrade));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
            } else {
                new CheckPackage().execute(userID);
            }

            /*applicant_result.clear();
            new SearchBackGroundWorker().execute(searchBox.getText().toString());
*/
        }

    }

    public class CheckPackage extends AsyncTask {

        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getContext());
            progress.setMessage(getResources().getText(R.string.Searching));
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            Log.e("Check Package", "");
        }

        protected void onPostExecute(Object o) {

            Log.e("Sex", "" + o);

            if (o.equals(1)) {
                applicant_result.clear();
                new SearchBackGroundWorker().execute(searchBox.getText().toString());
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Out of Package");
                builder.setMessage("Wanna buy?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        applicant_result.clear();
                        new SearchBackGroundWorker().execute(searchBox.getText().toString());

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progress.dismiss();
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        }

        protected Object doInBackground(Object[] objects) {

            String expired_date_url = "http://allreadymyanmar.com/api/expired_employer/" + userID;
            Log.e("Sex", "" + userID);
            HttpHandler handlerH = new HttpHandler();
            String checkResponse1 = handlerH.makeServiceCall(expired_date_url);
            JSONObject employer_package = null;
            if (checkResponse1 != null) {
                Log.e("Sex", "");
                try {
                    JSONObject jsonObject = new JSONObject(checkResponse1);
                    success = jsonObject.getInt("employer");
                    Log.e("Emma Waton", "" + success);


                } catch (JSONException e) {
                    Log.e("JSON Error", "");
                    e.printStackTrace();
                }
            }
            return success;
        }
    }


    private class SearchBackGroundWorker extends AsyncTask<Object, Object, ArrayList<EmployerModel.Search_Applicant_Data>> {


        protected ArrayList<EmployerModel.Search_Applicant_Data> doInBackground(Object... params) {
            Object keyword = params[0];
            String searchLink = "http://allreadymyanmar.com/api/getFilterApplicant/" + keyword;
            HttpHandler handlerH = new HttpHandler();
            String checkResponse = handlerH.makeServiceCall(searchLink);
            JSONArray jobList = null;
            if (checkResponse != null) {
                Log.e(TAG, "doInBackground search Applicant: " + checkResponse);
                try {
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    jobList = jsonObject.getJSONArray("filter_applicant");
                    for (int i = 0; i < jobList.length(); i++) {
                        JSONObject c = jobList.getJSONObject(i);
                        applicant_result.add(new EmployerModel.Search_Applicant_Data(
                                c.getString("id"),
                                c.getString("user_id"),
                                c.getString("name"),
                                c.getString("father_name"),
                                c.getString("marital_status"),
                                c.getString("gender"),
                                c.getString("expected_salary"),
                                c.getString("date_of_birth"),
                                c.getString("nrc_no"),
                                c.getString("religion"),
                                c.getString("place_of_birth"),
                                c.getString("photo"),
                                c.getString("mobile_no"),
                                c.getString("email"),
                                c.getString("address"),
                                c.getString("nationality"),
                                c.getString("cv_views"),
                                c.getString("attach_cv"),
                                c.getString("current_position"),
                                c.getString("desired_position"),
                                c.getString("driving_license"),
                                c.getString("city"),
                                c.getString("country"),
                                c.getString("township")));

                        Thread.sleep(100);
                    }
                    return applicant_result;
                } catch (JSONException e) {
                    return applicant_result;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e(TAG, "doInBackground + Search Applicant : Return Length :" + applicant_result.size());

            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.Search_Applicant_Data> dataLists) {
            progress.dismiss();
            if (dataLists != null){
                if (dataLists.size() >0) {
                    adapter = new EmployerSearchApplicantAdapter(applicant_result, container.getContext());
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    searchResult.setLayoutManager(lm);
                    searchResult.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Done...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No Applicant Found", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), getResources().getString(R.string.Check_connection), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
