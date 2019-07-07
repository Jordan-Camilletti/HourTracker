package com.example.hourtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;

public class AddScreen extends AppCompatActivity {
    private static final String FILE_NAME="hours.txt";

    private SharedPreferences.Editor mEditor;

    private EditText wageInput;
    private EditText startTimeInput;
    private EditText stopTimeInput;
    private EditText dateInput;

    private Context context=this;

    public BigDecimal timeToHours(String start,String stop){
        BigDecimal main = BigDecimal.valueOf(Integer.parseInt(stop.substring(0, 2)) - Integer.parseInt(start.substring(0, 2)));
        BigDecimal remain = BigDecimal.valueOf((Integer.parseInt(stop.substring(3)) - Integer.parseInt(start.substring(3))) / 60.0);
        return(main.add(remain));
    }

    public void appendHours(String add){
        String filePath=context.getFilesDir().getPath()+"/"+FILE_NAME;
        File file=new File(filePath);
        try {
            FileWriter fr = new FileWriter(file, true);
            fr.write(add);
            fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String correctHour(String hour){
        if((hour.contains(":") &hour.length()==4) || (!hour.contains(":") && hour.length()==3)){
            hour="0"+hour;//Hour doesn't have :
        }
        if(!hour.contains(":")){//6:00 instead of 06:00
            hour=hour.substring(0,2)+":"+hour.substring(2);
        }
        if(Integer.parseInt(hour.substring(0,2))<8){//Time is in PM form
            return(Integer.parseInt(hour.substring(0,2))+12+hour.substring(2)+" ");
        }else{
            return(hour);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        wageInput = findViewById(R.id.wageInput);
        startTimeInput = findViewById(R.id.startTimeInput);
        stopTimeInput = findViewById(R.id.stopTimeInput);
        dateInput = findViewById(R.id.dateInput);
        Button addHours = findViewById(R.id.addHours);
        Button wageSetButton = findViewById(R.id.wageSetButton);
        Button backButton = findViewById(R.id.backButton);

        SharedPreferences mPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        addHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtn="";
                String startH=correctHour(startTimeInput.getText().toString());
                String stopH=correctHour(stopTimeInput.getText().toString());
                rtn+=startH+" ";
                rtn+=stopH+" ";
                rtn+=dateInput.getText().toString()+" ";
                rtn+=timeToHours(startH,stopH)+" ";

                startTimeInput.getText().clear();
                stopTimeInput.getText().clear();
                dateInput.getText().clear();
                appendHours(rtn);
            }
        });

        wageSetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wageInput=findViewById(R.id.wageInput);
                mEditor.putString("Wage",wageInput.getText().toString());
                mEditor.apply();
                wageInput.getText().clear();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();//Switching to main screen
            }
        });
    }
}
