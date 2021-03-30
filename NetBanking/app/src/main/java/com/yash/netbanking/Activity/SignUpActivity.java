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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yash.netbanking.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mEditTextMail, getmEditTextPassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar = findViewById(R.id.progressBarSignUp);
        progressBar.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();
        mEditTextMail = findViewById(R.id.editTextTextEmailAddress);
        getmEditTextPassword = findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    }

    public void login(View v){
        Intent i =new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    public void signUp(View v){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mEditTextMail.getText().toString(), getmEditTextPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("SignUP", "Successfully");
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else{
                    Log.d("SignUP", "UnSuccessful");
                    Toast.makeText(SignUpActivity.this, "Please enter correct Email Id and Password!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

}