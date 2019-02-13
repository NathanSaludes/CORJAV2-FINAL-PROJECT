package controller;

import java.io.FileNotFoundException;

import controller.InputFileReader;
import model.StudentDatabase;
import model.Student;

public class App {
	
	// APP CONFIG
	public static String tableName 		= null;
	public static String inputFilePath 	= null;
	public static String logFilePath	= null;
	
	private static String JDBC_DRIVER	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "saludes-se21-db";
	private static String DB_URL		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	
	public static void main(String[] args) {
				
		// MAIN ARGUMENTS
		tableName 		= args[0];
		inputFilePath 	= args[1];
		logFilePath 	= args[2];
		
		// CREATE A DATABASE CONNECTION
		StudentDatabase database = new StudentDatabase(
			JDBC_DRIVER, DB_NAME, DB_URL, tableName
		);
		
		
		// READ INPUT FILE
		try {
			InputFileReader ifr = new InputFileReader(inputFilePath);
			ifr.readFile();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
