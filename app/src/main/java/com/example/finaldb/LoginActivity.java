package com.example.finaldb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginBtn;
    private TextView goToRegister, forgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.btn_login);
        goToRegister = findViewById(R.id.tv_go_to_register); // Matches your XML ID
        forgotPassword = findViewById(R.id.tv_forgot_password); // Added link

        // 1. Navigation to Register Page
        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // 2. Forgot Password Logic
        forgotPassword.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email to reset password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Reset link sent to email", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // 3. Login Logic (Admin + Student)
        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkAdminCredentials(email, pass)) {
                recordAdminLogin(email);
                Toast.makeText(this, "Admin Access Granted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminDashboardActivity.class));
                finish();
            } else {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean checkAdminCredentials(String email, String pass) {
        if (email.equals("mamathabaihm69@gmail.com") && pass.equals("Mamatha@123")) return true;
        if (email.equals("admin1.library@bms.edu") && pass.equals("AdminOne@2026")) return true;
        return false;
    }

    private void recordAdminLogin(String email) {
        String cleanEmail = email.replace(".", "_").replace("@", "_");
        DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("Admins").child(cleanEmail);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Map<String, Object> adminData = new HashMap<>();
        adminData.put("email", email);
        adminData.put("role", "Admin");
        adminData.put("last_login_time", currentTime);
        adminRef.setValue(adminData);
    }
}