package model;

import java.sql.*;

public class Student {
	
	// STUDENT BEAN MEMBERS
	private int 	unitsEnrolled;
	private int 	yearLevel;
	private String 	firstName;
	private String 	lastName;
	private String 	course;
	private String 	studentId;
	
	// STUDENT BEAN CONSTRUCTORS	
	public Student(
		String id, 
		String lastName, 
		String firstName, 
		String course, 
		int yearLevel, 
		int unitsEnrolled
	){
		this.unitsEnrolled 	= unitsEnrolled;
		this.yearLevel 		= yearLevel;
		this.firstName 		= firstName;
		this.lastName 		= lastName;
		this.course 		= course;
		this.studentId 		= id;
	}



	// =============================================================================================================================================
	// SETTERS AND GETTERS
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String id) {
		this.studentId = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getYearLevel() {
		return yearLevel;
	}
	public void setYearLevel(int yearLevel) {
		this.yearLevel = yearLevel;
	}
	public int getUnitsEnrolled() {
		return unitsEnrolled;
	}
	public void setUnitsEnrolled(int unitsEnrolled) {
		this.unitsEnrolled = unitsEnrolled;
	}
	
	
	
}
