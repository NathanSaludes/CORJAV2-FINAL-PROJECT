package controller;

import model.StudentDatabase;
import model.Student;

public class App {
	
	public static String tableName 	= null;
	public static String inputFilePath 	= null;
	public static String logFilePath	= null;
	
	
	public static void main(String[] args) {
		
		// PROGRAM ARGUMENTS
		tableName 		= args[0];
		inputFilePath 	= args[1];
		logFilePath 	= args[2];
							
		// STUDENT DATABASE CONNECTION OBJECT
		StudentDatabase SDBConn = new StudentDatabase(tableName);
			
		// STUDENT OBJECT SAMPLE
		Student student = new Student(
			"201502034", 
			"Saludes", 
			"Nathaniel", 
			"BS-SE", 
			4, 
			123
		);
		
		// CREATES NEW STUDENT TABLE
		 SDBConn.createStudentTable();
	}

}
