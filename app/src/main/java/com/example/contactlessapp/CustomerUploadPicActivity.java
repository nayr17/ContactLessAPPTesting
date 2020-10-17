package com.example.contactlessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CustomerUploadPicActivity extends AppCompatActivity {
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    private static  final int chooseImage_Req = 000;
    private Uri selectedFile; //Uri Object
    private ImageView selectedImage;
    private String username;
    private String imageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_upload_pic);
        storageReference = FirebaseStorage.getInstance().getReference("Registered_Users");
         selectedImage = findViewById(R.id.selectedImage);

         Intent intent = getIntent();
         username = intent.getStringExtra("getUserRef");
        Toast.makeText(this, ""+ username, Toast.LENGTH_LONG).show();


    }
    private void uploadFile(){
        if(selectedFile != null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference profilePicUpload = storageReference.child(username +"/profile");
            profilePicUpload.putFile(selectedFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child(username +"/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageRef = uri.toString().trim();
                            DatabaseReference addPhotoRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + username);
                            addPhotoRef.child("profilePhotoURL").setValue(imageRef);
                            Toast.makeText(CustomerUploadPicActivity.this, "File Uploaded ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CustomerUploadPicActivity.this, CustomerMainActivity.class);
                            startActivity(intent);

                        }
                    });

//                    Task<Uri> downloadURL = profilePicUpload.getDownloadUrl();
//                    downloadURL.addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            imageRef = task.toString();
//                            Toast.makeText(CustomerUploadPicActivity.this , "url :" + imageRef, Toast.LENGTH_SHORT).show();
//                            DatabaseReference addPhotoRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + username);
//                            addPhotoRef.child("profilePhotoURL").setValue(imageRef);
//                        }
//
                    progressDialog.dismiss();
//                    Toast.makeText(CustomerUploadPicActivity.this, "File Uploaded ", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CustomerUploadPicActivity.this, CustomerMainActivity.class);
//                    startActivity(intent);
//                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerUploadPicActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    progressDialog.setMessage((int)progress + "% Uploaded...");
                }
            })
            ;
        }
//        final DatabaseReference addPhotoRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + username) ;
//        addPhotoRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    Toast.makeText(CustomerUploadPicActivity.this , "Exist!!!", Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(CustomerUploadPicActivity.this , " :" + addPhotoRef, Toast.LENGTH_SHORT).show();
//
//                    addPhotoRef.child("profilePhotoURL").setValue(123456);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == chooseImage_Req && resultCode ==RESULT_OK && data != null && data.getData() != null){
            selectedFile = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void btnSelectImage(View view) {
        fileChooser();
    }

    public void btnUploadImage(View view) {
        uploadFile();

    }

    private  void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*"); // see images only
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, chooseImage_Req);

    }


}