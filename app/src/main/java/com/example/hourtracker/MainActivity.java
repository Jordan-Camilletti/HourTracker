package com.example.hourtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout activity_main;
    private TextView hoursOutput;//Output of hours worked and their days
    private Button addButton;

    private String[] days;//Days worked
    private float[] dayHours;//Hours worked per day
    private final double wage=12.50;//Wage I'm paid
    private float totalHours;//What I'm owed
    private float totalOwed;
    private float paid;

    private StringBuilder daysString(String[] days,float[] hours){
        StringBuilder rtn=new StringBuilder("Start Hours \t Stop Hours \t Hours \t Date\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t");
            rtn.append(hours[n+1]).append("\t");
            rtn.append(hours[n+1]-hours[n]).append("\t");
            rtn.append(days[n/2]).append("\n");
        }
        return(rtn);
    }

    private StringBuilder wagesString(float totHours,double wage){
        StringBuilder rtn=new StringBuilder("Hours \t\t Owed");
        rtn.append(totHours).append("\t\t");
        rtn.append(totHours*wage);
        return(rtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hoursOutput=findViewById(R.id.hoursOutput);
    }
}