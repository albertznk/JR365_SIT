package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Education_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by kurio on 7/24/17.
 */

public class CustomAdapter_Edu extends BaseAdapter {
    Button upload, cancel;
    String token;
    ProgressDialog progressdialog;
    TextView tv1, tv2, tv3, tv4;
    ImageView img, edit;
    ArrayList<Education_Data> edu_list = new ArrayList<>();
    Context context;
    ArrayList<String> uni_list;
    ArrayList<String> degree_list;
    ArrayList<String> edu_id;
    ArrayList<String> edu_start_date;
    ArrayList<String> edu_end_date;
    Communicator communicator;
    RelativeLayout rel;
    FragmentManager fm;
    EditText EduUniversity, EduDegree;
    Button btnPostEducation;
    static TextView textViewEduYear, textViewEduYear_end;
    DB_Control db_control;
    ProgressDialog pdAddEdu;
    ArrayList<DB_USERDATA> db_userdata;
    String userId, eduUniversity, eduDegree, edu_start_Year, edu_end_year;
    private Dialog dialog;
    EditText et_universit, et_degree;
    String a, b, c, d, e;
    public static TextView tv_st_year, tv_end_year;
    private String Delete_Edu = "http://allreadymyanmar.com/api/education/";
    private String Update_Edu = "http://allreadymyanmar.com/api/update_education/";

    LayoutInflater inf;
    private int position;

    public CustomAdapter_Edu(Context applicationContext, ArrayList<String> uni_list,
                             ArrayList<String> degree_list, ArrayList<String> edu_start_date, ArrayList<String> edu_end_date, ArrayList<String> edu_id, String userId) {
        this.context = applicationContext;
        this.uni_list = uni_list;
        this.degree_list = degree_list;
        dialog = new Dialog(context);
        progressdialog = new ProgressDialog(context);
        this.edu_start_date = edu_start_date;
        this.edu_end_date = edu_end_date;
        this.edu_id = edu_id;
        this.userId = userId;
    }

