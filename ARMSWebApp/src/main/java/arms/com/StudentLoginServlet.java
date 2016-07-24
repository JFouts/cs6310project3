package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Student;

public class StudentLoginServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	request.setAttribute("loginFailed", false);
		request.getRequestDispatcher("WEB-INF/StudentLogin.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	int userId;
    	try {
    		userId = Integer.parseInt(request.getParameter("userId"));
    	} catch (Exception e) {
    		userId = -1;
    	}
    	
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
		if(student != null && student.getStudentId() >= 0) {
			response.sendRedirect("StudentDashboard?userId=" + userId);;
		} else {
    		request.setAttribute("loginFailed", true);
    		request.getRequestDispatcher("WEB-INF/StudentLogin.jsp").forward(request, response);						
		}
    }
}
