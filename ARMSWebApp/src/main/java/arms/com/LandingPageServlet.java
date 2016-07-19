package arms.com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class LandingPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
		try {
			Context initCtx = null;
			initCtx = new InitialContext();
			Context envCtx;
			envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)
			envCtx.lookup("jdbc/ARMS");
			
			Connection connect = ds.getConnection();

			Statement statement = connect.createStatement();
			
			ResultSet resultSet = statement.executeQuery("select data from test;");
		    
			StringBuilder sb = new StringBuilder();
			while (resultSet.next()) {
				sb.append(resultSet.getString("data"));
				sb.append(" ");
			}

			request.setAttribute("result", sb.toString());
			request.getRequestDispatcher("WEB-INF/LandingPage.jsp").forward(request, response);
			
		} catch (NamingException e) {
			e.printStackTrace();
			response.getWriter().println(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println(e.toString());
		}   
		    
    }
}