    public int getCount() {
        return uni_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {


        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.edu_list_row, null);
        //db_control = new DB_Control(context);


        communicator = new Communicator();
        FragmentActivity activity = (FragmentActivity) context;
        fm = activity.getSupportFragmentManager();
        tv1 = (TextView) view.findViewById(R.id.tv_uni);
        tv2 = (TextView) view.findViewById(R.id.tv_degree);
        tv3 = (TextView) view.findViewById(R.id.tv_start_date);
        tv4 = (TextView) view.findViewById(R.id.tv_end_date);
        rel = (RelativeLayout) view.findViewById(R.id.rel33);
        edit = (ImageView) view.findViewById(R.id.edu_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                position = i;
                new GetEduID().execute(edu_id.get(i));

            }
        });
        img = (ImageView) view.findViewById(R.id.edu_delete);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //rel.setVisibility(View.GONE);
                //useDeleteEdu(edu_id.get(i), "");

                new AsyncTask<String, Object, String>() {

                    protected void onPreExecute() {
                        super.onPreExecute();

                        progressdialog.setMessage("Deleting...");
                        progressdialog.setCancelable(false);
                        progressdialog.show();
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(55,TimeUnit.SECONDS)
                                .readTimeout(55,TimeUnit.SECONDS)
                                .writeTimeout(55,TimeUnit.SECONDS)
                                .build();
                        Log.e("IOPP", "" + strings[0]);
                        Request request = new Request.Builder()
                                .url(Delete_Edu + strings[0])
                                .delete()
                                .build();
                        try {
                            //Response response=client.newCall(request).execute();
                            Response response = client.newCall(request).execute();
                            return response.body().toString();
                        } catch (Exception e) {
                            Log.e("Exception", "" + e);
                        }
                        return null;
                    }

                    protected void onPostExecute(String s) {
                        if (s != null) {
                            progressdialog.dismiss();
                            edu_id.remove(i);
                            uni_list.remove(i);
                            degree_list.remove(i);
                            edu_end_date.remove(i);
                            edu_start_date.remove(i);
                            Log.e("uniiii", "" + uni_list.size());
                            if (uni_list.size() == 0) {
                                getEducation();
                            }
                            notifyDataSetChanged();
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            progressdialog.dismiss();

                            Toast.makeText(context, "Please Retry", Toast.LENGTH_SHORT).show();
                        }

                    }
                }.execute(edu_id.get(i));


            }
        });

        tv1.setText(uni_list.get(i));
        tv2.setText(degree_list.get(i));
        tv3.setText(edu_start_date.get(i));
        tv4.setText(edu_end_date.get(i));
        return view;
    }


    public static class EducationYearPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            tv_st_year.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    public static class EducationYearPickerFragment2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);

            int month = c.get(Calendar.MONTH);

            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            tv_end_year.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    public class GetEduID extends AsyncTask<String, Object, ArrayList<Education_Data>> {

        protected void onPreExecute() {
            super.onPreExecute();
            edu_list.clear();
            progressdialog.setMessage(context.getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.show();
        }

        protected void onPostExecute(final ArrayList<Education_Data> arrayList) {
            progressdialog.dismiss();
            if (arrayList != null) {
        /*    AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_education,null);

            et_universit= (EditText) dialogView.findViewById(R.id.editText_university);
            et_degree= (EditText) dialogView.findViewById(R.id.edittext_degree);
            tv_st_year= (TextView) dialogView.findViewById(R.id.textView_edu_start_year);
            tv_end_year= (TextView) dialogView.findViewById(R.id.textView_edu_end_year);

            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();
            dialog.show();*/

                dialog.setContentView(R.layout.dialog_education);
                dialog.setTitle(context.getResources().getString(R.string.education));
                cancel = (Button) dialog.findViewById(R.id.btnDismiss);
                upload = (Button) dialog.findViewById(R.id.btn_post_edu);
                et_degree = (EditText) dialog.findViewById(R.id.edittext_degree);
                et_universit = (EditText) dialog.findViewById(R.id.editText_university);

                tv_st_year = (TextView) dialog.findViewById(R.id.textView_edu_start_year);
                tv_end_year = (TextView) dialog.findViewById(R.id.textView_edu_end_year);
                et_degree.setText(arrayList.get(0).getDegree());
                et_universit.setText(arrayList.get(0).getUniversity());
                tv_st_year.setText(arrayList.get(0).getEdu_start_date());
                tv_end_year.setText(arrayList.get(0).getEdu_end_date());
                //     cancel = (Button) dialogView.findViewById(R.id.btnDismiss);
                //    upload = (Button) dialogView.findViewById(R.id.btn_post_edu);
                tv_st_year.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        DialogFragment newFragment = new EducationYearPickerFragment();
                        newFragment.show(fm, "datePicker");
                    }
                });
                tv_end_year.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        DialogFragment newFragment = new EducationYearPickerFragment2();
                        newFragment.show(fm, "datePicker");
                    }
                });
                dialog.show();


                upload.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        new UpdateEducation().execute(arrayList.get(0).getEdu_id(), et_universit.getText().toString(), et_degree.getText().toString(), tv_st_year.getText().toString(), tv_end_year.getText().toString());
                        //updateEducation(arrayList.get(0).getEdu_id(), et_universit.getText().toString(), et_degree.getText().toString(), tv_st_year.getText().toString(), tv_end_year.getText().toString(), token);
                        //    Toast.makeText(context, "Upload", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(context, "Connection Lost...", Toast.LENGTH_SHORT).show();
            }
        }

        protected ArrayList<Education_Data> doInBackground(String... objects) {
            JSONObject c = null;
            String url = "http://jobready365.com/api/get_education/" + objects[0];

            HttpHandler sh = new HttpHandler();

            String checkResponse = sh.makeServiceCall(url);

            if (checkResponse != null) {
                try {
                    JSONObject applicant = null;
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    applicant = jsonObject.getJSONObject("education");


                    Log.e("hello3", "hello3" + "\t" + applicant.length());
                    String eduID = applicant.getString("id");
                    String university = applicant.getString("university");
                    String degree = applicant.getString("degree");
                    String start_date = applicant.getString("start_date");
                    String end_date = applicant.getString("end_date");
                    Log.e("Uni", "" + university);
                    Log.e("Degree", "" + degree);
                    Log.e("StartDate", "" + start_date);
                    Log.e("EndDate", "" + end_date);
                    edu_list.add(new Education_Data(eduID, university, degree, start_date, end_date));
                    Log.e("cv_size", "cv_size" + edu_list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return edu_list;
        }

    }

    public class UpdateEducation extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");


        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog.setCancelable(false);
            progressdialog.setMessage(context.getResources().getString(R.string.loading));
            progressdialog.show();

        }

        protected String doInBackground(String... params) {
            JSONObject jsonObject = new JSONObject();
            client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS);
            try {

                jsonObject.put("id", params[0]);
                jsonObject.put("university", params[1]);
                jsonObject.put("degree", params[2]);
                jsonObject.put("sdate", params[3]);
                jsonObject.put("edate", params[4]);

                a = params[1];
                b = params[2];
                c = params[3];
                d = params[4];
                e = params[0];
                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonObject));
                Request request = new Request.Builder()
                        .url(Update_Edu + params[0])
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Transfer-Encoding", "chunked")
                        .put(body)
                        .build();
                Response response = client.newCall(request).execute();
                //Log.e("UIOP", "" + response.code());
                return response.body().string();
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
            Log.e("JSON Edu","Education"+s);
            if (s != null) {

                Log.e("JSON Object",""+s);
                progressdialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").equals("Success")) {
                        edu_id.remove(position);
                        uni_list.remove(position);
                        degree_list.remove(position);
                        edu_start_date.remove(position);
                        edu_end_date.remove(position);

                        edu_id.add(position, e);
                        uni_list.add(position, a);
                        degree_list.add(position, b);
                        edu_start_date.add(position, c);
                        edu_end_date.add(position, d);

                        notifyDataSetChanged();

                        dialog.dismiss();
                        Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            } else {
                progressdialog.dismiss();
                Toast.makeText(context, "Retry", Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    private void updateView(int index) {
        View v = inf.getChildAt(index - inf.getFirstVisiblePosition());

        if (v == null)
            return;

        TextView someText = (TextView) v.findViewById(R.id.sometextview);
        someText.setText("Hi! I updated you manually!");
    }*/


    /*  public void updateEducation(String id, String university, String degree, String start_date, String end_date, String token) {
          communicator.updateEducationByID(id, university, degree, start_date, end_date, token);
      }
  */
    public void getEducation() {
        Log.e("OKIIII", "" + userId);
        communicator.getEducationById(userId, token);
    }


}

