package com.example.hourtracker;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;

//TODO: Have days, dayHours, and wage read/write to a file
//https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android Writing
//https://stackoverflow.com/questions/30417810/reading-from-a-text-file-in-android-studio-java Reading
//TODO: Create system for removing hours
//TODO: setup 2nd screen for changing wage, seeing hours, etc
//BigDecimal is used for storing most values as it is the best data type when dealing with currency.
public class MainActivity extends AppCompatActivity {
    private Resources res=getResources();
    private ConstraintLayout activity_main;
    private Button addButton;
    private Button removeButton;
    private TextView wageText;
    private TextView totHourText;
    private TextView totOwedText;

    private String[] days;//Days worked
    private BigDecimal[] hours={new BigDecimal("123.321")};//Hours worked per day
    private final BigDecimal wage=new BigDecimal("12.50");//Wage I'm paid
    private BigDecimal paid;

    private StringBuilder daysString(String[] days,BigDecimal[] hours){//Outputs the days owed and hours
        StringBuilder rtn=new StringBuilder("Start Hours \t Stop Hours \t Hours \t Date\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t");
            rtn.append(hours[n+1]).append("\t");
            rtn.append(hours[n+1].subtract(hours[n])).append("\t");
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

    //private float

    private BigDecimal getTotalHours(BigDecimal[] hours){
        BigDecimal sum=new BigDecimal("0");
        for(BigDecimal h:hours){
            sum=sum.add(h);
        }
        return(sum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        System.out.println("X");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton=findViewById(R.id.addButton);
        removeButton=findViewById(R.id.removeButton);
        wageText=findViewById(R.id.wageText);
        totHourText=findViewById(R.id.totHourText);
        totOwedText=findViewById(R.id.totOwedText);
        wageText.setText(String.format(res.getString(R.string.wage),wage));
        totHourText.setText("Total Hours:\n$"+getTotalHours(hours).toString());
        /*
        //TODO: get better formating for BigDecimals
        totHourText.setText(new DecimalFormat("Total Hours:\n%#.##").format(getTotalHours(hours)));
        totOwedText.setText(new DecimalFormat("Total Owed:\n$#.##").format(getTotalHours(hours).multiply(wage)));
        */

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: add function for addButton click
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: add function for removeButton click
            }
        });
    }
}