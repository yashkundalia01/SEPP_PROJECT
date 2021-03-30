package com.yash.admin;

import java.io.Serializable;
import java.util.ArrayList;

public class Client extends Person implements Serializable
{
    private String userName;
    private String password;
    private String clientId;
    private String accountType;
    private String accountNo;
    private double balance;
    private String panNo;
    private String aadhaarNo;
    private String lastLogin;
    private ArrayList<String> history;
    private ArrayList<Beneficiary> beneficiary;
    private ArrayList<Beneficiary> favourite_transaction;

    public void setPassword(String password) {
        this.password = password;
    }

    public Client(){
        super();
    }

    public Client(String firstName, String lastName, String age, String gender, String dOB, String mobileNo, String userName, String password, String clientId, String accountType, String accountNo, double balance, String panNo, String aadhaarNo)
    {
        super(firstName, lastName, age, gender, dOB, mobileNo);
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
        this.accountType = accountType;
        this.accountNo = accountNo;
        this.balance = balance;
        this.panNo = panNo;
        this.aadhaarNo = aadhaarNo;
        history = new ArrayList<>();
        beneficiary = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getClientId() {
        return clientId;
    }
    public String getAccountType() {
        return accountType;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public double getBalance() {
        return balance;
    }
    public String getPanNo() {
        return panNo;
    }
    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public ArrayList<Beneficiary> getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(ArrayList<Beneficiary> beneficiary) {
        this.beneficiary = beneficiary;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public void setBalance(double b){
        balance = b;
    }

    public ArrayList<Beneficiary> getFavourite_transaction() {
        return favourite_transaction;
    }

    public void setFavourite_transaction(ArrayList<Beneficiary> favourite_transaction) {
        this.favourite_transaction = favourite_transaction;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Client{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", clientId='" + clientId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                ", panNo='" + panNo + '\'' +
                ", aadhaarNo='" + aadhaarNo + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", history=" + history +
                ", beneficiary=" + beneficiary +
                ", favourite_transaction=" + favourite_transaction +
                '}';
    }
}

