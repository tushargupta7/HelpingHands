package com.example.tushar.helpinghands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tushar.helpinghands.OrphanageDetailsActivity;
import com.example.tushar.helpinghands.models.OrphanageEntries;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hussain on 11/4/16.
 */
class OrphanagesEntryAdapter extends RecyclerView.Adapter<OrphanagesEntryAdapter.ViewHolder>{

    private static List<OrphanageEntries> orphanageEntries = null;
    private static Context context;

    public OrphanagesEntryAdapter(List<OrphanageEntries> orphanageEntries, Context context) {
        this.orphanageEntries = orphanageEntries;
        this.context = context;
    }

    public void addAll(List<OrphanageEntries> orphanageEntriesList){
        if(orphanageEntries.size()>0) {
            orphanageEntries.clear();
        }
        orphanageEntries.addAll(orphanageEntriesList);
    }

    @Override
    public OrphanagesEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orphanage_list_card_view,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrphanagesEntryAdapter.ViewHolder holder, int position) {
        OrphanageEntries orphanageEntry = orphanageEntries.get(position);
        holder.orphanageName.setText(orphanageEntry.getName());
        holder.orphanageStrength.setText(Integer.toString(orphanageEntry.getStrength()));
        holder.orphanageContactNo.setText(orphanageEntry.getContactNo());
        holder.orphanagePincode.setText(orphanageEntry.getPincode());
        holder.orphanageAddress.setText(orphanageEntry.getAddress());
    }

    @Override
    public int getItemCount() {
        return orphanageEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orphanageName;
        public TextView orphanageStrength;
        public TextView orphanageContactNo;
        public TextView orphanagePincode;
        public TextView orphanageAddress;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrphanageDetailsActivity.class);
            intent.putExtra("OrphanageEntry", (Serializable) orphanageEntries.get(v.getVerticalScrollbarPosition()));
            context.startActivity(intent);
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            orphanageName = (TextView)view.findViewById(R.id.edit_orphanage_name);
            orphanageStrength = (TextView)view.findViewById(R.id.edit_orphanage_strength);
            orphanageContactNo = (TextView)view.findViewById(R.id.edit_orphanage_phone_no);
            orphanagePincode = (TextView)view.findViewById(R.id.edit_orphanage_picode);
            orphanageAddress = (TextView)view.findViewById(R.id.edit_orphanage_address);

        }
    }
}
