package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.Check_connection;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.CV_Data;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Education_Data;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Experience_Data;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Qualification_Data;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Skill_Data;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.goldenictsolutions.win.goldenictjob365.View_Web_CV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.package_Type;
import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.remain_time;


/**
 * Created by kurio on 8/1/17.
 */

public class EmployerViewApplicant extends AppCompatActivity implements View.OnClickListener {
    String organi, rank, exp_start, exp_end;
    String uni, degree, edu_start_date, edu_end_date;
    String center, course, qua_start, qua_end;
    String language, spoken, written;
    ImageView imgView_profile;
    TextView cv_attach;
    DB_Control db_control;
    ArrayList<DB_USERDATA> userdata = new ArrayList<>();
    String EMP_ID;
    ProgressDialog pd;
    String ph;
    String CALL_SMSM = "http://allreadymyanmar.com/api/candidate";
    private String PDF_DOWNLOAD_LINK = "http://allreadymyanmar.com/uploads/cv/";
    private File outputFile;
    ArrayList<String> edu_start_date_list = new ArrayList<>();
    ArrayList<String> edu_end_date_list = new ArrayList<>();
    ArrayList<String> uni_list = new ArrayList<>();
    ArrayList<String> degree_list = new ArrayList<>();
    ArrayList<String> center_list = new ArrayList<>();
    ArrayList<String> course_list = new ArrayList<>();
    ArrayList<String> qua_start_list = new ArrayList<>();
    ArrayList<String> qua_end_list = new ArrayList<>();
    ArrayList<String> language_list = new ArrayList<>();
    ArrayList<String> spoken_list = new ArrayList<>();
    ArrayList<String> written_list = new ArrayList<>();
    ArrayList<String> organizatio_listn = new ArrayList<>();
    ArrayList<String> rank_data = new ArrayList<>();
    ArrayList<String> exp_start_list = new ArrayList<>();
    ArrayList<String> exp_end_list = new ArrayList<>();
    TextView toolbar_text;
    ArrayList<Education_Data> education_data = new ArrayList<>();
    ArrayList<Experience_Data> experience_data = new ArrayList<>();
    ArrayList<Qualification_Data> qualification_data = new ArrayList<>();
    ArrayList<CV_Data> cvData = new ArrayList<>();
    ArrayList<Skill_Data> skill_data = new ArrayList<>();
    ScrollView scroll_layout;
    ProgressBar pb;
    TextView[] eText_applicant_List = new TextView[25];
    TextView ls_uni_list, ls_qua_list, ls_work_experience_list, ls_skill_list;
    Button callBTN, smsBTN;
    private String userId;

