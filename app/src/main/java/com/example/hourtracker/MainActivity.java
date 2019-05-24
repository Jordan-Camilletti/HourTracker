package com.example.hourtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private ConstraintLayout activity_main;
    private Button addButton;

    private String[] dates;
    private float[] dayHours;
    private float[] dayTimes;
    private double wage;
    private float totalHours;
    private float totalOwed;
    private float paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}   
