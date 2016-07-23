package arms.db;




public class Course {
    private int id;
    private String name;
    private int maxSize;
    private int[] semesters;
    private int[] prereqs;
    
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
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public int[] getSemesters() {
		return semesters;
	}
	public void setSemesters(int[] semesters) {
		this.semesters = semesters;
	}
	public int[] getPrereqs() {
		return prereqs;
	}
	public void setPrereqs(int[] prereqs) {
		this.prereqs = prereqs;
	}
}
