package com.example.tushar.helpinghands;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.Date;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_form);
        childrenList=new ArrayList<ChildDetails>();
        childNameText=(EditText)findViewById(R.id.child_name);
        parentNameText=(EditText)findViewById(R.id.parent_name);
        Spinner spinner = (Spinner) findViewById(R.id.orphans_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //childSchoolName=(EditText)findViewById(R.id.child_school);
        date=(EditText)findViewById(R.id.child_dob);
        address=(EditText)findViewById(R.id.child_address);
        contactNumber=(EditText)findViewById(R.id.child_contact);
        childClass=(EditText)findViewById(R.id.child_class);
        email=(EditText)findViewById(R.id.child_email);
        childHighClass=(EditText)findViewById(R.id.highest_class);
        childPerfo=(EditText)findViewById(R.id.acad_perfo);
        pIncome=(EditText)findViewById(R.id.parent_income);
        nextChildButton=(Button)findViewById(R.id.next_child_buton);
        submitButton=(Button)findViewById(R.id.submit_form);
        submitButton.setOnClickListener(this);
        nextChildButton.setOnClickListener(this);
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
                addNewOrphanageToServerDb();
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

    private void addNewOrphanageToServerDb() {

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
                childrenDetails.put("address", child.getChildAddress());
                childrenDetails.put("class", child.getChildClass());
                childrenDetails.put("contact", child.getChildContact());
                childrenDetails.put("email", child.getChildEmail());
                childrenDetails.put("hclass", child.getChildHighestClass());
                childrenDetails.put("school", child.getChildSchool());
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
        String Url="http://192.168.1.14:3000/";
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
        childDetail.setChildAddress(address.getText().toString());
        childDetail.setChildContact(contactNumber.getText().toString());
        childDetail.setChildEmail(email.getText().toString());
        childDetail.setChildSchool(childSchoolName.getText().toString());
        childDetail.setChildClass(childClass.getText().toString());
        childDetail.setChildacadScore(childPerfo.getText().toString());
        childDetail.setChildHighestClass(childHighClass.getText().toString());
        childDetail.setChildParentIncome(pIncome.getText().toString());
        childDetail.setChildDob(date.getText().toString());
        childrenList.add(childDetail);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
