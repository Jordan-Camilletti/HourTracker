package com.example.hourtracker;

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
import java.util.ArrayList;
import java.util.Arrays;

public class RemoveScreen extends AppCompatActivity {

    private Button backButton;
    private Button clearAllButton;
    private Button removeButton;
    private EditText paidInput;

    private SharedPreferences mPreferences;
    private MainActivity mainAc=new MainActivity();

    private BigDecimal hoursPaid=new BigDecimal("0.0");
    private BigDecimal wage=new BigDecimal("12.50");
    private String FILE_NAME="hours.txt";
    private ArrayList<String> hours;
    private ArrayList<String> days;

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
            String text="";
            while((text=br.readLine())!=null){
                sb.append(text);
            }
            String rtn[]=(sb.toString().split(" "));
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

    public void resetFile(String FILE_NAME) {//Used to reset the contents of "hours.txt" because I'm an idiot
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write("00:00 00:00 0000-00-00 ".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        backButton=(Button) findViewById(R.id.backButton);
        clearAllButton=(Button) findViewById(R.id.clearAllButton);
        removeButton=(Button) findViewById(R.id.removeButton);
        paidInput=(EditText) findViewById(R.id.paidInput);

        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println(mainAc.getTotalHours(hours));

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clearAllButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetFile(FILE_NAME);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setHoursInfo();
                wage=new BigDecimal(mPreferences.getString("Wage","12.50"));
                hoursPaid=new BigDecimal(paidInput.getText().toString());
                hoursPaid=hoursPaid.divide(wage);
                System.out.println(hoursPaid);
            }
        });

    }
}
