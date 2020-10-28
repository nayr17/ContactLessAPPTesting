package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EventListener;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    EditText username_input;
    EditText password_input;

    private String username;
    private String password;
    private String Customer = "Customer";
    private String Shop= "Shop";
    private String Establishment = "Establishment";
    private String email;

    private ProgressDialog progressDialog;
    private  FirebaseAuth firebaseAuth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dbreference = db.collection("Customer Accounts").document("Customer Accounts");

    private static final String KEY_USERNAME = "Username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser()!= null){
//            firebaseAuth.signOut();
//        }

        username_input = findViewById(R.id.username_xml);
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

                //Toast.makeText(MainActivity.this,"you clicked forgot pass",Toast.LENGTH_LONG).show();
            }
        });

        createaccount_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateAccountActivity.class);
                startActivity(intent);

                //Toast.makeText(MainActivity.this,"you clicked create account",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username)) {
                    username_input.setError("field cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    password_input.setError("field cannot be empty.");
                    return;
                }
                else{
                    dbreference.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()){
                                        username = documentSnapshot.getString(KEY_USERNAME);
                                        Map<String, Object> retrieve = documentSnapshot.getData();
                                        if (username.)
                                        firebaseAuth.signInWithEmailAndPassword(email,)
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                username = username_input.getText().toString().trim();
                password = password_input.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email,password)
            }
        });
    }
}