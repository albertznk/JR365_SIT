package com.goldenictsolutions.win.goldenictjob365.Employer.FragPages;

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

import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_Control;
import com.goldenictsolutions.win.goldenictjob365.DB_Controller.DB_USERDATA;
import com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler.LanguageHandler;
import com.goldenictsolutions.win.goldenictjob365.JLoginActivity;
import com.goldenictsolutions.win.goldenictjob365.Policy_FromApp;
import com.goldenictsolutions.win.goldenictjob365.R;
import com.goldenictsolutions.win.goldenictjob365.commonfile.Feedback_Form;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class EmployerMenu extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    DB_Control db_control;
    ArrayList<DB_USERDATA> db_deleteUserdata = new ArrayList<>();
    RadioButton r_eng, r_myan;
    RadioGroup rgp;
    int language;
    SharedPreferences.Editor edit;
    SharedPreferences more_sp;
    LinearLayout logOut, feedback, policy, upgrade;
    TextView tv_register_no;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employer_frag_menu, container, false);
        db_control = new DB_Control(getContext());
        db_control.openDb();
        db_deleteUserdata = db_control.getUserid();
        rgp = (RadioGroup) view.findViewById(R.id.empr_more_rgp);
        r_eng = (RadioButton) view.findViewById(R.id.empr_radio_english);
        more_sp = getContext().getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
        language = more_sp.getInt(JLoginActivity.LANGUAGE, 0);
        tv_register_no = (TextView) view.findViewById(R.id.tv_empr_register_no);
        r_myan = (RadioButton) view.findViewById(R.id.empr_radio_myanmar);
        logOut = (LinearLayout) view.findViewById(R.id.employer_LogOut);
        feedback = (LinearLayout) view.findViewById(R.id.empr_feedback);
        upgrade = (LinearLayout) view.findViewById(R.id.empr_upgradetv);
        policy = (LinearLayout) view.findViewById(R.id.empr_policy);
        tv_register_no.setText(db_deleteUserdata.get(0).getUser_name());

        if (language == 1) {
            r_eng.setChecked(true);

        } else if (language == 2) {
            r_myan.setChecked(true);
        }

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logOut.setOnClickListener(this);
        rgp.setOnCheckedChangeListener(this);
        policy.setOnClickListener(this);
        feedback.setOnClickListener(this);
        upgrade.setOnClickListener(this);
    }

    public void restart() {
        Intent in = getActivity().getIntent();
        startActivity(in);
        getActivity().finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.employer_LogOut:

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.exit));
                builder.setMessage(getResources().getString(R.string.logout_exit));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
                builder.create().show();




                break;
            case R.id.empr_policy:
                Intent intent=new Intent(getActivity(), Policy_FromApp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.empr_upgradetv:
                //Toast.makeText(getActivity(), "Empr Upgrade", Toast.LENGTH_SHORT).show();
                break;
            case R.id.empr_register_no:
               // Toast.makeText(getActivity(), "Empr Register No", Toast.LENGTH_SHORT).show();
                break;
            case R.id.empr_feedback:
                Intent tofeedback=new Intent(getActivity(), Feedback_Form.class);
                tofeedback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(tofeedback);
                //  Toast.makeText(getActivity(), "Empr Feedback", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.empr_radio_english:
                more_sp = getContext().getSharedPreferences(JLoginActivity.LOGIN, MODE_PRIVATE);
                edit = more_sp.edit();
                edit.putInt(JLoginActivity.LANGUAGE, 1);
                edit.commit();
                LanguageHandler.changeLocale(getResources(), "en");
                restart();
                break;
            case R.id.empr_radio_myanmar:
                edit = more_sp.edit();
                edit.putInt(JLoginActivity.LANGUAGE, 2);
                edit.commit();
                LanguageHandler.changeLocale(getResources(), "my");
                restart();
                break;
        }
    }

}
