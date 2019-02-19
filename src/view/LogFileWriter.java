package view;

import java.io.FileWriter;

import model.Student;

public class LogFileWriter {
	
	private static StringBuilder sb = new StringBuilder();
	protected String endl			= System.lineSeparator();
	
	public void logFile(String logFilePath) {
		try{    
			FileWriter fw = new FileWriter(logFilePath);
		   	fw.write(sb.toString());
		   	fw.close();
		   	
		} catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void println(String s) {
		sb.append(s + System.lineSeparator());
	}
	
	public void printAppConfig(String DB_NAME, String tableName, String inputFile, String logFile, Boolean defaultConfig) {
		println("# APP CONFIGURATION");
		if(defaultConfig) {
			println("(Default) STUDENT DATABASE NAME: " + DB_NAME);
			println("(Default) STUDENT DATABASE TABLE NAME: " + tableName);
			println("(Default) INPUT FILE PATH: \"" + inputFile + "\"");
			println("(Default) LOG FILE PATH: \"" + logFile + "\"");			
		} else {
			println("STUDENT DATABASE TABLE NAME: " + tableName + "");
			println("INPUT FILE PATH: \"" + inputFile + "\"");
			println("LOG FILE PATH: \"" + logFile + "\"");
		}
	}

	public void cannotReadCommandFIle() {
		println("# Unable to read command file.");
	}

	public void programEnd() {
		println("# Program Terminated. Thank you for using the program!");
	}

	public void printDatabaseConfig(String JDBC_DRIVER, String DB_NAME, String DB_URL) {
		println("# DATABASE CONFIGURATION");
		println("JDBC_DRIVER: " 	+ JDBC_DRIVER);
		println("DB_NAME: "		+ DB_NAME);
		println("DB_URL: "		+ DB_URL);
	}

	public void cannotLoadDriver() {
		println("# Unable to load JDBC driver");
	}

	public void cannotConnectToDatabase() {
		println("# Unable to connect to the database. Invalid Connection.");
	}

	public void failedToCreateDatabase() {
		println("# Failed to create a new database.");
	}

	public void createNewTable(Boolean success, String tableName) {
		if(success) {
			println("# Successfully created `" + tableName.toLowerCase() + "` table!");
		} else {
			println("# Failed to create `" + tableName.toLowerCase() + "` table. ");			
		}
		
	}

	public void alertCreateNewTable(String tableName) {
		println("# Creating new table `" + tableName.toLowerCase() + "`");
	}

	public void alertDuplicateTableDeleted(String tableName) {
		println("# Found an existing duplicate table `" + tableName.toLowerCase() + "`");
	}

	public void failedToDeleteAnExistingTable(String tableName) {
		println("# Failed to delete a duplicate table, `" + tableName + "`");
		
	}

	public void tableDeleted(String tableName) {
		println("# Dropped an existing table `" + tableName.toLowerCase() + "`");
	}

	public void printAStudentRecord(Student stud) {
		println("ID: " + stud.getStudentId());
    	println("Name: " + stud.getLastName() + ", " + stud.getFirstName());
    	println("Course: " + stud.getCourse());
    	println("Year Level: " + stud.getYearLevel());
    	println("Units Enrolled: " + stud.getUnitsEnrolled());
	}

	public void printAllPossibleCourses() {
		println(endl + "# List of Courses: ");
		println("# [SE] - Software Engineering");
		println("# [GD] - Game Development");
		println("# [WD] - Web Development");
		println("#");
	}

	public void hr(int style) {
		switch(style) {
		case 1:
			println("-------------------------------------------------------------------"
					+ "--------------------------------------------------------------------------------------------------------------------------");
			break;
		case 2:
			println("==================================================================="
					+ "==========================================================================================================================");
			break;
		case 3:
			println("___________________________________________________________________"
					+ "__________________________________________________________________________________________________________________________");
			break;
		default:
			println("+-----------------------------------------------------------------+"
					+ "-----------------------------------------------------------------+-------------------------------------------------------+");
			break;
		}
	}
}
