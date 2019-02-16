package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Student;
import model.StudentDatabaseManager;
import view.View;
import exceptions.StudentNotFoundException;

public class InputCommandFileReader {
	
	private static StudentDatabaseManager DatabaseManager	= null;
	private String fileName 								= null;
	private String filePath 								= null;
	private File inputCommandFile							= null;
	private View view										= null;
	private static Student student							= null;

	// CONSTRUCTOR
	public InputCommandFileReader(String path, StudentDatabaseManager DatabaseManager) throws StudentNotFoundException {
		
		// Initialize class variables
		InputCommandFileReader.DatabaseManager	= DatabaseManager;
		this.filePath 			= path;
		this.fileName 			= path.substring(path.lastIndexOf('\\') + 1);
		this.inputCommandFile	= new File(this.filePath);
		this.view				= new View();
		
		// Print the path and file name
		System.out.println("# FILE CONFIGURATION");
		System.out.println("PATH: \"" + this.filePath + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"");
		View.hr(1);
		
		// printFileContents();
		readFile();
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
	
	
	public void readFile() throws StudentNotFoundException {
		Scanner scanner = null;
		
		try {
			// open file and set the delimitter to 
			// TODO: Resource Leak: closeable value for Scanner
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
	
	public void commandReader(Scanner scanner) throws StudentNotFoundException {
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
				commandS();
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
		
		// determines the index of parameter being taken
		int parameterIndex;
		
		// temporary containers for student model fields
		String 	firstName		= null;
		String 	lastName		= null;
		String 	course			= null;
		String 	studentId		= null;
		int 	unitsEnrolled	= 0;
		int 	yearLevel		= 0;
		
		// get all required inputs for each parameter
		for(parameterIndex = 1; parameterIndex <= 6 && scanner.hasNext(); parameterIndex++) {
			switch (parameterIndex) {
			case 1: 
				studentId = scanner.next().trim();
				break;
			case 2:
				lastName = scanner.next().trim();
				break;
			case 3:
				firstName = scanner.next().trim();
				break;
			case 4:
				course = scanner.next().trim();
				break;
			case 5:
				yearLevel = Integer.parseInt(scanner.next().trim());
				break;
			case 6:
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
		
		// TODO
		// check if the studentID is unique based on the records in the database table
		// if not then its either throw a custom exception for duplicate student entry
		// or overwrite the existing student record with the latest student record entry
		
		
		// print the student
		this.view.printStudent(student);
		
		System.out.println("\n# Created a new student!");
		System.out.println("# Inserting student record into the database...");
		
		// insert student to database
		DatabaseManager.insertRecord(student);
		
		View.hr(1);
	}
	
	private void commandQ(Scanner s) {
		if(DatabaseManager.terminateConnection()) {
			// print message
			View.quitCommandMessage();
		}
	}

	private void commandP() {
		DatabaseManager.clearTable();
		View.deleteRecordsMessage();
	}

	private void commandR(Scanner scanner) {
		// TODO Generate a report based on given criteria
		// Criteria "ALL" or by specific course SE/GD/WD
		String criteriaChoice = null;
		
		criteriaChoice = scanner.next().trim();
		
		switch (criteriaChoice) 
		{
			case "ALL":
				//prints all??
				DatabaseManager.listAll();
				break;
			
			case "SE":
				DatabaseManager.listAllByCourse("SE");
				break;
				
			case "GD":
				DatabaseManager.listAllByCourse("SE");
				break;
			
			case "WD":
				DatabaseManager.listAllByCourse("SE");
				break;
			
			default: 
				//skips
				System.out.println("Error, criteria choice wrong");
				break;
		}	
		
	}

	private void commandD(Scanner scanner)throws StudentNotFoundException {
		// TODO Auto-generated method stub
		// search and delete
		String studId = null;
		studId = scanner.next().trim();
		
		System.out.println("\nPlease wait . . . searching for student record " + studId);
		boolean result = DatabaseManager.deleteRecord(studId);
		
		if (result == true)
		{
			//print message record deleted
			View.deleteStudentRecordMessage();
		}
		else {
			throw new StudentNotFoundException();
		}
			
		
		
	}

	private void commandS() {
		// TODO Auto-generated method stub
		// search a student entry via studentID or lastName and display full info
		// readRecords()
		
	}

	private void commandL() {
		DatabaseManager.listAll();
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
}
