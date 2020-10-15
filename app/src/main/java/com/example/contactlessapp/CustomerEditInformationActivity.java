package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;

public class CustomerEditInformationActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText phoneNumber;
    EditText barangay;
    EditText username;
    ImageView userPic;

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
        phoneNumber = findViewById(R.id.editInformationPhoneNumber);
        barangay = findViewById(R.id.editInformationBarangay);
        username = findViewById(R.id.editInformationUsername);
        userPic = findViewById(R.id.UserPic);

        Intent intent = getIntent();
        getUsername = intent.getStringExtra("getUsername");




    }

    public void btnUpdate(View view) {

        updateName = name.getText().toString().trim();
        updateAddress = address.getText().toString().trim();
        updatePhoneNumber = phoneNumber.getText().toString().trim();
        updateBarangay = barangay.getText().toString().trim();
        updateUsername = username.getText().toString().trim();

        if(TextUtils.isEmpty(updateName)){
            name.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(updateAddress)){
            address.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty((updatePhoneNumber))){
            phoneNumber.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(updateBarangay)){
            barangay.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(updateUsername)){
            username.setError("field cannot be empty.");
            return;
        }
        if(updateName != null && updateAddress != null && updatePhoneNumber != null
            && updateBarangay != null && updateUsername != null){

            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference userRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);

            userRef.child("name").setValue(updateName);
            userRef.child("address").setValue(updateAddress);
            userRef.child("phoneNumber").setValue(updatePhoneNumber);
            userRef.child("barangay").setValue(updateBarangay);
            userRef.child("username").setValue(updateUsername);

        }

//        final DatabaseReference getUserRef = firebaseDatabase.getReference("Registered_Users/" + getUsername);
//        getUserRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
////                    DatabaseReference getNameRef = firebaseDatabase.getReference("Registered_Users/" + getUsername + "/name");
//
//                    getUserRef.child("name").setValue(updateName);
//                    getUserRef.child("address").setValue(updateAddress);
//                    getUserRef.child("phoneNumber").setValue(updatePhoneNumber);
//                    getUserRef.child("barangay").setValue(updateBarangay);
//                    getUserRef.child("username").setValue(updateUsername);
//
//                    Toast.makeText(CustomerEditInformationActivity.this, "Your information has been updated!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CustomerEditInformationActivity.this, CustomerMainActivity.class);
//                    startActivity(intent);
//
//                }
//                else{
//                    Toast.makeText(CustomerEditInformationActivity.this, "Error!, please try again", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(CustomerEditInformationActivity.this, ""+ error, Toast.LENGTH_SHORT).show();
//            }
//        });
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


}