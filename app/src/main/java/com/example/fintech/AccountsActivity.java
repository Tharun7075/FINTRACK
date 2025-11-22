package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountsActivity extends AppCompatActivity {

    EditText editCash, editDebit, editSavings, editAllAccounts, editExpenses, editIncome;
    private DatabaseReference mDatabase;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get username from intent
        currentUser = getIntent().getStringExtra("USERNAME");
        if (currentUser == null) {

            currentUser = "test1@fintrack.com";
        }

        // Convert email to Firebase-safe key (replace . with _)
        currentUser = currentUser.replace(".", "_");

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

        // Load saved balances when activity starts
        loadAccountBalances();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_accounts);

        bottomNav.setOnItemSelectedListener(item -> {
            // Save before navigating away
            saveAccountBalances();

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
                intent.putExtra("USERNAME", currentUser.replace("_", ".")); // Convert back for passing
                startActivity(intent);
                return true;
            }
            return false;
        });
    }


    private void loadAccountBalances() {
        mDatabase.child("user_accounts").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Double cash = snapshot.child("cash").getValue(Double.class);
                    Double debit = snapshot.child("debit").getValue(Double.class);
                    Double savings = snapshot.child("savings").getValue(Double.class);

                    if (cash != null) editCash.setText(String.valueOf(cash));
                    if (debit != null) editDebit.setText(String.valueOf(debit));
                    if (savings != null) editSavings.setText(String.valueOf(savings));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AccountsActivity", "Failed to load account balances", error.toException());
            }
        });
    }

    private void setupAutoSave() {
        // Save when user stops typing (optional)
        editCash.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) saveAccountBalances(); });
        editDebit.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) saveAccountBalances(); });
        editSavings.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) saveAccountBalances(); });
    }
    private void saveAccountBalances() {
        try {
            double cash = editCash.getText().toString().isEmpty() ? 0 : Double.parseDouble(editCash.getText().toString());
            double debit = editDebit.getText().toString().isEmpty() ? 0 : Double.parseDouble(editDebit.getText().toString());
            double savings = editSavings.getText().toString().isEmpty() ? 0 : Double.parseDouble(editSavings.getText().toString());

            Map<String, Object> accountData = new HashMap<>();
            accountData.put("cash", cash);
            accountData.put("debit", debit);
            accountData.put("savings", savings);
            accountData.put("lastUpdated", System.currentTimeMillis());

            mDatabase.child("user_accounts").child(currentUser).setValue(accountData)
                    .addOnSuccessListener(aVoid -> Log.d("AccountsActivity", "Balances saved for: " + currentUser))
                    .addOnFailureListener(e -> Log.e("AccountsActivity", "Save failed", e));

        } catch (NumberFormatException e) {
            Log.e("AccountsActivity", "Invalid number format");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAccountBalances();
    }
}