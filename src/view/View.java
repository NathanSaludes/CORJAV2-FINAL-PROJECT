package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.Student;

public class View {


	// APP	
	public static void printAppConfig(String DB_NAME, String tableName, String inputFile, String logFile, Boolean defaultConfig) {
		System.out.println("# APP CONFIGURATION");
		if(defaultConfig) {
			System.out.println("(Default) STUDENT DATABASE NAME: " + DB_NAME);
			System.out.println("(Default) STUDENT DATABASE TABLE NAME: " + tableName);
			System.out.println("(Default) INPUT FILE PATH: \"" + inputFile + "\"");
			System.out.println("(Default) LOG FILE PATH: \"" + logFile + "\"");			
		} else {
			System.out.println("STUDENT DATABASE TABLE NAME: " + tableName);
			System.out.println("INPUT FILE PATH: \"" + inputFile + "\"");
			System.out.println("LOG FILE PATH: \"" + logFile + "\"");
		}
	}
	
	public void cannotReadCommandFile() {
		System.out.println("# Unable to read command file.");
	}
	
	public void programEnd() {
		System.out.println("# Program Terminated. Thank you for using the program!");
	}
	
	public void printDatabaseConfig(String JDBC_DRIVER, String DB_NAME, String DB_URL) {
		System.out.println("# DATABASE CONFIGURATION");
		System.out.println("JDBC_DRIVER: " 	+ JDBC_DRIVER);
		System.out.println("DB_NAME: "		+ DB_NAME);
		System.out.println("DB_URL: "		+ DB_URL);
	}
	
	public void cannotLoadDriver() {
		System.out.println("# Unable to load JDBC driver");
	}
	
	public void cannotConnectToDatabase() {
		System.out.println("# Unable to connect to the database. Invalid Connection.");
	}
	
	public void failedToCreateDatabase() {
		System.out.println("# Failed to create a new database.");
		
	}
	
	public void createNewTable(Boolean success, String tableName) {
		if(success) {
			System.out.println("# Successfully created `" + tableName.toLowerCase() + "` table!");
		} else {
			System.out.println("# Failed to create `" + tableName.toLowerCase() + "` table. ");			
		}
	}
	
	public void alertCreateNewTable(String tableName) {
		System.out.println("# Creating new table `" + tableName.toLowerCase() + "`");
	}
	
	public void alertDuplicateTableDeleted(String tableName) {
		System.out.println("# Found an existing duplicate table `" + tableName.toLowerCase() + "`");
	}
	
	public void failedToDeleteAnExistingTable(String tableName) {
		System.out.println("# Failed to delete a duplicate table, `" + tableName + "`");
		
	}
	
	public void tableDeleted(String tableName) {
		System.out.println("# Dropped an existing table `" + tableName.toLowerCase() + "`");
	}
	
	// ---------------------------------------------------------------------------------------------------------------------
	
	
	public void printAStudentRecord(Student stud) {
		System.out.println("ID: " + stud.getStudentId());
    	System.out.println("Name: " + stud.getLastName() + ", " + stud.getFirstName());
    	System.out.println("Course: " + stud.getCourse());
    	System.out.println("Year Level: " + stud.getYearLevel());
    	System.out.println("Units Enrolled: " + stud.getUnitsEnrolled());
	}
	
	public void printAllPossibleCourses() {
		System.out.println("\n# List of Courses: ");
		System.out.println("# [SE] - Software Engineering");
		System.out.println("# [GD] - Game Development");
		System.out.println("# [WD] - Web Development");
		System.out.println("#");
	}
	
	
	
	
	
	// FOR DEBUGGING -----------------------------------------------------------------------------------------------------------------------------------------------
	public void printFileContents(Scanner s, String fileName) {
		hr(1);
		System.out.println("# FILE: " + fileName + "\n");
		while(s.hasNextLine()) {
			System.out.println("|"+ s.nextLine().toString());
		}
		System.out.println("\n# End of file, " + fileName + ".");
		hr(1);
	}
	
	
	

	
	// PRINTS HORIZONTAL RULE -------------------------------------------------------------------------------------------------------------------------------------
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