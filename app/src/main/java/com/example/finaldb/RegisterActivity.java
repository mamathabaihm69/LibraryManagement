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

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField, emailField, uucmsField, deptField, passwordField;
    private Button registerBtn;
    private TextView loginLink;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        nameField = findViewById(R.id.reg_name);
        emailField = findViewById(R.id.reg_email);
        uucmsField = findViewById(R.id.reg_uucms);
        deptField = findViewById(R.id.reg_dept);
        passwordField = findViewById(R.id.reg_password);
        registerBtn = findViewById(R.id.btn_register);
        loginLink = findViewById(R.id.tv_login_link);

        loginLink.setOnClickListener(v -> finish());

        registerBtn.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String uucms = uucmsField.getText().toString().trim();
            String dept = deptField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(uucms) || TextUtils.isEmpty(dept) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String uid = mAuth.getCurrentUser().getUid();
                    User student = new User(name, email, uucms, dept, "Student");
                    mDatabase.child(uid).setValue(student).addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    });
                } else {
                    Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}