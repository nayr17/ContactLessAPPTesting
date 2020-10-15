package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CustomerMainActivity extends AppCompatActivity {
     TextView name;
     TextView address;
     TextView phoneNumber;
     TextView barangay;
     TextView username;
     TextView email;
     TextView welcomeDisplay;
     ImageView userPic;
     ImageView generatredQR_code;

     private  String getUsername;
     String QR_ID = "contactlessapp-";


    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Registered_Users");

        name = findViewById(R.id.CustomerMain_Name);
        address = findViewById(R.id.CustomerMain_Address);
        phoneNumber = findViewById(R.id.CustomerMain_Phone);
        barangay = findViewById(R.id.CustomerMain_Barangay);
        username = findViewById(R.id.CustomerMain_Username);
        email = findViewById(R.id.CustomerMain_Email);
        welcomeDisplay = findViewById(R.id.welcomeMessage);
        userPic = findViewById(R.id.UserPic);
        generatredQR_code = findViewById(R.id.generatedQRCode);

        Intent intent = getIntent();
        getUsername = intent.getStringExtra("username_input");
        welcomeDisplay.setText("USER: " + getUsername);
        UserGenerateQR_code();





        DatabaseReference getUserRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + getUsername);
        getUserRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                   name.setText("Name: " + snapshot.child("name").getValue());
                   address.setText("Address: " + snapshot.child("address").getValue());
                   phoneNumber.setText("Phone number: " + snapshot.child("phoneNumber").getValue());
                   barangay.setText("Barangay: " + snapshot.child("barangay").getValue());
                   username.setText("Username: " + snapshot.child("username").getValue());
                   email.setText("Email: " +snapshot.child("emailAddress").getValue());

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerMainActivity.this, "Error! Restart app", Toast.LENGTH_SHORT).show();
            }
        });
//        FirebaseUser getEmailRef = firebaseAuth.getCurrentUser();
//        getEmailRef.getEmail().trim();
//        String emailRef = getEmailRef.toString().trim();
//        Toast.makeText(CustomerMainActivity.this,"email"+ emailRef,Toast.LENGTH_LONG).show();




    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference getUrl = FirebaseDatabase.getInstance().getReference("Registered_Users/" + getUsername + "/profilePhotoURL");
        getUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                getUrl.setValue(result);
                Picasso.get()
                        .load(result)
                        .into(userPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void CustomerMain_Logout(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(CustomerMainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnUpload(View view) {
        Intent intent = new Intent(CustomerMainActivity.this, CustomerUploadPicActivity.class);
        intent.putExtra("getUserRef", getUsername);
        startActivity(intent);
        finish();
    }

    public void btnEdit(View view) {
        Intent intent = new Intent(CustomerMainActivity.this, CustomerEditInformationActivity.class);
        intent.putExtra("getUsername", getUsername);
        startActivity(intent);
        finish();
    }
    public void UserGenerateQR_code(){
//        generatredQR_code.setVisibility(View.INVISIBLE);
        QR_ID = QR_ID + getUsername;
        String data = QR_ID.trim();
        QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,250);
        qrgEncoder.setColorBlack(Color.rgb(50,205,50));
        qrgEncoder.setColorWhite(Color.rgb(0,0,0));
        //Get QR code as bitmap
        Bitmap bitmap = qrgEncoder.getBitmap();
        //set bitmap as image
        generatredQR_code.setImageBitmap(bitmap);

        Toast.makeText(CustomerMainActivity.this, "" + data, Toast.LENGTH_LONG).show();
    }
}