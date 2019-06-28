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

public class AddScreen extends AppCompatActivity {
    private static final String FILE_NAME="hours.txt";

    //private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText wageInput;
    private EditText startTimeInput;
    private EditText stopTimeInput;
    private EditText dateInput;
    //private Button addHours;
    //private Button wageSetButton;
    //private Button backButton;

    private Context context=this;

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
                String startInput=startTimeInput.getText().toString();
                String stopInput=stopTimeInput.getText().toString();
                if(startInput.length()==4){
                    startInput=startInput.substring(0,2)+":"+startInput.substring(2);
                }
                System.out.print(startInput);
                rtn+=startInput+" ";
                if(stopInput.length()==4){
                    stopInput=stopInput.substring(0,2)+":"+stopInput.substring(2);
                }
                if(Integer.parseInt(stopInput.substring(0,2))<9){//Time is in PM form
                    rtn+=Integer.parseInt(stopInput.substring(0,2))+12+stopInput.substring(2)+" ";
                }else{
                    rtn+=stopInput+" ";
                }
                rtn+=dateInput.getText().toString()+" ";

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
