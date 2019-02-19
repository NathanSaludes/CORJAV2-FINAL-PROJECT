package view;

import model.Student;

public class CommandFileReaderPrinter {
	
	private static CommandFileLogger logger = new CommandFileLogger();
	
	public void fileMetaData(String path, String name) {
		System.out.println("# FILE CONFIGURATION");
		System.out.println("PATH: " + path);
		System.out.println("FILE NAME: " + name);
		
		logger.fileMetaData(path, name);
	}
	
	public void commandPrinter(String input) {
		System.out.println("# reading " + input + " command... \n");
		logger.printUserEntry(input);
	}
	
	public void FileReadingStatus(int process) {
		switch(process) {
			case 1:
				System.out.println("# Opening input file...");
				break;
			case 0:
				System.out.println("# Closing input file...");
		}
		
		logger.FileReadingStatus(process);
	}
	
	public void fileNotFound() {
		System.out.println("# File not found!");
		
		logger.fileNotFound();
	}
	
	public void printUserEntry(String input) {
		System.out.println("COMMAND: " + input + "\n");
		new CommandFileReaderPrinter().commandPrinter(input);
	}
	
	public void invalidUserCommand(String input) {
		System.out.println("# \'" + input + "\'");
		System.out.println("# Invalid Command!");
		
		logger.invalidUserCommand(input);
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void invalidIdFormat(String error) {
		System.out.println("ERROR: " + error);
		System.out.println("\n# Unable to create/add new record.");
		System.out.println("# student ID must not have non-digit characters");
		
		logger.invalidIdFormat(error);
	}
	
	public void invalidIdLength(String error) {
		System.out.println("ERROR " + error);
		System.out.println("\n# Unable to create/add new record.");
		System.out.println("# Student ID should be composed of 11 digits");
		
		logger.invalidIdLength(error);
	}
	
	public void invalidCourseFormat(String error) {
		System.out.println("ERROR: " + error);
		new View().printAllPossibleCourses();
		System.out.println("# Unable to create/add new record.");
		
		logger.invalidCourseFormat(error);
	}
	
	public void studentNotFound() {
		System.out.println("# Student not found.");
		
		logger.studentNotFound();
	}
	
	public void printRecordFound() {
		System.out.println("RECORD FOUND!\n");
		
		logger.printRecordFound();
	}
	
	
	
	
	
	
	
	// COMMAND A ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void newStudentCreated() {
		System.out.println("# Created a new student!");
		logger.newStudentCreated();
	}
	
	public void databaseInsertStudent(boolean success) {
		System.out.println("# Inserting student record into the database...");
		
		if(success) {
			System.out.println("# Successfully inserted a new student record into the database!");
		} else {
			System.out.println("# Failed to insert student record into the database.");			
		}
		
		logger.databaseInsertStudent(success);
	}
	
	public void printStudent(Student s) {
		System.out.println("last name: " + s.getLastName());
		System.out.println("first name: " + s.getFirstName());
		System.out.println("student id: " + s.getStudentId());
		System.out.println("course: " + s.getCourse());
		System.out.println("year level: " + s.getYearLevel());
		System.out.println("units enrolled: " + s.getUnitsEnrolled() + "\n");
	}
	
	public void addStudentError() {
		System.out.println("# Unable to add new student.");
		
		logger.addStudentError();
	}
	
	// COMMAND R ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void invalidParameterArgument() {
		System.out.println("# Invalid R Command.");
		
