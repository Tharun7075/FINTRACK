package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Navigate to SignupActivity when "Sign up" is clicked
        TextView signUpPrompt = findViewById(R.id.signUpPrompt);
        signUpPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Navigate to HomeActivity when "Login" button is clicked
        Button loginButton = findViewById(R.id.loginButton); // Make sure this ID exists in activity_main.xml
        loginButton.setOnClickListener(v -> {
            // TODO: Add login validation logic here

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}