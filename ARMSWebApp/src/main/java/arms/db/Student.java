import java.util.ArrayList;
import java.util.Map;


public class Student {
	private int studentId;
	private int takenHours = 0;
	private static ARMDatabase db;
	
	public Student(){
		try{
			this.studentId = db.addStudent(this);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Student(int id, int hours){
		this.studentId = id;
		this.takenHours = hours;
	}
	
	public static void setDatabase(ARMDatabase database){
		db = database;
	}
	
	public static Student get(int id) throws Exception{
		return db.getStudent(id);
	}
	
	public static Map<Integer,Student> getStudents() throws Exception{
		return db.getStudents();
	}
	
	public ArrayList<String> viewCatalog() throws Exception{
		return db.getCatalog();
	}
	
	public Map<String, String> viewCourse(int id) throws Exception{
		Map<String, String> courseDetails = db.getCourseDetails(id);
		int demand = db.getCourseDemand(id);
		courseDetails.put("demand", String.valueOf(demand));
		return courseDetails;
	}
	
	public void scheduleRequest(ArrayList<Integer> courses) throws Exception{
		
		ArrayList<Integer> coursesWithPrereqs = addAllPrereqs(courses);
		db.addRequest(studentId, coursesWithPrereqs.size(),coursesWithPrereqs);
		
		//gurobi here?
		
		// return schedule?
		
	}
	
	public ArrayList<Integer> addAllPrereqs(ArrayList<Integer> courses) throws Exception{
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

}
