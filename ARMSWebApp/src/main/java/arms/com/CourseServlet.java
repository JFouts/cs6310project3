package arms.com;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;

/**
 * CourseServlet is the controller for the Course page.
 * This page has two functions
 * 	(1) Display to the user details about the course (GET)
 *  (2) Update data for a course (POST)
 * 
 * Depending on the logged in users privileges they may or may
 * not be able to perform the POST action.
 * 
 * This page primarily involves the usage of the Course object.
 * */

public class CourseServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * The GET functionality is provided to view course details
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	List<Course> courseList;
		try {
			courseList = api.getCatalog();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		} 
    	
    	Course course = null;
    	
    	try {
			course = api.getCourse(courseId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}

    	request.setAttribute("course", course);
    	
		request.getRequestDispatcher("WEB-INF/Course.jsp").forward(request, response);
    }
    
    /**
     * The POST functionality is provided to update course details
     */
    @Override
    protected void doPost(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {

    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	int sizeLimit = Integer.parseInt(request.getParameter("maxsize"));
    	String courseName = request.getParameter("courseName");
    	
    	Course course = new Course(courseId, courseName, sizeLimit);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	api.updateCourse(course);
    	
    	this.doGet(request, response);
    }
}
