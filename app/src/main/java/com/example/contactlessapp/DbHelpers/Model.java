package com.example.contactlessapp.DbHelpers;

public class Model {

    String name, address, phoneNumber, date, time, photoUrl, username;

    public Model() {

    }

    public Model(String name, String address, String phoneNumber, String date, String time, String photoUrl, String username) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.photoUrl = photoUrl;
        this.username = username;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUsername() {return  username;}

    public void  setUsername(String username) {this.username = username;}
}
