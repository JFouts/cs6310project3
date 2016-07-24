package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Administrator;
import arms.db.Student;

public class ARMSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected int userId = -1;
    protected boolean isAdmin = false;
    protected boolean isStudent = false;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	String userIdString = request.getParameter("userId");
    	if(userIdString != null && !"".equals(userIdString))
    	{
    		int userId = Integer.parseInt(userIdString);
    		ARMDatabase api = ARMDatabase.getDatabase();
    		boolean isAdmin = false;
    		boolean isStudent = false;
    		
    		try {
				Student student = api.getStudent(userId);
				if(student != null && student.getStudentId() >= 0) {
					isStudent = true;
				} else {
					isAdmin = true;
				}
			} catch (Exception e) {
				request.setAttribute("error", e.toString());
				e.printStackTrace();
				request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			}
    		
    		request.setAttribute("isLoggedIn", true);
    		request.setAttribute("userId", userId);
    		request.setAttribute("isAdmin", isAdmin);
    	}    	
    }
}
