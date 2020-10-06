package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountEstablishmentActivity extends AppCompatActivity {
    EditText establishmentName;
    EditText establishmentLocation;
    EditText establishmentUsername;
    EditText establishmentEmail;
    EditText establishmentPassword;
    EditText establishmentConfirmPassword;
    Button establishmentCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_establishment);
        establishmentName = findViewById(R.id.editTextEstablishmentName);
        establishmentLocation = findViewById(R.id.editTextEstablishmentLocation);
        establishmentUsername = findViewById(R.id.editTextEstablishmentUsername);
        establishmentEmail = findViewById(R.id.editTextEstablishmentemail);
        establishmentPassword = findViewById(R.id.editTextEstablishmentPassword);
        establishmentConfirmPassword= findViewById(R.id.editTextEstablishmentConfirmPassword);
    }


    public void btnEstablishmentCreateAccount(View view) {
        establishmentCreateAccount = findViewById(R.id.btnEstablishmentCreateAccount);
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