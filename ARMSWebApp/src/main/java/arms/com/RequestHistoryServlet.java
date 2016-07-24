package arms.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
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
    	Student student = null;
    	try {
			student = api.getStudent(userId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
    	//TODO: Some db call here
    	ArrayList<StudentRequest> requests = new ArrayList<StudentRequest>();
    	
//    	TempStudentSchedule[] schedule = new TempStudentSchedule[] {
//    			new TempStudentSchedule((String) courses.get(1), 1),
//    			new TempStudentSchedule((String) courses.get(2), 2),
//    	};
//    	TempStudentRequest[] requestList = new TempStudentRequest[] {
//    			new TempStudentRequest(3, new Date(), schedule),
//    			new TempStudentRequest(2, new Date(), schedule),
//    			new TempStudentRequest(1, new Date(), schedule),
//    	};
    	
    	//request.setAttribute("requestList", requestList);
    	
		request.getRequestDispatcher("WEB-INF/RequestHistory.jsp").forward(request, response);
    }
}
