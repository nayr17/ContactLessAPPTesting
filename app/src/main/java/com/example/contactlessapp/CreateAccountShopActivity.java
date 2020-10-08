package com.example.contactlessapp;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.AccountType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountShopActivity extends AppCompatActivity {
    EditText shopName;
    EditText shopLocation;
    EditText shopUsername;
    EditText shopEmail;
    EditText shopPassword;
    EditText shopConfirmPassword;
    Button shopCreateAccount;

    String AccountType ="Shop";

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Registered_Users");

        String accountType = AccountType;
        String name = shopName.getText().toString().trim();
        String location = shopLocation.getText().toString().trim();
        String username = shopUsername.getText().toString().trim();
        String email = shopEmail.getText().toString().trim();
        String password = shopPassword.getText().toString().trim();
        String confirmPassword = shopConfirmPassword.getText().toString().trim();

        if(shopName.length() ==0){
            shopName.setError("field cannot be empty.");
        }
        if(shopLocation.length()==0){
            shopLocation.setError("field cannot be empty.");
        }
       if(shopUsername.length()==0){
            shopUsername.setError("field cannot be empty.");
        }
       if(shopEmail.length()==0){
            shopEmail.setError("field cannot be empty.");
        }
        if(shopPassword.length()==0){
            shopPassword.setError("field cannot be empty.");
        }
        if(shopConfirmPassword.length()==0){
            shopConfirmPassword.setError("field cannot be empty.");
        }
        else if (password != confirmPassword){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }

        if(password.equals(confirmPassword)){
            if(password.length()!=0){
                if(password.length()!=0){

                        CreateAccountShopHelperClass helperClass = new CreateAccountShopHelperClass(accountType, name, location, username, email, password);
                        myref.child(username).setValue(helperClass);

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                } 
            }


        }

    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}