package arms.db;

import java.util.ArrayList;
import java.util.Map;


public class Administrator {
	private static ARMDatabase db;
	
	public Administrator(){
		db = ARMDatabase.getDatabase();
	}
	
	public Student registerStudent(){
		return new Student();
	}
	
	public void deregisterStudent(int id) throws Exception{
		db.deleteStudent(id);
	}
	
	public ArrayList<String> viewCatalog() throws Exception{
		return db.getCatalog();
	}
	
	public Course viewCourse(int id) throws Exception{
		Course c = db.getCourse(id);
		int demand = db.getCourseDemand(id);
		//courseDetails.put("demand", String.valueOf(demand));
		c.setDemand(demand);
		return c;
	}
	
	public void shadowRequest(ArrayList<Integer> courses, int studentId) throws Exception{
		
		//ArrayList<Integer> coursesWithPrereqs = addAllPrereqs(courses);
		
		
	}
}
