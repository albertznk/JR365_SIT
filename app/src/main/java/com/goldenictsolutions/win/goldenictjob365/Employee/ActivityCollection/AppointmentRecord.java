package com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.Adapters.AppointmentAdapter;
import com.goldenictsolutions.win.goldenictjob365.Employee.DataSet.AppointmentData;
import com.goldenictsolutions.win.goldenictjob365.Employee.HttpHandler;
import com.goldenictsolutions.win.goldenictjob365.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nuke on 6/28/17.
 */

public class AppointmentRecord extends AppCompatActivity {
    ArrayList<AppointmentData> AppointmentList = new ArrayList<>();
    TextView AppRecordResult;
    private ListView AppointList;
    DB_Control db_control;
    ArrayList<DB_USERDATA> dbuser;
    String user_id;
    Toolbar tb;
    TextView toolbar_text;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_show);
        toolbar_text= (TextView) findViewById(R.id.toolbar_text);
        toolbar_text.setText(getResources().getText(R.string.more_appointment));
        db_control = new DB_Control(getApplicationContext());
        db_control.openDb();
        dbuser = db_control.getUserid();
        user_id = dbuser.get(0).getUser_id();
        db_control.closeDb();
        AppRecordResult = (TextView) findViewById(R.id.AppointmentResult);
        AppointList = (ListView) findViewById(R.id.appointmentList);
        new FetchAppointmentRecord().execute(user_id);
    }

    ///////////////////////////Download Appointment Record//////////////////////
    public class FetchAppointmentRecord extends AsyncTask<String, String, ArrayList<AppointmentData>> {

        protected void onPreExecute() {
            Toast.makeText(AppointmentRecord.this, "Starting", Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(ArrayList<AppointmentData> AppointmentLists) {
            Toast.makeText(AppointmentRecord.this, "Finished", Toast.LENGTH_SHORT).show();
            if (AppointmentLists.size() == 0) {
                AppRecordResult.setVisibility(View.VISIBLE);
                AppRecordResult.setText(" NO Appointment Record is here !");
            } else {
                AppointmentAdapter adapter = new AppointmentAdapter(getApplicationContext(), AppointmentLists);
                AppointList.setAdapter(adapter);
            }
        }

        protected ArrayList<AppointmentData> doInBackground(String... params) {
            String userToken = params[0];
            HttpHandler handlerH = new HttpHandler();
            String url = "http://allreadymyanmar.com/api/getAllJobOffer/" + userToken;
            String checkResponse = handlerH.makeServiceCall(url);
            if (checkResponse != null) {
                Log.e("Applied History", "doInBackground +1: " + checkResponse);
                try {
                    JSONObject jsonObject = new JSONObject(checkResponse);
                    JSONArray jobList = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jobList.length(); i++) {
                        JSONObject c = jobList.getJSONObject(i);
                        AppointmentList.add(new AppointmentData(c.getString("id"), c.getString("employer_id"),
                                c.getString("applicant_id"), c.getString("description"),
                                c.getString("contact_info"), c.getString("contact_no"), c.getString("created_at"),
                                c.getString("updated_at")));
                    }
                    Log.e("Final Length", "doInBackground: Success :" + jobList.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return AppointmentList;
        }
    }


}
