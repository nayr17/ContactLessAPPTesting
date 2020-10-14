package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    EditText email_input;
    EditText password_input;

    private String email;
    private String password;
    private String Customer = "Customer";
    private String Shop = "Shop";
    private String Establishment = "Establishment";
    private  String getEmail;

    private ProgressDialog progressDialog;
    private  FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser()!= null){
//            firebaseAuth.signOut();
//        }

        email_input = findViewById(R.id.email_xml);
        password_input = findViewById(R.id.password_xml);
        login_button = findViewById(R.id.login_xml);
        TextView forgotpass_input = findViewById(R.id.forgotpass_xml);
        TextView createaccount_input = findViewById(R.id.createaccount_xml);
        progressDialog = new ProgressDialog(this);

        forgotpass_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ForgotPassActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"you clicked forgot pass",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        createaccount_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateAccountActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"you clicked create account",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser!=null){
//            //user logged in
//            startActivity(new Intent(this,));
//        }else{
//            //no one logged in
//            finish();
//        }
//    }

    public void btnLogin(View view) {
         email = email_input.getText().toString().trim();
         password = password_input.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            email_input.setError("enter email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            password_input.setError("enter email");
            return;
        }
        progressDialog.setMessage("Checking account...");
        progressDialog.show();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.isEmpty()&&password.isEmpty()){
                    email_input.setError("Enter Username");
                    password_input.setError("Enter Password");
                }
                else if(email.isEmpty()){
                    email_input.setError("Enter Username");
                }
                else if(password.isEmpty()){
                    password_input.setError("Enter Password");
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast toast = Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
    }
}