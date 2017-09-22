package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.Skill_Data;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.Employee.Services.Communicator;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kurio on 7/24/17.
 */

public class CustomAdapter_Language extends BaseAdapter {
    TextView tv1, tv2, tv3;
    Button upload, cancel;
    Context context;
    ProgressDialog pd;
    ImageView img, edit;
    ArrayList<Skill_Data> skill_list = new ArrayList<>();
    String token;
    Communicator communicator;
    LinearLayout li;
    ArrayList<String> language_list;
    ArrayList<String> spoken_list;
    ArrayList<String> written_list;
    ArrayList<String> language_id_list;
    EditText language, spoken_lvl, written_lvl;
    protected Dialog dialog;
    String Delete_Language = "http://allreadymyanmar.com/api/skill/";
    String Update_Language = "http://allreadymyanmar.com/api/update_skill/";
    String str_id, str_language, str_spoken_level, str_written_level;
    private int position;

    public CustomAdapter_Language(Context applicationContext, ArrayList<String> language_list, ArrayList<String> spoken_list, ArrayList<String> written_list, ArrayList<String> language_Id_list) {
        this.context = applicationContext;
        this.language_list = language_list;
        this.spoken_list = spoken_list;
        dialog = new Dialog(context);
        this.written_list = written_list;
        this.language_id_list = language_Id_list;
    }

    @Override
    public int getCount() {
        return language_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        communicator = new Communicator();
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.language_row, null);
        tv1 = (TextView) view.findViewById(R.id.tv_language);
        tv2 = (TextView) view.findViewById(R.id.tv_spoken);
        tv3 = (TextView) view.findViewById(R.id.tv_written);
        li = (LinearLayout) view.findViewById(R.id.layout_3);
        edit = (ImageView) view.findViewById(R.id.language_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                position = i;
                new GetLanguage().execute(language_id_list.get(i));
            }
        });
        img = (ImageView) view.findViewById(R.id.language_delete);
        tv1.setText(language_list.get(i));
        tv2.setText(spoken_list.get(i));
        tv3.setText(written_list.get(i));
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //li.setVisibility(View.GONE);
                new AsyncTask<String, Object, String>() {

                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(55, TimeUnit.SECONDS)
                            .writeTimeout(55, TimeUnit.SECONDS)
                            .readTimeout(55, TimeUnit.SECONDS)
                            .build();


                    @Override
                    protected void onPreExecute() {
                        pd = new ProgressDialog(context);
                        pd.setCancelable(false);
                        pd.setMessage(context.getResources().getString(R.string.deleting));
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.show();
                    }

                    protected String doInBackground(String... strings) {


                        Request request = new Request.Builder()
                                .url(Delete_Language + strings[0])
                                .delete()
                                .build();
                        try {
                            Response res = client.newCall(request).execute();
                            return res.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        pd.dismiss();
                        if (s != null) {
                            Log.e("JSON Object", "" + s);

                            try {
                                JSONObject json = new JSONObject(s);
                                if (json.getString("result").equals("Success")) {
                                    language_id_list.remove(i);
                                    language_list.remove(i);
                                    written_list.remove(i);
                                    spoken_list.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "" + json.getString("result"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "" + json.getString("result"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(context, "Connection Lost", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(language_id_list.get(i));
                // useDeleteSkill(language_id_list.get(i), "");
                //new Put_Language().execute(language_id_list.get(i));

            }
        });

        return view;
    }

    public class GetLanguage extends AsyncTask<String, Object, ArrayList<Skill_Data>> {

        protected void onPreExecute() {
            skill_list.clear();
            pd = new ProgressDialog(context);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        protected void onPostExecute(final ArrayList<Skill_Data> arrayList) {
            pd.dismiss();
            dialog.setContentView(R.layout.dialog_skill);
            dialog.setTitle(context.getResources().getString(R.string.Language));
            cancel = (Button) dialog.findViewById(R.id.btn_dismiss_language);
            upload = (Button) dialog.findViewById(R.id.btn_post_language);
            language = (EditText) dialog.findViewById(R.id.edittext_language_);
            spoken_lvl = (EditText) dialog.findViewById(R.id.edittext_language_level);
            written_lvl = (EditText) dialog.findViewById(R.id.edittext_language_wlevel);


            language.setText(arrayList.get(0).getLanguage());
            spoken_lvl.setText(arrayList.get(0).getSpoken());
            written_lvl.setText(arrayList.get(0).getWritten());

            upload.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    str_id = arrayList.get(0).getLanguage_id();
                    str_language = language.getText().toString();
                    str_spoken_level = spoken_lvl.getText().toString();
                    str_written_level = written_lvl.getText().toString();
                    new UpdateSkill().execute(str_id, str_language, str_spoken_level, str_written_level);
                    //  updateSkill(arrayList.get(0).getLanguage_id(), language.getText().toString(), spoken_lvl.getText().toString(), written_lvl.getText().toString(), token);
                    //  dialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        }

        protected ArrayList<Skill_Data> doInBackground(String... objects) {

            JSONObject c = null;
            String url = "http://allreadymyanmar.com/api/get_skill/" + objects[0];

            HttpHandler sh = new HttpHandler();

            String checkResponse = sh.makeServiceCall(url);

            if (checkResponse != null) {
                try {
                    JSONObject applicant = null;
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    applicant = jsonObject.getJSONObject("skill");


                    Log.e("hello3", "hello3" + "\t" + applicant.length());
                    String skillID = applicant.getString("id");
                    String Language = applicant.getString("language");
                    String sp_lvl = applicant.getString("spoken_level");
                    String wri_lvl = applicant.getString("written_level");

                    skill_list.add(new Skill_Data(Language, sp_lvl, wri_lvl, skillID));
                    Log.e("cv_size", "cv_size" + skill_list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return skill_list;
        }
    }

    public class UpdateSkill extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();
        private MediaType jHeader = MediaType.parse("application/json;charset=utf-8");

        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setMessage(context.getResources().getString(R.string.loading));
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            JSONObject jsonobject = new JSONObject();
            try {
                jsonobject.put("id", params[0]);
                jsonobject.put("language", params[1]);
                jsonobject.put("slevel", params[2]);
                jsonobject.put("wlevel", params[3]);


                RequestBody body = RequestBody.create(jHeader, String.valueOf(jsonobject));
                Request request = new Request.Builder()
                        .url(Update_Language + params[0])
                        .put(body)
                        .build();

                Response reponse = client.newCall(request).execute();
                return reponse.body().string();
            } catch (Exception e) {
                Log.e("Update Exception", "");
            }
            return null;
        }

        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").equals("success")) {
                        language_id_list.remove(position);
                        language_list.remove(position);
                        written_list.remove(position);
                        spoken_list.remove(position);

                        language_id_list.add(position, str_id);
                        language_list.add(position, str_language);
                        written_list.add(position, str_spoken_level);
                        spoken_list.add(position, str_written_level);
                        notifyDataSetChanged();
                        dialog.dismiss();
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
    }
    
  /*  private void updateSkill(String skillID, String language, String spoken_lvl, String written_lvl, String token) {
        communicator.updateSkillByID(skillID, language, spoken_lvl, written_lvl, token);
    }*/

}

