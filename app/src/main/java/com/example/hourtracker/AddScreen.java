package com.example.hourtracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

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
    private BigDecimal newWage=new BigDecimal("12.50");//Wage I'm paid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        wageInput=(EditText) findViewById(R.id.wageInput);
        startTimeInput=(EditText) findViewById(R.id.startTimeInput);
        stopTimeInput=(EditText) findViewById(R.id.stopTimeInput);
        dateInput=(EditText) findViewById(R.id.dateInput);
        addHours=(Button) findViewById(R.id.addHours);
        wageSetButton=(Button) findViewById(R.id.wageSetButton);
        backButton=(Button) findViewById(R.id.backButton);

        https://www.youtube.com/watch?v=3Zrwi3FFrC8
        mPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        addHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtn="";
                rtn+=startTimeInput.getText().toString()+" ";
                rtn+=stopTimeInput.getText().toString()+" ";
                rtn+=dateInput.getText().toString()+"\n";
                //System.out.println(rtn);
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(rtn.getBytes());
                    startTimeInput.getText().clear();
                    stopTimeInput.getText().clear();
                    dateInput.getText().clear();
                    //Toast.makeText(this,"Saved to "+getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(fos!=null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        wageSetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wageInput=(EditText) findViewById(R.id.wageInput);
                newWage=new BigDecimal(wageInput.getText().toString());
                //System.out.println(newWage);
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
