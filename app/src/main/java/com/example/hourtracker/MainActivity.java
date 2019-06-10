package com.example.hourtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

//TODO 1: Have days, dayHours, and wage read/write to a file
//https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android Writing
//TODO 2: Create system for removing hours
//TODO 3: setup 2nd screen for changing wage, seeing hours, etc

//BigDecimal is used for storing most values as it is the best data type when dealing with currency.
public class MainActivity extends AppCompatActivity {
    private ConstraintLayout activity_main;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private Button addButton;
    private Button removeButton;
    private TextView wageText;
    private TextView totHourText;
    private TextView totOwedText;
    private TextView datesText;
    private TextView startHoursText;
    private TextView stopHoursText;
    private TextView hoursText;

    private DecimalFormat df=new DecimalFormat("0.00");
    private ArrayList<String> hours=new ArrayList<>();//Hours worked per day
    private ArrayList<String> days=new ArrayList<>();//Days worked
    private final BigDecimal wage=new BigDecimal("12.50");//Wage I'm paid
    private BigDecimal paid;

    public void setHoursInfo(){
        try{
            InputStream is=getAssets().open("hours.txt");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            String rtn[]=(new String(buffer)).split(" |\\\n");
            for(int n=0;n<rtn.length;n++){
                if((n+1)%3==0){//days
                    days.add(rtn[n]);
                }else{//hours
                    hours.add(rtn[n]);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public BigDecimal timeToHours(ArrayList<String> hours, int index){
        //Main is the hour difference between the two
        //Remain is +/- 30 minutes of time to account for times ending at ##:30
        BigDecimal main=BigDecimal.valueOf(Integer.parseInt(hours.get(index+1).substring(0,2))-Integer.parseInt(hours.get(index).substring(0,2)));
        BigDecimal remain=BigDecimal.valueOf((Integer.parseInt(hours.get(index+1).substring(3))-Integer.parseInt(hours.get(index).substring(3)))/60.0);
        return(main.add(remain));
    }

    public BigDecimal getTotalHours(ArrayList<String> hours){
        BigDecimal sum=new BigDecimal("0");
        for(int n=0;n<hours.size()/2;n++){
            sum=sum.add(timeToHours(hours,n*2));
        }
        return(sum);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton=findViewById(R.id.addButton);
        removeButton=findViewById(R.id.removeButton);
        wageText=findViewById(R.id.wageText);
        totHourText=findViewById(R.id.totHourText);
        totOwedText=findViewById(R.id.totOwedText);
        datesText=findViewById(R.id.datesText);
        startHoursText=findViewById(R.id.startHoursTest);
        stopHoursText=findViewById(R.id.stopHoursText);
        hoursText=findViewById(R.id.hoursText);

        //https://www.youtube.com/watch?v=3Zrwi3FFrC8
        mPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        setHoursInfo();
        wageText.setText("Wage:\n$"+df.format(wage));
        totHourText.setText("Total Hours:\n"+getTotalHours(hours).toString());
        totOwedText.setText("Total Owed:\n$"+df.format(getTotalHours(hours).multiply(wage)));
        for(int n=0;n<hours.size();n+=2){
            startHoursText.append(hours.get(n)+"\n");
            stopHoursText.append(hours.get(n+1)+"\n");
            hoursText.append(timeToHours(hours,n)+"\n");
            datesText.append(days.get(n/2)+"\n");
        }

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent addIntent=new Intent(MainActivity.this,AddScreen.class);
                startActivity(addIntent);//Switching to add screen
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent removeIntent=new Intent(MainActivity.this,RemoveScreen.class);
                startActivity(removeIntent);//Switching to remove screen
            }
        });
    }
}