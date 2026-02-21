package com.example.finaldb;

public class User {
    public String name, email, uucms, department, role;

    public User() {} // Required for Firebase

    public User(String name, String email, String uucms, String department, String role) {
        this.name = name;
        this.email = email;
        this.uucms = uucms; // Fixed typo
        this.department = department;
        this.role = role;
    }
}