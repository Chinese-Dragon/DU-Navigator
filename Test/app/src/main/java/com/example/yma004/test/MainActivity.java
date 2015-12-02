package com.example.yma004.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_day;
    private String currentDay = "Monday";
    private static final String[]paths = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};

    private Spinner spinner_schedule;
    private String currentSchedule = "1";
    private static final String[]schedules = {"1", "2", "3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_day = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        spinner_schedule = (Spinner)findViewById(R.id.spinner_schedule);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,schedules);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        if(parent.getId() == spinner_day.getId()) {
            currentDay = (String) parent.getItemAtPosition(position);
        }

        else if(parent.getId() == spinner_schedule.getId()){
            currentSchedule = (String) parent.getItemAtPosition(position);
        }
    }

    public void onNothingSelected(AdapterView<?> parent){


    }
}
