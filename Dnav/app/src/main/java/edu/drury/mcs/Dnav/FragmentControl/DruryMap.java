package edu.drury.mcs.Dnav.FragmentControl;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.drury.mcs.Dnav.JavaClass.Course;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DruryMap extends Fragment implements OnMapReadyCallback,AdapterView.OnItemSelectedListener, View.OnClickListener {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    //Spinner dropdown for the day filter
    private Spinner spinner_day;
    //default value
    private String currentDay = Course.MONDAY;
    //all possible values
    private final String[] days = {Course.MONDAY, Course.TUESDAY, Course.WEDNESDAY, Course.THURSDAY,
            Course.FRIDAY, Course.SATURDAY, Course.SUNDAY};

    //Spinner dropdown for the schedule filter
    private Spinner spinner_schedule;

    //All the schedules placed into a list so that we can select them
    private List<Schedule> scheduleList;
    //default schedule
    private Schedule currentSchedule;
    //display values for the schedules
    private String[]schedules;

    private XMLController xml;

    //Allows us to associate multiple polylines before we place them
    private PolylineOptions polylineSet;
    //The actual polyline to be placed on the map
    private Polyline realPolyline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drury_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        xml = new XMLController(getActivity());

        scheduleList = xml.getSchedules();

        schedules = new String[scheduleList.size()];

        for(int i=0; i<scheduleList.size(); i++)
        {
            schedules[i]=scheduleList.get(i).getName();
        }

        spinner_day = (Spinner)getActivity().findViewById(R.id.spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        //initialize schedule spinner
        spinner_schedule = (Spinner)getActivity().findViewById(R.id.spinner_schedule);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, schedules);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);

        Button but = (Button) getActivity().findViewById(R.id.button_route);
        but.setOnClickListener(this);

        polylineSet = new PolylineOptions();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng ini = new LatLng(37.221084, -93.285704);
        //enables location tracking (the little blue blip on the map)
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap = map;
        map.setMyLocationEnabled(true);
        //moves the camera over Drury and zooms in
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ini, 17.0f));

        //Adds Markers that point to various buildings that are currently used in the routing process and labels them
        //Adds Markers that point to various buildings that are currently used in the routing process and labels them
        map.addMarker(new MarkerOptions().position(ini).title("FSC Circle: Default Assumed Starting Position"));
        map.addMarker(new MarkerOptions().position(Course.BURNHAMLATLNG).title(Course.BURNHAM));
        map.addMarker(new MarkerOptions().position(Course.PEARSONLATLNG).title(Course.PEARSONS));
        map.addMarker(new MarkerOptions().position(Course.HAMMONSLATLNG).title(Course.HSA));
        map.addMarker(new MarkerOptions().position(Course.TSCLATLNG).title(Course.TSC));

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
    public void onClick(View parent) {

        //if there is already a polyline....
        if (realPolyline != null) {
            //remove it
            realPolyline.remove();
            //and reset the list of coordinates to a blank slate
            polylineSet = new PolylineOptions();
        }

        //get the courses that meet the filter criteria for the currently selected schedule and day
        List<Course> courseresult = currentSchedule.getCourseOnDay(currentDay);

        //if the resulting set is empty...
        if (courseresult.size() == 0) {
            //simply break out of the method call
            return;
        }

        /*This is the Initial Coordinate that exists in all of our polylines. It points to the the Drury
         Lane circle and is labeled as "Drury" currently.*/
        LatLng ini = new LatLng(37.221084, -93.285704);

        Map<String, LatLng> buildingToLatLngMap = Course.generateBuildingToLatLngMap();

        //Adds the first line from the initial point to the first building in the course list
         polylineSet.add(ini, buildingToLatLngMap.get(courseresult.get(0).getLocation())).width(4).color(Color.RED);

        //Loops through each course in the course list and adds a polyline between each pair of buildings
        for (int i = 0; i < courseresult.size() - 1; i++) {
            LatLng building = buildingToLatLngMap.get(courseresult.get(i).getLocation());
            LatLng nextbuilding = buildingToLatLngMap.get(courseresult.get(i + 1).getLocation());
            polylineSet.add(building, nextbuilding).width(4).color(Color.RED);
        }

        //Adds the polyline to the map and assigns it to the realPolyline handle so we can remove it later
        realPolyline=mMap.addPolyline(polylineSet);

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
    public void onNothingSelected(AdapterView<?> adapterView) {

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