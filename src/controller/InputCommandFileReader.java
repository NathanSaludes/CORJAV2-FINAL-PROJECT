package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.Student;
import model.StudentDatabaseManager;
import view.CommandFileReaderPrinter;
import view.View;
import exceptions.*;

public class InputCommandFileReader extends View {
	
	private static StudentDatabaseManager DatabaseManager	= null;
	private static String fileName 							= null;
	private static String filePath 							= null;
	private static File inputCommandFile					= null;
	private static Student student							= null;
	private static String DB_TABLENAME						= null;
	
	private CommandFileReaderPrinter printer				= new CommandFileReaderPrinter(); 

	// CONSTRUCTOR
	public InputCommandFileReader(String path, StudentDatabaseManager DatabaseManager, String tableName) {
		
		// Initialize class variables
		InputCommandFileReader.DatabaseManager	= DatabaseManager;
		filePath 			= path;
		fileName 			= path.substring(path.lastIndexOf('\\') + 1);
		inputCommandFile	= new File(filePath);
		DB_TABLENAME			= tableName;
		
		// Print the path and file name
		printer.fileMetaData(filePath, fileName);
		hr(1);
		
		// start reading the file
		readFile();
	}
	
	public void readFile() {
		Scanner scanner = null;
		
		try {
			// set delimiter to 'next line' character
			scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			printer.FileReadingStatus(1);
			hr(2);
			
			
			// read user commands
			commandReader(scanner);
			
			// close resources
			scanner.close();
			
			printer.FileReadingStatus(0);
			
		} catch (FileNotFoundException e) {
			printer.fileNotFound();
			hr(1);
		}
	}
	
	public void commandReader(Scanner scanner) {
		String input = null;
		boolean Quit = false;
		
		while(scanner.hasNext() && !Quit) {
			switch(input = scanner.next().toString().toUpperCase().trim()) {
			case "A": //student entry
				printer.printUserEntry(input);
				commandA(scanner);
				break;
			case "L": //display all
				printer.printUserEntry(input);
				commandL();
				break;
			case "S": //search and display
				printer.printUserEntry(input);
				commandS(scanner);
				break;
			case "D": //search and delete
				printer.printUserEntry(input);
				commandD(scanner);
				break;
			case "R": //generate report
				printer.printUserEntry(input);
				commandR(scanner);
				break;
			case "P": //erase all records
				printer.printUserEntry(input);
				commandP();
				break;
			case "Q":
				printer.printUserEntry(input);
				commandQ(scanner);
				Quit = true;
				break;
			default:
				if(input.isEmpty()) {
					// skip
					break;
				} else {
					printer.invalidUserCommand(input);					
					hr(1);
					break;
				}
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
				String input = scanner.next().toString().trim();
				
				if(!input.isEmpty()) {
					
					switch (parameterIndex) {
					case 1: // id
						studentId = input;
						
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
						lastName = input;
						break;
					case 3: // first name
						firstName = input;
						break;
					case 4: // course
						course = input;
						
						if(!course.equalsIgnoreCase("SE")) {
							if(!course.equalsIgnoreCase("GD")) {
								if(!course.equalsIgnoreCase("WD")) {
									throw new CourseFormatInvalidException();
								}
							}
						}
						
						break;
					case 5: // year level
						yearLevel = Integer.parseInt(input);
						break;
					case 6: // units enrolled
						unitsEnrolled = Integer.parseInt(input);
						break;
						
					default:
						break;
					}
					
				} else {
					printer.invalidUserCommand(input);
					break;
				}				
			}
			
			
			
			if(!(studentId == null)) {
				if(!(lastName == null)) {
					if(!(firstName == null)) {
						if(!(course == null)) {
							if(!(yearLevel <= 0)) {
								if(!(unitsEnrolled <= 0)) {
									
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
									printer.printStudent(student);
									logger.printAStudentRecord(student);
									
									printer.newStudentCreated();
									
									
									// insert student to database
									if(DatabaseManager.insertStudentRecord(student)) {
										printer.databaseInsertStudent(true);	
									} else {
										printer.databaseInsertStudent(false);
									}
									
								} else {
									printer.addStudentError();
								}
							} else {
								printer.addStudentError();
							}
						} else {
							printer.addStudentError();
						}
					} else {
						printer.addStudentError();
					}
				} else {
					printer.addStudentError();
				}
			} else {
				printer.addStudentError();
			}
			
			
			
			
		} catch (IDFormatInvalidException e) {
			printer.invalidIdFormat(e.getMessage());
			
		} catch (IDSizeNotValidException e) {
			printer.invalidIdLength(e.getMessage());
			
		} catch (CourseFormatInvalidException e) {
			printer.invalidCourseFormat(e.getMessage());
			
		} finally {
			View.hr(1);
		}
	}
	
