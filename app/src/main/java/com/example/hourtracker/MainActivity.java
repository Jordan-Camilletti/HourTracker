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
    private ConstraintLayout activity_main;
    private Button addButton;
    private Button removeButton;
    private TextView wageText;
    private TextView totHourText;
    private TextView totOwedText;

    private DecimalFormat df=new DecimalFormat("0.00");
    private String[] days;//Days worked
    private String[] hours={"123.5"};//Hours worked per day
    private final BigDecimal wage=new BigDecimal("12.50");//Wage I'm paid
    private BigDecimal paid;

    public BigDecimal timeToHours(String[] hours, int index){
        //Main is the hour difference between the two
        //Remain is +/- 30 minutes of time to account for times ending at ##:30
        BigDecimal main=BigDecimal.valueOf(Integer.parseInt(hours[index+1].substring(0,3))-Integer.parseInt(hours[index].substring(0,3)));
        BigDecimal remain=BigDecimal.valueOf((Integer.parseInt(hours[index+1].substring(3))-Integer.parseInt(hours[index].substring(3)))/60);
        return(main.add(remain));
    }

    public StringBuilder daysString(String[] days,String[] hours){//Outputs the days owed and hours
        StringBuilder rtn=new StringBuilder("Start Hours \t Stop Hours \t Hours \t Date\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t");
            rtn.append(hours[n+1]).append("\t");
            rtn.append(timeToHours(hours,n));
            //rtn.append(hours[n+1].subtract(hours[n])).append("\t");
            rtn.append(days[n/2]).append("\n");
        }
        return(rtn);
    }

    public BigDecimal getTotalHours(String[] hours){
        BigDecimal sum=new BigDecimal("0");
        for(int n=0;n<hours.length/2;n++){
            sum=sum.add(timeToHours(hours,n));
        }
        return(sum);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        System.out.print("X");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton=findViewById(R.id.addButton);
        removeButton=findViewById(R.id.removeButton);
        wageText=findViewById(R.id.wageText);
        totHourText=findViewById(R.id.totHourText);
        totOwedText=findViewById(R.id.totOwedText);
        wageText.setText("Wage:\n$"+df.format(wage));
        totHourText.setText("Total Hours:\n"+getTotalHours(hours).toString());
        totOwedText.setText("Total Owed:\n$"+df.format(getTotalHours(hours).multiply(wage)));

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