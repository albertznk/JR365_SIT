package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.goldenictsolutions.win.goldenictjob365.BuildConfig;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.Employee.Helper.PathToRealPathHelper;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.BusProvider;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ErrorUploadPhotoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadCompanyFirstLogoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadCompanyLogoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.City_List;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Company_Profile_Data;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Job_Category;
import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.Township_List;
import com.goldenictsolutions.win.goldenictjob365.JLoginActivity;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EmployerProfile extends AppCompatActivity implements View.OnClickListener {
    Button upload_logo, save;
    ProgressDialog pd;
    ArrayList<Job_Category> job_categories = new ArrayList<>();
    ArrayList<City_List> city = new ArrayList<>();
    ArrayList<Township_List> township = new ArrayList<>();
    ArrayList<Company_Profile_Data> company_profile_data = new ArrayList<>();
    String link = "http://allreadymyanmar.com/api/company/";
    Communicator commu;
    String filename;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String user_name, user_password;
    private String mCurrentPhotoPath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "JobReady 365";
    String company_id;
    Button back;
    EditText business_name, telephone, primary_telephone, secondary_telephone, address, about_company, email, primary_person_name, secondary_person_name;
    String userID = "";
    TextView toolbar_Text;
    int oki, oki2, oki3;
    ArrayList job_category_list = new ArrayList();
    ArrayList city_list = new ArrayList();
    ArrayList township_list = new ArrayList();
    Spinner state_spinner, city_spinner, job;
    boolean state_condition = false, city_condition = false, job_condition = false;
    boolean newOrOld = false;
    private String state_name, city_name, job_name;
    boolean uploaddoneornot = false;
    Dialog dialog;
    private String token;
    private ProgressDialog pdUploadPhoto;
    ScrollView scrollview;
    ProgressBar pb;
    private String newcompanyid;
    DB_Control db_control;
    SharedPreferences sh;
    SharedPreferences.Editor ed;
    GetJob getJob;
    private int offically_new;
    String remain_time, package_type;
    private TextView tv_attach_logo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile);

        findID();
        if (getIntent().getExtras().getInt("new") == 1) {
            userID = getIntent().getExtras().getString("user_id");
            user_name = getIntent().getExtras().getString("login_username");
            user_password = getIntent().getExtras().getString("login_userpassword");
            offically_new = getIntent().getExtras().getInt("new_ha");

            Log.e("Official New", "" + offically_new);

            newOrOld = true;
            uploaddoneornot = true;
            package_type = getIntent().getExtras().getString("package_type");
            remain_time = getIntent().getExtras().getString("remain_time");
            if (getIntent().getExtras().getInt("new_ha") == 1) {
                back.setVisibility(View.GONE);

            }
            //shop_or_company=true;

            getJob = new GetJob();
            getJob.execute();
            // new GetJob().execute();
        } else if (getIntent().getExtras().getInt("new") == 0) {
            company_id = getIntent().getExtras().getString("company_id");
            package_type = getIntent().getExtras().getString("package_type");
            remain_time = getIntent().getExtras().getString("remain_time");
       /*     if (type.equals("1")) {
                toolbar_Text.setText("Shop Profile");
            } else {
                toolbar_Text.setText("Company Profile");
            }
            Log.e("Type123", "" + type);*/
            state_condition = true;
            job_condition = true;
            city_condition = true;
            //shop_or_company=true;
            Log.e("Company _ID", company_id + "");
            new GetCompany_Profile().execute(link + company_id);
        }

    }

    private void findID() {
        tv_attach_logo= (TextView) findViewById(R.id.tv_attach_logo);
        db_control = new DB_Control(this);
        commu = new Communicator();
        //company_radio = (RadioButton) findViewById(R.id.company_radio);
        //shop_radio = (RadioButton) findViewById(R.id.shop_radio);
        pb = (ProgressBar) findViewById(R.id.progressbar);
        scrollview = (ScrollView) findViewById(R.id.scroll_view);
        dialog = new Dialog(EmployerProfile.this);
        back = (Button) findViewById(R.id.profile_back);
        toolbar_Text = (TextView) findViewById(R.id.toolbar_text);
        job = (Spinner) findViewById(R.id.busisness_type_spinner);
        state_spinner = (Spinner) findViewById(R.id.state_spinner_company);
        city_spinner = (Spinner) findViewById(R.id.city_spinner_company);
        upload_logo = (Button) findViewById(R.id.attach_company_logo);
        save = (Button) findViewById(R.id.save);
        business_name = (EditText) findViewById(R.id.et_business_name);
        telephone = (EditText) findViewById(R.id.company_Tele);
        email = (EditText) findViewById(R.id.company_email);
        address = (EditText) findViewById(R.id.company_address);
        primary_person_name = (EditText) findViewById(R.id.company_et_primary_person);
        secondary_person_name = (EditText) findViewById(R.id.company_et_sec_person);
        primary_telephone = (EditText) findViewById(R.id.company_et_primary_telephone);
        secondary_telephone = (EditText) findViewById(R.id.company_et_sec_telephone);
        about_company = (EditText) findViewById(R.id.company_et_company_about);
        toolbar_Text.setText(getResources().getString(R.string.Company_Profile));
        upload_logo.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        //rgp.setOnCheckedChangeListener(this);

        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                job_name = job_categories.get(i).getId();
                Log.e("Job_name", "" + job_name);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onBackPressed() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_back:
             /*   Intent i = new Intent(EmployerProfile.this, EmployerFragHost.class);
                i.putExtra("remain_time", remain_time);
                i.putExtra("package_type", package_type);
                startActivity(i);*/
                finish();
                break;

            case R.id.attach_company_logo:
                ImageView image = new ImageView(EmployerProfile.this);
                //   image.setImageResource(R.drawable.logo);

                Glide.with(EmployerProfile.this)
                        .load(R.drawable.true_logo)
                        .into(image);
                Dialog d = new AlertDialog.Builder(EmployerProfile.this, AlertDialog.THEME_HOLO_LIGHT)

                        .setIcon(R.drawable.icon_and_button_01)

                        .setTitle("Take your Photo")
                        .setNegativeButton("Cancel", null)

                        .setItems(new String[]{"Take new photo", "Choose photo from Galaxy", "", ""}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int position) {
                                if (position == 0) {
                                    //  dispatchTakePictureIntent();
                                    verifyStoragePermissions(EmployerProfile.this);
                                    try {
                                        captureImage(getApplicationContext(), EmployerProfile.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else if (position == 1) {
                                    verifyStoragePermissions(EmployerProfile.this);
                                    showImagePopup(EmployerProfile.this);
                                }
                            }
                        })
                        .create();
                d.show();
                break;
            case R.id.save:
                if (!business_name.getText().toString().isEmpty() &&
                        !telephone.getText().toString().isEmpty() &&
                        !address.getText().toString().isEmpty() &&
                        !primary_person_name.getText().toString().isEmpty() &&
                        !primary_telephone.getText().toString().isEmpty()) {
                    if (newOrOld == true && uploaddoneornot == true) {
                        new UploadProfile().execute(userID,
                                business_name.getText().toString(),         //1
                                telephone.getText().toString(),             //2
                                email.getText().toString(),                 //3
                                address.getText().toString(),               //4
                                primary_person_name.getText().toString(),   //5
                                primary_telephone.getText().toString(),     //6
                                secondary_person_name.getText().toString(), //7
                                secondary_telephone.getText().toString(),   //8
                                about_company.getText().toString(), state_name, city_name, job_name);
                    } else if (newOrOld == true && uploaddoneornot == false) {
                        new UpdateProfile().execute(newcompanyid,
                                business_name.getText().toString(),
                                telephone.getText().toString(),
                                email.getText().toString(),
                                address.getText().toString(),
                                primary_person_name.getText().toString(),
                                primary_telephone.getText().toString(),
                                secondary_person_name.getText().toString(),
                                secondary_telephone.getText().toString(),
                                about_company.getText().toString(), state_name, city_name, job_name);

                    } else {
                        new UpdateProfile().execute(company_id,
                                business_name.getText().toString(),
                                telephone.getText().toString(),
                                email.getText().toString(),
                                address.getText().toString(),
                                primary_person_name.getText().toString(),
                                primary_telephone.getText().toString(),
                                secondary_person_name.getText().toString(),
                                secondary_telephone.getText().toString(),
                                about_company.getText().toString(), state_name, city_name, job_name);
                    }

                } else {
                    Toast.makeText(EmployerProfile.this, "Required Field are missing", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void ErrorEvent(ErrorUploadPhotoEvent event) {
        pdUploadPhoto.dismiss();
        Toast.makeText(EmployerProfile.this, "Check Your Connection & Try again", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void uploadFirstLogoEvent(ServerUploadCompanyFirstLogoEvent event) {
        if (event.getServerResponse().getStatusCode() == 200) {
            newcompanyid = event.getServerResponse().getCompany_photo();
            uploaddoneornot = false;
            dialog.dismiss();
            pdUploadPhoto.dismiss();
            tv_attach_logo.setHint("Photo Received");
            Toast.makeText(EmployerProfile.this, filename + " is successfully upload..", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EmployerProfile.this, "File upload Fail....", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void uploadlogoEvent(ServerUploadCompanyLogoEvent event) {
        Log.e("Lolz", "______" + event.getServerResponse().getStatusCode());
        Log.e("Lolz", "______" + event.getServerResponse().getResult());
        Log.e("Lolz", "______" + event.getServerResponse().getMessage());
        Log.e("Lolz", "______" + event.getServerResponse().getStatus());
        Log.e("Lolz", "______" + event.getServerResponse().getCompany_photo());
        if (event.getServerResponse().getStatusCode() == 210) {
            pdUploadPhoto.dismiss();
            Toast.makeText(EmployerProfile.this, event.getServerResponse().getResult(), Toast.LENGTH_SHORT).show();
        } else if (event.getServerResponse().getStatusCode() == 200) {
            dialog.dismiss();
            pdUploadPhoto.dismiss();
            tv_attach_logo.setHint("Photo Received");
            Toast.makeText(EmployerProfile.this, filename + " is successfully upload", Toast.LENGTH_SHORT).show();
        } else {
            pdUploadPhoto.dismiss();
            Toast.makeText(EmployerProfile.this, "Connection Lost", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadlogo(String userId, String uri, String token) {
        commu.uploadCompanyLogo(userId, uri, token);
    }

    private void uploadFirstlogo(String userId, String uri, String token) {
        commu.uploadCompanyFirstLogo(userId, uri, token);
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            // successfully captured the image
            if (resultCode == RESULT_OK) {
                dialog.setContentView(R.layout.dialog_image_view);
                // Set dialog title
                dialog.setTitle("Image to upload");

                ImageView imageViewResume = (ImageView) dialog.findViewById(R.id.imageView_resume_photo);

                final Uri imageUri = Uri.parse(mCurrentPhotoPath);
                String path = imageUri.toString();
                filename = path.substring(path.lastIndexOf("/") + 1);
                Log.e("File Name", "" + filename);
                //final Uri imageUri = Uri.parse(mCurrentPhotoPath);
                // Show the thumbnail on ImageView
                File file = new File(imageUri.getPath());

                try {
                    resizeImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(this)
                        .load(imageUri.getPath())
                        .placeholder(R.drawable.placeholder_loading_image)
                        .into(imageViewResume);


                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(EmployerProfile.this,
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
                        Log.e("OOPPP", "" + imageUri.getPath());

                        if (uploaddoneornot) {
                            Log.e("USERID", "" + userID);
                            uploadFirstlogo(userID, imageUri.getPath(), token);
                            pdUploadPhoto = new ProgressDialog(EmployerProfile.this);
                            pdUploadPhoto.setMessage(getResources().getString(R.string.Uploading));
                            pdUploadPhoto.setCancelable(false);
                            pdUploadPhoto.show();
                        } else {
                            uploadlogo(company_id, imageUri.getPath(), token);
                            pdUploadPhoto = new ProgressDialog(EmployerProfile.this);
                            pdUploadPhoto.setMessage(getResources().getString(R.string.Uploading));
                            pdUploadPhoto.setCancelable(false);
                            pdUploadPhoto.show();
                        }

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
                dialog.setTitle("Image to upload");
                ImageView imageViewResume = (ImageView) dialog.findViewById(R.id.imageView_resume_photo);
                final Uri imageUri = data.getData();
                //  imageViewResume.setImageURI(imageUri);
                //      imageViewResume.setImageBitmap(BitmapFactory.decodeFile(imageUri.getPath()));

                //      imagePath = getRealPathFromURI(imageUri);
                final String imagePath = PathToRealPathHelper.getPath(EmployerProfile.this, imageUri);
                filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                Log.e("File Name", "" + filename);

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


                        if (uploaddoneornot) {
                            Log.e("Get From Gallary", "" + userID);
                            uploadFirstlogo(userID, imagePath, token);
                            pdUploadPhoto = new ProgressDialog(EmployerProfile.this);
                            pdUploadPhoto.setMessage(getResources().getString(R.string.Uploading));
                            pdUploadPhoto.setCancelable(false);
                            pdUploadPhoto.show();

                        } else {
                            uploadlogo(company_id, imagePath, token);
                            pdUploadPhoto = new ProgressDialog(EmployerProfile.this);
                            pdUploadPhoto.setMessage(getResources().getString(R.string.Uploading));
                            pdUploadPhoto.setCancelable(false);
                            pdUploadPhoto.show();

                        }

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
        }
    }


    public void savestate() {
        Log.e("Save tl", "");
        sh = getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
        ed = sh.edit();
        ed.putBoolean(JLoginActivity.LOGIN, true);
        ed.putString(JLoginActivity.USER_ID, userID);
        ed.putString(JLoginActivity.USER_TOKEN, token);

        ed.commit();

        db_control.insertData(userID, user_name, user_password);
        db_control.closeDb();

        Log.e("Save Pee tl", "");
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
            //   matrix.postRotate(90); // anti-clockwise by 90 degrees

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
    }

    /*@Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.company_radio:
                type = "1";
                if (getJob.getStatus() == AsyncTask.Status.RUNNING) {
                    getJob.cancel(true);

                }
                getJob = new GetJob();
                getJob.execute(type);

                Toast.makeText(EmployerProfile.this, "Company", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shop_radio:
                type = "2";
                if (getJob.getStatus() == AsyncTask.Status.RUNNING) {
                    getJob.cancel(true);

                }
                getJob = new GetJob();
                getJob.execute(type);
                Toast.makeText(EmployerProfile.this, "Shop", Toast.LENGTH_SHORT).show();
                break;

        }
    }*/

    public class GetCity extends AsyncTask<Object, Object, ArrayList<City_List>> {
        protected ArrayList<City_List> doInBackground(Object[] objects) {

            try {
                JSONArray job_list = null;
                OkHttpClient httpClient = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json;charset = UTF-8");
                Request request = new Request.Builder()
                        .url("http://allreadymyanmar.com/api/city")
                        .get()
                        .build();

                Response response = httpClient.newCall(request).execute();
                String jsonResponse = response.body().string();
                Log.i("Response", "doInBackground: " + jsonResponse);
                if (jsonResponse != null) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    job_list = jsonObject.getJSONArray("city");
                    for (int i = 0; i < job_list.length(); i++) {
                        JSONObject c = job_list.getJSONObject(i);
                        city.add(new City_List(
                                c.getString("city"), c.getString("id")));

                    }
                    Log.i("Json Response", "doInBackground: City Size" + city.size());
                    return city;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "doInBackground: City" + e);
            } catch (JSONException e) {
                Log.e("Error", "doInBackground: City" + e);
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final ArrayList<City_List> arrayList) {
            Log.e("City List", "" + arrayList.size());
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        city_list.add(arrayList.get(i).getName());
                    }
                    ArrayAdapter<City_List> ae = new ArrayAdapter<City_List>(getApplicationContext(), R.layout.custom_layout_for_spinner, city_list);
                    state_spinner.setAdapter(ae);
                    if (state_condition) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (company_profile_data.get(0).getCity().equals(arrayList.get(i).getName())) {
                                oki2 = i;
                                state_condition = false;
                                break;
                            }
                        }
                    }

                    state_spinner.setSelection(oki2);
                    state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            state_name = arrayList.get(i).getId();
                            city_spinner.setEnabled(false);
                            new GetTownship().execute(arrayList.get(i).getId());
                        }

                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            } else {
                new GetCity().execute();
            }

        }
    }

    public class GetJob extends AsyncTask<String, Object, ArrayList<Job_Category>> {
        protected void onPreExecute() {
            job_categories.clear();
            job_category_list.clear();
        }

        protected void onPostExecute(ArrayList<Job_Category> arrayList) {
            Log.e("Job Post Exe", "");
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    job_category_list.add(arrayList.get(i).getJob_name());
                }
                new GetCity().execute();
                ArrayAdapter<Job_Category> a = new ArrayAdapter<Job_Category>(getApplicationContext(), R.layout.custom_layout_for_spinner, job_category_list);
                job.setAdapter(a);
                if (job_condition) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (company_profile_data.get(0).getJob_industry().equals(arrayList.get(i).getJob_name())) {
                            oki = i;
                            job_condition = false;
                            Log.e("Lote tl", "" + oki);
                            break;
                        }
                    }
                    job.setSelection(oki);
                }

            } else {
                new GetJob().execute();
            }
        }

        protected ArrayList<Job_Category> doInBackground(String... objects) {
            try {
                JSONArray job_list = null;
                OkHttpClient httpClient = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json;charset = UTF-8");
                //Log.e("JOB_Name", "" + objects[0]);
                Request request = new Request.Builder()
                        .url("http://allreadymyanmar.com/api/industry/1")
                        .get()
                        .build();
                Response response = httpClient.newCall(request).execute();
                String jsonResponse = response.body().string();
                Log.i("Response", "doInBackground: " + jsonResponse);
                if (jsonResponse != null) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    job_list = jsonObject.getJSONArray("jobindustry");
                    for (int i = 0; i < job_list.length(); i++) {
                        JSONObject c = job_list.getJSONObject(i);
                        job_categories.add(new Job_Category(
                                c.getString("job_industry"), c.getString("id")));

                    }
                    Log.i("Json Response", "doInBackground: Job Size" + job_categories.size());
                    return job_categories;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "doInBackground: Job" + e);
            } catch (JSONException e) {
                Log.e("Error", "doInBackground: " + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public class GetCompany_Profile extends AsyncTask<String, Object, ArrayList<Company_Profile_Data>> {

        JSONArray company_profile_list = null;

        protected ArrayList<Company_Profile_Data> doInBackground(String... params) {
            OkHttpClient httpClient = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json;charset = UTF-8");
            Request request = new Request.Builder()
                    .url(params[0])
                    .get()
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                String jsonResponse = response.body().string();
                Log.i("Response", "doInBackground: " + jsonResponse);
                if (jsonResponse != null) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    company_profile_list = jsonObject.getJSONArray("company");
                    for (int i = 0; i < company_profile_list.length(); i++) {
                        JSONObject c = company_profile_list.getJSONObject(i);
                        company_profile_data.add(new Company_Profile_Data(
                                c.getString("id"),
                                c.getString("company_name"),
                                c.getString("company_logo"),
                                c.getString("address"),
                                c.getString("township"),
                                c.getString("job_industry"),
                                c.getString("city"),
                                c.getString("mobile_no"),
                                c.getString("email"),
                                c.getString("website"),
                                c.getString("description"),
                                c.getString("primary_contact_person"),
                                c.getString("primary_mobile"),
                                c.getString("secondary_contact_person"),
                                c.getString("secondary_mobile"),
                                c.getString("country")
                        ));
                    }
                    Log.i("Json Response", "doInBackground: Info Size" + company_profile_data.size());
                    return company_profile_data;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "doInBackground: Company Profile 1" + e);
            } catch (JSONException e) {
                Log.e("Error", "doInBackground: Company Profile " + e);
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(ArrayList<Company_Profile_Data> arrayList) {
            if (arrayList != null) {

                new GetJob().execute();
                business_name.setText(arrayList.get(0).getCompany_name());
                address.setText(arrayList.get(0).getAddress());
                telephone.setText(arrayList.get(0).getMobile_no());
                email.setText(arrayList.get(0).getEmail());
                primary_person_name.setText(arrayList.get(0).getPrimary_person());
                primary_telephone.setText(arrayList.get(0).getPrimary_tele());
                secondary_person_name.setText(arrayList.get(0).getSecodary_person());
                secondary_telephone.setText(arrayList.get(0).getSecondary_tele());
                about_company.setText(arrayList.get(0).getDescription());
                if(arrayList.get(0).getCompany_logo()!=null){
                    tv_attach_logo.setText("Photo Received");
                }
            } else {
                new GetCompany_Profile().execute(link + userID);
            }
        }
    }

    public class GetTownship extends AsyncTask<String, Object, ArrayList<Township_List>> {
        protected void onPreExecute() {
            township.clear();

        }

        protected void onPostExecute(final ArrayList<Township_List> arrayList) {
            if (arrayList != null) {
                township_list.clear();
                city_spinner.setEnabled(true);
                pb.setVisibility(View.GONE);
                for (int i = 0; i < arrayList.size(); i++) {
                    township_list.add(arrayList.get(i).getTownshipName());
                }
                ArrayAdapter<Township_List> a = new ArrayAdapter<Township_List>(getApplicationContext(), R.layout.custom_layout_for_spinner, township_list);
                city_spinner.setAdapter(a);
                if (city_condition) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (company_profile_data.get(0).getTownship().equals(arrayList.get(i).getTownshipName())) {
                            oki3 = i;
                            city_condition = false;
                            break;
                        }
                    }
                    city_spinner.setSelection(oki3);
                }
                scrollview.setVisibility(View.VISIBLE);

                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        city_name = arrayList.get(i).getId();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else {
                new GetTownship().execute(state_name);
            }
        }

        protected ArrayList<Township_List> doInBackground(String... objects) {

            try {
                JSONArray job_list = null;
                OkHttpClient httpClient = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json;charset = UTF-8");
                Request request = new Request.Builder()
                        .url("http://allreadymyanmar.com/api/township/" + objects[0])
                        .get()
                        .build();
                Response response = httpClient.newCall(request).execute();
                String jsonResponse = response.body().string();
                Log.i("Response", "doInBackground: " + jsonResponse);
                if (jsonResponse != null) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    job_list = jsonObject.getJSONArray("township");
                    for (int i = 0; i < job_list.length(); i++) {
                        JSONObject c = job_list.getJSONObject(i);
                        township.add(new Township_List(
                                c.getString("township"), c.getString("id")));

                    }
                    Log.i("Json Response", "doInBackground: City Size" + township.size());
                    return township;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", "doInBackground: township " + e);
            } catch (JSONException e) {
                Log.e("Error", "doInBackground township " + e);
                e.printStackTrace();
            }
            return null;
        }
    }


    public class UpdateProfile extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient.Builder().connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS).build();
        private String UpdateLink = "http://allreadymyanmar.com/api/company/";
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {

            pd = new ProgressDialog(EmployerProfile.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.setMessage("Please Wait");
            pd.show();

            save.setEnabled(false);
        }

        protected String doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            try {

                //Log.e("OPQP", "" + params[3]);
                jsonObject.put("id", params[0]);
                jsonObject.put("company_name", params[1]);
                jsonObject.put("mobile_no", params[2]);
                jsonObject.put("email", params[3]);
                jsonObject.put("address", params[4]);
                jsonObject.put("primary_contact_person", params[5]);
                jsonObject.put("primary_mobile", params[6]);
                jsonObject.put("secondary_contact_person", params[7]);
                jsonObject.put("secondary_mobile", params[8]);
                jsonObject.put("description", params[9]);
                jsonObject.put("city", params[10]);
                jsonObject.put("township", params[11]);
                jsonObject.put("job_industry", params[12]);
                //jsonObject.put("type", params[13]);


                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonObject));
                Request request = new Request.Builder()
                        .url(UpdateLink + params[0])
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Transfer-Encoding", "chunked")
                        .put(body)
                        .build();
                Response response = client.newCall(request).execute();
                Log.e("UIOP", "" + response.code());
                return response.message().toString();
            } catch (JSONException e) {
                Log.e("JSON PUT ERROR", "doInBackground: " + e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("JSON POST ERROR", "doInBackground: " + e);
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            Log.e("Post Execute", "");
            pd.dismiss();
            if (s != null) {
                Toast.makeText(EmployerProfile.this, s, Toast.LENGTH_SHORT).show();

                if (offically_new == 1) {
                    db_control.openDb();
                    savestate();
                }

                Intent i = new Intent(EmployerProfile.this, EmployerFragHost.class);
                i.putExtra("remain_time", remain_time);
                i.putExtra("package_type", package_type);
                //i.putExtra("user_id", userID);
                startActivity(i);
                finish();
            } else {
                save.setEnabled(true);
                Log.e("bla","bla");
                //Toast.makeText(getApplicationContext(), " !! Error !!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UploadProfile extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient.Builder().connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS).build();
        private String Upload = "http://allreadymyanmar.com/api/company";
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            pd = new ProgressDialog(EmployerProfile.this);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            save.setEnabled(false);
        }

        protected String doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", params[0]);
                jsonObject.put("company_name", params[1]);
                jsonObject.put("mobile_no", params[2]);
                jsonObject.put("email", params[3]);
                jsonObject.put("address", params[4]);
                jsonObject.put("primary_contact_person", params[5]);
                jsonObject.put("primary_mobile", params[6]);
                jsonObject.put("secondary_contact_person", params[7]);
                jsonObject.put("secondary_mobile", params[8]);
                jsonObject.put("description", params[9]);
                jsonObject.put("city", params[10]);
                jsonObject.put("township", params[11]);
                jsonObject.put("job_industry", params[12]);
                jsonObject.put("logo", "Oki");
                jsonObject.put("website", "Lolz");
                //jsonObject.put("type", params[13]);

                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonObject));
                Request request = new Request.Builder()
                        .url(Upload)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Transfer-Encoding", "chunked")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                Log.e("UIOP", "" + response.code());
                return response.message().toString();
            } catch (JSONException e) {
                Log.e("JSON PUT ERROR", "doInBackground: " + e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("JSON POST ERROR", "doInBackground: " + e);
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Toast.makeText(EmployerProfile.this, s, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EmployerProfile.this, EmployerFragHost.class);
                i.putExtra("remain_time", remain_time);
                i.putExtra("package_type", package_type);
                if (offically_new == 1) {
                    db_control.openDb();
                    savestate();
                }
                startActivity(i);
                finish();
            } else {
                save.setEnabled(true);
                Toast.makeText(EmployerProfile.this, " !! Upload Fail  !!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
