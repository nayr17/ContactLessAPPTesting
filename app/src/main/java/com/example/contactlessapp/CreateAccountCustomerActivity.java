package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountCustomerActivity extends AppCompatActivity {
    EditText CustomerName;
    EditText CustomerAddress;
    EditText CustomerPhoneNumber;
    EditText CustomerBarangay;
    EditText CustomerUsername;
    EditText CustomerEmail;
    EditText CustomerPass;
    EditText CustomerConfirmPassword;
    TextView goToLogin;
    Button CustomerCreateAccount;


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
        /*


        code
        if statement

         */

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();

    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}