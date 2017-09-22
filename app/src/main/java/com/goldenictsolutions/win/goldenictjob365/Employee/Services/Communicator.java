package com.goldenictsolutions.win.goldenictjob365.Employee.Services;

import android.util.Log;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Applicant;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Education;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Experience;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.OtherQualification;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Skill;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddEducation_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddLanguage_Error_Event;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.AddQua_Error_Event;
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
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerCountryEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEducationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerExperienceEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetEducationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetExperienceEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetQualificationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerGetSkillEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerJobCategoryEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerOtherQualificationEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerRefereeEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerSkillEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerTownshipEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUpLoadCVEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadCompanyFirstLogoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadCompanyLogoEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.ServerUploadResumeEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.TownshipErrorEvent;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Event.Update_Error_Event;
import com.squareup.otto.Produce;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Communicator {
    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://allreadymyanmar.com/";

    public void getCityById() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getCityById();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "City Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerCityEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new CityErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getCountryById() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getCountryById();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerCountryEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getTownshipById(int cityId) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getTownshipById(cityId);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Township ID" + response.code());
                Log.e(TAG, "Success Township ID" + response.body());
                Log.e(TAG, "Success Township ID" + response.message());
                BusProvider.getInstance().post(new ServerTownshipEvent(response.body()));
                Log.e(TAG, "Success Township ID");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new TownshipErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getJobCategory() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getJobCategory();

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Job Success" + response.code());

                Log.e(TAG, "Job Success" + response.body());
                Log.e(TAG, "Job Success" + response.message());
                BusProvider.getInstance().post(new ServerJobCategoryEvent(response.body()));
                Log.e(TAG, "Job Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new JobErrorEvent(-2, t.getMessage()));
            }
        });
    }


    public void getTownship() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getTownship();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Town Success" + response.code());

                Log.e(TAG, "Town Success" + response.body());
                Log.e(TAG, "Town Success" + response.message());
                BusProvider.getInstance().post(new ServerTownshipEvent(response.body()));
                Log.e(TAG, "Town Success");

            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getEducationById(String userId, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getEducationById(userId, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Get Education" + response.code());
                Log.e(TAG, "Success Get Education" + response.body());
                Log.e(TAG, "Success Get Education" + response.message());
                BusProvider.getInstance().post(new ServerGetEducationEvent(response.body()));
                Log.e(TAG, "Success Get Education");

            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new GetEducationErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getQualificationById(String userId, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getQualificationById(userId, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Get Qualification" + response.code());

                Log.e(TAG, "Success Get Qualification" + response.body());
                Log.e(TAG, "Success Get Qualification" + response.message());
                BusProvider.getInstance().post(new ServerGetQualificationEvent(response.body()));
                Log.e(TAG, "Success Get Qualification");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new GetQualificationErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getExperienceById(String userId, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45, TimeUnit.SECONDS)
                        .connectTimeout(45, TimeUnit.SECONDS)
                        .writeTimeout(45, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.getExpereinceById(userId, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Get Experience 1 " + response.code());

                Log.e(TAG, "Success Get Experience 2 " + response.body());
                Log.e(TAG, "Success Get Experience 3" + response.message());
                BusProvider.getInstance().post(new ServerGetExperienceEvent(response.body()));
                Log.e(TAG, "Success Get Experience 4");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new GetExperienceErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void getSkillById(String userId, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(35, TimeUnit.SECONDS)
                        .connectTimeout(35, TimeUnit.SECONDS)
                        .writeTimeout(35, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getSkillById(userId, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Get Skill" + response.code());

                Log.e(TAG, "Success Get Skill" + response.body());
                Log.e(TAG, "Success Get Skill" + response.message());
                BusProvider.getInstance().post(new ServerGetSkillEvent(response.body()));
                Log.e(TAG, "Success Get Skill");

            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new GetSkillErrorEVent(-2, t.getMessage()));
            }
        });
    }

    public void uploadCV(String userId, String imagePath, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS);

        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);
        File file = new File(String.valueOf(imagePath));


        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body1 = MultipartBody.Part.createFormData("attach_cv", file.getName(), requestFile);
/*
        // add another part within the multipart request
        String descriptionString = "attach_cv";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
*/

        Log.e("File Name ","Name"+file);
        Call<ServerResponse> call = service.uploadCvFile(userId, body1, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerUpLoadCVEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorUploadCVEvent(-2, t.getMessage()));
            }
        });
    }

    public void uploadCompanyLogo(String userId, String imagePath, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);
        File file = new File(String.valueOf(imagePath));


        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body1 = MultipartBody.Part.createFormData("logo", file.getName(), requestFile);
        Call<ServerResponse> call = service.uploadCompanyLogo(userId, body1, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Logo" + response.code());

                Log.e(TAG, "Success Logo" + response.body());
                Log.e(TAG, "Success Logo" + response.message());
                BusProvider.getInstance().post(new ServerUploadCompanyLogoEvent(response.body()));
                Log.e(TAG, "Success Logo");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorUploadPhotoEvent(-2, t.getMessage()));
            }
        });
    }


    public void uploadCompanyFirstLogo(String userId, String imagePath, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);
        File file = new File(String.valueOf(imagePath));

        RequestBody userID = RequestBody.create(MultipartBody.FORM, userId);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body1 = MultipartBody.Part.createFormData("logo", file.getName(), requestFile);
        Call<ServerResponse> call = service.uploadCompanyFirstLogo(userID, body1, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Logo" + response.code());

                Log.e(TAG, "Success Logo" + response.body());
                Log.e(TAG, "Success Logo" + response.message());
                BusProvider.getInstance().post(new ServerUploadCompanyFirstLogoEvent(response.body()));
                Log.e(TAG, "Success Logo");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorUploadPhotoEvent(-2, t.getMessage()));
            }
        });
    }


    public void uploadResumePhoto(String userId, String imagePath, String api_key) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);
        File file = new File(String.valueOf(imagePath));


        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body1 = MultipartBody.Part.createFormData("resume-photo", file.getName(), requestFile);
        Call<ServerResponse> call = service.uploadResumePhoto(userId, body1, api_key);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerUploadResumeEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorUploadPhotoEvent(-2, t.getMessage()));
            }
        });
    }

    public void insertSkill(String userId, String skillType, String skillLevel, String skillWLevel, String token) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(55, TimeUnit.SECONDS)
                        .connectTimeout(55, TimeUnit.SECONDS)
                        .writeTimeout(55, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.insertSkill(new Skill(userId, skillType, skillLevel, skillWLevel), token);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerSkillEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new AddLanguage_Error_Event(-2, t.getMessage()));
            }
        });
    }

    public void insertExperience(String userId, String expOrganization, String expRank, String expStartDate, String expEndDate, String token) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(55, TimeUnit.SECONDS)
                        .connectTimeout(55, TimeUnit.SECONDS)
                        .writeTimeout(55, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.insertExperience(new Experience(userId, expOrganization, expRank, expStartDate, expEndDate), token);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());
                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerExperienceEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new AddQua_Error_Event(-2, t.getMessage()));
            }
        });
    }

    public void updateApplicantById(String Id, String gender, String cvViews, String name, String fatherName,
                                    String dateOfBirth, String pob, String maritalStatus, String nationality,
                                    String religion, String nrc,
                                    String mobileNo, String email, String expectedSalary, String address, String township
            , String city, String current_position, String desired_position, int chkDriving, String apiKey) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.retryOnConnectionFailure(true);

      /*  Log.e("2020202", "" + cvViews);
        Log.e("2020202", "" + chkDriving);*/
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.updateApplicantById(Id, new Applicant(Id, gender, cvViews, name, fatherName
                , dateOfBirth, pob, maritalStatus, nationality, religion, nrc, mobileNo, email, expectedSalary, address
                , township, city, current_position, desired_position, chkDriving), apiKey);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerApplicantEvent(response.body()));
                Log.e(TAG, "Success");

            }

            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new Update_Error_Event(-2, t.getMessage()));
            }
        });
    }

    public void insertEducation(String userId, String university, String degree, String start_year, String end_year, String token) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(55, TimeUnit.SECONDS)
                        .connectTimeout(55, TimeUnit.SECONDS)
                        .writeTimeout(55, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.insertEducation(new Education(userId, university, degree, start_year, end_year), token);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());

                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerEducationEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new AddEducation_Error_Event(-2, t.getMessage()));
            }
        });
    }

    public void insertOtherQualification(String userId, String centerName, String course, String sDate, String eDate, String token) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(55, TimeUnit.SECONDS)
                        .writeTimeout(55, TimeUnit.SECONDS)
                        .connectTimeout(55, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.insertOtherQualification(new OtherQualification(userId, centerName, course, sDate, eDate), token);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success Insert Qualification" + response.code());

                Log.e(TAG, "Success Insert Qualification" + response.body());
                Log.e(TAG, "Success Insert Qualification" + response.message());
                BusProvider.getInstance().post(new ServerOtherQualificationEvent(response.body()));
                Log.e(TAG, "Success Insert Qualification");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new AddQua_Error_Event(-2, t.getMessage()));
            }
        });
    }

    public void getApplicant(String userId) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.readTimeout(45,TimeUnit.SECONDS)
                        .connectTimeout(45,TimeUnit.SECONDS)
                        .writeTimeout(45,TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);


        Call<ServerResponse> call = service.getApplicant(userId);

        call.enqueue(new Callback<ServerResponse>() {
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                Log.e(TAG, "Success" + response.code());
                Log.e(TAG, "Success" + response.body());
                Log.e(TAG, "Success" + response.message());
                BusProvider.getInstance().post(new ServerApplicantWithIDEvent(response.body()));
                Log.e(TAG, "Success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                Log.e(TAG, "Failure " + t.getMessage());
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ServerUpLoadCVEvent produceServerUploadCVEvent(ServerResponse serverResponse) {
        return new ServerUpLoadCVEvent(serverResponse);
    }

    @Produce
    public ServerUploadResumeEvent produceServerUploadResumeEvent(ServerResponse serverResponse) {
        return new ServerUploadResumeEvent(serverResponse);
    }

    @Produce
    public ServerUploadCompanyLogoEvent produceServerUploadCompanyLogoEvent(ServerResponse serverResponse) {
        return new ServerUploadCompanyLogoEvent(serverResponse);
    }


    @Produce
    public ServerSkillEvent produceServerSkillEvent(ServerResponse serverResponse) {
        return new ServerSkillEvent(serverResponse);
    }

    @Produce
    public ServerEducationEvent produceServerEducationEvent(ServerResponse serverResponse) {
        return new ServerEducationEvent(serverResponse);
    }

    @Produce
    public ServerExperienceEvent produceServerExperienceEvent(ServerResponse serverResponse) {
        return new ServerExperienceEvent(serverResponse);
    }

    @Produce
    public ServerRefereeEvent produceServerRefereeEvent(ServerResponse serverResponse) {
        return new ServerRefereeEvent(serverResponse);
    }

    @Produce
    public ServerCityEvent produceServerCityEvent(ServerResponse serverResponse) {
        return new ServerCityEvent(serverResponse);
    }

}
