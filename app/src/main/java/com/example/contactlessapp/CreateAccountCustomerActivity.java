package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountCustomerActivity extends AppCompatActivity {
    EditText CustomerName;
    EditText CustomerAddress;
    EditText CustomerPhoneNumber;
    EditText CustomerBarangay;
    EditText CustomerUsername;
    EditText CustomerEmail;
    EditText CustomerPass;
    EditText CustomerConfirmPassword;

    String AccountType ="Customer";
    FirebaseDatabase myDB;
    DatabaseReference users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);

        CustomerName = findViewById(R.id.editTextCustomerName);
        CustomerAddress = findViewById(R.id.editTextCustomerAddress);
        CustomerPhoneNumber = findViewById(R.id.editTextCustomerPhoneNO);
        CustomerBarangay = findViewById(R.id.editTextCustomerBarangay);
        CustomerUsername = findViewById(R.id.editTextCustomerUsername);
        CustomerEmail = findViewById(R.id.editTextCustomerEmail);
        CustomerPass = findViewById(R.id.editTextCustomerPassword);
        CustomerConfirmPassword = findViewById(R.id.editTextCustomerConfirmPassword);

    }

    public void btnCustomerCreateAccount (View view) {
       myDB = FirebaseDatabase.getInstance();
       users = myDB.getReference("Users");

       String accountType = AccountType;
       String name = CustomerName.getText().toString().trim();
       String address = CustomerAddress.getText().toString().trim();
       String phoneNumber =CustomerPhoneNumber.getText().toString().trim();
       String barangay = CustomerBarangay.getText().toString().trim();
       String username = CustomerUsername.getText().toString().trim();
       String email = CustomerEmail.getText().toString().trim();
       String password = CustomerPass.getText().toString().trim();
       String confirmpass = CustomerConfirmPassword.getText().toString().trim();


           CreateAccountCustomerHelperClass helperClass = new CreateAccountCustomerHelperClass( accountType, name, address, phoneNumber, barangay, username, email, password);
           users.child(username).setValue(helperClass);
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();
           finish();


    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}