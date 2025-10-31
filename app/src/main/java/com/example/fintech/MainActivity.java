package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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

        // "Sign up" navigation
        signUpPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Login click
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
                return; // ⟵ prevents navigation
            }

            // TODO: Replace with your real authentication
            // If validation passes, navigate to Home
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}