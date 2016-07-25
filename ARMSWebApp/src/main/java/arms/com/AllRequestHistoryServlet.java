package arms.com;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;
import arms.db.Student;
import arms.db.StudentRequest;

public class AllRequestHistoryServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
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
    	
    	String studentFilterString = request.getParameter("studentFilter");
    	String courseFilterString = request.getParameter("courseFilter");
    	
    	List<StudentRequest> requests = null;
    	try {
    		int studentFilter = -1;
    		int courseFilter = -1;
    		
    		if(studentFilterString != null && !"".equals(studentFilterString)){
    			studentFilter = Integer.parseInt(studentFilterString);
    		}
    		if(courseFilterString != null && !"".equals(courseFilterString)){
    			courseFilter = Integer.parseInt(courseFilterString);
    		}
    		
    		if(courseFilter == -1 && studentFilter == -1) {
    			requests = api.getAllStudentRequests();
    		} else if(courseFilter == -1) {
    			requests = api.getAllStudentRequestsByStudent(studentFilter);		
    		} else if(studentFilter == -1) {
    			requests = api.getAllStudentRequestsByCourse(courseFilter);
    		} else {
    			requests = api.getAllStudentRequestsByStudentCourse(studentFilter, courseFilter);
    		}
    		
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	request.setAttribute("requests",requests);
    	
		request.getRequestDispatcher("WEB-INF/AllRequestHistory.jsp").forward(request, response);
    }
}
