package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

/**
 * Created by kurio on 7/25/17.
 */

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Qualification_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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


public class CustomAdapter_List_Qua extends BaseAdapter {
    Context context;
    TextView text1, text2, text3, text4;
    ImageView img, edit;
    private Dialog dialog;
    LinearLayout li;
    FragmentManager fm;
    ArrayList<String> io1 = new ArrayList<>();
    ArrayList<String> io2 = new ArrayList<>();
    ArrayList<String> io3 = new ArrayList<>();
    ArrayList<String> io4 = new ArrayList<>();
    ArrayList<String> io5 = new ArrayList<>();
    String token;
    ProgressDialog pd;
    DB_Control db_control;
    ArrayList<Qualification_Data> qua_list = new ArrayList<>();
    ArrayList<DB_USERDATA> user_data;
    private Communicator communicator;
    EditText center_name, course;
    static TextView start_date, end_date;
    Button cancel, upload;
    String Delete_Qua = "http://allreadymyanmar.com/api/qualification/";
    String Update_Qua = "http://allreadymyanmar.com/api/update_qualification/";
    private int position;
    String str_id, str_center, str_course, str_start, str_end;

    public CustomAdapter_List_Qua(Context context, ArrayList<String> io1, ArrayList<String> io2, ArrayList<String> io3, ArrayList<String> io4, ArrayList<String> io5) {
        this.context = context;
        this.io1 = io1;
        this.io2 = io2;
        this.io3 = io3;
        dialog = new Dialog(context);
        this.io4 = io4;
        this.io5 = io5;
    }


