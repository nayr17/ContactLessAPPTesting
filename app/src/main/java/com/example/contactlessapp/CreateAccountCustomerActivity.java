package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;

import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
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

import javax.xml.transform.Result;

public class CreateAccountCustomerActivity extends AppCompatActivity{
    private EditText CustomerName;
    private EditText CustomerAddress;
    private EditText CustomerPhoneNumber;
    private EditText CustomerBarangay;
    private EditText CustomerUsername;
    private EditText CustomerEmail;
    private EditText CustomerPass;
    private EditText CustomerConfirmPassword;

    //Customer helper class parameters
    String AccountType ="Customer";
    private String accountType;
    private String name;
    private String address;
    private String phoneNumber;
    private String barangay;
    private String username;
    private String email;
    private String password;
    private String confirmpass;

    private  FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


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

       accountType = AccountType;
       name = CustomerName.getText().toString().trim();
       address = CustomerAddress.getText().toString().trim();
       phoneNumber =CustomerPhoneNumber.getText().toString().trim();
       barangay = CustomerBarangay.getText().toString().trim();
       username = CustomerUsername.getText().toString().trim();
       email = CustomerEmail.getText().toString().trim();
       password = CustomerPass.getText().toString().trim();
       confirmpass = CustomerConfirmPassword.getText().toString().trim();

       if(TextUtils.isEmpty(name)){
           CustomerName.setError("field cannot be empty.");
           return;
       }
        if(TextUtils.isEmpty(address)){
            CustomerAddress.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            CustomerPhoneNumber.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(username)){
            CustomerUsername.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(email)){
            CustomerEmail.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            CustomerPass.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(confirmpass)){
            CustomerConfirmPassword.setError("field cannot be empty.");
            return;
        }
        if(!password.equals(confirmpass)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }
        if(password.equals(confirmpass) && !password.equals(null) && !confirmpass.equals(null)){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
            userRef.orderByChild("username")
                    .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        CustomerUsername.setError("pick another username.");
                        Toast.makeText(CreateAccountCustomerActivity.this,"Username already exist.",Toast.LENGTH_SHORT).show();

                    }else{

//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myref = database.getReference("Registered_Users");
//                        CreateAccountCustomerHelperClass helperClass = new CreateAccountCustomerHelperClass(accountType, name, address, phoneNumber, barangay, username, email, password);
//                        myref.child(username).setValue(helperClass);

                        firebaseAuth.createUserWithEmailAndPassword(email,password);

                        Toast.makeText(CreateAccountCustomerActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

//                       Intent intent = new Intent(CreateAccountCustomerActivity.this, MainActivity.class);
//                       startActivity(intent);
//
//                       finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CreateAccountCustomerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


        }

//


        /*
       if(CustomerName.length() ==0 || CustomerName.equals("")){
           CustomerName.setError("field cannot be empty.");
       }
       if(CustomerAddress.length()==0 || CustomerAddress.equals("")){
           CustomerAddress.setError("field cannot be empty.");
       }
       if(CustomerPhoneNumber.length()==0 || CustomerPhoneNumber.equals("")){
           CustomerPhoneNumber.setError("field cannot be empty.");
       }
       if(CustomerBarangay.length()==0 || CustomerBarangay.equals("")){
           CustomerBarangay.setError("field cannot be empty.");
       }
       if(CustomerUsername.length()==0 || CustomerUsername.equals("")){
           CustomerUsername.setError("field cannot be empty.");
       }
       if(CustomerEmail.length()==0 || CustomerEmail.equals("")){
           CustomerEmail.setError("field cannot be empty.");
       }
       if(CustomerPass.length()==0 || CustomerPass.equals("")){
           CustomerPass.setError("field cannot be empty.");
       }
       if(CustomerConfirmPassword.length()==0 || CustomerConfirmPassword.equals("")){
           CustomerConfirmPassword.setError("field cannot be empty.");
       }
       if(CustomerPass != CustomerConfirmPassword) {
            if (CustomerPass.length() != 0) {
                if(CustomerConfirmPassword.length() != 0) {
                    Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        }

         */


//       if(password.equals(confirmpass)){
//           if(password != null){
//               if(password != null){
//
//                       CreateAccountCustomerHelperClass helperClass = new CreateAccountCustomerHelperClass(accountType, name, address, phoneNumber, barangay, username, email, password);
//                       myref.child(username).setValue(helperClass);
//
//                       Intent intent = new Intent(this, MainActivity.class);
//                       startActivity(intent);
//                       Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();
//
//               }
//           }
//
//       }



    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}