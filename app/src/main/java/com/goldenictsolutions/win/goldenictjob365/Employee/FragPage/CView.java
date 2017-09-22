package com.goldenictsolutions.win.goldenictjob365.Employee.FragPage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.Check_connection;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.CV_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Education_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Experience_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Qualification_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Skill_Data;

import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ModifyCvActivity;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.ImageLoader;
import com.goldenictsolutions.win.goldenictjob365.R;

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

public class CView extends Fragment implements View.OnClickListener {
    DB_Control db_control;
    Uri path;

    ArrayList<CV_Data> cvData = new ArrayList<>();
    ArrayList<Skill_Data> skill_data = new ArrayList<>();
    TextView[] eText_applicant_List = new TextView[25];
    TextView ls_uni_list, ls_qua_list, ls_work_experience_list, ls_skill_list;
    Button modify_cv_btn;
    ImageView imgView_profile;
    TextView cv_attach;
    Uri uri, selectImage;
    TextView toolbar_text;
    ArrayList<DB_USERDATA> user_id;
    String organi, rank, exp_start, exp_end;
    String uni, degree, edu_start_date, edu_end_date;
    String center, course, qua_start, qua_end;
    String language, spoken, written;

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

    ArrayList<Education_Data> education_data = new ArrayList<>();
    ArrayList<Experience_Data> experience_data = new ArrayList<>();
    ArrayList<Qualification_Data> qualification_data = new ArrayList<>();

