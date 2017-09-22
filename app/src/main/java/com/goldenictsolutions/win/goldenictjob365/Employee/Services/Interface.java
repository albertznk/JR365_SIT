package com.goldenictsolutions.win.goldenictjob365.Employee.Services;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.*;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Applicant;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface Interface {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            //  "Authorization: Basic ZWlwaHl1cGh5dWhsYWluZ0BnbWFpbC5jb206MTIzNDU2Nzg="
    })
    //@FormUrlEncoded
    @POST("/api/login")
        //Call<ServerResponse> post(@Body @Root("email") String email,@Root("password") String password);
    Call<ServerResponse> post(@Body login body);
    ///update
    @GET("/api/qualification/{user_id}")
    Call<ServerResponse> getQualificationById(@Path("user_id") String userId, @Query("token") String apiKey);

    //@FormUrlEncoded
    @POST("/api/register")
    //Call<ServerResponse> post(@Body @Root("email") String email,@Root("password") String password);
    Call<ServerResponse> postSignUp(@Body SignUp body);

    @GET("/api/city")
    Call<ServerResponse> getCityById();

    @GET("/api/country")
    Call<ServerResponse> getCountryById();

    @GET("/api/township/{city_id}")
    Call<ServerResponse> getTownshipById(@Path("city_id") int cityId);

    @GET("/api/township")
    Call<ServerResponse> getTownship();

    @GET("/api/category")
    Call<ServerResponse> getJobCategory();

    @GET("/api/education/{user_id}")
    Call<ServerResponse> getEducationById(@Path("user_id") String userId, @Query("token") String apiKey);

    @GET("/api/experience/{user_id}")
    Call<ServerResponse> getExpereinceById(@Path("user_id") String userId, @Query("token") String apiKey);

    @GET("/api/jobseeker/{user_id}")
    Call<ServerResponse> getApplicantById(@Path("user_id") String userId, @Query("token") String apiKey);

    @GET("/api/skill/{user_id}")
    Call<ServerResponse> getSkillById(@Path("user_id") String userId, @Query("token") String apiKey);


    @POST("/api/skill")
    Call<ServerResponse> insertSkill(@Body Skill body, @Query("token") String token);

    @POST("/api/experience")
    Call<ServerResponse> insertExperience(@Body Experience body, @Query("token") String token);

    @POST("/api/education")
    Call<ServerResponse> insertEducation(@Body Education body, @Query("token") String token);

    @POST("/api/qualification")
    Call<ServerResponse> insertOtherQualification(@Body OtherQualification body, @Query("token") String token);

    // update applicant by Id
    @PUT("/api/jobseeker/{id}")
    Call<ServerResponse> updateApplicantById(@Path("id") String Id, @Body Applicant body, @Query("token") String token);

    @GET("/api/jobseeker/{user_id}")
    Call<ServerResponse> getApplicant(@Path("user_id") String userId);

    @Multipart
    @POST("api/upload_picture/{id}")
    Call<ServerResponse> uploadResumePhoto(@Path("id") String Id,
                                           @Part MultipartBody.Part file, @Query("token") String token);

    @Multipart
    @POST("api/upload_logo/{id}")
    Call<ServerResponse> uploadCompanyLogo(@Path("id") String Id,
                                           @Part MultipartBody.Part file, @Query("token") String token);
    @Multipart
    @POST("api/upload_logo")
    Call<ServerResponse> uploadCompanyFirstLogo(@Part("user_id")RequestBody Id,
                                           @Part MultipartBody.Part file, @Query("token") String token);

    @Multipart
    @POST("api/upload_cv/{id}")
    Call<ServerResponse> uploadCvFile(@Path("id") String Id,
                                      @Part MultipartBody.Part file, @Query("token") String token);


}