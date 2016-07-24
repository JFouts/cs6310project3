package arms.com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConstraintsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	// TODO: grab values from DB
    	Map<String,String> constraintValues = new HashMap<String,String>();
    	constraintValues.put("maxCourses", "2");
    	constraintValues.put("maxSemesters", "12");
    	constraintValues.put("maxStudentsPerCourse", "100");
    	
    	request.setAttribute("constraintValuesMap", constraintValues);
    	
    	request.getRequestDispatcher("WEB-INF/Constraints.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	// TODO: update DB with new values
    	this.doGet(request, response);
    }
}
