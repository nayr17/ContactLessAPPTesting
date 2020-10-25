package com.example.contactlessapp.LinearNavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactlessapp.DbHelpers.Adapter;
import com.example.contactlessapp.DbHelpers.Model;
import com.example.contactlessapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RecentScan extends AppCompatActivity
{

    RecyclerView recyclerView;
    Adapter adapter;
    String Username;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_scan);

        firebaseAuth = FirebaseAuth.getInstance();
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

}