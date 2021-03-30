package com.yash.admin;

import java.io.Serializable;

public class Person implements Serializable
{
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String dob;
    private String mobileNo;

    public Person(){

    }

    public Person(String firstName, String lasttName, String age, String gender, String dOB, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lasttName;
        this.age = age;
        this.gender = gender;
        dob = dOB;
        this.mobileNo = mobileNo;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getDOB() {
        return dob;
    }
    public String getMobileNo() {
        return mobileNo;
    }
}

