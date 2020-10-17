package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerQRcodeActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView barangay;
    TextView phoneNumber;

    private String getUsername;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_q_rcode);
        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.customerQrCode_name);
        address = findViewById(R.id.customerQrCode_address);
        barangay = findViewById(R.id.customerQrCode_barangay);
        phoneNumber = findViewById(R.id.customerQrCode_phoneNumber);

        Intent intent = getIntent();
        getUsername = intent.getStringExtra("getUsername");
        getData();


    }

    private void getData() {
        userRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);
        userRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name.setText("Name: " + snapshot.child("name").getValue());
                    address.setText("Address: " + snapshot.child("address").getValue());
                    barangay.setText("Barangay: " + snapshot.child("barangay").getValue());
                    phoneNumber.setText("Phone number : " + snapshot.child("phoneNumber").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}