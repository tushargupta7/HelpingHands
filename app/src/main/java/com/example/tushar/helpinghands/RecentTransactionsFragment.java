package com.example.tushar.helpinghands;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class RecentTransactionsFragment extends Fragment {
    private static Context mContext;
    public RecentTransactionsFragment(Context context){
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.recent_transactions_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recent_transactions_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(getAdapter());
        return rootView;
    }

    private static RecentTransactionsAdapter getAdapter() {
        String myJsonString = null;
        List<RecentTransactionEntries> transactionList = null;
        try {
            myJsonString = AssetJSONFile("childeren.json");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            transactionList = new ArrayList<>();
            JSONObject jsonObjMain = new JSONObject(myJsonString);
            JSONArray jsonArray1 = jsonObjMain.optJSONArray("Data");
            JSONObject abc = jsonArray1.getJSONObject(3);
            JSONArray jsonArray = abc.optJSONArray("Transaction");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                RecentTransactionEntries entry = getTransactionEntry(jsonObj);
                transactionList.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecentTransactionsAdapter transactionsAdapter = new RecentTransactionsAdapter(transactionList);
        return transactionsAdapter;
    }

    public static String AssetJSONFile(String filename) throws IOException {
        String json = null;
        try {

            InputStream is = mContext.getAssets().open("children.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static RecentTransactionEntries getTransactionEntry(JSONObject jsonObject) {
        RecentTransactionEntries transactionEntry = null;
        try {
            transactionEntry = new RecentTransactionEntries(jsonObject.getString("t_id"),jsonObject.getString("t_id"), jsonObject.getString("spon_id"), jsonObject.getString("stu_id"), jsonObject.getString("product_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionEntry;
    }

}
