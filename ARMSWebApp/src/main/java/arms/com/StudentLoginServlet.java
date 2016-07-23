package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setAttribute("loginFailed", false);
		request.getRequestDispatcher("WEB-INF/StudentLogin.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	
    	// TODO: Validate user id in the DB
    	if(userId != 1) {
    		request.setAttribute("loginFailed", true);
    		request.getRequestDispatcher("WEB-INF/StudentLogin.jsp").forward(request, response);
    	}
    	else
    	{
    		response.sendRedirect("StudentDashboard?userId=" + userId);
    	}
    }
}
