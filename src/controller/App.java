package controller;

import model.Database;
import model.Student;
import model.StudentDatabaseManager;

public class App {
	
	// APP CONFIG
	public static String tableName 		= null;
	public static String inputFilePath 	= null;
	public static String logFilePath	= null;
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "iacademy";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	
	public static void main(String[] args) {
				
		// MAIN ARGUMENTS
		tableName 		= args[0];
		inputFilePath 	= args[1];
		logFilePath 	= args[2];
		
		StudentDatabaseManager DatabaseManager = createDatabaseConnection();
		readCommandFile(DatabaseManager);
	}


	// ===========================================================================================================================================================
	// START STUDENT DATABASE OPERATIONS MANAGER
	public static StudentDatabaseManager createDatabaseConnection() {
		StudentDatabaseManager database = new StudentDatabaseManager(
				JDBC_DRIVER,
				DB_NAME, 
				DB_URL, 
				tableName
		);
		
		return database;
	}

	// READ INPUT COMMAND FILE
	public static void readCommandFile(StudentDatabaseManager DatabaseManager) {
		InputCommandFileReader inputReader = new InputCommandFileReader(inputFilePath, DatabaseManager);
	}
}
