package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CustomerQRcodeActivity extends AppCompatActivity {

    TextView name;
    TextView address;
    TextView barangay;
    TextView phoneNumber;
    ImageView userQrCode;

    private String getUsername;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;
    private String QR_text_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_q_rcode);
        firebaseAuth = FirebaseAuth.getInstance();

//        name = findViewById(R.id.customerQrCode_name);
//        address = findViewById(R.id.customerQrCode_address);
//        barangay = findViewById(R.id.customerQrCode_barangay);
//        phoneNumber = findViewById(R.id.customerQrCode_phoneNumber);
        userQrCode = findViewById(R.id.userQrCode);

        Intent intent = getIntent();
        getUsername = intent.getStringExtra("getUsername");
//        getData();
        UserGenerateQR_code();




    }

//    private void getData() {
//        userRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);
//        userRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    name.setText("Name: " + snapshot.child("name").getValue());
//                    address.setText("Address: " + snapshot.child("address").getValue());
//                    barangay.setText("Barangay: " + snapshot.child("barangay").getValue());
//                    phoneNumber.setText("Phone number : " + snapshot.child("phoneNumber").getValue());
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void UserGenerateQR_code(){
//        userQrCode.setVisibility(View.INVISIBLE);
        userRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    QR_text_ID = snapshot.child("QR_Text_ID").getValue().toString().trim();
                    Toast.makeText(CustomerQRcodeActivity.this," " + QR_text_ID, Toast.LENGTH_SHORT).show();
                    String data = QR_text_ID;
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT,250);
                    qrgEncoder.setColorBlack(Color.rgb(250,205,50));
                    //Get QR code as bitmap
                    Bitmap bitmap = qrgEncoder.getBitmap();
                    //set bitmap as image
                    userQrCode.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}