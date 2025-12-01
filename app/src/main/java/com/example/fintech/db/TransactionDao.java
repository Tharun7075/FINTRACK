package com.example.fintech.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(TransactionEntity tx);

    @Query("DELETE FROM transactions")
    void deleteAll();

    @Query("SELECT category, SUM(amount) AS total FROM transactions WHERE amount < 0 GROUP BY category")
    List<CategoryTotal> getExpenseTotalsByCategory();

    @Query("SELECT SUM(CASE WHEN amount < 0 THEN -amount ELSE 0 END) FROM transactions")
    Double getTotalExpenses();

    @Query("SELECT SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END) FROM transactions")
    Double getTotalIncome();

    @Query("SELECT * FROM transactions ORDER BY dateMillis DESC")
    List<TransactionEntity> getAll();

    @Query("SELECT SUM(amount) FROM transactions")
    Double getNetTotal();
}