package arms.com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import arms.com.CourseServlet.CourseId;

public class CatalogServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

    	super.doGet(request, response);
    	
    	// TODO: Read full course list from DB
    	// TODO: The CourseId class is only needed until the Course class is completed
    	
    	CourseId[] courseList = new CourseId[] { 
    			new CourseId( 1, "6210 - Advanced Operating Systems"), 
    			new CourseId( 2, "6250 - Computer Networks"), 
    			new CourseId( 3, "6262 - Network Security"), 
    			new CourseId( 4, "6290 - High Performance Computer Architecture"), 
    			new CourseId( 5, "6300 - Software Development Process"), 
    			new CourseId( 6, "6310 - Software Architecture & Design"), 
    			new CourseId( 7, "6340 - Software Analysis & Test"), 
    			new CourseId( 8, "6400 - Database Systems Concepts & Design"), 
    			new CourseId( 9, "6440 - Intro Health Informatics"), 
    			new CourseId(10, "6460 - Education Tech-Foundations"), 
    			new CourseId(11, "6475 - Computational Photography"), 
    			new CourseId(12, "6476 - Computer Vision"), 
    			new CourseId(13, "6505 - Computability & Algorithms"), 
    			new CourseId(14, "6601 - Artificial Intelligence"), 
    			new CourseId(15, "7637 - Knowledge-Based AI"), 
    			new CourseId(16, "7641 - Machine Learning"), 
    			new CourseId(17, "7646 - Machine Learning For Trading")
    	};
    	
    	request.setAttribute("courseList", courseList);
    	
		request.getRequestDispatcher("WEB-INF/Catalog.jsp").forward(request, response);
    }
}
