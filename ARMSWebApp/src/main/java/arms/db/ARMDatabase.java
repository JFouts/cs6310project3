package arms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ARMDatabase {
	private static final String url = "jdbc:mysql://localhost/arms";
	private static final String user = "root";
    private static final String password = "root";
    
    Connection conn = null;
    Statement stmt = null;
    
    private static ARMDatabase instance;
    
    private ARMDatabase(){
    	setup();
    }
    
    public static ARMDatabase getDatabase(){
    	if(instance == null){
    		instance = new ARMDatabase();
    	}
    	return instance;
    }
    
    
    public void setup() {
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
    
    public Administrator getAdmin(int adminId) throws Exception{
		try {
			String sql = "SELECT * FROM administrator WHERE admin_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, adminId);
			ResultSet rs = statement.executeQuery();
		
			int id = -1;
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				id = rs.getInt("admin_id");			
			}
			rs.close();
			
			return new Administrator(id);
		} catch (SQLException e){
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
    
    public ArrayList<Course> getCatalog() throws Exception{
    	try{
    		stmt = conn.createStatement();
    		String sql = "SELECT course_id, name FROM course ORDER BY course_id ASC";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    ArrayList<Course> courses = new ArrayList<Course>();
    	    
    	    int id = -1;
    	    String name = "";
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  id = rs.getInt("course_id");
    	    	  name = rs.getString("name");

        	      Course c = new Course(id, name);
        	      courses.add(c);
    	    	  //courseNames.add(name);
    	      }
    	      
    	      rs.close();
    	      
    	      return courses;
    	} catch (SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public Course getCourse(int courseId) throws Exception{
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
			
		  String name = "", semesters= "", prereqs = "";	
  	      int maxSize = -1;
  	      
		  while(rs.next()){
  	         //Retrieve by column name
  	         //String id  = String.valueOf(rs.getInt("course_id"));
  	         name = rs.getString("name");
  	         maxSize = rs.getInt("max_size");
  	         semesters = rs.getString("semesters");
  	         prereqs = rs.getString("prerequisites");
		  }
		  if(maxSize == 0)
			  maxSize = -1;
		  Course c = new Course(courseId, name, maxSize);
		  
		  // availability never null
		  String[] availability = semesters.split(",");
		  for(int i=0; i<availability.length; i++){
			  c.addAvailability(Integer.parseInt(availability[i]));
		  }
		  
		  // prereq sometimes null
		  if(prereqs != null){
			  String[] prerequisites = prereqs.split(",");
			  for(int i=0; i<prerequisites.length; i++){
				  c.addPrereqs(Integer.parseInt(prerequisites[i]));
			  }
		  }
		  
		  return c;
		  
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
    
    public void updateCourse(Course course) throws Exception {
    	try{
    		String sql = "UPDATE course SET name = ?, max_size = ? WHERE course_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, course.getName());
			statement.setInt(2, course.getMaxSize());
			statement.setInt(3, course.getId());
			
			statement.executeUpdate();
			
			sql = "DELETE FROM course_availability WHERE course_id = ?;";
			statement = conn.prepareStatement(sql);
			
			statement.setInt(1, course.getId());
			
			statement.executeUpdate();
			
			for(int i=0;i<course.getAvailability().size();i++) {
				sql = "INSERT INTO course_availability (course_id, semester) VALUES (?, ?);";
				statement = conn.prepareStatement(sql);
				
				statement.setInt(1, course.getId());
				statement.setInt(2, course.getAvailability().get(i));
				
				statement.executeUpdate();
			}
			
			sql = "DELETE FROM course_prereq WHERE course_id = ?;";
			statement = conn.prepareStatement(sql);
			
			statement.setInt(1, course.getId());
			
			statement.executeUpdate();
			
			for(int i=0;i<course.getPrereqs().size();i++) {
				if(course.getPrereqs().get(i) == -1) continue;
				
				sql = "INSERT INTO course_prereq (course_id, prereq_id) VALUES (?, ?);";
				statement = conn.prepareStatement(sql);
				
				statement.setInt(1, course.getId());
				statement.setInt(2, course.getPrereqs().get(i));
				
				statement.executeUpdate();
			}
			
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
    
    public StudentRequest getStudentRequest(int studentId) throws Exception{
    	try{
    	String sql = "SELECT s.request_id, s.timestamp, GROUP_CONCAT(DISTINCT r.course_id) AS courses " +
				"FROM request_course AS `r` "+
				"INNER JOIN student_request AS `s` ON (s.request_id = r.request_id) " +
				"WHERE s.student_id = ? AND " +
					"s.request_id = (SELECT MAX(request_id) " +
									"FROM student_request " +
									"WHERE student_id = ?);";
    	PreparedStatement statement = conn.prepareStatement(sql);

		statement.setInt(1, studentId);
		statement.setInt(2, studentId);
		ResultSet rs = statement.executeQuery();
	
		//ArrayList<Integer> requestedCourses = new ArrayList<Integer>();
		int reqId = -1;
		java.sql.Timestamp ts = null;
		String courseStr = "";
		while(rs.next()){
         //Retrieve by column name
    	  reqId = rs.getInt("request_id");
    	  ts = rs.getTimestamp("timestamp");
    	  courseStr = rs.getString("courses");
    	}
		
		StudentRequest sr = new StudentRequest(reqId, ts);
  	  
  	  // availability never null
		  String[] courses = courseStr.split(",");
		  for(int i=0; i<courses.length; i++){
			  sr.addCourse(Integer.parseInt(courses[i]));
		  }
		
		  rs.close();
		  return sr;
    	} catch(SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public List<StudentRequest> getStudentRequestHistory(int studentId) throws Exception{
    	try{
	    	String sql = 
	    			"SELECT s.request_id, GROUP_CONCAT(s.semester_id) AS `semesters`, s.timestamp, GROUP_CONCAT(c.course_id) AS `courses` " +
					"FROM student_request AS `s`                                                                        " +
					"  INNER JOIN request_course AS `r` on s.request_id = r.request_id                                " +
					"  INNER JOIN course AS `c` ON c.course_id = r.course_id                                          " +
					"WHERE s.student_id = ?                                                                       " +
					"GROUP BY s.request_id                                                                              " +
					"ORDER BY s.request_id DESC;                                                                        ";
	    	PreparedStatement statement = conn.prepareStatement(sql);
	
			statement.setInt(1, studentId);
			ResultSet rs = statement.executeQuery();
		
			ArrayList<StudentRequest> requests = new ArrayList<StudentRequest>();
			while(rs.next()){
				//Retrieve by column name
				int reqId = rs.getInt("request_id");
				Timestamp ts = rs.getTimestamp("timestamp");
				String courseStr = rs.getString("courses");
				String semesterStr = rs.getString("semesters");
				
				StudentRequest sr = new StudentRequest(reqId, ts);
				
				String[] courses = courseStr.split(",");
				String[] semesters = semesterStr.split(",");
				
				for(int i=0; i<courses.length; i++){
					sr.addCourse(Integer.parseInt(courses[i]));
				}
				
				Map<Integer, Integer> schedule = new HashMap<Integer, Integer>();
				for(int i=0; i < semesters.length && i < courses.length;i++) {
					schedule.put(sr.getCourses().get(i), Integer.parseInt(semesters[i]));
				}
				sr.setSchedule(schedule);
				
				requests.add(sr);
	    	}
			rs.close();
			return requests;
		} catch(SQLException e){
			throw new Exception(e);
		}
    }
    
    public Map<Integer,Integer> getStudentSchedule(int studentId) throws Exception{
    	try{
    		String sql = "SELECT s.course_id, s.semester_id "+
    				  "FROM schedule_course AS `s` " +
    				  "INNER JOIN student_schedule AS `a` ON (a.schedule_id = s.schedule_id) "+
    				  "WHERE a.student_id = ? AND "+
    				  		"a.schedule_id = (SELECT MAX(schedule_id) "+
    				  		"FROM student_schedule " +
    				  		"WHERE student_id = ?);";
    		PreparedStatement statement = conn.prepareStatement(sql);

    		statement.setInt(1, studentId);
    		statement.setInt(2, studentId);
    		ResultSet rs = statement.executeQuery();
    		
    		Map<Integer, Integer> sched = new HashMap<Integer,Integer>();
    		int courseId = -1, sem = -1;
    		while(rs.next()){
    	         //Retrieve by column name
    	    	  courseId = rs.getInt("course_id");
    	    	  sem = rs.getInt("semester_id");
    	    	  
    	    	  sched.put(courseId, sem);
    	    }
    		rs.close();
    		
    		return sched;
    		
    	} catch(SQLException e){
    		throw new Exception(e);
    	}
    }
    
    public SummaryReport generateSummaryReport() throws Exception {
    	SummaryReport report = new SummaryReport();
    	
    	report.setNumStudents(getStudentCount());
    	report.setNumRequests(getRequestCount());    	
    	report.setMaxCourses(getMaxCoursesPerSemester());
    	report.setMaxSemesters(getNumSemesters());
    	report.setMaxStudents(getMaxStudentsPerCourse());
    	
    	List<Course> catalog = getCatalog();
    	for(int i=0;i<catalog.size();i++) {
    		SummaryReport.SummaryCourseRequest request = new SummaryReport.SummaryCourseRequest(catalog.get(i).getName(), getCourseRequestCount(catalog.get(i).getId()));
    		report.addCourseRequest(request);
    	}
    	
    	Iterator<Student> studentIterator = getStudents().values().iterator();
    	while(studentIterator.hasNext()) {
    		Student s = studentIterator.next();
			SummaryReport.SummaryStudentRequest request = 
					new SummaryReport.SummaryStudentRequest(
							s.getStudentId(),
							getStudentNextSemesterCount(s.getStudentId(), 0),
							getStudentFutureSemesterCount(s.getStudentId(), 0),
							getStudentUnavilableCount(s.getStudentId())
							);
			report.addStudentRequest(request);
    	}   	
    	
    	return report;
    }
    
	public int getRequestCount() throws Exception{
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT COUNT(request_id) AS num_request FROM student_request;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int numRequests = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numRequests = rs.getInt("num_request");
    	      }
    	      
    	      rs.close();
    	      return numRequests;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getStudentNextSemesterCount(int studentId, int currentSemester) throws Exception{
		try{
    		String sql = "SELECT COUNT(schedule_course.schedule_id) AS num_request " +
    				"FROM schedule_course " +
    				"INNER JOIN student_schedule ON student_schedule.schedule_id = schedule_course.schedule_id " +
    				"WHERE student_schedule.student_id = ? " +
    				"AND schedule_course.semester_id = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);

    		statement.setInt(1, studentId);
    		statement.setInt(2, currentSemester);    		
    		
    	    ResultSet rs = statement.executeQuery();
    		
    	    int numRequests = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numRequests = rs.getInt("num_request");
    	      }
    	      
    	      rs.close();
    	      return numRequests;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getStudentFutureSemesterCount(int studentId, int currentSemester) throws Exception{
		try{
    		String sql = "SELECT COUNT(schedule_course.schedule_id) AS num_request " +
    				"FROM schedule_course " +
    				"INNER JOIN student_schedule ON student_schedule.schedule_id = schedule_course.schedule_id " +
    				"WHERE student_schedule.student_id = ? " +
    				"AND schedule_course.semester_id > ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);

    		statement.setInt(1, studentId);
    		statement.setInt(2, currentSemester);    		
    		
    	    ResultSet rs = statement.executeQuery();
    		
    	    int numRequests = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numRequests = rs.getInt("num_request");
    	      }
    	      
    	      rs.close();
    	      return numRequests;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getStudentUnavilableCount(int studentId) throws Exception{
		try{
    		String sql = "SELECT COUNT(schedule_course.schedule_id) AS num_request " +
    				"FROM schedule_course " +
    				"INNER JOIN student_schedule ON student_schedule.schedule_id = schedule_course.schedule_id " +
    				"WHERE student_schedule.student_id = ? " +
    				"AND schedule_course.semester_id = -1;";
    		PreparedStatement statement = conn.prepareStatement(sql);

    		statement.setInt(1, studentId);   		
    		
    	    ResultSet rs = statement.executeQuery();
    		
    	    int numRequests = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numRequests = rs.getInt("num_request");
    	      }
    	      
    	      rs.close();
    	      return numRequests;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getCourseRequestCount(int courseId) throws Exception{
		try{
    		String sql = "SELECT COUNT(request_id) AS num_request FROM request_course WHERE course_id = ?;";
    		PreparedStatement statement = conn.prepareStatement(sql);

    		statement.setInt(1, courseId);
    		
    	    ResultSet rs = statement.executeQuery();
    		
    	    int numRequests = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numRequests = rs.getInt("num_request");
    	      }
    	      
    	      rs.close();
    	      return numRequests;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public void updateGlobalParams(int maxSemesters, int maxClasses, int maxStudents) throws Exception {
		String sql = "UPDATE global_constraint SET max_semesters = ?, max_classes = ?, max_students = ?;";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setInt(1, maxSemesters);
		statement.setInt(2, maxClasses);
		statement.setInt(3, maxStudents);
		
		statement.executeUpdate();
	}
	
	public List<StudentRequest> getAllStudentRequests() throws Exception {
		String sql = "SELECT request_id, student_id, timestamp " +
				"FROM student_request " + 
				"ORDER BY request_id DESC;";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
	    ResultSet rs = statement.executeQuery();

	    List<StudentRequest> requests = new ArrayList<StudentRequest>();
	    while(rs.next()){
	    	//Retrieve by column name
	    	StudentRequest request = new StudentRequest(rs.getInt("request_id"),  rs.getTimestamp("timestamp"));
	    	request.setStudentId(rs.getInt("student_id"));
	    	requests.add(request);
	    }
	  
		rs.close();
		return requests;
	}
	    
    /*******************GUROBI METHODS************************/
	
	public int getStudentCount() throws Exception{
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT COUNT(student_id) AS num_students FROM student;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int numStudents = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numStudents = rs.getInt("num_students");
    	      }
    	      
    	      rs.close();
    	      return numStudents;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getCourseCount() throws Exception{
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT COUNT(course_id) AS num_courses FROM course;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int numCourses = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numCourses = rs.getInt("num_courses");
    	      }
    	      
    	      rs.close();
    	      return numCourses;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getNumSemesters() throws Exception{
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT max_semesters FROM global_constraint;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int numSemesters = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  numSemesters = rs.getInt("max_semesters");
    	      }
    	      
    	      rs.close();
    	      return numSemesters;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public int getMaxCoursesPerSemester() throws Exception {
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT max_classes FROM global_constraint;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int maxClasses = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  maxClasses = rs.getInt("max_classes");
    	      }
    	      
    	      rs.close();
    	      return maxClasses;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}

	public int getMaxStudentsPerCourse() throws Exception {
		try{
    		stmt = conn.createStatement();
    		String sql = "SELECT max_students FROM global_constraint;";
    	    ResultSet rs = stmt.executeQuery(sql);
    	    
    	    int maxStudents = -1;
    	    
    	      while(rs.next()){
    	         //Retrieve by column name
    	    	  maxStudents = rs.getInt("max_students");
    	      }
    	      
    	      rs.close();
    	      return maxStudents;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public ArrayList<Integer> getRequestedCourses(int studentId) throws Exception{
		try{
			String sql = "SELECT course_id " +
					"FROM request_course AS `r` "+
					"INNER JOIN student_request AS `s` ON (s.request_id = r.request_id) " +
					"WHERE s.student_id = ? AND " +
						"s.request_id = (SELECT MAX(request_id) " +
										"FROM student_request " +
										"WHERE student_id = ?);";
	    	PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, studentId);
			statement.setInt(2, studentId);
			ResultSet rs = statement.executeQuery();
		
			ArrayList<Integer> requestedCourses = new ArrayList<Integer>();
			while(rs.next()){
	         //Retrieve by column name
	    	  requestedCourses.add(rs.getInt("course_id"));
	    	}
			rs.close();
			
			return requestedCourses;
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public ArrayList<Integer> getPrereqs(int courseId) throws Exception{
		try{
			String sql = "SELECT prereq_id " +
					"FROM course_prereq "+
					"WHERE course_id = ?;";
	    	PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			
			ArrayList<Integer> prereqs = new ArrayList<Integer>();
			while(rs.next()){
	         //Retrieve by column name
	    	  prereqs.add(rs.getInt("prereq_id"));
	    	}
			rs.close();
			
			return prereqs;
		}catch (SQLException e){
    		throw new Exception(e);
    	}
	}
	
	public void addSchedule(int studentId, Map<Integer, Integer> schedMap) throws Exception{
		try{
	    	  stmt = conn.createStatement();
	  	      String sql = "INSERT INTO student_schedule (student_id) VALUES (?)";
	  	      PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	  	      
	  	      
	  	      statement.setInt(1, studentId);
	  	      statement.executeUpdate();

	  	      int scheduleId = -1;
	  	      ResultSet rs = statement.getGeneratedKeys();
	  	      if(rs != null && rs.next()){
	  	    	  scheduleId = rs.getInt(1);
	  	      }
	  	      
	  	      Iterator<Integer> courseIds = schedMap.keySet().iterator();
	  	      while(courseIds.hasNext()){
	  	    	  int courseId = courseIds.next();
	  	    	  addScheduleCourse(scheduleId, courseId, schedMap.get(courseId));
	  	      }	    		
	    		
	    	} catch (SQLException e){
	    		throw new Exception(e);
	    	}	
	}
	
	public void addScheduleCourse(int schedId, int courseId, int semester) throws Exception{
		try{
	    	  //stmt = conn.createStatement();
	  	      String sql = "INSERT INTO schedule_course (schedule_id, course_id, semester_id) " +
	  	                   "VALUES (?, ?, ?);";
	  	      PreparedStatement statement = conn.prepareStatement(sql);
	  	      
	  	      statement.setInt(1, schedId);
	  	      statement.setInt(2, courseId);
	  	      statement.setInt(3, semester);
	  	      statement.executeUpdate();
	    	} catch (SQLException e){
	    		throw new Exception(e);
	    	}
	}
	
	public ArrayList<Integer> getStudentRequestPriority() throws Exception{
		try{
	    	 stmt = conn.createStatement();
	    	 String sql = "SELECT student_id "+
	    			 	"FROM student_request " +
	    			 	"WHERE request_id IN (SELECT MAX(request_id) " +
											"FROM student_request " +
											"GROUP BY student_id) " +
						"ORDER BY timestamp ASC;";
	    	
	    	 ResultSet rs = stmt.executeQuery(sql);
	    	    
	    	 ArrayList<Integer> studentPriority = new ArrayList<Integer>();
	    	    
	    	 while(rs.next()){
	    	         //Retrieve by column name
	    		 int id = rs.getInt("student_id");
	    		 studentPriority.add(id);
	    	 }
	    	 
	    	      
	    	 rs.close();
	    	 return studentPriority;
	    	 
		} catch (SQLException e){
    		throw new Exception(e);
    	}
	}
}
