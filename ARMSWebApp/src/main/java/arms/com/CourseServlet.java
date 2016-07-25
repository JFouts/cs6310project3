package arms.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;
import arms.db.Semester;

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
			throw new ServletException(e);
		} 
    	
    	Course course = null;
    	
    	try {
			course = api.getCourse(courseId);
			course.setDemand(api.getCourseDemand(courseId));
		} catch (Exception e) {
			throw new ServletException(e);
		}
    	
    	Semester[] availableSemesters = null;
    	if(course != null) {
    		ArrayList<Integer> avails = course.getAvailability();
    		int availsLen = avails.size();
    		availableSemesters = new Semester[availsLen]; 
    		for(int i=0;i < availsLen;i++){
    			availableSemesters[i] = new Semester(avails.get(i)+3);
    		}
    	}
    	
    	Course[] coursePrereqs = null;
    	if(course != null) {
    		ArrayList<Integer> prereqs = course.getPrereqs();
    		int prereqsLen = prereqs.size();
    		coursePrereqs = new Course[prereqsLen]; 
    		for(int i=0;i < prereqsLen;i++){
    			try {
					coursePrereqs[i] = api.getCourse(prereqs.get(i));
				} catch (Exception e) {
					throw new ServletException(e);
				}
    		}
    	}
    	
    	Semester[] allSemesters = new Semester[3];
    	for(int i=0;i<3;i++)
    		allSemesters[i] = new Semester(i+3);    	
    	
    	int defaultMaxSize;
		try {
			defaultMaxSize = api.getMaxStudentsPerCourse();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
    	request.setAttribute("course", course);
    	request.setAttribute("availableSemesters", availableSemesters);
    	request.setAttribute("coursePrereqs", coursePrereqs);
    	request.setAttribute("allSemesters", allSemesters);
    	request.setAttribute("defaultMaxSize", defaultMaxSize);
    	
		request.getRequestDispatcher("WEB-INF/Course.jsp").forward(request, response);
    }
    
    /**
     * The POST functionality is provided to update course details
     */
    @Override
    protected void doPost(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {

    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	int sizeLimit = Integer.parseInt(request.getParameter("maxSize"));
    	String courseName = request.getParameter("courseName");
    	
    	String[] semestersList = request.getParameterValues("availableSemesters");
    	String[] preqsList = request.getParameterValues("coursePrereqs");
    	
    	Course course = new Course(courseId, courseName, sizeLimit);
    	
    	for(int i=0;i<semestersList.length;i++) {
    		course.addAvailability(Integer.parseInt(semestersList[i])-3);
    	}
    	
    	for(int i=0;i<preqsList.length;i++) {
    		course.addPrereqs(Integer.parseInt(preqsList[i]));
    	}
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	try {
    		api.updateCourse(course);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		response.sendRedirect("Course?userId=" + userId + "&courseId=" + courseId);
    }
}
