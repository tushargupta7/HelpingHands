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
class StudentCardViewFragment extends Fragment {
    private static Context mContext;
    public StudentCardViewFragment(Context context){
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.student_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(getAdapter());
        return rootView;
    }

    private static StudentEntryAdapter getAdapter() {
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
            JSONArray jsonArray1 = jsonObjMain.optJSONArray("Data");
            JSONObject abc = jsonArray1.getJSONObject(0);
            JSONArray jsonArray = abc.optJSONArray("Children");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                StudentEntries entry = getStudentEntry(jsonObj);
                studentList.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentList, mContext);
        return studentEntryAdapter;
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

    public static StudentEntries getStudentEntry(JSONObject jsonObject) {
        StudentEntries studentEntry = null;
        try {
            studentEntry = new StudentEntries(jsonObject.getString("_id"), jsonObject.optString("picture").toString(), Integer.parseInt(jsonObject.optString("age").toString()), Integer.parseInt(jsonObject.optString("aclass").toString()), jsonObject.optString("name").toString(), Float.parseFloat(jsonObject.optString("performance").toString()), Integer.parseInt(jsonObject.optString("income").toString()), jsonObject.optString("gender").toString(), jsonObject.optString("school").toString(), jsonObject.optString("orphanage").toString(), jsonObject.optString("email").toString(), jsonObject.optString("phone").toString(), jsonObject.optString("address").toString(), jsonObject.optString("about").toString(), jsonObject.optString("registered").toString(), Float.parseFloat(jsonObject.optString("latitude").toString()), Float.parseFloat(jsonObject.optString("longitude").toString()), jsonObject.getJSONArray("requirements"), jsonObject.getJSONArray("fulfilled"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentEntry;
    }

}
