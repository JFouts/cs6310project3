package arms.com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/HelloServlet")
public class LandingPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			response.getWriter().println(e.toString());
		}
    	
    	try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/arms?user=root&password=root");
			
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("select data from test;");
		    
			response.getWriter().println("Data:");
			while (resultSet.next()) {
				response.getWriter().println(resultSet.getString("data"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println(e.toString());
		}
    	
    	
        
    }
}
