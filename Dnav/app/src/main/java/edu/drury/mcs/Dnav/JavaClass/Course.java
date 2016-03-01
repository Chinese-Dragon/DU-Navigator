package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 2/28/2016.
 */
// import com.google.android.gms.maps.model.LatLng;

/**
 *  Class that represents a single course on a schedule. Currently contains information for a course name,
 *  building, day, and beginTime. Later iterations may expand upon this.
 */
public class Course {
    private String id;

    // basic fields
    private String name;
    private String location;
    private String days;
    private String beginTime;
    private String endTime;
    private String roomNum;
    private String prof;

    // days of the week as constants.
    protected static final String MONDAY = "Mon";
    protected static final String TUESDAY = "Tues";
    protected static final String WEDNESDAY = "Wed";
    protected static final String THURSDAY = "Thurs";
    protected static final String FRIDAY = "Fri";
    protected static final String SATURDAY = "Sat";
    protected static final String SUNDAY = "Sun";

    // Drury buildings as coordinate constants.
    // protected static final LatLng PEARSON = new LatLng(37.219146, -93.286636);
    // protected static final LatLng TSC = new LatLng(37.215706, -93.286456);
    // protected static final LatLng BURNHAM = new LatLng(37.218480, -93.286673);
    // protected static final LatLng HAMMONS = new LatLng(37.215567, -93.285314);

    /**
     *  Constructor for the class. Takes in parameters for each field and assigns them.
     */
    public Course(String id, String name, String building, String days, String beginTime, String endTime, String roomNum, String prof) {
        this.id = id;
        this.name = name;
        this.location = building;
        this.days = days;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.roomNum = roomNum;
        this.prof = prof;
    }

    //getters and setters for each field. Setters are unused in current iteration and may not be used
    //in the future, and so are at risk of removal pending further design planning. Setters could be used if
    //we allow the user to correct mistakes in their schedule or update it.
    public String getID(){
        return id;
    }

    public String getName() {
        return name;
    }

    // public LatLng getBuilding() {
    // return building;
    // }

    public String getLocation(){
        return location;
    }

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

    // public void setBuilding(LatLng building) {
    // this.building = building;
    // }

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
