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
                return;
            }

            // Stub authentication check
            boolean isValid = (username.equals(stubEmail1) && password.equals(stubPassword1)) ||
                    (username.equals(stubEmail2) && password.equals(stubPassword2)) ||
                    (username.equals(stubEmail3) && password.equals(stubPassword3));

            if (isValid) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials (stub check)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}