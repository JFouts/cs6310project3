package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ARMSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	String userIdString = request.getParameter("userId");
    	if(userIdString != null && !"".equals(userIdString))
    	{
    		request.setAttribute("isLoggedIn", true);
    		int userId = Integer.parseInt(userIdString);
    		request.setAttribute("userId", userId);
    		
    		boolean isAdmin = false;
    		if(userId == 2) {
    			isAdmin = true;
    		}
    		
    		request.setAttribute("isAdmin", isAdmin);
    	}    	
    }
}
