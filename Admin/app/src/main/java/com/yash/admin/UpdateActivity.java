package com.yash.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    Client client = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("client");

    TextInputEditText mtextInputFirstName,mtextInputLastName,mtextInputAge,mtextInputBirthDate,mtextInputPhoneNo,mtextInputAccountNo,mtextInputPanNo,mtextInputAadhaarNo,mtextInputAccountType;
    TextInputLayout mRadioInputLayoutGender,mtextInputValidationFirstName,mtextInputValidationLastName,mtextInputValidationAge,mtextInputValidationBirthDate,mtextInputValidationPhoneNo,mtextInputValidationAccountNo,mtextInputValidationPanNo,mtextInputValidationAadhaarNo,mtextInputValidationAccountType;
    MaterialRadioButton mRadioInputGenderMale,mRadioInputGenderFemale,mRadioInputGenderOther;

    String fName, lName, age, bDate, phoneNo, accountNo, panNo, aadhaarNo, accountType, gender;
    boolean isMale, isFemale, isOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initializedTextInputEditText();
        initializedTextInputLayout();
        initializedGenderInput();


        mRadioInputGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioInputGenderMale.isChecked()){
                    mRadioInputGenderFemale.setChecked(false);
                    mRadioInputGenderOther.setChecked(false);
                    gender = "male";
                }
            }
        });
        mRadioInputGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioInputGenderFemale.isChecked()){
                    mRadioInputGenderMale.setChecked(false);
                    mRadioInputGenderOther.setChecked(false);
                    gender = "female";
                }
            }
        });
        mRadioInputGenderOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioInputGenderOther.isChecked()){
                    mRadioInputGenderFemale.setChecked(false);
                    mRadioInputGenderMale.setChecked(false);
                    gender = "other";
                }
            }
        });

    }

    private void initializedGenderInput(){
        mRadioInputGenderMale = findViewById(R.id.male);
        mRadioInputGenderFemale = findViewById(R.id.female);
        mRadioInputGenderOther = findViewById(R.id.other);
    }

    private void initializedTextInputEditText(){
        mtextInputFirstName = (TextInputEditText) findViewById(R.id.first_name);
        mtextInputLastName = (TextInputEditText) findViewById(R.id.last_name);
        mtextInputAge = (TextInputEditText) findViewById(R.id.age);
        mtextInputBirthDate = (TextInputEditText) findViewById(R.id.birth_date);
        mtextInputPhoneNo = (TextInputEditText) findViewById(R.id.phone_number);
        mtextInputAccountNo = (TextInputEditText) findViewById(R.id.account_no);
        mtextInputPanNo = (TextInputEditText) findViewById(R.id.pancard_no);
        mtextInputAadhaarNo = (TextInputEditText) findViewById(R.id.aadhaar_no);
        mtextInputAccountType = (TextInputEditText) findViewById(R.id.account_type);
    }

    private void initializedTextInputLayout(){
        mRadioInputLayoutGender = findViewById(R.id.gender_validation);
        mtextInputValidationFirstName = findViewById(R.id.first_name_validation);
        mtextInputValidationLastName = findViewById(R.id.last_name_validation);
        mtextInputValidationAge = findViewById(R.id.age_validation);
        mtextInputValidationBirthDate = findViewById(R.id.birth_date_validation);
        mtextInputValidationPhoneNo = findViewById(R.id.phone_number_validation);
        mtextInputValidationAccountNo = findViewById(R.id.account_no_validation);
        mtextInputValidationPanNo = findViewById(R.id.pancard_no_validation);
        mtextInputValidationAadhaarNo = findViewById(R.id.aadhaarcard_no_validation);
        mtextInputValidationAccountType = findViewById(R.id.account_type_validation);
    }

    private boolean isValid(){
        boolean ans = true;

        if (fName.isEmpty()){
            mtextInputValidationFirstName.setError("Please enter a First Name!");
            ans = false;
        }else{
            mtextInputValidationFirstName.setErrorEnabled(false);
            for (int i = 0; i < fName.length(); i++){
                if (fName.charAt(i) >= '0' && fName.charAt(i) <= '9'){
                    mtextInputValidationFirstName.setError("Please enter a First Name!");
                    ans = false;
                    break;
                }
            }

            fName = mtextInputFirstName.getText().toString();
        }
        if (lName.isEmpty()){
            mtextInputValidationLastName.setError("Please enter a Last Name!");
            ans = false;
        }else{
            mtextInputValidationLastName.setErrorEnabled(false);
            for (int i = 0; i < lName.length(); i++){
                if (lName.charAt(i) >= '0' && lName.charAt(i) <= '9'){
                    mtextInputValidationLastName.setError("Please enter a Last Name!");
                    ans = false;
                    break;
                }
            }
            lName = mtextInputLastName.getText().toString();
        }
        if (age.isEmpty() || age.length() > 3){
            mtextInputValidationAge.setError("Please enter a proper Age!");
            ans = false;
        }else{
            mtextInputValidationAge.setErrorEnabled(false);
            for (int i = 0; i < age.length(); i++){
                if ((age.charAt(i) >= 'A' && age.charAt(i) <= 'Z') || (age.charAt(i) >= 'a' && age.charAt(i) <= 'z')){
                    mtextInputValidationAge.setError("Please enter a proper Age!");
                    ans = false;
                    break;
                }
            }
            age = mtextInputAge.getText().toString();
        }
        if (bDate.isEmpty() || bDate.length() != 10){
            mtextInputValidationBirthDate.setError("Please enter a proper Birth Date(DD/MM/YYYY)!");
            ans = false;
        }else{
            mtextInputValidationBirthDate.setErrorEnabled(false);
            for (int i = 0; i < bDate.length(); i++){
                if ((bDate.charAt(i) >= 'A' && bDate.charAt(i) <= 'Z') || (bDate.charAt(i) >= 'a' && bDate.charAt(i) <= 'z')){
                    mtextInputValidationBirthDate.setError("Please enter a proper Birth Date(DD/MM/YYYY)!");
                    ans = false;
                    break;
                }
            }
            bDate = mtextInputBirthDate.getText().toString();
        }
        if (phoneNo.isEmpty() || phoneNo.length() != 10){
            mtextInputValidationPhoneNo.setError("Please enter a proper Phone Number!");
            ans = false;
        }else{
            mtextInputValidationPhoneNo.setErrorEnabled(false);
            for (int i = 0; i < phoneNo.length(); i++){
                if ((phoneNo.charAt(i) >= 'A' && phoneNo.charAt(i) <= 'Z') || (phoneNo.charAt(i) >= 'a' && phoneNo.charAt(i) <= 'z')){
                    mtextInputValidationPhoneNo.setError("Please enter a Phone Number!");
                    ans = false;
                    break;
                }
            }
            phoneNo = mtextInputPhoneNo.getText().toString();
        }
        if (accountNo.isEmpty() || accountNo.length() != 11){
            mtextInputValidationAccountNo.setError("Please enter a proper Account Number!");
            ans = false;
        }else{
            mtextInputValidationAccountNo.setErrorEnabled(false);
            for (int i = 0; i < accountNo.length(); i++){
                if ((accountNo.charAt(i) >= 'A' && accountNo.charAt(i) <= 'Z') || (accountNo.charAt(i) >= 'a' && accountNo.charAt(i) <= 'z')){
                    mtextInputValidationAccountNo.setError("Please enter a proper Account Number!");
                    ans = false;
                    break;
                }
            }
            accountNo = mtextInputAccountNo.getText().toString();
        }
        if (aadhaarNo.isEmpty() || aadhaarNo.length() != 12){
            mtextInputValidationAadhaarNo.setError("Please enter a proper Aadhaarcard Number!");
            ans = false;
        }else{
            mtextInputValidationAadhaarNo.setErrorEnabled(false);
            for (int i = 0; i < aadhaarNo.length(); i++){
                if ((aadhaarNo.charAt(i) >= 'A' && aadhaarNo.charAt(i) <= 'Z') || (aadhaarNo.charAt(i) >= 'a' && aadhaarNo.charAt(i) <= 'z')){
                    mtextInputValidationAadhaarNo.setError("Please enter a proper Aadhaarcard Number!");
                    ans = false;
                    break;
                }
            }
            aadhaarNo = mtextInputAadhaarNo.getText().toString();
        }
        if (panNo.isEmpty() || panNo.length() != 10){
            mtextInputValidationPanNo.setError("Please enter a proper Pancard Number!");
            ans = false;
        }else{
            mtextInputValidationPanNo.setErrorEnabled(false);
            panNo = mtextInputPanNo.getText().toString();
        }
        if (accountType.isEmpty()){
            mtextInputValidationAccountType.setError("Please enter a proper Account Type!");
            ans = false;
        }else{
            mtextInputValidationAccountType.setErrorEnabled(false);
            for (int i = 0; i < accountType.length(); i++){
                if (accountType.charAt(i) >= '0' && accountType.charAt(i) <= '9'){
                    mtextInputValidationAccountType.setError("Please enter a proper Account Type!");
                    ans = false;
                    break;
                }
            }
            accountType= mtextInputAccountType.getText().toString();
        }
        
        return ans;
    }

    private boolean isGenderValid(){
        boolean ans = true;
        if (isFemale == false && isMale == false && isOther == false){
            mRadioInputLayoutGender.setError("Please select gender");
            ans = false;
        }else{
            mRadioInputLayoutGender.setErrorEnabled(false);
            ans = true;
        }
        return ans;
    }

    public void updateClient(View view){
        fName = mtextInputFirstName.getText().toString();
        lName = mtextInputLastName.getText().toString();
        age = mtextInputAge.getText().toString();
        bDate = mtextInputBirthDate.getText().toString();
        phoneNo = mtextInputPhoneNo.getText().toString();
        accountNo = mtextInputAccountNo.getText().toString();
        panNo = mtextInputPanNo.getText().toString();
        aadhaarNo = mtextInputAadhaarNo.getText().toString();
        accountType= mtextInputAccountType.getText().toString();

        isMale = mRadioInputGenderMale.isChecked();
        isFemale = mRadioInputGenderFemale.isChecked();
        isOther = mRadioInputGenderOther.isChecked();

        boolean isTextFieldValid = isValid();
        boolean isGenderSelected = isGenderValid();

        if (isTextFieldValid == true && isGenderSelected == true){

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
            builder.setMessage("Are you sure you want to submit?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Client c = new Client(fName, lName, age, gender, bDate, phoneNo, client.getUserName(), client.getPassword(), client.getClientId(), accountType, accountNo, client.getBalance(), panNo, aadhaarNo);
                    myRef.child(c.getClientId()).setValue(c);


                    mtextInputFirstName.setText("");
                    mtextInputLastName.setText("");
                    mtextInputAge.setText("");
                    mtextInputBirthDate.setText("");
                    mtextInputPhoneNo.setText("");
                    mtextInputAccountNo.setText("");
                    mtextInputPanNo.setText("");
                    mtextInputAadhaarNo.setText("");
                    mtextInputAccountType.setText("");
                    EditText text = findViewById(R.id.accountno);
                    text.setText("");

                    mRadioInputGenderMale.setChecked(false);
                    mRadioInputGenderFemale.setChecked(false);
                    mRadioInputGenderOther.setChecked(false);

                }
            }).setNegativeButton("NO", null);
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void getClient(View v){
        EditText text = findViewById(R.id.accountno);
        final String account_no = text.getText().toString();
        if (account_no != null && !account_no.isEmpty()){
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        Client current = d.getValue(Client.class);
                        if (current.getAccountNo().equals(account_no)){
                            client = current;
                            fName = current.getFirstName();
                            lName = current.getLastName();
                            age = current.getAge();
                            bDate = current.getDOB();
                            phoneNo = current.getMobileNo();
                            accountNo =current.getAccountNo();
                            panNo = current.getPanNo();
                            aadhaarNo = current.getAadhaarNo();
                            accountType= current.getAccountType();
                            gender = current.getGender();

                            setDate();
                            Log.d("UpdateActivity", fName + " " + lName + " " + gender + " " + current.getDOB() + " " + current);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }else {
            Toast.makeText(this, "Please enter the account number", Toast.LENGTH_LONG).show();
        }
    }

    public void setDate(){

        if (gender.equals("female")){
            mRadioInputGenderMale.setChecked(false);
            mRadioInputGenderFemale.setChecked(true);
            mRadioInputGenderOther.setChecked(false);
        }else if (gender.equals("male")) {
            mRadioInputGenderMale.setChecked(true);
            mRadioInputGenderFemale.setChecked(false);
            mRadioInputGenderOther.setChecked(false);
        }else {
            mRadioInputGenderMale.setChecked(false);
            mRadioInputGenderFemale.setChecked(false);
            mRadioInputGenderOther.setChecked(true);
        }

        mtextInputFirstName.setText(fName);
        mtextInputLastName.setText(lName);
        mtextInputAge.setText(age);
        mtextInputBirthDate.setText(bDate);
        mtextInputPhoneNo.setText(phoneNo);
        mtextInputAccountNo.setText(accountNo);
        mtextInputPanNo.setText(panNo);
        mtextInputAadhaarNo.setText(aadhaarNo);
        mtextInputAccountType.setText(accountType);
    }
    
}