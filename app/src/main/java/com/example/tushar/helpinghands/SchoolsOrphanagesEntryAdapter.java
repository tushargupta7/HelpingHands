package com.example.tushar.helpinghands;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hussain on 11/4/16.
 */
public class SchoolsOrphanagesEntryAdapter extends RecyclerView.Adapter<SchoolsOrphanagesEntryAdapter.ViewHolder>{

    private static List<SchoolOrphanageEntries> schoolOrphanageEntries = null;
    private static Context context;

    public SchoolsOrphanagesEntryAdapter(List<SchoolOrphanageEntries> schoolOrphanageEntries, Context context) {
        this.schoolOrphanageEntries = schoolOrphanageEntries;
        this.context = context;
    }

    public void addAll(List<SchoolOrphanageEntries> schoolEntriesList){
        if(schoolOrphanageEntries.size()>0) {
            schoolOrphanageEntries.clear();
        }
        schoolOrphanageEntries.addAll(schoolEntriesList);
    }

    @Override
    public SchoolsOrphanagesEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.school_list_card_view,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolsOrphanagesEntryAdapter.ViewHolder holder, int position) {
        SchoolOrphanageEntries schoolEntry = schoolOrphanageEntries.get(position);
        holder.schoolName.setText(schoolEntry.getName());
        holder.schoolStrength.setText(Integer.toString(schoolEntry.getStrength()));
        holder.schoolContactNo.setText(schoolEntry.getContactNo());
        holder.schoolEmailId.setText(schoolEntry.getEmailId());
        holder.schoolAddress.setText(schoolEntry.getAddress());
    }

    @Override
    public int getItemCount() {
        return schoolOrphanageEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView schoolName;
        public TextView schoolStrength;
        public TextView schoolContactNo;
        public TextView schoolEmailId;
        public TextView schoolAddress;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrphanageDetailsActivity.class);
            intent.putExtra("OrphanageEntry", (Serializable)schoolOrphanageEntries.get(v.getVerticalScrollbarPosition()));
            context.startActivity(intent);
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            schoolName = (TextView)view.findViewById(R.id.edit_school_name);
            schoolStrength = (TextView)view.findViewById(R.id.edit_school_strength);
            schoolContactNo = (TextView)view.findViewById(R.id.edit_school_phone_no);
            schoolEmailId = (TextView)view.findViewById(R.id.edit_school_email);
            schoolAddress = (TextView)view.findViewById(R.id.edit_school_address);

        }
    }
}
