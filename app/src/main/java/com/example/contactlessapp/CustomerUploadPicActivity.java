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
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerUploadPicActivity extends AppCompatActivity {
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    private static  final int chooseImage_Req = 000;
    private Uri selectedFile; //Uri Object
    private ImageView selectedImage;
    private String username;
    private String imageRef;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //done
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_upload_pic);
        storageReference = FirebaseStorage.getInstance().getReference("Registered_Users");
         selectedImage = findViewById(R.id.selectedImage1);

         btnUpdate = findViewById(R.id.btnCustomerActivityUploadImage);
         btnUpdate.setEnabled(false);
         btnUpdate.setVisibility(View.INVISIBLE);


         Intent intent = getIntent();
         username = intent.getStringExtra("getUserRef");


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

                    progressDialog.dismiss();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == chooseImage_Req && resultCode ==RESULT_OK && data != null && data.getData() != null){
            selectedFile = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                selectedImage.setImageBitmap(bitmap);
                btnUpdate.setEnabled(true);
                btnUpdate.setVisibility(View.VISIBLE);
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