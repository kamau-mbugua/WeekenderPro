package com.example.weekenderpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

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
    }
}