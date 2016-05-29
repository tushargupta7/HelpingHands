package com.example.tushar.helpinghands;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import java.util.Iterator;
import java.util.Map;

import User.ChildDetails;
import User.Requirements;

/**
 * Created by tushar on 1/4/16.
 */
public class FormFillActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private EditText childNameText;
    private EditText parentNameText;
    private EditText childSchoolName;
    private EditText childDob;
    private EditText address;
    private EditText contactNumber;
    private EditText email;
    private EditText childClass;
    private EditText childHighClass;
    private EditText childPerfo;
    private EditText pIncome;
    private Button addNextButton;
    private Button updateSubmitButton;
    private ScrollView addChildSection;
    private ScrollView updateChildSection;
    private ArrayList<ChildDetails> childrenList;
    private Spinner spinner;
    private TextView notInList;
    private ProgressDialog progDialog;
    private EditText updateChildNameText;
    private EditText updateChildDob;
    private TextView updateOrphanageName;
    private ArrayList<String> orphanageList;
    private Object requirementList;
    private ArrayList<Requirements> requirementArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_form);
        addChildSection = (ScrollView) findViewById(R.id.add_child_section);
        updateChildSection = (ScrollView) findViewById(R.id.update_child_section);
        if (getIntent().getStringExtra("Action").equalsIgnoreCase("ADD_CHILD")) {
            addChildSection.setVisibility(View.VISIBLE);
            updateChildSection.setVisibility(View.GONE);
        } else {
            addChildSection.setVisibility(View.GONE);
            updateChildSection.setVisibility(View.VISIBLE);
        }
        childrenList = new ArrayList<ChildDetails>();
        childNameText = (EditText) findViewById(R.id.child_name);
        updateChildNameText = (EditText) findViewById(R.id.update_child_name);
        updateChildDob = (EditText) findViewById(R.id.update_child_dob);
        updateOrphanageName = (TextView) findViewById(R.id.update_orphans_spinner);
        parentNameText = (EditText) findViewById(R.id.parent_name);
        initializeRegisteredOrphanageList();
        spinner = (Spinner) findViewById(R.id.orphans_spinner);
        spinner.setOnItemSelectedListener(this);
        //childSchoolName=(EditText)findViewById(R.id.child_school);
        childDob = (EditText) findViewById(R.id.child_dob);
        address = (EditText) findViewById(R.id.child_address);
        contactNumber = (EditText) findViewById(R.id.child_contact);
        childClass = (EditText) findViewById(R.id.child_class);
        email = (EditText) findViewById(R.id.child_email);
        childHighClass = (EditText) findViewById(R.id.highest_class);
        childPerfo = (EditText) findViewById(R.id.acad_perfo);
        pIncome = (EditText) findViewById(R.id.parent_income);
        notInList = (TextView) findViewById(R.id.not_in_list);
        notInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrphanage();
            }
        });
        addNextButton = (Button) findViewById(R.id.add_next_btn);
        addNextButton.setOnClickListener(this);
        updateSubmitButton = (Button) findViewById(R.id.update_save_btn);
        updateSubmitButton.setOnClickListener(this);

    }

    private void initializeRegisteredOrphanageList() {
        progDialog = ProgressDialog.show(FormFillActivity.this, "",
                "Loading. Please wait...", true);
        String url = Constants.Url + "/orphanagelist";
        final HashMap<String, String> mheader = new HashMap<>();
        mheader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest newOrphanageRequestApi = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                createOrphanageArrayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FormFillActivity", "failed to get orphanageList");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mheader;
            }
        };
        newOrphanageRequestApi.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(newOrphanageRequestApi);
    }

    private void createOrphanageArrayList(JSONObject response) {
        orphanageList = new ArrayList<String>();
        try {
            JSONArray orphanageListJsonArray = response.getJSONArray("orphanage");
            for (int i = 0; i < orphanageListJsonArray.length(); i++) {
                JSONObject orphanage = orphanageListJsonArray.getJSONObject(i);
                orphanageList.add(orphanage.getString("_id") + " " + orphanage.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, orphanageList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        progDialog.dismiss();
    }

    public void addNewOrphanage() {
        final Dialog dialog = new Dialog(this);
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
        Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void addNewOrphanageToServerDb(Dialog dialog) {
        EditText orphanageName = (EditText) dialog.findViewById(R.id.orphanage_name_text);
        EditText orphanageAddress1 = (EditText) dialog.findViewById(R.id.orphanage_address1_text);
        EditText orphanageAddress2 = (EditText) dialog.findViewById(R.id.orphanage_address2_text);
        EditText orphanagePincode = (EditText) dialog.findViewById(R.id.orphanage_pincode_text);
        EditText orphanageContact = (EditText) dialog.findViewById(R.id.orphanage_phone_text);
        try {
            JSONObject orphanage = new JSONObject();
            orphanage.put("name", orphanageName.getText().toString());
            orphanage.put("address1", orphanageAddress1.getText().toString());
            orphanage.put("address2", orphanageAddress2.getText().toString());
            orphanage.put("pincode", orphanagePincode.getText().toString());
            orphanage.put("contact", orphanageContact.getText().toString());
            doRequestForCreation(orphanage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRequestForCreation(JSONObject orphanage) {
        String url = Constants.Url + "/addOrphanage";
        final HashMap<String, String> mheader = new HashMap<>();
        mheader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest newOrphanageRequestApi = new JsonObjectRequest(Request.Method.POST, url, orphanage, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showInSpinner(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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
        TextView orphanageTextView = (TextView) findViewById(R.id.orphanage_name_textview);
        try {
            progDialog.dismiss();
            orphanageTextView.setText(response.getString("oid") + " " + response.getString("name"));
            orphanageTextView.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_next_btn: {
                addChildSection.setVisibility(View.GONE);
                submitStudentDetails();
                JSONObject obj = createJson();
                //  saveChildrenList(obj);
                //importChildDetails();
                getRequirementList();
                break;
                /*submitStudentDetails();


                finish();
                break;*/
            }

            case R.id.update_save_btn: {
                saveStudentRequirement();
            }
        }
    }

    private void saveStudentRequirement() {

    }

    private void importChildDetails() {
        updateChildNameText.setText(childNameText.getText());
        updateChildDob.setText(childDob.getText());
        updateOrphanageName.setText(spinner.getSelectedItemPosition());

    }

    private JSONObject createJson() {
        JSONObject json = new JSONObject();
        JSONArray childArray = new JSONArray();
        try {
            for (ChildDetails child : childrenList) {

                JSONObject childrenDetails = new JSONObject();
                childrenDetails.put("name", child.getChildName());
                childrenDetails.put("pname", child.getParentName());
                childrenDetails.put("perfo", child.getChildacadScore());
                childrenDetails.put("orphanageid", child.getChildOrphanageId());
                childrenDetails.put("orphanagename", child.getChildOrphanageName());
                childrenDetails.put("class", child.getChildClass());
                childrenDetails.put("email", child.getChildEmail());
                childrenDetails.put("hclass", child.getChildHighestClass());
                childrenDetails.put("pincome", child.getChildParentIncome());
                childrenDetails.put("dob", child.getChildDob());
                childArray.put(childrenDetails);
            }
            json.put("child", childArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void saveChildrenList(JSONObject obj) {
        String Url = Constants.Url;
        String saveChildUrl = Url + "addchild";
        final HashMap<String, String> mHeader = new HashMap<>();
        mHeader.put("Content-Type", "application/json; charset=utf-8");
        JsonObjectRequest saveChildRequest = new JsonObjectRequest(Request.Method.POST, saveChildUrl, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("saveCHild Result", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SaveChild Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeader;
            }
        };
        saveChildRequest.setRetryPolicy(new DefaultRetryPolicy(40000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(saveChildRequest);
    }

    private void submitStudentDetails() {
        ChildDetails childDetail = new ChildDetails();
        childDetail.setChildName(childNameText.getText().toString());
        childDetail.setParentName(parentNameText.getText().toString());
        childDetail.setChildOrphanageId(getTrimmed("oid", spinner.getSelectedItem().toString()));
        childDetail.setChildOrphanageName(getTrimmed("name", spinner.getSelectedItem().toString()));
        childDetail.setChildEmail(email.getText().toString());
        childDetail.setChildClass(childClass.getText().toString());
        childDetail.setChildacadScore(childPerfo.getText().toString());
        childDetail.setChildHighestClass(childHighClass.getText().toString());
        childDetail.setChildParentIncome(pIncome.getText().toString());
        childDetail.setChildDob(childDob.getText().toString());
        childrenList.add(childDetail);
    }

    private String getTrimmed(String type, String spinnerText) {
        String[] spinnerTextSplitter = spinnerText.split(" ");
        String orphanageName = "";
        if (type.equalsIgnoreCase("oid"))
            return spinnerTextSplitter[0];
        else if (type.equalsIgnoreCase("name")) {
            for (int i = 1; i < spinnerTextSplitter.length; i++) {
                orphanageName += (spinnerTextSplitter[i] + " ");
            }
            return orphanageName;
        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getRequirementList() {
        String requirementUrl = Constants.Url + "/getrequirements";
        JsonObjectRequest requiremnetsJson = new JsonObjectRequest(Request.Method.GET, requirementUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                createRequirementArrayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requiremnetsJson.setRetryPolicy(new DefaultRetryPolicy(40000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getBaseContext()).add(requiremnetsJson);
    }

    private void createRequirementArrayList(JSONObject requireListJson) {
        requirementArrayList = new ArrayList<Requirements>();
        try {
            JSONArray requirementsJsonArray = requireListJson.getJSONArray("requirements");
            for (int i = 0; i < requirementsJsonArray.length(); i++) {
                Requirements newRequirement = new Requirements(requirementsJsonArray.getString(i));
                requirementArrayList.add(newRequirement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showRequirementsInForm();
    }

    private void showRequirementsInForm() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.requirements_linear_layout);
        Iterator<Requirements> iterator = requirementArrayList.iterator();


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int i = 80;
        while (iterator.hasNext()) {
            Requirements reqObject = iterator.next();
            CheckBox reqCheck = new CheckBox(getApplicationContext());
           /* int id1 = Resources.getSystem().getIdentifier("btn_check_holo_dark", "drawable", "android");
            reqCheck.setButtonDrawable(R.color.mediumGreen);*/
            reqCheck.setTextColor(getResources().getColor(R.color.black));
            reqCheck.setLayoutParams(lparams);
            reqCheck.setText(reqObject.getName());
            reqCheck.setOnCheckedChangeListener(this);
            int id = View.generateViewId();
            reqCheck.setId(id);
            reqObject.setViewId(id);
            ll.addView(reqCheck);
        }
        updateChildSection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        for (int i = 0; i < requirementArrayList.size(); i++) {
            Requirements reqObj = requirementArrayList.get(i);
            if (id == reqObj.getViewId()) {
                if (isChecked) {
                    reqObj.setStatus("required");
                }
                else
                    reqObj.setStatus("notrequired");
                return;
            }
        }

    }
}
