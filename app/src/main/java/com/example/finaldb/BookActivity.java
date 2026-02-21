package com.example.finaldb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class BookActivity extends AppCompatActivity {

    private Button btnBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        btnBorrow = findViewById(R.id.btn_borrow);

        btnBorrow.setOnClickListener(v -> {
            String uniqueCode = generateBorrowCode();
            showBorrowDialog(uniqueCode);
        });
    }

    // Generates the unique 6-digit alphanumeric code
    private String generateBorrowCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 6) {
            int index = (int) (rnd.nextFloat() * chars.length());
            code.append(chars.charAt(index));
        }
        return "LIB-" + code.toString();
    }

    private void showBorrowDialog(String code) {
        new AlertDialog.Builder(this)
                .setTitle("Borrow Request Generated")
                .setMessage("Show this code to Admin (Mamatha):\n\n" + code)
                .setPositiveButton("I UNDERSTAND", (dialog, which) -> {
                    Toast.makeText(this, "Request sent to Admin", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}