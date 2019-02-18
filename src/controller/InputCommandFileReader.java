package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Student;
import model.StudentDatabaseManager;
import view.View;
import exceptions.*;

public class InputCommandFileReader {
	
	private static StudentDatabaseManager DatabaseManager	= null;
	private String fileName 								= null;
	private String filePath 								= null;
	private File inputCommandFile							= null;
	private View view										= null;
	private static Student student							= null;
	private static String DB_TABLENAME						= null;

	// CONSTRUCTOR
	public InputCommandFileReader(String path, StudentDatabaseManager DatabaseManager, String tableName) {
		
		// Initialize class variables
		InputCommandFileReader.DatabaseManager	= DatabaseManager;
		this.filePath 			= path;
		this.fileName 			= path.substring(path.lastIndexOf('\\') + 1);
		this.inputCommandFile	= new File(this.filePath);
		this.view				= new View();
		this.DB_TABLENAME		= tableName;
		
		// Print the path and file name
		System.out.println("# FILE CONFIGURATION");
		System.out.println("PATH: \"" + this.filePath + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"");
		View.hr(1);
		
		// printFileContents();
		readFile();
	}
	
	public void readFile() {
		Scanner scanner = null;
		
		try {
			// TODO: Resource Leak: closeable value for Scanner
			
			// open file and set the delimitter to next line character ('\n')
			scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			System.out.println("# Opening File...");
			System.out.println("# Reading File...");
			View.hr(2);
			
			// read user commands
			commandReader(scanner);
			
			// close resources
			scanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found...");
		}
	}
	
	public void commandReader(Scanner scanner) {
		String input = null;
		boolean Quit = false; // to stop the loop
		
		while(scanner.hasNext() && !Quit) {
			switch(input = scanner.next().toString().toUpperCase().trim()) {
			case "A": //student entry
				this.view.printUserEntry(input);
				commandA(scanner);
				break;
			case "L": //display all
				this.view.printUserEntry(input);
				commandL();
				break;
			case "S": //search and display
				this.view.printUserEntry(input);
				commandS(scanner);
				break;
			case "D": //search and delete
				this.view.printUserEntry(input);
				commandD(scanner);
				break;
			case "R": //generate report
				this.view.printUserEntry(input);
				commandR(scanner);
				break;
			case "P": //erase all records
				this.view.printUserEntry(input);
				commandP();
				break;
			case "Q":
				this.view.printUserEntry(input);
				commandQ(scanner);
				Quit = true;
				break;
			default:
				// skip
				break;
			}
		}
	}
	
	// USER COMMANDS OPERATIONS ======================================================================================
	private void commandA(Scanner scanner) {
		// determines the INDEX of the parameter required
		int parameterIndex;
		
		// temporary containers for student model fields
		String 	firstName		= null;
		String 	lastName		= null;
		String 	course			= null;
		String 	studentId		= null;
		int 	unitsEnrolled	= 0;
		int 	yearLevel		= 0;
		
		try {
			// get all required inputs for each parameter
			for(parameterIndex = 1; parameterIndex <= 6 && scanner.hasNext(); parameterIndex++) {
				switch (parameterIndex) {
				case 1: // id
					studentId = scanner.next().trim();
					
					char studId[] = studentId.toCharArray();
					
					if(studId.length != 11) {
						throw new IDSizeNotValidException();
					} else {
						for(char letter : studId) {
							if(!Character.isDigit(letter)) {
								throw new IDFormatInvalidException();
							}
						}						
					}
					
					
					break;
				case 2: // last name
					lastName = scanner.next().trim();
					break;
				case 3: // first name
					firstName = scanner.next().trim();
					break;
				case 4: // course
					course = scanner.next().toString().trim();
					
					if(!course.equalsIgnoreCase("SE")) {
						if(!course.equalsIgnoreCase("GD")) {
							if(!course.equalsIgnoreCase("WD")) {
								throw new CourseFormatInvalidException();
							}
						}
					}
					
					break;
				case 5: // year level
					yearLevel = Integer.parseInt(scanner.next().trim());
					break;
				case 6: // units enrolled
					unitsEnrolled = Integer.parseInt(scanner.next().trim());
					break;
					
				default:
					break;
				}
			}
			
			// create a new student object
			student = new Student(
					studentId,
					lastName,
					firstName,
					course,
					yearLevel,
					unitsEnrolled
			);
			
			// print the student
			this.view.printStudent(student);
			
			System.out.println("\n# Created a new student!");
			System.out.println("# Inserting student record into the database...");
			
			// insert student to database
			DatabaseManager.insertRecord(student);
			
		} catch (IDFormatInvalidException e) {
			System.out.println("ERROR: " + e.getMessage());
			System.out.println("\n# Unable to create/add new record.");
			System.out.println("# student ID must not have non-digit characters");
			
		} catch (IDSizeNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			System.out.println("\n# Unable to create/add new record.");
			System.out.println("# Student ID should be composed of 11 digits");
			
		} catch (CourseFormatInvalidException e) {
			System.out.println("ERROR: " + e.getMessage());
			view.printAllPossibleCourses();
			System.out.println("# Unable to create/add new record.");
			
		} finally {
			View.hr(1);
		}
		
		
		
		
		
		
		
	}
	
