package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        EditText emailforpass_input = findViewById(R.id.emailforpass_xml);
        Button resetpass_button = findViewById(R.id.resetpassbutton_xml);
    }
}