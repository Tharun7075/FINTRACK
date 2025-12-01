package com.example.fintech.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {TransactionEntity.class, BudgetEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class FintechDatabase extends RoomDatabase {

    private static volatile FintechDatabase INSTANCE;

    public abstract TransactionDao transactionDao();
    public abstract BudgetDao budgetDao();

    public static FintechDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FintechDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    FintechDatabase.class,
                                    "fintech_db"
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}