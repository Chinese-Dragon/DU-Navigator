package edu.drury.mcs.Dnav.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import edu.drury.mcs.Dnav.JavaClass.Course;
import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;
import edu.drury.mcs.Dnav.JavaClass.Message;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

public class GenerateCourse extends AppCompatActivity implements View.OnClickListener {

    private static ArrayList<String> buildingArray;
    private SQLiteDatabase db;

    public final static String EXTRA_ANOTEHRSCHE = "edu.drury.mcs.Dnav.ANOTEHRSCHE";

    private Button buttonDone;
    private Button buttonAdd;
    private Button buttonCancel;
    private Button buttonStart;
    private Button buttonEnd;
    private TextView startTimeField;
    private TextView endTimeField;
    private Schedule sched;
    private int cHour,cMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_course);

        Toolbar toolbar = (Toolbar) findViewById(edu.drury.mcs.Dnav.R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeBuildingList();

        Spinner buildingSpinner = (Spinner) findViewById(R.id.SpinnerBuilding);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, buildingArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter2);

        buttonDone = (Button) findViewById(R.id.ButtonDone);
        buttonDone.setOnClickListener(this);

        buttonAdd = (Button) findViewById(R.id.ButtonAddCourse);
        buttonAdd.setOnClickListener(this);

        buttonCancel = (Button) findViewById(R.id.ButtonCancel);
        buttonCancel.setOnClickListener(this);

        buttonStart = (Button) findViewById(R.id.pick_start);
        buttonStart.setOnClickListener(this);

        buttonEnd = (Button) findViewById(R.id.pick_end);
        buttonEnd.setOnClickListener(this);




    }

    private void initializeBuildingList() {
        buildingArray = new ArrayList<>();

        db = (new DnavDBAdapter(this)).getReadOnlyDB();

        Cursor cursor = db.rawQuery("Select " + DnavDBAdapter.LNAME + " from " + DnavDBAdapter.TABLE_LANDMARKS, null);

        while (cursor.moveToNext()) {
            buildingArray.add(cursor.getString(cursor.getColumnIndex(DnavDBAdapter.LNAME)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        // Acquire data from EditText fields and spinner
        final EditText courseNameField = (EditText) findViewById(R.id.EditTextCourseName);
        String courseName = courseNameField.getText().toString();
        final EditText courseCodeField = (EditText) findViewById(R.id.EditTextCourseCode);
        String courseCode = courseCodeField.getText().toString();
        final Spinner buildingSpinner = (Spinner) findViewById(R.id.SpinnerBuilding);
        String building = buildingSpinner.getSelectedItem().toString();
        final EditText roomNumberField = (EditText) findViewById(R.id.EditTextRoomNumber);
        String roomNumber = roomNumberField.getText().toString();

        // Check which of the Day checkboxes are checked
        final CheckBox sundayCheckbox = (CheckBox) findViewById(R.id.CheckBoxSunday);
        boolean sunday = sundayCheckbox.isChecked();
        final CheckBox mondayCheckbox = (CheckBox) findViewById(R.id.CheckBoxMonday);
        boolean monday = mondayCheckbox.isChecked();
        final CheckBox tuesdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxTuesday);
        boolean tuesday = tuesdayCheckbox.isChecked();
        final CheckBox wednesdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxWednesday);
        boolean wednesday = wednesdayCheckbox.isChecked();
        final CheckBox thursdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxThursday);
        boolean thursday = thursdayCheckbox.isChecked();
        final CheckBox fridayCheckbox = (CheckBox) findViewById(R.id.CheckBoxFriday);
        boolean friday = fridayCheckbox.isChecked();
        final CheckBox saturdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxSaturday);
        boolean saturday = saturdayCheckbox.isChecked();

        // Make a string to say which of the days are checked
        String days = "";
        if (sunday) days += Course.SUNDAY;
        if (monday) days += Course.MONDAY;
        if (tuesday) days += Course.TUESDAY;
        if (wednesday) days += Course.WEDNESDAY;
        if (thursday) days += Course.THURSDAY;
        if (friday) days += Course.FRIDAY;
        if (saturday) days += Course.SATURDAY;

        // Acquire data from the last few EditText fields
        startTimeField = (TextView) findViewById(R.id.EditTextStartTime);
        final String startTime = startTimeField.getText().toString();
        endTimeField = (TextView) findViewById(R.id.EditTextEndTime);
        String endTime = endTimeField.getText().toString();
        final EditText professorField = (EditText) findViewById(R.id.EditTextProfessor);
        String professor = professorField.getText().toString();

        // Generate a unique course ID based on epoch time
        String courseID = "C"; // "C" is for "course"!
        long epochTime = System.currentTimeMillis() / 1000; // epoch time in seconds
        courseID += (Long.toString(epochTime) + new Random().nextInt(100000));
        // Create course object
        Course c = new Course(courseID, courseName, courseCode, building, days, startTime, endTime, roomNumber, professor);

        XMLController xcont = new XMLController(this);
        sched = (Schedule) getIntent().getExtras().getSerializable(LookUpSchedule.EXTRA_CURRENTSCHE);

        if (view.getId() == buttonDone.getId()) {
            if (!courseName.equals("")) {
                if (!startTime.equals("")) {
                    if (!endTime.equals("")) {
                        if(monday||tuesday||wednesday||thursday||friday||saturday||sunday){
                            View fview = getCurrentFocus();
                            sched.addCourse(c);
                            xcont.editSchedule(sched);

                            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(fview.getWindowToken(), 0);
                            finish();
                        }else{
                            Message.message(this, "Required Course Days");
                        }
                    } else {
                        Message.message(this, "Required Course End Time");
                    }
                } else {
                    Message.message(this, "Required Course Start Time");
                }
            } else {
                Message.message(this, "Required Course Name");
            }
        } else if (view.getId() == buttonAdd.getId()) {
            if (!courseName.equals("")) {
                if (!startTime.equals("")) {
                    if (!endTime.equals("")) {
                        View fview = getCurrentFocus();
                        sched.addCourse(c);
                        xcont.editSchedule(sched);
                        Intent intent = new Intent(this, GenerateCourse.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(LookUpSchedule.EXTRA_CURRENTSCHE, sched);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(fview.getWindowToken(), 0);
                        finish();
                    } else {
                        Message.message(this, "Required Course End Time");
                    }
                } else {
                    Message.message(this, "Required Course Start Time");
                }
            } else {
                Message.message(this, "Required Course Name");
            }
        }else if(view.getId() == buttonCancel.getId()){
            View fview = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(fview.getWindowToken(), 0);
            finish();
        }else if (view.getId() == buttonStart.getId()){
            final Calendar calendar = Calendar.getInstance();
            cHour = calendar.get(Calendar.HOUR_OF_DAY);
            cMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hrStr = "";
                    String minStr = "";
                    String ampm = "";
                    if(minute <=9)
                        minStr = "0"+minute;
                    else
                        minStr = ""+minute;

                    if(hourOfDay == 0){
                        hrStr = "" + 12;
                        ampm = "AM";
                    }

                    else if (hourOfDay==12){
                        hrStr = "" + hourOfDay;
                        ampm = "PM";
                    }
                    else if (hourOfDay>12) {
                        hrStr = "" + (hourOfDay - 12);
                        ampm = "PM";
                    }
                    else {
                        hrStr = "" + hourOfDay;
                        ampm = "AM";
                    }

                    startTimeField.setText(hrStr + ":" + minStr + " " + ampm);

                }
            },cHour,cMinute,false);

            timePickerDialog.show();

        }else if(view.getId() == buttonEnd.getId()){
            final Calendar calendar = Calendar.getInstance();
            cHour = calendar.get(Calendar.HOUR_OF_DAY);
            cMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hrStr = "";
                    String minStr = "";
                    String ampm = "";
                    if(minute <=9)
                        minStr = "0"+minute;
                    else
                        minStr = ""+minute;


                    if(hourOfDay == 0){
                        hrStr = "" + 12;
                        ampm = "AM";
                    }

                    else if (hourOfDay==12) {
                        hrStr = "" + hourOfDay;
                        ampm = "PM";
                    }
                    else if (hourOfDay>12) {
                        hrStr = "" + (hourOfDay - 12);
                        ampm = "PM";
                    }
                    else {
                        hrStr = "" + hourOfDay;
                        ampm = "AM";
                    }

                    endTimeField.setText(hrStr + ":" + minStr + " " + ampm);

                }
            },cHour,cMinute,false);

            timePickerDialog.show();
        }
    }
}
