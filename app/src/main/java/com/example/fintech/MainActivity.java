package com.example.fintech;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private DatabaseReference mDatabase;

    // Stub credentials
    private final String stubEmail1 = "test1@fintrack.com";
    private final String stubPassword1 = "123456";
    private final String stubEmail2 = "test2@fintrack.com";
    private final String stubPassword2 = "654321";
    private final String stubEmail3 = "test3@fintrack.com";
    private final String stubPassword3 = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inset handling (status/nav bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        TextView signUpPrompt = findViewById(R.id.signUpPrompt);
        Button loginButton = findViewById(R.id.loginButton);

        // Test Firebase - Write and read sample data
        testFirebaseConnection();

        // "Sign up" navigation
        signUpPrompt.setOnClickListener(v -> {
            // Write login attempt to Firebase
            writeLoginAttempt("signup_click", "User clicked sign up");

            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            boolean hasError = false;

            if (TextUtils.isEmpty(username)) {
                usernameField.setError("Username is required");
                hasError = true;
            } else {
                usernameField.setError(null);
            }

            if (TextUtils.isEmpty(password)) {
                passwordField.setError("Password is required");
                hasError = true;
            } else {
                passwordField.setError(null);
            }

            if (hasError) {
                Toast.makeText(MainActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                // Log failed attempt
                writeLoginAttempt("validation_failed", "Missing credentials");
                return;
            }

            // Write login attempt to Firebase
            writeLoginAttempt("login_attempt", "User: " + username);

            // Stub authentication check
            boolean isValid = (username.equals(stubEmail1) && password.equals(stubPassword1)) ||
                    (username.equals(stubEmail2) && password.equals(stubPassword2)) ||
                    (username.equals(stubEmail3) && password.equals(stubPassword3));

            if (isValid) {
                // Write successful login to Firebase
                writeLoginAttempt("login_success", "User: " + username);

                // You could also store user data
                storeUserLoginData(username);

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Write failed login to Firebase
                writeLoginAttempt("login_failed", "User: " + username);
                Toast.makeText(MainActivity.this, "Invalid credentials (stub check)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testFirebaseConnection() {
        // Write a test message
        DatabaseReference testRef = mDatabase.child("test_connection");
        testRef.setValue("Firebase connected at: " + System.currentTimeMillis())
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Test write successful"))
                .addOnFailureListener(e -> Log.e(TAG, "Test write failed", e));

        // Read the test message
        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Test read - Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Test read failed", error.toException());
            }
        });
    }

    private void writeLoginAttempt(String type, String details) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference loginRef = mDatabase.child("login_attempts").child(timestamp);

        Map<String, Object> loginData = new HashMap<>();
        loginData.put("type", type);
        loginData.put("details", details);
        loginData.put("timestamp", timestamp);

        loginRef.setValue(loginData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Login attempt logged: " + type))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to log login attempt", e));
    }

    private void storeUserLoginData(String username) {
        DatabaseReference userRef = mDatabase.child("users").child(username.replace(".", "_"));

        Map<String, Object> userData = new HashMap<>();
        userData.put("last_login", System.currentTimeMillis());
        userData.put("login_count", 1); // You'd increment this in a real app

        userRef.setValue(userData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User data stored for: " + username))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to store user data", e));
    }
}