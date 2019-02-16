package controller;

import model.StudentDatabaseManager;
import view.View;

public class App {
	
	// DEFAULT APP CONFIG
	public static String tableName 		= "students";
	public static String inputFilePath 	= "C:\\Users\\Guest Account\\Desktop\\Github Project Repository\\testInputFile.txt";
	public static String logFilePath	= "C:\\Users\\Guest Account\\Desktop\\Github Project Repository\\testLogFile.txt";
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "iacademy";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	public static void main(String[] args) {
		
		// If main arguments are provided, it will override the application configurations
		if(args.length == 3) {
			tableName 		= args[0];
			inputFilePath 	= args[1];
			logFilePath 	= args[2];	
		} else {
			// If main arguments are not provided, the application will use the default configuration
			new View().printDefaultAppConfig(tableName, inputFilePath, logFilePath);
		}
		
		// create a database manager
		StudentDatabaseManager databaseManager = handleDatabaseManager();
		handleInputCommandFileReader(databaseManager);			
		
	}


	// ===========================================================================================================================================================
	// START STUDENT DATABASE OPERATIONS MANAGER
	public static StudentDatabaseManager handleDatabaseManager() {
		StudentDatabaseManager database = new StudentDatabaseManager(
			JDBC_DRIVER,
			DB_NAME, 
			DB_URL, 
			tableName
		);
		
		return database;
	}

	// READ INPUT COMMAND FILE
	@SuppressWarnings("static-access")
	public static void handleInputCommandFileReader(StudentDatabaseManager DatabaseManager) {
		
		//before reading input command file, check if db connection is valid.
		if(DatabaseManager.hasValidConnection()) {
			new InputCommandFileReader(inputFilePath, DatabaseManager);			
		} else {
			System.out.println("# Unable to read command file. invalid database connection.");
			new View().quitCommandMessage();
		}
		
	}
}