    public int getCount() {
        return io1.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = infl.inflate(R.layout.custom_layout_other_qua, null);
        communicator = new Communicator();
        FragmentActivity activity = (FragmentActivity) context;
        fm = activity.getSupportFragmentManager();
        text1 = (TextView) view.findViewById(R.id.tv1);
        text2 = (TextView) view.findViewById(R.id.tv2);
        text3 = (TextView) view.findViewById(R.id.tv3);
        text4 = (TextView) view.findViewById(R.id.tv4);
        //li = (LinearLayout) view.findViewById(R.id.linear_layout_work_qua);
        img = (ImageView) view.findViewById(R.id.delete);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new AsyncTask<String, Object, String>() {

                    protected void onPreExecute() {
                        pd = new ProgressDialog(context);
                        pd.setCancelable(false);
                        pd.setMessage(context.getResources().getString(R.string.deleting));
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                    }

                    protected String doInBackground(String... strings) {
                        OkHttpClient client = new OkHttpClient();
                        client.newBuilder().readTimeout(55,TimeUnit.SECONDS)
                                .writeTimeout(55,TimeUnit.SECONDS)
                                .connectTimeout(55,TimeUnit.SECONDS)
                                .build();
                        Request request = new Request.Builder()
                                .url(Delete_Qua + strings[0])
                                .delete()
                                .build();


                        try {
                            Response response = client.newCall(request).execute();
                            return response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    protected void onPostExecute(String s) {
                        pd.dismiss();
                        if (s != null) {
                            Log.e("JSON Object",""+s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getString("result").equals("Success")) {
                                    pd.dismiss();
                                    io1.remove(i);
                                    io2.remove(i);
                                    io3.remove(i);
                                    io4.remove(i);
                                    io5.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(context, "Connection Lost", Toast.LENGTH_SHORT).show();

                        }
                    }
                }.execute(io5.get(i));
            }
        });
        edit = (ImageView) view.findViewById(R.id.edit_qua);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                position = i;
                new GetQua().execute(io5.get(i));
            }
        });
        text1.setText(io1.get(i));
        text2.setText(io2.get(i));
        text3.setText(io3.get(i));
        text4.setText(io4.get(i));

        return view;
    }


    public class GetQua extends AsyncTask<String, Object, ArrayList<Qualification_Data>> {

        protected void onPreExecute() {
            qua_list.clear();
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        protected void onPostExecute(final ArrayList<Qualification_Data> arrayList) {
            pd.dismiss();
            dialog.setContentView(R.layout.dialog_other_qualification);
            dialog.setTitle(context.getResources().getString(R.string.post_Qualification));
            upload = (Button) dialog.findViewById(R.id.btn_post_qualification);
            center_name = (EditText) dialog.findViewById(R.id.editText_other_center_name);
            course = (EditText) dialog.findViewById(R.id.edittext_other_course);

            start_date = (TextView) dialog.findViewById(R.id.textView_other_start_date);
            end_date = (TextView) dialog.findViewById(R.id.textView_other_end_date);

            center_name.setText(arrayList.get(0).getCenter_name());
            course.setText(arrayList.get(0).getCourse());
            start_date.setText(arrayList.get(0).getStart_date());
            end_date.setText(arrayList.get(0).getEnd_date());
            start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogFragment newFragment = new EducationYearPickerFragment();
                    newFragment.show(fm, "datePicker");
                }
            });
            end_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogFragment newFragment = new EducationYearPickerFragment2();
                    newFragment.show(fm, "datePicker");
                }
            });
            upload.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //function
                    str_center = center_name.getText().toString();
                    str_course = course.getText().toString();
                    str_id = arrayList.get(0).getQua_id();
                    str_start = start_date.getText().toString();
                    str_end = end_date.getText().toString();
                    new UpdateQualification().execute(str_id, str_center, str_course, str_start, str_end);
                   /* updateQualification(arrayList.get(0).getQua_id(), center_name.getText().toString(),
                            course.getText().toString(), start_date.getText().toString(), end_date.getText().toString(), token);*/
                    //  dialog.dismiss();

                }
            });
            cancel = (Button) dialog.findViewById(R.id.btn_dismiss_qualification);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        protected ArrayList<Qualification_Data> doInBackground(String... objects) {

            JSONObject c = null;
            String url = "http://allreadymyanmar.com/api/get_qualification/" + objects[0];

            HttpHandler sh = new HttpHandler();

            String checkResponse = sh.makeServiceCall(url);

            if (checkResponse != null) {
                try {
                    JSONObject applicant = null;
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    applicant = jsonObject.getJSONObject("qualification");


                    Log.e("hello3", "hello3" + "\t" + applicant.length());
                    String qua_id = applicant.getString("id");
                    String center_name = applicant.getString("center_name");
                    String course = applicant.getString("course");
                    String start_date = applicant.getString("start_date");
                    String end_date = applicant.getString("end_date");

                    qua_list.add(new Qualification_Data(qua_id, center_name, course, start_date, end_date));
                    Log.e("cv_size", "cv_size" + qua_list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return qua_list;
        }
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
            start_date.setText(year + "-" + (month + 1) + "-" + day);

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
            end_date.setText(year + "-" + (month + 1) + "-" + day);

        }
    }

    public class UpdateQualification extends AsyncTask<String, Void, String> {

        private OkHttpClient client = new OkHttpClient();
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        protected void onPostExecute(String s) {
            pd.dismiss();
            Log.e("JSON Object", "Object" + s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").equals("Success")) {

                        dialog.dismiss();
                        io1.remove(position);
                        io2.remove(position);
                        io3.remove(position);
                        io4.remove(position);
                        io5.remove(position);

                        io1.add(position, str_center);
                        io2.add(position, str_course);
                        io3.add(position, str_start);
                        io4.add(position, str_end);
                        io5.add(position, str_id);
                        notifyDataSetChanged();
                        Toast.makeText(context, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "" + jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
            }
        }

        protected String doInBackground(String... strings) {

            client.newBuilder().connectTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(35, TimeUnit.SECONDS);

            JSONObject json = new JSONObject();
            try {

                json.put("id", strings[0]);
                json.put("center_name", strings[1]);
                json.put("course", strings[2]);
                json.put("sdate", strings[3]);
                json.put("edate", strings[4]);

                RequestBody body = RequestBody.create(jHeader, String.valueOf(json));

                Request request = new Request.Builder()
                        .url(Update_Qua + strings[0])
                        .put(body)
                        .build();

                Response reponse = client.newCall(request).execute();
                return reponse.body().string();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

  /*  public void updateQualification(String qua_id,String center_name,String course,String start_date,String end_date,String token){
        communicator.updateQualification(qua_id,center_name,course,start_date,end_date,token);
    }*/


}



