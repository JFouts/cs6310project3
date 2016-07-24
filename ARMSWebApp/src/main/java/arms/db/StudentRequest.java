package arms.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentRequest {
	int requestId;
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
	
	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}
	
	public Map<Integer, Integer> getSchedule() {
		return schedule;
	}
}
