package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
    
    //TODO: This class is only temporary until we can pass Course objects around
    public static class CourseId {

		int Id;
    	String Name;
    	
    	public CourseId(int id, String name){
    		this.Id = id;
    		this.Name = name;
    	}

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}
    }
    
    /**
     * The GET functionality is provided to view course details
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	int courseId = Integer.parseInt(request.getParameter("courseId"));
    	
    	// TODO: Read full course list from DB
    	// TODO: The CourseId class is only needed until the Course class is completed
    	
    	CourseId[] courseList = new CourseId[] { 
    			new CourseId( 1, "6210 - Advanced Operating Systems"), 
    			new CourseId( 2, "6250 - Computer Networks"), 
    			new CourseId( 3, "6262 - Network Security"), 
    			new CourseId( 4, "6290 - High Performance Computer Architecture"), 
    			new CourseId( 5, "6300 - Software Development Process"), 
    			new CourseId( 6, "6310 - Software Architecture & Design"), 
    			new CourseId( 7, "6340 - Software Analysis & Test"), 
    			new CourseId( 8, "6400 - Database Systems Concepts & Design"), 
    			new CourseId( 9, "6440 - Intro Health Informatics"), 
    			new CourseId(10, "6460 - Education Tech-Foundations"), 
    			new CourseId(11, "6475 - Computational Photography"), 
    			new CourseId(12, "6476 - Computer Vision"), 
    			new CourseId(13, "6505 - Computability & Algorithms"), 
    			new CourseId(14, "6601 - Artificial Intelligence"), 
    			new CourseId(15, "7637 - Knowledge-Based AI"), 
    			new CourseId(16, "7641 - Machine Learning"), 
    			new CourseId(17, "7646 - Machine Learning For Trading")
    	};
    	
    	
    	// TODO: Read this data from the DB
    	
    	Course course = null;
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	try {
			course = api.getCourse(courseId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}

    	request.setAttribute("course", course);
    	request.setAttribute("courseList", courseList);
    	
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
