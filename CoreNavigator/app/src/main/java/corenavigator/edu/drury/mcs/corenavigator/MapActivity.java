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

public class MapActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
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

    private final LatLng Pearson=new LatLng(37.219146, -93.286636);
    private final LatLng TSC=new LatLng(37.215706, -93.286456);
    private final LatLng Burham=new LatLng(37.218480, -93.286673);
    private final LatLng Hammons=new LatLng(37.215567, -93.285314);

    private PolylineOptions polyline=new PolylineOptions();
    private Polyline realPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();

        spinner_day = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        spinner_schedule = (Spinner)findViewById(R.id.spinner_schedule);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,schedules);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);

        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);

        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.PEARSON, Course.FRIDAY, "poo"));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.MONDAY, "poo" ));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.FRIDAY, "poo" ));
        schedule1.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.FRIDAY, "poo" ));

        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.FRIDAY, "poo" ));
        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.FRIDAY, "poo" ));
        schedule2.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));

        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.PEARSON, Course.FRIDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.HAMMONS, Course.WEDNESDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.TSC, Course.WEDNESDAY, "poo" ));
        schedule3.addCourse(new Course("Bob's Woodshop Class", Course.BURNHAM, Course.FRIDAY, "poo" ));



    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onRoute(View parent){
        //this button does SOMETHINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG
        if(realPolyline!=null){
            realPolyline.remove();
            polyline = new PolylineOptions();
        }

        List<Course> courseresult=currentSchedule.getCourseOnDay(currentDay);

        if(courseresult.size()==0) {
            return;
        }

        LatLng ini = new LatLng(37.221084, -93.285704);

        polyline.add(ini, courseresult.get(0).getBuilding()).width(4).color(Color.RED);


        for(int i=0;i<courseresult.size()-1;i++){
            LatLng building=courseresult.get(i).getBuilding();
            LatLng nextbuilding=courseresult.get(i+1).getBuilding();
            polyline.add(building, nextbuilding).width(4).color(Color.RED);

        }
        realPolyline=mMap.addPolyline(polyline);

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
        LatLng ini = new LatLng(37.221084, -93.285704);
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.221084, -93.285704), 17.0f));
        mMap.addMarker(new MarkerOptions().position(ini).title("Drury"));
        mMap.addMarker(new MarkerOptions().position(Course.BURNHAM).title("Burnham Hall"));
        mMap.addMarker(new MarkerOptions().position(Course.PEARSON).title("Pearsons Hall"));
        mMap.addMarker(new MarkerOptions().position(Course.HAMMONS).title("Hammons School of Architecture"));
        mMap.addMarker(new MarkerOptions().position(Course.TSC).title("Trustee Science Center"));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == spinner_day.getId()) {
            currentDay = (String) parent.getItemAtPosition(position);
        }

        else if(parent.getId() == spinner_schedule.getId()){
            currentSchedule = scheduleList.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //NOTHING
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }
}
