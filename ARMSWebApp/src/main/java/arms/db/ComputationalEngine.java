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
			
			X = model.addVar(0, GRB.INFINITY, 1, GRB.INTEGER, "X");
			
			model.update();
			
		} catch(GRBException e){
			e.printStackTrace();
		}
	}
	
	public void processStudentRequests(){
		setObjectiveFunc();
		generateConstraints();
		//runModel();
	}
	
	// to be implemented
	/*public void processShadowRequest(int studentId, StudentRequest shadowReq) {
		
	}*/
	
	private void setObjectiveFunc(){
		try	{
			GRBLinExpr obj = new GRBLinExpr();
			obj.addTerm(1, X);
			model.setObjective(obj,GRB.MAXIMIZE);
		} catch(GRBException e){
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
		//maxStudentConstraint(); 
		
		
		
		// maximize sum of student requests across all semesters
		//objectiveFuncConstraint();
		
		// courses not requested should equal zero
		//dontAddCourseConstraint();
		
		//
		//courseAvailabilityConstraint();
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
	
	
}
