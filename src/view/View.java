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
		//displays all students enrolled
		
		int numberOfTotalStudents = 0;
		int numberOfSE = 0;
		int numberOfGD = 0;
		int numberOfWD = 0;
		
		System.out.println("Lists of Students Enrolled ");
		System.out.println("\n====================== \n\n");
		
		// loop through all entries of students in the database
		
		System.out.println("\nTotal Students Enrolled: " + numberOfTotalStudents);
		
		System.out.println("\nTotal Number of SE: " + numberOfSE);
		System.out.println("\nTotal Number of GD: " + numberOfGD);
		System.out.println("\nTotal Number of WD: " + numberOfWD);
		
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
	
	public void commandQ() {
		// terminates the application
		System.out.println("\nProgram terminated. Thank you for using the system.");
	}
	
	
	
	
	// DEBUGGING METHODS ===========================================================================================================================================
	public void printInputFileData(Scanner inputFile, String fileName) throws InterruptedException {
		System.out.println("Printing input file...\n\n");
		Thread.sleep(2000);
		System.out.println("=============================================== " + fileName + " ====================================================");
		while(inputFile.hasNextLine()) {
			System.out.println("# " + inputFile.nextLine().toString());
		}
		
		System.out.println("==================================================================================================================");
	}
}