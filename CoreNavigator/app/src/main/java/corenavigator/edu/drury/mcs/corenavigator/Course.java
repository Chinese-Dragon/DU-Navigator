package corenavigator.edu.drury.mcs.corenavigator;

import com.google.android.gms.maps.model.LatLng;

/**
 *  Class that represents a single course on a schedule. Currently contains information for a course name,
 *  building, day, and beginTime. Later iterations may expand upon this.
 */
public class Course {
    //basic fields
    private String name;
    private LatLng building;
    private String days;
    private String beginTime;

    // days of the week as constants.
    protected static final String MONDAY = "Mon";
    protected static final String TUESDAY = "Tues";
    protected static final String WEDNESDAY = "Wed";
    protected static final String THURSDAY = "Thurs";
    protected static final String FRIDAY = "Fri";
    protected static final String SATURDAY = "Sat";
    protected static final String SUNDAY = "Sun";

    // Drury buildings as coordinate constants.
    protected static final LatLng PEARSON = new LatLng(37.219146, -93.286636);
    protected static final LatLng TSC = new LatLng(37.215706, -93.286456);
    protected static final LatLng BURNHAM = new LatLng(37.218480, -93.286673);
    protected static final LatLng HAMMONS = new LatLng(37.215567, -93.285314);

    /**
     *  Constructor for the class. Takes in parameters for each field and assigns them.
     */
    public Course(String name, LatLng building, String days, String beginTime) {
        this.name = name;
        this.building = building;
        this.days = days;
        this.beginTime = beginTime;
    }

    //getters and setters for each field. Setters are unused in current iteration and may not be used
    //in the future, and so are at risk of removal pending further design planning. Setters could be used if
    //we allow the user to correct mistakes in their schedule or update it.
    public String getName() {
        return name;
    }

    public LatLng getBuilding() {
        return building;
    }

    public String getDays() {
        return days;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(LatLng building) {
        this.building = building;
    }

    public void setDays() {
        this.days = days;
    }

    public void setBeginTime() {
        this.beginTime = beginTime;
    }
}
