package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.JobCategory;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Win on 6/22/2017.
 */

public class SpinnerCustomAdapterDesirePosition extends BaseAdapter{

    Context context;
    int flags[];

    List<JobCategory> jobCategories = new List<JobCategory>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<JobCategory> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(JobCategory jobCategory) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends JobCategory> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends JobCategory> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public JobCategory get(int index) {
            return null;
        }

        @Override
        public JobCategory set(int index, JobCategory element) {
            return null;
        }

        @Override
        public void add(int index, JobCategory element) {

        }

        @Override
        public JobCategory remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<JobCategory> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<JobCategory> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<JobCategory> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    LayoutInflater inflter;

    public SpinnerCustomAdapterDesirePosition(Context applicationContext, List<JobCategory> jobCategories) {            // int[] flags  as parameter
        this.context = applicationContext;
        //  this.flags = flags;
        this.jobCategories = jobCategories;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if(jobCategories.size() == 0){
            return 0;

        }else {

            return jobCategories.size();
        }
    }

    @Override
    public Object getItem(int i) {
       return jobCategories.get(i).getCategory();
    }

    @Override
    public long getItemId(int i) {
        return jobCategories.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        //   ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView_spin);
        // icon.setImageResource(flags[i]);


        names.setText(jobCategories.get(i).getCategory().toString());

        return view;
    }

}
