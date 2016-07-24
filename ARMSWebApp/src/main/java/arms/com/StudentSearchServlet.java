package arms.com;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Course;
import arms.db.Student;

public class StudentSearchServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	ARMDatabase api = ARMDatabase.getDatabase();
    	try {
			Map<Integer, Student> students = api.getStudents();
			request.setAttribute("studentList", students.values());
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
    	
		request.getRequestDispatcher("WEB-INF/StudentSearch.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

    	String action = request.getParameter("buttonAction");
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	ARMDatabase api = ARMDatabase.getDatabase();
    	
    	if ("shadow".equals(action)) {
    		int studentId = Integer.parseInt(request.getParameter("selectedStudent"));
    		
    		//TODO: Shadow Student
    		response.sendRedirect("AdminDashboard?userId=" + userId);
    		
    	} else if ("add".equals(action)) {
    		
    		try {
				api.addStudent(null);
			} catch (Exception e) {
				request.setAttribute("error", e.toString());
				e.printStackTrace();
				request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
				return;
			}
    		
    	} else if ("remove".equals(action)) {
    		
    		int studentId = Integer.parseInt(request.getParameter("selectedStudent"));
    		try {
				api.deleteStudent(studentId);
			} catch (Exception e) {
				request.setAttribute("error", e.toString());
				e.printStackTrace();
				request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
				return;
			}
    	} else {
    		request.setAttribute("error", "Unknown Action: " + action);
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
    	}
    	
		response.sendRedirect("StudentSearch?userId=" + userId);
    }
}
