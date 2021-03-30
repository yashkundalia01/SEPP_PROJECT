package com.yash.netbanking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yash.netbanking.Beneficiary;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;

import java.util.ArrayList;

public class AddBeneficiaryActivity extends AppCompatActivity {

    EditText mPersonName, mBankName, mBankBranchName, mBankIFSCCode, mAccountNo;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);

        getSupportActionBar().setTitle("Add Beneficiary");

        mPersonName = findViewById(R.id.beneficiary_person_name);
        mBankBranchName = findViewById(R.id.beneficiary_bank_branch);
        mBankName = findViewById(R.id.beneficiary_bank_name);
        mAccountNo = findViewById(R.id.beneficiary_account_no);
        mBankIFSCCode = findViewById(R.id.beneficiary_IFSC_code);

        client = (Client) getIntent().getSerializableExtra("CurrentUser");

    }


    public void addBeneficiary(View v){

        String name, account_no, bank_branch, bank_name, bank_ifsc_code;
        name = mPersonName.getText().toString();
        account_no = mAccountNo.getText().toString();
        bank_ifsc_code = mBankIFSCCode.getText().toString();
        bank_branch = mBankBranchName.getText().toString();
        bank_name = mBankName.getText().toString();

        if (!name.isEmpty() && !account_no.isEmpty() && !bank_branch.isEmpty() && !bank_ifsc_code.isEmpty() && !bank_name.isEmpty()){
            mPersonName.setError(null);
            mAccountNo.setError(null);
            mBankBranchName.setError(null);
            mBankIFSCCode.setError(null);
            mBankName.setError(null);

            Beneficiary t = new Beneficiary(bank_name, name, bank_branch, bank_ifsc_code, account_no);
            ArrayList<Beneficiary> arrayList = client.getBeneficiary();
            if (arrayList == null){
                arrayList = new ArrayList<>();
            }
            arrayList.add(t);
            client.setBeneficiary(arrayList);

            String id = client.getClientId();
            new Task().execute(id);
        }else {
            if (name.isEmpty()){
                mPersonName.setError("Please Enter an account name");
            }
            if (account_no.isEmpty()) {
                mAccountNo.setError("Please Enter an account no");
            }
            if (bank_branch.isEmpty()) {
                mBankBranchName.setError("Please Enter a bank branch");
            }
            if (bank_ifsc_code.isEmpty()) {
                mBankIFSCCode.setError("Please Enter a bank IFSC code");
            }
            if (bank_name.isEmpty()) {
                mBankName.setError("Please Enter a bank name");
            }
        }

    }

    private class Task extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... id) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("client").child(id[0]).setValue(client);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(getApplicationContext(), BeneficiaryActivity.class);
            i.putExtra("CurrentUser", client);
            startActivity(i);
        }
    }

}