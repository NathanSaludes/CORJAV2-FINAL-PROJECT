package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.Student;

public class View {

	public void printStudent(Student s) {
		// commandA
		System.out.println("last name: " + s.getLastName());
		System.out.println("first name: " + s.getFirstName());
		System.out.println("student id: " + s.getStudentId());
		System.out.println("course: " + s.getCourse());
		System.out.println("year level: " + s.getYearLevel());
		System.out.println("units enrolled: " + s.getUnitsEnrolled());
	}
	
	public void commandL() {
		
	}
	
	public void commandS(String studentLastNameOrID) {
		// searches for a student entry in the database and displays information
		// search options are: studentID or studentLastName
		
		
		System.out.println("\nReading command . . . . "); //maybe this is before function calls
		System.out.println("\nPlease wait. . . searching for student record " + studentLastNameOrID);
		
		//if record found, show records for that student. Else, records not found or error/exception input
	}
	
	public static void searchAndDeleteFoundMessage(String studentID) {
		System.out.println("\nStudent record " + studentID + " found and successfully deleted!");
	}
	
	public static void searchAndDeleteNotFoundMessage(String studentID) {
		// commandD
		// if found, print otherwise search then show error message
		System.out.println("\nStudent record " + studentID + " not found");
		
	}
	
	public static void generateReportMessage() {
		// generates report option based on the given criteria
		// criterias: ALL or Course
		// commandR
		
	}
	
	public void deleteStudentRecordMessage() {
		// deletes a specific student entry record
		System.out.println("# Student record successfully deleted!");
	}

	public static void deleteRecordsMessage() {
		// Erases all records in the database
		System.out.println("\nDatabase Table Records deleted");
	}
	
	public static void quitCommandMessage() {
		// terminates the application
		System.out.println("# Quitting...");
		System.out.println("\nProgram terminated. Thank you for using the system.");
		hr(2);
	}
	
	public void printUserEntry(String input) {
		System.out.println("COMMAND: " + input);
		// System.out.println("# Reading command " + input + "...");
		System.out.println();
	}
	
	public static void printAppConfig(String tableName, String inputFile, String logFile, Boolean defaultConfig) {
		System.out.println("# APP CONFIGURATION");
		if(defaultConfig) {
			System.out.println("(Default) STUDENT DATABASE TABLE NAME: " + tableName);
			System.out.println("(Default) INPUT FILE PATH: \"" + inputFile + "\"");
			System.out.println("(Default) LOG FILE PATH: \"" + logFile + "\"");			
		} else {
			System.out.println("STUDENT DATABASE TABLE NAME: " + tableName);
			System.out.println("INPUT FILE PATH: \"" + inputFile + "\"");
			System.out.println("LOG FILE PATH: \"" + logFile + "\"");
		}
	}
	
	
	public void printAStudentRecord(ResultSet res) throws SQLException {
		System.out.println("ID: " + res.getString("studId"));
    	System.out.println("Name: " + res.getString("lastName")+", "+res.getString("firstName"));
    	System.out.println("Course: " + res.getString("course"));
    	System.out.println("Year Level: " + res.getString("yearLevel"));
    	System.out.println("Units Enrolled: " + res.getString("unitsEnrolled"));
	}
	
	// DEBUGGING METHODS ===========================================================================================================================================
	public void printFileContents(Scanner s, String fileName) {
		hr(1);
		System.out.println("# FILE: " + fileName + "\n");
		while(s.hasNextLine()) {
			System.out.println("|"+ s.nextLine().toString());
		}
		System.out.println("\n# End of file, " + fileName + ".");
		hr(1);
	}
	
	
	

	
	// PRINTS HORIZONTAL RULE
	public static void hr(int style) {
		switch(style) {
		case 1:
			System.out.println("-------------------------------------------------------------------"
					+ "--------------------------------------------------------------------------------------------------------------------------");
			break;
		case 2:
			System.out.println("==================================================================="
					+ "==========================================================================================================================");
			break;
		case 3:
			System.out.println("___________________________________________________________________"
					+ "__________________________________________________________________________________________________________________________");
			break;
		default:
			System.out.println("+-----------------------------------------------------------------+"
					+ "-----------------------------------------------------------------+-------------------------------------------------------+");
			break;
		}
	}
}