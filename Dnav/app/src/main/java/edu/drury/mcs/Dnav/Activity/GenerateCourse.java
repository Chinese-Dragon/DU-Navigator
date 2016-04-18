package edu.drury.mcs.Dnav.Activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import edu.drury.mcs.Dnav.FragmentControl.ScheduleName_Dialog;
import edu.drury.mcs.Dnav.JavaClass.Course;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

public class GenerateCourse extends AppCompatActivity implements View.OnClickListener {

    public static final String[] buildingArray = {Course.BURNHAM, Course.PEARSONS, Course.OLIN, Course.LAY,
            Course.MABEE, Course.BREECH, Course.SHEWMAKER, Course.TSC, Course.HSA, Course.CONGREGATIONAL,
            Course.PAC, Course.FSC, Course.LAWENFORCEMENT};

    public final static String EXTRA_ANOTEHRSCHE = "edu.drury.mcs.Dnav.ANOTEHRSCHE";

    private Button buttonDone;
    private Button buttonAdd;
    private Schedule sched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_course);

        Toolbar toolbar = (Toolbar) findViewById(edu.drury.mcs.Dnav.R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner buildingSpinner = (Spinner) findViewById(R.id.SpinnerBuilding);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, buildingArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter2);

        buttonDone = (Button) findViewById(R.id.ButtonDone);
        buttonDone.setOnClickListener(this);

        buttonAdd = (Button) findViewById(R.id.ButtonAddCourse);
        buttonAdd.setOnClickListener(this);

        
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

    /**
     * addCourse takes all the data from the form and enters it into the schedule through the
     * XML Controller.
     * //   * @param button The button that is pressed
     *
     * @return
     */
    //  public void addCourse(View button){
//        // Acquire data from EditText fields and spinner
//        final EditText courseNameField = (EditText) findViewById(R.id.EditTextCourseName);
//        String courseName = courseNameField.getText().toString();
//        final EditText courseCodeField = (EditText) findViewById(R.id.EditTextCourseCode);
//        String courseCode = courseCodeField.getText().toString();
//        final Spinner buildingSpinner = (Spinner) findViewById(R.id.SpinnerBuilding);
//        String building = buildingSpinner.getSelectedItem().toString();
//        final EditText roomNumberField = (EditText) findViewById(R.id.EditTextRoomNumber);
//        String roomNumber = roomNumberField.getText().toString();
//
//        // Check which of the Day checkboxes are checked
//        final CheckBox sundayCheckbox = (CheckBox) findViewById(R.id.CheckBoxSunday);
//        boolean sunday = sundayCheckbox.isChecked();
//        final CheckBox mondayCheckbox = (CheckBox) findViewById(R.id.CheckBoxMonday);
//        boolean monday = mondayCheckbox.isChecked();
//        final CheckBox tuesdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxTuesday);
//        boolean tuesday = tuesdayCheckbox.isChecked();
//        final CheckBox wednesdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxWednesday);
//        boolean wednesday = wednesdayCheckbox.isChecked();
//        final CheckBox thursdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxThursday);
//        boolean thursday = thursdayCheckbox.isChecked();
//        final CheckBox fridayCheckbox = (CheckBox) findViewById(R.id.CheckBoxFriday);
//        boolean friday = fridayCheckbox.isChecked();
//        final CheckBox saturdayCheckbox = (CheckBox) findViewById(R.id.CheckBoxSaturday);
//        boolean saturday = saturdayCheckbox.isChecked();
//
//        // Make a string to say which of the days are checked
//        String days = "";
//        if(sunday) days += Course.SUNDAY;
//        if(monday) days += Course.MONDAY;
//        if(tuesday) days += Course.TUESDAY;
//        if(wednesday) days += Course.WEDNESDAY;
//        if(thursday) days += Course.THURSDAY;
//        if(friday) days += Course.FRIDAY;
//        if(saturday) days += Course.SATURDAY;
//
//        // Acquire data from the last few EditText fields
//        final EditText startTimeField = (EditText) findViewById(R.id.EditTextStartTime);
//        String startTime = startTimeField.getText().toString();
//        final EditText endTimeField = (EditText) findViewById(R.id.EditTextEndTime);
//        String endTime = endTimeField.getText().toString();
//        final EditText professorField = (EditText) findViewById(R.id.EditTextProfessor);
//        String professor = professorField.getText().toString();
//
//        // Generate a unique course ID based on epoch time
//        String courseID = "C"; // "C" is for "course"!
//        long epochTime = System.currentTimeMillis()/1000; // epoch time in seconds
//        courseID += Long.toString(epochTime);
//        // Create course object
//        Course c = new Course(courseID, courseName, building, days, startTime, endTime, roomNumber, professor);
//
//
//
////        TODO:
////        Find how to get the current schedule
////            *this will likely depend on how we create a schedule.
//
//        XMLController xcont = new XMLController(this);
//        Schedule sched = (Schedule) getIntent().getExtras().getSerializable(ScheduleName_Dialog.EXTRA_SCHEDULE);
//        sched.addCourse(c);
//        xcont.editSchedule(sched);
//    }
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
        final EditText startTimeField = (EditText) findViewById(R.id.EditTextStartTime);
        String startTime = startTimeField.getText().toString();
        final EditText endTimeField = (EditText) findViewById(R.id.EditTextEndTime);
        String endTime = endTimeField.getText().toString();
        final EditText professorField = (EditText) findViewById(R.id.EditTextProfessor);
        String professor = professorField.getText().toString();

        // Generate a unique course ID based on epoch time
        String courseID = "C"; // "C" is for "course"!
        long epochTime = System.currentTimeMillis() / 1000; // epoch time in seconds
        courseID += (Long.toString(epochTime)+ new Random().nextInt(100000));
        // Create course object
        Course c = new Course(courseID, courseName, courseCode, building, days, startTime, endTime, roomNumber, professor);

        XMLController xcont = new XMLController(this);
        sched = (Schedule) getIntent().getExtras().getSerializable(LookUpSchedule.EXTRA_CURRENTSCHE);

        sched.addCourse(c);
        xcont.editSchedule(sched);


        finish();

        if (view.getId() == buttonDone.getId()) {
            finish();
        } else if (view.getId() == buttonAdd.getId()) {

            Intent intent = new Intent(this, GenerateCourse.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(LookUpSchedule.EXTRA_CURRENTSCHE, sched);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
