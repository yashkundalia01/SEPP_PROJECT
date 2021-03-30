package com.yash.admin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmployeeActivity extends AppCompatActivity {

    FloatingActionButton mFloatingActionButton;
    FloatingActionButton mFloatingActionButtonAdd;
    FloatingActionButton mFloatingActionButtonUpdate;
    FloatingActionButton mFloatingActionButtonDelete;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mFloatingActionButton = findViewById(R.id.addclient);
        mFloatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        mFloatingActionButtonUpdate = findViewById(R.id.floatingActionButtonupdate);
        mFloatingActionButtonDelete = findViewById(R.id.floatingActionButtonDelete);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    mFloatingActionButtonUpdate.show();
                    mFloatingActionButtonAdd.show();
                    mFloatingActionButtonDelete.show();
                    mFloatingActionButton.setImageResource(R.drawable.ic_baseline_close_24);
                    flag = false;
                }else {
                    mFloatingActionButtonAdd.hide();
                    mFloatingActionButtonUpdate.hide();
                    mFloatingActionButtonDelete.hide();
                    mFloatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                    flag = true;
                }
            }
        });

        mFloatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClient();
            }
        });
        mFloatingActionButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeeActivity.this, UpdateActivity.class);
                startActivity(i);
            }
        });

    }

    public void addClient(){
        Intent i = new Intent(this, AddClientActivity.class);
        startActivity(i);
    }

    public void deleteClient(View v){
        Intent i = new Intent(this, DeleteClientActivity.class);
        startActivity(i);
    }


}