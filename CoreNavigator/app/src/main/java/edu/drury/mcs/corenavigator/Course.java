package edu.drury.mcs.corenavigator;

public class Course{
		private String name;
		private String building;
		private String days;
		private String beginTime;
		
		// days of the week
		public static final String MONDAY = "M";
		public static final String TUESDAY = "T";
		public static final String WEDNESDAY = "W";
		public static final String THURSDAY = "R";
		public static final String FRIDAY = "F";
		public static final String SATURDAY = "S";
		public static final String SUNDAY = "U";
		
		// Drury buildings
		public static final String PEARSONS = "Pearsons Hall";
		public static final String TSC = "Trustee Science Center";
		public static final String BURNHAM = "Burnham Hall";
		public static final String HAMMONS = "Hammons School of Architecture";
		

		public Course(String name, String building, String days, String beginTime){
			this.name = name;
			this.building = building;
			this.days = days;
			this.beginTime = beginTime;
		}
		
		public String getName(){
			return name;
		}
		
		public String getBuilding(){
			return building;
		}
		
		public String getDays(){
			return days;
		}
		
		public String getBeginTime(){
			return beginTime;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public void setBuilding(String building){
			this.building = building;
		}
		
		public void setDays(){
			this.days = days;
		}
		
		public void setBeginTime(){
			this.beginTime = beginTime;
		}
}

