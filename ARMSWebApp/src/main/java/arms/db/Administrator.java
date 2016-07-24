package arms.db;

import java.util.ArrayList;
import java.util.Map;


public class Administrator {
	private static ARMDatabase db;
	private int adminId;
	
	public Administrator(int id){
		db = ARMDatabase.getDatabase();
		this.adminId=id;
	}
	
	public static Administrator get(int id) throws Exception{
		db = ARMDatabase.getDatabase();
		return db.getAdmin(id);
	}
	
	public Student registerStudent(){
		return new Student();
	}
	
	public void deregisterStudent(int id) throws Exception{
		db.deleteStudent(id);
	}
	
	public ArrayList<Course> viewCatalog() throws Exception{
		return db.getCatalog();
	}
	
	public Course viewCourse(int id) throws Exception{
		Course c = db.getCourse(id);
		int demand = db.getCourseDemand(id);
		//courseDetails.put("demand", String.valueOf(demand));
		c.setDemand(demand);
		return c;
	}
	
	public Map<Integer,Integer> shadowRequest(ArrayList<Integer> courses, int studentId) throws Exception {
		
		Map<Integer,Integer> schedule = null;
		ArrayList<Integer> coursesWithPrereqs = addAllPrereqs(courses,studentId);
		//db.addRequest(studentId, coursesWithPrereqs.size(),coursesWithPrereqs);
		
		ComputationalEngine e = new ComputationalEngine();
		schedule = e.processShadowRequest(studentId,courses);
		
		return schedule;
	}
	
	public ArrayList<Integer> addAllPrereqs(ArrayList<Integer> courses,  int studentId) throws Exception{
		ArrayList<Integer> newCourses = new ArrayList<Integer>();
		for(int i=0; i< courses.size(); i++)
			newCourses.add(courses.get(i));
		
		do{
			// set courses to new courses
			courses.clear();
			for(int j=0; j< newCourses.size(); j++){
				courses.add(newCourses.get(j));
			}

			// populate with unique prereqs
			for(int i=0; i< courses.size(); i++){
				ArrayList<Integer> prereqsNotTaken = db.getPrereqsNotTaken(courses.get(i), studentId);
				for(int j=0; j<prereqsNotTaken.size(); j++){
					if(!newCourses.contains(prereqsNotTaken.get(j))){
						newCourses.add(prereqsNotTaken.get(j));
					}
				}
			}
			// if prereqs are added, continue to loop for nested prereqs
		} while(newCourses.size() != courses.size());
		
		return courses;
	}

	public int getAdminId() {
		return adminId;
	}
}
