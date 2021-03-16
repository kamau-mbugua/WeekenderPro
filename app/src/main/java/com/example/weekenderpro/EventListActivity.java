package com.example.weekenderpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private ValueEventListener mDBListener;
    List<Events> mEvents;
    List<Events> filteredmEvents;
    LinearLayoutManager layoutManager;
    EditText searchText;
    ProgressBar circleP_bar;
    TextView defaultView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.recyclerVw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        defaultView = findViewById(R.id.defaultView);

        searchText = findViewById(R.id.editTextSearch);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {

//                    productSearch(s.toString());
                    settAdapter(editable.toString());
                } else {
                    mAdapter = new RecyclerAdapter(EventListActivity.this, mEvents);
                    recyclerView.setAdapter(mAdapter);
                }

            }
        });

        circleP_bar = findViewById(R.id.progressBarCircle);
        //        Obtaining reference to the firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference("hotelProducts");

        storage = FirebaseStorage.getInstance();

        mEvents = new ArrayList<>();
        filteredmEvents = new ArrayList<>();

        //       Initializing and Setting up of layout Manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        Initializing and Setting up of adapter
        mAdapter  = new RecyclerAdapter(EventListActivity.this, mEvents);
        recyclerView.setAdapter(mAdapter);

        if (isNetworkConnected()) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mEvents.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Events model = postSnapshot.getValue(Events.class);
//                        model.setID(postSnapshot.getKey());

//                        if (Integer.valueOf(receivedProduct.getCapacity()) > 0) {

                        mEvents.add(model);
//                        } else {
//                            StorageReference storeRef = storage.getReferenceFromUrl(receivedProduct.getImage());

//                            storeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    mDatabase.child(receivedProduct.getID()).removeValue();
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(Products_view.this, "Error in Deletion", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
                    }

                    if (mEvents.isEmpty()) {
                        defaultView.setVisibility(View.VISIBLE);
                        circleP_bar.setVisibility(View.INVISIBLE);
                    }

                    mAdapter.notifyDataSetChanged();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(EventListActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EventListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            circleP_bar.setVisibility(View.INVISIBLE);
            defaultView.setVisibility(View.VISIBLE);
            defaultView.setText(R.string.No_network);
            Toast.makeText(this, " Check out", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void settAdapter(String toString) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                filteredmEvents.clear();
                recyclerView.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String hotelLocation = snapshot.child("hotelLocation").getValue(String.class);
                    String hotelName = snapshot.child("hotelName").getValue(String.class);
                    String tagList = snapshot.child("hotelListTag").getValue(String.class);

//                    Products filteredProduct = snapshot.getValue(Products.class);
                    Events modelFiltered = snapshot.getValue(Events.class);

                    if (hotelLocation.equals(null)) {
                        Toast.makeText(EventListActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                    } else {
                        if (hotelLocation.toLowerCase().contains(toString.toLowerCase())) {
//                            nameList.add(pName);
                            filteredmEvents.add(modelFiltered);
                        }

                        if (hotelName.equals(null)) {
                            Toast.makeText(EventListActivity.this, "Hotel Name is Null", Toast.LENGTH_SHORT).show();
                        } else {
                            if (hotelName.toLowerCase().contains(toString.toLowerCase())) {
                                filteredmEvents.add(modelFiltered);
                            }

                            if (tagList.equals(null)) {
                                Toast.makeText(EventListActivity.this, "Tag List is null", Toast.LENGTH_SHORT).show();
                            } else {
                                if (tagList.toLowerCase().contains(toString.toLowerCase())) {
                                    filteredmEvents.add(modelFiltered);
                                }
                            }
                        }
                    }
                }

                if (!filteredmEvents.isEmpty()) {
                    defaultView.setVisibility(View.INVISIBLE);
                    mAdapter = new RecyclerAdapter(EventListActivity.this, filteredmEvents);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    defaultView.setVisibility(View.VISIBLE);
                    defaultView.setText("No Products based on Your Search Criteria... Try Again");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}