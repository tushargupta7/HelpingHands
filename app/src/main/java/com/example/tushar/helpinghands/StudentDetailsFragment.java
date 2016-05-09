package com.example.tushar.helpinghands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
public class StudentDetailsFragment extends Fragment {
    private static Context mContext;
    private Intent mIntent;
    private StudentEntries studentData;
    private TextView mClass;
    private TextView mAge;
    private TextView mGender;
    private TextView mschoolName;
    private TextView mDescription;
    private TextView mPhoneNo;
    private TextView mEmailId;
    private TextView mAddress;
    private TextView mPerforance;
    private TextView mIncome;
    private View rootView;
    public StudentDetailsFragment(Context context, Intent intent){
        mContext = context;
        mIntent = intent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.student_details_fragment_layout, container, false);
        studentData = (StudentEntries)mIntent.getSerializableExtra("StudentEntry");

        mClass = (TextView)rootView.findViewById(R.id.edit_class);
        mAge = (TextView)rootView.findViewById(R.id.edit_age);
        mGender = (TextView)rootView.findViewById(R.id.edit_gender);
        mPerforance = (TextView)rootView.findViewById(R.id.edit_performance);
        mDescription = (TextView)rootView.findViewById(R.id.edit_description);
        mIncome = (TextView)rootView.findViewById(R.id.edit_income);
        mEmailId = (TextView)rootView.findViewById(R.id.edit_email);
        mPhoneNo = (TextView)rootView.findViewById(R.id.edit_phone_no);
        mAddress = (TextView)rootView.findViewById(R.id.edit_address);
        mschoolName = (TextView)rootView.findViewById(R.id.edit_school_name);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(studentData.getName());

        loadData();

        return rootView;
    }

    private void loadData() {
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.cheese_1).centerCrop().into(imageView);
        mClass.setText(studentData.getCclass());
        mAge.setText(studentData.getDob());
        mGender.setText(studentData.getGender());
        mPerforance.setText(studentData.getPerformance());
        mDescription.setText(studentData.getDescription());
        mIncome.setText(studentData.getIncome());
        mEmailId.setText(studentData.getEmail());
        mPhoneNo.setText(studentData.getPhoneNo());
        mAddress.setText(studentData.getAddress());
        mschoolName.setText(studentData.getSchool());
    }

}
