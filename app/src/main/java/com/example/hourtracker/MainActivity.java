package com.example.hourtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private ConstraintLayout activity_main;
    private Button addButton;

    private float[] dayHours;
    private float[] dayTimes;
    private final double wage=12.50;
    private float totalHours;
    private float totalOwed;
    private float paid;

    private StringBuilder daysString(float[] hours,float[] times){
        StringBuilder rtn=new StringBuilder("Start Hours \t Stop Hours \t Hours \t Date\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t");
            rtn.append(hours[n+1]).append("\t");
            rtn.append(hours[n+1]-hours[n]).append("\t");
            rtn.append(times[n/2]).append("\n");
}
        return(rtn);
                }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}