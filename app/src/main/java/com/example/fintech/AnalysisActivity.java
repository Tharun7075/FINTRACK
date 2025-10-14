package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_analysis); // Highlight Records icon

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_records) {
                startActivity(new Intent(this, RecordsActivity.class));
                return true;
            } else if (itemId == R.id.nav_analysis) {
                startActivity(new Intent(this, AnalysisActivity.class));
                return true;
            } else if (itemId == R.id.nav_budget) {
                startActivity(new Intent(this, BudgetActivity.class));
                return true;
            } else if (itemId == R.id.nav_accounts) {
                startActivity(new Intent(this, AccountsActivity.class));
                return true;
            }
            return false;
        });
    }
}