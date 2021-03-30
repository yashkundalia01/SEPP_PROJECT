package com.yash.netbanking.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;

public class ChangePasswordActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("client");

    EditText mOldPasswordEditText;
    EditText mNewPasswordEditText;

    Client c;

    String oldPassword, newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");

        final Client client = (Client) getIntent().getSerializableExtra("CurrentUser");
        c = client;

        mOldPasswordEditText = findViewById(R.id.editTextOldPassword);
        mNewPasswordEditText = findViewById(R.id.editTextNewPassword);
    }

    public void doChange(View view){
        oldPassword = mOldPasswordEditText.getText().toString();
        newPassword = mNewPasswordEditText.getText().toString();

        if (oldPassword.equals(c.getPassword())){
            c.setPassword(newPassword);
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
            builder.setMessage("Are you sure you want to change the password?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myRef.child(c.getClientId()).setValue(c);
                    Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
            }).setNegativeButton("NO", null);
            AlertDialog alert = builder.create();
            alert.show();

        }else {
            Toast.makeText(this, "Please enter correct password", Toast.LENGTH_LONG).show();
        }
    }

}