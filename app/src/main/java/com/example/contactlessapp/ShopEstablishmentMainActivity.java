package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.Blog;
import com.example.contactlessapp.DbHelpers.GetCustomerInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


public class ShopEstablishmentMainActivity extends AppCompatActivity {
    TextView textView1;
    ImageView getUserpic;
    TextView getID;
    TextView getDate;
    TextView getTime;
    TextView getName;
    TextView getAddress;
    TextView getPhonenumber;
    String QR_ID_edited;
    String Username;

//    private RecyclerView getData;
//    private DatabaseReference getRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_establishment_main);

//        textView1 = findViewById(R.id.textView1);
//        getUserpic = findViewById(R.id.imageViewCustomerPic);
//        getID = findViewById(R.id.textViewCustomerID);
//        getName = findViewById(R.id.textViewGetCustomerName);
//        getAddress = findViewById(R.id.textViewGetCustomerAddress);
//        getPhonenumber = findViewById(R.id.textViewGetCustomerPhoneNumber);
//        getDate = findViewById(R.id.textViewDate);
//        getTime = findViewById(R.id.textViewTime);

        Intent intent = getIntent();
        Username = intent.getStringExtra("username_input");

//        getRef = FirebaseDatabase.getInstance().getReference("Scanned Customer/" + Username);
//        getRef.keepSynced(true);
//
//        getData = findViewById(R.id.myRecycleView);
//        getData.setHasFixedSize(true);
//        getData.setLayoutManager(new LinearLayoutManager(this));



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerAdapter<Blog,viewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, viewHolder>
//                (Blog.class, R.layout.format, viewHolder.class, getRef) {
//            @Override
//            protected void populateViewHolder(viewHolder viewHolder, Blog blog, int i) {
//                viewHolder.setName(blog.getName());
//                viewHolder.setAddress(blog.getAddress());
//                viewHolder.setPhoneNumber(blog.getPhoneNumber());
//                viewHolder.setDate(blog.getDate());
//                viewHolder.setTime(blog.getTime());
//                viewHolder.setQR_ID(blog.getQR_ID());
//
//            }
//        };
//        getData.setAdapter(firebaseRecyclerAdapter);
//    }
//
//    public static class viewHolder extends RecyclerView.ViewHolder{
//        View view;
//        public viewHolder(View list){
//            super(list);
//            view = list;
//        }
//        public void setName(String name){
//            TextView showName = view.findViewById(R.id.textViewGetCustomerName);
//            showName.setText(name);
//        }
//        public void setAddress(String address){
//            TextView showAddress = view.findViewById(R.id.textViewGetCustomerAddress);
//            showAddress.setText(address);
//        }
//        public void setPhoneNumber(String phoneNumber){
//            TextView showPhoneNumber = view.findViewById(R.id.textViewGetCustomerPhoneNumber);
//            showPhoneNumber.setText(phoneNumber);
//
//        }
//        public void setDate(String date){
//            TextView showDate = view.findViewById(R.id.textViewDate);
//            showDate.setText(date);
//        }
//        public void setTime(String time){
//            TextView showTime = view.findViewById(R.id.textViewTime);
//            showTime.setText(time);
//        }
//        public void setQR_ID(String QR_ID){
//            TextView showQR_ID = view.findViewById(R.id.textViewCustomerID);
//            showQR_ID.setText(QR_ID);
//        }
//    }

    public void btnScan(View view) {
        scanCode();
    }

    public void scanCode(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan Code");
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                String QR_ID = result.getContents();
                textView1.setText("ID: " + QR_ID);
                // contactlessapp-
                StringBuilder stringBuilder = new StringBuilder(QR_ID);
                QR_ID_edited = stringBuilder.delete(0,15).toString();
                getRefScan();
            }
            else {
                Toast.makeText(this,"No Result found", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void getRefScan(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        final String date = format.format(today);
//        getDate.setText(date);

        final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
//        getTime.setText(currentTime);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getRef = firebaseDatabase.getReference("Registered_Users/" + QR_ID_edited );
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String address = snapshot.child("address").getValue().toString().trim();
                    String barangay = snapshot.child("barangay").getValue().toString().trim();
                    String email = snapshot.child("emailAddress").getValue().toString().trim();
                    String name = snapshot.child("name").getValue().toString().trim();
                    String phoneNumber = snapshot.child("phoneNumber").getValue().toString().trim();
                    String username = snapshot.child("username").getValue().toString().trim();


                    DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Scanned Customer");
                    GetCustomerInfo helper = new GetCustomerInfo( address, barangay, email, name, phoneNumber, username, date, currentTime);
                    uploadRef.child(Username).child(name).setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(ShopEstablishmentMainActivity.this,"Success!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(ShopEstablishmentMainActivity.this,"customer has not been registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}