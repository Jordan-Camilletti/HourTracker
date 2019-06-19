package com.example.hourtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;

public class RemoveScreen extends AppCompatActivity {

    private Button backButton;
    private Button clearAllButton;
    private Button removeButton;
    private EditText paidInput;

    private String FILE_NAME="hours.txt";

    public void resetFile(String FILE_NAME){//Used to reset the contents of "hours.txt" because I'm an idiot
        try{
            FileOutputStream fos=openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
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
    }
}
