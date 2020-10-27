package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.example.contactlessapp.DbHelpers.EmailDBClass;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class CreateAccountCustomerActivity extends AppCompatActivity {
    EditText CustomerName;
    EditText CustomerAddress;
    EditText CustomerPhoneNumber;
    EditText CustomerBarangay;
    EditText CustomerUsername;
    EditText CustomerEmail;
    EditText CustomerPass;
    EditText CustomerConfirmPassword;
    Button CustomerNewAccountButton;

    //Customer helper class parameters
    String AccountType = "Customer";
    private String accountType;
    private static final String KEY_NAME = "Name";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_PHONENUMBER = "Phone Number";
    private static final String KEY_BARANGAY = "Name";
    private static final String KEY_USERNAME = "Username";
    private String name;
    private String address;
    private String phoneNumber;
    private String barangay;
    private String username;
    private String email;
    private String password;
    private String confirmpass;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);
        firebaseAuth = FirebaseAuth.getInstance();

        CustomerName = findViewById(R.id.editTextCustomerName);
        CustomerAddress = findViewById(R.id.editTextCustomerAddress);
        CustomerPhoneNumber = findViewById(R.id.editTextCustomerPhoneNO);
        CustomerBarangay = findViewById(R.id.editTextCustomerBarangay);
        CustomerUsername = findViewById(R.id.editTextCustomerUsername);
        CustomerEmail = findViewById(R.id.editTextCustomerEmail);
        CustomerPass = findViewById(R.id.editTextCustomerPassword);
        CustomerConfirmPassword = findViewById(R.id.editTextCustomerConfirmPassword);
        CustomerNewAccountButton = findViewById(R.id.btnCustomerCreate);

        progressBar = findViewById(R.id.progressbarxml);

//        if (firebaseAuth.getCurrentUser()!=null){
//
//        }

        CustomerNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = CustomerName.getText().toString().trim();
                address = CustomerAddress.getText().toString().trim();
                phoneNumber = CustomerPhoneNumber.getText().toString().trim();
                barangay = CustomerBarangay.getText().toString().trim();
                username = CustomerUsername.getText().toString().trim();
                email = CustomerEmail.getText().toString().trim();
                password = CustomerPass.getText().toString().trim();
                confirmpass = CustomerConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    CustomerName.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    CustomerAddress.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    CustomerPhoneNumber.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(barangay)) {
                    CustomerBarangay.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    CustomerName.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    CustomerUsername.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    CustomerEmail.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    CustomerPass.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(confirmpass)) {
                    CustomerConfirmPassword.setError("field cannot be empty.");
                    return;
                }
                if (password.length()<6) {
                    CustomerName.setError("Password must be 6 characters");
                    return;
                }

                // register using firebase

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);

                            Map<String,Object> test = new HashMap<>();
                            test.put(KEY_NAME,name);
                            test.put(KEY_ADDRESS,address);
                            test.put(KEY_PHONENUMBER,phoneNumber);
                            test.put(KEY_BARANGAY,barangay);
                            test.put(KEY_USERNAME,username);

                            db.collection("Customer Accounts").document().set(test)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CreateAccountCustomerActivity.this, "Data Saved", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CreateAccountCustomerActivity.this, "Error!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(CreateAccountCustomerActivity.this, "User Created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(CreateAccountCustomerActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}