	private void commandQ(Scanner s) {
		if(DatabaseManager.terminateConnection()) {
			// print message
			View.quitCommandMessage();
		}
	}

	private void commandP() {
		// purge table records
		DatabaseManager.clearTable();
	}

	private void commandR(Scanner scanner) {
		String studentCourse = null;
		
		if(scanner.hasNext()) {
			studentCourse = scanner.next().toString().toUpperCase().trim(); // convert to String -> convert all to upper case -> trim trailing white space/s.
			System.out.println(studentCourse + "\n");
			DatabaseManager.generateReports(studentCourse);
		}		
	}

	private void commandD(Scanner scanner) {
		try {
			String studId = scanner.next().trim();
			
			System.out.println("# Please wait...");
			System.out.println("# Searching for student ID \'" + studId + "\' \n");
			
			String preparedStatement = "SELECT * FROM " + DB_TABLENAME + " WHERE studId=?";
			
			// if true, it will proceed on deleting the studentRecord
			if(DatabaseManager.searchStudent(studId, false, preparedStatement, true)) {
				if(DatabaseManager.deleteRecord(studId)) {
					View.deleteRecordsMessage();
					View.hr(1);
				}
			}
			
		} catch (StudentNotFoundException snfe) {
			System.out.println(snfe.getMessage());
			View.hr(1);
		}
		
	}

	private void commandS(Scanner scanner) {
		// search a student entry via studentID or lastName and display full info
		try {
			String studIdOrLastName = scanner.next().toString().trim();
			String preparedStatement = "SELECT * FROM " + DB_TABLENAME + " WHERE studId=? OR lastName=?";
			
			System.out.println("# Please wait...");
			System.out.println("# Searching for student record \'" + studIdOrLastName + "\' \n");
			
			DatabaseManager.searchStudent(studIdOrLastName, true, preparedStatement, false);
			View.hr(1);
		} catch (StudentNotFoundException snfe) {
			System.out.println(snfe.getMessage());
			View.hr(1);
		}
	}

	private void commandL() {
		DatabaseManager.listAllStudents();
	}

	
	
	
	
	
	
	

	// ====================================================================================================================================================
	// TEST METHODS: insert a new student record 
	public void Test1() {
		Student student = null;
		student = new Student("201502034", "Saludes", "Nathaniel", "BS SE", 4, 165);
		DatabaseManager.insertRecord(student);
		student = new Student("201546895", "Latoja", "Paolo", "BS SE", 4, 160);
		DatabaseManager.insertRecord(student);
	}
	
	public void printFileContents() {
		try {
			// TODO: Resource Leak: closeable value for Scanner
			Scanner scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			System.out.println("# Opening File...Please wait.");
			System.out.println("# Printing file contents...");
			
			// print file contents using the view class
			this.view.printFileContents(scanner, this.fileName);
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found...");
		}
	}
}
