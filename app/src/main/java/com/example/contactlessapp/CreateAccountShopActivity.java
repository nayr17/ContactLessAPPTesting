package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountShopActivity extends AppCompatActivity {
    EditText shopName;
    EditText shopLocation;
    EditText shopUsername;
    EditText shopEmail;
    EditText shopPassword;
    EditText shopConfirmPassword;
    Button shopCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_shop);
        shopName = findViewById(R.id.editTextShopName);
        shopLocation = findViewById(R.id.editTextShopLocation);
        shopUsername = findViewById(R.id.editTextShopUsername);
        shopEmail = findViewById(R.id.editTextShopEmail);
        shopPassword = findViewById(R.id.editTextShopPassword);
        shopConfirmPassword = findViewById(R.id.editTextShopConfirmPassword);

    }

    public void btnShopCreateAccount(View view) {
        shopCreateAccount = findViewById(R.id.btnShopCreateAccount);
        /*

        code

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