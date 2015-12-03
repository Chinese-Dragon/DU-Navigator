package edu.drury.mcs.corenavigator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class RouteActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner_day;
    private String currentDay = Course.MONDAY;
    private final String[]paths = {Course.MONDAY, Course.TUESDAY, Course.WEDNESDAY, Course.THURSDAY, Course.FRIDAY, Course.SATURDAY, Course.SUNDAY};

    private Spinner spinner_schedule;
    private Schedule schedule1 = new Schedule("Schedule1");
    private Schedule schedule2 = new Schedule("Schedule2");
    private Schedule schedule3 = new Schedule("Schedule3");
    private ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
    private Schedule currentSchedule = schedule1;
    private final String[]schedules = {schedule1.getName(), schedule2.getName(), schedule3.getName()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        spinner_day = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RouteActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        spinner_schedule = (Spinner)findViewById(R.id.spinner_schedule);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(RouteActivity.this,
                android.R.layout.simple_spinner_item,schedules);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);

        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
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
            currentSchedule = scheduleList.get(position);
        }
    }

    public void onNothingSelected(AdapterView<?> parent){
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }
}

