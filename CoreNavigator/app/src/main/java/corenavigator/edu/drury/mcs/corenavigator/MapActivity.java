package corenavigator.edu.drury.mcs.corenavigator;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for our single screen in our app currently. Handles the GoogleMap and the inputs on the screen.
 */
public class MapActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    //Spinner dropdown for the day filter
    private Spinner spinner_day;
    //default value
    private String currentDay = Course.MONDAY;
    //all possible values
    private final String[] days = {Course.MONDAY, Course.TUESDAY, Course.WEDNESDAY, Course.THURSDAY, Course.FRIDAY, Course.SATURDAY, Course.SUNDAY};

    //Spinner dropdown for the schedule filter
    private Spinner spinner_schedule;
    //Three hard-coded schedules for the sake of Sprint 1
    private Schedule schedule1 = new Schedule("Schedule1");
    private Schedule schedule2 = new Schedule("Schedule2");
    private Schedule schedule3 = new Schedule("Schedule3");
    //All the schedules placed into a list so that we can select them
    private ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
    //default schedule
    private Schedule currentSchedule = schedule1;
    //display values for the schedules
    private final String[]schedules = {schedule1.getName(), schedule2.getName(), schedule3.getName()};

    //Allows us to associate multiple polylines before we place them
    private PolylineOptions polylineSet =new PolylineOptions();
    //The actual polyline to be placed on the map
    private Polyline realPolyline;

    @Override
    /**
     * Method called when the activity is first created. A lot of initialization and setup occurs here.
     */
    protected void onCreate(Bundle savedInstanceState) {
        //initial setup common to all activities
        super.onCreate(savedInstanceState);

        //associates this Java controller with the map view
        setContentView(R.layout.activity_map);
        //Sets up the map
        setUpMapIfNeeded();

        //initialize day spinner
        spinner_day = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        //initialize schedule spinner
        spinner_schedule = (Spinner)findViewById(R.id.spinner_schedule);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schedules);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);

        //add schedules to list of schedules
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);

        //Hard-coding all the courses for the first schedule
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.PEARSON, Course.FRIDAY, "poo"));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.MONDAY, "poo" ));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.FRIDAY, "poo" ));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.FRIDAY, "poo" ));

        //Hard-coding all the courses for the second schedule
        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.FRIDAY, "poo" ));
        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.FRIDAY, "poo" ));
        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));

        //Hard-coding all the courses for the third schedule
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.PEARSON, Course.FRIDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.WEDNESDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.WEDNESDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));



    }

    @Override
    /**
     * Method called when this screen is resumed after suspension. Largely unused in the current sprint
     * as we only have one screen and need no special behaviors after resuming as a result.
     */
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Method called when the user hits the "ROUTE" button on the screen. Pulls info from the spinners/dropdowns
     * and filters the schedule based on the given criteria. Then takes the building coordinates from each course
     * in the filtered schedule and builds a polyline on the map. If there is already a polyline, it is removed
     * before placing a new one. If the spinner filters result in no courses, a polyline is simply not placed
     * (but any polylines already placed will be removed, so as to not confuse the user).
     *
     * @param parent - Required parameter for the method. This is a View object, more specifically in this case,
     *               it is the ROUTE button. There is no relevant information in the route button, so this
     *               parameter goes unused in our implementation of this method.
     */
    public void onRoute(View parent){

        //if there is already a polyline....
        if(realPolyline!=null){
            //remove it
            realPolyline.remove();
            //and reset the list of coordinates to a blank slate
            polylineSet = new PolylineOptions();
        }

        //get the courses that meet the filter criteria for the currently selected schedule and day
        List<Course> courseresult=currentSchedule.getCourseOnDay(currentDay);

        //if the resulting set is empty...
        if(courseresult.size()==0) {
            //simply break out of the method call
            return;
        }

        /*This is the Initial Coordinate that exists in all of our polylines. It points to the the Drury
         Lane circle and is labeled as "Drury" currently.*/
        LatLng ini = new LatLng(37.221084, -93.285704);

        //Adds the first line from the initial point to the first building in the course list
        polylineSet.add(ini, courseresult.get(0).getBuilding()).width(4).color(Color.RED);

        //Loops through each course in the course list and adds a polyline between each pair of buildings
        for(int i=0;i<courseresult.size()-1;i++){
            LatLng building=courseresult.get(i).getBuilding();
            LatLng nextbuilding=courseresult.get(i+1).getBuilding();
            polylineSet.add(building, nextbuilding).width(4).color(Color.RED);

        }

        //Adds the polyline to the map and assigns it to the realPolyline handle so we can remove it later
        realPolyline=mMap.addPolyline(polylineSet);

    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //Initial Marker position, points to Drury Lane circle (in front of FSC)
        LatLng ini = new LatLng(37.221084, -93.285704);
        //enables location tracking (the little blue blip on the map)
        mMap.setMyLocationEnabled(true);
        //moves the camera over Drury and zooms in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.221084, -93.285704), 17.0f));

        //Adds Markers that point to various buildings that are currently used in the routing process and labels them
        mMap.addMarker(new MarkerOptions().position(ini).title("Drury"));
        mMap.addMarker(new MarkerOptions().position(Course.BURNHAM).title("Burnham Hall"));
        mMap.addMarker(new MarkerOptions().position(Course.PEARSON).title("Pearsons Hall"));
        mMap.addMarker(new MarkerOptions().position(Course.HAMMONS).title("Hammons School of Architecture"));
        mMap.addMarker(new MarkerOptions().position(Course.TSC).title("Trustee Science Center"));

    }


    @Override
    /**
     * Method called when an item is selected from a dropdown spinner. Behaves differently based on
     * which spinner is being used.
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //if the spinner is the day filter spinner...
        if(parent.getId() == spinner_day.getId()) {
            //set currentDay to their selection
            currentDay = (String) parent.getItemAtPosition(position);
        }
        //otherwise, if the spinner is the schedule filter spinner...
        else if(parent.getId() == spinner_schedule.getId()){
            //set the currentSchedule to their selection
            currentSchedule = scheduleList.get(position);
        }
    }

    @Override
    /**
     * Currently doesn't do anything, not necessary for our current iteration of the app, but
     * required by the OnItemSelectedListener interface.
     */
    public void onNothingSelected(AdapterView<?> parent) {
        //NOTHING
    }

    /**
     * Getter for the current day. Not used in current iteration of App because there is only one
     * activity, but may be useful later if we end up splitting up the functionalities.
     * @return
     */
    public String getCurrentDay() {
        return currentDay;
    }
    /**
     * Getter for the current schedule. Not used in current iteration of App because there is only one
     * activity, but may be useful later if we end up splitting up the functionalities.
     * @return
     */
    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }
}
