package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

            // Collect user inputs
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String age = ageField.getText().toString().trim();
            String password = createPasswordField.getText().toString().trim();
            String rePassword = reenterPasswordField.getText().toString().trim();

            // Validate fields
            boolean hasError = false;

            if (TextUtils.isEmpty(name)) {
                nameField.setError("Name is required");
                hasError = true;
            }

            if (TextUtils.isEmpty(email)) {
                emailField.setError("Email is required");
                hasError = true;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError("Enter a valid email address");
                hasError = true;
            }

            if (TextUtils.isEmpty(age)) {
                ageField.setError("Age is required");
                hasError = true;
            } else {
                try {
                    int ageVal = Integer.parseInt(age);
                    if (ageVal <= 0) {
                        ageField.setError("Enter a valid age");
                        hasError = true;
                    }
                } catch (NumberFormatException e) {
                    ageField.setError("Enter a numeric age");
                    hasError = true;
                }
            }

            if (TextUtils.isEmpty(password)) {
                createPasswordField.setError("Password is required");
                hasError = true;
            } else if (password.length() < 6) {
                createPasswordField.setError("Password must be at least 6 characters");
                hasError = true;
            }

            if (TextUtils.isEmpty(rePassword)) {
                reenterPasswordField.setError("Please re-enter password");
                hasError = true;
            } else if (!password.equals(rePassword)) {
                reenterPasswordField.setError("Passwords do not match");
                hasError = true;
            }

            // If any validation fails, stop here
            if (hasError) {
                Toast.makeText(SignupActivity.this, "Please fix the errors above", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: You can add code here to save user info to database or SharedPreferences

            Toast.makeText(SignupActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

            // Navigate back to login page
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // close signup page
        });
    }
}