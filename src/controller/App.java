package controller;

import model.StudentDatabaseManager;
import view.View;

public class App {
	
	// DEFAULT APP CONFIG
	public static String inputFilePath 	= "C:\\Users\\Nathaniel Saludes\\Desktop\\input.dat";
	public static String logFilePath	= "C:\\Users\\Nathaniel Saludes\\Desktop\\testLogFile.txt";
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "default";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/";
	public	static String DB_TABLENAME	= "students";
	
	public static void main(String[] args) {
		
		// If main arguments are provided, it will override the application configurations
		if(args.length == 3) {
			DB_NAME 		= args[0];
			inputFilePath 	= args[1];
			logFilePath 	= args[2];
			
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, false);
			
		} else if(args.length == 2) {
			inputFilePath 	= args[0];
			logFilePath 	= args[1];
			
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, false);
		} else {
			// If main arguments are not provided, the application will use the default configuration
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, true);
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
			DB_TABLENAME
		);
		
		return database;
	}

	// handles the reading of input command file
	@SuppressWarnings("static-access")
	public static void handleInputCommandFileReader(StudentDatabaseManager DatabaseManager) {
		
		//before reading input command file, check if the database connection is valid.
		if(DatabaseManager.hasValidConnection()) {
			new InputCommandFileReader(inputFilePath, DatabaseManager, DB_TABLENAME);			
		} else {
			System.out.println("# Unable to read command file. invalid database connection.");
			new View().quitCommandMessage();
		}
		
	}
}
