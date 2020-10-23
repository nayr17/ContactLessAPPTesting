package com.example.contactlessapp.DbHelpers;

public class GetCustomerInfo {

    public GetCustomerInfo() {

    }
    String  address, barangay, emailAddress, name, phoneNumber, usernmae, date, time, photoUrl;

    public GetCustomerInfo(String address, String barangay, String emailAddress, String name, String phoneNumber, String usernmae, String date, String time, String photoUrl) {
        this.address = address;
        this.barangay = barangay;
        this.emailAddress = emailAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.usernmae = usernmae;
        this.date = date;
        this.time = time;
        this. photoUrl = photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsernmae() {
        return usernmae;
    }

    public void setUsernmae(String usernmae) {
        this.usernmae = usernmae;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
