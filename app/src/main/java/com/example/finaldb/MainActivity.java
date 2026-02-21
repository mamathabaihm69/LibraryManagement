package com.example.finaldb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; // Add this import

public class MainActivity extends AppCompatActivity { // Add 'extends AppCompatActivity'
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
