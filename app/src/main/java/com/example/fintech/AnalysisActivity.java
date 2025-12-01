package com.example.fintech;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fintech.db.CategoryTotal;
import com.example.fintech.db.FintechDatabase;
import com.example.fintech.db.TransactionDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnalysisActivity extends AppCompatActivity {

    private final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PieChart pieChart = findViewById(R.id.pieChart);
        if (pieChart != null) {
            setupPieChart(pieChart);
            loadChartData(pieChart);
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_analysis);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_analysis) {
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

    private void setupPieChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleRadius(65f);
        pieChart.setEntryLabelTextSize(12f);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }

    private void loadChartData(PieChart pieChart) {
        FintechDatabase db = FintechDatabase.getInstance(getApplicationContext());
        TransactionDao txDao = db.transactionDao();

        dbExecutor.execute(() -> {
            List<CategoryTotal> totals = txDao.getExpenseTotalsByCategory();
            if (totals == null || totals.isEmpty()) {
                return;
            }

            List<PieEntry> entries = new ArrayList<>();
            for (CategoryTotal t : totals) {
                float value = (float) Math.abs(t.total);
                if (value > 0f) {
                    entries.add(new PieEntry(value, t.category));
                }
            }
            if (entries.isEmpty()) {
                return;
            }

            runOnUiThread(() -> {
                PieDataSet dataSet = new PieDataSet(entries, "Expenses");
                dataSet.setSliceSpace(2f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(pieChart));
                data.setValueTextSize(12f);

                pieChart.setData(data);
                pieChart.invalidate();
            });
        });
    }
}
