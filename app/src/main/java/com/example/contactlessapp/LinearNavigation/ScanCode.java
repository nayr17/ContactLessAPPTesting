package com.example.contactlessapp.LinearNavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.CaptureActivity;
import com.example.contactlessapp.DbHelpers.Adapter;
import com.example.contactlessapp.DbHelpers.GetCustomerInfo;
import com.example.contactlessapp.DbHelpers.Model;
import com.example.contactlessapp.R;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScanCode extends AppCompatActivity
{

    RecyclerView recyclerView;
    Adapter adapter;
    TextView customerID;
    String Username;
    String QR_ID_edited;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        firebaseAuth = FirebaseAuth.getInstance();
        customerID = findViewById(R.id.customerID);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");


        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Scanned Customer").child(Username),Model.class)
                .build();

        adapter = new Adapter(options);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


    public void btnScanCode(View view)
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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getRef = firebaseDatabase.getReference("Registered_Users/" + QR_ID_edited );
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                if(snapshot.exists()){
                    String address = snapshot.child("address").getValue().toString().trim();
                    String barangay = snapshot.child("barangay").getValue().toString().trim();
                    String email = snapshot.child("emailAddress").getValue().toString().trim();
                    String name = snapshot.child("name").getValue().toString().trim();
                    String phoneNumber = snapshot.child("phoneNumber").getValue().toString().trim();
                    String username = snapshot.child("username").getValue().toString().trim();
                    String photoUrl = snapshot.child("profilePhotoURL").getValue().toString().trim();


                    DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Scanned Customer/" + Username);
                    GetCustomerInfo helper = new GetCustomerInfo( address, barangay, email, name, phoneNumber, username, date, currentTime, photoUrl);
                    uploadRef.child(name).setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task)
                        {
                            if(task.isComplete())
                            {
                                Toast.makeText(ScanCode.this,"Success!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                    {
                    Toast.makeText(ScanCode.this,"customer has not been registered", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(DatabaseError error)
            {

            }
        });


    }


}