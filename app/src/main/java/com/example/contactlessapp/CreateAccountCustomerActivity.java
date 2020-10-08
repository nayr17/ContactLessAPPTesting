package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.contactlessapp.DbHelpers.CreateAccountCustomerHelperClass;

import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.CreateAccountShopHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountCustomerActivity extends AppCompatActivity {
    EditText CustomerName;
    EditText CustomerAddress;
    EditText CustomerPhoneNumber;
    EditText CustomerBarangay;
    EditText CustomerUsername;
    EditText CustomerEmail;
    EditText CustomerPass;
    EditText CustomerConfirmPassword;


    String AccountType ="Customer";
    Integer id;
    Button btncreate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Registered_Users");


       String accountType = AccountType;
       String name = CustomerName.getText().toString().trim();
       String address = CustomerAddress.getText().toString().trim();
       String phoneNumber =CustomerPhoneNumber.getText().toString().trim();
       String barangay = CustomerBarangay.getText().toString().trim();
       String username = CustomerUsername.getText().toString().trim();
       String email = CustomerEmail.getText().toString().trim();
       String password = CustomerPass.getText().toString().trim();
       String confirmpass = CustomerConfirmPassword.getText().toString().trim();


       if(CustomerName.length() ==0){
           CustomerName.setError("field cannot be empty.");
       }
       if(CustomerAddress.length()==0){
           CustomerAddress.setError("field cannot be empty.");
       }
       if(CustomerPhoneNumber.length()==0){
           CustomerPhoneNumber.setError("field cannot be empty.");
       }
       if(CustomerBarangay.length()==0){
           CustomerBarangay.setError("field cannot be empty.");
       }
       if(CustomerUsername.length()==0){
           CustomerUsername.setError("field cannot be empty.");
       }
       if(CustomerEmail.length()==0){
           CustomerEmail.setError("field cannot be empty.");
       }
       if(CustomerPass.length()==0){
           CustomerPass.setError("field cannot be empty.");
       }
       if(CustomerConfirmPassword.length()==0){
           CustomerConfirmPassword.setError("field cannot be empty.");
       }
       else if (password != confirmpass) {
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }



       if(password.equals(confirmpass)){
           if(password != null){
               if(password != null){

                   DatabaseReference checkUser = FirebaseDatabase.getInstance().getReference("Registered_Users");
                   checkUser.orderByChild("username").equalTo(username);
                   if(checkUser != null){
                       Toast.makeText(CreateAccountCustomerActivity.this, "already exist!", Toast.LENGTH_LONG).show();
                   }


                   /*.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.getValue() != null){
                               Toast.makeText(CreateAccountCustomerActivity.this, "already exist!", Toast.LENGTH_LONG).show();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                    */


                      Toast.makeText(this, "wow!", Toast.LENGTH_LONG).show();

                   Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
                    /*
                   CreateAccountCustomerHelperClass helperClass = new CreateAccountCustomerHelperClass(accountType, name, address, phoneNumber, barangay, username, email, password);
                   myref.child(username).setValue(helperClass);

                   Intent intent = new Intent(this, MainActivity.class);
                   startActivity(intent);
                   Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                     */

               }
           }

       }





    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}