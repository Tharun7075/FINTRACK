package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
import android.widget.Button;
=======
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fintech.db.FintechDatabase;
import com.example.fintech.db.TransactionDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountsActivity extends AppCompatActivity {

<<<<<<< HEAD
    EditText editCash, editDebit, editSavings;
    TextView txtAllAccounts, txtExpenses, txtIncome;

    private DatabaseReference mDatabase;
    private String currentUser;

    private ExecutorService dbExecutor = Executors.newSingleThreadExecutor();
    private TransactionDao txDao;
=======
    EditText editCash, editDebit, editSavings, editAllAccounts, editExpenses, editIncome;
    private DatabaseReference mDatabase;
    private String currentUser;
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accounts);

<<<<<<< HEAD
        // Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Local Room DB for income/expense totals
        FintechDatabase localDb = FintechDatabase.getInstance(getApplicationContext());
        txDao = localDb.transactionDao();

        // Username from intent (fallback)
        currentUser = getIntent().getStringExtra("USERNAME");
        if (currentUser == null) {
            currentUser = "test1@fintrack.com";
        }
=======
        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get username from intent
        currentUser = getIntent().getStringExtra("USERNAME");
        if (currentUser == null) {

            currentUser = "test1@fintrack.com";
        }

        // Convert email to Firebase-safe key (replace . with _)
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
        currentUser = currentUser.replace(".", "_");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views
        editCash = findViewById(R.id.editCash);
        editDebit = findViewById(R.id.editDebit);
        editSavings = findViewById(R.id.editSavings);

<<<<<<< HEAD
        txtAllAccounts = findViewById(R.id.txtAllAccountsValue);
        txtExpenses = findViewById(R.id.txtExpensesValue);
        txtIncome = findViewById(R.id.txtIncomeValue);

        Button btnSaveAccounts = findViewById(R.id.btnSaveAccounts);

        // Load balances and summary
        loadAccountBalances();
        refreshSummary(); // in case Room already has data

        // Auto-save when leaving fields
        setupAutoSave();

        // Save button
        btnSaveAccounts.setOnClickListener(v -> {
            saveAccountBalances();
            refreshSummary();
        });

        // Bottom navigation
=======
        // Load saved balances when activity starts
        loadAccountBalances();

>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_accounts);

        bottomNav.setOnItemSelectedListener(item -> {
<<<<<<< HEAD
            saveAccountBalances();
            refreshSummary();
=======
            // Save before navigating away
            saveAccountBalances();
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e

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
<<<<<<< HEAD
                intent.putExtra("USERNAME", currentUser.replace("_", "."));
=======
                intent.putExtra("USERNAME", currentUser.replace("_", ".")); // Convert back for passing
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

<<<<<<< HEAD
    private double parseOrZero(EditText edt) {
        String s = edt.getText().toString().trim();
        if (s.isEmpty()) return 0;
        try { return Double.parseDouble(s); } catch (Exception e) { return 0; }
    }

    private void loadAccountBalances() {
        mDatabase.child("user_accounts").child(currentUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Double cash = snapshot.child("cash").getValue(Double.class);
                            Double debit = snapshot.child("debit").getValue(Double.class);
                            Double savings = snapshot.child("savings").getValue(Double.class);

                            if (cash != null) editCash.setText(String.valueOf(cash));
                            if (debit != null) editDebit.setText(String.valueOf(debit));
                            if (savings != null) editSavings.setText(String.valueOf(savings));

                            // After loading from Firebase, update totals
                            refreshSummary();
                        } else {
                            refreshSummary();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("AccountsActivity", "Failed to load account balances", error.toException());
                    }
                });
    }

    private void setupAutoSave() {
        editCash.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) { saveAccountBalances(); refreshSummary(); }});
        editDebit.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) { saveAccountBalances(); refreshSummary(); }});
        editSavings.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) { saveAccountBalances(); refreshSummary(); }});
    }

    private void saveAccountBalances() {
        try {
            double cash = parseOrZero(editCash);
            double debit = parseOrZero(editDebit);
            double savings = parseOrZero(editSavings);
=======

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
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e

            Map<String, Object> accountData = new HashMap<>();
            accountData.put("cash", cash);
            accountData.put("debit", debit);
            accountData.put("savings", savings);
            accountData.put("lastUpdated", System.currentTimeMillis());

            mDatabase.child("user_accounts").child(currentUser).setValue(accountData)
                    .addOnSuccessListener(aVoid -> Log.d("AccountsActivity", "Balances saved for: " + currentUser))
                    .addOnFailureListener(e -> Log.e("AccountsActivity", "Save failed", e));

<<<<<<< HEAD
        } catch (Exception e) {
            Log.e("AccountsActivity", "Invalid number format", e);
        }
    }

    private void refreshSummary() {
        // 1) Accounts total from fields
        double cash = parseOrZero(editCash);
        double debit = parseOrZero(editDebit);
        double savings = parseOrZero(editSavings);
        double accountsTotal = cash + debit + savings;
        txtAllAccounts.setText(String.valueOf((int) accountsTotal));

        // 2) Income / expenses from Room
        dbExecutor.execute(() -> {
            Double totalExp = txDao.getTotalExpenses();
            Double totalInc = txDao.getTotalIncome();

            double exp = totalExp == null ? 0 : totalExp;
            double inc = totalInc == null ? 0 : totalInc;

            runOnUiThread(() -> {
                txtExpenses.setText(String.valueOf((int) exp));
                txtIncome.setText(String.valueOf((int) inc));
            });
        });
    }

=======
        } catch (NumberFormatException e) {
            Log.e("AccountsActivity", "Invalid number format");
        }
    }

>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
    @Override
    protected void onPause() {
        super.onPause();
        saveAccountBalances();
<<<<<<< HEAD
        refreshSummary();
    }
}
=======
    }
}
>>>>>>> 44fd03d43475b7f75c92e15e9b47d6600ad12d5e
