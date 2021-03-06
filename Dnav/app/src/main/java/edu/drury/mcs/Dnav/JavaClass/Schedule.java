package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 2/28/2016.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class the represents a single student schedule. Contains a list of courses and has a name field.
 * Currently lacks any means by which a course can be removed... might need to implement that in the
 * future.
 */
public class Schedule implements Serializable{
    //ID field for internal identification
    private String id;

    //Name field and course list
    private String name;
    private List<Course> courses;

    //constructor simply makes a Schedule with a given name and no courses. Courses must be added on at a time
    // using the add function.
    public Schedule(String name){
        this.name = name;
        courses = new ArrayList<Course>();
    }

    public Schedule(String ID, String name){
        this.id = ID;
        this.name = name;
        courses = new ArrayList<Course>();
    }

    //getter for name
    public String getName(){
        return name;
    }

    public String getID(){
        return id;
    }

    //getter for course list
    public List<Course> getCourses(){
        return courses;
    }

    /**
     * This is a filtering function that only selects courses that have the given day as its day,
     * and then returns the remaining list.
     *
     * @param day - The day that is to be filtered on. Use the constants built into the Course class
     * @return - A list of courses that meet on the given day
     */
    public List<Course> getCourseOnDay(String day){
        List<Course> timeCourses = getCoursesSortedByTime();
        ArrayList<Course> dayCourses = new ArrayList<>();
        for(Course c : timeCourses){
            if((c.getDays()).contains(day)) {
                dayCourses.add(c);
                System.out.println("Course "+c.getName()+" is on days: "+c.getDays());
            }
        }
        return dayCourses;
    }

    public Course getCourseByID(String id){
        Course result = null;
        for(int i=0; i<courses.size(); i++){
            if(courses.get(i).getID().equals(id))
                result = courses.get(i);
        }

        return result;
    }

    public void removeCourseByID(String id){
        Course result = null;
        for(int i=0; i<courses.size(); i++){
            if(courses.get(i).getID().equals(id))
                courses.remove(i);
        }
    }

    public List<Course> getCoursesSortedByTime(){
        ArrayList<Course> sortedList = (ArrayList<Course>) courses;

        Collections.sort(sortedList);

//        Course[] courseArray = courses.toArray(new Course[courses.size()]);
//
//        // B U B B L E  S O R T  B O Y S
//        for(int i = 0; i<courses.size(); i++)
//        {
//            String strTime = courses.get(i).getBeginTime();
//            Time curTime = new Time(strTime);
//            for(int j=i; j<courses.size(); j++)
//            {
//                String strCompTime = courses.get(j).getBeginTime();
//                Time compTime = new Time(strCompTime);
//                if(curTime.greaterThan(compTime)){
//                    Course curCourse = courses.get(i);
//                    Course compCourse = courses.get(j);
//
//                    courseArray[i] = compCourse;
//                    courseArray[j] = curCourse;
//                }
//            }
//        }
//
//        for(int i = 0; i<courseArray.length; i++)
//        {
//            sortedList.add(courseArray[i]);
//        }

        return sortedList;
    }

    /**
     * Only means by which a course can be added to the schedule.
     * @param c - Course to be added
     */
    public void addCourse(Course c){
        courses.add(c);
    }
}