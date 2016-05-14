package com.example.tushar.helpinghands;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import User.ChildDetails;

/**
 * Created by tushar on 1/4/16.
 */
public class FormFillActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener{
    private EditText childNameText;
    private EditText parentNameText;
    private EditText childSchoolName;
    private EditText date;
    private EditText address;
    private EditText contactNumber;
    private EditText email;
    private EditText childClass;
    private EditText childHighClass;
    private EditText childPerfo;
    private EditText pIncome;
    private Button nextChildButton;
    private Button submitButton;
    private ArrayList<ChildDetails> childrenList;
    private Spinner spinner;
    private TextView notInList;
    private ProgressDialog progDialog;
    private ArrayList<String> orphanageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_form);
        childrenList=new ArrayList<ChildDetails>();
        childNameText=(EditText)findViewById(R.id.child_name);
        parentNameText=(EditText)findViewById(R.id.parent_name);
        initializeRegisteredOrphanageList();
        spinner = (Spinner) findViewById(R.id.orphans_spinner);
        spinner.setOnItemSelectedListener(this);
        //childSchoolName=(EditText)findViewById(R.id.child_school);
        date=(EditText)findViewById(R.id.child_dob);
        address=(EditText)findViewById(R.id.child_address);
        contactNumber=(EditText)findViewById(R.id.child_contact);
        childClass=(EditText)findViewById(R.id.child_class);
        email=(EditText)findViewById(R.id.child_email);
        childHighClass=(EditText)findViewById(R.id.highest_class);
        childPerfo=(EditText)findViewById(R.id.acad_perfo);
        pIncome=(EditText)findViewById(R.id.parent_income);
        notInList=(TextView)findViewById(R.id.not_in_list);
        notInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrphanage();
            }
        });
        nextChildButton=(Button)findViewById(R.id.next_child_buton);
        submitButton=(Button)findViewById(R.id.submit_form);
        submitButton.setOnClickListener(this);
        nextChildButton.setOnClickListener(this);

    }

    private void initializeRegisteredOrphanageList() {
        progDialog = ProgressDialog.show(FormFillActivity.this, "",
                "Loading. Please wait...", true);
        String url="http://192.168.43.187:3000/orphanagelist";
        final HashMap<String,String> mheader=new HashMap<>();
        mheader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest newOrphanageRequestApi=new JsonObjectRequest(Request.Method.GET, url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              createOrphanageArrayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.e("FormFillActivity","failed to get orphanageList");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mheader;
            }
        };
        newOrphanageRequestApi.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(newOrphanageRequestApi);
    }

    private void createOrphanageArrayList(JSONObject response) {
        orphanageList=new ArrayList<String>();
        try {
            JSONArray orphanageListJsonArray=response.getJSONArray("orphanage");
            for (int i=0;i<orphanageListJsonArray.length();i++){
                JSONObject orphanage=orphanageListJsonArray.getJSONObject(i);
                orphanageList.add(orphanage.getString("_id")+" "+orphanage.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,orphanageList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
       progDialog.dismiss();
    }

    public void addNewOrphanage(){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_orphan_dialog);
        dialog.setTitle("Add Orphanage");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonSave);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrphanageToServerDb(dialog);
                progDialog.show();
                dialog.dismiss();
            }
        });
        Button dialogCancelButton= (Button)dialog.findViewById(R.id.dialogButtonCancel);
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addNewOrphanageToServerDb(Dialog dialog) {
        EditText orphanageName=(EditText)dialog.findViewById(R.id.orphanage_name_text);
        EditText orphanageAddress1=(EditText)dialog.findViewById(R.id.orphanage_address1_text);
        EditText orphanageAddress2=(EditText)dialog.findViewById(R.id.orphanage_address2_text);
        EditText orphanagePincode=(EditText)dialog.findViewById(R.id.orphanage_pincode_text);
        EditText orphanageContact=(EditText)dialog.findViewById(R.id.orphanage_phone_text);
       try {
           JSONObject orphanage = new JSONObject();
           orphanage.put("name", orphanageName.getText().toString());
           orphanage.put("address1",orphanageAddress1.getText().toString());
           orphanage.put("address2",orphanageAddress2.getText().toString());
           orphanage.put("pincode",orphanagePincode.getText().toString());
           orphanage.put("contact",orphanageContact.getText().toString());
           doRequestForCreation(orphanage);
       }
    catch (Exception e){
        e.printStackTrace();
    }
    }

    private void doRequestForCreation(JSONObject orphanage) {
        String url="http://192.168.43.187:3000/addOrphanage";
        final HashMap<String,String> mheader=new HashMap<>();
        mheader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest newOrphanageRequestApi=new JsonObjectRequest(Request.Method.POST, url,orphanage, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              showInSpinner(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mheader;
            }
        };
        newOrphanageRequestApi.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(newOrphanageRequestApi);
    }

    private void showInSpinner(JSONObject response) {
        spinner.setVisibility(View.GONE);
        notInList.setVisibility(View.GONE);
        TextView orphanageTextView=(TextView)findViewById(R.id.orphanage_name_textview);
        try {
            progDialog.dismiss();
            orphanageTextView.setText(response.getString("oid")+" "+response.getString("name"));
            orphanageTextView.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_form : {
                submitDetails();
                JSONObject obj=createJson();
                saveChildrenList(obj);
                finish();
                break;
            }

            case R.id.next_child_buton : {
                submitDetails();
            }
        }
    }

    private JSONObject createJson() {
        JSONObject json=new JSONObject();
        JSONArray childArray=new JSONArray();
        try {
            for (ChildDetails child : childrenList) {

                JSONObject childrenDetails = new JSONObject();
                childrenDetails.put("name", child.getChildName());
                childrenDetails.put("pname", child.getParentName());
                childrenDetails.put("perfo", child.getChildacadScore());
                childrenDetails.put("orphanageId", child.getChildOrphanage());
                childrenDetails.put("class", child.getChildClass());
                childrenDetails.put("email", child.getChildEmail());
                childrenDetails.put("hclass", child.getChildHighestClass());
                childrenDetails.put("pincome", child.getChildParentIncome());
                childrenDetails.put("dob",child.getChildDob());
                childArray.put(childrenDetails);
            }
            json.put("child",childArray);
        }catch (JSONException e) {
                e.printStackTrace();
            }
    return json;
    }

    private void saveChildrenList(JSONObject obj) {
        String Url="http://192.168.43.187:3000/";
        String saveChildUrl=Url+"addchild";
        final HashMap<String,String> mHeader=new HashMap<>();
        mHeader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest saveChildRequest=new JsonObjectRequest(Request.Method.POST, saveChildUrl, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             Log.d("saveCHild Result",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SaveChild Error",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeader;
            }
        };
        saveChildRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(saveChildRequest);
    }

    private void submitDetails() {
        ChildDetails childDetail= new ChildDetails();
        childDetail.setChildName(childNameText.getText().toString());
        childDetail.setParentName(parentNameText.getText().toString());
        childDetail.setChildOrphanage(getTrimmedOid(spinner.getSelectedItem().toString()));
        childDetail.setChildEmail(email.getText().toString());
        childDetail.setChildClass(childClass.getText().toString());
        childDetail.setChildacadScore(childPerfo.getText().toString());
        childDetail.setChildHighestClass(childHighClass.getText().toString());
        childDetail.setChildParentIncome(pIncome.getText().toString());
        childDetail.setChildDob(date.getText().toString());
        childrenList.add(childDetail);
    }

    private String getTrimmedOid(String s) {
        String[] spinnerTextSplitter=s.split(" ");
        String OrphanageId=spinnerTextSplitter[0];
        return OrphanageId;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
