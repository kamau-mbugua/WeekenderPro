 package com.example.weekenderpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

 public class MainActivity extends AppCompatActivity {
     ExtendedFloatingActionButton extended_fab;
     Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extended_fab= findViewById(R.id.extended_fab);
        btnSearch = findViewById(R.id.btnSearch);
    }
}