		logger.invalidParameterArgument();
	}
	
	public void generateStudentReport(Student[] stud, String course) {
		System.out.println("# Creating a report for all " + course.toUpperCase() + " students.");
		System.out.println("\n Total number of " + course.toUpperCase() + " students: " + stud.length + "\n");
		for(int counter=0; counter < stud.length; ++counter) {
			System.out.println("[" + (counter+1) + "]");
			printStudent(stud[counter]);
		}
		
		logger.generateStudentReport(stud, course);
	}
	
	public void generateStudentReport(Object[] courses) {		
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
		
		System.out.println("# Creating a report for all students... \n");
		
		System.out.println("Total Number of Students: " + totalNumberOfStudents + "\n");
		
		System.out.println("Total number of SE students: " + SE.length);
		System.out.println("Total number of GD students: " + GD.length);
		System.out.println("Total number of WD students: " + WD.length);
		
		System.out.println("\n --------- Software Engineering ---------- ");
		for(int counter=0; counter < SE.length; ++counter) {
			System.out.println("[" + (counter+1) + "]");
			printStudent(SE[counter]);
		}
		System.out.println(" --------- Game Development ---------- ");
		for(int counter=0; counter < GD.length; ++counter) {
			System.out.println("[" + (counter+1) + "]");
			printStudent(GD[counter]);
		}
		System.out.println(" --------- Web Development ---------- ");
		for(int counter=0; counter < WD.length; ++counter) {
			System.out.println("[" + (counter+1) + "]");
			printStudent(WD[counter]);
		}
		
		logger.generateStudentReport(courses);
	}

	public void printGenerateReportError() {
		System.out.println("# Unable to generate report");
		
		logger.printGenerateReportError();
	}
	
	// COMMAND D ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void searchStudentRecordsById(String id) {
		System.out.println("# Searching for student with an id \'" + id + "\'");
		System.out.println("# Please wait...\n");
		
		logger.searchStudentRecordsById(id);
	}
	
	public void deleteRecordsMessage() {
		System.out.println("\n# Student record deleted!");
		
		logger.deleteRecordsMessage();
	}

	public void deleteError() {
		System.out.println("# Unable to delete student record...\n");	
		
		logger.deleteError();
	}
	
	public void searchByIdError() {
		System.out.println("# Unable to perform search by id.");
		
		logger.searchByIdError();
	}
	
	// COMMAND Q ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void quitCommandMessage() {
		// terminates the application
		System.out.println("\n# Quitting...");
		
		logger.quitCommandMessage();
	}
	
	// COMMAND P ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void startPurgeCommand() {
		System.out.println("# Deleting all records...Please wait.");
		
		logger.startPurgeCommand();
	}
	
	public void tableCleared(boolean success) {
		if(success) {
			System.out.println("# Successfully clear ALL records in the student database table.");
		} else {
			System.out.println("# Unable to clear student database table records");
		}
		
		logger.tableCleared(success);
	}
	
	// COMMAND S ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void searchStudentRecords(String idOrlastName) {
		System.out.println("# Searching for student record \'" + idOrlastName + "\'");
		System.out.println("# Please wait...\n");
		
		logger.searchStudentRecords(idOrlastName);
	}
	
	public void searchError() {
		System.out.println("# Unable to perform search student record.");
		
		logger.searchError();
	}

	// COMMAND L ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void listAllStudentsError() {
		System.out.println("# Unable to list all student records.");
		
		logger.listAllStudentsError();
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
		
		System.out.println("Listing all students enrolled");
		System.out.println("----------------------------------------------+ \n");
		
		for(int counter=0; counter < SE.length; ++counter) {
			System.out.println("["+lastCounter+"]");
			printStudent(SE[counter]);
			++lastCounter;
		}
		
		for(int counter=0; counter < GD.length; ++counter) {
			System.out.println("["+lastCounter+"]");
			printStudent(GD[counter]);
			++lastCounter;
		}
		
		for(int counter=0; counter < WD.length; ++counter) {
			System.out.println("["+lastCounter+"]");
			printStudent(WD[counter]);
			++lastCounter;
		}
		
		System.out.println("----------------------------------------------+");
		System.out.println("Total number of students enrolled: " + totalNumberOfStudents);
		System.out.println("----------------------------------------------+");
		System.out.println("Total number of SE students: " + SE.length);
		System.out.println("Total number of GD students: " + GD.length);
		System.out.println("Total number of WD students: " + WD.length);
		
		logger.listAllStudents(courses);
	}
	
	
}
