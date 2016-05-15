package com.example.tushar.helpinghands;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hussain on 10/4/16.
 */
public class SchoolOrphanageFragment extends Fragment implements View.OnClickListener{
    private static Context mContext;
    private static List<SchoolOrphanageEntries> schoolList;
    private static List<SchoolOrphanageEntries> orphanageList;
    private SchoolsOrphanagesEntryAdapter schoolsOrphanagesEntryAdapter;
    private TextView schoolTab;
    private TextView orphanageTab;


    public SchoolOrphanageFragment(Context context){
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.school_orphanage_list_layout, container, false);
        loadSchoolList();
        loadOrphanageList();
        schoolTab = (TextView)rootView.findViewById(R.id.schools_list);
        orphanageTab = (TextView)rootView.findViewById(R.id.orphanages_list);
        schoolTab.setOnClickListener(this);
        orphanageTab.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.schools_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        schoolsOrphanagesEntryAdapter = new SchoolsOrphanagesEntryAdapter(orphanageList, mContext );
        recyclerView.setAdapter(schoolsOrphanagesEntryAdapter);
        return rootView;
    }

    private static void loadSchoolList() {
        String myJsonString = null;
        try {
            myJsonString = AssetJSONFile("childeren.json");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            schoolList = new ArrayList<>();
            JSONObject jsonObjMain = new JSONObject(myJsonString);
            JSONArray jsonArray1 = jsonObjMain.optJSONArray("Data");
            JSONObject abc = jsonArray1.getJSONObject(1);
            JSONArray jsonArray = abc.optJSONArray("School");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                SchoolOrphanageEntries entry = getSchoolEntry(jsonObj);
                schoolList.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void loadOrphanageList() {
        String myJsonString = null;
        try {
            myJsonString = AssetJSONFile("childeren.json");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            orphanageList = new ArrayList<>();
            JSONObject jsonObjMain = new JSONObject(myJsonString);
            JSONArray jsonArray1 = jsonObjMain.optJSONArray("Data");
            JSONObject abc = jsonArray1.getJSONObject(2);
            JSONArray jsonArray = abc.optJSONArray("Orphanage");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                SchoolOrphanageEntries entry = getOrphanageEntry(jsonObj);
                orphanageList.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String AssetJSONFile(String filename) throws IOException {
        String json = null;
        try {

            InputStream is = mContext.getAssets().open("children.json");

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

    public static SchoolOrphanageEntries getSchoolEntry(JSONObject jsonObject) {
        SchoolOrphanageEntries schoolEntry = null;
        try {
            schoolEntry = new SchoolOrphanageEntries(jsonObject.getString("name"), 25, jsonObject.getString("school_id"), "tushargupta7@", jsonObject.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schoolEntry;
    }

    public static SchoolOrphanageEntries getOrphanageEntry(JSONObject jsonObject) {
        SchoolOrphanageEntries orphanageEntry = null;
        try {
            orphanageEntry = new SchoolOrphanageEntries(jsonObject.getString("name"), 25, jsonObject.getString("school_id"), "tushargupta7@", jsonObject.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orphanageEntry;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.schools_list){
            schoolsOrphanagesEntryAdapter.addAll(schoolList);
            schoolsOrphanagesEntryAdapter.notifyDataSetChanged();
        }else if(id == R.id.orphanages_list){
            schoolsOrphanagesEntryAdapter.addAll(orphanageList);
            schoolsOrphanagesEntryAdapter.notifyDataSetChanged();
        }
    }
}
