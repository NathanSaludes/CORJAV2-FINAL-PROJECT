package controller;

import java.io.FileNotFoundException;

import controller.InputFileReader;
import model.StudentDatabase;
import model.Student;

public class App {
	
	public static String tableName 		= null;
	public static String inputFilePath 	= null;
	public static String logFilePath	= null;
	
	
	public static void main(String[] args) {
		
		// PROGRAM ARGUMENTS
		tableName 		= args[0];
		inputFilePath 	= args[1];
		logFilePath 	= args[2];
		
		// CREATE A DATABASE CONNECTION
		StudentDatabase database = new StudentDatabase(tableName);
		
		// READ INPUT FILE
		/*try {
			InputFileReader ifr = new InputFileReader(inputFilePath);
			ifr.readFile();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	
							
			
		// STUDENT OBJECT SAMPLE
//		Student student = new Student("201502034","Saludes","Nathaniel","BS-SE",4,123);
		
	}

}
