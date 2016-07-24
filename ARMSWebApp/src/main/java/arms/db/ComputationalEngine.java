package arms.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gurobi.*;

public class ComputationalEngine {
	private GRBEnv env;
	private GRBModel model;
	private GRBVar[][][] Yijk;
	private GRBVar X;
	
	ARMDatabase db;
	
	int numStudents,numCourses,numSemesters;
	int requestingStudentId = -1;
	boolean shadowMode = false;
	ArrayList<Integer> shadowRequest;
	
	public ComputationalEngine(){
		db = ARMDatabase.getDatabase();
		
		try {
			numStudents = db.getStudentCount();
			numCourses = db.getCourseCount();
			numSemesters = db.getNumSemesters();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			env = new GRBEnv();
			env.set(GRB.IntParam.LogToConsole, 0);
			model = new GRBModel(env);
			
			Yijk = new GRBVar[numStudents][numCourses][numSemesters];
			for(int i=0; i<numStudents; i++){
				for(int j=0;j<numCourses;j++){
					for(int k=0; k<numSemesters;k++){
						Yijk[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "Y"+i+j+k);
					}
				}
			}
			
			//X = model.addVar(0, GRB.INFINITY, 1, GRB.INTEGER, "X");
			
			model.update();
			
		} catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	public Map<Integer,Integer> processStudentRequests(int studentId) throws Exception{
		this.requestingStudentId = studentId;
		setObjectiveFunc();
		generateConstraints();
		
		return runModel();
		
	}
	
	// to be implemented
	public Map<Integer, Integer> processShadowRequest(int studentId, ArrayList<Integer> shadowReq) throws Exception {
		shadowMode = true;
		shadowRequest = shadowReq;
		return processStudentRequests(studentId);
		
	}
	
	private void setObjectiveFunc(){
		try	{
			GRBLinExpr objFuncConstraint = new GRBLinExpr();
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					for(int i=0; i<numStudents; i++){
						objFuncConstraint.addTerm(numSemesters-k, Yijk[i][j][k]);
					}
					//String cname = "OBJFUNC_Semester"+k+"_Course"+j;
					//model.addConstr(objFuncConstraint, GRB.LESS_EQUAL, X, cname);
				}
			}
			model.setObjective(objFuncConstraint,GRB.MAXIMIZE);
		}catch(GRBException e){
			e.printStackTrace();
		}
			
			
	}
	
	private void generateConstraints(){
		// semester constraint already imposed
		try {
		
		//studentPriorityConstraint();
			
		// max courses per semester per student
		if(db.getMaxCoursesPerSemester() != 0){	
			maxCourseConstraint();
		}
		
		// set prereq constraints for all courses
		prereqBeforeCourseConstraint();
		forcePrereqConstraint();
		
		// max students per course per semester (if class has a max)
		maxStudentConstraint(); 
		
		// courses not requested should equal zero
		dontAddCourseConstraint();
		

		// semesters available
		courseAvailabilityConstraint();
		
		// do not take course multiple times
		onlyTakeCourseOnceConstraint();
				
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*public void studentPriorityConstraint() throws Exception{
		ArrayList<Integer> studentsInOrder = db.getStudentRequestPriority();
		if(shadowMode){
			if(studentsInOrder.contains(requestingStudentId)){
				studentsInOrder.remove(requestingStudentId);
			}
			studentsInOrder.add(requestingStudentId);
		}
		
		for(int i=0; i< studentsInOrder.size()-1; i++){
			Student s = db.getStudent(studentsInOrder.get(i));
			ArrayList<Integer> reqCourses = s.getRequestedCourses();
			if(s.getStudentId() == requestingStudentId && shadowMode)
				reqCourses = shadowRequest;
			for(int j=0; j<numCourses; j++){
				//Course c = db.getCourse(j+1);
				if(reqCourses.contains(j+1)){
					for(int iSub = i+1; iSub < studentsInOrder.size(); iSub++){
						GRBLinExpr priorityStudentConstraint = new GRBLinExpr();
						GRBLinExpr nonPriorityStudentExpr = new GRBLinExpr();
						int priorityStudId = s.getStudentId()-902448900;
						int nonPriorStudId = studentsInOrder.get(iSub)-902448900;
						for(int k=0; k<numSemesters;k++){
							priorityStudentConstraint.addTerm(k+1, Yijk[priorityStudId][j][k]);
							nonPriorityStudentExpr.addTerm(k+1, Yijk[nonPriorStudId][j][k]);
						}
						String cname = "REQPRIORITY_Student"+priorityStudId+"_Course"+j+"_NonPriorityStudent"+nonPriorStudId;
						model.addConstr(priorityStudentConstraint, GRB.GREATER_EQUAL,nonPriorityStudentExpr, cname);
					}
				}
			}
		}
		
	}*/
	
	public void maxCourseConstraint() throws Exception{
		int maxCourses = db.getMaxCoursesPerSemester();
		// for each student, for each semester, for each course
		// sum <= maxCourses
		try{
			for(int i=0; i<numStudents; i++){
				for(int k=0; k<numSemesters;k++){
					GRBLinExpr maxCoursesConstraint = new GRBLinExpr();
					for(int j=0; j<numCourses; j++){
						maxCoursesConstraint.addTerm(1, Yijk[i][j][k]);
					}
					String cname = "MAXCOURSE_Student"+i+"_Semester"+k;
					model.addConstr(maxCoursesConstraint, GRB.LESS_EQUAL, maxCourses, cname);
				}
			}
			
		}catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	private void prereqBeforeCourseConstraint() throws Exception{
		try{
			for(int i=0; i<numStudents; i++){
				Student s = Student.get(i+902448900);
				for(int j=0; j<numCourses; j++){
					//Course c = db.getCourse(j+1);
						// only add prereq constraints if student taking class
						// and prereq exists
						ArrayList<Integer> reqCourses = s.getRequestedCourses();
						if(s.getStudentId() == requestingStudentId && shadowMode)
							reqCourses = shadowRequest;
						if(reqCourses.contains(j+1)){
							ArrayList<Integer> prereqs = db.getPrereqs(j+1);
							if(prereqs.size() > 0){
								for(int preJ=0; preJ <prereqs.size(); preJ++){
									GRBLinExpr leftPrereqConstraint = new GRBLinExpr();
									GRBLinExpr rightCourseExpr = new GRBLinExpr();
									int prereqIndex = prereqs.get(preJ)-1;
									for(int k=0; k<numSemesters;k++){
										leftPrereqConstraint.addTerm(k+2, Yijk[i][prereqIndex][k]);
										rightCourseExpr.addTerm(k+1, Yijk[i][j][k]);
									}
									//leftPrereqConstraint.addConstant(1);
									String cname = "PREREQ_Student"+i+"_Course"+j+"_Prereq"+prereqIndex;
									model.addConstr(leftPrereqConstraint, GRB.LESS_EQUAL,rightCourseExpr, cname);
								}
							}
								
						}
					}
				}
			}catch(GRBException e){
				e.printStackTrace();
			}
		}
	
	private void forcePrereqConstraint() throws Exception{
		try{
			for(int i=0; i<numStudents; i++){
				Student s = Student.get(i+902448900);
				for(int j=0; j<numCourses; j++){
					//Course c = db.getCourse(j+1);
						// only add prereq constraints if student taking class
						// and prereq exists
						ArrayList<Integer> reqCourses = s.getRequestedCourses();
						if(s.getStudentId() == requestingStudentId && shadowMode)
							reqCourses = shadowRequest;
						if(reqCourses.contains(j+1)){
							ArrayList<Integer> prereqs = db.getPrereqs(j+1);
							if(prereqs.size() > 0){
								for(int preJ=0; preJ <prereqs.size(); preJ++){
									GRBLinExpr leftPrereqConstraint = new GRBLinExpr();
									GRBLinExpr rightCourseExpr = new GRBLinExpr();
									int prereqIndex = prereqs.get(preJ)-1;
									for(int k=0; k<numSemesters;k++){
										leftPrereqConstraint.addTerm(1, Yijk[i][prereqIndex][k]);
										rightCourseExpr.addTerm(1, Yijk[i][j][k]);
									}
									//leftPrereqConstraint.addConstant(2);
									String cname = "PREREQFORCE_Student"+i+"_Course"+j+"_Prereq"+prereqIndex;
									model.addConstr(leftPrereqConstraint, GRB.GREATER_EQUAL,rightCourseExpr, cname);
								}
							}
								
						}
					}
				}
			}catch(GRBException e){
				e.printStackTrace();
			}
		}
	
	
	public void maxStudentConstraint() throws Exception{
		// max students per course per semester kji
		
		try{
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					Course c = db.getCourse(j+1);
					if(c.getMaxSize() != -1 || db.getMaxStudentsPerCourse() != 0){
						// assign max students from course or globals
						int maxStudents = c.getMaxSize();
						if(maxStudents == -1)
							maxStudents = db.getMaxStudentsPerCourse();
						GRBLinExpr maxStudentsConstraint = new GRBLinExpr();
						for(int i=0; i<numStudents; i++){
							maxStudentsConstraint.addTerm(1, Yijk[i][j][k]);
						}
						String cname = "MAXSTUDENT_Course"+j+"_Semester"+k;
						model.addConstr(maxStudentsConstraint, GRB.LESS_EQUAL, maxStudents, cname);
					}
				}
			}
			
		}catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	public void dontAddCourseConstraint() throws Exception{
		try{
			for(int i=0; i<numStudents; i++){
				Student s = Student.get(i+902448900);
				ArrayList<Integer> reqCourses = s.getRequestedCourses();
				if(s.getStudentId() == requestingStudentId && shadowMode)
					reqCourses = shadowRequest;
				for(int j=0;j< numCourses;j++){
					int courseId = j+1;
					// if the course not in student requests, we don't want it to be 1
					if(!reqCourses.contains(courseId)){
						GRBLinExpr dontAddCourseConstraint = new GRBLinExpr();
						//int courseIndex = s.getCourses().get(j)-1;
						for(int k=0; k<numSemesters;k++){
							dontAddCourseConstraint.addTerm(1, Yijk[i][j][k]);
						}
						String cname = "DONTADDCOURSE_Student"+i+"_Course"+j;
						model.addConstr(dontAddCourseConstraint, GRB.EQUAL, 0, cname);
				
					}
				}
			}
		}catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	public void courseAvailabilityConstraint() throws Exception{
		try{
			for(int i=0; i<numStudents; i++){
				for(int j=0; j<numCourses; j++){
					Course c = db.getCourse(j+1);
					ArrayList<Integer> offerings = c.getAvailability();
					
					for(int index=0;index <= 2; index ++){
						if(!offerings.contains(index)){
							GRBLinExpr courseAvailConstraint = new GRBLinExpr();
							for(int k=0+index; k<numSemesters;k+=3){
								courseAvailConstraint.addTerm(1, Yijk[i][j][k]);
							}
							String cname = "COURSENOTAVAIL_Student"+i+"_Course"+j+"_Term"+index;
							model.addConstr(courseAvailConstraint, GRB.EQUAL, 0, cname);
						}
					}
				}
			}	
		}catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	public void onlyTakeCourseOnceConstraint() throws Exception{
		for(int i=0; i<numStudents; i++){
			for(int j=0;j<numCourses;j++){
				GRBLinExpr courseTakeOnceConstraint = new GRBLinExpr();
				for(int k=0; k<numSemesters;k++){
					courseTakeOnceConstraint.addTerm(1, Yijk[i][j][k]);
				}
				String cname = "TAKEONCE_Student"+i+"_Course"+j;
				model.addConstr(courseTakeOnceConstraint, GRB.LESS_EQUAL, 1, cname);
			}
		}
	}
	
	
	
	public Map<Integer,Integer> runModel() throws Exception{
		try{
			model.optimize();
			
			//GRBExpr obj = model.getObjective();
			
			int status = model.get(GRB.IntAttr.Status);
			
			double[][][] x = model.get(GRB.DoubleAttr.X, Yijk);
			
			
			Map<Integer, Integer> schedule = new HashMap<Integer, Integer>();
			
			int sI = requestingStudentId - 902448900;
			for(int j=0; j<numCourses; j++){
				for(int k=0; k<numSemesters;k++){
					double courseVal = x[sI][j][k];
					if(courseVal > 0){
						schedule.put(j+1, k+1);
					}
				}
			}
			
			// add class with -1 if it couldn't be assigned
			Student s = Student.get(requestingStudentId);
			ArrayList<Integer> reqCourses = s.getRequestedCourses();
			if(s.getStudentId() == requestingStudentId && shadowMode)
				reqCourses = shadowRequest;
			for(int j= 0; j<reqCourses.size(); j++){
				if (schedule.get(reqCourses.get(j)) == null){
					schedule.put(reqCourses.get(j), -1);
				}
			}
			
		/*for(int i=0; i<numStudents; i++){
			int id = 902448900+i;
			System.out.println("Student: "+ id);
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					System.out.print(x[i][j][k] + ",");
				}
				System.out.println();
			}
			System.out.println();
		}*/
			
			if(!shadowMode){
				db.addSchedule(requestingStudentId,schedule);
			} 
			
			return schedule;
			
			
			
		}catch(GRBException e){
			throw new Exception(e);
		}
	}
	
	
}
