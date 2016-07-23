import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ARMDatabase {
	private static final String url = "jdbc:mysql://localhost/arms";
	private static final String user = "root";
    private static final String password = "root";
    
    Connection conn = null;
    Statement stmt = null;
    
    public void setup() throws Exception {
    	//STEP 2: Register JDBC driver
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch(ClassNotFoundException ex) {
    		System.out.println("Error: unable to load driver class!");
    		System.exit(1);
    	}
    	
    	 //STEP 3: Open a connection
        //System.out.println("Connecting to database...");
    	try{
    		conn = DriverManager.getConnection(url, user, password);
    		// System.out.println("Connected database successfully...");
    	} catch(SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public void teardown() throws Exception {
    	try{
    		if(conn!=null){
    			conn.close();
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public int addStudent(Student student) throws Exception {
    	//STEP 4: Execute a query
    	//System.out.println("Inserting records into the table...");
    	try{
    		  stmt = conn.createStatement();
    	      String sql = "INSERT INTO student (taken_hours) " +
    	                   "VALUES (0)";
    	      stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
    		
    	      ResultSet rs = stmt.getGeneratedKeys();
    	      int id = -1;
    	      if (rs.next()){
    	            id = rs.getInt(1);
    	            
    	      }
    	      
    	      rs.close();
    	      return id;
    	} catch(SQLException e) {
    		throw new Exception(e);
    	}
    }
    
    public Student getStudent(int studentId) throws Exception{
    		//STEP 4: Execute a query
    		//System.out.println("Creating statement...");
    		try {
    			//stmt = conn.createStatement();
    			String sql = "SELECT * FROM student WHERE student_id = ?;";
    			PreparedStatement statement = conn.prepareStatement(sql);
 
    			statement.setInt(1, studentId);
    			ResultSet rs = statement.executeQuery();
    		
    			int id = -1;
    			int hours = -1;
    			//STEP 5: Extract data from result set
    			while(rs.next()){
    				//Retrieve by column name
    				id = rs.getInt("student_id");
    				hours = rs.getInt("taken_hours");			
    			}
    			rs.close();
    			
    			return new Student(id, hours);
    		} catch (SQLException e){
    			throw new Exception(e);
    		}
    		
    }
    
    public Map<Integer,Student> getStudents() throws Exception{
    	try{
    		stmt = conn.createStatement();
    		String sql = "SELECT * FROM student";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    Map<Integer, Student> students = new HashMap<Integer,Student>();
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	         int id  = rs.getInt("student_id");
    	         int hours = rs.getInt("taken_hours");
    	         
    	         students.put(id, new Student(id,hours));
    	      }
    	      rs.close();
    	      
    	      return students;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public void deleteStudent(int id) throws Exception{
    	try {
    		stmt = conn.createStatement();
    		String sql = "DELETE FROM student WHERE student_id = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);
    		
    		statement.setInt(1, id);
    		statement.executeUpdate();
    	} catch (SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<String> getCatalog() throws Exception{
    	try{
    		stmt = conn.createStatement();
    		String sql = "SELECT name FROM course ORDER BY course_id ASC";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    ArrayList<String> courseNames = new ArrayList<String>();
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  String name = rs.getString("name");
    	         
    	    	  courseNames.add(name);
    	      }
    	      rs.close();
    	      
    	      return courseNames;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public Map<String, String> getCourseDetails(int courseId) throws Exception{
    	try{
    		String sql = "SELECT c.course_id, c.name, c.max_size, " +
    						"GROUP_CONCAT(DISTINCT a.semester) " +
    							"AS semesters, " +
    						"GROUP_CONCAT(DISTINCT p.prereq_id) " +
    							"AS prerequisites " +
    					  "FROM course AS `c` " +
    					  "LEFT OUTER JOIN course_prereq AS `p` " +
    					  	"ON (c.course_id = p.course_id) " +
    					  "INNER JOIN course_availability AS `a` " +
    					  	"ON (c.course_id = a.course_id)" +
    					  "WHERE c.course_id = ? " +
    					  "GROUP BY c.course_id;";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
    	    
    	    Map<String, String> details = new HashMap<String,String>();
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	         String id  = String.valueOf(rs.getInt("course_id"));
    	         String name = rs.getString("name");
    	         String maxSize = String.valueOf(rs.getInt("max_size"));
    	         String semesters = rs.getString("semesters");
    	         String prereqs = rs.getString("prerequisites");
    	         
    	         details.put("course_id", id);
    	         details.put("name", name);
    	         details.put("max_size", maxSize);
    	         details.put("semesters", semesters);
    	         details.put("prereqs", prereqs);
    	      }
    	      rs.close();
    	      
    	      return details;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public int getCourseDemand(int courseId) throws Exception{
    	try{
    		String sql = "SELECT COUNT(course_id) AS demand " +
    					 "FROM schedule_course  " +
    					 "WHERE semester_id = 1 AND " +
    					 	"course_id = ? AND " +
    					 	"schedule_id IN (SELECT MAX(schedule_id) " +
    					 	"FROM student_schedule " +
    					 	"GROUP BY student_id);";
    		PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			
			int demand = -1;
			while(rs.next()){
   	         //Retrieve by column name
   	         demand  = rs.getInt("demand");
			}
			rs.close();
  	      
  	      return demand;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}	
    }
    
    /*public ArrayList<Integer> getTakenCourses(int studentId) throws Exception{
    	try{
    		String sql = "SELECT course_id " +
    					 "FROM student_taken_course  " +
    					 "WHERE student_id = ?;"; 
    		PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, studentId);
			ResultSet rs = statement.executeQuery();
			
			ArrayList<Integer> taken = new ArrayList<Integer>();
			while(rs.next()){
   	         //Retrieve by column name
   	         	int course = rs.getInt("course_id");
   	         	taken.add(course);
			}
			rs.close();
  	      
  	      return taken;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}	
    }*/
    
    public ArrayList<Integer> getPrereqsNotTaken(int courseId, int studentId) throws Exception{
    	try{
    		String sql = "SELECT prereq_id FROM course_prereq " +
    					"WHERE course_id = ? AND " +
    					"prereq_id NOT IN (SELECT course_id " +
    					 				"FROM student_taken_course  " +
    					 				"WHERE student_id = ?);"; 
    		PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, courseId);
			statement.setInt(2, studentId);
			ResultSet rs = statement.executeQuery();
			
			ArrayList<Integer> prereqsNotTaken = new ArrayList<Integer>();
			while(rs.next()){
   	         //Retrieve by column name
   	         	int course = rs.getInt("prereq_id");
   	         	prereqsNotTaken.add(course);
			}
			
			rs.close();
  	      
  	      return prereqsNotTaken;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}	
    }
    
    public void addRequest(int studentId, int numCourses, ArrayList<Integer> courses) throws Exception{
    	try{
    	  stmt = conn.createStatement();
  	      String sql = "INSERT INTO student_request (student_id, num_courses, semester_id, timestamp) VALUES ( ?,?,1, NOW())";
  	      PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
  	      
  	      
  	      statement.setInt(1, studentId);
  	      statement.setInt(2, numCourses);
  	      statement.executeUpdate();

  	      int requestId = -1;
  	      ResultSet rs = statement.getGeneratedKeys();
  	      if(rs != null && rs.next()){
  	    	  requestId = rs.getInt(1);
  	      }
  	      
  	      for(int i=0; i< courses.size(); i++){
  	    	  addRequestCourse(requestId, courses.get(i));
  	      }
    		
    		
    	} catch (SQLException e){
    		throw new Exception(e);
    	}	
    }
    
    public void addRequestCourse(int requestId, int courseId) throws Exception{
    	try{
    	  //stmt = conn.createStatement();
  	      String sql = "INSERT INTO request_course (request_id, course_id) " +
  	                   "VALUES (?, ?);";
  	      PreparedStatement statement = conn.prepareStatement(sql);
  	      
  	      statement.setInt(1, requestId);
  	      statement.setInt(2, courseId);
  	      statement.executeUpdate();
    	} catch (SQLException e){
    		throw new Exception(e);
    	}
    }

    
}
