package com.example.contactlessapp;

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

import com.example.contactlessapp.DbHelpers.CreateAccountEstablishmentHelperClass;
import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;
import com.example.contactlessapp.DbHelpers.EmailDBClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountEstablishmentActivity extends AppCompatActivity {
    EditText establishmentName;
    EditText establishmentLocation;
    EditText establishmentUsername;
    EditText establishmentEmail;
    EditText establishmentPassword;
    EditText establishmentConfirmPassword;

    String AccountType ="Establishment";
    private String accountType;
    private String name;
    private String location;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_establishment);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();


        establishmentName = findViewById(R.id.editTextEstablishmentName);
        establishmentLocation = findViewById(R.id.editTextEstablishmentLocation);
        establishmentUsername = findViewById(R.id.editTextEstablishmentUsername);
        establishmentEmail = findViewById(R.id.editTextEstablishmentemail);
        establishmentPassword = findViewById(R.id.editTextEstablishmentPassword);
        establishmentConfirmPassword= findViewById(R.id.editTextEstablishmentConfirmPassword);
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
    public void btnEstablishmentCreateAccount(View view) {

        accountType = AccountType;
        name = establishmentName.getText().toString().trim();
        location = establishmentLocation.getText().toString().trim();
        username = establishmentUsername.getText().toString().trim();
        email = establishmentEmail.getText().toString().trim();
        password = establishmentPassword.getText().toString().trim();
        confirmPassword = establishmentConfirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            establishmentName.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(location)){
            establishmentLocation.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(username)){
            establishmentUsername.setError("field cannot be empty.");
            return;
        }
        if(TextUtils.isEmpty(email)){
            establishmentEmail.setError("field cannot be empty.");
            return;
        }validateEmailAddress(establishmentEmail);
        if(TextUtils.isEmpty(password)){
            establishmentPassword.setError("field cannot be empty.");
            return;
        }checkPasswordChar(establishmentPassword);
        if(TextUtils.isEmpty(confirmPassword)){
            establishmentConfirmPassword.setError("field cannot be empty.");
            return;
        }checkConfirmPasswordChar(establishmentConfirmPassword);
        if(!password.equals(confirmPassword)){
            Toast.makeText(this,"Password does not match", Toast.LENGTH_SHORT).show();
        }
        if(password.equals(confirmPassword) && !password.equals(null) && !confirmPassword.equals(null)){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
            userRef.orderByChild("username")
                    .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        establishmentUsername.setError("Pick another username.");
                        Toast.makeText(CreateAccountEstablishmentActivity.this, "Username already exist", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.setMessage("Registering User..");
                        progressDialog.show();
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference establishmentRef = database.getReference("Registered_Users");
                                    CreateAccountEstablishmentHelperClass helperClass = new CreateAccountEstablishmentHelperClass(accountType, name, location, username,email, password);
                                    establishmentRef.child(username).setValue(helperClass);

                                    Toast.makeText(CreateAccountEstablishmentActivity.this, "Account successfully created!",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(CreateAccountEstablishmentActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(CreateAccountEstablishmentActivity.this, "could not register! please try again",
                                            Toast.LENGTH_SHORT).show();
//                                    finish();
//                                    Intent intent = new Intent(CreateAccountEstablishmentActivity.this, CreateAccountEstablishmentActivity.class);
//                                    startActivity(intent);
                                }
                                progressDialog.dismiss();
                            }
                        });



                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CreateAccountEstablishmentActivity.this, "Error!", Toast.LENGTH_SHORT).show();
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