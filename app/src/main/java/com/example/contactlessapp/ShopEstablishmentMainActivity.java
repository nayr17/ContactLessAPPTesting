package com.example.contactlessapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ShopEstablishmentMainActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_establishment_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();

            }
            else {
              updateText(result.getContents());
            }

        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    public void startScan(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
//        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();

    }

    public void btnScan(View view) {
        startScan();


    }


    private void updateText(String scanCode){
        textView1.setText(scanCode);
    }

    //    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//
//        if(result!=null)
//        {
//            if(result.getContents() == null)
//            {
//                Toast.makeText(getApplicationContext(), "No result found", Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                textView1.setText(result.getFormatName());
//                textView2.setText(result.getContents());
//
//            }
//        }
//        else
//        {
//            super.onActivityResult(requestCode,resultCode,data);
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Toast.makeText(this, " " + requestCode,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, " " + resultCode,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, " " + data,Toast.LENGTH_SHORT).show();
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//      IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);
//        if (scanResult != null) {
//            String re = scanResult.getContents();
//            Toast.makeText(this," " + re, Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            super.onActivityResult(requestCode, resultCode, data);
//        }

//
////        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
////        if (result != null) {
////            String getdata = result.getContents();
////            Toast.makeText(getApplicationContext(), "" + getdata, Toast.LENGTH_LONG).show();
////            super.onActivityResult(requestCode, resultCode, data);
////
////        }
//    }



}