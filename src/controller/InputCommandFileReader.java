package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Student;
import model.StudentDatabaseManager;
import view.View;

public class InputCommandFileReader {
	
	private StudentDatabaseManager DatabaseManager		= null;
	private String fileName 							= null;
	private String filePath 							= null;
	private File inputCommandFile						= null;
	private View view									= null;
	private static Student student						= null;

	// CONSTRUCTOR
	public InputCommandFileReader(String path, StudentDatabaseManager DatabaseManager) {
		// Initialize class variables
		this.DatabaseManager	= DatabaseManager;
		this.filePath 			= path;
		this.fileName 			= path.substring(path.lastIndexOf('\\') + 1);
		this.inputCommandFile	= new File(this.filePath);
		this.view				= new View();
		
		// Print the path and file name
		System.out.println("# FILE CONFIGURATION");
		System.out.println("PATH: \"" + this.filePath + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"");
		View.hr(1);
		
		printFileContents();
		readFile();
	}
	
	public void printFileContents() {
		try {
			Scanner scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			System.out.println("# Opening File...Please wait.");
			System.out.println("# Printing file contents...");
			// print file contents using the view class
			this.view.printFileContents(scanner, this.fileName);
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found...");
		}
		
		
	}
	
	
	public void readFile() {		
		// Open file
		try {
			Scanner scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			System.out.println("# Opening File...");
			System.out.println("# Reading File...");
			View.hr(2);
			
			commandReader(scanner);
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found...");
		}
		
		
	}
	
	public void commandReader(Scanner scanner) {
		String input = null;
		
		while(scanner.hasNext()) {
			switch(input = scanner.next().toString().toUpperCase().trim()) {
			case "A":
				this.view.printUserEntry(input);
				commandA(scanner);
				break;
			case "L":
				this.view.printUserEntry(input);
				commandL();
				break;
			case "S":
				this.view.printUserEntry(input);
				commandS();
				break;
			case "D":
				this.view.printUserEntry(input);
				commandD();
				break;
			case "R":
				this.view.printUserEntry(input);
				commandR();
				break;
			case "P":
				this.view.printUserEntry(input);
				commandP();
				break;
			case "Q":
				this.view.printUserEntry(input);
				commandQ();
				break;
			default:
				// skip
				break;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	// USER COMMANDS OPERATIONS ======================================================================================
	private void commandA(Scanner scanner) {		
		int parameterIndex;
		
		String 	firstName		= null;
		String 	lastName		= null;
		String 	course			= null;
		String 	studentId		= null;
		int 	unitsEnrolled	= 0;
		int 	yearLevel		= 0;
		
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
		
		
		// print the student
		this.view.printStudent(student);
		
		System.out.println("\n# Created a new student!");
		
		// insert student to database
		DatabaseManager.insertRecord(student);
		View.hr(1);
	}
	
	private void commandQ() {
		// TODO Auto-generated method stub
	}

	private void commandP() {
		// TODO Auto-generated method stub
		
	}

	private void commandR() {
		// TODO Auto-generated method stub
		
	}

	private void commandD() {
		// TODO Auto-generated method stub
		
	}

	private void commandS() {
		// TODO Auto-generated method stub
		
	}

	private void commandL() {
		// TODO Auto-generated method stub
		
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
