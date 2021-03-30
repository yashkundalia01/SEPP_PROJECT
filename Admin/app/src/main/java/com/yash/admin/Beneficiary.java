package com.yash.admin;

import java.io.Serializable;

public class Beneficiary implements Serializable {
    private String bank_name;
    private String bank_account_name;
    private String bank_branch;
    private String bank_ifsc_code;
    private String bank_account_no;

    public Beneficiary(String bank_name, String bank_account_name, String bank_branch, String bank_ifsc_code, String bank_account_no) {
        this.bank_name = bank_name;
        this.bank_account_name = bank_account_name;
        this.bank_branch = bank_branch;
        this.bank_ifsc_code = bank_ifsc_code;
        this.bank_account_no = bank_account_no;
    }

    public Beneficiary() {

    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBank_ifsc_code() {
        return bank_ifsc_code;
    }

    public void setBank_ifsc_code(String bank_ifsc_code) {
        this.bank_ifsc_code = bank_ifsc_code;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }
}
