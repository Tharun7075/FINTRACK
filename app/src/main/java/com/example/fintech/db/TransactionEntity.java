package com.example.fintech.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long dateMillis;   // System.currentTimeMillis()
    public String category;   // Food, Transport, Rent, etc.
    public double amount;     // negative = expense, positive = income
    public String note;       // optional

    public TransactionEntity(long dateMillis, String category, double amount, String note) {
        this.dateMillis = dateMillis;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }
}
