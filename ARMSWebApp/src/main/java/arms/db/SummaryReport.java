package arms.db;

import java.util.ArrayList;
import java.util.List;

public class SummaryReport {
	
	public static class SummaryCourseRequest {
    	String name;
    	int count;
    	public SummaryCourseRequest(String name, int count) {
    		this.name = name;
    		this.count = count;
    	}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
    }
    
    public static class SummaryStudentRequest {
    	int studentId;
    	int nextSemesterCount;
    	int futureSemesterCount;
    	int unavilableCount;
    	
    	public SummaryStudentRequest(int studentId, int nextSemesterCount, int futureSemesterCount, int unavilableCount) {
    		this.studentId = studentId;
    		this.nextSemesterCount = nextSemesterCount;
    		this.futureSemesterCount = futureSemesterCount;
    		this.unavilableCount = unavilableCount;
    	}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public int getNextSemesterCount() {
			return nextSemesterCount;
		}

		public void setNextSemesterCount(int nextSemesterCount) {
			this.nextSemesterCount = nextSemesterCount;
		}

		public int getFutureSemesterCount() {
			return futureSemesterCount;
		}

		public void setFutureSemesterCount(int futureSemesterCount) {
			this.futureSemesterCount = futureSemesterCount;
		}

		public int getUnavilableCount() {
			return unavilableCount;
		}

		public void setUnavilableCount(int unavilableCount) {
			this.unavilableCount = unavilableCount;
		}
    }
	
	private int numStudents;
	private int numRequests;
	private List<SummaryCourseRequest> courseRequests = new ArrayList<SummaryCourseRequest>();
	private List<SummaryStudentRequest> studentRequests = new ArrayList<SummaryStudentRequest>();
	private int maxCourses;
	private int maxSemesters;
	private int maxStudents;
	
	public int getNumStudents() {
		return numStudents;
	}
	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}
	public int getNumRequests() {
		return numRequests;
	}
	public void setNumRequests(int numRequests) {
		this.numRequests = numRequests;
	}
	public List<SummaryCourseRequest> getCourseRequests() {
		return courseRequests;
	}
	public void setCourseRequests(List<SummaryCourseRequest> courseRequests) {
		this.courseRequests = courseRequests;
	}
	public List<SummaryStudentRequest> getStudentRequests() {
		return studentRequests;
	}
	public void setStudentRequests(List<SummaryStudentRequest> studentRequests) {
		this.studentRequests = studentRequests;
	}
	public int getMaxCourses() {
		return maxCourses;
	}
	public void setMaxCourses(int maxCourses) {
		this.maxCourses = maxCourses;
	}
	public int getMaxSemesters() {
		return maxSemesters;
	}
	public void setMaxSemesters(int maxSemesters) {
		this.maxSemesters = maxSemesters;
	}
	public int getMaxStudents() {
		return maxStudents;
	}
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	public void addCourseRequest(SummaryCourseRequest request) {
		courseRequests.add(request);
	}
	public void addStudentRequest(SummaryStudentRequest request) {
		studentRequests.add(request);
	}
}
