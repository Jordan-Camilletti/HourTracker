package com.example.hourtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//TODO: Have days, dayHours, and wage read/write to a file
//https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android 
//https://stackoverflow.com/questions/30417810/reading-from-a-text-file-in-android-studio-java
//TODO: Create system for removing hours
//TODO: setup 2nd screen for changing wage, seeing hours, etc
public class MainActivity extends AppCompatActivity {
    private ConstraintLayout activity_main;
    private TextView hoursOutput;//Output of hours worked and their days
    private TextView wageOutput;//Output of total owed
    private Button addButton;

    private String[] days;//Days worked
    private float[] dayHours;//Hours worked per day
    private final double wage=12.50;//Wage I'm paid
    private float totalHours;//What I'm owed
    private float totalOwed;
    private float paid;

    private StringBuilder daysString(String[] days,float[] hours){//Outputs the days owed and hours
        StringBuilder rtn=new StringBuilder("Start Hours \t Stop Hours \t Hours \t Date\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t");
            rtn.append(hours[n+1]).append("\t");
            rtn.append(hours[n+1]-hours[n]).append("\t");
            rtn.append(days[n/2]).append("\n");
        }
        return(rtn);
    }

    private StringBuilder wagesString(float totHours,double wage){//Outputs the total owed hours and $ owed
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
        wageOutput=findViewById(R.id.wagesOutput);
        addButton=findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: add function for addButton click
            }
        });
    }
}