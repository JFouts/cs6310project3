package arms.db;

import java.util.ArrayList;




public class Course {
	private int id;
	private String name;
	private ArrayList<Integer> prereqs;
	private ArrayList<Integer> availability;
	int maxSize;
	int demand = 0;
	
	public Course(int id, String name, int maxSize){
		this.id = id;
		this.name = name;
		this.maxSize = maxSize;
		
		prereqs = new ArrayList<Integer>();
		availability = new ArrayList<Integer>();
	}
	
	public void addPrereqs(int pId){
		if(!prereqs.contains(pId))
			prereqs.add(pId);
	}
	
	public void addAvailability(int sem){
		if(!availability.contains(sem))
			availability.add(sem);
	}
	
	public void setDemand(int d){
		demand = d;
	}
	
}
