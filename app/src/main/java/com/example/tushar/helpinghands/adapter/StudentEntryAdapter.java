package com.example.tushar.helpinghands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tushar.helpinghands.StudentDetailsActivity;
import com.example.tushar.helpinghands.models.StudentEntries;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        holder.studentAge.setText(String.valueOf(getAge(studentEntry.getDob())));
        holder.studentName.setText(studentEntry.getName());
        holder.studentClass.setText(studentEntry.getCclass());
        holder.studentOrphanage.setText(studentEntry.getOrphanage());
        holder.studentImage.setImageResource(R.drawable.helping_hands);
    }

    private int getAge(String dob) {
        //String startDateString = "06/27/2007";
        int age = 0;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        Date currentDate;
        String date=android.text.format.DateFormat.format("dd/MM/yyyy", new java.util.Date()).toString();
        Calendar today=Calendar.getInstance();

            String[] dobarr=dob.split("/");
            String[] curarr=date.toString().split("/");
            Integer dbYY=Integer.parseInt(dobarr[2]);
            Integer bdMM=Integer.parseInt(curarr[2]);
            age=bdMM-dbYY;

        return age;
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
        public TextView studentOrphanage;

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
            studentOrphanage = (TextView)view.findViewById(R.id.edit_orphanage_name);

        }
    }
}
