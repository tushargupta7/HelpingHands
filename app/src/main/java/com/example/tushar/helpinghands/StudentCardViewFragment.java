package com.example.tushar.helpinghands;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by hussain on 10/4/16.
 */
public class StudentCardViewFragment extends Fragment {
    private static String childJsonFromServer;
    private Context mContext;
    RecyclerView recyclerView;
    ArrayList<StudentEntries> studentsList;
    private ProgressDialog progDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();

    }

    public void getChildJsonFromServer( Context cntx) {
        String Url=Constants.Url;
        String childListUrl =Url+"/childrenlist";

        HashMap<String,String> param= new HashMap<>();
        param.put("uuid", Preferences.getInstance(getContext()).getUserUid());
        param.put("token",Preferences.getInstance(getContext()).getUserToken());
        final HashMap<String,String> mHeader=new HashMap<>();
        mHeader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, childListUrl, new JSONObject(param), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                saveJsonToClass(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Json Response","error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeader;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(cntx).add(jsonObjectRequest);
    }



    private void saveJsonToClass(JSONObject response) {
        Log.d("json response",response.toString());
        studentsList=new ArrayList<StudentEntries>();
        try {
            JSONArray childrenArray=response.getJSONArray("children");
            for(int i=0;i<childrenArray.length();i++){
                JSONObject child=childrenArray.getJSONObject(i);
                StudentEntries studentEntries=new StudentEntries(child.getString("_id"),child.getString("name"),child.getString("dob"),child.getString("pname"),
                        child.getString("orphanageid"),child.getString("orphanagename"),child.getString("email"),child.getString("perfo"),child.getString("pincome"),child.getString("cclass"),child.getString("hclass"));
                studentsList.add(studentEntries);
            }
            StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentsList,mContext);
            recyclerView.setAdapter(studentEntryAdapter);
            progDialog.dismiss();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.student_recycler_view, container, false);
        JSONObject myJsonString = null;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            if(studentsList==null){
                progDialog = ProgressDialog.show(getContext(), "",
                        "Loading. Please wait...", true);
                getChildJsonFromServer(mContext);
            }
            else{
                StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentsList,mContext);
                recyclerView.setAdapter(studentEntryAdapter);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return rootView;
    }


       /* public String AssetJSONFile(String filename) throws IOException {
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
*/

}
