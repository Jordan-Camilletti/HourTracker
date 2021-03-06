package com.example.hourtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class RemoveScreen extends AppCompatActivity {
    private EditText paidInput;

    private SharedPreferences mPreferences;

    private BigDecimal compareZero=new BigDecimal("0.0");
    private BigDecimal hoursPaid=new BigDecimal("0.0");
    private BigDecimal wage=new BigDecimal("12.50");
    private String FILE_NAME="hours.txt";
    private ArrayList<String> hours;
    private ArrayList<String> days;
    private ArrayList<BigDecimal> unpaid;

    public String arrsToString(ArrayList<String> h,ArrayList<String> d, ArrayList<BigDecimal> u){
        //Turns the contents of hours, days, and unpaid ArrayLists into a string formatted properly for hours.txt
        StringBuilder rtnStr=new StringBuilder();
        for(int n=0;n<h.size();n+=2){
            rtnStr.append(h.get((n))).append(" ");
            rtnStr.append(h.get(n+1)).append(" ");
            rtnStr.append(d.get(n/2)).append(" ");
            rtnStr.append(u.get(n/2)).append(" ");
        }
        return(rtnStr.toString());
    }

    public void writeNewHours(String FILE_NAME, boolean reset){
        //Writes the new contents of hours and days ArrayLists into FILE_NAME
        //Also used to reset the contents of "hours.txt" if 'reset' if true because I'm an idiot
        try {
            FileOutputStream fos= openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            if(reset){
                fos.write("00:00 00:00 0000-00-00 0.0 ".getBytes());
            }else {
                fos.write(arrsToString(hours, days, unpaid).getBytes());
            }
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setHoursInfo(){
        //This is used to get hour info from "hours.txt"
        //Everything after the for-loop is simply there to stop error messages
        hours=new ArrayList<>();
        days=new ArrayList<>();
        unpaid=new ArrayList<>();
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
                if(n%4<=1){//days
                    hours.add(rtn[n]);
                }else if(n%4==2){//hours
                    days.add(rtn[n]);
                }else{
                    unpaid.add(new BigDecimal(rtn[n]));
                }
            }
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

    public BigDecimal minDec(BigDecimal n1, BigDecimal n2){
        if(n1.compareTo(n2)>0){
            return(n2);
        }
        return(n1);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        Button backButton = findViewById(R.id.backButton);
        Button clearAllButton = findViewById(R.id.clearAllButton);
        Button removeButton = findViewById(R.id.removeButton);
        paidInput = findViewById(R.id.paidInput);

        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        setHoursInfo();

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();//Returns back to main screen
            }
        });

        clearAllButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                writeNewHours(FILE_NAME,true);//Clears the file
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                wage=new BigDecimal(mPreferences.getString("Wage","12.50"));
                hoursPaid=new BigDecimal(paidInput.getText().toString());
                hoursPaid=hoursPaid.divide(wage, RoundingMode.CEILING);//Calculating hours owed via the wage and $ owed
                BigDecimal smallestDec;

                //Removes a date if it's total hours is less than the current owed
                //If there's any hours owed left after all dates are checked then the left is displayed as leftover hours
                for(int n=1;n<unpaid.size();n++){
                    if(unpaid.get(n).compareTo(compareZero)>0 && hoursPaid.compareTo(compareZero)>0){
                        smallestDec=minDec(unpaid.get(n),hoursPaid);
                        unpaid.set(n,unpaid.get(n).subtract(smallestDec));
                        hoursPaid=hoursPaid.subtract(smallestDec);
                    }
                    System.out.println("U:"+unpaid.get(n)+"\nH:"+hoursPaid);
                    /*if(mainAc.timeToHours(hours,n).compareTo(hoursPaid)<=0){
                        hoursPaid=hoursPaid.subtract(mainAc.timeToHours(hours,n));
                        days.remove(n/2);
                        hours.remove(n+1);
                        hours.remove(n);
                        n-=2;
                    }*/
                    //System.out.println(mainAc.timeToHours(hours,n));
                }
                writeNewHours(FILE_NAME,false);
            }
        });
    }
}
