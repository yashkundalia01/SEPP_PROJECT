package com.yash.netbanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yash.netbanking.R;

public class EmployeeActivity extends AppCompatActivity {

    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mFloatingActionButton = findViewById(R.id.add_client);

    }

    public void addClient(View view){
        Intent i = new Intent(this, AddClientActivity.class);
        startActivity(i);
    }

}