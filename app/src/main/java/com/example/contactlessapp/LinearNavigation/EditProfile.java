package com.example.contactlessapp.LinearNavigation;

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

import com.example.contactlessapp.CustomerMainActivity;
import com.example.contactlessapp.CustomerUploadPicActivity;
import com.example.contactlessapp.R;
import com.example.contactlessapp.ShopEstablishmentMainActivity;
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

public class EditProfile extends AppCompatActivity {
//done

    Button upload;

    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    private static  final int chooseImage_Req = 000;
    private Uri selectedFile; //Uri Object
    private CircleImageView selectedPhoto;
    private String imageRef;
    private String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        selectedPhoto = findViewById(R.id.shopSelectedPhoto);
        upload = findViewById(R.id.btnUploadphoto);
        upload.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        Username = i.getStringExtra("Username");

        storageReference = FirebaseStorage.getInstance().getReference("Registered_Users");
    }

    private void uploadFile()
    {
        if (selectedFile != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference profilePicUpload = storageReference.child(Username + "/profile");
            profilePicUpload.putFile(selectedFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot)
                {
                    storageReference.child(Username + "/profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            imageRef = uri.toString().trim();
                            DatabaseReference addPhotoRef = FirebaseDatabase.getInstance().getReference("Registered_Users/" + Username);
                            addPhotoRef.child("profilePhotoURL").setValue(imageRef);
                            Toast.makeText(EditProfile.this, "File Uploaded ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfile.this, ShopEstablishmentMainActivity.class);
                            startActivity(intent);

                        }
                    });

                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressDialog.setMessage((int) progress + "% Uploaded...");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == chooseImage_Req && resultCode ==RESULT_OK && data != null && data.getData() != null)
        {
            selectedFile = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedFile);
                selectedPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    public void btnselectphoto(View view)
    {
        fileChooser();
        upload.setVisibility(View.VISIBLE);
    }

    private  void fileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*"); // see images only
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, chooseImage_Req);

    }

    public void btnshopUpload(View view)
    {
        uploadFile();
    }
}