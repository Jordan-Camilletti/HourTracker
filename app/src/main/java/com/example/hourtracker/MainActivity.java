package com.example.hourtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO 1: Have days, dayHours, and wage read/write to a file
//https://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android Writing
//https://stackoverflow.com/questions/30417810/reading-from-a-text-file-in-android-studio-java Reading
//TODO 2: Create system for removing hours
//TODO 3: setup 2nd screen for changing wage, seeing hours, etc

//BigDecimal is used for storing most values as it is the best data type when dealing with currency.
public class MainActivity extends AppCompatActivity {
    private ConstraintLayout activity_main;

    private Context mContext;//Used to read/write data to/from hours.txt

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
    private String[] days={"2019-02-42","3214-32-13","0926-06-21"};//Days worked
    private String[] hours={"12:30","19:00","10:00","19:00","13:00","19:00"};//Hours worked per day
    private final BigDecimal wage=new BigDecimal("12.50");//Wage I'm paid
    private BigDecimal paid;

    public List<String> getHours(){
        List<String> daysLines=new ArrayList<>();
        AssetManager am=mContext.getAssets();
        try {
            InputStream is = am.open("hours.txt");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String ln;
            while((ln=reader.readLine())!=null)
                daysLines.add(ln);
        } catch(IOException e){
            e.printStackTrace();
        }
        return(daysLines);
    }

    public BigDecimal timeToHours(String[] hours, int index){
        //Main is the hour difference between the two
        //Remain is +/- 30 minutes of time to account for times ending at ##:30
        BigDecimal main=BigDecimal.valueOf(Integer.parseInt(hours[index+1].substring(0,2))-Integer.parseInt(hours[index].substring(0,2)));
        BigDecimal remain=BigDecimal.valueOf((Integer.parseInt(hours[index+1].substring(3))-Integer.parseInt(hours[index].substring(3)))/60.0);
        return(main.add(remain));
    }

    public StringBuilder daysString(String[] days,String[] hours){//Outputs the days owed and hours
        StringBuilder rtn=new StringBuilder("Date \t\t Stop Hours \t\t Hours \t\t Start Hours\n");
        for(int n=0;n<hours.length;n+=2){
            rtn.append(hours[n]).append("\t\t");
            rtn.append(hours[n+1]).append("\t\t");
            rtn.append(timeToHours(hours,n)).append("\t\t");
            rtn.append(days[n/2]).append("\t\t").append("\n");
        }
        return(rtn);
    }

    public BigDecimal getTotalHours(String[] hours){
        BigDecimal sum=new BigDecimal("0");
        for(int n=0;n<hours.length/2;n++){
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

        System.out.println(getHours());

        wageText.setText("Wage:\n$"+df.format(wage));
        totHourText.setText("Total Hours:\n"+getTotalHours(hours).toString());
        totOwedText.setText("Total Owed:\n$"+df.format(getTotalHours(hours).multiply(wage)));
        for(int n=0;n<hours.length;n+=2){
            datesText.append(hours[n]+"\n");
            startHoursText.append(hours[n+1]+"\n");
            stopHoursText.append(timeToHours(hours,n)+"\n");
            hoursText.append(days[n/2]+"\n");
        }

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO 2.a: add function for addButton click
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO 2.b: add function for removeButton click
            }
        });
    }
}