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

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText wageInput;
    private EditText startTimeInput;
    private EditText stopTimeInput;
    private EditText dateInput;
    private Button addHours;
    private Button wageSetButton;
    private Button backButton;

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
        addHours = findViewById(R.id.addHours);
        wageSetButton = findViewById(R.id.wageSetButton);
        backButton = findViewById(R.id.backButton);

        mPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        addHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtn="";
                rtn+=startTimeInput.getText().toString()+" ";
                startTimeInput.getText().clear();
                if(Integer.parseInt(stopTimeInput.getText().toString().substring(0,2))<9){//Time is in PM form
                    rtn+=Integer.toString(Integer.parseInt(stopTimeInput.getText().toString().substring(0,2))+12)+stopTimeInput.getText().toString().substring(2)+" ";
                }else{
                    rtn+=stopTimeInput.getText().toString()+" ";
                }
                stopTimeInput.getText().clear();
                rtn+=dateInput.getText().toString()+" ";
                dateInput.getText().clear();
                appendHours(rtn);
            }
        });

        wageSetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wageInput=findViewById(R.id.wageInput);
                mEditor.putString("Wage",wageInput.getText().toString());
                mEditor.commit();
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
