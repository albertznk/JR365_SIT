package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nuke on 8/3/17.
 */

public class EmployerAboutCompany extends AppCompatActivity implements View.OnClickListener {
    public static final String GET_COMPANY_INFO_BY_CID = "http://allreadymyanmar.com/api/company/";
    TextView setCompanyNameF, setCompanyConatact, setCompanyMail, setCompanyAddress,
            setCompanyBusinessType, setCompany1ConPerson, setCompany2ConPerson,
            setMobile1, setMobile2, setAboutCompany;
    ImageView setCompanyLogo;
    RelativeLayout aboutCompanyLayout;
    ProgressBar progressBar;
    Button backBTN, ModifyBTN;
    String i;
    ImageLoader imageLoader;
    String type,remain_time,package_type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_about_company);
        findID();
        i = getIntent().getExtras().getString("company_id");
        remain_time=getIntent().getExtras().getString("remain_time");
        package_type=getIntent().getExtras().getString("package_type");
        new FetchCompanyDetail().execute(i);
        ModifyBTN.setOnClickListener(this);
        backBTN.setOnClickListener(this);
    }

    private void findID() {
        progressBar = (ProgressBar) findViewById(R.id.aboutCompanyProgressBar);
        aboutCompanyLayout = (RelativeLayout) findViewById(R.id.aboutCompanyLayout);
        setCompanyNameF = (TextView) findViewById(R.id.empCompanyName);
        setCompanyConatact = (TextView) findViewById(R.id.setCompanyContact);
        setCompanyMail = (TextView) findViewById(R.id.setCompanyMail);
        setCompanyAddress = (TextView) findViewById(R.id.setCompanyAddress);
        setCompanyBusinessType = (TextView) findViewById(R.id.setCompanyBusinessType);
        setCompany1ConPerson = (TextView) findViewById(R.id.setCompanyCoPerson);
        setCompany2ConPerson = (TextView) findViewById(R.id.setCompanySePerson);
        setMobile1 = (TextView) findViewById(R.id.setMobilePrimary);
        setMobile2 = (TextView) findViewById(R.id.setMobileSecondary);
        setAboutCompany = (TextView) findViewById(R.id.setAboutCompany);
        setCompanyLogo = (ImageView) findViewById(R.id.setCompanyLogo);
        backBTN = (Button) findViewById(R.id.backButton);
        ModifyBTN = (Button) findViewById(R.id.modifyCompany);
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modifyCompany:
                Intent ii = new Intent(EmployerAboutCompany.this, EmployerProfile.class);
                ii.putExtra("new", 0);
                ii.putExtra("company_id", i);
                ii.putExtra("type",type);
                ii.putExtra("remain_time",remain_time);
                ii.putExtra("package_type",package_type);
                ii.putExtra("type",type);
                Log.e("About Modify","Modify"+remain_time+"\n"+package_type);
                startActivity(ii);
                finish();
                break;
            case R.id.backButton:
                Intent i=new Intent(EmployerAboutCompany.this,EmployerFragHost.class);
                i.putExtra("remain_time",remain_time);
                i.putExtra("package_type",package_type);

                startActivity(i);
                finish();
                break;
        }
    }


    //Download Company Detail
    private class FetchCompanyDetail extends AsyncTask<Object, Object, ArrayList<EmployerModel.CompanyInfo>> {
        protected void onPreExecute() {
            imageLoader = new ImageLoader(getApplicationContext());
            aboutCompanyLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected ArrayList<EmployerModel.CompanyInfo> doInBackground(Object... params) {
            HttpHandler httpHandle = new HttpHandler();
            Object companyId = params[0];
            String ResuLt = httpHandle.makeServiceCall(GET_COMPANY_INFO_BY_CID + companyId);
            if (ResuLt != null) {
                Log.e("Response Error", "doInBackground: " + ResuLt);
                try {
                    JSONObject jsonObj = new JSONObject(ResuLt);
                    JSONArray jsonAry = jsonObj.getJSONArray("company");
                    JSONObject take = jsonAry.getJSONObject(0);
                    ArrayList<EmployerModel.CompanyInfo> CompanyInfoList = new ArrayList<>();
                    CompanyInfoList.add(new EmployerModel.CompanyInfo(take.getString("company_name"),
                            take.getString("company_logo"),
                            take.getString("address"),
                            take.getString("mobile_no"),
                            take.getString("email"),
                            take.getString("website"),
                            take.getString("description"),
                            take.getString("primary_contact_person"),
                            take.getString("primary_mobile"),
                            take.getString("secondary_contact_person"),
                            take.getString("secondary_mobile"),
                            take.getString("city"),
                            take.getString("township"),
                            take.getString("country"),
                            take.getString("job_industry")
                    ));
                    return CompanyInfoList;
                } catch (JSONException e) {
                    Log.e("JSON object Error", "doInBackground: " + e);
                }
            }
            return null;
        }

        protected void onPostExecute(ArrayList<EmployerModel.CompanyInfo> companyInfos) {
            if (companyInfos != null) {
                imageLoader.DisplayImage("http://allreadymyanmar.com/uploads/company_logo/" + companyInfos.get(0).getCompanyLogo(), setCompanyLogo);
                setCompanyNameF.setText(companyInfos.get(0).getCompanyName());
                setCompanyConatact.setText(companyInfos.get(0).getCompanyMobile());
                setCompanyMail.setText(companyInfos.get(0).getCompanyemail());
                setCompanyAddress.setText(companyInfos.get(0).getCompanyAddress() + "\n" + companyInfos.get(0).getCompanyTownship() +
                        " - " + companyInfos.get(0).getCompanyCity() + " - " + companyInfos.get(0).getCompanyCounty());
                setCompanyBusinessType.setText(companyInfos.get(0).getCompanyType());
                setCompany1ConPerson.setText(companyInfos.get(0).getPcontactP());
                setCompany2ConPerson.setText(companyInfos.get(0).getScontactP());
                setMobile1.setText(companyInfos.get(0).getPcontactM());
                setMobile2.setText(companyInfos.get(0).getScontactM());
                setAboutCompany.setText(companyInfos.get(0).getAboutCompany());
                progressBar.setVisibility(View.GONE);
                aboutCompanyLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(EmployerAboutCompany.this, "Data Downloading error ", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
