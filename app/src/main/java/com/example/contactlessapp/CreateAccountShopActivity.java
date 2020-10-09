package com.example.contactlessapp;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.AccountType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountShopActivity extends AppCompatActivity {
    private EditText shopName;
    private EditText shopLocation;
    private EditText shopUsername;
    private EditText shopEmail;
    private EditText shopPassword;
    private EditText shopConfirmPassword;

    String AccountType ="Shop";
    private String accountType;
    private String name;
    private String location;
    private String username;
    private String email;
    private String password;
    private String confirmpass;

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

         accountType = AccountType;
         name = shopName.getText().toString().trim();
         location = shopLocation.getText().toString().trim();
         username = shopUsername.getText().toString().trim();
         email = shopEmail.getText().toString().trim();
         password = shopPassword.getText().toString().trim();
         confirmpass = shopConfirmPassword.getText().toString().trim();

         if(TextUtils.isEmpty(name)){
             shopName.setError("field cannot be empty.");
             return;
         }
        if(TextUtils.isEmpty(location)){
            shopLocation.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(username)){
            shopUsername.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(email)){
            shopEmail.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            shopPassword.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(confirmpass)){
            shopConfirmPassword.setError("field cannot be empty.");
            return;
        }
        if(!password.equals(confirmpass)){
            Toast.makeText(this,"Password does not match", Toast.LENGTH_SHORT).show();
        }
        if(password.equals(confirmpass) && !password.equals(null) && !confirmpass.equals(null)){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
            userRef.orderByChild("username")
                    .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                shopUsername.setError("Pick another username.");
                                Toast.makeText(CreateAccountShopActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
                            }else{
                               FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                               DatabaseReference shopRef = database1.getReference("Registered_Users");
                               CreateAccountShopHelperClass helperClass = new CreateAccountShopHelperClass(accountType, name, location, username, email, password);
                               shopRef.child(username).setValue(helperClass);
                               Toast.makeText(CreateAccountShopActivity.this, "Account successfully created!",Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(CreateAccountShopActivity.this, MainActivity.class);
                               startActivity(intent);
                               finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(CreateAccountShopActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        }


//        if(shopName.length() ==0 || shopName.equals("")){
//            shopName.setError("field cannot be empty.");
//
//        }
//        if(shopLocation.length()==0){
//            shopLocation.setError("field cannot be empty.");
//        }
//       if(shopUsername.length()==0){
//            shopUsername.setError("field cannot be empty.");
//        }
//       if(shopEmail.length()==0){
//            shopEmail.setError("field cannot be empty.");
//        }
//        if(shopPassword.length()==0){
//            shopPassword.setError("field cannot be empty.");
//        }
//        if(shopConfirmPassword.length()==0){
//            shopConfirmPassword.setError("field cannot be empty.");
//        }
//        if(shopPassword != shopConfirmPassword){
//            if(shopPassword.length() != 0){
//                if(shopConfirmPassword.length() !=0){
//                    Toast.makeText(this,"Password does not match", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
//
//
//        if(password.equals(confirmPassword)){
//            if(password.length()!=0){
//                if(password.length()!=0){
//
//                        CreateAccountShopHelperClass helperClass = new CreateAccountShopHelperClass(accountType, name, location, username, email, password);
//                        myref.child(username).setValue(helperClass);
//
//                        Intent intent = new Intent(this, MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            }

    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}