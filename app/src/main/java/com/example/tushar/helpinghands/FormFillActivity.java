package com.example.tushar.helpinghands;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import User.ChildDetails;

/**
 * Created by tushar on 1/4/16.
 */
public class FormFillActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText childNameText;
    private EditText parentNameText;
    private EditText childSchoolName;
    private EditText date;
    private EditText address;
    private EditText contactNumber;
    private EditText email;
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
        childSchoolName=(EditText)findViewById(R.id.child_school);
        date=(EditText)findViewById(R.id.child_dob);
        address=(EditText)findViewById(R.id.child_address);
        contactNumber=(EditText)findViewById(R.id.child_contact);
        nextChildButton=(Button)findViewById(R.id.next_child_buton);
        submitButton=(Button)findViewById(R.id.submit_form);
        submitButton.setOnClickListener(this);
        nextChildButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_form : {
                submitDetails();
                saveChildrenList();
                finish();
            }

            case R.id.next_child_buton : {
                submitDetails();
            }
        }
    }

    private void saveChildrenList() {

    }

    private void submitDetails() {
        ChildDetails childDetail= new ChildDetails();
        childDetail.setChildName(childNameText.getText().toString());
        childDetail.setParentName(parentNameText.getText().toString());
        childDetail.setChildAddress(address.getText().toString());
        childDetail.setChildContact(contactNumber.getText().toString());
        childDetail.setChildEmail(email.getText().toString());
        childDetail.setChildSchool(childSchoolName.getText().toString());
        childrenList.add(childDetail);
    }
}
