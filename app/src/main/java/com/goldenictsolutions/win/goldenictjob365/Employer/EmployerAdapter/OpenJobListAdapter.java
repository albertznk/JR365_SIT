package com.goldenictsolutions.win.goldenictjob365.Employer.EmployerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employer.DataModel.EmployerModel;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.CandidateView;
import com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.JobOpenning;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.package_Type;
import static com.goldenictsolutions.win.goldenictjob365.Employer.EmployerActivity.EmployerFragHost.remain_time;

/**
 * Created by nuke on 8/2/17.
 */

public class OpenJobListAdapter extends RecyclerView.Adapter<OpenJobListAdapter.Holder> {
    private static String OPEN_JOB_DEL = "http://allreadymyanmar.com/api/jobdelete/";

    private Context context;
    private ArrayList<EmployerModel.OpenJobModel> openJosList;

    public OpenJobListAdapter(Context context, ArrayList<EmployerModel.OpenJobModel> openJosList) {
        this.openJosList = openJosList;
        this.context = context;
        Log.i("hhhh", "OpenJobListAdapter: "+openJosList.size()+"++++++++++++++++++++++++==");
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lif = LayoutInflater.from(parent.getContext());
        View v = lif.inflate(R.layout.open_jobs_list_row, null);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.jobType.setText(openJosList.get(position).getJob_title().toString());
        holder.candidateCount.setText(openJosList.get(position).getJob_count().toString());
        holder.delRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v, context.getResources().getString(R.string.delete_job), Snackbar.LENGTH_LONG);
                snackbar.setAction(context.getResources().getString(R.string.delete), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AsyncTask<String, Object, String>() {
                            @Override
                            protected void onPreExecute() {
                                holder.delRow.setEnabled(false);
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                OkHttpClient httpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(OPEN_JOB_DEL + params[0])
                                        .delete()
                                        .build();
                                try {
                                    Response response = httpClient.newCall(request).execute();
                                    return response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            protected void onPostExecute(String s) {
                                if (s != null) {
                                    openJosList.remove(position);
                                    notifyDataSetChanged();
                                    holder.delRow.setEnabled(true);
                                }
                            }
                        }.execute(openJosList.get(position).getId());
                    }
                }).show();

            }
        });
        holder.candidateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(openJosList.get(position).getJob_count())>0){
                    Intent i = new Intent(context, CandidateView.class);
                    i.putExtra("job_id",openJosList.get(position).getId());
                    context.startActivity(i);
                }else {
                    Snackbar snackbar = Snackbar.make(v,"no candidate found !",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, JobOpenning.class);
                i.putExtra("job_id", openJosList.get(position).getId());
                i.putExtra("remain_time",remain_time);
                i.putExtra("package_type",package_Type);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return openJosList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView jobType, candidateCount;
        public ImageButton viewDetail;
        public ImageButton delRow;

        public Holder(View itemView) {
            super(itemView);
            jobType = (TextView) itemView.findViewById(R.id.jobType);
            candidateCount = (TextView) itemView.findViewById(R.id.candidateCount);
            viewDetail = (ImageButton) itemView.findViewById(R.id.viewDetailButton);
            delRow = (ImageButton) itemView.findViewById(R.id.listDelButton);
        }
    }
}
