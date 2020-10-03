package com.example.contactlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassActivity extends AppCompatActivity {
    EditText emailforpass_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailforpass_input = findViewById(R.id.emailforpass_xml);
        Button resetpass_button = findViewById(R.id.resetpassbutton_xml);

        resetpass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailforpass_string = emailforpass_input.getText().toString().trim();
                if(TextUtils.isEmpty(emailforpass_string)){
                    emailforpass_input.setError("Email is required");
                    return;
                }
            }
        });
    }
}