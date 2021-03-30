package com.yash.netbanking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yash.netbanking.R;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<String> {

    ArrayList<String> arrayList;
    public HistoryAdapter(Context context, ArrayList<String> history){
        super(context, 0, history);
        arrayList = history;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView date = view.findViewById(R.id.history_date);
        TextView statement = view.findViewById(R.id.history_statement);
        String temp = arrayList.get(position);
        String arr[];
        String dateOfTransaction, statementOfTransaction, amountOfTransaction;
        if (temp.contains("DD")){
            arr = temp.split("DD");
            dateOfTransaction = arr[2];
            amountOfTransaction = arr[0];
            statementOfTransaction = amountOfTransaction + " ₹ " + "Debited to "+'\n'+"Account no. :" + arr[1];
        }
        else {
            arr = temp.split("CC");
            dateOfTransaction = arr[2];
            amountOfTransaction = arr[0];
            statementOfTransaction = amountOfTransaction + " ₹ " + "Credited from "+'\n'+"Account No:" + arr[1];
        }
        date.setText(dateOfTransaction.substring(0, 8)+'\n'+dateOfTransaction.substring(8));
        statement.setText(statementOfTransaction);
        return view;
    }
}
