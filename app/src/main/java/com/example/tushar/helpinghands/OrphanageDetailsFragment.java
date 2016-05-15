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

/**
 * Created by hussain on 10/4/16.
 */
public class OrphanageDetailsFragment extends Fragment {
    private static Context mContext;
    private Intent mIntent;
    private SchoolOrphanageEntries orphanageData;
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

    public OrphanageDetailsFragment(){

    }

    public OrphanageDetailsFragment(Context context, Intent intent, SchoolOrphanageEntries entry){
        mContext = context;
        mIntent = intent;
        orphanageData = entry;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.orphanage_details_fragment_layout, container, false);

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
        collapsingToolbar.setTitle(orphanageData.getName());

        loadData();

        return rootView;
    }

    private void loadData() {
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.cheese_1).centerCrop().into(imageView);
       /* mClass.setText(orphanageData.getCclass());
        mAge.setText(orphanageData.getDob());
        mGender.setText(orphanageData.getGender());
        mPerforance.setText(orphanageData.getPerformance());
        mDescription.setText(orphanageData.getDescription());
        mIncome.setText(orphanageData.getIncome());
        mEmailId.setText(orphanageData.getEmail());
        mPhoneNo.setText(orphanageData.getPhoneNo());
        mAddress.setText(orphanageData.getAddress());
        mschoolName.setText(orphanageData.getSchool());*/
    }

}
