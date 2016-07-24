package arms.com;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestHistoryServlet is the controller for the RequestHistory page.
 * This page has one function:
 * 	(1) Display to the user details on the stored student requests for a student (GET)
 * */

public class RequestHistoryServlet extends ARMSServlet {
    private static final long serialVersionUID = 1L;
    
  //TODO: This class is only temporary until we get StudentRequest/Schedule Classes
    public static class TempStudentSchedule {
		String course;
    	int semester;
    	public TempStudentSchedule(String course, int semester){
    		this.course = course;
    		this.semester = semester;
    	}
    	public String getCourse() {
    		return course;
    	}
    	public int getSemester() {
    		return semester;
    	}
    }
    public static class TempStudentRequest {
		int id;
    	Date timestamp;
    	TempStudentSchedule[] schedule;
    	public TempStudentRequest(int id, Date timestamp, TempStudentSchedule[] schedule){
    		this.id = id;
    		this.timestamp = timestamp;
    		this.schedule = schedule;
    	}
    	public Date getTimestamp() {
    		return timestamp;
    	}
    	public TempStudentSchedule[] getSchedule() {
    		return schedule;
    	}
    }
   
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	super.doGet(request, response);
    	
    	int userId = Integer.parseInt(request.getParameter("userId"));
    	request.setAttribute("userId", userId);
    	
    	// TODO: get the requests for this user from the database
    	Map<Integer, String> courses = new HashMap<Integer, String>(); 
        courses.put(1, "6210 - Advanced Operating Systems");
		courses.put(2, "6250 - Computer Networks");
		courses.put(3, "6262 - Network Security");
		courses.put(4, "6290 - High Performance Computer Architecture");
		courses.put(5, "6300 - Software Development Process");
		courses.put(6, "6310 - Software Architecture & Design");
		courses.put(7, "6340 - Software Analysis & Test");
		courses.put(8, "6400 - Database Systems Concepts & Design");
		courses.put(9, "6440 - Intro Health Informatics");
		courses.put(10, "6460 - Education Tech-Foundations");
		courses.put(11, "6475 - Computational Photography");
		courses.put(12, "6476 - Computer Vision");
		courses.put(13, "6505 - Computability & Algorithms");
		courses.put(14, "6601 - Artificial Intelligence");
		courses.put(15, "7637 - Knowledge-Based AI");
		courses.put(16, "7641 - Machine Learning");
		courses.put(17, "7646 - Machine Learning For Trading");
    	TempStudentSchedule[] schedule = new TempStudentSchedule[] {
    			new TempStudentSchedule((String) courses.get(1), 1),
    			new TempStudentSchedule((String) courses.get(2), 2),
    	};
    	TempStudentRequest[] requestList = new TempStudentRequest[] {
    			new TempStudentRequest(3, new Date(), schedule),
    			new TempStudentRequest(2, new Date(), schedule),
    			new TempStudentRequest(1, new Date(), schedule),
    	};
    	
    	request.setAttribute("requestList", requestList);
    	
		request.getRequestDispatcher("WEB-INF/RequestHistory.jsp").forward(request, response);
    }
}
