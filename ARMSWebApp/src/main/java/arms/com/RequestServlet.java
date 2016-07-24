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
    	
    	request.setAttribute("mode", "output");
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	String[] courseStrs = request.getParameterValues("course");
    	ArrayList<Integer> courseIds = new ArrayList<Integer>();
    	for (String s: courseStrs)
    		courseIds.add(Integer.parseInt(s));
    	
		ARMDatabase api = ARMDatabase.getDatabase();
		
		Student student = null;
    	try {
			student = api.getStudent(userId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		
    	Map<String, Integer> schedule = new HashMap<String, Integer>();
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
    	return;
//    	for (Integer courseId : sr.getSchedule().keySet()) {
//    		String courseName;
//			try {
//				courseName = api.getCourse(courseId).getName();
//			} catch (Exception e) {
//				e.printStackTrace();
//				request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
//				return;
//			}
//    		schedule.put(courseName, sr.getSchedule().get(courseId));
//    	}
//    	
//		request.setAttribute("schedule", schedule);
//		out.write("Formatted schedule for page.");
//		request.getRequestDispatcher("WEB-INF/Request.jsp").forward(request, response);
    }
}
