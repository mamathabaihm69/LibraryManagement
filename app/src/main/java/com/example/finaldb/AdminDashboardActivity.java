package com.example.finaldb;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddBook;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Connect to Firebase Books node
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        recyclerView = findViewById(R.id.recycler_inventory);
        fabAddBook = findViewById(R.id.fab_add_book);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Click to open the 'Add Book' screen (Create part of CRUD)
        fabAddBook.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AddBookActivity.class));
        });
    }
}