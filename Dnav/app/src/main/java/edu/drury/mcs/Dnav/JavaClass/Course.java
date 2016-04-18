package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 2/28/2016.
 */
// import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  Class that represents a single course on a schedule. Currently contains information for a course name,
 *  building, day, and beginTime. Later iterations may expand upon this.
 */
public class Course implements Serializable{
    private String id;

    // basic fields
    private String name;
    private String code;
    private String location;
    private String days;
    private String beginTime;
    private String endTime;
    private String roomNum;
    private String prof;

    // days of the week as constants.
    public static final String MONDAY = "Mon";
    public static final String TUESDAY = "Tues";
    public static final String WEDNESDAY = "Wed";
    public static final String THURSDAY = "Thurs";
    public static final String FRIDAY = "Fri";
    public static final String SATURDAY = "Sat";
    public static final String SUNDAY = "Sun";

    // Drury buildings as string constants.
    //WHEN ADDING A BUILDING TO THIS LIST UPDATE THE buildingToLatLng FUNCTION TOO
    public static final String BURNHAM = "Burnham";
    public static final String PEARSONS = "Pearsons Hall";
    public static final String OLIN = "Olin Library";
    public static final String LAY = "Lay Hall";
    public static final String MABEE = "Mabee Center";
    public static final String BREECH = "Breech Business School";
    public static final String SHEWMAKER = "Shewmaker Communication Center";
    public static final String TSC = "Trustee Science Center";
    public static final String HSA = "Hammons School of Architecture";
    public static final String CONGREGATIONAL = "Congregational Hall";
    public static final String PAC = "Pool Art Center";
    public static final String FSC = "Findlay Student Center";
    public static final String LAWENFORCEMENT = "Law Enforcement Academy";

    // Drury buildings as coordinate constants.
    /** NOTE: DruryMap.java points to these constants. **/
    public static final LatLng PEARSONLATLNG = new LatLng(37.219146, -93.286636);
    public static final LatLng TSCLATLNG = new LatLng(37.215706, -93.286456);
    public static final LatLng BURNHAMLATLNG = new LatLng(37.218480, -93.286673);
    public static final LatLng HAMMONSLATLNG = new LatLng(37.215567, -93.285314);


    public static Map<String, LatLng> generateBuildingToLatLngMap(){

        HashMap<String, LatLng> result = new HashMap<String, LatLng>();

        result.put(Course.PEARSONS, Course.PEARSONLATLNG);
        result.put(Course.TSC, Course.TSCLATLNG);
        result.put(Course.BURNHAM, Course.BURNHAMLATLNG);
        result.put(Course.HSA, Course.HAMMONSLATLNG);


        return result;
    }
    /**
     *  Constructor for the class. Takes in parameters for each field and assigns them.
     */
    public Course(String id, String name, String code, String building, String days, String beginTime, String endTime, String roomNum, String prof) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.location = building;
        this.days = days;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.roomNum = roomNum;
        this.prof = prof;
    }

    public static LatLng buildingToLatLng(String build){

        if(build.equals(Course.PEARSONS))
            return Course.PEARSONLATLNG;

        //if all else fails, pass a LatLng to the FSC Circle
        return new LatLng(37.221084, -93.285704);
    }

    //getters and setters for each field. Setters are unused in current iteration and may not be used
    //in the future, and so are at risk of removal pending further design planning. Setters could be used if
    //we allow the user to correct mistakes in their schedule or update it.
    public String getID(){
        return id;
    }

    public String getName() { return name; }

    public String getCode() { return code; }

    public String getLocation(){ return location;}

    public String getDays() {
        return days;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getRoomNum(){
        return roomNum;
    }

    public String getProf(){
        return prof;
    }



    public void setID(String id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) { this.code = code; }

    public void setLocation(String building) {
        this.location = building;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setRoomNum(String roomNum){
        this.roomNum = roomNum;
    }

    public void setProf(String prof){
        this.prof = prof;
    }

}
