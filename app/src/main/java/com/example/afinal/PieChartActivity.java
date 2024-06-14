package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        Intent mIntent = getIntent();
        int cal = mIntent.getIntExtra("cal", 0);
        int fat = mIntent.getIntExtra("fat", 0);
        int pro = mIntent.getIntExtra("pro", 0);

        PieChart pieChart= findViewById(R.id.pie);

        ArrayList<PieEntry> foodVal= new ArrayList<>();
        foodVal.add(new PieEntry(cal,"Calories"));
        foodVal.add(new PieEntry(fat,"Fats"));
        foodVal.add(new PieEntry(pro,"Proteins"));

        PieDataSet pieDataSet= new PieDataSet(foodVal, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData=new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Quantities");
        pieChart.animate();

    }
}