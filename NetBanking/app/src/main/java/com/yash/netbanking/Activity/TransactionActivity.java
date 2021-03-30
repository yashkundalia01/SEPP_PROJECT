package com.yash.netbanking.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yash.netbanking.Beneficiary;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    Client currentClient;
    Beneficiary beneficiary;
    EditText mPersonName, mPersonAccountNumber, mTotalAmount, mBankName, mBranchName, mIFSCCode;
    double amount = -100;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    String account_name, account_no, bank_name, bank_branch, bank_IFSC_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        currentClient = (Client) getIntent().getSerializableExtra("CurrentUser");
        beneficiary = (Beneficiary) getIntent().getSerializableExtra("beneficiary");
        getSupportActionBar().setTitle("Fund Transfer");

        mPersonAccountNumber = findViewById(R.id.editTextPersonAccountNumber);
        mPersonName = findViewById(R.id.editTextPersonName);
        mBankName = findViewById(R.id.editTextBankName);
        mBranchName = findViewById(R.id.editTextBankBranchName);
        mIFSCCode = findViewById(R.id.editTextBankIFSCCode);
        mTotalAmount = findViewById(R.id.editTextAmount);

        if (beneficiary != null){
            account_name = beneficiary.getBank_account_name();
            account_no = beneficiary.getBank_account_no();
            bank_branch = beneficiary.getBank_branch();
            bank_name = beneficiary.getBank_name();
            bank_IFSC_code = beneficiary.getBank_ifsc_code();

            mPersonName.setFocusable(false);
            mBankName.setFocusable(false);
            mBranchName.setFocusable(false);
            mPersonAccountNumber.setFocusable(false);
            mIFSCCode.setFocusable(false);

            mPersonAccountNumber.setText(account_no);
            mIFSCCode.setText(bank_IFSC_code);
            mBranchName.setText(bank_branch);
            mBankName.setText(bank_name);
            mPersonName.setText(account_name);


        }else {
            mPersonName.setFocusable(true);
            mBankName.setFocusable(true);
            mBranchName.setFocusable(true);
            mPersonAccountNumber.setFocusable(true);
            mIFSCCode.setFocusable(true);
        }

        myRef = database.getReference("client");

    }

    public void makeTransaction(View v){

        if (!mTotalAmount.getText().toString().isEmpty())
            amount = Double.parseDouble(mTotalAmount.getText().toString());
        account_no = mPersonAccountNumber.getText().toString();
        double balance = currentClient.getBalance()-amount;


        Log.e("TransactionActivity1", account_no + " " + amount);
        if (!account_name.isEmpty() && !account_no.isEmpty() && !bank_branch.isEmpty() && !bank_IFSC_code.isEmpty() && !bank_name.isEmpty() && amount != -100 && balance > 0){
            mTotalAmount.setError(null);
            currentClient.setBalance(balance);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot clientSnapshot: dataSnapshot.getChildren()){
                        Client client = clientSnapshot.getValue(Client.class);
                        if (account_no.equals(client.getAccountNo())){
                            Log.e("TransactionActivity2", account_no + " " + amount);
                            Date d = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                            String date = simpleDateFormat.format(d);

                            client.setBalance(client.getBalance()+amount);
                        try{
                            ArrayList<String> c1 = currentClient.getHistory();
                            ArrayList<String> c2 = client.getHistory();
                            if (c1 == null){
                                c1 = new ArrayList<>();
                            }
                            if (c2 == null){
                                c2 = new ArrayList<>();
                            }
                            c1.add(amount + "DD" + account_no + "DD" + date);
                            c2.add(amount + "CC" + currentClient.getAccountNo() + "CC" + date);

                            currentClient.setHistory(c1);
                            client.setHistory(c2);
                        }catch (Exception e){
                            Log.e("TransactionActivity", e.getMessage());
                            e.printStackTrace();
                        }

                            try {
                                final Client c = client;
                                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionActivity.this);
                                builder.setMessage("Are you sure you want to make the transaction?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        final Intent i = new Intent(TransactionActivity.this, ClientActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtra("Client1", currentClient);
                                        i.putExtra("Client2", c);
                                        startActivity(i);
                                        Toast.makeText(TransactionActivity.this, "Transaction has been Successfully done.", Toast.LENGTH_LONG).show();
                                    }
                                }).setNegativeButton("NO", null);
                                AlertDialog alert = builder.create();
                                alert.show();

                                break;
                            }catch (Exception e){

                            }
                            break;
                        }
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        } else {
            if (account_name.isEmpty()){
                mPersonName.setError("Please Enter an account name");
            }
            if (account_no.isEmpty()) {
                mPersonAccountNumber.setError("Please Enter an account no");
            }
            if (bank_branch.isEmpty()) {
                mBranchName.setError("Please Enter a bank branch");
            }
            if (bank_IFSC_code.isEmpty()) {
                mIFSCCode.setError("Please Enter a bank IFSC code");
            }
            if (bank_name.isEmpty()) {
                mBankName.setError("Please Enter a bank name");
            }
            if (amount == -100){
                mTotalAmount.setError("Please Enter an amount");
            }
            if (balance < 0) {
                mTotalAmount.setError("Not an enough money in your account");
            }

        }
    }

}