    ImageLoader imgload;
    ScrollView scroll_layout;
    ProgressBar pb;
    SwipeRefreshLayout cvSwiper;
    ListView ls;
    GettingData getData;
    Button cv_download;
    private String PDF_DOWNLOAD_LINK = "http://allreadymyanmar.com/uploads/cv/";
    private File outputFile;
    private String success;
    CV_Download cv_download_thread;
    private int con_live;

    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        user_id = db_control.getUserid();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cv_veiw, container, false);
        finderapplicantId(v);
        getData = new GettingData();
        getData.execute(user_id);
        imgload = new ImageLoader(getContext());
        db_control.closeDb();
        return v;
    }

    private void finderapplicantId(View v) {
        scroll_layout = (ScrollView) v.findViewById(R.id.scroll_layout);
        pb = (ProgressBar) v.findViewById(R.id.progressBar);
        ls_uni_list = (TextView) v.findViewById(R.id.list_em_Edu);
        cv_download = (Button) v.findViewById(R.id.cv_download);
        ls_qua_list = (TextView) v.findViewById(R.id.em_Qualification);
        ls_work_experience_list = (TextView) v.findViewById(R.id.em_WorkingExperience);
        ls_skill_list = (TextView) v.findViewById(R.id.em_language);
        toolbar_text = (TextView) v.findViewById(R.id.toolbar_text);
        eText_applicant_List[0] = (TextView) v.findViewById(R.id.ed_salary);
        eText_applicant_List[1] = (TextView) v.findViewById(R.id.ed_city_id);
        eText_applicant_List[2] = (TextView) v.findViewById(R.id.ed_drivingLicense);
        eText_applicant_List[3] = (TextView) v.findViewById(R.id.ed_desire_position);
        eText_applicant_List[4] = (TextView) v.findViewById(R.id.ed_current_position);
        eText_applicant_List[5] = (TextView) v.findViewById(R.id.ed_marital);
        eText_applicant_List[6] = (TextView) v.findViewById(R.id.ed_religion);
        eText_applicant_List[7] = (TextView) v.findViewById(R.id.ed_national);
        eText_applicant_List[8] = (TextView) v.findViewById(R.id.emFatherName_workCode);
        eText_applicant_List[9] = (TextView) v.findViewById(R.id.emNRC_WorkCode);
        eText_applicant_List[10] = (TextView) v.findViewById(R.id.emDate);
        eText_applicant_List[11] = (TextView) v.findViewById(R.id.em_Mobile);
        eText_applicant_List[12] = (TextView) v.findViewById(R.id.ed_gmail);
        eText_applicant_List[16] = (TextView) v.findViewById(R.id.em_Name);
        eText_applicant_List[17] = (TextView) v.findViewById(R.id.countryEdit);
        eText_applicant_List[20] = (TextView) v.findViewById(R.id.genderEdit);
        eText_applicant_List[22] = (TextView) v.findViewById(R.id.addressEdit);
        eText_applicant_List[23] = (TextView) v.findViewById(R.id.township_edit);
        cv_attach = (TextView) v.findViewById(R.id.cv_attach);
        modify_cv_btn = (Button) v.findViewById(R.id.modify_cv_button);

        imgView_profile = (ImageView) v.findViewById(R.id.em_Img);
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (getData.getStatus() == AsyncTask.Status.RUNNING) {
                getData.cancel(true);

            }
        } catch (Exception e) {

        }
        try {
            if (cv_download_thread.getStatus() == AsyncTask.Status.RUNNING) {
                cv_download_thread.cancel(true);
            }

        } catch (Exception e) {

        }

    }

    public void cvFormsetText() {
        if (cvData.get(0).getPhoto() != null) {

            imgload.DisplayImage("http://allreadymyanmar.com/uploads/resume-photo/" + cvData.get(0).getPhoto(), imgView_profile);
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
            if(qua_end.equals("null")){
            qua_end_list.add("working");
                Toast.makeText(getActivity(), qua_end, Toast.LENGTH_SHORT).show();
            }
            else
            {
                qua_end_list.add(qua_end);
            }
        }
        StringBuilder sb_qua = new StringBuilder();
        for (int i = 0; i < center_list.size(); i++) {
            //sb_qua.append(center_list.get(i).concat("\t"+course_list.get(i).concat("(" + qua_start_list.get(i)).concat(")" + "\t" + "("+qua_end_list.get(i) + ")")));
            sb_qua.append((i + 1) + ")" + center_list.get(i));
            sb_qua.append("\n");
            sb_qua.append(course_list.get(i));
            sb_qua.append("\n");
            sb_qua.append(qua_start_list.get(i).concat("    ~    " + qua_end_list.get(i)));
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
        edu_start_date_list.clear();
        edu_end_date_list.clear();
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
        for (int i = 0; i < experience_data.size(); i++){
            organi = experience_data.get(i).getOrganization();
            rank = experience_data.get(i).getRank();
            exp_start = experience_data.get(i).getStart_date();
            exp_end = experience_data.get(i).getEnd_date();
            if (exp_end.equals("null")) {
                exp_end = "Currently Working";
            }
            organizatio_listn.add(organi);
            rank_data.add(rank);
            exp_start_list.add(exp_start);
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
        if (cvData.get(0).getAttach_cv().equals("null")) {
            cv_attach.setText("No CV Form");
        } else

        {
            cv_attach.setText(cvData.get(0).getAttach_cv());
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        modify_cv_btn.setOnClickListener(this);
        imgView_profile.setEnabled(false);
        cv_download.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_cv_button:
                Intent i = new Intent(getContext(), ModifyCvActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // getActivity().finish();
                startActivity(i);
                break;
            case R.id.cv_download:
                Check_connection check_connection = new Check_connection(getActivity());
                con_live = check_connection.CheckWifiConnected();
                if (con_live == 1 || con_live == 2) {
                    Log.i("hhhhh", "onClick: "+cvData.get(0).getAttach_cv()+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
                    if(cvData.get(0).getAttach_cv().toString().contains(".dox") || cvData.get(0).getAttach_cv().toString().contains("docx")){
                        Log.i("hhhhh", "onClick: "+cvData.get(0).getAttach_cv()+",,,,,,,,,,,,+++++++++++++++++++++");
                        new CV_Download().execute();
                    }
                    else{
                        new CV_Download().execute();
                        //TODO webciew_cv is not clear so i drop
//                        Intent i1 = new Intent(getContext(), View_Web_CV.class);
//                        i1.putExtra("cv_link", cvData.get(0).getAttach_cv());
//                        startActivity(i1);
//                        Log.i("hhhhh", "onClick: "+cvData.get(0).getAttach_cv()+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
//                        if(cvData.get(0).getAttach_cv().toString().contains(".dox") || cvData.get(0).getAttach_cv().toString().contains("docx")){
//                            Log.i("hhhhh", "onClick: "+cvData.get(0).getAttach_cv()+",,,,,,,,,,,,+++++++++++++++++++++");
//                            new CV_Download().execute();
                    }



                } else {
                    Toast.makeText(getActivity(), "Need to turn on internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public class CV_Download extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Start to Download...", Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(Void... arg0) {
            try {
                URL url = new URL(PDF_DOWNLOAD_LINK + cvData.get(0).getAttach_cv());//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");
                c.connect();
                outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cvData.get(0).getAttach_cv());//Create Output file in Main File
                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

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
            }

            return null;
        }

        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getActivity(), "CV Download Complete", Toast.LENGTH_SHORT).show();

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        cvData.get(0).getAttach_cv());

                if (Build.VERSION.SDK_INT == 24) {
                    path = FileProvider.getUriForFile(getContext(),
                            getContext().getPackageName() + ".provider",
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
                    getContext().startActivity(pdfOpenintent);
                } catch (ActivityNotFoundException e) {

                }

            } else {
                Toast.makeText(getActivity(), "CV Download Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

  ///////////////////////////////Download Userdata///////////////////////
    public class GettingData extends AsyncTask<Object, Object, ArrayList<CV_Data>> {
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
            scroll_layout.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    cvFormsetText();
                }
            } else {
                cvSwiper.setEnabled(true);
            }
        }

        protected ArrayList<CV_Data> doInBackground(Object... objects) {
            JSONArray cvList = null;
            JSONArray edu = null;
            JSONArray exp = null;
            JSONArray qua = null;
            JSONArray language = null;
            String url = "http://allreadymyanmar.com/api/jobseeker/" + user_id.get(0).getUser_id();
            String Result = CheckConn(url);
            if (Result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(Result);
                    cvList = jsonObject.getJSONArray("applicant");
                    c = cvList.getJSONObject(0);
                    try {
                        language = c.getJSONArray("skill");

                        for (int s = 0; s < language.length(); s++) {
                            JSONObject ss = language.getJSONObject(s);
                            String s5 = ss.getString("id");
                            String s1 = ss.getString("language");
                            String s2 = ss.getString("spoken_level");
                            String s3 = ss.getString("written_level");
                            skill_data.add(new Skill_Data(s1, s2, s3, s5));
                        }
                    } catch (JSONException e) {
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
                    } catch (JSONException e) {
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
                        }
                    } catch (JSONException e) {
                    }
                    //education...........
                    try {
                        edu = c.getJSONArray("education");


                        for (int i = 0; i < edu.length(); i++) {
                            JSONObject ee = edu.getJSONObject(i);
                            String a = ee.getString("id");
                            String a1 = ee.getString("university");
                            String a2 = ee.getString("degree");
                            String a3 = ee.getString("start_date");
                            String a4 = ee.getString("end_date");
                            education_data.add(new Education_Data(a, a1, a2, a3, a4));
                        }
                    } catch (JSONException e) {
                    }

                } catch (JSONException e) {
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
                            c.getString("attach_cv"), c.getString("country"), c.getString("id"), c.getString("cv_views")));

                } catch (NullPointerException e) {
                    e.getMessage();
                } catch (JSONException e) {
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
