package com.example.contactlessapp;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.EmailDBClass;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_shop);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuth.signOut();


        shopName = findViewById(R.id.editTextShopName);
        shopLocation = findViewById(R.id.editTextShopLocation);
        shopUsername = findViewById(R.id.editTextShopUsername);
        shopEmail = findViewById(R.id.editTextShopEmail);
        shopPassword = findViewById(R.id.editTextShopPassword);
        shopConfirmPassword = findViewById(R.id.editTextShopConfirmPassword);
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
        if(password.length()>=5){
            return  true;
        }else {
            CustomerPass.setError("must be aleast 6 characters");
            return  false;
        }
    }
    private boolean checkConfirmPasswordChar(EditText CustomerConfirmPassword){
        String password = CustomerConfirmPassword.getText().toString();
        if(password.length()>=5){
            return  true;
        }else {
            CustomerConfirmPassword.setError("must be aleast 6 characters");
            return  false;
        }
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
        }validateEmailAddress(shopEmail);
        if(TextUtils.isEmpty(password)){
            shopPassword.setError("field cannot be empty.");
            return;
        }checkPasswordChar(shopPassword);
        if(TextUtils.isEmpty(confirmpass)){
            shopConfirmPassword.setError("field cannot be empty.");
            return;
        }checkConfirmPasswordChar(shopConfirmPassword);
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
                                progressDialog.setMessage("Registering User..");
                                progressDialog.show();

                                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference shopRef = database.getReference("Registered_Users");
                                            CreateAccountShopHelperClass helperClass = new CreateAccountShopHelperClass(accountType, name, location, username, email, password);
                                            shopRef.child(username).setValue(helperClass);

                                            Toast.makeText(CreateAccountShopActivity.this, "Account successfully created!",Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(CreateAccountShopActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(CreateAccountShopActivity.this, "could not register! please try again",
                                                    Toast.LENGTH_SHORT).show();
//
//                                            Intent intent = getIntent();
//                                            finish();
//                                            startActivity(intent);
                                        }
                                        progressDialog.dismiss();
                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(CreateAccountShopActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

        }

    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}