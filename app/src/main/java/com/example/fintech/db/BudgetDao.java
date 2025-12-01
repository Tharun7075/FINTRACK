package com.example.fintech.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert
    void insert(BudgetEntity budget);

    @Update
    void update(BudgetEntity budget);

    @Query("SELECT * FROM budgets")
    List<BudgetEntity> getAll();

    @Query("SELECT * FROM budgets WHERE category = :category LIMIT 1")
    BudgetEntity getByCategory(String category);
}
