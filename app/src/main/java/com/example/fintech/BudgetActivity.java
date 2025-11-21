package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

    // Holder for initial dummy budget data
    private static class BudgetData {
        final String label;
        final int spent;
        final int limit;

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

        // ---------------- Setup Editable Cards ----------------
        Map<Integer, BudgetData> budgets = new HashMap<>();
        budgets.put(R.id.foodCard,      new BudgetData("🍔 Food", 20, 500));
        budgets.put(R.id.transportCard, new BudgetData("🚗 Transport", 100, 1000));
        budgets.put(R.id.groceryCard,   new BudgetData("🛒 Groceries", 80, 300));
        budgets.put(R.id.billsCard,     new BudgetData("📄 Bills", 100, 700));

        for (Map.Entry<Integer, BudgetData> entry : budgets.entrySet()) {
            View card = findViewById(entry.getKey());
            if (card == null) continue;

            TextView label = card.findViewById(R.id.categoryLabel);
            EditText spentInput = card.findViewById(R.id.spentInput);
            EditText limitInput = card.findViewById(R.id.limitInput);
            ProgressBar bar = card.findViewById(R.id.categoryProgress);

            BudgetData data = entry.getValue();

            label.setText(data.label);
            spentInput.setText(String.valueOf(data.spent));
            limitInput.setText(String.valueOf(data.limit));

            bar.setMax(data.limit);
            bar.setProgress(data.spent);

            // ---- Update progress bar when limit changes ----
            limitInput.addTextChangedListener(new SimpleWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int newLimit = Integer.parseInt(s.toString());
                        bar.setMax(newLimit);
                    } catch (Exception ignored) {}
                }
            });

            // ---- Update progress bar when spent changes ----
            spentInput.addTextChangedListener(new SimpleWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int newSpent = Integer.parseInt(s.toString());
                        bar.setProgress(newSpent);
                    } catch (Exception ignored) {}
                }
            });

            // ---------------- Bottom Navigation ----------------
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.nav_budget);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_analysis) {
                    startActivity(new Intent(this, AnalysisActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_budget) {
                    return true;
                } else if (itemId == R.id.nav_accounts) {
                    startActivity(new Intent(this, AccountsActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }

    // Helper class for simpler TextWatcher
    abstract static class SimpleWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}