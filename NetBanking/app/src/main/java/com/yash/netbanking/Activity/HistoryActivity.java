package com.yash.netbanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.yash.netbanking.Adapter.HistoryAdapter;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("Transaction History");

        Client c = (Client) getIntent().getSerializableExtra("CurrentUser");
        if (c.getHistory() != null){
            ArrayList<String> arr = new ArrayList<>(c.getHistory());
            Collections.reverse(arr);
            HistoryAdapter arrayAdapter = new HistoryAdapter(this, arr);
            ListView l = findViewById(R.id.list_view);
            l.setAdapter(arrayAdapter);

            TextView empty = findViewById(R.id.emptyView);
            empty.setText("No transaction yet");
            l.setEmptyView(empty);

        }
    }
}