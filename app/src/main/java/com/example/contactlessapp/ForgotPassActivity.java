package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText emailforpass_input;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);


        firebaseAuth = FirebaseAuth.getInstance();
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
                else{
                    firebaseAuth.sendPasswordResetEmail(emailforpass_string).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ForgotPassActivity.this, "The link has been sent to email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgotPassActivity.this, "Error! The link has not been sent "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}