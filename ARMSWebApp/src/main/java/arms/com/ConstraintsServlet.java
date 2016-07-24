package arms.com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;

public class ConstraintsServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	Map<String,Integer> constraintValues = new HashMap<String,Integer>();
    	try {
			constraintValues.put("maxCourses", api.getMaxCoursesPerSemester());
			constraintValues.put("maxSemesters", api.getNumSemesters());
			constraintValues.put("maxStudentsPerCourse", api.getMaxStudentsPerCourse());
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	request.setAttribute("constraintValuesMap", constraintValues);
    	
    	request.getRequestDispatcher("WEB-INF/Constraints.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	 	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	int maxCourses = Integer.parseInt(request.getParameter("maxCourses"));
    	int maxSemesters = Integer.parseInt(request.getParameter("maxSemesters"));
    	int maxStudentsPerCourse = Integer.parseInt(request.getParameter("maxStudentsPerCourse"));

    	ARMDatabase api = ARMDatabase.getDatabase();
    	try {
			api.updateGlobalParams(maxSemesters, maxCourses, maxStudentsPerCourse);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
		response.sendRedirect("Constraints?userId=" + userId);
    }
}
