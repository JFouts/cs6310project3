package arms.db;

import java.util.ArrayList;




public class Course {
	private int id;
	private String name;
	private ArrayList<Integer> prereqs;
	private ArrayList<Integer> availability;
	int maxSize;
	int demand = 0;
	
	public Course(int id, String name){
		this.id = id;
		this.name = name;
		
		prereqs = new ArrayList<Integer>();
		availability = new ArrayList<Integer>();
	}
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getPrereqs() {
		return prereqs;
	}

	public void setPrereqs(ArrayList<Integer> prereqs) {
		this.prereqs = prereqs;
	}

	public ArrayList<Integer> getAvailability() {
		return availability;
	}

	public void setAvailability(ArrayList<Integer> availability) {
		this.availability = availability;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getDemand() {
		return demand;
	}
	
}
