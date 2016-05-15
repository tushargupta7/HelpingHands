package com.example.tushar.helpinghands;

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
import java.security.PublicKey;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    public JSONObject getChildJsonFromServer(Context cntx) {

        ChildListThread childListThread = new ChildListThread(cntx);
        JSONObject jsonObject = null;
        try {
            jsonObject = childListThread.execute().get(30, TimeUnit.SECONDS);
            Log.d("kuch toh", jsonObject.toString());
            //return jsonObject;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        //A a=new A(getContext());
        //a.start();
        return null;
    }

    public class A extends Thread {
        public Context cntx;

        public A(Context context) {
            cntx = context;
        }

        @Override
        public void run() {
           /* ChildListThread childListThread=new ChildListThread(cntx);
            JSONObject jsonObject=null;
            try {
                jsonObject = childListThread.execute().get(10, TimeUnit.SECONDS);
                Log.d("kuch toh",jsonObject.toString());
                //return jsonObject;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }*/
        }
    }

    public class ChildListThread extends AsyncTask<Void, Void, JSONObject> {
        private Context cntx;

        ChildListThread(Context context) {
            cntx = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String Url = "http://192.168.1.14:3000/";
            String childListUrl = Url + "childrenlist";

            HashMap<String, String> param = new HashMap<>();
            param.put("uuid", "be0a16f8-340b-b9bc-7226-1d9572c05f4f");
            param.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiZTBhMTZmOC0zNDBiLWI5YmMtNzIyNi0xZDk1NzJjMDVmNGYifQ.SXzBKwK3_S8TbAQWkr8EsjtpNF2ug-8cGbcI2A04NSg");
            final HashMap<String, String> mHeader = new HashMap<>();
            mHeader.put("Content-Type", "application/json; charset=utf-8");
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, childListUrl, new JSONObject(param), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    saveJsonToClass(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Json Response", "error");
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return mHeader;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(cntx).add(jsonObjectRequest);
         /*   try {
                JSONObject response = future.get(20, TimeUnit.SECONDS);
                // a=response;
                Log.d("otp response", response.toString());
                return response;
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
            return null;
        }
    }

    private void saveJsonToClass(JSONObject response) {
        Log.d("json response", response.toString());
        studentsList = new ArrayList<StudentEntries>();
        try {
            JSONArray childrenArray = response.getJSONArray("children");
            for (int i = 0; i < childrenArray.length(); i++) {
                JSONObject child = childrenArray.getJSONObject(i);
                StudentEntries studentEntries = new StudentEntries(child.getString("_id"), child.getString("name"), child.getString("dob"), child.getString("pname"),
                        child.getString("school"), child.getString("email"), child.getString("address"), child.getString("perfo"), child.getString("pincome"), child.getString("cclass"), child.getString("contact"), child.getString("hclass"));
                studentsList.add(studentEntries);
            }
            StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentsList, mContext);
            recyclerView.setAdapter(studentEntryAdapter);
           /* getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*public class StudentCardViewFragment extends Fragment {
        private Context mContext;

        public StudentCardViewFragment(Context context) {
            mContext = context;
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
                if (studentsList == null) {//myJsonString = AssetJSONFile("childeren.json");
                    myJsonString = getChildJsonFromServer(mContext);
                    Log.d("Children List", myJsonString.toString());
                } else {
                    StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentsList, mContext);
                    recyclerView.setAdapter(studentEntryAdapter);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }

            //   recyclerView.setAdapter(getAdapter(getContext()));
            return rootView;
        }
*//*
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

        StudentEntryAdapter studentEntryAdapter = new StudentEntryAdapter(studentList,mContext );
        return studentEntryAdapter;
    }*//*

        public String AssetJSONFile(String filename) throws IOException {
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

 *//*   public static StudentEntries getStudentEntry(JSONObject jsonObject) {
        StudentEntries studentEntry = null;
        try {
            studentEntry = new StudentEntries(jsonObject.getString("_id"), jsonObject.optString("picture").toString(), Integer.parseInt(jsonObject.optString("age").toString()), Integer.parseInt(jsonObject.optString("aclass").toString()), jsonObject.optString("name").toString(), Float.parseFloat(jsonObject.optString("performance").toString()), Integer.parseInt(jsonObject.optString("income").toString()), jsonObject.optString("gender").toString(), jsonObject.optString("school").toString(), jsonObject.optString("orphanage").toString(), jsonObject.optString("email").toString(), jsonObject.optString("phone").toString(), jsonObject.optString("address").toString(), jsonObject.optString("about").toString(), jsonObject.optString("registered").toString(), Float.parseFloat(jsonObject.optString("latitude").toString()), Float.parseFloat(jsonObject.optString("longitude").toString()), jsonObject.getJSONArray("requirements"), jsonObject.getJSONArray("fulfilled"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentEntry;
    }*//*

    }*/
}
