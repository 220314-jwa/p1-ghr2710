package dev.roberts;

public class Person {
	
	private String fName, lName;
	
	Person(){
		this.fName = "FIRST";
		this.lName = "LAST";
	}
	
	Person(String fName, String lName){
		this.fName = fName;
		this.lName = lName;
	}
	
	public String getName() {
		return fName + " " + lName;
	}
	
	public void setFirst(String newFirst) {
		fName = newFirst;
	}
	
	public void setLast(String newLast) {
		fName = newLast;
	}
}
