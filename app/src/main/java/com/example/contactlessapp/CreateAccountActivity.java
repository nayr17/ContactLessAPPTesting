package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioSelect;
    private RadioButton customer;
    private RadioButton shop;
    private RadioButton establishment;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        radioSelect =(RadioGroup)findViewById(R.id.RadioGroup);
        customer =(RadioButton)findViewById(R.id.radioButtonCustomer);
        shop =(RadioButton)findViewById(R.id.radioButtonShop);
        establishment =(RadioButton)findViewById(R.id.radioButtonEstablishment);
        btnNext =(Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(this);

        radioSelect.setOnClickListener(this);

        if(customer.isChecked()){
            shop.setChecked(false);
            establishment.setChecked(false);
        }
        if(shop.isChecked()){
            customer.setChecked(false);
            establishment.setChecked(false);
        }
        if(establishment.isChecked()){
            customer.setChecked(false);
            shop.setChecked(false);
        }



    }

    @Override
    public void onClick(View v) {
        if(v == btnNext){
            if(customer.isChecked()){
                shop.setChecked(false);
                establishment.setChecked(false);
                Intent intent = new Intent(this,CreateAccountCustomerActivity.class);
                startActivity(intent);
            }else if(shop.isChecked()){
                customer.setChecked(false);
                establishment.setChecked(false);
                Intent intent= new Intent(this, CreateAccountShopActivity.class);
                startActivity(intent);
            }else if(establishment.isChecked()){
                shop.setChecked(false);
                customer.setChecked(false);
                Intent intent = new Intent(this,CreateAccountEstablishmentActivity.class);
            }else if(customer.isChecked() && shop.isChecked() && establishment.isChecked()){
                Toast.makeText(this,"Invalid account type, please select one only!",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Invalid account type, please select!",Toast.LENGTH_LONG).show();
            }
        }

    }
}