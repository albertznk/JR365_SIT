package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

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

import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nuke on 6/22/17.
 */

public class AboutCompany extends AppCompatActivity implements View.OnClickListener {
    public static final String GET_COMPANY_INFO_BY_CID = "http://allreadymyanmar.com/api/company/";
    TextView setCompanyNameF, setCompanyConatact, setCompanyMail, setCompanyAddress,
            setCompanyBusinessType, setCompany1ConPerson, setCompany2ConPerson,
            setMobile1, setMobile2, setAboutCompany;
    ImageView setCompanyLogo;
    RelativeLayout aboutCompanyLayout;
    ProgressBar progressBar;
    Button backBTN;
    String i;
    ImageLoader imageLoader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_company);
        findID();
        i = getIntent().getExtras().getString("company_id");
        new FetchCompanyDetail().execute(i);
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
    }

    public void onBackPressed() {
        //Toast.makeText(AboutCompany.this, "Going Back", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onClick(View v) {
        //Toast.makeText(AboutCompany.this, "Going Back", Toast.LENGTH_SHORT).show();
        finish();
    }

    //Download CompanyDetail
    private class FetchCompanyDetail extends AsyncTask<Object, Object, ArrayList<CompanyInfo>> {
        protected void onPreExecute() {
            imageLoader = new ImageLoader(getApplicationContext());
        }

        protected ArrayList<CompanyInfo> doInBackground(Object... params) {
            HttpHandler httpHandle = new HttpHandler();
            Object companyId = params[0];
            String ResuLt = httpHandle.makeServiceCall(GET_COMPANY_INFO_BY_CID + companyId);
            if (ResuLt != null) {
                Log.e("Response", "doInBackground: " + ResuLt);
                try {
                    JSONObject jsonObj = new JSONObject(ResuLt);
                    JSONArray jsonAry = jsonObj.getJSONArray("company");
                    JSONObject take = jsonAry.getJSONObject(0);
                    ArrayList<CompanyInfo> CompanyInfoList = new ArrayList<>();
                    CompanyInfoList.add(new CompanyInfo(take.getString("company_name"),
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

        protected void onPostExecute(ArrayList<CompanyInfo> companyInfos) {
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
                new FetchCompanyDetail().execute(i);
                //Toast.makeText(AboutCompany.this, "Data Downloading error ", Toast.LENGTH_SHORT).show();
            }

        }
    }


    //Data Model for CompanyInfo
    public class CompanyInfo {
        String CompanyName, CompanyLogo, CompanyAddress, CompanyMobile, Companyemail, CompanySebsite,
                AboutCompany, PcontactP, PcontactM, ScontactP, ScontactM, CompanyCity, CompanyTownship,
                CompanyCounty, CompanyType;

        public CompanyInfo(String companyName, String companyLogo, String companyAddress, String companyMobile, String companyemail, String companySebsite, String aboutCompany, String pcontactP, String pcontactM, String scontactP, String scontactM, String companyCity, String companyTownship, String companyCounty, String CompanyType) {
            CompanyName = companyName;
            CompanyLogo = companyLogo;
            CompanyAddress = companyAddress;
            CompanyMobile = companyMobile;
            Companyemail = companyemail;
            CompanySebsite = companySebsite;
            AboutCompany = aboutCompany;
            PcontactP = pcontactP;
            PcontactM = pcontactM;
            ScontactP = scontactP;
            ScontactM = scontactM;
            CompanyCity = companyCity;
            CompanyTownship = companyTownship;
            CompanyCounty = companyCounty;
            this.CompanyType = CompanyType;
        }

        public String getCompanyType() {
            return CompanyType;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public String getCompanyLogo() {
            return CompanyLogo;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public String getCompanyMobile() {
            return CompanyMobile;
        }

        public String getCompanyemail() {
            return Companyemail;
        }

        public String getCompanySebsite() {
            return CompanySebsite;
        }

        public String getAboutCompany() {
            return AboutCompany;
        }

        public String getPcontactP() {
            return PcontactP;
        }

        public String getPcontactM() {
            return PcontactM;
        }

        public String getScontactP() {
            return ScontactP;
        }

        public String getScontactM() {
            return ScontactM;
        }

        public String getCompanyCity() {
            return CompanyCity;
        }

        public String getCompanyTownship() {
            return CompanyTownship;
        }

        public String getCompanyCounty() {
            return CompanyCounty;
        }
    }

}
