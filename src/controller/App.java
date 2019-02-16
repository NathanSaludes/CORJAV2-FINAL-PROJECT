package controller;

import exceptions.StudentNotFoundException;
import model.StudentDatabaseManager;
import view.View;

public class App {
	
	// DEFAULT APP CONFIG
	public static String tableName 		= "students";
	public static String inputFilePath 	= "C:\\Users\\Guest Account\\Desktop\\Github Project Repository\\testInputFile.txt";
	public static String logFilePath	= "C:\\Users\\Guest Account\\Desktop\\Github Project Repository\\testLogFile.txt";
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "saludes-se21-db";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	public static void main(String[] args) throws StudentNotFoundException {
		
		// If main arguments are provided, it will override the application configurations
		if(args.length == 3) {
			tableName 		= args[0];
			inputFilePath 	= args[1];
			logFilePath 	= args[2];	
		} else {
			// If main arguments are not provided, the application will use the default configuration
			new View().printDefaultAppConfig(tableName, inputFilePath, logFilePath);
		}
		
		// create a database
		StudentDatabaseManager DatabaseManager = createDatabaseConnection();
		
		if(DatabaseManager.hasValidConnection()) {
			readCommandFile(DatabaseManager);			
		}
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
	public static void readCommandFile(StudentDatabaseManager DatabaseManager) throws StudentNotFoundException {
		new InputCommandFileReader(inputFilePath, DatabaseManager);
	}
}
