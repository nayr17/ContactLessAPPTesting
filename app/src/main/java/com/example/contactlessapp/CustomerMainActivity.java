package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerMainActivity extends AppCompatActivity {
     TextView name;
     TextView address;
     TextView phoneNumber;
     TextView barangay;
     TextView username;
     TextView email;
     TextView welcomeDisplay;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.CustomerMain_Name);
        address = findViewById(R.id.CustomerMain_Address);
        phoneNumber = findViewById(R.id.CustomerMain_Phone);
        barangay = findViewById(R.id.CustomerMain_Barangay);
        username = findViewById(R.id.CustomerMain_Username);
        email = findViewById(R.id.CustomerMain_Email);
        welcomeDisplay = findViewById(R.id.welcomeMessage);

        FirebaseUser getEmailRef = firebaseAuth.getCurrentUser();
        getEmailRef.getEmail().trim();
        String emailRef = getEmailRef.toString().trim();
        Toast.makeText(CustomerMainActivity.this,"email"+ emailRef,Toast.LENGTH_LONG).show();

        DatabaseReference getUserRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcomeDisplay.setText("Hi " + user.getEmail());


    }

    public void CustomerMain_Logout(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(CustomerMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}