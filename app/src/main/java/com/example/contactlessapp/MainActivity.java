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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    EditText email_input;
    EditText password_input;

    private String email;
    private String password;
    private String Customer = "Customer";
    private String Shop= "Shop";
    private String Establishment = "Establishment";

    private ProgressDialog progressDialog;
    private  FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void btnLogin(View view) {
         email = email_input.getText().toString().trim();
         password = password_input.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            email_input.setError("enter username");
            return;
        }
        if(TextUtils.isEmpty(password)){
            password_input.setError("enter password");
            return;
        }
//        progressDialog.setMessage("Logging in...");
//        progressDialog.show();

        DatabaseReference login = FirebaseDatabase.getInstance().getReference("Registered_Users");
        login.orderByChild("username").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.child("accountype").getValue().toString();
                if(snapshot.equals(Customer)){
                    Toast.makeText(MainActivity.this,"user match " + Customer, Toast.LENGTH_LONG).show();
                }
                if(snapshot.equals(Shop)){
                    Toast.makeText(MainActivity.this,"user match " + Shop, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//       DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
//       loginRef.orderByChild("username").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               if(snapshot.exists()){
//                   Toast.makeText(MainActivity.this,"user match", Toast.LENGTH_LONG).show();
//                   snapshot.child("AccountType").getValue().toString().trim();
//                   if(snapshot.equals(Customer)){
//                       Toast.makeText(MainActivity.this,"user match " + Customer, Toast.LENGTH_LONG).show();
//                   }
//                   if(snapshot.equals(Shop)){
//                       Toast.makeText(MainActivity.this,"user match " + Shop, Toast.LENGTH_LONG).show();
//                   }
//                   if(snapshot.equals(Establishment)){
//                       Toast.makeText(MainActivity.this,"user match " + Establishment, Toast.LENGTH_LONG).show();
//                   }
//               }
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });
//        progressDialog.dismiss();


//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered_Users");
//        userRef.orderByChild("username")
//                .equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    DatabaseReference passref = FirebaseDatabase.getInstance().getReference("Registered_Users").child(username);
//                    passref.child("password").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                Toast.makeText(MainActivity.this,"user match", Toast.LENGTH_LONG).show();
//                            }else{
//                                Toast.makeText(MainActivity.this,"user not found!", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
////                    DatabaseReference passRef = FirebaseDatabase.getInstance().getReference("Registered_Users" + username);
////                    passRef.orderByChild("username").equalTo(username).orderByChild("password").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            if(snapshot.exists()){
////                                Toast.makeText(MainActivity.this,"user match", Toast.LENGTH_LONG).show();
////
////                            }else{
////                                Toast.makeText(MainActivity.this,"user not found!", Toast.LENGTH_LONG).show();
////                            }
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError error) {
////
////                        }
////                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}