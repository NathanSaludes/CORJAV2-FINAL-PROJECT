package controller;

import exceptions.StudentNotFoundException;
import model.StudentDatabaseManager;
import view.View;

public class App {
	
	// DEFAULT APP CONFIG
	public static String tableName 		= "students";
	public static String inputFilePath 	= "C:\\Users\\Nathaniel Saludes\\Desktop\\testInputFile.txt";
	public static String logFilePath	= "C:\\Users\\Nathaniel Saludes\\Desktop\\testLogFile.txt";
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "iacademy";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	public static void main(String[] args) throws StudentNotFoundException {
		
		// If main arguments are provided, it will override the application configurations
		if(args.length == 3) {
			tableName 		= args[0];
			inputFilePath 	= args[1];
			logFilePath 	= args[2];
			
			View.printAppConfig(tableName, inputFilePath, logFilePath, false);
			
		} else {
			// If main arguments are not provided, the application will use the default configuration
			View.printAppConfig(tableName, inputFilePath, logFilePath, true);
		}
		
		// create a database manager
		StudentDatabaseManager databaseManager = handleDatabaseManager();
		
		handleInputCommandFileReader(databaseManager);			
		
	}


	// ===========================================================================================================================================================
	// returns a student database manager object
	public static StudentDatabaseManager handleDatabaseManager() {
		StudentDatabaseManager database = new StudentDatabaseManager(
			JDBC_DRIVER,
			DB_NAME, 
			DB_URL, 
			tableName
		);
		
		return database;
	}

	// handles 
	@SuppressWarnings("static-access")
	public static void handleInputCommandFileReader(StudentDatabaseManager DatabaseManager) throws StudentNotFoundException {
		
		//before reading input command file, check if the database connection is valid.
		if(DatabaseManager.hasValidConnection()) {
			new InputCommandFileReader(inputFilePath, DatabaseManager);			
		} else {
			System.out.println("# Unable to read command file. invalid database connection.");
			new View().quitCommandMessage();
		}
		
	}
}
