package com.example.hourtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

//BigDecimal is used for storing most values as it is the best data type when dealing with currency.
public class MainActivity extends AppCompatActivity {
    private ConstraintLayout activity_main;
    private static final String FILE_NAME="hours.txt";

    private TextView wageText;
    private TextView totHourText;
    private TextView totOwedText;
    private TextView datesText;
    private TextView startHoursText;
    private TextView stopHoursText;
    private TextView hoursText;

    private SharedPreferences mPreferences;

    private DecimalFormat df=new DecimalFormat("0.00");
    private ArrayList<String> hours=new ArrayList<>();//Hours worked per day
    private ArrayList<String> days=new ArrayList<>();//Days worked


    public void setHoursInfo(){
        //This is used to get hour info from "hours.txt"
        //Everything after the for-loop is simply there to stop error messages
        hours=new ArrayList<>();
        days=new ArrayList<>();
        FileInputStream fis=null;
        try{
            fis=openFileInput(FILE_NAME);
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String text;
            while((text=br.readLine())!=null){
                sb.append(text);
            }
            String[] rtn=(sb.toString().split(" "));
            for(int n=0;n<rtn.length;n++){
                if((n+1)%3==0){//days
                    days.add(rtn[n]);
                }else{//hours
                    hours.add(rtn[n]);
                }
            }
            System.out.println(Arrays.toString(rtn));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public BigDecimal timeToHours(ArrayList<String> hours, int index){
        //Main is the hour difference between the two
        //Remain is +/- 30 minutes of time to account for times ending at ##:30
        try {
            BigDecimal main = BigDecimal.valueOf(Integer.parseInt(hours.get(index + 1).substring(0, 2)) - Integer.parseInt(hours.get(index).substring(0, 2)));
            BigDecimal remain = BigDecimal.valueOf((Integer.parseInt(hours.get(index + 1).substring(3)) - Integer.parseInt(hours.get(index).substring(3))) / 60.0);
            return(main.add(remain));
        }catch(StringIndexOutOfBoundsException e){
            return(new BigDecimal(0));
        }
    }

    public BigDecimal getTotalHours(ArrayList<String> hours){
        //Calculates the total hours from the hours array
        BigDecimal sum=new BigDecimal("0");
        for(int n=0;n<hours.size()/2;n++){
            sum=sum.add(timeToHours(hours,n*2));
        }
        return(sum);
    }

    @SuppressLint("SetTextI18n")
    public void updateAll(){
        //This updates all of the info on the main screen
        //It updates the wage, hours worked per day, total hours worked, and total owed
        setHoursInfo();

        System.out.println(days.toString());
        System.out.println(hours.toString());

        BigDecimal wage=new BigDecimal(mPreferences.getString("Wage","12.50"));

        wageText.setText("Wage:\n$"+df.format(wage));
        totHourText.setText("Total Hours:\n"+getTotalHours(hours).toString());
        totOwedText.setText("Total Owed:\n$"+df.format(getTotalHours(hours).multiply(wage)));

        startHoursText.setText("Start Hours:\n");
        stopHoursText.setText("Stop Hours:\n");
        hoursText.setText("Hours:\n");
        datesText.setText("Dates:\n");
        for(int n=2;n<hours.size();n+=2){
            startHoursText.append(hours.get(n)+"\n");
            stopHoursText.append(hours.get(n+1)+"\n");
            hoursText.append(timeToHours(hours,n)+"\n");
            datesText.append(days.get(n/2)+"\n");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton=findViewById(R.id.addButton);//Setting all layout items
        Button removeButton=findViewById(R.id.removeButton);
        Button updateButton=findViewById(R.id.updateButton);
        wageText=findViewById(R.id.wageText);
        totHourText=findViewById(R.id.totHourText);
        totOwedText=findViewById(R.id.totOwedText);
        datesText=findViewById(R.id.datesText);
        startHoursText=findViewById(R.id.startHoursTest);
        stopHoursText=findViewById(R.id.stopHoursText);
        hoursText=findViewById(R.id.hoursText);

        mPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        updateAll();

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
                Intent removeIntent=new Intent(v.getContext(),RemoveScreen.class);
                startActivity(removeIntent);//Switching to remove screen
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateAll();
            }
        });
    }
}