package arms.db;

public class Semester {
	
	private int id;
	private String term;
	private int year;
	
	public Semester(int id) {
		String[] terms = {"Spring", "Summer", "Fall"};
		int startingYear = 2016;
		int startingTermId = 2;
		int termId = (id-1) % 3;
		this.year = ((id-1)+startingTermId / 3) + startingYear;
		this.id = id;
		this.term = terms[termId];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
