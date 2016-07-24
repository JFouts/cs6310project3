package arms.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Administrator;
import arms.db.Course;

public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * The GET functionality is provided to present schedule request widgets
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setAttribute("mode", "input");
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	String shadowId = request.getParameter("shadowId");
    	if (shadowId!=null && !shadowId.isEmpty()) {
        	int shadowUserId = Integer.parseInt(shadowId);
    		request.setAttribute("shadowId", shadowUserId);
    	}
    	
    	// TODO: get the course list for this user from the database
    	Map<Integer, String> courses = new HashMap<Integer, String>(); 
        courses.put(1, "6210 - Advanced Operating Systems");
		courses.put(2, "6250 - Computer Networks");
		courses.put(3, "6262 - Network Security");
		courses.put(4, "6290 - High Performance Computer Architecture");
		courses.put(5, "6300 - Software Development Process");
		courses.put(6, "6310 - Software Architecture & Design");
		courses.put(7, "6340 - Software Analysis & Test");
		courses.put(8, "6400 - Database Systems Concepts & Design");
		courses.put(9, "6440 - Intro Health Informatics");
		courses.put(10, "6460 - Education Tech-Foundations");
		courses.put(11, "6475 - Computational Photography");
		courses.put(12, "6476 - Computer Vision");
		courses.put(13, "6505 - Computability & Algorithms");
		courses.put(14, "6601 - Artificial Intelligence");
		courses.put(15, "7637 - Knowledge-Based AI");
		courses.put(16, "7641 - Machine Learning");
		courses.put(17, "7646 - Machine Learning For Trading");
		
		request.setAttribute("courses", courses);
    	
		request.getRequestDispatcher("WEB-INF/Request.jsp").forward(request, response);
    }
    
    /**
     * The POST functionality is provided to take in schedule request and print the generated schedule
     */
    @Override
    protected void doPost(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {
    	  	    	
    	Map<String, Integer> schedule = new HashMap<String, Integer>();
    	String[] courseStrs = request.getParameterValues("course");
    	ArrayList<Integer> courseIds = new ArrayList<Integer>();
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	request.setAttribute("mode", "output");
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	for (String s: courseStrs) {
    		courseIds.add(Integer.parseInt(s));
    	}
    	
    	String shadowId = request.getParameter("shadowId");
    	if (shadowId!=null && !shadowId.isEmpty()) {
        	int shadowUserId = Integer.parseInt(shadowId);
    		request.setAttribute("shadowId", shadowUserId);
    		try {
				Administrator admin = Administrator.get(userId);
				Map<Integer,Integer> shadowSchedule = admin.shadowRequest(courseIds,shadowUserId);
				for (Integer courseID : shadowSchedule.keySet()) {
					Course c = api.getCourse(courseID.intValue());
					schedule.put(c.getName(), shadowSchedule.get(courseID));
				}
			} catch (Exception e) {
				request.setAttribute("error", e.toString());
				e.printStackTrace();
				request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
				return;
			}
    	} else {	    	
	    	// TODO: get schedule from database by passing in courseIds and userId		
			for (int c: courseIds)
				schedule.put(null, 1);
    	}
    	
		request.setAttribute("schedule", schedule);
    	
    	Map<Integer, String> courses = new HashMap<Integer, String>(); 
        courses.put(1, "6210 - Advanced Operating Systems");
		courses.put(2, "6250 - Computer Networks");
		courses.put(3, "6262 - Network Security");
		courses.put(4, "6290 - High Performance Computer Architecture");
		courses.put(5, "6300 - Software Development Process");
		courses.put(6, "6310 - Software Architecture & Design");
		courses.put(7, "6340 - Software Analysis & Test");
		courses.put(8, "6400 - Database Systems Concepts & Design");
		courses.put(9, "6440 - Intro Health Informatics");
		courses.put(10, "6460 - Education Tech-Foundations");
		courses.put(11, "6475 - Computational Photography");
		courses.put(12, "6476 - Computer Vision");
		courses.put(13, "6505 - Computability & Algorithms");
		courses.put(14, "6601 - Artificial Intelligence");
		courses.put(15, "7637 - Knowledge-Based AI");
		courses.put(16, "7641 - Machine Learning");
		courses.put(17, "7646 - Machine Learning For Trading");
		request.setAttribute("courses", courses);
    	
		request.getRequestDispatcher("WEB-INF/Request.jsp").forward(request, response);
    }
}
