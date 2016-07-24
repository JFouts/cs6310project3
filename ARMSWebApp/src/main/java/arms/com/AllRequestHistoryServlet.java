package arms.com;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;
import arms.db.Student;

public class AllRequestHistoryServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    public static class Request {
    	private int requestId;
    	private int studentId;
    	private Date timestamp;
    	
    	public Request(int requestId, int studentId, Date timestamp) {
    		this.requestId = requestId;
    		this.studentId = studentId;
    		this.timestamp = timestamp;
    	}

		public int getRequestId() {
			return requestId;
		}

		public void setRequestId(int requestId) {
			this.requestId = requestId;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
		try {
	    	List<Course> courseList;
			courseList = api.getCatalog();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		
    	try {
			Map<Integer, Student> students = api.getStudents();
			request.setAttribute("studentList", students.values());
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	request.setAttribute("requests", new Request[] {
    			new Request(123, 1, new Date()),
    			new Request(456, 2, new Date()),
    			new Request(789, 3, new Date()),
    	});
    	
		request.getRequestDispatcher("WEB-INF/AllRequestHistory.jsp").forward(request, response);
    }
}
