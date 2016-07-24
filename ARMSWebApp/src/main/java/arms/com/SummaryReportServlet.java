package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SummaryReportServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    public static class CourseRequest {
    	String name;
    	int count;
    	public CourseRequest(String name, int count) {
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
    
    public static class StudentRequest {
    	int studentId;
    	int nextSemesterCount;
    	int futureSemesterCount;
    	int unavilableCount;
    	
    	public StudentRequest(int studentId, int nextSemesterCount, int futureSemesterCount, int unavilableCount) {
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
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	request.setAttribute("numStudents", 100);
    	request.setAttribute("numRequests", 200);
    	request.setAttribute("courseRequests", new CourseRequest[] {
    			new CourseRequest("Test1", 12),
    			new CourseRequest("Test2", 56),
    			new CourseRequest("Test3", 45),
    			new CourseRequest("Test4", 34),
    	});
    	
    	request.setAttribute("studentRequests", new StudentRequest[]{
    			new StudentRequest(123, 2, 3, 1),
    			new StudentRequest(456, 0, 2, 4),
    			new StudentRequest(789, 1, 1, 2),
    	});
    	request.setAttribute("maxCourses", 2);
    	request.setAttribute("maxSemesters", 3);
    	request.setAttribute("maxStudents", 20);
    	
		request.getRequestDispatcher("WEB-INF/SummaryReport.jsp").forward(request, response);
    }
}
