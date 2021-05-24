package com.example.ridesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RidesAdapter adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Rides> options =
                new FirebaseRecyclerOptions.Builder<Rides>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("rides"), Rides.class)
                        .build();

        adapter = new RidesAdapter(options);
        recyclerView.setAdapter(adapter);
        fb=(FloatingActionButton)findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddRides.class));
            }
        });



    }



    @Override
        protected void onStart() {
            super.onStart();
            adapter.startListening();
        }
        @Override
        protected void onStop() {
            super.onStop();
            adapter.stopListening();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item= menu.findItem(R.id.searchfrom);

        SearchView searchView =(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                funsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                funsearch(s);
                return false;
            }
        });
        searchView.setQueryHint("Destination");
        return super.onCreateOptionsMenu(menu);
    }

    private void funsearch(String s)
    {
        FirebaseRecyclerOptions<Rides> options =
                new FirebaseRecyclerOptions.Builder<Rides>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("rides").orderByChild("to").startAt(s).endAt(s+"\uf8ff"), Rides.class)
                        .build();


        adapter=new RidesAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
