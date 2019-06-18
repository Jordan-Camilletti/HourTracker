package com.example.hourtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.io.FileOutputStream;

public class RemoveScreen extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

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

        //https://www.youtube.com/watch?v=3Zrwi3FFrC8
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();
    }
}
