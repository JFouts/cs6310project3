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
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	List<Course> courseList;
		try {
			courseList = api.getCatalog();
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		request.setAttribute("courseList", courseList);
    	
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
    	
    	request.setAttribute("mode", "output");
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	String[] courseStrs = request.getParameterValues("course");
    	ArrayList<Integer> courseIds = new ArrayList<Integer>();
    	for (String s: courseStrs)
    		courseIds.add(Integer.parseInt(s));
    	
    	String shadowId = request.getParameter("shadowId");
    	
    	StudentRequest sr;
    	Map<String, Integer> schedule = new HashMap<String, Integer>();
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	if (shadowId != null && !shadowId.isEmpty()) {
        	int shadowUserId = Integer.parseInt(shadowId);
    		request.setAttribute("shadowId", shadowUserId);
    		try {
				Administrator admin = Administrator.get(userId);
				Map<Integer,Integer> shadowSchedule = admin.shadowRequest(courseIds,shadowUserId);
				for (Integer courseID : shadowSchedule.keySet())
					schedule.put(api.getCourse(courseID).getName(), shadowSchedule.get(courseID));
			} catch (Exception e) {
				throw new ServletException(e);
			}
    	} else {	    	
    		Student student = null;
        	try {
    			student = api.getStudent(userId);
    			sr = student.scheduleRequest(courseIds);
    			for (Integer courseId : sr.getSchedule().keySet())
    				schedule.put(api.getCourse(courseId).getName(), sr.getSchedule().get(courseId));
    		} catch (Exception e) {
				throw new ServletException(e);
    		}
    		request.setAttribute("schedule", schedule);
    	}
    	
		request.getRequestDispatcher("WEB-INF/Request.jsp").forward(request, response);
    }
}