    Button back_employer_cv, cv_download_employer;
    private String success;
    private Uri path;
    TextView cv_employer_text;
    String job_id;
    private int con_live;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_cv_veiw);
        finderapplicantId();

        if (getIntent().getExtras().getString("job_id") != null) {
            job_id = getIntent().getExtras().getString("job_id");

        } else {
            callBTN.setVisibility(View.INVISIBLE);
            smsBTN.setVisibility(View.INVISIBLE);
        }
        userId = getIntent().getExtras().getString("applicant_id");
        db_control = new DB_Control(EmployerViewApplicant.this);
        db_control.openDb();
        userdata = db_control.getUserid();
        EMP_ID = userdata.get(0).getUser_id();
        db_control.closeDb();
        new GetApplicantData().execute(userId);

        callBTN.setOnClickListener(this);
        back_employer_cv.setOnClickListener(this);
        smsBTN.setOnClickListener(this);
        cv_download_employer.setOnClickListener(this);
    }

    private void finderapplicantId() {
        cv_employer_text = (TextView) findViewById(R.id.cv_employer_text);
        cv_download_employer = (Button) findViewById(R.id.cv_download_employer);
        callBTN = (Button) findViewById(R.id.callBTN);
        smsBTN = (Button) findViewById(R.id.smsBTN);
        scroll_layout = (ScrollView) findViewById(R.id.scroll_layout);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        ls_uni_list = (TextView) findViewById(R.id.list_em_Edu);
        ls_qua_list = (TextView) findViewById(R.id.em_Qualification);
        back_employer_cv = (Button) findViewById(R.id.back_employer_cv);
        ls_work_experience_list = (TextView) findViewById(R.id.em_WorkingExperience);
        ls_skill_list = (TextView) findViewById(R.id.em_language);
//        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
//        toolbar_text.setText("CV");

        eText_applicant_List[0] = (TextView) findViewById(R.id.ed_salary);
        eText_applicant_List[1] = (TextView) findViewById(R.id.ed_city_id);
        eText_applicant_List[2] = (TextView) findViewById(R.id.ed_drivingLicense);
        eText_applicant_List[3] = (TextView) findViewById(R.id.ed_desire_position);
        eText_applicant_List[4] = (TextView) findViewById(R.id.ed_current_position);
        eText_applicant_List[5] = (TextView) findViewById(R.id.ed_marital);
        eText_applicant_List[6] = (TextView) findViewById(R.id.ed_religion);
        eText_applicant_List[7] = (TextView) findViewById(R.id.ed_national);
        eText_applicant_List[8] = (TextView) findViewById(R.id.emFatherName_workCode);
        eText_applicant_List[9] = (TextView) findViewById(R.id.emNRC_WorkCode);
        eText_applicant_List[10] = (TextView) findViewById(R.id.emDate);
        eText_applicant_List[11] = (TextView) findViewById(R.id.em_Mobile);
        eText_applicant_List[12] = (TextView) findViewById(R.id.ed_gmail);
        eText_applicant_List[16] = (TextView) findViewById(R.id.em_Name);
        eText_applicant_List[17] = (TextView) findViewById(R.id.countryEdit);
        eText_applicant_List[20] = (TextView) findViewById(R.id.genderEdit);
        eText_applicant_List[21] = (TextView) findViewById(R.id.birthplaceEdit);
        eText_applicant_List[22] = (TextView) findViewById(R.id.addressEdit);
        eText_applicant_List[23] = (TextView) findViewById(R.id.township_edit);
        cv_attach = (TextView) findViewById(R.id.cv_attach);
        imgView_profile = (ImageView) findViewById(R.id.em_Img);

    }

    public void cvFormsetText() {
        ImageLoader imgLoad = new ImageLoader(EmployerViewApplicant.this);
        if (cvData.get(0).getPhoto() != null) {
            imgLoad.DisplayImage("http://allreadymyanmar.com/uploads/resume-photo/" + cvData.get(0).getPhoto(), imgView_profile);
        } else {
            imgLoad.DisplayImage("http://allreadymyanmar.com/uploads/resume-photo/person.jpg", imgView_profile);
        }

        //qua set Text
        center_list.clear();
        course_list.clear();
        qua_end_list.clear();
        qua_start_list.clear();
        for (int i = 0; i < qualification_data.size(); i++) {
            center = qualification_data.get(i).getCenter_name();
            course = qualification_data.get(i).getCourse();
            qua_start = qualification_data.get(i).getStart_date();
            qua_end = qualification_data.get(i).getEnd_date();

            center_list.add(center);
            course_list.add(course);
            qua_start_list.add(qua_start);
            qua_end_list.add(qua_end);
        }
        StringBuilder sb_qua = new StringBuilder();
        for (int i = 0; i < center_list.size(); i++) {
            sb_qua.append((i + 1) + ")" + center_list.get(i));
            sb_qua.append("\n");
            sb_qua.append(course_list.get(i));
            sb_qua.append("\n");
            sb_qua.append(qua_start_list.get(i).concat("    ~    " + qua_end_list.get(i)));
            sb_qua.append("\n");
            sb_qua.append("\n");
        }

        //skill
        language_list.clear();
        spoken_list.clear();
        written_list.clear();
        for (int s = 0; s < skill_data.size(); s++) {
            language = skill_data.get(s).getLanguage();
            spoken = skill_data.get(s).getSpoken();
            written = skill_data.get(s).getWritten();
            language_list.add(language);
            spoken_list.add(spoken);
            written_list.add(written);
        }
        StringBuilder sb_skill = new StringBuilder();
        for (int i = 0; i < language_list.size(); i++) {
            sb_skill.append((i + 1) + ")" + language_list.get(i));
            sb_skill.append("\n" + "Speaking Level" + "\t\t" + spoken_list.get(i).concat("\n" + "Writing Level" + "\t\t" + written_list.get(i)));
            sb_skill.append("\n\n");
        }
        uni_list.clear();
        degree_list.clear();

        for (int i = 0; i < education_data.size(); i++) {
            uni = education_data.get(i).getUniversity();
            degree = education_data.get(i).getDegree();
            edu_start_date = education_data.get(i).getEdu_start_date();
            edu_end_date = education_data.get(i).getEdu_end_date();
            uni_list.add(uni);
            degree_list.add(degree);
            edu_start_date_list.add(edu_start_date);
            edu_end_date_list.add(edu_end_date);

        }
        StringBuilder sb_edu = new StringBuilder();
        for (int i = 0; i < uni_list.size(); i++) {
            sb_edu.append((i + 1) + ")" + degree_list.get(i));
            sb_edu.append("\t\t\t\t");
            sb_edu.append(uni_list.get(i));
            sb_edu.append("\n");
            sb_edu.append(edu_start_date_list.get(i).concat("     ~    " + edu_end_date_list.get(i)));
            sb_edu.append("\n");
            sb_edu.append("\n");

        }

        //experience set Text
        organizatio_listn.clear();
        rank_data.clear();
        exp_start_list.clear();
        exp_end_list.clear();
        for (int i = 0; i < experience_data.size(); i++) {
            organi = experience_data.get(i).getOrganization();
            rank = experience_data.get(i).getRank();
            exp_start = experience_data.get(i).getStart_date();
            exp_end = experience_data.get(i).getEnd_date();

            organizatio_listn.add(organi);
            rank_data.add(rank);
            exp_start_list.add(exp_start);
            if (exp_end.equals("null")) {
                exp_end = "Currently Working";
            }
            exp_end_list.add(exp_end);

        }
        StringBuilder sb_exp = new StringBuilder();
        for (int i = 0; i < organizatio_listn.size(); i++) {
            sb_exp.append((i + 1) + ")" + organizatio_listn.get(i));
            sb_exp.append("\n");
            sb_exp.append(rank_data.get(i));
            sb_exp.append("\n");
            sb_exp.append(exp_start_list.get(i).concat("    ~    " + exp_end_list.get(i)));
            sb_exp.append("\n\n");
        }

        cv_employer_text.setText(cvData.get(0).getAttach_cv());
        ls_uni_list.setText(sb_edu);
        ls_qua_list.setText(sb_qua);
        ls_skill_list.setText(sb_skill);
        ls_work_experience_list.setText(sb_exp);
        eText_applicant_List[8].setText(cvData.get(0).getFather_name());
        eText_applicant_List[16].setText(cvData.get(0).getName());
        eText_applicant_List[0].setText(cvData.get(0).getExpected_salary());
        eText_applicant_List[5].setText(cvData.get(0).getMatrtal_status());
        eText_applicant_List[10].setText(cvData.get(0).getDate_of_birth());
        eText_applicant_List[9].setText(cvData.get(0).getNrc_no());
        eText_applicant_List[6].setText(cvData.get(0).getReligion());
        if (cvData.get(0).getDriving_license().equals("0")) {
            eText_applicant_List[2].setText("No");
        } else {
            eText_applicant_List[2].setText("Yes");
        }

        eText_applicant_List[11].setText(cvData.get(0).getMobile_no());
        eText_applicant_List[12].setText(cvData.get(0).getEmail());
        eText_applicant_List[4].setText(cvData.get(0).getCurrent_position());
        eText_applicant_List[3].setText(cvData.get(0).getDesired_position());
        eText_applicant_List[7].setText(cvData.get(0).getNationality());
        eText_applicant_List[1].setText(cvData.get(0).getCity());

        eText_applicant_List[17].setText(cvData.get(0).getCounrty());
        eText_applicant_List[20].setText(cvData.get(0).getGender());
        eText_applicant_List[22].setText(cvData.get(0).getAddress());
        eText_applicant_List[23].setText(cvData.get(0).getTownship());


    }

    public void onClick(View view) {
        // String ph = getIntent().getExtras().getString("mobile_no");
        ph = cvData.get(0).getMobile_no();
        switch (view.getId()) {
            case R.id.cv_download_employer:
                if (cvData.get(0).getAttach_cv().equals("null")) {
                    Toast.makeText(EmployerViewApplicant.this, "No CV Form", Toast.LENGTH_SHORT).show();
                } else {

                    Check_connection check_connection = new Check_connection(EmployerViewApplicant.this);
                    con_live = check_connection.CheckWifiConnected();
                    if (con_live == 1 || con_live == 2) {


                        if (cvData.get(0).getAttach_cv().contains(".doc") || cvData.get(0).getAttach_cv().contains(".docx")) {
                            new CV_Download().execute();
                        } else {
                            Intent i1 = new Intent(EmployerViewApplicant.this, View_Web_CV.class);
                            i1.putExtra("cv_link", cvData.get(0).getAttach_cv());
                            startActivity(i1);

                        }


                    } else {
                        Toast.makeText(EmployerViewApplicant.this, "Need to turn on internet", Toast.LENGTH_SHORT).show();
                    }

                    //downloadPDF();

                    //new CV_Download().execute();
                }
                break;
            case R.id.back_employer_cv:
                Intent i = new Intent(EmployerViewApplicant.this, EmployerFragHost.class);
                i.putExtra("remain_time", remain_time);
                i.putExtra("package_type", package_Type);
                startActivity(i);
                finish();
                break;
            case R.id.callBTN:

                AlertDialog.Builder builder = new AlertDialog.Builder(EmployerViewApplicant.this);
                builder.setMessage("Call?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Call_SMS().execute();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).create().show();
                // Toast.makeText(getApplicationContext(), "PH :" + ph + " :" + ph.length(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.smsBTN:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:" + ph));
                if (!getIntent().getExtras().getString("CompanyName").isEmpty()) {
                    String compName = getIntent().getExtras().getString("CompanyName");
                    String phoneOne = getIntent().getExtras().getString("CompMobile");
                    String phoneTwo = getIntent().getExtras().getString("PrimaryMobile");
                    String smsBodyB = getResources().getString(R.string.Dear);
                    String smsBody = String.format(smsBodyB, cvData.get(0).getName(), phoneOne, phoneTwo, compName);
//                    String smsBody = getResources().getString(R.string.Dear,cvData.get(0).getName(),compName,phoneOne,phoneTwo);
                    smsIntent.putExtra("sms_body", smsBody);
                    startActivity(smsIntent);
                } else {

                }
                break;
        }
    }


    public class Call_SMS extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            pd = new ProgressDialog(EmployerViewApplicant.this);
            pd.setCancelable(false);
            pd.setMessage(getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();

        }

        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Log.e("Result", "" + s);
                Toast.makeText(EmployerViewApplicant.this, "Calling...", Toast.LENGTH_SHORT).show();
                if (ph.length() >= 9 && ph.length() <= 13) {
                    Intent inn = new Intent(Intent.ACTION_CALL);
                    inn.setData(Uri.parse("tel:" + ph));
                    if (ContextCompat.checkSelfPermission(EmployerViewApplicant.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EmployerViewApplicant.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        startActivity(inn);
                    }
                }
            } else {
                Toast.makeText(EmployerViewApplicant.this, "Lost....", Toast.LENGTH_SHORT).show();
            }
        }

        protected String doInBackground(String... voids) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("heid", EMP_ID);
                jsonObject.put("haid", userId);
                jsonObject.put("hjid", job_id);
                jsonObject.put("contact_info", "");
                jsonObject.put("description", "");

                client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                        .writeTimeout(35, TimeUnit.SECONDS)
                        .readTimeout(35, TimeUnit.SECONDS);

                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonObject));

                Request request = new Request.Builder()
                        .url(CALL_SMSM)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Transfer-Encoding", "chunked")
                        .post(body)
                        .build();

                okhttp3.Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class CV_Download extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            cv_download_employer.setEnabled(false);
            Toast.makeText(EmployerViewApplicant.this, "Start to Download...", Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(Void... arg0) {
            try {
                URL url = new URL(PDF_DOWNLOAD_LINK + cvData.get(0).getAttach_cv());//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection
/*
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .get()
                        .build();*/

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e("Download", "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());
                }

                outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cvData.get(0).getAttach_cv());//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e("File", "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                //     InputStream iss=request.
                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }
                success = "OKI";
                //Close all connection after doing task
                fos.close();
                is.close();
                return success;
            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e("Download", "Download Error Exception " + e.getMessage());
            }

            return null;
        }
        protected void onPostExecute(String s) {
            cv_download_employer.setEnabled(true);
            if (s != null) {
                Toast.makeText(EmployerViewApplicant.this, "CV Download Complete", Toast.LENGTH_SHORT).show();

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        cvData.get(0).getAttach_cv());

                if (Build.VERSION.SDK_INT == 24) {
                    path = FileProvider.getUriForFile(EmployerViewApplicant.this,
                            EmployerViewApplicant.this.getPackageName() + ".provider",
                            file);
                } else {
                    path = Uri.fromFile(file);
                }

                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pdfOpenintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


                pdfOpenintent.setDataAndType(path, "application/msword");
                try {
                    EmployerViewApplicant.this.startActivity(pdfOpenintent);
                } catch (ActivityNotFoundException e) {

                }

            } else {
                Toast.makeText(EmployerViewApplicant.this, "CV Download Fail", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public class GetApplicantData extends AsyncTask<String, Object, ArrayList<CV_Data>> {
        JSONObject c = null;

        protected void onPreExecute() {
            scroll_layout.setVisibility(View.INVISIBLE);
            cvData.clear();
            experience_data.clear();
            skill_data.clear();
            education_data.clear();
            qualification_data.clear();
        }

        protected void onPostExecute(ArrayList<CV_Data> arrayList) {
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    pb.setVisibility(View.GONE);
                    scroll_layout.setVisibility(View.VISIBLE);
                    cvFormsetText();
                }
            } else {
                new GetApplicantData().execute(userId);
            }
        }

        protected ArrayList<CV_Data> doInBackground(String... objects) {
            JSONArray cvList = null;
            JSONArray edu = null;
            JSONArray exp = null;
            JSONArray qua = null;
            JSONArray language = null;
            String url = "http://allreadymyanmar.com/api/jobseeker/" + objects[0];
            String Result = CheckConn(url);
            if (Result != null) {
                Log.e("Response", "Response From CV: " + Result);
                try {
                    JSONObject jsonObject = new JSONObject(Result);
                    cvList = jsonObject.getJSONArray("applicant");
                    c = cvList.getJSONObject(0);
                    Log.e("OBJECT", "OBJECT" + c);

                    Log.e("CV_applicant", "CV_applicant" + c.length());
                    try {
                        language = c.getJSONArray("skill");

                        Log.e("IO", "IO" + language.length());
                        for (int s = 0; s < language.length(); s++) {
                            JSONObject ss = language.getJSONObject(s);
                            String s5 = ss.getString("id");
                            String s1 = ss.getString("language");
                            String s2 = ss.getString("spoken_level");
                            String s3 = ss.getString("written_level");
                            skill_data.add(new Skill_Data(s1, s2, s3, s5));
                        }
                        Log.e("Language_Done", "Done");
                    } catch (JSONException e) {
                        Log.e("Language Error", "Error");
                    }
                    try {
                        exp = c.getJSONArray("experience");

                        for (int j = 0; j < exp.length(); j++) {
                            JSONObject e = exp.getJSONObject(j);
                            String e2 = e.getString("id");
                            String e3 = e.getString("organization");
                            String e4 = e.getString("rank");
                            String e5 = e.getString("start_date");
                            String e6 = e.getString("end_date");
                            experience_data.add(new Experience_Data(e2, e3, e4, e5, e6));
                        }
                        Log.e("Work_exp Done", "Done");
                    } catch (JSONException e) {
                        Log.e("Work_Exp_Error", "Error");
                    }

                    try {

                        qua = c.getJSONArray("qualification");


                        for (int k = 0; k < qua.length(); k++) {
                            JSONObject q = qua.getJSONObject(k);
                            String q1 = q.getString("id");
                            String q3 = q.getString("center_name");
                            String q4 = q.getString("course");
                            String q5 = q.getString("start_date");
                            String q6 = q.getString("end_date");
                            qualification_data.add(new Qualification_Data(q1, q3, q4, q5, q6));
                            Log.e("olo", "olo" + qualification_data.size() + q3 + q4 + q5 + q6);
                        }
                        Log.e("Qua_Done", "Done");
                    } catch (JSONException e) {
                        Log.e("Qua_Error", "Error");
                    }
                    //education...........
                    try {
                        edu = c.getJSONArray("education");

                        Log.e("Education", "Education" + edu.length());

                        for (int i = 0; i < edu.length(); i++) {
                            JSONObject ee = edu.getJSONObject(i);
                            String a = ee.getString("id");
                            String a1 = ee.getString("university");
                            String a2 = ee.getString("degree");
                            String a3 = ee.getString("start_date");
                            String a4 = ee.getString("end_date");
                            Log.e("String", "String" + a1 + a2);
                            education_data.add(new Education_Data(a, a1, a2, a3, a4));
                            Log.e("EDU", "EDU" + education_data.size());
                            Log.e("UNI", "UNI" + education_data.get(0).getUniversity());
                        }
                        Log.e("Edu_Done", "Done");
                    } catch (JSONException e) {
                        Log.e("Edu_Error", "Error");
                    }

                } catch (JSONException e) {
                    Log.e("Part 2", "Part 2");
                    e.printStackTrace();
                }


                try {

                    cvData.add(new CV_Data(c.getString("name"), c.getString("father_name"),
                            c.getString("marital_status"), c.getString("gender"),
                            c.getString("expected_salary"), c.getString("date_of_birth"),
                            c.getString("nrc_no"), c.getString("religion"), c.getString("place_of_birth"),
                            c.getString("mobile_no"), c.getString("email"),
                            c.getString("address"), c.getString("township"),
                            c.getString("nationality"), c.getString("city"), c.getString("country_id"),
                            c.getString("current_position"), c.getString("desired_position"),
                            c.getString("driving_license"), c.getString("created_at"),
                            c.getString("updated_at"), c.getString("photo"),
                            c.getString("attach_cv"), c.getString("country"), c.getString("id")));
                    Log.e("CV_Data Size CV", "CV" + cvData.size());

                } catch (NullPointerException e) {
                    Log.e("Null Pointer", "Null Pointer");
                    e.getMessage();
                } catch (JSONException e) {
                    Log.e("JSON Applicant Error", "Error");
                    e.printStackTrace();
                }

            }
            return cvData;
        }

        private String CheckConn(String reqUrl) {
            URL url;
            HttpURLConnection conn;
            InputStream in = null;
            try {
                url = new URL(reqUrl);
                conn = (HttpURLConnection) url.openConnection();
                Log.d("Opened Conn", "conn is okay");
                conn.setRequestMethod("GET");
                in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                return sb.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
