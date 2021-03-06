package edu.drury.mcs.Dnav.FragmentControl;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.drury.mcs.Dnav.JavaClass.Building;
import edu.drury.mcs.Dnav.JavaClass.BuildingInfo_Dialog;
import edu.drury.mcs.Dnav.JavaClass.Contact_Info;
import edu.drury.mcs.Dnav.JavaClass.Course;
import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;
import edu.drury.mcs.Dnav.JavaClass.MyInfoWindowAdapter;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.JavaClass.resource;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DruryMap extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener
        , View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_LOCATION = 666;
    public static final String[] LOC = {"All Buildings", "Buildings", "Parking Lots", "Outdoor Fields", "Residential Building", "Just Map"};
    private DnavDBAdapter dbHelper;
    private SQLiteDatabase Dnav_db;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Spinner marker_selection;
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
    private String[] schedules;

    private XMLController xml;

    //Allows us to associate multiple polylines before we place them
    private ArrayList<Polyline> polylineSet = new ArrayList<>();

    //    private PolylineOptions pLineOptions;
//
//    //The actual polyline to be placed on the map
//    private Polyline realPolyline;
    private View layout;

    private AutoCompleteTextView textView;
    private List<Building> data;
    private Map<String, LatLng> buildingToLatLngMap = new HashMap<>();
    private List<Marker> markerList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        layout = inflater.inflate(R.layout.fragment_drury_map, container, false);

        dbHelper = new DnavDBAdapter(getActivity());
        Dnav_db = dbHelper.getReadOnlyDB();

        data = getData();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                , android.R.layout.simple_dropdown_item_1line, getAllBuildingNames(data));

        textView = (AutoCompleteTextView) layout.findViewById(R.id.building_search);
        textView.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        xml = new XMLController(getActivity());

        scheduleList = xml.getSchedules();

        schedules = new String[scheduleList.size()];

        for (int i = 0; i < scheduleList.size(); i++) {
            schedules[i] = scheduleList.get(i).getName();
        }


        spinner_day = (Spinner) layout.findViewById(R.id.spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter);
        spinner_day.setOnItemSelectedListener(this);

        //initialize schedule spinner
        spinner_schedule = (Spinner) layout.findViewById(R.id.spinner_schedule);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, schedules);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter2);
        spinner_schedule.setOnItemSelectedListener(this);

        FloatingActionButton but = (FloatingActionButton) layout.findViewById(R.id.button_route);
        but.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng ini = new LatLng(37.219499, -93.286032);
        mMap = map;
        //enables location tracking (the little blue blip on the map)
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            mMap.setMyLocationEnabled(true);
        }

        //mMap.setMyLocationEnabled(true);
        //moves the camera over Drury and zooms in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ini, 15.75f));
        //Adds Markers that point to various buildings that are currently used in the routing process and labels them
        setUpMarkers(data);

        //setup spinner for user to chose building markers
        marker_selection = (Spinner) layout.findViewById(R.id.marker_selection);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, LOC);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marker_selection.setAdapter(adapter3);
        marker_selection.setOnItemSelectedListener(this);


        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(getActivity(), data));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //show user a dialog box with info they need
                BuildingInfo_Dialog dialog = new BuildingInfo_Dialog(marker, data);
                dialog.show(getActivity().getSupportFragmentManager(), "show Building Info");
                marker.hideInfoWindow();

            }
        });

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                View view = getActivity().getCurrentFocus();

                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String building_selected = textView.getText().toString();
                    LatLng building_loc = getBuildingLatng(building_selected, data);

                    if (building_loc != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(building_loc));
                        getFocusedMarker(building_loc, markerList).setVisible(true);
                        getFocusedMarker(building_loc, markerList).showInfoWindow();
                    }


                    textView.setText("");

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }

                return false;
            }
        });

        ImageView searchButton = (ImageView) layout.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                String building_selected = textView.getText().toString();
                LatLng building_loc = getBuildingLatng(building_selected, data);

                if (building_loc != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(building_loc));
                    getFocusedMarker(building_loc, markerList).setVisible(true);
                    getFocusedMarker(building_loc, markerList).showInfoWindow();
                }


                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                textView.setText("");
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        System.out.println("Request code: " + requestCode + ". Request Location: " + REQUEST_LOCATION);
        if (requestCode == REQUEST_LOCATION) {
            System.out.println("User answered: " + grantResults[0] + ". Granted permissions = " + PackageManager.PERMISSION_GRANTED);
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                mMap.setMyLocationEnabled(true);

            } else {
                // Permission was denied or request was cancelled
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning!");
                builder.setMessage("We need your location permission for the best user experience");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
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

        if (mMap != null) {

            resetPolyline();
            hideAllMarkers(markerList);

            if (currentSchedule != null) {

                //get the courses that meet the filter criteria for the currently selected schedule and day
                List<Course> courseresult = currentSchedule.getCourseOnDay(currentDay);

                //if the resulting set is empty...
                if (courseresult.size() == 0) {
                    //give the user a toast message
                    System.out.println("There are no courses on the selected day for the given schedule");


                } else {//Draw some polylines

                /*This is the Initial Coordinate that exists in all of our polylines. It points to the the Drury
                Lane circle and is labeled as "Drury" currently.*/
                    LatLng ini = new LatLng(37.221084, -93.285704);

                    ArrayList<Course> courseErrorList = new ArrayList<>();
                    ArrayList<LatLng> courseLocationList = new ArrayList<>();

                    for (Course c : courseresult) {
                        LatLng buildingLocation = buildingToLatLngMap.get(c.getLocation());
                        if (buildingLocation == null) {
                            courseErrorList.add(c);
                        } else {
                            getFocusedMarker(buildingLocation, markerList).setVisible(true);
                        }
                        courseLocationList.add(buildingLocation);
                    }

                    if (courseErrorList.size() > 0 || courseLocationList.contains(null)) {
                        String errorString = "Schedule \"" + currentSchedule.getName() + "\" has invalid locations in the following courses: ";

                        for (Course errorCourse : courseErrorList) {
                            errorString += "\"" + errorCourse.getName() + "\" ";
                        }
                        errorString += "\n\nPlease delete and remake these courses and then select a location from the dropdown.";

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Error!");
                        builder.setMessage(errorString);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        System.out.println(errorString);
                        return;
                    }


                    //Set up polyline options
                    PolylineOptions polyOptions = new PolylineOptions();
                    polyOptions.addAll(courseLocationList);
                    polyOptions.width(4);
                    polyOptions.color(Color.DKGRAY);

                    //add the polylines to the map and to our list
                    polylineSet.add(mMap.addPolyline(polyOptions));


//                //Loops through each course in the course list and adds a polyline between each pair of buildings
//                for (int i = 0; i < courseresult.size() - 1; i++) {
//                    LatLng building = buildingToLatLngMap.get(courseresult.get(i).getLocation());
//                    LatLng nextbuilding = buildingToLatLngMap.get(courseresult.get(i + 1).getLocation());
//                    polylineSet.add(mMap.addPolyline(new PolylineOptions().add(building, nextbuilding).width(4).color(Color.DKGRAY)));
//                }
//
//                    //Adds the polyline to the map and assigns it to the realPolyline handle so we can remove it later
                }
            }
        }
    }

    private void resetPolyline() {
        //if there is already a polyline....
        if (polylineSet.size() > 0) {
            for (Polyline polyLine : polylineSet) {
                polyLine.remove();
            }

            polylineSet.clear();
        }
    }

    @Override
    /**
     * Method called when an item is selected from a dropdown spinner. Behaves differently based on
     * which spinner is being used.
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //if the spinner is the day filter spinner...
        if (parent.getId() == spinner_day.getId()) {
            //set currentDay to their selection
            currentDay = (String) parent.getItemAtPosition(position);
        }
        //otherwise, if the spinner is the schedule filter spinner...
        else if (parent.getId() == spinner_schedule.getId()) {
            //set the currentSchedule to their selection
            currentSchedule = scheduleList.get(position);
        } else if (parent.getId() == marker_selection.getId()) {

            if (position == 0) {
                hideAllMarkers(markerList);
                resetPolyline();
                ShowAllMarkers(markerList);
            } else {
                hideAllMarkers(markerList);
                resetPolyline();
                showSelectedMarkers(markerList, position);
            }

        }
    }

    @Override
    /**
     * Currently doesn't do anything, not necessary for our current iteration of the app, but
     * required by the OnItemSelectedListener interface.
     */
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public List<Building> getData() {
        List<Building> building_Data = new ArrayList<>();

        String query_building = "SELECT "
                + DnavDBAdapter.LID + ", "
                + DnavDBAdapter.LNAME + ", "
                + DnavDBAdapter.LDESCRIPTION + ", "
                + DnavDBAdapter.LTYPE + ", "
                + DnavDBAdapter.LLAT + ", "
                + DnavDBAdapter.LLNG + " "
                + "FROM "
                + DnavDBAdapter.TABLE_LANDMARKS;

//        String query_building = "select landmark_id, name, description, type, lat, lng from landmarks";

        Cursor cursor_buildings = Dnav_db.rawQuery(query_building, null);

        while (cursor_buildings.moveToNext()) {
            String parameterArray[] = new String[1];
            parameterArray[0] = String.valueOf(cursor_buildings.getInt(cursor_buildings.getColumnIndex(DnavDBAdapter.LID)));
            String query_resources = "SELECT resource_id, name, description FROM resources WHERE landmark_id = ?";
            Cursor cursor_resources = Dnav_db.rawQuery(query_resources, parameterArray);
            ArrayList<resource> resource_data = new ArrayList<>();

            while (cursor_resources.moveToNext()) {
                String query_contacts = "Select contact_id, name, phone, email, address FROM contacts where resource_id = ?";

                String parameterArray2[] = new String[1];
                parameterArray2[0] = String.valueOf(cursor_resources.getInt(cursor_resources.getColumnIndex(DnavDBAdapter.RID)));

                Cursor cursor_contacts = Dnav_db.rawQuery(query_contacts, parameterArray2);

                ArrayList<Contact_Info> contact_data = new ArrayList<>();
                while (cursor_contacts.moveToNext()) {
                    contact_data.add(new Contact_Info(cursor_contacts.getString(cursor_contacts.getColumnIndex(DnavDBAdapter.CNAME)), cursor_contacts.getString(cursor_contacts.getColumnIndex(DnavDBAdapter.CPHONE)), cursor_contacts.getString(cursor_contacts.getColumnIndex(DnavDBAdapter.CEMAIL)), cursor_contacts.getString(cursor_contacts.getColumnIndex(DnavDBAdapter.CADDRESS)), cursor_contacts.getInt(cursor_contacts.getColumnIndex(DnavDBAdapter.CID))));
                }

                if (contact_data.size() == 1) {
                    resource_data.add(new resource(cursor_resources.getString(cursor_resources.getColumnIndex(DnavDBAdapter.RNAME)), cursor_resources.getString(cursor_resources.getColumnIndex(DnavDBAdapter.RDESCRIPTION)), contact_data.get(0), cursor_resources.getInt(cursor_resources.getColumnIndex(DnavDBAdapter.RID))));
                }
                if (contact_data.size() >= 2) {
                    resource_data.add(new resource(cursor_resources.getString(cursor_resources.getColumnIndex(DnavDBAdapter.RNAME)), cursor_resources.getString(cursor_resources.getColumnIndex(DnavDBAdapter.RDESCRIPTION)), contact_data.get(0), contact_data.get(1), cursor_resources.getInt(cursor_resources.getColumnIndex(DnavDBAdapter.RID))));
                }
            }

            LatLng latLng = new LatLng(cursor_buildings.getDouble(4), cursor_buildings.getDouble(5));
            building_Data.add(new Building(cursor_buildings.getString(cursor_buildings.getColumnIndex(DnavDBAdapter.LNAME)), cursor_buildings.getString(cursor_buildings.getColumnIndex(DnavDBAdapter.LDESCRIPTION)), cursor_buildings.getInt(cursor_buildings.getColumnIndex(DnavDBAdapter.LTYPE)), cursor_buildings.getInt(cursor_buildings.getColumnIndex(DnavDBAdapter.LID)), latLng, resource_data));

            //convenience structure that is used when a course is associated with a location on campus
            buildingToLatLngMap.put(cursor_buildings.getString(cursor_buildings.getColumnIndex(DnavDBAdapter.LNAME)), latLng);
        }

        return building_Data;
    }

    public String[] getAllBuildingNames(List<Building> buildingData) {
        String[] names = new String[buildingData.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = buildingData.get(i).getBuilding_name();
        }
        return names;
    }

    public LatLng getBuildingLatng(String building_name, List<Building> buildingData) {
        LatLng location = null;
        for (Building i : buildingData) {
            if (building_name.equals(i.getBuilding_name())) {
                location = i.getLocation();
            }
        }
        return location;
    }

    public Marker getFocusedMarker(LatLng location, List<Marker> markerList) {
        Marker marker = null;
        for (Marker i : markerList) {
            if (i.getPosition().equals(location)) {
                marker = i;
            }
        }
        return marker;
    }

    public void setUpMarkers(List<Building> data) {
        Marker currentMarker = null;
        //Adds Markers that point to various buildings that are currently used in the routing process and labels them
        for (Building i : data) {
            if (i.getType() == 1) {
                currentMarker = mMap.addMarker(new MarkerOptions().position(i.getLocation())
                        .title("1"));
                currentMarker.setVisible(false);
            } else if (i.getType() == 2) {
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(i.getLocation())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title("2"));
                currentMarker.setVisible(false);
            } else if (i.getType() == 3) {
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(i.getLocation())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title("3"));
                currentMarker.setVisible(false);
            } else if (i.getType() == 4) {
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(i.getLocation())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        .title("4"));
            }
            markerList.add(currentMarker);
        }
    }

    public void showSelectedMarkers(List<Marker> markerList, int type) {
        for (Marker i : markerList) {
            if (i.getTitle().equals(Integer.toString(type))) {
                i.setVisible(true);
            }
        }
    }

    public void hideAllMarkers(List<Marker> markerList) {
        for (Marker i : markerList) {
            i.setVisible(false);
        }
    }

    public void ShowAllMarkers(List<Marker> markerList) {
        for (Marker i : markerList) {
            i.setVisible(true);
        }
    }

}
