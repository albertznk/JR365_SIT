package com.goldenictsolutions.win.goldenictjob365.Employee.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Location;
import com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses.Township;
import com.goldenictsolutions.win.goldenictjob365.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Win on 6/10/2017.
 */


public class SpinnerCustomAdapterTownship extends BaseAdapter {
    Context context;
    int flags[];
    List<Location> cities = new List<Location>() {
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
    List<Township> townships = new List<Township>() {
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
        public Iterator<Township> iterator() {
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
        public boolean add(Township township) {
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
        public boolean addAll(@NonNull Collection<? extends Township> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Township> c) {
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
        public Township get(int index) {
            return null;
        }

        @Override
        public Township set(int index, Township element) {
            return null;
        }

        @Override
        public void add(int index, Township element) {

        }

        @Override
        public Township remove(int index) {
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
        public ListIterator<Township> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Township> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Township> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    LayoutInflater inflter;

    public SpinnerCustomAdapterTownship(Context applicationContext, List<Township> townships, List<Location> cities) {            // int[] flags  as parameter
        this.context = applicationContext;
        //  this.flags = flags;
        this.townships = townships;
        this.cities = cities;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return townships.size();

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


                names.setText(townships.get(i).getTownship().toString());

        return view;
    }

}
