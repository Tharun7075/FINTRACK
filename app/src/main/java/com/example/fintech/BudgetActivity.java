package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class BudgetActivity extends AppCompatActivity {

    // Simple holder for budget data
    private static class BudgetData {
        final String label;   // e.g., "🍔 Food"
        final int spent;      // e.g., 20
        final int limit;      // e.g., 500
        BudgetData(String label, int spent, int limit) {
            this.label = label;
            this.spent = spent;
            this.limit = limit;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ---- Bottom Navigation ----
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_budget); // highlight current tab

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                finish(); // avoid stacking
                return true;
            } else if (itemId == R.id.nav_analysis) {
                startActivity(new Intent(this, AnalysisActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_budget) {
                // Already on Budget; do nothing
                return true;
            } else if (itemId == R.id.nav_accounts) {
                startActivity(new Intent(this, AccountsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        // ---- Populate stub cards ----
        // Map each include's ID to its data
        Map<Integer, BudgetData> budgets = new HashMap<>();
        budgets.put(R.id.foodCard,      new BudgetData("🍔 Food",      20,  500));
        budgets.put(R.id.transportCard, new BudgetData("🚗 Transport", 100, 1000));
        budgets.put(R.id.groceryCard,   new BudgetData("🛒 Groceries", 80,  300));
        budgets.put(R.id.billsCard,     new BudgetData("📄 Bills",     100, 700));

        for (Map.Entry<Integer, BudgetData> entry : budgets.entrySet()) {
            View card = findViewById(entry.getKey());
            if (card == null) continue;

            TextView label = card.findViewById(R.id.categoryLabel);
            ProgressBar bar = card.findViewById(R.id.categoryProgress);

            BudgetData data = entry.getValue();
            if (label != null) {
                label.setText(data.label + ": " + data.spent + " / " + data.limit);
            }
            if (bar != null) {
                bar.setMax(data.limit);
                bar.setProgress(data.spent);
            }
        }
    }
}