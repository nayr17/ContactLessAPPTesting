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

import com.example.contactlessapp.DbHelpers.Adapter;
import com.example.contactlessapp.DbHelpers.GetCustomerInfo;
import com.example.contactlessapp.DbHelpers.Model;
import com.example.contactlessapp.LinearNavigation.EditProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShopEstablishmentMainActivity extends AppCompatActivity {

    String QR_ID_edited;
    String Username;
    String label = "Customer No.";
    int number = 0;

    CircleImageView showPhoto;
    TextView shopName;
    TextView shopAddress;
    TextView shopUsername;
    TextView shopEmail;

//    RecyclerView recyclerView;
//    Adapter adapter;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_establishment_main);
        showPhoto = findViewById(R.id.shopPic);
        firebaseAuth = FirebaseAuth.getInstance();


        shopName = findViewById(R.id.shopName);
        shopAddress = findViewById(R.id.shopAddress);
        shopUsername = findViewById(R.id.shopUsername);
        shopEmail = findViewById(R.id.shopEmail);

        Intent intent = getIntent();
        Username = intent.getStringExtra("username_input");

        getData();





//        textView1 = findViewById(R.id.textView1);

//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("Scanned Customer").child(Username),Model.class)
//                .build();
//
//        adapter = new Adapter(options);
//        recyclerView.setAdapter(adapter);


    }
    public void getData(){
        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + Username);
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    shopName.setText("Shop Name: " + snapshot.child("name").getValue().toString());
                    shopAddress.setText("Shop Location: " + snapshot.child("location").getValue().toString());
                    shopUsername.setText(snapshot.child("username").getValue().toString());
                    shopEmail.setText("Shop Email: " + snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference getUrl = FirebaseDatabase.getInstance().getReference("Registered_Users/" + Username + "/profilePhotoURL");
        getUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result = snapshot.getValue(String.class);
                getUrl.setValue(result);
                Picasso.get()
                        .load(result)
                        .resize(150,150)
                        .into(showPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//        adapter.stopListening();
//    }
//
    public void btnScan(View view)
    {
        scanCode();
    }

    public void scanCode()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan Code");
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                String QR_ID = result.getContents();
//                textView1.setText("ID: " + QR_ID);
                // contactlessapp-
                StringBuilder stringBuilder = new StringBuilder(QR_ID);
                QR_ID_edited = stringBuilder.delete(0,15).toString();


                getRefScan();
            }
            else
                {
                Toast.makeText(this,"No Result found", Toast.LENGTH_SHORT).show();
                }
        }
        else
            {
            super.onActivityResult(requestCode, resultCode, data);
            }

    }

    public void getRefScan()
    {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        final String date = format.format(today);


        final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());


//        StringBuilder stringBuilder = new StringBuilder(label);
//        int display = stringBuilder.delete(0,12).to();


//        String childName = label + number;
//        Toast.makeText(ShopEstablishmentMainActivity.this,childName, Toast.LENGTH_SHORT).show();
//        textView1.setText("id"+childName);




//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference getRef = firebaseDatabase.getReference("Registered_Users/" + QR_ID_edited );
//        getRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if(snapshot.exists()){
//                    String address = snapshot.child("address").getValue().toString().trim();
//                    String barangay = snapshot.child("barangay").getValue().toString().trim();
//                    String email = snapshot.child("emailAddress").getValue().toString().trim();
//                    String name = snapshot.child("name").getValue().toString().trim();
//                    String phoneNumber = snapshot.child("phoneNumber").getValue().toString().trim();
//                    String username = snapshot.child("username").getValue().toString().trim();
//                    String photoUrl = snapshot.child("profilePhotoURL").getValue().toString().trim();
//
//
//                    DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Scanned Customer/" + Username);
//                    GetCustomerInfo helper = new GetCustomerInfo( address, barangay, email, name, phoneNumber, username, date, currentTime, photoUrl);
//                    uploadRef.child(name).setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task)
//                        {
//                            if(task.isComplete())
//                            {
//                                Toast.makeText(ShopEstablishmentMainActivity.this,"Success!!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }
//                else
//                    {
//                    Toast.makeText(ShopEstablishmentMainActivity.this,"customer has not been registered", Toast.LENGTH_SHORT).show();
//                    }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error)
//            {
//
//            }
//        });


    }


    public void btnUploadphoto(View view) {
        Intent i = new Intent(ShopEstablishmentMainActivity.this, EditProfile.class);
        i.putExtra("Username", Username);
        startActivity(i);

    }

    public void btnLogout(View view) {
        firebaseAuth.signOut();
        Intent i = new Intent(ShopEstablishmentMainActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}