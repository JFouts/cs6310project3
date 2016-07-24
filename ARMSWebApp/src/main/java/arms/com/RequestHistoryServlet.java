package arms.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;
import arms.db.Student;
import arms.db.StudentRequest;

/**
 * RequestHistoryServlet is the controller for the RequestHistory page.
 * This page has one function:
 * 	(1) Display to the user details on the stored student requests for a student (GET)
 * */

public class RequestHistoryServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	Map<Integer, String> courses = new HashMap<Integer, String>();
		try {
			for (Course c: api.getCatalog())
				courses.put(c.getId(), c.getName());
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		request.setAttribute("courses", courses);
    	
    	Student student = null;
    	try {
			student = api.getStudent(userId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	//TODO: Some db call here using student
    	ArrayList<StudentRequest> requestList = new ArrayList<StudentRequest>();
    	StudentRequest sr = new StudentRequest(1, new java.sql.Timestamp(9999));
    	Map<Integer,Integer> srs = new HashMap<Integer,Integer>();
    	srs.put(1, 1);
    	srs.put(2, 1);
    	srs.put(3, -1);
    	sr.setSchedule(srs);
    	requestList.add(sr);
    	
    	request.setAttribute("requestList", requestList);
    	
    	
    	
		request.getRequestDispatcher("WEB-INF/RequestHistory.jsp").forward(request, response);
    }
}
