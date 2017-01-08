package com.example.tushar.helpinghands.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tushar.helpinghands.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hussain on 4/4/16.
 */
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    private static Context context;
    public DemoCollectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if(i==0) {
            fragment = new StudentCardViewFragment();
            Bundle args = new Bundle();
            args.putInt(StudentCardViewFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
        }else if(i==1){
            fragment = new RecentTransactionsFragment();
            Bundle args = new Bundle();
            args.putInt(RecentTransactionsFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
        }else{
            fragment = new RecentTransactionsFragment();
            Bundle args = new Bundle();
            args.putInt(RecentTransactionsFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }

    public static class StudentCardViewFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.student_recycler_view, container, false);
            RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
           // recyclerView.setAdapter(setData());
            return rootView;
        }
    }

    public static class SchoolsFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.student_details_layout, container, false);
            return rootView;
        }
    }

    public static class RecentTransactionsFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.recent_transactions_layout, container, false);
            return rootView;
        }
    }

/*    private static StudentEntryAdapter setData(){
        String myJsonString = null;
        List<StudentEntries> studentList = null;
        try {
            myJsonString = AssetJSONFile("childeren.json");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            studentList = new ArrayList<>();
            JSONObject jsonObjMain = new JSONObject(myJsonString);
            JSONArray jsonArray = jsonObjMain.optJSONArray("Children");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                StudentEntries entry = getStudentEntry(jsonObj);
                studentList.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentList, context);
        return studentEntryAdapter;
    }*/

    public static String AssetJSONFile (String filename) throws IOException {
        String json = null;
        try {

            InputStream is = context.getAssets().open("children.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

 /*   public static StudentEntries getStudentEntry(JSONObject jsonObject){
        StudentEntries studentEntry = null;
        try {
            studentEntry =new StudentEntries(jsonObject.getString("_id"), jsonObject.optString("picture").toString(), Integer.parseInt(jsonObject.optString("age").toString()), Integer.parseInt(jsonObject.optString("aclass").toString()), jsonObject.optString("name").toString(), Float.parseFloat(jsonObject.optString("performance").toString()), Integer.parseInt(jsonObject.optString("income").toString()), jsonObject.optString("gender").toString(), jsonObject.optString("school").toString(), jsonObject.optString("orphanage").toString(), jsonObject.optString("email").toString(), jsonObject.optString("phone").toString(), jsonObject.optString("address").toString(), jsonObject.optString("about").toString(), jsonObject.optString("registered").toString(), Float.parseFloat(jsonObject.optString("latitude").toString()), Float.parseFloat(jsonObject.optString("longitude").toString()), jsonObject.getJSONArray("requirements"), jsonObject.getJSONArray("fulfilled"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentEntry;
    }*/
}
