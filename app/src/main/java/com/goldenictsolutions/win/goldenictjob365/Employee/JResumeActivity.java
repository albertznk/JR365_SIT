package com.goldenictsolutions.win.goldenictjob365.Employee;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.goldenictsolutions.win.goldenictjob365.BuildConfig;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ContentHost;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_Edu;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_Language;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_List_Qua;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.CustomAdapter_List_Work;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.SpinnerCustomAdapterOne;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.SpinnerCustomAdapterTownship;

import com.goldenictsolutions.win.goldenictjob365.Employee.Helper.PathToRealPathHelper;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Applicant;
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
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadCVEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadPhotoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.GetEducationErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.GetExperienceErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.GetQualificationErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.GetSkillErrorEVent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.JobErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerApplicantEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerApplicantWithIDEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerCityEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerDeleteEduEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerDeleteExpEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerDeleteQualificationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerDeleteSkillEvent;
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
import com.goldenictsolutions.win.goldenictjob365.JLoginActivity;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.squareup.otto.Subscribe;


import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;


public class JResumeActivity extends AppCompatActivity {
    DB_Control db_control;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ListView education_list_resume, qualification_list_resume, language_list_resume, work_exp_list_resume;
    String imagePath, cvPath;
    ImageView refresh;
    CheckBox currently_working;
    RadioButton radioMale, radioFemale;
    // directory name to store captured images and videos

    TextView question_hide;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private static TextView editTextDateOfBirth, textViewEduYear, textViewEduYear2, textViewStartDate, textViewEndDate, otherStartDate, otherEndDate;
    private EditText EduUniversity, EduDegree;
    private ImageView imageViewTakePhoto;
    private TextView editTextTakePhoto;
    private Spinner spinnerStateDivision;
    MaterialSpinner spinnerTownship;
    private MaterialSpinner spinnerReligion, spinnerDesirePosition, spinnerMaritalStatus;
    private Dialog dialogImagePhoto;
    private ProgressDialog pdDeleteSkill, pdDeleteEdu, pdDeleteExp, pdDeleteQua, pdAddSkill, pdAddEdu, pdAddExp, pdAddQua, pdUploadCV;
    private Button btnAddEducation, btnPostEducation, btnAddWorkingExperience, btnAddOtherQualification, btnSubmit, btnAddLanguage, btnUploadCv;
    private ImageButton btnDeleteEdu, btnDeleteExp, btnDeleteSkill, btnDeleteQua;
    private String token;
    private String userId;
    private Applicant applicant;
    private String expId, skillId, eduuId, quaId;
    private ProgressDialog pdUploadPhoto;
    private String gender;
    public static List<Township> townshipList;
    public static List<JobCategory> jobCategories;
    private Communicator communicator;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_CV = 300;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri; // file url to store image/video
    EditText edtNationality, edtFatherName;


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

    ArrayList<String> edu_id_list = new ArrayList<>();
    ArrayList<String> exp_id_list = new ArrayList<>();
    ArrayList<String> qua_id_list = new ArrayList<>();
    ArrayList<String> language_id_list = new ArrayList<>();

    ArrayList<String> edu_start_date_list = new ArrayList<>();
    ArrayList<String> edu_end_date_list = new ArrayList<>();

    private List<Location> cityList;
    private List<Township> townShipWithId;
    private int chkDriving = 0;
    private String maritalStatus;
    private String applicantId;
    private List<Skill> skillList;
    private List<Education> educationList;
    private List<Experience> experienceList;
    private List<OtherQualification> qualificationList;
    private List<OtherQualification> otherQualification;

    private int cityId, townshipId;
    private String desirePosition;
    private String religion, nrcNr, nrcDivision;
    private String cvViews;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    private CheckBox noFormalEduCheckBox;
    private int u_type;
    String user_name, password;
    private String city;
    private String name;
    LinearLayout edu_view, qua_view, language_view, exp_view;
    private EditText nrcNumberEnd, edtMobileNr, edtEmail, edtExpSalary, edtAddress, edtCurrentPosition;
    ;


    //nuke
    LinearLayout maleFeamelLayout, uKoLayout, maDawLayout, showEdit;
    EditText inPutName;
    TextView PreName;
    String remain_time;
    ProgressBar pb;
    String[] religion__zzz;
    String[] marital_status;

