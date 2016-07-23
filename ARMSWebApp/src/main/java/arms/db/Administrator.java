package arms.db;

import java.util.ArrayList;
import java.util.Map;


public class Administrator {
	private static ARMDatabase db;
	
	public static void setDatabase(ARMDatabase database){
		db = database;
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
	
	/*public Map<String, String> viewCourse(int id) throws Exception{
		Map<String, String> courseDetails = db.getCourseDetails(id);
		int demand = db.getCourseDemand(id);
		courseDetails.put("demand", String.valueOf(demand));
		return courseDetails;
	}*/
	
	public void shadowRequest(ArrayList<Integer> courses, int studentId) throws Exception{
		
		//ArrayList<Integer> coursesWithPrereqs = addAllPrereqs(courses);
		
		
	}
}
