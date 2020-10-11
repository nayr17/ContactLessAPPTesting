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
     EditText CustomerName;
     EditText CustomerAddress;
     EditText CustomerPhoneNumber;
     EditText CustomerBarangay;
     EditText CustomerUsername;
     EditText CustomerEmail;
     EditText CustomerPass;
     EditText CustomerConfirmPassword;

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

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            firebaseAuth.signOut();
        }

        CustomerName = findViewById(R.id.editTextCustomerName);
        CustomerAddress = findViewById(R.id.editTextCustomerAddress);
        CustomerPhoneNumber = findViewById(R.id.editTextCustomerPhoneNO);
        CustomerBarangay = findViewById(R.id.editTextCustomerBarangay);
        CustomerUsername = findViewById(R.id.editTextCustomerUsername);
        CustomerEmail = findViewById(R.id.editTextCustomerEmail);
        CustomerPass = findViewById(R.id.editTextCustomerPassword);
        CustomerConfirmPassword = findViewById(R.id.editTextCustomerConfirmPassword);
        progressDialog = new ProgressDialog(this);


    }
    private boolean validateEmailAddress(EditText CustomerEmail){
        String email = CustomerEmail.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            CustomerEmail.setError("email is not valid");
            return  false;
        }
    }
    private boolean checkPasswordChar(EditText CustomerPass){
        String password = CustomerPass.getText().toString();
        if(password.length()>=6){
            return  true;
        }else {
            CustomerPass.setError("must be aleast 6 characters");
            return  false;
        }
    }
    private boolean checkConfirmPasswordChar(EditText CustomerConfirmPassword){
        String password = CustomerConfirmPassword.getText().toString();
        if(password.length()<=5){
            return  true;
        }else {
            CustomerConfirmPassword.setError("must be aleast 6 characters");
            return  false;
        }
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
        }validateEmailAddress(CustomerEmail);
        if(TextUtils.isEmpty(password)){
            CustomerPass.setError("field cannot be empty.");
            return;
        }checkPasswordChar(CustomerPass);
        if(TextUtils.isEmpty(confirmpass)){
            CustomerConfirmPassword.setError("field cannot be empty.");
            return;
        }checkConfirmPasswordChar(CustomerConfirmPassword);
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

                    }

                    else{
                        progressDialog.setMessage("Registering User..");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Registered_Users");
                                    CreateAccountCustomerHelperClass helperClass = new CreateAccountCustomerHelperClass(accountType, name, address, phoneNumber, barangay, username, email, password);
                                    myRef.child(username).setValue(helperClass);

                                    //for login
                                    DatabaseReference emailDB = database.getReference("Account_Type");
                                    EmailDBClass dbhelper = new EmailDBClass(accountType, email, password);
                                    emailDB.child(accountType).setValue(dbhelper);


                                    Toast.makeText(CreateAccountCustomerActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(CreateAccountCustomerActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    finish();

                                }
                                else{
                                    Toast.makeText(CreateAccountCustomerActivity.this, "could not register! please try again",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(CreateAccountCustomerActivity.this, CreateAccountCustomerActivity.class);
                                    startActivity(intent);
                                }
                                progressDialog.dismiss();

                            }
                        });

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