    public String expStartDate, expEndDate, expOrganization, expRank;
    public String edu_start_Year, eduUniversity, eduDegree, edu_end_year;
    public String centerName, coursee, othStartDate, othEndDate;
    public String language_type, spoken_Level, written_Level;
    private String mCurrentPhotoPath;
    boolean uploaddone = false;
    TextView attach_c_resume;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jresume);
        maDawLayout = (LinearLayout) findViewById(R.id.maDawLayout);
        uKoLayout = (LinearLayout) findViewById(R.id.uKoLayout);
        maleFeamelLayout = (LinearLayout) findViewById(R.id.maleFemaleLayout);
        showEdit = (LinearLayout) findViewById(R.id.showEdit);
        PreName = (TextView) findViewById(R.id.preName);
        deFault();

        work_exp_list_resume = (ListView) findViewById(R.id.work_list_resume);
        qualification_list_resume = (ListView) findViewById(R.id.qualification_list_resume);
        language_list_resume = (ListView) findViewById(R.id.language_list_resume);
        education_list_resume = (ListView) findViewById(R.id.edu_list_resume);

        db_control = new DB_Control(this);
        dialog = new Dialog(JResumeActivity.this);
        //token = JLoginActivity.token;
        communicator = new Communicator();
        u_type = getIntent().getExtras().getInt("U_TYPE");
        user_name = getIntent().getExtras().getString("JP_username");
        password = getIntent().getExtras().getString("JP_Password");
        userId = getIntent().getExtras().getString("JP_userID");
        remain_time = getIntent().getExtras().getString("remain_time");
        Log.e("UID", "UID" + userId);

        token = getIntent().getExtras().getString("token");
        useGetCityById();
        useGetJobCategory();
        useGetEducationById(userId, token);
        useGetExperienceById(userId, token);
        useGetQualificationById(userId, token);
        useGetSkillById(userId, token);
        useGetApplicantById(userId);
        inPutName = (EditText) findViewById(R.id.et_fullname);
        imageViewTakePhoto = (ImageView) findViewById(R.id.profile_picture);
        attach_c_resume = (TextView) findViewById(R.id.attach_c_resume);
        editTextTakePhoto = (TextView) findViewById(R.id.take_photo);

        edtFatherName = (EditText) findViewById(R.id.et_father_name_resume);
        editTextDateOfBirth = (TextView) findViewById(R.id.editText_dob1_resume);
        // here pob place of birth
        spinnerMaritalStatus = (MaterialSpinner) findViewById(R.id.spinner_marital_status_resume);
        edtNationality = (EditText) findViewById(R.id.et_nationality_resume);
        spinnerReligion = (MaterialSpinner) findViewById(R.id.spinner_religion_resume);
        btnUploadCv = (Button) findViewById(R.id.btn_cv_upload_resume);
        question_hide = (TextView) findViewById(R.id.hide_cv_tv_resume);
        question_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        JResumeActivity.this);

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

        btnUploadCv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verifyReadExternalPermissions(JResumeActivity.this);

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                String[] mimeTypes = {"application/msword", "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, 300);

            }
        });

        nrcNumberEnd = (EditText) findViewById(R.id.et_nrc_no_resume);
        edtMobileNr = (EditText) findViewById(R.id.et_contact_number_resume);
        spinnerDesirePosition = (MaterialSpinner) findViewById(R.id.desire_position_spinner_resume);
        edtEmail = (EditText) findViewById(R.id.et_email_resume);
        edtExpSalary = (EditText) findViewById(R.id.et_expected_salary_resume);
        edtAddress = (EditText) findViewById(R.id.et_address_Resume);
        spinnerTownship = (MaterialSpinner) findViewById(R.id.township_spinner_resume);
        spinnerStateDivision = (Spinner) findViewById(R.id.city_spinner_resume);
        edtCurrentPosition = (EditText) findViewById(R.id.et_current_position_resume);
        spinnerTownship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    townshipId = townshipList.get(position).getId();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                townshipId = townshipList.get(0).getId();
            }
        });

        spinnerDesirePosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == -1) {
                    desirePosition = null;
                } else {
                    desirePosition = jobCategories.get(position).getCategory();
                }
                //desirePosition = (String) spinnerDesirePosition.getSelectedItem();
                //Toast.makeText(JResumeActivity.this, "You Selected" + desirePosition, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                desirePosition = null;

            }
        });

        spinnerStateDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(), "" + Flash.cityList.get(position).getId(), Toast.LENGTH_LONG).show();
                cityId = cityList.get(position).getId();
                useGetTownshipById(cityList.get(position).getId());

            }

            public void onNothingSelected(AdapterView<?> parent) {
                cityId = cityList.get(0).getId();

            }
        });
        religion__zzz = getResources().getStringArray(R.array.spin_religion);
        ArrayAdapter<String> adapterReligion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, religion__zzz);
        adapterReligion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReligion.setAdapter(adapterReligion);

        spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                religion = (String) spinnerReligion.getSelectedItem();
                //Toast.makeText(getApplicationContext(), " " + religion, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> adaptermartialstatus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spin_marital_status));
        adaptermartialstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalStatus.setAdapter(adaptermartialstatus);
        spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                maritalStatus = (String) spinnerMaritalStatus.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit = (Button) findViewById(R.id.Submit_resume);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = PreName.getText() + inPutName.getText().toString();


                String fatherName = edtFatherName.getText().toString();
                String dob = editTextDateOfBirth.getText().toString();
                String pob = "Myanmar";

                String nationality = edtNationality.getText().toString();
                String nrc = nrcNumberEnd.getText().toString();
                String mobileNr = edtMobileNr.getText().toString();
                String email = edtEmail.getText().toString();
                String expectedSalary = edtExpSalary.getText().toString();
                String address = edtAddress.getText().toString();
                String current_position = edtCurrentPosition.getText().toString();

                Log.e("Education List", "List" + educationList.size());
                if (inPutName.getText().toString().isEmpty() ||
                        edtFatherName.getText().toString().isEmpty() ||
                        editTextDateOfBirth.getText().toString().isEmpty() ||
                        nrcNumberEnd.getText().toString().isEmpty() ||
                        edtNationality.getText().toString().isEmpty() ||

                        edtMobileNr.getText().toString().isEmpty() ||
                        edtAddress.getText().toString().isEmpty() ||
                        edtExpSalary.getText().toString().isEmpty() ||
                        desirePosition == null
                    //desirePosition.equals("Desired Position *")
                        ) {
                    Log.e("OKII", "" + nrc);
                    Toast.makeText(JResumeActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                } else if (educationList.size() == 0 &&
                        !inPutName.getText().toString().isEmpty() &&
                        !edtFatherName.getText().toString().isEmpty() &&
                        !editTextDateOfBirth.getText().toString().isEmpty() &&
                        !nrcNumberEnd.getText().toString().isEmpty() &&
                        !edtMobileNr.getText().toString().isEmpty() &&
                        !edtExpSalary.getText().toString().isEmpty() &&
                        !edtAddress.getText().toString().isEmpty() &&
                        desirePosition != null &&
                        noFormalEduCheckBox.isChecked()) {

                    Log.e("Oki", "000000");

                    useUpdateApplicantById(applicantId, gender, cvViews, name, fatherName, dob
                            , pob, maritalStatus, nationality, religion, nrc, mobileNr, email, expectedSalary, address
                            , String.valueOf(townshipId), String.valueOf(cityId), current_position, desirePosition, chkDriving,
                            token);

                    progressDialog = new ProgressDialog(JResumeActivity.this);
                    progressDialog.setMessage("Uploading ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                } else if (educationList.size() > 0 &&
                        !inPutName.getText().toString().isEmpty() &&
                        !edtFatherName.getText().toString().isEmpty() &&
                        !editTextDateOfBirth.getText().toString().isEmpty() &&
                        !nrcNumberEnd.getText().toString().isEmpty() &&
                        !edtMobileNr.getText().toString().isEmpty() &&
                        !edtAddress.getText().toString().isEmpty() &&
                        !edtExpSalary.getText().toString().isEmpty() &&
                        desirePosition != null) {


                    Log.e("Oki", "1111111");
                    Log.e("Education List", "List" + educationList.size());
                    useUpdateApplicantById(applicantId, gender, cvViews, name, fatherName, dob
                            , pob, maritalStatus, nationality, religion, nrc, mobileNr, email, expectedSalary, address
                            , String.valueOf(townshipId), String.valueOf(cityId), current_position, desirePosition, chkDriving,
                            token);


                    progressDialog = new ProgressDialog(JResumeActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                } else {
                    Toast.makeText(JResumeActivity.this, "Enter Your Education", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnAddEducation = (Button) findViewById(R.id.btn_add_education_resume);
        btnAddOtherQualification = (Button) findViewById(R.id.btn_add_qualification_resume);
        btnAddWorkingExperience = (Button) findViewById(R.id.btn_add_work_exp_resume);
        btnAddLanguage = (Button) findViewById(R.id.btn_add_language_resume);
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
                        pdAddSkill = new ProgressDialog(JResumeActivity.this);
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
                    public void onClick(View v) {
                        expStartDate = textViewStartDate.getText().toString();
                        expEndDate = textViewEndDate.getText().toString();
                        expOrganization = EditTextexpOrganization.getText().toString();
                        expRank = EditTextexpRank.getText().toString();
                        if (currently_working.isChecked() || !textViewEndDate.getText().toString().isEmpty()) {
                            pdAddExp = new ProgressDialog(JResumeActivity.this);
                            pdAddExp.setMessage(getResources().getString(R.string.loading));
                            pdAddExp.setCancelable(false);
                            pdAddExp.show();
                            useInsertExperience(userId, expOrganization, expRank, expStartDate, expEndDate, token);
                        } else {
                            Toast.makeText(JResumeActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                        }
                        // useGetExperienceById(userId, token);


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
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }


        });
        btnAddOtherQualification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Create custom dialog object


                dialog.setContentView(R.layout.dialog_other_qualification);
                dialog.setTitle(getResources().getString(R.string.job_detailsummary));
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

                        pdAddQua = new ProgressDialog(JResumeActivity.this);
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

        noFormalEduCheckBox = (CheckBox) findViewById(R.id.checkBox_noFormalEdu);
        noFormalEduCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Toast.makeText(JResumeActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                    btnAddEducation.setClickable(false);
                } else {
                    //Toast.makeText(JResumeActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                    btnAddEducation.setClickable(true);
                }
            }
        });

        btnAddEducation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                dialog.setContentView(R.layout.dialog_education);
                // Set dialog title
                dialog.setTitle(getResources().getString(R.string.education));
                /*ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView21);
                Glide.with(getApplicationContext())
                        .load(R.drawable.icon_and_button_13)
                        .into(imageView);*/
                EduUniversity = (EditText) dialog.findViewById(R.id.editText_university);
                EduDegree = (EditText) dialog.findViewById(R.id.edittext_degree);

                btnPostEducation = (Button) dialog.findViewById(R.id.btn_post_edu);
                btnPostEducation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edu_start_Year = textViewEduYear.getText().toString();
                        eduUniversity = EduUniversity.getText().toString();
                        eduDegree = EduDegree.getText().toString();
                        edu_end_year = textViewEduYear2.getText().toString();
                        if (edu_start_Year.equals("") || edu_end_year.equals("") || eduUniversity.equals("") || eduDegree.equals("")) {
                            Toast.makeText(JResumeActivity.this, getResources().getString(R.string.required_field), Toast.LENGTH_SHORT).show();
                        } else {
                            useInsertEducation(userId, eduUniversity, eduDegree, edu_start_Year, edu_end_year, token);
                            //  useGetEducationById(userId, token);
                            dialog.hide();
                            pdAddEdu = new ProgressDialog(JResumeActivity.this);
                            pdAddEdu.setMessage(getResources().getString(R.string.loading));
                            pdAddEdu.setCancelable(false);
                            pdAddEdu.show();
                        }
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
                textViewEduYear2 = (TextView) dialog.findViewById(R.id.textView_edu_end_year);
                textViewEduYear2.setOnClickListener(new View.OnClickListener() {
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
                // Toast.makeText(getApplicationContext(), "sldkfjlkdsjf", Toast.LENGTH_LONG).show();

            }
        });


        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        qualification_list_resume.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        language_list_resume.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        education_list_resume.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        work_exp_list_resume.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout


        marital_status = getResources().getStringArray(R.array.spin_marital_status);
        ArrayAdapter<String> martial_status_adapter = new ArrayAdapter<String>(JResumeActivity.this, R.layout.custom_layout_for_spinner, marital_status);

        martial_status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalStatus.setAdapter(martial_status_adapter);


        editTextTakePhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                ImageView image = new ImageView(JResumeActivity.this);

                Glide.with(JResumeActivity.this)
                        .load(R.drawable.true_logo)
                        .into(image);
                Dialog d = new AlertDialog.Builder(JResumeActivity.this, AlertDialog.THEME_HOLO_LIGHT)

                        .setIcon(R.drawable.icon_and_button_01)

                        .setTitle(getResources().getString(R.string.take_your_photo))
                        .setNegativeButton(getResources().getString(R.string.Cancel), null)

                        .setItems(new String[]{getResources().getString(R.string.take_your_photo1), getResources().getString(R.string.choose_from_gallery), "", ""}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int position) {
                                if (position == 0) {
                                    //  dispatchTakePictureIntent();
                                    verifyStoragePermissions(JResumeActivity.this);
                                    try {
                                        captureImage(getApplicationContext(), JResumeActivity.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (position == 1) {
                                    verifyStoragePermissions(JResumeActivity.this);
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

    }

    @Subscribe
    public void errorUploadCVEvent(ErrorUploadCVEvent event) {
        pdUploadCV.dismiss();
        Toast.makeText(JResumeActivity.this, "Fail...", Toast.LENGTH_SHORT).show();

    }

    private void useUpdateApplicantById(String Id, String gender, String cvViews, String name, String fatherName,
                                        String dateOfBirth, String pob, String maritalStatus, String nationality,
                                        String religion, String nrc,
                                        String mobileNo, String email, String expectedSalary, String address, String township
            , String city, String current_position, String desired_position, int chkDriving, String apiKey) {

        communicator.updateApplicantById(Id, gender, cvViews, name, fatherName, dateOfBirth, pob, maritalStatus, nationality
                , religion, nrc, mobileNo, email, expectedSalary, address, township, city, current_position
                , desired_position, chkDriving, apiKey);


    }

    private void useGetApplicantById(String userId) {
        communicator.getApplicant(userId);
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

    @Subscribe
    public void GetEducationErrorEvent(GetEducationErrorEvent event) {
        useGetEducationById(userId, token);
    }

    @Subscribe
    public void GetQualificationErrorEvent(GetQualificationErrorEvent event) {
        useGetQualificationById(userId, token);
    }

    @Subscribe
    public void cityErrorEvent(CityErrorEvent event) {
        useGetCityById();
    }

    @Subscribe
    public void GetSkillErrorEVent(GetSkillErrorEVent event) {
        useGetSkillById(userId, token);
    }

    @Subscribe
    public void GetWorkExperienceevent(GetExperienceErrorEvent event) {
        useGetExperienceById(userId, token);
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

    public void onBackPressed() {

    }

    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        db_control.openDb();
    }


    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        db_control.closeDb();
    }


    @Subscribe
    public void onServerOtherQualificationEvent(ServerOtherQualificationEvent serverOtherQualificationEvent) {

        //otherQualification = serverOtherQualificationEvent.getServerResponse().getOtherQualificationList();
        pdAddQua.dismiss();
        useGetQualificationById(userId, token);
        dialog.dismiss();
        //  viewPagerAdapter.addFragment(new Profile_education_fragment(), "Education");
        // viewPager.setAdapter(viewPagerAdapter);
    }

/*
    @Subscribe
    public void onServerEductionEvent(ServerEducationEvent serverEducationEvent) {
        if (serverEducationEvent.getServerResponse().getStatusCode() == 200) {
            //     education = serverEducationEvent.getServerResponse().getEducationList().get(0);
            pdAddEdu.cancel();
            pdAddEdu.dismiss();
            dialog.dismiss();
            useGetEducationById(userId, token);
        }
        //  viewPagerAdapter.addFragment(new Profile_education_fragment(), "Education");
        // viewPager.setAdapter(viewPagerAdapter);

    }*/

    @Subscribe
    public void onServerApplicantEvent(ServerApplicantEvent serverApplicantEvent) {
        if (serverApplicantEvent.getServerResponse().getStatusCode() == 200) {

            db_control.openDb();
            savestate();
            Toast.makeText(getApplicationContext(), "" + serverApplicantEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            Intent i = new Intent(this, ContentHost.class);
            i.putExtra("remain_time", remain_time);
            startActivity(i);
            finish();

        } else if (serverApplicantEvent.getServerResponse().getStatusCode() == 210) {
            progressDialog.dismiss();
            Toast.makeText(JResumeActivity.this, "" + serverApplicantEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSeverExperienceEvent(ServerExperienceEvent serverExperienceEvent) {
        if (serverExperienceEvent.getServerResponse().getResult().equals("Success")) {
            pdAddExp.cancel();
            pdAddExp.dismiss();
            dialog.dismiss();
            useGetExperienceById(userId, token);
        } else {
            pdAddExp.cancel();
            pdAddExp.dismiss();
            Toast.makeText(getApplicationContext(), "" + serverExperienceEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }


    }

    @Subscribe
    public void onSeverEducationEvent(ServerEducationEvent serverEducationEvent) {
        if (serverEducationEvent.getServerResponse().getResult().equals("Success")) {
            pdAddEdu.cancel();
            pdAddEdu.dismiss();
            dialog.dismiss();

            useGetEducationById(userId, token);
            Toast.makeText(JResumeActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            pdAddEdu.cancel();
            pdAddEdu.dismiss();
            Toast.makeText(getApplicationContext(), "" + serverEducationEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onSeverSkillEvent(ServerSkillEvent serverSkillEvent) {
        if (serverSkillEvent.getServerResponse().getStatusCode() == 200) {
            pdAddSkill.cancel();
            pdAddSkill.dismiss();
            dialog.dismiss();
            useGetSkillById(userId, token);
            Toast.makeText(JResumeActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSeverQuaEvent(ServerOtherQualificationEvent serverOtherQualificationEvent) {
        if (serverOtherQualificationEvent.getServerResponse().getResult().equals("Success")) {
            //  experience = serverExperienceEvent.getServerResponse().getExperienceList().get(0);
            //  progressDialog.hide();
            pdAddQua.cancel();
            pdAddQua.dismiss();

            useGetQualificationById(userId, token);
            Toast.makeText(JResumeActivity.this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            pdAddQua.cancel();
            pdAddQua.dismiss();
            Toast.makeText(getApplicationContext(), "" + serverOtherQualificationEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe
    public void onSeverDeleteEduEvent(ServerDeleteEduEvent serverDeleteEduEvent) {
        if (serverDeleteEduEvent.getServerResponse().getStatusCode() == 200) {
            /*pdDeleteEdu.cancel();
            pdDeleteEdu.dismiss();*/
            useGetEducationById(userId, token);

        }
    }

    @Subscribe
    public void onSeverDeleteExpEvent(ServerDeleteExpEvent serverDeleteExpEvent) {
        if (serverDeleteExpEvent.getServerResponse().getStatusCode() == 200) {
            /*pdDeleteExp.cancel();
            pdDeleteExp.dismiss();*/
            useGetExperienceById(userId, token);

        }
    }

    @Subscribe
    public void onSeverDeleteSkillEvent(ServerDeleteSkillEvent serverDeleteSkillEvent) {
        if (serverDeleteSkillEvent.getServerResponse().getStatusCode() == 200) {
/*
            pdDeleteSkill.cancel();
            pdDeleteSkill.dismiss();
*/

            useGetSkillById(userId, token);
        }
    }

    @Subscribe
    public void onSeverDeleteQuaEvent(ServerDeleteQualificationEvent serverDeleteQualificationEvent) {
        if (serverDeleteQualificationEvent.getServerResponse().getStatusCode() == 200) {
            /*pdDeleteQua.cancel();
            pdDeleteQua.dismiss();*/
            useGetEducationById(userId, token);

        }
    }

    @Subscribe
    public void errorUploadPhotoEvent(ErrorUploadPhotoEvent event) {
        pdUploadPhoto.dismiss();
        Toast.makeText(JResumeActivity.this, "Fail....", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onSeverGetApplicantEvent(ServerApplicantWithIDEvent event) {
        applicantId = event.getServerResponse().getApplicant().get(0).getId().toString();
        Log.e("Applicant ID", "" + event.getServerResponse().getApplicant().get(0).getId().toString());
    }

    @Subscribe
    public void onSeverEvent(ServerEvent serverEvent) {
        Log.e("Applicant ID", "" + serverEvent.getServerResponse().getApplicant().get(0).getId().toString());
        // applicantId = serverEvent.getServerResponse().getApplicant().get(0).getId().toString();

    }

    @Subscribe
    public void UploadErrorEvent(Update_Error_Event event) {
        progressDialog.dismiss();
        Toast.makeText(JResumeActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onSkillEvent(ServerGetSkillEvent serverGetSkillEvent) {
        spoken_list.clear();
        language_list.clear();
        written_list.clear();
        language_id_list.clear();
        skillList = serverGetSkillEvent.getServerResponse().getSkillList();
        for (int i = skillList.size()-1; i >=0 ; i--) {
            spoken_list.add(skillList.get(i).getGetspoken_lvl());
            language_list.add(skillList.get(i).getType());
            language_id_list.add(skillList.get(i).getSkillId());
            written_list.add(skillList.get(i).getGetwritten_lvl());
        }
        Log.e("Skill Language", "" + spoken_list.size());
        Log.e("Skill Language", "" + written_list.size());

        CustomAdapter_Language language_adapter = new CustomAdapter_Language(JResumeActivity.this, language_list, spoken_list, written_list, language_id_list);
        // CustomAdapter_Language language_adapter = new CustomAdapter_Language(JResumeActivity.this, language_list, spoken_list, written_list, language_id_list);
        //  language_list_resume.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, language_list.size() * 100));
        language_list_resume.setAdapter(language_adapter);
        language_adapter.notifyDataSetChanged();

      /*  CustomAdapter_Language eer = new CustomAdapter_Language(JResumeActivity.this, language_list, spoken_list, written_list, language_id_list);
        language_list_resume.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, language_list.size() * 350));
        language_list_resume.setAdapter(eer);*/
    }

    @Subscribe
    public void onSeverGetExperienceEvent(ServerGetExperienceEvent serverGetExperienceEvent) {

        exp_id_list.clear();
        organizatio_listn.clear();
        rank_data.clear();
        exp_end_list.clear();
        exp_start_list.clear();
        experienceList = serverGetExperienceEvent.getServerResponse().getExperienceList();

        Log.e(".....Fucking Bitch", "Bith"+experienceList.size());
        for (int i = experienceList.size()-1; i >=0 ; i--) {

            exp_id_list.add(experienceList.get(i).getExpId());
            organizatio_listn.add(experienceList.get(i).getOrganization());
            Log.e("FUcking EEE", "" + experienceList.get(i).getRank());
            rank_data.add(experienceList.get(i).getRank());
            exp_start_list.add(experienceList.get(i).getStartDate());
            //exp_end_list.add(experienceList.get(i).getEndDate());

            Log.e("Errorrrrrr","Errorrrr"+i);

            if (experienceList.get(i).getEndDate()==null) {
                exp_end_list.add("Currently Working");
            } else {
                exp_end_list.add(experienceList.get(i).getEndDate());
            }
        }

        CustomAdapter_List_Work work_exp = new CustomAdapter_List_Work(JResumeActivity.this, organizatio_listn, rank_data, exp_start_list, exp_end_list, exp_id_list);
        //   work_exp_list_resume.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, organizatio_listn.size() * 300));
        work_exp_list_resume.setAdapter(work_exp);
        work_exp.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverGetEducationEvent(ServerGetEducationEvent serverGetEducationEvent) {
        edu_id_list.clear();
        degree_list.clear();
        uni_list.clear();
        edu_start_date_list.clear();
        edu_end_date_list.clear();
        educationList = serverGetEducationEvent.getServerResponse().getEducationList();

        Log.e("Education List", "Education List" + educationList.size());

        for (int i = educationList.size()-1; i >=0 ; i--) {
            edu_id_list.add(educationList.get(i).getEduId());
            degree_list.add(educationList.get(i).getDegree());
            uni_list.add(educationList.get(i).getUniversity());
            edu_start_date_list.add(educationList.get(i).getGetSStart_date());
            edu_end_date_list.add(educationList.get(i).getGetEEnd_date());
        }

        if (educationList.size() != 0) {
            noFormalEduCheckBox.setEnabled(false);
        } else {
            noFormalEduCheckBox.setEnabled(true);
        }
        Log.e("QWER", "" + edu_start_date_list.size());
        Log.e("QWER", "" + edu_end_date_list.size());
        CustomAdapter_Edu customAdapterEdu = new CustomAdapter_Edu(JResumeActivity.this, uni_list, degree_list, edu_start_date_list, edu_end_date_list, edu_id_list, applicantId);
        //   education_list_resume.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, uni_list.size() * 130));
        education_list_resume.setAdapter(customAdapterEdu);
        customAdapterEdu.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverGetQualificationEvent(ServerGetQualificationEvent serverGetQualificationEvent) {
        //  Toast.makeText(JResumeActivity.this, "GET Qualification", Toast.LENGTH_SHORT).show();
        center_list.clear();
        course_list.clear();
        qua_id_list.clear();
        qua_start_list.clear();
        qua_end_list.clear();
        qualificationList = serverGetQualificationEvent.getServerResponse().getOtherQualificationList();
        for (int i = qualificationList.size()-1; i >=0 ; i--) {
            center_list.add(qualificationList.get(i).getCenterName());
            course_list.add(qualificationList.get(i).getCourse());
            qua_id_list.add(qualificationList.get(i).getQuaId());
            qua_start_list.add(qualificationList.get(i).getStart_date());
            qua_end_list.add(qualificationList.get(i).getEnd_date());
        }
        CustomAdapter_List_Qua ee = new CustomAdapter_List_Qua(JResumeActivity.this, center_list, course_list, qua_start_list, qua_end_list, qua_id_list);
        //    qualification_list_resume.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, center_list.size() * 400));
        qualification_list_resume.setAdapter(ee);
        ee.notifyDataSetChanged();
    }

    @Subscribe
    public void onSeverUploadPhotoEvent(ServerUploadResumeEvent serverUploadResumeEvent) {
        Toast.makeText(getApplicationContext(), "" + serverUploadResumeEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        uploaddone = true;
     /*   Glide.with(this)
                .load(imagePath)
                .into(imageViewTakePhoto);*/
        //imageViewTakePhoto.setLayoutParams(new ImageView());
        //editTextTakePhoto.setVisibility(View.GONE);
        editTextTakePhoto.setText("Photo Received");
    }

    @Subscribe
    public void onServerTownshipEvent(ServerTownshipEvent serverTownshipEvent) {
        townshipList = serverTownshipEvent.getServerResponse().getTownshipList();
        Log.e("Township", "Township" + townshipList);

    }


    @Subscribe
    public void onSeverCityEvent(ServerCityEvent serverCityEvent) {
        cityList = serverCityEvent.getServerResponse().getLocationList();
        SpinnerCustomAdapterOne customAdapter111 = new SpinnerCustomAdapterOne(getApplicationContext(), cityList);
        spinnerStateDivision.setAdapter(customAdapter111);
    }

    @Subscribe
    public void JobErrorEvent(JobErrorEvent event) {
        useGetJobCategory();
    }

    @Subscribe
    public void onSeverJobCategoryEvent(ServerJobCategoryEvent serverJobCategoryEvent) {
        try {
            jobCategories = serverJobCategoryEvent.getServerResponse().getJobCategories();
            ArrayAdapter customAdapter112 = new ArrayAdapter(JResumeActivity.this, R.layout.custom_layout_for_spinner, jobCategories);
            customAdapter112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDesirePosition.setAdapter(customAdapter112);
        } catch (Exception e) {
            useGetJobCategory();
        }

    }


    @Subscribe
    public void onSeverUploadCVEvent(ServerUpLoadCVEvent serverUpLoadCVEvent) {
        if (serverUpLoadCVEvent.getServerResponse().getStatusCode() == 200) {
            Toast.makeText(getApplicationContext(), "" + serverUpLoadCVEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
            attach_c_resume.setText("CV Received");
            pdUploadCV.dismiss();


        } else {
            Toast.makeText(getApplicationContext(), "" + serverUpLoadCVEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();

            pdUploadCV.dismiss();
        }

    }


    @Subscribe
    public void onSeverUploadResumeEvent(ServerUploadResumeEvent serverUploadResumeEvent) {
        pdUploadPhoto.hide();
        pdUploadPhoto.dismiss();
        Toast.makeText(getApplicationContext(), "" + serverUploadResumeEvent.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();


    }

/*

    @Subscribe
    public void updateQua(UpdateServerQuaEvent event) {
        useGetQualificationById(userId, token);
    }

    @Subscribe
    public void updateWork(UpdateServerWorkEvent event) {
        useGetExperienceById(userId, token);
    }

    @Subscribe
    public void updateSkill(UpdateServerSkillEvent event) {
        useGetSkillById(userId, token);
    }


    @Subscribe
    public void updateEducation(UpdateServerEducationEvent event) {
        useGetEducationById(userId, token);
    }

*/

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        progressDialog.dismiss();
        Toast.makeText(this, "onErrorEvent fail " + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }


    @Subscribe
    public void onSeverAddEdu_Error_Event(AddEducation_Error_Event event) {
        pdAddEdu.dismiss();
        Toast.makeText(JResumeActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();
        // useInsertEducation(userId, eduUniversity, eduDegree, edu_start_Year, edu_end_year, token);
    }

    @Subscribe
    public void onSeverAddQua_Error_Event(AddQua_Error_Event event) {
        pdAddQua.dismiss();
        Toast.makeText(JResumeActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();
        // useInsertQualification(userId, centerName, coursee, othStartDate, othEndDate, token);
    }

    @Subscribe
    public void onSeverAddWork_Exp_Error_Event(AddWork_Exp_Error_Event event) {
        pdAddExp.dismiss();
        Toast.makeText(JResumeActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();
        //   useInsertExperience(userId, expOrganization, expRank, expStartDate, expEndDate, token);
    }

    @Subscribe
    public void onSeverAddLanguage_Error_Event(AddLanguage_Error_Event event) {
        pdAddSkill.dismiss();
        Toast.makeText(JResumeActivity.this, "Please Retry", Toast.LENGTH_SHORT).show();

        //useInsertSkill(userId, language_type, spoken_Level, written_Level, token);
    }

    @Subscribe
    public void townshipErrorEvent(TownshipErrorEvent event) {
        useGetTownshipById(cityId);
    }

    private void useGetTownshipById(int cityId) {
        communicator.getTownshipById(cityId);
    }

    @Subscribe
    public void onSeverTownshipEvent(ServerTownshipEvent serverTownshipEvent) {
        townShipWithId = serverTownshipEvent.getServerResponse().getTownshipList();
        SpinnerCustomAdapterTownship customAdapter1 = new SpinnerCustomAdapterTownship(getApplicationContext(), townShipWithId, cityList);
        spinnerTownship.setAdapter(customAdapter1);

        if (serverTownshipEvent.getServerResponse() == null) {

            //   Toast.makeText(getApplicationContext(), "null township", Toast.LENGTH_SHORT).show();
        } else if (serverTownshipEvent.getServerResponse() != null) {

            //   townshipList = serverTownshipEvent.getServerResponse().getTownshipList();

        }
    }

    // helper class for expEndDate


    // helper class for DatePicker of DateOfBirth
    public static class DateofBirthPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
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

            textViewEduYear2.setText(year + "-" + (month + 1) + "-" + day);

        }
    }


    // helper class for DatePicker of EducationYearPicker
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

    // helper class for endDatePicker
    // helper class for DatePicker
    public static class ExperienceEndDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

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

            textViewEndDate.setText(year + "-" + (month + 1) + "-" + day);


        }


    }

    // helper class for DatePicker of EducationYearPicker
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


    public void onRadioButtonDrivingClickedd(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.driving_license_yes_resume:
                if (checked)
                    // Pirates are the best
                    chkDriving = 1;
                break;
            case R.id.driving_license_no_resume:
                if (checked)
                    // Ninjas rule
                    chkDriving = 0;
                break;
            default:
                chkDriving = 0;
        }
    }

    public void onRadioButtonCVViewsClickedd(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.hide_cv_yes_resume:
                if (checked)
                    // Pirates are the best
                    cvViews = "1";
                break;
            case R.id.hide_cv_no_resume:
                if (checked)
                    // Ninjas rule
                    cvViews = "0";
                break;
            default:
                cvViews = "0";
        }
    }


    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
        //   return FileProvider.getUriForFile(JResumeActivity.this,
        //    BuildConfig.APPLICATION_ID + ".provider",
        //  getOutputMediaFile(type));
    }

    public Uri getOutputMediaFileUriVersion24(int type) {
        // return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(JResumeActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputMediaFile(type));
    }
     /*
     * returning image / video
     */

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
                Glide.with(this)
                        .load(imageUri.getPath())
                        .placeholder(R.drawable.icon_and_button_01)

                        .into(imageViewResume);


                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(JResumeActivity.this,
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
                        pdUploadPhoto = new ProgressDialog(JResumeActivity.this);
                        pdUploadPhoto.setMessage("uploading ...");
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

             /*   File file=new File(imageUri.getPath());

                try {
                    resizeImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                final String imagePath = PathToRealPathHelper.getPath(JResumeActivity.this, imageUri);
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
                        pdUploadPhoto = new ProgressDialog(JResumeActivity.this);
                        pdUploadPhoto.setMessage("uploading ...");
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

                cvPath = PathToRealPathHelper.getPath(JResumeActivity.this, cvUri);
                verifyStoragePermissions(this);
                //  Toast.makeText(getApplicationContext()," Cv Response" + cvPath,Toast.LENGTH_LONG).show();
                useUploadCv(applicantId, cvPath, token);

                pdUploadCV = new ProgressDialog(JResumeActivity.this);
                pdUploadCV.setMessage("uploading ...");
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


    public File getOutputMediaFileVersion24() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";


        /*
       // this Enviroment directory works
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        */

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


    public void resizeImage(File file) throws IOException {

        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
        // original measurements
        int origWidth = b.getWidth();
        int origHeight = b.getHeight();

        final int destWidth = 600;//or the width you need

        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            int destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            Bitmap b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);

            // create a matrix object
            Matrix matrix = new Matrix();
            //  matrix.postRotate(90); // anti-clockwise by 90 degrees

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

        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + file.getAbsolutePath();
        Log.e("Current Photo Path", "Path" + mCurrentPhotoPath);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    public void savestate() {
        Log.e("JRSave State", "JRSave State" + u_type);

        SharedPreferences sh = getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor ed = sh.edit();
        ed.putBoolean(JLoginActivity.LOGIN, true);
        ed.putString(JLoginActivity.USER_ID, userId);
        ed.commit();
        db_control.insertData(userId, user_name, password);

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
                PreName.setText(" " + getResources().getString(R.string.U) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
            case R.id.koBTN:
                uKoLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(" " + getResources().getString(R.string.Ko) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
            case R.id.Mrs_BTN:
                uKoLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(" " + getResources().getString(R.string.Mr) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
            case R.id.maBTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(" " + getResources().getString(R.string.Ma) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
            case R.id.Mriss_BTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(" " + getResources().getString(R.string.Ms) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
            case R.id.dawBTN:
                maDawLayout.setVisibility(View.GONE);
                showEdit.setVisibility(View.VISIBLE);
                PreName.setText(" " + getResources().getString(R.string.Daw) + " ");
                inPutName.setEnabled(true);
                inPutName.requestFocus();
                break;
        }

    }
}
