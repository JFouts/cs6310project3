package arms.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Administrator;
import arms.db.Course;
import arms.db.Student;
import arms.db.StudentRequest;

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
    	
    	Map<Integer, String> courses = new HashMap<Integer, String>(); 
        for (Course c: courseList)
        	courses.put(c.getId(), c.getName());
		
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
    			
		Student student = null;
    	try {
			student = api.getStudent(userId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		
    	StudentRequest sr;
		try {
			sr = student.scheduleRequest(courseIds);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		

    	request.getRequestDispatcher("WEB-INF/Request.jsp").forward(request, response);
    }
}
