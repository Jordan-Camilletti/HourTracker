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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private Context context=this;

    public StringBuilder readFile(){
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
            return(sb);
            //String rtn[]=(sb.toString().split(" |\\\n"));
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
        return(new StringBuilder("WHY"));
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

                appendHours(rtn);
                /*FileOutputStream fos=null;
                try{
                    fos=openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
                    fos.write((readFile().toString()+rtn).getBytes());
                    fos.close();
                    System.out.println("Saved to "+getFilesDir()+"/"+FILE_NAME);
                }catch(Exception e){
                    e.printStackTrace();
                }*/

                /*try {
                    BufferedWriter writer = new BufferedWriter(
                                                new FileWriter(FILE_NAME, true)
                                            );
                    writer.newLine();
                    writer.write(rtn);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        wageSetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wageInput=(EditText) findViewById(R.id.wageInput);
                newWage=new BigDecimal(wageInput.getText().toString());
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
