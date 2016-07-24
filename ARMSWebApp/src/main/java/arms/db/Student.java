package arms.db;



import java.util.ArrayList;
import java.util.Map;


public class Student {
	private int studentId;
	private int takenHours = 0;
	private static ARMDatabase db;
	
	public Student(){
		try{
			db = ARMDatabase.getDatabase();
			this.studentId = db.addStudent(this);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Student(int id, int hours){
		this.studentId = id;
		this.takenHours = hours;
	}
	
	public static Student get(int id) throws Exception{
		db = ARMDatabase.getDatabase();
		return db.getStudent(id);
	}
	
	public static Map<Integer,Student> getStudents() throws Exception{
		return db.getStudents();
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
	
	public ArrayList<Integer> getRequestedCourses() throws Exception{
		return db.getRequestedCourses(studentId);
	}

}
