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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Experience_Data;
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

/**
 * Created by kurio on 7/23/17.
 */

public class CustomAdapter_List_Work extends BaseAdapter {
    Context context;
    CheckBox currently_working;
    TextView text1, text2, text3, text4;
    ImageView img, edit;
    ArrayList<Experience_Data> exp_list = new ArrayList<>();
    LinearLayout li;
    String text_end = null;
    //String[] io1,io2,io3,io4;
    ArrayList<String> io1 = new ArrayList<>();
    ArrayList<String> io2 = new ArrayList<>();
    ArrayList<String> io3 = new ArrayList<>();
    ArrayList<String> io4 = new ArrayList<>();
    ArrayList<String> io5 = new ArrayList<>();
    String token;
    DB_Control db_control;
    ProgressDialog pd;
    ArrayList<DB_USERDATA> user_data;
    private Communicator communicator;
    private Dialog dialog;
    Button upload, cancel;
    EditText organization, rank;
    FragmentManager fm;
    static TextView start_date, end_date;
    String Delete_Exp = "http://allreadymyanmar.com/api/experience/";
    String Update_Exp = "http://allreadymyanmar.com/api/update_experience/";
    String str_id, str_start, str_end, str_organization, str_rank;
    private int position;

    public CustomAdapter_List_Work(Context context, ArrayList<String> io1, ArrayList<String> io2, ArrayList<String> io3, ArrayList<String> io4, ArrayList<String> io5) {
        this.context = context;
        dialog = new Dialog(context);
        this.io1 = io1;
        this.io2 = io2;
        this.io3 = io3;
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
        view = infl.inflate(R.layout.custom_layout_listview, null);


        communicator = new Communicator();
        FragmentActivity activity = (FragmentActivity) context;
        fm = activity.getSupportFragmentManager();

        text1 = (TextView) view.findViewById(R.id.tv1);
        text2 = (TextView) view.findViewById(R.id.tv2);
        text3 = (TextView) view.findViewById(R.id.tv3);
        text4 = (TextView) view.findViewById(R.id.tv4);
        edit = (ImageView) view.findViewById(R.id.edit_work);
        //li = (LinearLayout) view.findViewById(R.id.linear_layout_work_qua);
        img = (ImageView) view.findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                position = i;
                new GetWork_Experience().execute(io5.get(i));
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               /* li.setVisibility(View.GONE);
                useDeleteExp(io5.get(i), "");
                Log.e("SF", "" + io1.size());*/
                new AsyncTask<String, Object, String>() {

                    @Override
                    protected String doInBackground(String... strings) {
                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(55, TimeUnit.SECONDS)
                                .writeTimeout(55, TimeUnit.SECONDS)
                                .readTimeout(55, TimeUnit.SECONDS)
                                .build();
                        Log.e("OKIOKI", "" + strings[0]);
                        Request request = new Request.Builder()
                                .url(Delete_Exp + strings[0])
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

                    protected void onPreExecute() {
                        pd = new ProgressDialog(context);
                        pd.setCancelable(false);
                        pd.setMessage(context.getResources().getString(R.string.deleting));
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                    }

                    protected void onPostExecute(String s) {
                        pd.dismiss();
                        if (s != null) {
                            Log.e("JSON Object",""+s);
                            try {
                                JSONObject jsonObject=new JSONObject(s);
                                //if (jsonObject.getString("result").equals("Success")){
                                    io1.remove(i);
                                    io2.remove(i);
                                    io3.remove(i);
                                    io4.remove(i);
                                    io5.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                /*}else{
                                    Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();*/

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
        try {
            text1.setText(io1.get(i));
            text2.setText(io2.get(i));
            text3.setText(io3.get(i));
            text4.setText(io4.get(i));
        } catch (Exception e) {
            Log.e("Fucky", "getView: " + e + " Cant set text exception");
        }

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


    public class GetWork_Experience extends AsyncTask<String, Object, ArrayList<Experience_Data>> {

        @Override
        protected void onPreExecute() {
            exp_list.clear();
            pd = new ProgressDialog(context);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        protected void onPostExecute(final ArrayList<Experience_Data> experience_datas) {

            pd.dismiss();
            dialog.setContentView(R.layout.dialog_experience);
            dialog.setTitle(context.getResources().getString(R.string.cv_Work_Experience));

            organization = (EditText) dialog.findViewById(R.id.editText_organization);
            rank = (EditText) dialog.findViewById(R.id.edittext_rank);
            start_date = (TextView) dialog.findViewById(R.id.textView_exp_start_date);
            end_date = (TextView) dialog.findViewById(R.id.textView_exp_end_date);
            upload = (Button) dialog.findViewById(R.id.btn_post_exp);
            currently_working = (CheckBox) dialog.findViewById(R.id.current_working);
            Log.e("Currently ", "Working" + experience_datas.get(0).getEnd_date());
            organization.setText(experience_datas.get(0).getOrganization());
            rank.setText(experience_datas.get(0).getRank());
            start_date.setText(experience_datas.get(0).getStart_date());
            if (experience_datas.get(0).getEnd_date().equals("null")) {
                Log.e("Emma Watson", "Emma Watson");
                currently_working.setChecked(true);
                end_date.setEnabled(false);
                text_end = "Currently Working";
                end_date.setHint(text_end);
            } else {
                text_end = experience_datas.get(0).getEnd_date();
                end_date.setText(text_end);
            }
            //end_date.setText(text_end);

            currently_working.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        end_date.setText("");
                        end_date.setHint(context.getResources().getString(R.string.Current_working));
                        end_date.setEnabled(false);
                    } else {
                        end_date.setHint(context.getResources().getString(R.string.end_date));
                        end_date.setEnabled(true);
                    }
                }
            });

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
                    str_id = experience_datas.get(0).getExp_id();
                    str_end = end_date.getText().toString();
                    str_start = start_date.getText().toString();
                    str_organization = organization.getText().toString();
                    str_rank = rank.getText().toString();
                    Log.e("End date", "date" + str_end);
                    new UpdateWorkExperience().execute(str_id, str_organization, str_rank, str_start, str_end);
                    //function
                    //updateWorkExperience(experience_datas.get(0).getExp_id(), organization.getText().toString(), rank.getText().toString(),
                    //    start_date.getText().toString(), end_date.getText().toString(), token);
                    // dialog.dismiss();
                }
            });

            cancel = (Button) dialog.findViewById(R.id.btn_dismiss_exp);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(context, "Dismiss", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog.hide();
                }
            });
            dialog.show();
        }

        protected ArrayList<Experience_Data> doInBackground(String... objects) {
            JSONObject c = null;
            String url = "http://allreadymyanmar.com/api/get_experience/" + objects[0];

            HttpHandler sh = new HttpHandler();

            String checkResponse = sh.makeServiceCall(url);

            if (checkResponse != null) {
                try {
                    JSONObject applicant = null;
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    applicant = jsonObject.getJSONObject("experience");


                    Log.e("hello3", "hello3" + "\t" + applicant.length());
                    String exp_id = applicant.getString("id");
                    String organization = applicant.getString("organization");
                    String rank = applicant.getString("rank");
                    String start_date = applicant.getString("start_date");
                    String end_date = applicant.getString("end_date");

                    exp_list.add(new Experience_Data(exp_id, organization, rank, start_date, end_date));
                    Log.e("cv_size", "cv_size" + exp_list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return exp_list;
        }
    }

    public class UpdateWorkExperience extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setCancelable(false);
            pd.show();
        }

        protected void onPostExecute(String s) {
            pd.dismiss();

            Log.e("JSON String", "String" + s);


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

                        io1.add(position, str_organization);
                        io2.add(position, str_rank);
                        io3.add(position, str_start);
                        Log.e("Endate","ate"+str_end);
                        if(str_end.equals("")){
                            str_end="Currently Working";
                        }
                        io4.add(position, str_end);
                        io5.add(position, str_id);
                        notifyDataSetChanged();
                        Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                dialog.dismiss();
                dialog.show();
                Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject json = new JSONObject();
            try {
                json.put("id", strings[0]);
                json.put("organization", strings[1]);
                json.put("rank", strings[2]);
                json.put("start_date", strings[3]);
                json.put("end_date", strings[4]);

                RequestBody body = RequestBody.create(jHeader, String.valueOf(json));

                Request request = new Request.Builder()
                        .url(Update_Exp + strings[0])
                        .put(body)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.e("Experience Exception", "");
            }


            return null;
        }
    }


}



