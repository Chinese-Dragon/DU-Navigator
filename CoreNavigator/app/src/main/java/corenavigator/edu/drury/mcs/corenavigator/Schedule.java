package corenavigator.edu.drury.mcs.corenavigator;

import java.util.ArrayList;
import java.util.List;

public class Schedule{
	private String name;
	private List<Course> courses;
	
	public Schedule(String name){
		this.name = name;
		courses = new ArrayList<Course>();
	}
	
	public String getName(){
		return name;
	}
	
	public List<Course> getCourses(){
		return courses;
	}
	
	public List<Course> getCourseOnDay(String day){
			List<Course> dayCourses = new ArrayList<Course>();
			for(int i=0; i<courses.size(); i++){
				Course c = courses.get(i);
				if((c.getDays()).contains(day)) dayCourses.add(c);
			}
			return dayCourses;
	}
	
	public void addCourse(Course c){
		courses.add(c);
	}
}