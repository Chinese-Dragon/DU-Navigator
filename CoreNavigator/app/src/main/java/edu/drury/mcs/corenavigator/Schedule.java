package edu.drury.mcs.corenavigator;

import java.util.ArrayList;
import java.util.List;

public class Schedule{
	private String name;
	private List<Course> courses;
	
	private static final String MONDAY = "M";
	private static final String TUESDAY = "T";
	private static final String WEDNESDAY = "W";
	private static final String THURSDAY = "R";
	private static final String FRIDAY = "F";
	
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
		if(day.length()>1){
			System.err.println("Use this method on one day at a time. Breaking...");
			return null;
		}
		else{
			List<Course> dayCourses = new ArrayList<Course>();
			for(int i=0; i<courses.size(); i++){
				Course c = courses.get(i);
				if((c.getDays()).contains(day)) dayCourses.add(c);
			}
			return dayCourses;
		}
	}
	
	public void addCourse(Course c){
		courses.add(c);
	}
}