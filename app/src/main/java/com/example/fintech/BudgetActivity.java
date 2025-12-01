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

import com.example.fintech.db.BudgetDao;
import com.example.fintech.db.BudgetEntity;
import com.example.fintech.db.FintechDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetActivity extends AppCompatActivity {

    private final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    private static class BudgetData {
        final String label;
        final String categoryKey; // "Food", "Transport", ...
        int spent;
        int limit;

        BudgetData(String label, String categoryKey, int spent, int limit) {
            this.label = label;
            this.categoryKey = categoryKey;
            this.spent = spent;
            this.limit = limit;
        }
    }

    private final Map<Integer, BudgetData> budgets = new HashMap<>();

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

        boolean fromHome = getIntent().getBooleanExtra("fromHome", false);
        int foodFromHome = (int) getIntent().getDoubleExtra("foodSpent", 0);
        int transportFromHome = (int) getIntent().getDoubleExtra("transportSpent", 0);
        int rentFromHome = (int) getIntent().getDoubleExtra("rentSpent", 0);
        int groceryFromHome = (int) getIntent().getDoubleExtra("grocerySpent", 0);

        // Default values (will be overridden by DB or home input)
        budgets.put(R.id.foodCard,
                new BudgetData("🍔 Food", "Food",
                        fromHome ? foodFromHome : 20, 500));
        budgets.put(R.id.transportCard,
                new BudgetData("🚗 Transport", "Transport",
                        fromHome ? transportFromHome : 100, 1000));
        budgets.put(R.id.groceryCard,
                new BudgetData("🛒 Groceries", "Groceries",
                        fromHome ? groceryFromHome : 80, 300));
        budgets.put(R.id.billsCard,
                new BudgetData("📄 Bills", "Rent",
                        fromHome ? rentFromHome : 100, 700));


        // Bind UI for each card
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

            limitInput.addTextChangedListener(new SimpleWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int newLimit = Integer.parseInt(s.toString());
                        bar.setMax(newLimit);
                        data.limit = newLimit;
                    } catch (Exception ignored) {}
                }
            });

            spentInput.addTextChangedListener(new SimpleWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int newSpent = Integer.parseInt(s.toString());
                        bar.setProgress(newSpent);
                        data.spent = newSpent;
                    } catch (Exception ignored) {}
                }
            });
        }

        // If not coming directly from Home, load saved budgets from DB
        if (!fromHome) {
            loadBudgetsFromDb();
        }

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

    private void loadBudgetsFromDb() {
        FintechDatabase db = FintechDatabase.getInstance(getApplicationContext());
        BudgetDao dao = db.budgetDao();

        dbExecutor.execute(() -> {
            List<BudgetEntity> list = dao.getAll();
            runOnUiThread(() -> {
                for (BudgetEntity b : list) {
                    for (Map.Entry<Integer, BudgetData> entry : budgets.entrySet()) {
                        BudgetData data = entry.getValue();
                        if (data.categoryKey.equals(b.category)) {
                            data.spent = (int) b.spent;
                            data.limit = (int) b.limit;

                            View card = findViewById(entry.getKey());
                            if (card == null) continue;
                            EditText spentInput = card.findViewById(R.id.spentInput);
                            EditText limitInput = card.findViewById(R.id.limitInput);
                            ProgressBar bar = card.findViewById(R.id.categoryProgress);

                            spentInput.setText(String.valueOf(data.spent));
                            limitInput.setText(String.valueOf(data.limit));
                            bar.setMax(data.limit);
                            bar.setProgress(data.spent);
                        }
                    }
                }
            });
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        FintechDatabase db = FintechDatabase.getInstance(getApplicationContext());
        BudgetDao dao = db.budgetDao();

        dbExecutor.execute(() -> {
            for (BudgetData data : budgets.values()) {
                BudgetEntity existing = dao.getByCategory(data.categoryKey);
                if (existing == null) {
                    dao.insert(new BudgetEntity(data.categoryKey, data.spent, data.limit));
                } else {
                    existing.spent = data.spent;
                    existing.limit = data.limit;
                    dao.update(existing);
                }
            }
        });
    }

    private double parseDoubleSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return 0;
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // Helper class for simpler TextWatcher
    abstract static class SimpleWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}