	private void commandQ(Scanner s) {
		if(DatabaseManager.terminateConnection()) {
			printer.quitCommandMessage();
			hr(1);
		}
	}

	private void commandP() {
		// purge table records
		printer.startPurgeCommand();
		
		if(DatabaseManager.clearStudentTable()) {
			printer.tableCleared(true);
		} else {
			printer.tableCleared(false);
		}
		
		hr(1);
	}

	private void commandR(Scanner scanner) {
		String studentCourse = null;
		
		if(scanner.hasNext()) {
			String input = scanner.next().toString().toUpperCase().trim(); // convert to String -> convert all to upper case -> trim trailing white space/s.
			if(!input.isEmpty()) {
				studentCourse = input;
				if(studentCourse.equalsIgnoreCase("ALL")) {
					// generate report for all
					Object[] coursesArr = DatabaseManager.getAllStudents();
					if(coursesArr != null) {
						printer.generateStudentReport(coursesArr);					
					} else {
						printer.printGenerateReportError();
					}
					
				} else if(studentCourse.equalsIgnoreCase("SE") || studentCourse.equalsIgnoreCase("GD") || studentCourse.equalsIgnoreCase("WD")) {
					Student[] studArr = DatabaseManager.getAllStudentsByCourse(studentCourse);
					if(studArr != null) {
						printer.generateStudentReport(studArr, studentCourse);					
					} else {
						printer.printGenerateReportError();
					}
					
				} else {
					printer.invalidParameterArgument();			
				}
				
			} else {
				printer.invalidUserCommand(input);
			}
			
		}
		
		hr(1);
	}

	private void commandD(Scanner scanner) {
		try {
			String input = scanner.next().toString().trim();
			
			if(!input.isEmpty()) {
				String studId = input;
				
				printer.searchStudentRecordsById(studId);
				
				// if true, it will proceed on deleting the studentRecord
				if(DatabaseManager.searchStudentRecordById(studId)) {
					
					printer.printRecordFound(); // RECORD FOUND!
					
					if(DatabaseManager.deleteStudentRecord(studId)) {
						printer.deleteRecordsMessage();
					} else {
						printer.deleteError();
					}
					
				} else {
					printer.searchByIdError();
				}
				
			} else {
				printer.invalidUserCommand(input);
			}
			
			
		} catch (StudentNotFoundException snfe) {
			printer.studentNotFound();
		} catch (Exception e) {
			printer.deleteError();
		} finally {
			hr(1);
		}
		
	}

	private void commandS(Scanner scanner) {
		// search a student entry via studentID or lastName and display full info
		try {
			String studIdOrLastName = scanner.next().toString().trim();
			
			if(!studIdOrLastName.isEmpty()) {
				printer.searchStudentRecords(studIdOrLastName);
				
				Student stud = DatabaseManager.searchStudentRecord(studIdOrLastName);
				
				if(stud != null) {
					printer.printRecordFound();
					new View().printAStudentRecord(stud);
				} else {
					printer.searchError();
				}							
			} else {
				printer.invalidUserCommand(studIdOrLastName);
			}
			
		} catch (StudentNotFoundException snfe) {
			printer.studentNotFound();
		} catch(SQLException sqle) {
			 // sqle.printStackTrace();
			printer.searchError();
		} catch(Exception e) {
			printer.searchError();
		} finally {
			hr(1);
		}
	}

	private void commandL() {
		Object[] studentsOfAllCourses = DatabaseManager.getAllStudents();
		if(studentsOfAllCourses != null) {
			printer.listAllStudents(studentsOfAllCourses);
		} else {
			printer.listAllStudentsError();
		}
		
		hr(1);
	}

	
	
	
	
	
	
	

	// DEBUGGER ====================================================================================================================================================
	public void printInputCommandFileContents() {
		try {
			// TODO: Resource Leak: closeable value for Scanner
			Scanner scanner = new Scanner(this.inputCommandFile).useDelimiter("\n");
			
			System.out.println("# Opening File...Please wait.");
			System.out.println("# Printing file contents...");
			
			// print file contents using the view class
			printFileContents(scanner, this.fileName);
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found...");
		}
	}
}
