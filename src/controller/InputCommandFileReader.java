package controller;

import java.io.FileReader;
import model.StudentDatabaseManager;
import model.Student;

public class InputCommandFileReader {
	
	private String fileName 							= null;
	private String filePath 							= null;
	private StudentDatabaseManager DatabaseManager		= null;

	// CONSTRUCTOR
	public InputCommandFileReader(String path, StudentDatabaseManager DatabaseManager) {
		this.filePath 			= path;
		this.fileName 			= path.substring(path.lastIndexOf('\\') + 1);
		this.DatabaseManager	= DatabaseManager;
		
		System.out.println("PATH: \"" + this.filePath + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"\n");
		
		createStudentTest(); // RUN TEST
	}
	
	public void createStudentTest() {
		Student student = new Student("201502034", "Saludes", "Nathaniel", "BS SE", 4, 165);
		DatabaseManager.insertRecord(student);
	}
	
	
}
