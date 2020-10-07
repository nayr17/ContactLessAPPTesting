package com.example.contactlessapp.DbHelpers;

public class CreateAccountCustomerHelperClass {

    public CreateAccountCustomerHelperClass(){

    }
    String accountype;
    String name;
    String address;
    String phoneNumber;
    String barangay;
    String username;
    String emailAddress;
    String password;

    public CreateAccountCustomerHelperClass(String accountType, String name, String address, String phoneNumber, String barangay, String username, String emailAddress, String password) {
        this.accountype =accountType;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.barangay = barangay;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getAccountype() {
        return accountype;
    }

    public void setAccountype(String accountype) {
        this.accountype = accountype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
