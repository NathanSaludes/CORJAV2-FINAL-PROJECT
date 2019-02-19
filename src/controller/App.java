package controller;

import model.StudentDatabaseManager;
import view.LogFileWriter;
import view.View;

public class App {
	
	// DEFAULT APP CONFIG
	public static String inputFilePath 	= "C:\\Users\\Nathaniel Saludes\\Desktop\\input.dat";
	public static String logFilePath	= "C:\\Users\\Nathaniel Saludes\\Desktop\\SRDBMS.dat";
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "corjav2";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/";
	public	static String DB_TABLENAME	= "students";
	
	public static LogFileWriter logger = new LogFileWriter();
	
	public static void main(String[] args) {
		
		// NOTE: application will use the default configurations if no arguments are provided
		if(args.length == 3) {
			DB_NAME 		= args[0];
			inputFilePath 	= args[1];
			logFilePath 	= args[2];
			
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, false);
			View.hr(1);
			
		} else if(args.length == 2) {
			inputFilePath 	= args[0];
			logFilePath 	= args[1];
			
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, false);
			View.hr(1);
			
		} else if(args.length < 2) {
			View.printAppConfig(DB_NAME, DB_TABLENAME, inputFilePath, logFilePath, true);
			View.hr(1);
		}
		
		View view = new View();
		
		handleDatabaseManager(view);
		
		View.hr(2);
		view.programEnd();
		
		// print to log file
		logger.logFile(logFilePath);
	}

	


	public static void handleDatabaseManager(View view) {
		
		StudentDatabaseManager databaseManager = new StudentDatabaseManager(
			JDBC_DRIVER,
			DB_NAME, 
			DB_URL, 
			DB_TABLENAME
		);
		
		String conn = databaseManager.initializeConnection();
		
		view.printDatabaseConfig(
				databaseManager.getJDBC_DRIVER(), 
				databaseManager.getDB_NAME(), 
				databaseManager.getDB_URL()
		);
		
		View.hr(1);
		
		switch(conn) {
			case "Driver Error":
				view.cannotLoadDriver();
				break;
				
			case "SQL Error":
				view.cannotConnectToDatabase();
				break;
				
			case "Connected":
				
				if (databaseManager.hasValidConnection()) {
					if(databaseManager.createDatabase(DB_NAME)) {
						int result = databaseManager.deleteDuplicateTable();
						if(result == 0) {
							view.alertCreateNewTable(DB_TABLENAME);
							View.hr(1);
							
							if(databaseManager.createStudentTable()) {
								handleInputCommandFileReader(databaseManager, view);						
							} else {
								view.createNewTable(false, DB_TABLENAME);
							}
							
						} else if (result == 1) {
							view.alertDuplicateTableDeleted(DB_TABLENAME);
							view.tableDeleted(DB_TABLENAME);
							view.alertCreateNewTable(DB_TABLENAME);
							View.hr(1);
							
							if(databaseManager.createStudentTable()) {
								handleInputCommandFileReader(databaseManager, view);						
							} else {
								view.createNewTable(false, DB_TABLENAME);
							}
							
						} else if (result == -1) {
							view.failedToDeleteAnExistingTable(DB_TABLENAME);
							View.hr(1);
						}
					} else {
						view.failedToCreateDatabase();
					}
				} else {
					view.cannotConnectToDatabase();
				}
				
				break;
		}
	}

	public static void handleInputCommandFileReader(StudentDatabaseManager DatabaseManager, View view) {
		
		if(DatabaseManager.hasValidConnection()) {
			new InputCommandFileReader(inputFilePath, DatabaseManager, DB_TABLENAME);			
		} else {
			view.cannotReadCommandFile();
		}
		
	}
}
