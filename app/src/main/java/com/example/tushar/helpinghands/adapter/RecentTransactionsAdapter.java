package com.example.tushar.helpinghands.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tushar.helpinghands.R;
import com.example.tushar.helpinghands.RecentTransactionEntries;

import java.util.List;

/**
 * Created by hussain on 11/4/16.
 */
public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsAdapter.ViewHolder>{

    private static List<RecentTransactionEntries> transactionEntries;

    public RecentTransactionsAdapter(List<RecentTransactionEntries> transactionEntries) {
        this.transactionEntries = transactionEntries;
    }
    @Override
    public RecentTransactionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_transactions_list_card_view,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecentTransactionsAdapter.ViewHolder holder, int position) {
        RecentTransactionEntries transactionEntry = transactionEntries.get(position);
        //TODO enter proper values
        holder.sponsorName.setText(transactionEntry.getSponsorName());
        holder.sponsoredTo.setText(transactionEntry.getSponsoredTo());
        holder.sponsoredID.setText(transactionEntry.getSponsoredId());
        holder.sponsoredItem.setText(transactionEntry.getSponsoredItem());
        holder.sponsoredTime.setText(transactionEntry.getSponsoredTime());
    }

    @Override
    public int getItemCount() {
        return transactionEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView sponsorName;
        public TextView sponsoredTo;
        public TextView sponsoredID;
        public TextView sponsoredItem;
        public TextView sponsoredTime;

        @Override
        public void onClick(View v) {
            /*Intent intent = new Intent(context, StudentDetailsActivity.class);
            intent.putExtra("StudentEntry", (Serializable)transactionEntries.get(v.getVerticalScrollbarPosition()));
            context.startActivity(intent);*/
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            sponsorName = (TextView)view.findViewById(R.id.edit_sponsor_name);
            sponsoredTo = (TextView)view.findViewById(R.id.edit_sponsored_child);
            sponsoredID = (TextView)view.findViewById(R.id.edit_unique_id);
            sponsoredItem = (TextView)view.findViewById(R.id.edit_sponsored_item);
            sponsoredTime = (TextView)view.findViewById(R.id.edit_sponsored_time);

        }
    }
}
