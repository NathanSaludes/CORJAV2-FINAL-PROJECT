package exceptions;

public interface Message {
	String EXISTING_STUDENT = "STUDENT ALREADY EXISTS IN THE DATABASE!";
		
	String STUDENT_NOTFOUND = "STUDENT NOT FOUND!";
	
    String INVALID_ID_SIZE = "Student ID length too long!";
    
    String INVALID_ID_FORMAT = "Invalid Student ID Format! ";
    
    String INVALID_COURSE_FORMAT = "Invalid Course Name/Format.";
	
}
