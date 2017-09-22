package com.goldenictsolutions.win.goldenictjob365.Employee.FragPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.AppliedJobHistory;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.AppointmentRecord;
import com.goldenictsolutions.win.goldenictjob365.Employee.ActivityCollection.ContentHost;
import com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler.LanguageHandler;
import com.goldenictsolutions.win.goldenictjob365.JLoginActivity;
import com.goldenictsolutions.win.goldenictjob365.Policy_FromApp;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.goldenictsolutions.win.goldenictjob365.commonfile.Feedback_Form;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by nuke on 6/19/17.
 */

public class More extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    LinearLayout Appointment, AppliedJobs, LogOut, Policy, Feedback, Topup_history;
    SharedPreferences more_sp;
    DB_Control db_control;
    ArrayList<DB_USERDATA> db_deleteUserdata = new ArrayList<>();
    RadioButton r_eng, r_myan;
    TextView tv_register_no,tv_register,changelanguage,tv_appointment,tv_applied_job,tv_top_up_history,tv_feedback,tv_policy,tv_logout;
    RadioGroup rgp;
    int language;
    SharedPreferences.Editor edit;
    String register_noo;
    public void onAttach(Context context) {
        super.onAttach(context);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        db_deleteUserdata = db_control.getUserid();
        register_noo=db_deleteUserdata.get(0).getUser_name();
        more_sp = getContext().getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
        language = more_sp.getInt(JLoginActivity.LANGUAGE, 0);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.more, container, false);
        findID(v);

        tv_register_no.setText(register_noo);
        if (language == 1) {
            r_eng.setChecked(true);

        } else if (language == 2) {
            r_myan.setChecked(true);
        }
        return v;
    }

    private void findID(View v) {
        Appointment = (LinearLayout) v.findViewById(R.id.AppoitmentTxtView);
        AppliedJobs = (LinearLayout) v.findViewById(R.id.AppliedJobs);
        tv_feedback= (TextView) v.findViewById(R.id.tv_feedback);
        tv_register= (TextView) v.findViewById(R.id.tv_register);
        rgp = (RadioGroup) v.findViewById(R.id.more_rgp);
        tv_logout= (TextView) v.findViewById(R.id.tv_logout);
        tv_top_up_history= (TextView) v.findViewById(R.id.tv_top_up_history);
        tv_applied_job= (TextView) v.findViewById(R.id.tv_applied_job);
        tv_appointment= (TextView) v.findViewById(R.id.tv_appointment);
        changelanguage= (TextView) v.findViewById(R.id.changelanguage);
        tv_policy= (TextView) v.findViewById(R.id.tv_policy);
        tv_register_no= (TextView) v.findViewById(R.id.register_noo);
        r_eng = (RadioButton) v.findViewById(R.id.radio_english);
        r_myan = (RadioButton) v.findViewById(R.id.radio_myanmar);
        LogOut = (LinearLayout) v.findViewById(R.id.LogOut);
        Policy = (LinearLayout) v.findViewById(R.id.policy);
        Topup_history = (LinearLayout) v.findViewById(R.id.top_up_history);
        Feedback = (LinearLayout) v.findViewById(R.id.feedback);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogOut.setOnClickListener(this);
        AppliedJobs.setOnClickListener(this);
        Feedback.setOnClickListener(this);
        Policy.setOnClickListener(this);
        Topup_history.setOnClickListener(this);
        Appointment.setOnClickListener(this);
        rgp.setOnCheckedChangeListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register_MobileNo:
                Toast.makeText(getActivity(), "Register Mobile No", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback:
                //Toast.makeText(getActivity(), "Employee Feedback", Toast.LENGTH_SHORT).show();
                Intent tofeedback=new Intent(getActivity(), Feedback_Form.class);
                tofeedback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(tofeedback);
                break;
            case R.id.policy:
               // Toast.makeText(getActivity(), "Policy", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), Policy_FromApp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.top_up_history:
                Toast.makeText(getActivity(), "Topup History", Toast.LENGTH_SHORT).show();
                break;
            case R.id.LogOut:

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.exit));
                builder.setMessage(getResources().getString(R.string.logout_exit));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        SharedPreferences sh = getContext().getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
                        edit = sh.edit();
                        edit.putBoolean(JLoginActivity.LOGIN, false);
                        edit.putString(JLoginActivity.USER_ID, "");
                        edit.putString(JLoginActivity.USER_TOKEN, "");
                        edit.commit();

                        db_control.deleteData(db_deleteUserdata.get(0).getUser_id());
                        db_control.closeDb();

                        Intent i = new Intent(getContext(), JLoginActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();



                break;
            case R.id.AppliedJobs:
                Intent i1 = new Intent(getContext(), AppliedJobHistory.class);
                startActivity(i1);

                break;
            case R.id.AppoitmentTxtView:
                Intent i2 = new Intent(getContext(), AppointmentRecord.class);
                startActivity(i2);
                break;
        }
    }

        public void changeEnglish(){
            tv_register.setText(getResources().getString(R.string.more_registered_mobile_no));
            changelanguage.setText(getResources().getString(R.string.more_change_language));
            tv_appointment.setText(getResources().getString(R.string.more_appointment));
            tv_applied_job.setText(getResources().getString(R.string.more_applyjob));
            tv_top_up_history.setText(getResources().getString(R.string.topup_histroy));
            tv_feedback.setText(getResources().getString(R.string.more_Feedback));
            tv_policy.setText(getResources().getString(R.string.more_Policy));
            tv_logout.setText(getResources().getString(R.string.more_log_out));

        }
        public void changeMyanmar(){
            tv_register.setText(getResources().getString(R.string.more_registered_mobile_no));
            changelanguage.setText(getResources().getString(R.string.more_change_language));
            tv_appointment.setText(getResources().getString(R.string.more_appointment));
            tv_applied_job.setText(getResources().getString(R.string.more_applyjob));
            tv_top_up_history.setText(getResources().getString(R.string.topup_histroy));
            tv_feedback.setText(getResources().getString(R.string.more_Feedback));
            tv_policy.setText(getResources().getString(R.string.more_Policy));
            tv_logout.setText(getResources().getString(R.string.more_log_out));

        }
    public void restart() {


        Intent in = getActivity().getIntent();
        in.putExtra("remain_time",ContentHost.remain_time);
        startActivity(in);
        getActivity().finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_english:
                more_sp = getContext().getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
                edit = more_sp.edit();
                edit.putInt(JLoginActivity.LANGUAGE, 1);
                edit.commit();
                LanguageHandler.changeLocale(getResources(), "en");
              //  changeEnglish();
                restart();
                //    Toast.makeText(getActivity(), "Changed English", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_myanmar:
                edit = more_sp.edit();
                edit.putInt(JLoginActivity.LANGUAGE, 2);
                edit.commit();
                LanguageHandler.changeLocale(getResources(), "my");
               // changeMyanmar();
                restart();
                //     Toast.makeText(getActivity(), "Changed Myanmar", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
