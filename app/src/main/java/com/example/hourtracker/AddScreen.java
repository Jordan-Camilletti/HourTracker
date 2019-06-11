package com.example.hourtracker;

import android.content.SharedPreferences;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class AddScreen extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText wageInput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        wageInput=(EditText) findViewById(R.id.wageInput);

        //https://www.youtube.com/watch?v=3Zrwi3FFrC8
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();
    }
}
