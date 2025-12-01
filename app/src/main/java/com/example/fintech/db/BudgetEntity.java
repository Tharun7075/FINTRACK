package com.example.fintech.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgets")
public class BudgetEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String category;   // Food, Transport, Groceries, Bills...
    public double spent;
    public double limit;

    public BudgetEntity(String category, double spent, double limit) {
        this.category = category;
        this.spent = spent;
        this.limit = limit;
    }
}
