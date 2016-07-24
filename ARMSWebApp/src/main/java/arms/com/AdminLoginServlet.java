package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.db.ARMDatabase;
import arms.db.Administrator;
import arms.db.Student;

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
    	
		ARMDatabase api = ARMDatabase.getDatabase();
    	Administrator admin = null;
		try {
			admin = api.getAdmin(userId);
		} catch (Exception e) {
			request.setAttribute("error", e.toString());
			e.printStackTrace();
			request.getRequestDispatcher("WEB-INF/Error.jsp").forward(request, response);
			return;
		}
		if(admin != null && admin.getAdminId() >= 0) {
			response.sendRedirect("AdminDashboard?userId=" + userId);
		} else {
    		request.setAttribute("loginFailed", true);
    		request.getRequestDispatcher("WEB-INF/AdminLogin.jsp").forward(request, response);						
		}
    }
}
