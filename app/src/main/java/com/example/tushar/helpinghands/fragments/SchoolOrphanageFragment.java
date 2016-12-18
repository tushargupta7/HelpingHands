package com.example.tushar.helpinghands.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tushar.helpinghands.constants.Constants;
import com.example.tushar.helpinghands.models.OrphanageEntries;
import com.example.tushar.helpinghands.adapter.OrphanagesEntryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SchoolOrphanageFragment extends Fragment {
    private static List<OrphanageEntries> orphanageList;
    private OrphanagesEntryAdapter schoolsOrphanagesEntryAdapter;
   private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.school_orphanage_list_layout, container, false);
        loadOrphanageListFromServer();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.schools_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    private void loadOrphanageListFromServer() {
        String url= Constants.Url+"/orphanagelist";
        JsonObjectRequest orphanageList= new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             loadOrphanageList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        orphanageList.setRetryPolicy(new DefaultRetryPolicy(40000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(orphanageList);

    }

    private void loadOrphanageList(JSONObject orphanageJson) {
        try
         {
            orphanageList = new ArrayList<>();
             JSONArray jsonArray = orphanageJson.getJSONArray("orphanage");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                OrphanageEntries entry = getOrphanageEntry(jsonObj);
                orphanageList.add(entry);
            }
             schoolsOrphanagesEntryAdapter = new OrphanagesEntryAdapter(orphanageList,getActivity());
             recyclerView.setAdapter(schoolsOrphanagesEntryAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static OrphanageEntries getOrphanageEntry(JSONObject jsonObject) {
        OrphanageEntries orphanageEntry = null;
        try {
            orphanageEntry = new OrphanageEntries(jsonObject.getString("name"), 25, jsonObject.getString("_id"), jsonObject.getString("contact"),jsonObject.getString("pincode"), jsonObject.getString("address1")+" "+jsonObject.getString("address2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orphanageEntry;
    }

}
