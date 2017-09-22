package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.goldenictsolutions.win.goldenictjob365.BuildConfig;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_Edu;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_Language;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_List_Qua;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_List_Work;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.SpinnerCustomAdapterOne;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.SpinnerCustomAdapterTownship;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.CV_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Education_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Experience_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Job_Data_Model;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Qualification_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Skill_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.Helper.PathToRealPathHelper;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadCVEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Education_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Exp_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Language_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Qualification_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.LogoLoader.Image_Download_CV;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Education;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Experience;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.JobCategory;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Location;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.OtherQualification;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Skill;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Township;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddEducation_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddLanguage_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddQua_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddWork_Exp_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.CityErrorEvent;
//import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadPhotoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadPhotoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.JobErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerApplicantEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerCityEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEducationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerExperienceEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetEducationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetExperienceEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetQualificationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetSkillEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerJobCategoryEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerOtherQualificationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerSkillEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerTownshipEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUpLoadCVEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadResumeEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.TownshipErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.otto.Subscribe;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ModifyCvActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    DB_Control db_control;
    TextView question_hide;
    RadioButton cv_view_yes, cv_view_no;
    ArrayList<Job_Data_Model> job_list = new ArrayList<>();
    RadioGroup rgp_check_driving_license;
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
    ArrayList<Skill_Data> skill_data = new ArrayList<>();
    ArrayList<CV_Data> cv_data = new ArrayList<>();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String imagePath, cvPath;
    Image_Download_CV img_load;

    String[] marital_status;
    ProgressBar pb;
    RelativeLayout modify_layout;
    // directory name to store captured images and videos
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private static TextView editTextDateOfBirth, textViewEduYear, textViewStartDate, textViewEndDate, otherStartDate, otherEndDate;
    private EditText EduUniversity, EduDegree;
    MaterialSpinner spinnerDesirePosition;
    private MaterialSpinner spinnerReligion, spinnerMaritalStatus;
    MaterialSpinner spinnerTownship;
    Spinner spinnerStateDivision;
    public ProgressDialog pdAddSkill, pdAddEdu, pdAddExp, pdAddQua, pdUploadCV;
    private Button btnAddEducation, btnPostEducation, btnAddWorkingExperience, btnAddOtherQualification, btnSubmit, btnAddLanguage, btnUploadCv;
    private String token;
    private String userId;
    private ProgressDialog pdUploadPhoto;
    private String gender;

    private Communicator communicator;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_CV = 300;
    //final Context context = this;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 1;
    TextView attach_cv_textview_modify;
    CheckBox currently_working;
    public String expStartDate, expEndDate, expOrganization, expRank;
    public String edu_start_Year, eduUniversity, eduDegree, edu_end_year;
    public String centerName, coursee, othStartDate, othEndDate;
    public String language_type, spoken_Level, written_Level;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "JobReady 365";

    public Uri fileUri; // file url to store image/video

    // LogCat tag
    RadioGroup hidev;
    public List<Location> cityList;
    public List<Township> townShipWithId;
    private int chkDriving = 0;
    private String maritalStatus;
    private String applicantId;
    private List<Skill> skillList;
    private List<Education> educationList;
    private List<Experience> experienceList;
    public static List<Township> townshipList;
    public static List<JobCategory> jobCategories;
    private List<OtherQualification> qualificationList;
    private int cityId;
    private String desirePosition;
    private int townshipId;
    private String religion;
    private String cvViews;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private List<OtherQualification> otherQualification;
    private int u_type;
    String user_name, password;
    private String city;
    String language, spoken, written;
    String organi, rank, exp_start, exp_end;
    String uni, degree, edu_start_date, edu_end_date;
    String center, course, qua_start, qua_end;
    private ArrayList<DB_USERDATA> user_Id;
    String lolz_gender;


    LinearLayout maleFeamelLayout, uKoLayout, maDawLayout, showEdit;
    TextView PreName;
    RadioButton dl_yes, dl_no;

    private EditText edtMobileNr, edtEmail, edtFatherName, edtNationality, edtCurrentPosition, edtExpSalary, edtAddress, fullName;
    private ImageView modify_img_photo;
    private EditText editText_nrc;
    private ListView work_ls, language_ls, edu_ls, qualification_ls;
    private String work_id, edu_id, qua_id, language_id;

    ArrayList<String> edu_start_date_list = new ArrayList<>();
    ArrayList<String> edu_end_date_list = new ArrayList<>();
    FetchData fetchData;

    ArrayList<String> edu_id_list = new ArrayList<>();
    ArrayList<String> exp_id_list = new ArrayList<>();
    ArrayList<String> qua_id_list = new ArrayList<>();
    ArrayList<String> language_id_list = new ArrayList<>();
    public static TextView textViewEduYear_end, textViewEduYear_start;

    public String name, fatherName, dob, pob, nationality, nrc, mobileNr, email, expectedSalary, address, current_position;
    private boolean noOrYes = false;
    private String mCurrentPhotoPath;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_cv);
        hidev = (RadioGroup) findViewById(R.id.hidev);
        rgp_check_driving_license = (RadioGroup) findViewById(R.id.rgp_check_driving_license);
        maDawLayout = (LinearLayout) findViewById(R.id.maDawLayout);
        uKoLayout = (LinearLayout) findViewById(R.id.uKoLayout);
        maleFeamelLayout = (LinearLayout) findViewById(R.id.maleFemaleLayout);
        fullName = (EditText) findViewById(R.id.et_fullname);
        showEdit = (LinearLayout) findViewById(R.id.showEdit);
        PreName = (TextView) findViewById(R.id.preName);
        attach_cv_textview_modify = (TextView) findViewById(R.id.attach_cv_textview_modify);
        //deFault();
        cv_view_yes = (RadioButton) findViewById(R.id.hide_cv_yes);
        cv_view_no = (RadioButton) findViewById(R.id.hide_cv_no);
        hidev.setOnCheckedChangeListener(this);
        rgp_check_driving_license.setOnCheckedChangeListener(this);
        db_control = new DB_Control(this);
        dialog = new Dialog(this);
        //token = JLoginActivity.token;
        communicator = new Communicator();
        img_load = new Image_Download_CV(getApplicationContext());
        db_control.openDb();
        user_Id = db_control.getUserid();
        userId = user_Id.get(0).getUser_id();
        db_control.closeDb();

        dl_yes = (RadioButton) findViewById(R.id.driving_license_yes);
        dl_no = (RadioButton) findViewById(R.id.driving_license_no);
        editTextDateOfBirth = (TextView) findViewById(R.id.editText_dob1);
        editText_nrc = (EditText) findViewById(R.id.et_nrc_no);
        edtFatherName = (EditText) findViewById(R.id.et_father_name);
        edtNationality = (EditText) findViewById(R.id.et_nationality);
        edtMobileNr = (EditText) findViewById(R.id.et_contact_number);
        edtEmail = (EditText) findViewById(R.id.et_email);
        edtExpSalary = (EditText) findViewById(R.id.et_expected_salary);
        edtAddress = (EditText) findViewById(R.id.et_address);
        edtCurrentPosition = (EditText) findViewById(R.id.et_current_position);


        // here pob place of birth
        work_ls = (ListView) findViewById(R.id.work_list);
        language_ls = (ListView) findViewById(R.id.language_list);
        qualification_ls = (ListView) findViewById(R.id.qualification_list);
        edu_ls = (ListView) findViewById(R.id.edu_list);

        btnUploadCv = (Button) findViewById(R.id.btn_cv_upload_modify);
        modify_img_photo = (ImageView) findViewById(R.id.modify_img_photo);
        question_hide = (TextView) findViewById(R.id.hide_cv_fromemp);
        question_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ModifyCvActivity.this);

                // set title
                // set dialog message
                alertDialogBuilder
                        .setMessage(R.string.hide_cv_extension)
                        .setCancelable(false)

                        .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        spinnerTownship = (MaterialSpinner) findViewById(R.id.township_spinner_modify);
        spinnerDesirePosition = (MaterialSpinner) findViewById(R.id.desire_position_spinner_modify);
        spinnerStateDivision = (Spinner) findViewById(R.id.city_spinner_modify);

        spinnerReligion = (MaterialSpinner) findViewById(R.id.spinner_religion_modify);
        spinnerMaritalStatus = (MaterialSpinner) findViewById(R.id.spinner_marital_status_modify);

        modify_layout = (RelativeLayout) findViewById(R.id.modify_layout);
        pb = (ProgressBar) findViewById(R.id.progressBBar);

        ArrayAdapter<String> adapterReligion = new ArrayAdapter<String>(this, R.layout.custom_layout_for_spinner, getResources().getStringArray(R.array.spin_religion));
        adapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReligion.setAdapter(adapterReligion);

        marital_status = getResources().getStringArray(R.array.spin_marital_status);
        ArrayAdapter customAdapter11 = new ArrayAdapter(ModifyCvActivity.this, R.layout.custom_layout_for_spinner, marital_status);
        customAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalStatus.setAdapter(customAdapter11);
        btnUploadCv.setOnClickListener(this);
        fetchData = new FetchData();
        fetchData.execute(userId);

        spinnerTownship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    townshipId = townshipList.get(position).getId();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDesirePosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    desirePosition = null;
                } else {
                    desirePosition = jobCategories.get(position).getCategory();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                desirePosition = null;
            }
        });

        spinnerStateDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    cityId = cityList.get(position).getId();
                    useGetTownshipById(cityId);
                } catch (Exception e) {
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                religion = (String) spinnerReligion.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalStatus = (String) spinnerMaritalStatus.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit = (Button) findViewById(R.id.Submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = PreName.getText().toString() + fullName.getText().toString();

                fatherName = edtFatherName.getText().toString();
                dob = editTextDateOfBirth.getText().toString();
                pob = "Myanmar";
                lolz_gender = gender;
                nationality = edtNationality.getText().toString();
                nrc = editText_nrc.getText().toString();
                mobileNr = edtMobileNr.getText().toString();
                email = edtEmail.getText().toString();
                expectedSalary = edtExpSalary.getText().toString();
                address = edtAddress.getText().toString();
                current_position = edtCurrentPosition.getText().toString();

                if (rgp_check_driving_license.getCheckedRadioButtonId() == R.id.driving_license_no) {
                    chkDriving = 0;
                } else if (rgp_check_driving_license.getCheckedRadioButtonId() == R.id.driving_license_yes) {
                    chkDriving = 1;
                }

                if (hidev.getCheckedRadioButtonId() == R.id.hide_cv_yes) {
                    cvViews = "1";
                } else if (hidev.getCheckedRadioButtonId() == R.id.hide_cv_no) {
                    cvViews = "0";
                }

                if (fullName.getText().toString().isEmpty()
                        || edtFatherName.getText().toString().isEmpty()
                        || editTextDateOfBirth.getText().toString().isEmpty()
                        || editText_nrc.getText().toString().isEmpty()
                        || edtMobileNr.getText().toString().isEmpty()
                        || edtExpSalary.getText().toString().isEmpty()
                        || edtNationality.getText().toString().isEmpty()
                        || edtAddress.getText().toString().isEmpty()
                        || desirePosition == null) {

                    //Toast.makeText(ModifyCvActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                    Toast.makeText(ModifyCvActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                } else {


                    useUpdateApplicantById(applicantId, lolz_gender, cvViews, name, fatherName, dob
                            , pob, maritalStatus, nationality, religion, nrc, mobileNr, email, expectedSalary, address
                            , String.valueOf(townshipId), String.valueOf(cityId), current_position, desirePosition, chkDriving,
                            token);
                    progressDialog = new ProgressDialog(ModifyCvActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }
        });

        btnAddEducation = (Button) findViewById(R.id.btn_add_education);
        btnAddOtherQualification = (Button) findViewById(R.id.btn_add_qualification);
        btnAddWorkingExperience = (Button) findViewById(R.id.btn_add_work_exp);
        btnAddLanguage = (Button) findViewById(R.id.btn_add_language);
        btnAddLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialog_skill);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.Language));
                final EditText EditTextLanguage = (EditText) dialog.findViewById(R.id.edittext_language_);
                final EditText EditTextLanguageLevel = (EditText) dialog.findViewById(R.id.edittext_language_level);
                final EditText EditTextLanguagewLevel = (EditText) dialog.findViewById(R.id.edittext_language_wlevel);

                Button btnPostLanguage = (Button) dialog.findViewById(R.id.btn_post_language);
                btnPostLanguage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        language_type = EditTextLanguage.getText().toString();
                        spoken_Level = EditTextLanguageLevel.getText().toString();
                        written_Level = EditTextLanguagewLevel.getText().toString();
                        useInsertSkill(userId, language_type, spoken_Level, written_Level, token);
                        //useGetSkillById(userId, token);
                        pdAddSkill = new ProgressDialog(ModifyCvActivity.this);
                        pdAddSkill.setMessage(getResources().getString(R.string.loading));
                        pdAddSkill.setCancelable(false);
                        pdAddSkill.show();

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_dismiss_language);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


            }
        });

        btnAddWorkingExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create custom dialog object
                dialog.setContentView(R.layout.dialog_experience);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.post_work_exp));
                final EditText EditTextexpOrganization = (EditText) dialog.findViewById(R.id.editText_organization);
                final EditText EditTextexpRank = (EditText) dialog.findViewById(R.id.edittext_rank);
                textViewStartDate = (TextView) dialog.findViewById(R.id.textView_exp_start_date);
                textViewEndDate = (TextView) dialog.findViewById(R.id.textView_exp_end_date);
                currently_working = (CheckBox) dialog.findViewById(R.id.current_working);
                btnPostEducation = (Button) dialog.findViewById(R.id.btn_post_exp);

                currently_working.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            textViewEndDate.setText("");
                            textViewEndDate.setHint(getResources().getString(R.string.Current_working));
                            textViewEndDate.setEnabled(false);
                        } else {
                            textViewEndDate.setHint(getResources().getString(R.string.end_date));
                            textViewEndDate.setEnabled(true);
                        }
                    }
                });
                btnPostEducation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        expStartDate = textViewStartDate.getText().toString();
                        expEndDate = textViewEndDate.getText().toString();
                        expOrganization = EditTextexpOrganization.getText().toString();
                        expRank = EditTextexpRank.getText().toString();
                        if (currently_working.isChecked()
                                || !textViewEndDate.getText().toString().isEmpty()) {

                            pdAddExp = new ProgressDialog(ModifyCvActivity.this);
                            pdAddExp.setMessage(getResources().getString(R.string.loading));
                            pdAddExp.setCancelable(false);
                            pdAddExp.show();
                            useInsertExperience(userId, expOrganization, expRank, expStartDate, expEndDate, token);
                        } else {
                            Toast.makeText(ModifyCvActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                        }
                        //   useGetExperienceById(userId, token);


                    }
                });
                TextView textViewStartDate = (TextView) dialog.findViewById(R.id.textView_exp_start_date);
                textViewStartDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new ExperienceStartDatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });

                TextView textViewEndDate = (TextView) dialog.findViewById(R.id.textView_exp_end_date);
                textViewEndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new ExperienceEndDatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_dismiss_exp);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                //  Toast.makeText(getApplicationContext(), "sldkfjlkdsjf", Toast.LENGTH_LONG).show();

            }


        });
        btnAddOtherQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Create custom dialog object


                dialog.setContentView(R.layout.dialog_other_qualification);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.resume_qualification));
                final EditText otherCenterName = (EditText) dialog.findViewById(R.id.editText_other_center_name);
                final EditText otherCourse = (EditText) dialog.findViewById(R.id.edittext_other_course);
                otherStartDate = (TextView) dialog.findViewById(R.id.textView_other_start_date);
                otherEndDate = (TextView) dialog.findViewById(R.id.textView_other_end_date);

                btnPostEducation = (Button) dialog.findViewById(R.id.btn_post_qualification);
                btnPostEducation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        centerName = otherCenterName.getText().toString();
                        coursee = otherCourse.getText().toString();
                        othStartDate = otherStartDate.getText().toString();
                        othEndDate = otherEndDate.getText().toString();
                        useInsertQualification(userId, centerName, coursee, othStartDate, othEndDate, token);
                        // useGetQualificationById(userId, token);

                        pdAddQua = new ProgressDialog(ModifyCvActivity.this);
                        pdAddQua.setMessage(getResources().getString(R.string.loading));
                        pdAddQua.setCancelable(false);
                        pdAddQua.show();
                        // Close dialog
                        // dialog.dismiss();

                    }
                });


                otherStartDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new OtherStartDateYearPickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });
                otherEndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new OtherEndDatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_dismiss_qualification);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


            }


        });


        btnAddEducation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create custom dialog object


                dialog.setContentView(R.layout.dialog_education);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.education));
                EduUniversity = (EditText) dialog.findViewById(R.id.editText_university);
                EduDegree = (EditText) dialog.findViewById(R.id.edittext_degree);

                btnPostEducation = (Button) dialog.findViewById(R.id.btn_post_edu);
                btnPostEducation.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        edu_start_Year = textViewEduYear.getText().toString();
                        eduUniversity = EduUniversity.getText().toString();
                        eduDegree = EduDegree.getText().toString();
                        edu_end_year = textViewEduYear_end.getText().toString();
                        if (textViewEduYear.getText().toString().isEmpty()
                                || EduUniversity.getText().toString().isEmpty()
                                || EduDegree.getText().toString().isEmpty()
                                || textViewEduYear_end.getText().toString().isEmpty()) {
                            Toast.makeText(ModifyCvActivity.this, "Fill All Field", Toast.LENGTH_SHORT).show();
                        } else {
                            pdAddEdu = new ProgressDialog(ModifyCvActivity.this);
                            pdAddEdu.setMessage(getResources().getString(R.string.loading));
                            pdAddEdu.setCancelable(false);
                            pdAddEdu.show();
                            useInsertEducation(userId, eduUniversity, eduDegree, edu_start_Year, edu_end_year, token);
                        }
                        // useGetEducationById(userId, token);
                        //  dialog.hide();


                        // Close dialog
                        // dialog.dismiss();

                    }
                });
                textViewEduYear = (TextView) dialog.findViewById(R.id.textView_edu_start_year);
                textViewEduYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new EducationYearPickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });

                textViewEduYear_end = (TextView) dialog.findViewById(R.id.textView_edu_end_year);
                textViewEduYear_end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new EducationYearPickerFragment2();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btnDismiss);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                textViewEduYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new EducationYearPickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");

                    }
                });


                dialog.show();

                //      Button declineButton = (Button) dialog.findViewById(R.id.btnDismiss);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        modify_img_photo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                ImageView image = new ImageView(ModifyCvActivity.this);
                //   image.setImageResource(R.drawable.logo);

                Glide.with(ModifyCvActivity.this)

                        .load(R.drawable.true_logo)
                        .into(image);
                Dialog d = new AlertDialog.Builder(ModifyCvActivity.this, AlertDialog.THEME_HOLO_LIGHT)

                        .setIcon(R.drawable.icon_and_button_01)

                        .setTitle("Take your Photo")
                        .setNegativeButton("Cancel", null)

                        .setItems(new String[]{"Take new photo", "Choose photo from Galaxy", "", ""}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int position) {
                                if (position == 0) {
                                    //  dispatchTakePictureIntent();
                                    verifyStoragePermissions(ModifyCvActivity.this);
                                    try {
                                        captureImage(getApplicationContext(), ModifyCvActivity.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (position == 1) {
                                    verifyStoragePermissions(ModifyCvActivity.this);
                                    showImagePopup(getApplicationContext());
                                }
                            }
                        })
                        .create();
                d.show();

            }


        });


        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DateofBirthPickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void setTextFunction() {
        modify_layout.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        if (cv_data.get(0).getCv_views().equals("1")) {
            cv_view_no.setChecked(true);
            cvViews = "1";
        } else if (cv_data.get(0).getCv_views().equals("0")) {
            cv_view_yes.setChecked(true);
            cvViews = "0";
        }

        if (cv_data.get(0).getAttach_cv().equals("null")) {
            attach_cv_textview_modify.setText("No CV Form");
        } else {
            attach_cv_textview_modify.setText("CV Received");
        }
        if (cv_data.get(0).getCv_views().equals("1")) {

        }

        applicantId = cv_data.get(0).getApp_id();
        if (cv_data.get(0).getPhoto() != null) {
            Glide
                    .with(ModifyCvActivity.this)
                    .load("http://allreadymyanmar.com/uploads/resume-photo/" + cv_data.get(0).getPhoto())
                    .placeholder(R.drawable.icon_and_button_01)
                    .into(modify_img_photo);
        } else if (cv_data.get(0).getPhoto().equals("null")) {
            Glide
                    .with(ModifyCvActivity.this)
                    .load(R.drawable.icon_and_button_01)
                    .into(modify_img_photo);
        }
        PreName.setVisibility(View.VISIBLE);
        maleFeamelLayout.setVisibility(View.GONE);
        uKoLayout.setVisibility(View.GONE);
        maDawLayout.setVisibility(View.GONE);
        showEdit.setVisibility(View.VISIBLE);
        gender = cv_data.get(0).getGender();
        qua_id_list.clear();
        center_list.clear();
        course_list.clear();
        qua_end_list.clear();
        qua_start_list.clear();

        for (int i = qualification_data.size() - 1; i >= 0; i--) {
            qua_id = qualification_data.get(i).getQua_id();
            center = qualification_data.get(i).getCenter_name();
            course = qualification_data.get(i).getCourse();
            qua_start = qualification_data.get(i).getStart_date();
            qua_end = qualification_data.get(i).getEnd_date();

            qua_id_list.add(qua_id);
            center_list.add(center);
            course_list.add(course);
            qua_start_list.add(qua_start);
            qua_end_list.add(qua_end);
        }
        CustomAdapter_List_Qua qualification_Adapter = new CustomAdapter_List_Qua(ModifyCvActivity.this,
                center_list, course_list, qua_start_list, qua_end_list, qua_id_list);
        qualification_ls.setAdapter(qualification_Adapter);

        //skill
        language_id_list.clear();
        language_list.clear();
        spoken_list.clear();
        written_list.clear();
        for (int s = skill_data.size() - 1; s >= 0; s--) {
            language = skill_data.get(s).getLanguage();
            language_id = skill_data.get(s).getLanguage_id();
            spoken = skill_data.get(s).getSpoken();
            written = skill_data.get(s).getWritten();
            language_list.add(language);
            spoken_list.add(spoken);
            language_id_list.add(language_id);
            written_list.add(written);
        }

        CustomAdapter_Language language_adapter = new CustomAdapter_Language(ModifyCvActivity.this, language_list, spoken_list, written_list, language_id_list);
        language_ls.setAdapter(language_adapter);

        uni_list.clear();
        degree_list.clear();
        edu_start_date_list.clear();
        edu_end_date_list.clear();
        for (int i = education_data.size() - 1; i >= 0; i--) {
            uni = education_data.get(i).getUniversity();
            degree = education_data.get(i).getDegree();
            edu_id = education_data.get(i).getEdu_id();
            edu_start_date = education_data.get(i).getEdu_start_date();
            edu_end_date = education_data.get(i).getEdu_end_date();
            uni_list.add(uni);
            degree_list.add(degree);
            edu_id_list.add(edu_id);
            edu_start_date_list.add(edu_start_date);
            edu_end_date_list.add(edu_end_date);

        }

        CustomAdapter_Edu edu_adapter = new CustomAdapter_Edu(ModifyCvActivity.this, uni_list, degree_list, edu_start_date_list, edu_end_date_list, edu_id_list, userId);
        edu_ls.setAdapter(edu_adapter);


        edu_ls.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        work_ls.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        language_ls.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        qualification_ls.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        //experience set Text
        organizatio_listn.clear();
        rank_data.clear();
        exp_start_list.clear();
        exp_end_list.clear();
        for (int i = experience_data.size() - 1; i >= 0; i--) {

            work_id = experience_data.get(i).getExp_id();
            organi = experience_data.get(i).getOrganization();
            rank = experience_data.get(i).getRank();
            exp_start = experience_data.get(i).getStart_date();
            exp_end = experience_data.get(i).getEnd_date();
            if (exp_end.equals("null")) {
                exp_end = "Currently Working";
            }

            exp_id_list.add(work_id);
            organizatio_listn.add(organi);
            rank_data.add(rank);
            exp_start_list.add(exp_start);
            exp_end_list.add(exp_end);

        }
        CustomAdapter_List_Work work_adapter = new CustomAdapter_List_Work(ModifyCvActivity.this, organizatio_listn, rank_data, exp_start_list, exp_end_list, exp_id_list);
        //    work_ls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rank_data.size() * 300));
        work_ls.setAdapter(work_adapter);


        maritalStatus = cv_data.get(0).getMatrtal_status();
        switch (maritalStatus.toString()) {
            case "Single":
                spinnerMaritalStatus.setSelection(1);
                break;
            case "Married":
                spinnerMaritalStatus.setSelection(2);
                break;
            case "In Relationship":
                spinnerMaritalStatus.setSelection(3);
                break;
            case "Other":
                spinnerMaritalStatus.setSelection(4);
                break;

            default:
                spinnerMaritalStatus.setSelection(0);
                break;
        }

        religion = cv_data.get(0).getReligion();
        switch (religion.toString()) {
            case "Buddhist":
                spinnerReligion.setSelection(1);
                break;
            case "Christian":
                spinnerReligion.setSelection(2);
                break;
            case "Islamic":
                spinnerReligion.setSelection(3);
                break;
            case "Freethinker":
                spinnerReligion.setSelection(4);
            case "Hindu":
                spinnerReligion.setSelection(5);
                break;
        }
        switch (religion.toString()) {
            case "ဗုဒၶဘာသာ":
                spinnerReligion.setSelection(1);
                break;
            case "ခရစ္ယာန္":
                spinnerReligion.setSelection(2);
                break;
            case "အစၥလာမ္":
                spinnerReligion.setSelection(3);
                break;
            case "အျခား":
                spinnerReligion.setSelection(4);
                break;
            case "ဟိနၵဴ":
                spinnerReligion.setSelection(5);
                break;
        }
        fullName.setText(cv_data.get(0).getName());

        if (!cv_data.get(0).getName().equals("null")) {
            if (cv_data.get(0).getName().equals("Female")) {
                maleFeamelLayout.setVisibility(View.GONE);
                uKoLayout.setVisibility(View.GONE);
                fullName.setText(cv_data.get(0).getName());
            } else if (cv_data.get(0).getName().equals("Male")) {
                maleFeamelLayout.setVisibility(View.GONE);
                maDawLayout.setVisibility(View.GONE);
                fullName.setText(cv_data.get(0).getName());
            }
        } else {
            maleFeamelLayout.setVisibility(View.VISIBLE);
            uKoLayout.setVisibility(View.INVISIBLE);
            maDawLayout.setVisibility(View.INVISIBLE);
            showEdit.setVisibility(View.INVISIBLE);
        }

        if (cv_data.get(0).getDriving_license().equals("1")) {
            if (dl_no.isChecked()) {
                dl_no.setChecked(false);
            }
            dl_yes.setChecked(true);
        } else {
            if (dl_yes.isChecked()) {
                dl_yes.setChecked(false);
            }
            dl_no.setChecked(true);
        }

        edtFatherName.setText(cv_data.get(0).getFather_name());

        editText_nrc.setText(cv_data.get(0).getNrc_no());

        edtAddress.setText(cv_data.get(0).getAddress());
        edtCurrentPosition.setText(cv_data.get(0).getCurrent_position());
        edtEmail.setText(cv_data.get(0).getEmail());
        edtExpSalary.setText(cv_data.get(0).getExpected_salary());
        edtMobileNr.setText(cv_data.get(0).getMobile_no());
        edtNationality.setText(cv_data.get(0).getNationality());
        gender = cv_data.get(0).getGender();
        editTextDateOfBirth.setText(cv_data.get(0).getDate_of_birth());
    }


    private void useUpdateApplicantById(String Id, String gender, String cvViews, String name, String fatherName,
                                        String dateOfBirth, String pob, String maritalStatus, String nationality,
                                        String religion, String nrc,
                                        String mobileNo, String email, String expectedSalary, String address, String township
            , String city, String current_position, String desired_position, int chkDriving, String apiKey) {
        //Toast.makeText(this, "FUck Way out"+gender, Toast.LENGTH_SHORT).show();
        communicator.updateApplicantById(Id, gender, cvViews, name, fatherName, dateOfBirth, pob, maritalStatus, nationality
                , religion, nrc, mobileNo, email, expectedSalary, address, township, city, current_position
                , desired_position, chkDriving, apiKey);


    }

    @Subscribe
    public void UpdateEduErrorEvent(Update_Education_Error_Event event) {
        Toast.makeText(ModifyCvActivity.this, "Update Fail", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void UpdateQuaErrorEvent(Update_Qualification_Error_Event event) {
        Toast.makeText(ModifyCvActivity.this, "Update Fail", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void UpdateLanguageErrorEvent(Update_Language_Error_Event event) {
        Toast.makeText(ModifyCvActivity.this, "Update Fail", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void UpdateExpErrorEvent(Update_Exp_Error_Event event) {
        Toast.makeText(ModifyCvActivity.this, "Update Fail ", Toast.LENGTH_SHORT).show();
    }

    private void useUploadResumePhoto(String userId, String uri, String token) {
        communicator.uploadResumePhoto(userId, uri, token);
    }

    private void useUploadCv(String userId, String uri, String token) {
        communicator.uploadCV(userId, uri, token);
    }

    private void useGetSkillById(String userId, String token) {
        communicator.getSkillById(userId, token);
    }

    private void useGetEducationById(String userId, String token) {
        communicator.getEducationById(userId, token);
    }

    private void useGetExperienceById(String userId, String token) {
        communicator.getExperienceById(userId, token);
    }

    private void useGetQualificationById(String userId, String token) {
        communicator.getQualificationById(userId, token);
    }


    private void useInsertSkill(String userId, String skillType, String skillLevel, String skillWLevel, String token) {
        communicator.insertSkill(userId, skillType, skillLevel, skillWLevel, token);
    }

    private void useInsertExperience(String userId, String expOrganizatioon, String expRank, String expStartedDate, String expEndDate, String token) {
        communicator.insertExperience(userId, expOrganizatioon, expRank, expStartedDate, expEndDate, token);
    }

    private void useInsertEducation(String userId, String university, String degree, String start_year, String end_year, String token) {
        communicator.insertEducation(userId, university, degree, start_year, end_year, token);
    }

    private void useInsertQualification(String userId, String centerName, String course, String sDate, String eDate, String token) {
        communicator.insertOtherQualification(userId, centerName, course, sDate, eDate, token);
    }


    private void useGetCityById() {

        communicator.getCityById();
    }

    private void useGetTownship() {

        communicator.getTownship();
    }

    private void useGetJobCategory() {

        communicator.getJobCategory();
    }

    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (fetchData.getStatus() == AsyncTask.Status.RUNNING) {
            try {
                fetchData.execute(false);
            } catch (Exception e) {

            }
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void City_Error_Event(CityErrorEvent event) {
        useGetCityById();
    }

    @Subscribe
    public void onServerOtherQualificationEvent(ServerOtherQualificationEvent serverOtherQualificationEvent) {

        otherQualification = serverOtherQualificationEvent.getServerResponse().getOtherQualificationList();

        dialog.dismiss();
    }

    @Subscribe
    public void onServerApplicantEvent(ServerApplicantEvent serverApplicantEvent) {


        if (serverApplicantEvent.getServerResponse().getResult().equals("Success")) {

            Toast.makeText(ModifyCvActivity.this, "" + serverApplicantEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            Intent i = new Intent(this, ContentHost.class);
            i.putExtra("remain_time", ContentHost.remain_time);
            startActivity(i);
            finish();

        } else {
            Toast.makeText(ModifyCvActivity.this, "" + serverApplicantEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @Subscribe
    public void onSeverExperienceEvent(ServerExperienceEvent serverExperienceEvent) {
        if (serverExperienceEvent.getServerResponse().getResult().equals("Success")) {

            pdAddExp.cancel();
            pdAddExp.dismiss();
            dialog.dismiss();
            useGetExperienceById(userId, token);
            Toast.makeText(ModifyCvActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            pdAddExp.cancel();
            pdAddExp.dismiss();
            Toast.makeText(ModifyCvActivity.this, "" + serverExperienceEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }


    }

    @Subscribe
    public void onSeverEducationEvent(ServerEducationEvent serverEducationEvent) {
        if (serverEducationEvent.getServerResponse().getResult().equals("Success")) {


            pdAddEdu.cancel();
            pdAddEdu.dismiss();
            dialog.dismiss();

            useGetEducationById(userId, token);
            Toast.makeText(ModifyCvActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            pdAddEdu.cancel();
            pdAddEdu.dismiss();
            Toast.makeText(ModifyCvActivity.this, "" + serverEducationEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onSeverSkillEvent(ServerSkillEvent serverSkillEvent) {
        if (serverSkillEvent.getServerResponse().getStatusCode() == 200) {
            pdAddSkill.cancel();
            pdAddSkill.dismiss();
            dialog.dismiss();
            useGetSkillById(userId, token);
            Toast.makeText(ModifyCvActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSeverQuaEvent(ServerOtherQualificationEvent serverOtherQualificationEvent) {
        if (serverOtherQualificationEvent.getServerResponse().getResult().equals("Success")) {
            //  experience = serverExperienceEvent.getServerResponse().getExperienceList().get(0);
            //  progressDialog.hide();
            pdAddQua.dismiss();
            dialog.dismiss();
            useGetQualificationById(userId, token);
            Toast.makeText(ModifyCvActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            pdAddQua.cancel();
            pdAddQua.dismiss();
            dialog.show();
            Toast.makeText(ModifyCvActivity.this, "" + serverOtherQualificationEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSeverAddEdu_Error_Event(AddEducation_Error_Event event) {
        pdAddEdu.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Retry", Toast.LENGTH_SHORT).show();
        // useInsertEducation(userId, eduUniversity, eduDegree, edu_start_Year, edu_end_year, token);
    }

    @Subscribe
    public void onSeverAddQua_Error_Event(AddQua_Error_Event event) {
        pdAddQua.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Retry", Toast.LENGTH_SHORT).show();
        //useInsertQualification(userId, centerName, coursee, othStartDate, othEndDate, token);
    }


    @Subscribe
    public void onSeverAddWork_Exp_Error_Event(AddWork_Exp_Error_Event event) {
        pdAddExp.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Retry", Toast.LENGTH_SHORT).show();
        // useInsertExperience(userId, expOrganization, expRank, expStartDate, expEndDate, token);
    }

    @Subscribe
    public void onSeverAddLanguage_Error_Event(AddLanguage_Error_Event event) {
        pdAddSkill.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Retry", Toast.LENGTH_SHORT).show();
        // useInsertSkill(userId, language_type, spoken_Level, written_Level, token);
    }


    @Subscribe
    public void onSeverEvent(ServerEvent serverEvent) {


        Toast.makeText(ModifyCvActivity.this, "Sever Event", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void errorUploadCVEvent(ErrorUploadCVEvent event) {
        pdUploadCV.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Fail...", Toast.LENGTH_SHORT).show();

    }

    @Subscribe
    public void onUpdateError_Event(Update_Error_Event event) {
        useUpdateApplicantById(applicantId, lolz_gender, cvViews, name, fatherName, dob
                , pob, maritalStatus, nationality, religion, nrc, mobileNr, email, expectedSalary, address
                , String.valueOf(townshipId), String.valueOf(cityId), current_position, desirePosition, chkDriving,
                token);
    }

    @Subscribe
    public void onSkillEvent(ServerGetSkillEvent serverGetSkillEvent) {
        spoken_list.clear();
        language_list.clear();
        written_list.clear();
        language_id_list.clear();
        skillList = serverGetSkillEvent.getServerResponse().getSkillList();
        for (int i = skillList.size() - 1; i >= 0; i--) {
            language_id_list.add(skillList.get(i).getSkillId());
            language_list.add(skillList.get(i).getType());
            spoken_list.add(skillList.get(i).getGetspoken_lvl());
            written_list.add(skillList.get(i).getGetwritten_lvl());
        }

        CustomAdapter_Language eer = new CustomAdapter_Language(ModifyCvActivity.this, language_list, spoken_list, written_list, language_id_list);
        //     language_ls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, language_list.size() * 100));
        language_ls.setAdapter(eer);
        eer.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverGetExperienceEvent(ServerGetExperienceEvent serverGetExperienceEvent) {
        exp_id_list.clear();
        organizatio_listn.clear();
        rank_data.clear();
        exp_end_list.clear();
        exp_start_list.clear();
        experienceList = serverGetExperienceEvent.getServerResponse().getExperienceList();
        String end_ddate;
        for (int i = experienceList.size() - 1; i >= 0; i--) {
            exp_id_list.add(experienceList.get(i).getExpId());
            organizatio_listn.add(experienceList.get(i).getOrganization());
            rank_data.add(experienceList.get(i).getRank());
            exp_start_list.add(experienceList.get(i).getStartDate());
            if (experienceList.get(i).getEndDate() == null) {
                end_ddate = "Currently Working";
            } else {
                end_ddate = experienceList.get(i).getEndDate();
            }
            exp_end_list.add(end_ddate);
        }
        CustomAdapter_List_Work work_exp = new CustomAdapter_List_Work(ModifyCvActivity.this, organizatio_listn, rank_data, exp_start_list, exp_end_list, exp_id_list);
        //  work_ls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, organizatio_listn.size() * 300));
        work_ls.setAdapter(work_exp);
        work_exp.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverGetEducationEvent(ServerGetEducationEvent serverGetEducationEvent) {
        edu_id_list.clear();
        degree_list.clear();
        uni_list.clear();
        edu_start_date_list.clear();
        edu_end_date_list.clear();
        edu_ls.setVisibility(View.VISIBLE);
        educationList = serverGetEducationEvent.getServerResponse().getEducationList();
        for (int i = educationList.size() - 1; i >= 0; i--) {
            edu_id_list.add(educationList.get(i).getEduId());
            degree_list.add(educationList.get(i).getDegree());
            uni_list.add(educationList.get(i).getUniversity());
            edu_start_date_list.add(educationList.get(i).getGetSStart_date());
            edu_end_date_list.add(educationList.get(i).getGetEEnd_date());
        }

        CustomAdapter_Edu customAdapterEdu = new CustomAdapter_Edu(ModifyCvActivity.this, uni_list, degree_list, edu_start_date_list, edu_end_date_list, edu_id_list, userId);
        //   edu_ls.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, uni_list.size() * 200));
        edu_ls.setAdapter(customAdapterEdu);
        customAdapterEdu.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverGetQualificationEvent(ServerGetQualificationEvent serverGetQualificationEvent) {
        center_list.clear();
        course_list.clear();
        qua_id_list.clear();
        qua_start_list.clear();
        qua_end_list.clear();
        qualificationList = serverGetQualificationEvent.getServerResponse().getOtherQualificationList();
        for (int i = qualificationList.size() - 1; i >= 0; i--) {
            center_list.add(qualificationList.get(i).getCenterName());
            course_list.add(qualificationList.get(i).getCourse());
            qua_id_list.add(qualificationList.get(i).getQuaId());
            qua_start_list.add(qualificationList.get(i).getStart_date());
            qua_end_list.add(qualificationList.get(i).getEnd_date());
        }
        CustomAdapter_List_Qua ee = new CustomAdapter_List_Qua(ModifyCvActivity.this, center_list, course_list, qua_start_list, qua_end_list, qua_id_list);
        qualification_ls.setAdapter(ee);
        ee.notifyDataSetChanged();
    }

    @Subscribe
    public void onServerTownshipEvent(ServerTownshipEvent serverTownshipEvent) {

        try {
            townshipList = serverTownshipEvent.getServerResponse().getTownshipList();
        } catch (NullPointerException e) {
            useGetTownship();
            useGetTownshipById(cityId);
        }
        if (townshipList.size() == 0) {
            useGetTownship();
            useGetTownshipById(cityId);
        }

    }

    @Subscribe
    public void onSeverCityEvent(ServerCityEvent serverCityEvent) {
        if (serverCityEvent.getServerResponse() != null) {
            cityList = serverCityEvent.getServerResponse().getLocationList();
            SpinnerCustomAdapterOne customAdapter111 = new SpinnerCustomAdapterOne(ModifyCvActivity.this, cityList);
            spinnerStateDivision.setAdapter(customAdapter111);
            for (int i = 0; i < cityList.size(); i++) {
                if (cv_data.get(0).getCity().equals(cityList.get(i).getCity())) {
                    spinnerStateDivision.setSelection(i);
                }
            }
        } else {
            useGetCityById();
            Log.e("Error City", "");

        }
    }

    @Subscribe
    public void onSeverJobCategoryEvent(ServerJobCategoryEvent serverJobCategoryEvent) {
        try {
            //jobCategories = serverJobCategoryEvent.getServerResponse().getJobCategories();
            jobCategories = serverJobCategoryEvent.getServerResponse().getJobCategories();
        } catch (NullPointerException e) {
        }
//        useGetJobuCategory();
        if (jobCategories.size() == 0) {
            useGetJobCategory();
        } else {

            ArrayAdapter customAdapter112 = new ArrayAdapter(ModifyCvActivity.this, R.layout.custom_layout_for_spinner, jobCategories);
            spinnerDesirePosition.setAdapter(customAdapter112);
            customAdapter112.setDropDownViewResource(android.R.layout.simple_list_item_1);
        }

        for (int i = 0; i < jobCategories.size(); i++) {
            if (cv_data.get(0).getDesired_position().equals(jobCategories.get(i).getCategory())) {
                spinnerDesirePosition.setSelection(i + 1);
                break;
            }


        }

    }

    @Subscribe
    public void onSeverUploadCVEvent(ServerUpLoadCVEvent serverUpLoadCVEvent) {
        if (serverUpLoadCVEvent.getServerResponse().getStatusCode() == 200) {
            Toast.makeText(ModifyCvActivity.this, "Upload Done", Toast.LENGTH_SHORT).show();
            attach_cv_textview_modify.setText("CV Received");
            pdUploadCV.dismiss();
            dialog.dismiss();


        } else {
            Toast.makeText(ModifyCvActivity.this, "" + serverUpLoadCVEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();

            pdUploadCV.dismiss();
        }

    }

    @Subscribe
    public void Job_Error_Event(JobErrorEvent event) {
        useGetJobCategory();
    }

    @Subscribe
    public void onSeverUploadResumeEvent(ServerUploadResumeEvent serverUploadResumeEvent) {

        if (serverUploadResumeEvent.getServerResponse().getStatusCode() == 200) {
            dialog.dismiss();

            Glide
                    .with(ModifyCvActivity.this)
                    .load("http://allreadymyanmar.com/uploads/resume-photo/" + serverUploadResumeEvent.getServerResponse().getResume_photo())
                    .placeholder(R.drawable.placeholder_loading_image)
                    .into(new GlideDrawableImageViewTarget(modify_img_photo) {
                        @Override
                        public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //progressBar.setVisibility(View.GONE);
                        }
                    });
            Toast.makeText(ModifyCvActivity.this, "Photo received", Toast.LENGTH_SHORT).show();
            pdUploadPhoto.cancel();
            pdUploadPhoto.dismiss();

        } else {
            Toast.makeText(ModifyCvActivity.this, "" + serverUploadResumeEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
            pdUploadPhoto.cancel();
            pdUploadPhoto.dismiss();
        }

    }

    @Subscribe
    public void errorUploadPhotoEvent(ErrorUploadPhotoEvent event) {
        pdUploadPhoto.dismiss();
        Toast.makeText(ModifyCvActivity.this, "Fail....", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void Township_Error_Event(TownshipErrorEvent event) {
        useGetTownshipById(cityId);
    }

    @Subscribe
    public void onError_Upload_Photo(ErrorUploadPhotoEvent event) {
        pdUploadPhoto.cancel();
        Toast.makeText(ModifyCvActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
    }

    public void useGetTownshipById(int cityId) {
        communicator.getTownshipById(cityId);
    }

    @Subscribe
    public void onSeverTownshipEvent(ServerTownshipEvent serverTownshipEvent) {
        if (serverTownshipEvent.getServerResponse() != null) {
            townShipWithId = serverTownshipEvent.getServerResponse().getTownshipList();
            //useGetTownshipById(cityId);
            SpinnerCustomAdapterTownship customAdapter1 = new SpinnerCustomAdapterTownship(getApplicationContext(), townShipWithId, cityList);
            if (noOrYes == false) {
                for (int i = 0; i < townShipWithId.size(); i++) {
                    if (cv_data.get(0).getTownship().equals(townShipWithId.get(i).getTownship())) {
                        spinnerTownship.setSelection(i);
                        break;
                    }
                }
            }
            noOrYes = true;
            spinnerTownship.setAdapter(customAdapter1);
        } else {
            useGetTownshipById(cityId);
        }

    }


    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btn_cv_upload_modify:
                verifyReadExternalPermissions(ModifyCvActivity.this);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                String[] mimeTypes = {"application/msword", "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, 300);
                break;
        }
    }

    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.hide_cv_yes:
                cvViews = "0";
                break;
            case R.id.hide_cv_no:
                cvViews = "1";

                break;
            case R.id.driving_license_yes:
                chkDriving = 1;
                break;
            case R.id.driving_license_no:
                chkDriving = 0;
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ModifyCv Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    ////...............................Date of Birth Date Dialog........................//
    public static class DateofBirthPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            editTextDateOfBirth.setText(year + "-" + (month + 1) + "-" + day);
        }
    }

    // helper class for DatePicker of EducationYearPicker
    public static class EducationYearPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            textViewEduYear.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    public static class EducationYearPickerFragment2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            textViewEduYear_end.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    //...............................Experience Date Dialog........................//
    public static class ExperienceStartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user


            textViewStartDate.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    public static class ExperienceEndDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();


            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            textViewEndDate.setText(year + "-" + (month + 1) + "-" + day);


        }


    }

    //...............................Qualification Date Dialog........................//
    public static class OtherStartDateYearPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            otherStartDate.setText(year + "-" + (month + 1) + "-" + day);
        }
    }

    // helper class for DatePicker of EducationYearPicker
    public static class OtherEndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            otherEndDate.setText(year + "-" + (month + 1) + "-" + day);
        }
    }

    public void onRadioButtonCVViewsClicked(View view) {
        // Is the button now checked?

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.hide_cv_yes:
                // Pirates are the best
                cvViews = "1";
                break;
            case R.id.hide_cv_no:
                // Ninjas rule
                cvViews = "0";
                break;

        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
        //   return FileProvider.getUriForFile(JResumeActivity.this,
        //    BuildConfig.APPLICATION_ID + ".provider",
        //  getOutputMediaFile(type));
    }

    public Uri getOutputMediaFileUriVersion24(int type) {
        // return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(ModifyCvActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

// Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
// Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            // successfully captured the image
            if (resultCode == RESULT_OK) {
                dialog.setContentView(R.layout.dialog_image_view);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.image_to_upload));

                ImageView imageViewResume = (ImageView) dialog.findViewById(R.id.imageView_resume_photo);

                final Uri imageUri = Uri.parse(mCurrentPhotoPath);
                // Show the thumbnail on ImageView
                File file = new File(imageUri.getPath());

                try {
                    resizeImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageViewResume.setImageURI(imageUri);

                /*  Glide.with(this)
                        .load(imageUri.getPath())
                        //.placeholder(R.drawable.placeholder_loading_image)
                        .into(imageViewResume);*/


                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(ModifyCvActivity.this,
                        new String[]{imageUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });

                // finally Upload Image
                Button btnPostLanguage = (Button) dialog.findViewById(R.id.btn_upload_imageView);
                btnPostLanguage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        useUploadResumePhoto(applicantId, imageUri.getPath(), token);
                        pdUploadPhoto = new ProgressDialog(ModifyCvActivity.this);
                        pdUploadPhoto.setMessage(getResources().getString(R.string.loading));
                        pdUploadPhoto.setCancelable(false);
                        pdUploadPhoto.show();

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_dismiss_imageView);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


            }


        } else if (resultCode == RESULT_OK && requestCode == 200) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Unable to pick image", Toast.LENGTH_LONG).show();
                return;
            } else {
                dialog.setContentView(R.layout.dialog_image_view);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.image_to_upload));
                ImageView imageViewResume = (ImageView) dialog.findViewById(R.id.imageView_resume_photo);
                final Uri imageUri = data.getData();
                //  imageViewResume.setImageURI(imageUri);
                //      imageViewResume.setImageBitmap(BitmapFactory.decodeFile(imageUri.getPath()));

                //      imagePath = getRealPathFromURI(imageUri);
                final String imagePath = PathToRealPathHelper.getPath(ModifyCvActivity.this, imageUri);

                File file = new File(imagePath);

                try {
                    resizeImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Glide.with(this)
                        .load(imagePath)
                        .override(400, 200)
                        .centerCrop()
                        .into(imageViewResume);

                Button btnPostLanguage = (Button) dialog.findViewById(R.id.btn_upload_imageView);
                btnPostLanguage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        useUploadResumePhoto(applicantId, imagePath, token);
                        pdUploadPhoto = new ProgressDialog(ModifyCvActivity.this);
                        pdUploadPhoto.setMessage(getResources().getString(R.string.loading));
                        pdUploadPhoto.setCancelable(false);
                        pdUploadPhoto.show();

                    }
                });


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_dismiss_imageView);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }
        } else if (requestCode == SELECT_CV) {
            if (resultCode == RESULT_OK) {
                Uri cvUri = data.getData();


                cvPath = PathToRealPathHelper.getPath(ModifyCvActivity.this, cvUri);
                verifyStoragePermissions(this);
                //  Toast.makeText(getApplicationContext()," Cv Response" + cvPath,Toast.LENGTH_LONG).show();
                useUploadCv(applicantId, cvPath, token);

                pdUploadCV = new ProgressDialog(ModifyCvActivity.this);
                pdUploadCV.setMessage(getResources().getString(R.string.loading));
                pdUploadCV.setCancelable(false);
                pdUploadCV.show();


            }

        } else if (resultCode == RESULT_CANCELED) {

            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();

        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,


                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyReadExternalPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,


                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void showImagePopup(Context context) {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose image");
        startActivityForResult(chooserIntent, 200);

    }

    private void captureImage(Context context, Activity activity) throws IOException {

        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Content grand Uri Permission
        captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        captureIntent.addFlags(captureIntent.FLAG_GRANT_READ_URI_PERMISSION);

        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        captureIntent.addFlags(captureIntent.FLAG_GRANT_WRITE_URI_PERMISSION);

        if (captureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = getOutputMediaFileVersion24();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        getOutputMediaFileVersion24());

                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(captureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }

        }


    }


    public File getOutputMediaFileVersion24() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


        // External sdcard location
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }


        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir                                      // toCheck storageDir
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();


        return image;
    }


    /////////////////////////Compress Image///////////////
    public void resizeImage(File file) throws IOException {

        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
        // original measurements
        int origWidth = b.getWidth();
        int origHeight = b.getHeight();

        final int destWidth = 600;//or the width you need

        // picture is wider than we want it, we calculate its target height
        int destHeight = origHeight / (origWidth / destWidth);
        // we create an scaled bitmap so it reduces the image, not just trim it
        Bitmap b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);
        Matrix matrix = new Matrix();
        //matrix.postRotate(0);


/*
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file, bounds);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(file, opts);
            ExifInterface exif = new ExifInterface(file);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);*/


        // create a new bitmap from the original using the matrix to transform the result
        Bitmap rotatedBitmap = Bitmap.createBitmap(b2, 0, 0, b2.getWidth(), b2.getHeight(), matrix, true);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // compress to the format you want, JPEG, PNG...
        // 70 is the 0-100 quality percentage

        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outStream);


        file.createNewFile();
        //write the bytes in file
        FileOutputStream fo = new FileOutputStream(file);

        fo.write(outStream.toByteArray());
        // remember close de FileOutput
        fo.close();

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + file.getAbsolutePath();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = "file:" + image.getAbsolutePath();  // // TODO: 7/8/2017
        return image;
    }

    public void onBackPressed() {
        //   super.onBackPressed();
       finish();
//        Intent intent = new Intent(ModifyCvActivity.this, ContentHost.class);
//        intent.putExtra("remain_time", ContentHost.remain_time);
//        startActivity(intent);
    }

    public void deFault() {
        gender = null;
        maDawLayout.setVisibility(View.INVISIBLE);
        uKoLayout.setVisibility(View.INVISIBLE);
        maleFeamelLayout.setVisibility(View.VISIBLE);
        showEdit.setVisibility(View.GONE);
    }

    public void MFBUTTON(View v) {
        switch (v.getId()) {
            case R.id.refreshButton:
                deFault();
                break;
            case R.id.maleBTN:
                maleFeamelLayout.setVisibility(View.GONE);
                gender = "Male";
                uKoLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.femaleBTN:
                maleFeamelLayout.setVisibility(View.GONE);
                gender = "Female";
                maDawLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.uBTN:
                uKoLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(getResources().getString(R.string.U));
                fullName.requestFocus();
                fullName.setEnabled(true);
                break;
            case R.id.Mrs_BTN:
                uKoLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(getResources().getString(R.string.Mr));
                fullName.requestFocus();
                fullName.setEnabled(true);
                break;
            case R.id.koBTN:
                uKoLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(getResources().getString(R.string.Ko));
                fullName.requestFocus();
                fullName.setEnabled(true);
                break;
            case R.id.maBTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(getResources().getString(R.string.Ma));
                fullName.requestFocus();
                fullName.setEnabled(true);
                break;
            case R.id.dawBTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(getResources().getString(R.string.Daw));
                fullName.requestFocus();
                fullName.setEnabled(true);
                break;
            case R.id.Mriss_BTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                fullName.requestFocus();
                fullName.setEnabled(true);
                PreName.setText(getResources().getString(R.string.Ms));
                break;

        }
    }

    //////////////////////////////////////Downloading User Data////////////////////////////////////////////
    public class FetchData extends AsyncTask<Object, Object, ArrayList<CV_Data>> {
        JSONObject c = null;

        protected void onPreExecute() {

            modify_layout.setVisibility(View.INVISIBLE);
            cv_data.clear();
            experience_data.clear();
            skill_data.clear();
            education_data.clear();
            qualification_data.clear();
        }

        protected void onPostExecute(ArrayList<CV_Data> arrayList) {

            if (arrayList.size() > 0) {
                setTextFunction();
            } else {
                new FetchData().execute(userId);
            }
        }

        protected ArrayList<CV_Data> doInBackground(Object... objects) {
            JSONArray cvList = null;
            JSONArray edu = null;
            JSONArray exp = null;
            JSONArray qua = null;
            JSONArray language = null;

            String url = "http://allreadymyanmar.com/api/jobseeker/" + userId;
            String Result = CheckConn(url);
            if (Result != null) {
                try {
                    useGetJobCategory();
                } catch (Exception e) {
                    useGetJobCategory();
                }
                useGetCityById();

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
                    cv_data.add(new CV_Data(c.getString("name"), c.getString("father_name"),
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

            return cv_data;
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

