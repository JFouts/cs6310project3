package arms.db;

import java.util.ArrayList;

import gurobi.*;

public class ComputationalEngine {
	private GRBEnv env;
	private GRBModel model;
	private GRBVar[][][] Yijk;
	private GRBVar X;
	
	ARMDatabase db;
	
	int numStudents,numCourses,numSemesters;
	
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
	
	public void processStudentRequests(){
		setObjectiveFunc();
		generateConstraints();
		runModel();
	}
	
	// to be implemented
	/*public void processShadowRequest(int studentId, StudentRequest shadowReq) {
		
	}*/
	
	private void setObjectiveFunc(){
		try	{
			GRBLinExpr objFuncConstraint = new GRBLinExpr();
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					for(int i=0; i<numStudents; i++){
						objFuncConstraint.addTerm(1, Yijk[i][j][k]);
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
		// max courses per semester per student
		if(db.getMaxCoursesPerSemester() != -1){	
			maxCourseConstraint();
		}
		
		// set prereq constraints for all courses
		prereqConstraint();
		
		// max students per course per semester (if class has a max)
		maxStudentConstraint(); 
		
		// courses not requested should equal zero
		dontAddCourseConstraint();
		

		// semesters available
		courseAvailabilityConstraint();
				
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
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
	
	private void prereqConstraint() throws Exception{
		try{
			for(int i=0; i<numStudents; i++){
				Student s = Student.get(i+902448900);
				for(int j=0; j<numCourses; j++){
					Course c = db.getCourse(j+1);
						// only add prereq constraints if student taking class
						// and prereq exists
						if(s.getRequestedCourses().contains(j+1)){
							ArrayList<Integer> prereqs = db.getPrereqs(j+1);
							if(prereqs.size() > 0){
								for(int preJ=0; preJ <prereqs.size(); preJ++){
									GRBLinExpr leftPrereqConstraint = new GRBLinExpr();
									GRBLinExpr rightCourseExpr = new GRBLinExpr();
									int prereqIndex = prereqs.get(preJ)-1;
									for(int k=0; k<numSemesters;k++){
										leftPrereqConstraint.addTerm(k+1, Yijk[i][prereqIndex][k]);
										rightCourseExpr.addTerm(k+1, Yijk[i][j][k]);
									}
									leftPrereqConstraint.addConstant(1);
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
	
	public void maxStudentConstraint() throws Exception{
		// max students per course per semester kji
		
		try{
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					Course c = db.getCourse(j+1);
					if(c.getMaxSize() != -1 || db.getMaxStudentsPerCourse() != -1){
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
	
	
	
	public void runModel(){
		try{
			model.optimize();
			
			//GRBExpr obj = model.getObjective();
			
			double[][][] x = model.get(GRB.DoubleAttr.X, Yijk);
			for(int k=0; k<numSemesters;k++){
				for(int j=0; j<numCourses; j++){
					for(int i=0; i<numStudents; i++){
						System.out.print(x[i][j][k]);
					}
				}
			}
			
			//GRBModel w =  model.fixedModel();
			//w.get(GRB.DoubleAttr.ObjVal);			
			/*double x = 0;
			
			if(status == GRB.Status.INFEASIBLE)
				System.out.println("Infeasible");
			else{
			    x = model.get(GRB.DoubleAttr.ObjVal);
				System.out.printf("X=%.2f", x);
				System.out.println();
			}*/
			
		}catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	
}
