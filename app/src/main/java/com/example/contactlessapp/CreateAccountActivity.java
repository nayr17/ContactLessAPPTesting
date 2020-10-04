package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton select;
    Button btnNext;
    RadioButton customer;
    RadioButton shop;
    RadioButton establishment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        radioGroup = findViewById(R.id.radioGroup);
        btnNext = findViewById(R.id.btnNext);
        customer = findViewById(R.id.radioButtonCustomer);
        shop = findViewById(R.id.radioButtonShop);
        establishment = findViewById(R.id.radioButtonEstablishment);

    }

    public void checkButton(View v) {
        int radID = radioGroup.getCheckedRadioButtonId();
        select = findViewById(radID);
        Toast.makeText(this, "Selected account type " + "'" + select.getText() +"'", Toast.LENGTH_SHORT).show();
    }

    public void btnNext(View view) {
        int radID = radioGroup.getCheckedRadioButtonId();
        select = findViewById(radID);
        if (select == customer) {
            Intent i = new Intent(this, CreateAccountCustomerActivity.class);
            startActivity(i);
        }
        if (select == shop) {
            Intent i = new Intent(this, CreateAccountShopActivity.class);
            startActivity(i);
        }
        if (select == establishment) {
            Intent i = new Intent(this, CreateAccountEstablishmentActivity.class);
            startActivity(i);
        }
    }
}

