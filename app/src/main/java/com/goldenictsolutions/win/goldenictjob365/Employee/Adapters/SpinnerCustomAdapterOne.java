package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

/**
 * Created by Win on 6/9/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Location;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SpinnerCustomAdapterOne extends BaseAdapter {
    Context context;
    int flags[];
    List<Location> cityNames  = new List<Location>() {
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
        public Iterator<Location> iterator() {
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
        public boolean add(Location location) {
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
        public boolean addAll(@NonNull Collection<? extends Location> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Location> c) {
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
        public Location get(int index) {
            return null;
        }

        @Override
        public Location set(int index, Location element) {
            return null;
        }

        @Override
        public void add(int index, Location element) {

        }

        @Override
        public Location remove(int index) {
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
        public ListIterator<Location> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Location> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Location> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    LayoutInflater inflter;

    public SpinnerCustomAdapterOne(Context applicationContext, List<Location> cityNames) {            // int[] flags  as parameter
        this.context = applicationContext;
        //  this.flags = flags;
        this.cityNames = cityNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        if (cityNames.size() == 0) {
            return 0;
        } else {
            return cityNames.size();
        }
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        //   ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView_spin);
        // icon.setImageResource(flags[i]);
        names.setText(cityNames.get(i).getCity().toString());
        return view;
    }

}
