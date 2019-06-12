package com.example.hourtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

public class AddScreen extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText wageInput;
    private Button wageSetButton;
    private Button backButton;
    private BigDecimal newWage=new BigDecimal("12.50");//Wage I'm paid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        wageInput=(EditText) findViewById(R.id.wageInput);
        wageSetButton=(Button) findViewById(R.id.wageSetButton);
        backButton=(Button) findViewById(R.id.backButton);

        //https://www.youtube.com/watch?v=3Zrwi3FFrC8
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        wageSetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wageInput=(EditText) findViewById(R.id.wageInput);
                newWage=new BigDecimal(wageInput.getText().toString());
                System.out.println(newWage);
                mEditor.putString("Wage",wageInput.getText().toString());
                mEditor.commit();
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
