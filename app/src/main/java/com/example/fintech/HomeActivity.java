package com.example.fintech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private String currentUsername;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get username from intent
        currentUsername = getIntent().getStringExtra("USERNAME");
        if (currentUsername == null) {
            currentUsername = "test1@fintrack.com"; // fallback
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent = null;

            if (itemId == R.id.nav_home) {
                intent = new Intent(this, HomeActivity.class);
            } else if (itemId == R.id.nav_analysis) {
                intent = new Intent(this, AnalysisActivity.class);
            } else if (itemId == R.id.nav_budget) {
                intent = new Intent(this, BudgetActivity.class);
            } else if (itemId == R.id.nav_accounts) {
                intent = new Intent(this, AccountsActivity.class);
            }

            if (intent != null) {
                intent.putExtra("USERNAME", currentUsername);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}