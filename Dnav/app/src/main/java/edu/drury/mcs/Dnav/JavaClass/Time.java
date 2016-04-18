package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by ksmith034 on 3/2/2016.
 *
 * The Time class makes it easier to compare times.
 */
public class Time {
    private int hour;
    private int minute;
    private String ampm;

    Time(String time)
    {
        String[] units = time.split(":");

        this.hour = Integer.parseInt(units[0])%12;
        this.minute = Integer.parseInt(units[1])%60;
        ampm = "none";
    }

    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
        ampm = "none";
    }

    Time(int hour, int minute, String ampm){
        // Make sure the input is a valid time.
        if(hour>0 && hour<=12 && minute>=0 && minute<=60 &&
                (ampm.equalsIgnoreCase("AM") || ampm.equalsIgnoreCase("PM")) ) {
            this.hour = hour;
            this.minute = minute;
            this.ampm = ampm;
        }
        else{
            this.hour = hour%12;
            this.minute = minute%60;
            if(hour%24 > 12) this.ampm = "PM";
            else this.ampm = "AM";
        }
    }

    // Getters
    public int getHour(){ return hour; }
    public int getMinute() { return minute; }
    public String getAmpm() { return ampm; }

    public boolean greaterThan(Time compTime){
        int thisHour = this.getHour();
        if(this.getAmpm().equalsIgnoreCase("PM")) thisHour+=12; // Convert to "army time" so we can compare
        int thisMinute = this.getMinute();

        int thatHour = compTime.getHour();
        if(compTime.getAmpm().equalsIgnoreCase("PM")) thatHour+=12; // Convert to "army time" so we can compare
        int thatMinute = compTime.getMinute();

        if(thisHour < thatHour) return false;
        else { // thisCompHour > thatCompHour
            if(thisMinute < thatMinute) return false;
            else return true;
        }
    }


    public String toString(){
        return hour + ":" + minute + " " + ampm;
    }
}