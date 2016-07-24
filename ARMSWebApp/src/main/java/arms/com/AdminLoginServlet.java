package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setAttribute("loginFailed", false);
		request.getRequestDispatcher("WEB-INF/AdminLogin.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	int userId;
    	try {
    		userId = Integer.parseInt(request.getParameter("userId"));
    	} catch (Exception e) {
    		userId = -1;
    	}
    	
    	// TODO: Validate user id in the DB
    	if(userId != 2) {
    		request.setAttribute("loginFailed", true);
    		request.getRequestDispatcher("WEB-INF/AdminLogin.jsp").forward(request, response);
    	}
    	else
    	{
    		response.sendRedirect("AdminDashboard?userId=" + userId);
    	}
    }
}
