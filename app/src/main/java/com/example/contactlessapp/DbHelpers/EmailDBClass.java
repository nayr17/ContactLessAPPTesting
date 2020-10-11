package com.example.contactlessapp.DbHelpers;

public class EmailDBClass {
    String accountType, email, password;

    public EmailDBClass() {
    }

    public EmailDBClass(String accountType, String email, String password) {
        this.accountType = accountType;
        this.email = email;
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
