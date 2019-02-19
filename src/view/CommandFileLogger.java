package view;

import model.Student;

public class CommandFileLogger extends LogFileWriter {

private static CommandFileLogger logger = new CommandFileLogger();
	
	public void fileMetaData(String path, String name) {
		println("# FILE CONFIGURATION");
		println("PATH: " + path);
		println("FILE NAME: " + name);
	}
	
	public void commandPrinter(String input) {
		println("# reading " + input + " command..." + endl);
	}
	
	public void FileReadingStatus(int process) {
		switch(process) {
			case 1:
				println("# Opening input file...");
				break;
			case 0:
				println("# Closing input file...");
		}
	}
	
	public void fileNotFound() {
		println("# File not found!");
	}
	
	public void printUserEntry(String input) {
		println("COMMAND: " + input + endl);
		println("# reading " + input + " command..." + endl);
	}
	
	public void invalidUserCommand(String input) {
		println("# \'" + input + "\'");
		println("# Invalid Command!");
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void invalidIdFormat(String error) {
		println("ERROR: " + error);
		println(endl + "# Unable to create/add new record.");
		println("# student ID must not have non-digit characters");
	}
	
	public void invalidIdLength(String error) {
		println("ERROR " + error);
		println(endl + "# Unable to create/add new record.");
		println("# Student ID should be composed of 11 digits");
	}
	
	public void invalidCourseFormat(String error) {
		println("ERROR: " + error);
		printAllPossibleCourses();
		println("# Unable to create/add new record.");
	}
	
	public void studentNotFound() {
		println("# Student not found.");
	}
	
	public void printRecordFound() {
		println("RECORD FOUND!" + endl);
	}
	
	
	
	
	
	
	
	// COMMAND A ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void newStudentCreated() {
		println(endl + "# Created a new student!");
	}
	
	public void databaseInsertStudent(boolean success) {
		println("# Inserting student record into the database...");
		
		if(success) {
			println("# Successfully inserted a new student record into the database!");
		} else {
			println("# Failed to insert student record into the database.");			
		}
	}
	
	public void printStudent(Student s) {
		println("last name: " + s.getLastName());
		println("first name: " + s.getFirstName());
		println("student id: " + s.getStudentId());
		println("course: " + s.getCourse());
		println("year level: " + s.getYearLevel());
		println("units enrolled: " + s.getUnitsEnrolled() + endl);
	}
	
	public void addStudentError() {
		println("# Unable to add new student.");
	}
	
	// COMMAND R ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void invalidParameterArgument() {
		println("# Invalid R Command.");
	}
	
	public void generateStudentReport(Student[] stud, String course) {
		println("# Creating a report for all " + course.toUpperCase() + " students.");
		println(endl + "Total number of " + course.toUpperCase() + " students: " + stud.length + endl);
		for(int counter=0; counter < stud.length; ++counter) {
			println("[" + (counter+1) + "]");
			printStudent(stud[counter]);
		}
	}
	
	public void generateStudentReport(Object[] courses) {	
		Student[] SE = new Student[0];
		Student[] GD = new Student[0];
		Student[] WD = new Student[0];
		int totalNumberOfStudents = SE.length + GD.length + WD.length;
		
		for(int counter=0; counter < courses.length; ++counter) {
			switch(counter) {
				case 0:
					if(courses[counter] != null)
						SE = (Student[]) courses[counter];
				break;
				case 1:
					if(courses[counter] != null)
						GD = (Student[]) courses[counter];
				break;
				case 2:
					if(courses[counter] != null)
						WD = (Student[]) courses[counter];
				break;
			}
		}
		
		println("# Creating a report for all students..." + endl);
		
		println("Total Number of Students: " + totalNumberOfStudents + endl);
		
		println("Total number of SE students: " + SE.length);
		println("Total number of GD students: " + GD.length);
		println("Total number of WD students: " + WD.length);
		
		println(endl + " --------- Software Engineering ---------- ");
		for(int counter=0; counter < SE.length; ++counter) {
			println("[" + (counter+1) + "]");
			printStudent(SE[counter]);
		}
		println(" --------- Game Development ---------- ");
		for(int counter=0; counter < GD.length; ++counter) {
			println("[" + (counter+1) + "]");
			printStudent(GD[counter]);
		}
		println(" --------- Web Development ---------- ");
		for(int counter=0; counter < WD.length; ++counter) {
			println("[" + (counter+1) + "]");
			printStudent(WD[counter]);
		}
	}

	public void printGenerateReportError() {
		println("# Unable to generate report");
	}
	
	// COMMAND D ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void searchStudentRecordsById(String id) {
		println("# Searching for student with an id \'" + id + "\'");
		println("# Please wait..." + endl);
	}
	
	public void deleteRecordsMessage() {
		println(endl + "# Student record deleted!");
	}

	public void deleteError() {
		println("# Unable to delete student record..." + endl);		
	}
	
	public void searchByIdError() {
		println("# Unable to perform search by id.");
	}
	
	// COMMAND Q ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void quitCommandMessage() {
		println(endl + " Quitting...");
	}
	
	// COMMAND P ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void startPurgeCommand() {
		println("# Deleting all records...Please wait.");
	}
	
	public void tableCleared(boolean success) {
		if(success) {
			println("# Successfully clear ALL records in the student database table.");
		} else {
			println("# Unable to clear student database table records");
		}
	}
	
	// COMMAND S ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void searchStudentRecords(String idOrlastName) {
		println("# Searching for student record \'" + idOrlastName + "\'");
		println("# Please wait..." + endl);
	}
	
	public void searchError() {
		println("# Unable to perform search student record.");
	}

	// COMMAND L ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void listAllStudentsError() {
		println("# Unable to list all student records.");
		
	}
	
	public void listAllStudents(Object[] courses) {
		
		Student[] SE = new Student[0];
		Student[] GD = new Student[0];
		Student[] WD = new Student[0];
		
		for(int counter=0; counter < courses.length; ++counter) {
			switch(counter) {
				case 0:
					if(courses[counter] != null)
						SE = (Student[]) courses[counter];
				break;
				case 1:
					if(courses[counter] != null)
						GD = (Student[]) courses[counter];
				break;
				case 2:
					if(courses[counter] != null)
						WD = (Student[]) courses[counter];
				break;
			}
		}
		
		int totalNumberOfStudents = SE.length + GD.length + WD.length;
		int lastCounter = 1;
		
		println("Listing all students enrolled");
		println("----------------------------------------------+ " + endl);
		
		for(int counter=0; counter < SE.length; ++counter) {
			println("["+lastCounter+"]");
			printStudent(SE[counter]);
			++lastCounter;
		}
		
		for(int counter=0; counter < GD.length; ++counter) {
			println("["+lastCounter+"]");
			printStudent(GD[counter]);
			++lastCounter;
		}
		
		for(int counter=0; counter < WD.length; ++counter) {
			println("["+lastCounter+"]");
			printStudent(WD[counter]);
			++lastCounter;
		}
		
		println("----------------------------------------------+");
		println("Total number of students enrolled: " + totalNumberOfStudents);
		println("----------------------------------------------+");
		println("Total number of SE students: " + SE.length);
		println("Total number of GD students: " + GD.length);
		println("Total number of WD students: " + WD.length);
	}
}
