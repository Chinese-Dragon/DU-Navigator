package edu.drury.mcs.Dnav.JavaClass;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by yma004 on 4/19/2016.
 */
public class Building {
    private String building_name;
    private String building_info;
    private LatLng location;
    private int id;
    private int type;
    private ArrayList<resource> resources_list;

    public Building(String name, String info, int type, int id,LatLng location, ArrayList<resource> resources_list) {
        this.building_name = name;
        this.building_info = info;
        this.location = location;
        this.type = type;
        this.id=id;
        this.resources_list = resources_list;
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

    public ArrayList<resource> getResources_list() {
        return resources_list;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
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

    public void setResources_list(ArrayList<resource> resources_list) {
        this.resources_list = resources_list;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }
}

