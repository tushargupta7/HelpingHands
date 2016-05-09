package com.example.tushar.helpinghands;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by hussain on 7/4/16.
 */
public class StudentEntryAdapter extends RecyclerView.Adapter<StudentEntryAdapter.ViewHolder> {

    private static List<StudentEntries> studentEntries;
    private static Context context;

    public StudentEntryAdapter(List<StudentEntries> studentEntries, Context context) {
        this.studentEntries = studentEntries;
        this.context = context;
    }

    @Override
    public StudentEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_card_view,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentEntryAdapter.ViewHolder holder, int position) {
        StudentEntries studentEntry = studentEntries.get(position);
        holder.studentAge.setText(studentEntry.getDob());
        holder.studentName.setText(studentEntry.getName());
        holder.studentClass.setText(studentEntry.getCclass());
        holder.studentSchool.setText(studentEntry.getSchool());
        holder.studentImage.setImageResource(R.drawable.helping_hands);
    }

    @Override
    public int getItemCount() {
        return studentEntries.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView studentImage;
        public TextView studentName;
        public TextView studentClass;
        public TextView studentAge;
        public TextView studentSchool;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, StudentDetailsActivity.class);
            intent.putExtra("StudentEntry", (Serializable)studentEntries.get(v.getVerticalScrollbarPosition()));
            context.startActivity(intent);
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            studentImage = (ImageView)view.findViewById(R.id.student_image);
            studentAge = (TextView)view.findViewById(R.id.edit_age);
            studentName = (TextView)view.findViewById(R.id.edit_student_name);
            studentClass = (TextView)view.findViewById(R.id.edit_class);
            studentSchool = (TextView)view.findViewById(R.id.edit_school_name);

        }
    }
}
