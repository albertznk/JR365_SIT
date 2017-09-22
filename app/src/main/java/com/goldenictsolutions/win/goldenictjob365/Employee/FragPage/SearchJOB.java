package com.goldenictsolutions.win.goldenictjob365.Employee.FragPage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.SearchAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.SearchDataList;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchJOB extends Fragment implements View.OnClickListener {
    EditText searchBox;
    ImageButton searchBTN;
    RecyclerView searchResult;
    ProgressDialog progress;
    ArrayList<SearchDataList> resultList = new ArrayList<>();
    ViewGroup container;
    SearchAdapter adapter;
    ImageView search_logo;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        View v = inflater.inflate(R.layout.frag_employee_search, container, false);
        searchResult = (RecyclerView) v.findViewById(R.id.searchResult);
        searchBox = (EditText) v.findViewById(R.id.job_search_box);
        searchBTN = (ImageButton) v.findViewById(R.id.jobSearchButton);
        searchBox.setText("");
        adapter = new SearchAdapter(resultList, container.getContext());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchResult.setLayoutManager(lm);
        return v;
    }

    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchBTN.setOnClickListener(this);
    }

    public void onClick(View v) {
        searchResult.setAdapter(null);
        if (searchBox.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Input What you want to Find", Toast.LENGTH_SHORT).show();
        } else {
            resultList.clear();
            new SearchBackGroundWorker().execute(searchBox.getText().toString());
        }

    }

    ///////////////////////////////////////Search Jobseeker////////////////////////////////////////
    private class SearchBackGroundWorker extends AsyncTask<String, Object, ArrayList<SearchDataList>> {
        protected void onPreExecute() {
            progress = new ProgressDialog(getContext());
            progress.setMessage(getResources().getText(R.string.Searching));
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
        }

        protected ArrayList<SearchDataList> doInBackground(String... params) {
            String keyword = params[0];
            String searchLink = "http://allreadymyanmar.com/api/getFilterJob/" + keyword;
            HttpHandler handlerH = new HttpHandler();
            String checkResponse = handlerH.makeServiceCall(searchLink);
            JSONArray jobList = null;
            if (checkResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    jobList = jsonObject.getJSONArray("job");
                    for (int i = 0; i < jobList.length(); i++) {
                        JSONObject c = jobList.getJSONObject(i);
                        resultList.add(new SearchDataList(c.getString("id"),
                                c.getString("employer_id"),
                                c.getString("job_category_id"),
                                c.getString("job_nature_id"),
                                c.getString("job_title"),
                                c.getString("company_id"),
                                c.getString("min_salary"),
                                c.getString("max_salary"),
                                c.getString("summary"),
                                c.getString("description"),
                                c.getString("requirement"),
                                c.getString("township_id"),
                                c.getString("city_id"),
                                c.getString("country_id"),
                                c.getString("open_date"),
                                c.getString("close_date"),
                                c.getString("contact_info"),
                                c.getString("graduate"),
                                c.getString("accomodation"),
                                c.getString("male"),
                                c.getString("female"),
                                c.getString("unisex"),
                                c.getString("min_age"),
                                c.getString("max_age"),
                                c.getString("single"),
                                c.getString("food_supply"),
                                c.getString("ferry_supply"),
                                c.getString("language_skill"),
                                c.getString("training"),
                                c.getString("is_active"),
                                c.getString("created_at"),
                                c.getString("updated_at"),
                                c.getString("township"),
                                c.getString("city"),
                                c.getString("company_name"),
                                c.getString("company_logo"),
                                c.getString("category")
                        ));
                        Thread.sleep(100);
                    }
                    return resultList;
                } catch (JSONException e) {
                    return resultList;
                    // e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(ArrayList<SearchDataList> dataLists) {

            progress.dismiss();
            if (dataLists != null) {
                if (dataLists.size() > 0) {
                    searchResult.setAdapter(adapter);

                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.search_job_employee), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.Check_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
