package com.example.fintech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fintech.db.BudgetDao;
import com.example.fintech.db.BudgetEntity;
import com.example.fintech.db.FintechDatabase;
import com.example.fintech.db.TransactionDao;
import com.example.fintech.db.TransactionEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    private final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    @SuppressLint("NonConstantResourceId")
    private double safeParse(EditText edt) {
        String val = edt.getText().toString().trim();
        if (val.isEmpty()) return 0;
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText foodAmount = findViewById(R.id.foodAmount);
        EditText transportAmount = findViewById(R.id.transportAmount);
        EditText rentAmount = findViewById(R.id.rentAmount);
        EditText groceryAmount = findViewById(R.id.groceryAmount);
        EditText salaryAmount = findViewById(R.id.salaryAmount);

        Button btnSave = findViewById(R.id.btnSendValues);

        btnSave.setOnClickListener(v -> {

            double food = safeParse(foodAmount);
            double transport = safeParse(transportAmount);
            double rent = safeParse(rentAmount);
            double grocery = safeParse(groceryAmount);
            double salary = safeParse(salaryAmount);

            // ---------- WRITE TO LOCAL DB (transactions + budgets) ----------
            FintechDatabase db = FintechDatabase.getInstance(getApplicationContext());
            TransactionDao txDao = db.transactionDao();
            BudgetDao budgetDao = db.budgetDao();
            long now = System.currentTimeMillis();

            dbExecutor.execute(() -> {

                // 1) Clear previous transactions so Analysis chart uses only latest Home values
                txDao.deleteAll();

                // 2) Insert new transactions (expenses negative, income positive)
                if (food != 0) {
                    txDao.insert(new TransactionEntity(now, "Food", -Math.abs(food), null));
                }
                if (transport != 0) {
                    txDao.insert(new TransactionEntity(now, "Transport", -Math.abs(transport), null));
                }
                if (rent != 0) {
                    txDao.insert(new TransactionEntity(now, "Rent", -Math.abs(rent), null));
                }
                if (grocery != 0) {
                    txDao.insert(new TransactionEntity(now, "Groceries", -Math.abs(grocery), null));
                }
                if (salary != 0) {
                    txDao.insert(new TransactionEntity(now, "Income", Math.abs(salary), null));
                }

                // 3) Update Budget table left-box values (spent values)
                updateBudgetSpent(budgetDao, "Food", food);
                updateBudgetSpent(budgetDao, "Transport", transport);
                updateBudgetSpent(budgetDao, "Rent", rent);
                updateBudgetSpent(budgetDao, "Groceries", grocery);
                // Salary is income, not a spending budget
            });

            // ---------- NAVIGATE TO BUDGET PAGE ----------
            Intent intent = new Intent(HomeActivity.this, BudgetActivity.class);
            intent.putExtra("fromHome", true);
            intent.putExtra("foodSpent", food);
            intent.putExtra("transportSpent", transport);
            intent.putExtra("rentSpent", rent);
            intent.putExtra("grocerySpent", grocery);
            intent.putExtra("salaryAmount", salary);
            startActivity(intent);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
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

    private void updateBudgetSpent(BudgetDao dao, String category, double amount) {
        double value = Math.abs(amount);
        if (value == 0) return;

        BudgetEntity existing = dao.getByCategory(category);
        if (existing == null) {
            // default limit = spent * 2 (arbitrary starter)
            dao.insert(new BudgetEntity(category, value, value * 2));
        } else {
            existing.spent = value; // overwrite with latest entry
            dao.update(existing);
        }
    }
}
