package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactlessapp.DbHelpers.GetCustomerInfo;
import com.example.contactlessapp.LinearNavigation.EditProfile;
import com.example.contactlessapp.LinearNavigation.RecentScan;
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

import java.text.SimpleDateFormat;
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
                final String QR_ID = result.getContents();
//                textView1.setText("ID: " + QR_ID);
                // contactlessapp-
                StringBuilder stringBuilder = new StringBuilder(QR_ID);
                QR_ID_edited = stringBuilder.delete(0,15).toString();

                DatabaseReference checkRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + QR_ID_edited);
                checkRef.orderByChild("QR_Text_ID")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    getRefScan();
                                }
                                else
                                {
                                    Toast.makeText(ShopEstablishmentMainActivity.this,"customer has not been registered", Toast.LENGTH_SHORT).show();
                                    Intent i = getIntent();
                                    finish();
                                    startActivity(i);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
            else
            {
                Toast.makeText(ShopEstablishmentMainActivity.this,"No Result found", Toast.LENGTH_SHORT).show();
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void getRefScan()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getRef = firebaseDatabase.getReference("Registered_Users/" + QR_ID_edited );
        getRef.addValueEventListener(new ValueEventListener()
        {
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

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                    final String date = format.format(today);

                    final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

                    DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Scanned Customer/" + Username);
                    GetCustomerInfo helper = new GetCustomerInfo( address, barangay, email, name, phoneNumber, username, date, currentTime, photoUrl);
                    uploadRef.push().setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task)
                        {
                            if(task.isComplete())
                            {
                                Toast.makeText(ShopEstablishmentMainActivity.this, "successfully scanned customer", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(ShopEstablishmentMainActivity.this,"customer has not been registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error)
            {

            }
        });


    }


    public void btnUploadphoto(View view)
    {
        Intent i = new Intent(ShopEstablishmentMainActivity.this, EditProfile.class);
        i.putExtra("Username", Username);
        startActivity(i);

    }

    public void btnLogout(View view)
    {
        firebaseAuth.signOut();
        Intent i = new Intent(ShopEstablishmentMainActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void btnScanQR(View view)
    {
        scanCode();
    }

    public void ViewScan(View view) {
        Intent i = new Intent(ShopEstablishmentMainActivity.this, RecentScan.class);
        i.putExtra("Username", Username);
        startActivity(i);
    }
}