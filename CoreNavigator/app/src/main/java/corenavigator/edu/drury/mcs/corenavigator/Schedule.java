package corenavigator.edu.drury.mcs.corenavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * A class the represents a single student schedule. Contains a list of courses and has a name field.
 * Currently lacks any means by which a course can be removed... might need to implement that in the
 * future.
 */
public class Schedule{
	//Name field and course list
	private String name;
	private List<Course> courses;

	//constructor simply makes a Schedule with a given name and no courses. Courses must be added on at a time
	// using the add function.
	public Schedule(String name){
		this.name = name;
		courses = new ArrayList<Course>();
	}

	//getter for name
	public String getName(){
		return name;
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
		List<Course> dayCourses = new ArrayList<Course>();
		for(int i=0; i<courses.size(); i++){
			Course c = courses.get(i);
			if((c.getDays()).contains(day)) dayCourses.add(c);
		}
		return dayCourses;
	}

	/**
	 * Only means by which a course can be added to the schedule.
	 * @param c - Course to be added
	 */
	public void addCourse(Course c){
		courses.add(c);
	}
}