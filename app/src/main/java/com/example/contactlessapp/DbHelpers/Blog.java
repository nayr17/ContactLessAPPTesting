package com.example.contactlessapp.DbHelpers;

public class Blog {
    String name, address, phoneNumber, date, time, QR_ID;



    public Blog(String name, String address, String phoneNumber, String date, String time, String QR_ID) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.QR_ID = QR_ID;
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

    public String getQR_ID() {
        return QR_ID;
    }

    public void setQR_ID(String QR_ID) {
        this.QR_ID = QR_ID;
    }
    public Blog() {

    }
}
