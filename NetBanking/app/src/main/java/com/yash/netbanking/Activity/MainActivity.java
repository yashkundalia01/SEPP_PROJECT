package com.yash.netbanking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.yash.netbanking.R;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.main_layout);
    }

    public void startNewActivity(View v){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected())
        {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
        else {

            Snackbar s = Snackbar.make(mainLayout, "No Internet Connection", Snackbar.LENGTH_LONG);

            s.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeConnection();
                }
            });
            s.show();

        }

    }

    public void makeConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected())
        {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
        else {

            Snackbar s = Snackbar.make(mainLayout, "No Internet Connection", Snackbar.LENGTH_LONG);

            s.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeConnection();
                }
            });
            s.show();

        }
    }

}