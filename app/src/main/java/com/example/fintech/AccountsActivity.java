package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountsActivity extends AppCompatActivity {

    EditText editCash, editDebit, editSavings, editAllAccounts, editExpenses, editIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editCash = findViewById(R.id.editCash);
        editDebit = findViewById(R.id.editDebit);
        editSavings = findViewById(R.id.editSavings);
        editAllAccounts = findViewById(R.id.editAllAccounts);
        editExpenses = findViewById(R.id.editExpenses);
        editIncome = findViewById(R.id.editIncome);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_accounts);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
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
