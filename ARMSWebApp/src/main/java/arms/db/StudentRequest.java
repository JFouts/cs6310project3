package arms.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentRequest {
	int studentId;
	int requestId;
	String courseNames;
	java.sql.Timestamp timestamp;
	ArrayList<Integer> courses;
	Map<Integer, Integer> schedule;
	
	public StudentRequest(int reqId, java.sql.Timestamp ts){
		this.requestId = reqId;
		this.timestamp = ts;
		courses = new ArrayList<Integer>();
		schedule = new HashMap<Integer, Integer>();
	}
	
	public void addCourse(int courseId){
		courses.add(courseId);
	}
	
	public void setSchedule(Map<Integer,Integer> sched){
		schedule = sched;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public ArrayList<Integer> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Integer> courses) {
		this.courses = courses;
	}

	public int getStudentId() {
		return studentId;
	}

	public Map<Integer, Integer> getSchedule() {
		return schedule;
	}
	
	public String getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}

}
