package com.yash.netbanking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText mEditTextUsername, mEditTexPassword;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        progressBar = findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);
        myRef = database.getReference("client");
        mEditTexPassword = findViewById(R.id.editTextTextPasswordForLogin);
        mEditTextUsername = findViewById(R.id.editTextTextUsernameForLogin);
    }

    public void login(View view){
        progressBar.setVisibility(View.VISIBLE);
        username = mEditTextUsername.getText().toString();
        password = mEditTexPassword.getText().toString();

        if (username.equals("yash123") && password.equals("yash123")){
            Intent i = new Intent(this, EmployeeActivity.class);
            startActivity(i);
        }
        else if (!username.isEmpty() && !password.isEmpty()){

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean isValid = false;
                    Client client = null;

                    for (DataSnapshot clientSnapshot: dataSnapshot.getChildren()){

                        Client c = (Client) clientSnapshot.getValue(Client.class);
                        if (c.getUserName().equals(username) && c.getPassword().equals(password)){
                            isValid = true;
                            client = c;
                            break;
                        }
                    }
                    if (isValid){
                        Log.d("TAG", client.getFirstName()+" I'm here");
                        Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                        i.putExtra("Client", client);
                        startActivity(i);
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Please enter correct Username and Password!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Please enter correct Username and Password!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}