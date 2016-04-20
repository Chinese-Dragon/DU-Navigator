package edu.drury.mcs.Dnav.JavaClass;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by yma004 on 4/19/2016.
 */
public class Building {
    private String building_name;
    private String building_info;
    private LatLng location;

    public Building(String name, String info, LatLng location) {
        this.building_name = name;
        this.building_info = info;
        this.location = location;
    }

    //getters
    public LatLng getLocation() {
        return location;
    }

    public String getBuilding_info() {
        return building_info;
    }

    public String getBuilding_name() {
        return building_name;
    }

    //setters
    public void setBuilding_info(String building_info) {
        this.building_info = building_info;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
