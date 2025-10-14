package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    // Declare views
    TextView signupTitle;
    EditText nameField, emailField, ageField, createPasswordField, reenterPasswordField;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        signupTitle = findViewById(R.id.signupTitle);
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        ageField = findViewById(R.id.ageField);
        createPasswordField = findViewById(R.id.createPasswordField);
        reenterPasswordField = findViewById(R.id.reenterPasswordField);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Handle Create Account button click
        createAccountButton.setOnClickListener(v -> {
            // You can add validation here if needed

            // Navigate back to MainActivity (Login Page)
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: closes SignupActivity so user can't return with back button
        });
    }
}