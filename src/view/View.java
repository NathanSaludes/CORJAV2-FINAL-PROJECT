package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import model.Student;

public class View {

	public void commandA() throws IOException {
		// initial db entry
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int index = 0;
		Student ss[] = new Student[index + 1]; //di pa ko sure dito, kailangan ko dynamic to eh pero bawal daw
		String choice;
		
		do {
			
			ss[index].setStudentId(reader.readLine()); //updated setId() to setStudentId()
			ss[index].setLastName(reader.readLine());
			ss[index].setFirstName(reader.readLine());
			ss[index].setCourse(reader.readLine());
			
			
			try {
				ss[index].setYearLevel(Integer.parseInt(reader.readLine()));
				ss[index].setYearLevel(Integer.parseInt(reader.readLine()));
				ss[index].setUnitsEnrolled(Integer.parseInt(reader.readLine()));
				System.out.println("\n");
				
			}
			catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
			
			System.out.println("\nAnother entry? Y/N ");
			choice = reader.readLine();
			++index;
			
		} while (choice != "N");
		
		//this can be from 1 to many. All these will be saved to the db table
		
	}
	
	public void commandL() {
		
	}
	
	public void commandS(String studentLastNameOrID) {
		// searches for a student entry in the database and displays information
		// search options are: studentID or studentLastName
		
		
		System.out.println("\nReading command . . . . "); //maybe this is before function calls
		System.out.println("\nPlease wait. . . searching for student record " + studentLastNameOrID);
		
		//if record found, show records for that student. Else, records not found or error/exception input
	}
	
	public void commandD(String studentLastNameOrID) {
		// search and delete
		// otherwise search then show error message
		
		
	}
	
	public void commandR() {
		// generates report option based on the given criteria
		
	}

	public void commandP() {
		// Erases all records in the database
		
	}
	
	public static void quitCommandExecuted() {
		// terminates the application
		System.out.println("\nProgram terminated. Thank you for using the system.");
		hr(2);
	}
	
	public void printUserEntry(String input) {
		System.out.println("COMMAND: " + input);
		// System.out.println("# Reading command " + input + "...");
		System.out.println();
	}
	
	
	
	
	// DEBUGGING METHODS ===========================================================================================================================================
	public void printFileContents(Scanner s, String fileName) {
		hr(1);
		System.out.println("# FILE: " + fileName + "\n");
		while(s.hasNextLine()) {
			System.out.println("|"+ s.nextLine().toString());
		}
		System.out.println("\n# End of file, " + fileName + ".");
		hr(1);
	}
	
	
	
	public void printStudent(Student s) {
		System.out.println("last name: " + s.getLastName());
		System.out.println("first name: " + s.getFirstName());
		System.out.println("student id: " + s.getStudentId());
		System.out.println("course: " + s.getCourse());
		System.out.println("year level: " + s.getYearLevel());
		System.out.println("units enrolled: " + s.getUnitsEnrolled());
	}
	
	public void printDefaultAppConfig(String tableName, String inputFile, String logFile) {
		System.out.println("# DEFAULT APP CONFIGURATION");
		System.out.println("(Default) STUDENT DATABASE TABLE NAME: " + tableName);
		System.out.println("(Default) INPUT FILE PATH: \"" + inputFile + "\"");
		System.out.println("(Default) LOG FILE PATH: \"" + logFile + "\"");
	}
	
	// PRINTS HORIZONTAL RULE
	public static void hr(int style) {
		switch(style) {
		case 1:
			System.out.println("-------------------------------------------------------------------"
					+ "--------------------------------------------------------------------------------------------------------------------------");
			break;
		case 2:
			System.out.println("==================================================================="
					+ "==========================================================================================================================");
			break;
		case 3:
			System.out.println("___________________________________________________________________"
					+ "__________________________________________________________________________________________________________________________");
			break;
		default:
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
					+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			break;
		}
	}
}