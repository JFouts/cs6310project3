package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class CourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * The GET functionality is provided to view course details
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	
    	// TODO: Read this data from the DB
    	
    	request.setAttribute("courseId", courseId);
    	request.setAttribute("courseName", "Sample Course");
    	request.setAttribute("courseAvail", new int[] { 0, 1, 2 });
    	request.setAttribute("coursePrereq", new int[] { 1, 5, 6 });
    	request.setAttribute("courseSize", -1);
    	
		request.getRequestDispatcher("WEB-INF/Course.jsp").forward(request, response);
    }
    
    /**
     * The POST functionality is provided to update course details
     */
    @Override
    protected void doPost(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {
    	
    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	String[] allowedSemestersString = request.getParameter("courseAvail").split(",");
    	int[] allowedSemesters = new int[allowedSemestersString.length];
    	for(int i=0;i<allowedSemesters.length;i++) {
    		allowedSemesters[i] = Integer.parseInt(allowedSemestersString[i]); 		
    	}
    	int sizeLimit = Integer.parseInt(request.getParameter("courseSize"));
    	
    	// TODO: Store this data into the DB
    }
}
