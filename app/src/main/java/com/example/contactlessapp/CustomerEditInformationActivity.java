package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerEditInformationActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText phoneNumber;
    EditText barangay;
    EditText username;

    private String updateName;
    private String updateAddress;
    private String updatePhoneNumber;
    private String updateBarangay;
    private String updateUsername;


    private String getUsername;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit_information);
        firebaseAuth = FirebaseAuth.getInstance();


        name = findViewById(R.id.editInformationName);
        address = findViewById(R.id.editInformationAddress);
        phoneNumber = findViewById(R.id.editInformationBarangay);
        username = findViewById(R.id.editInformationUsername);

        Intent intent = getIntent();
        getUsername = intent.getStringExtra("getUsername");

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getUserRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);
        getUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}