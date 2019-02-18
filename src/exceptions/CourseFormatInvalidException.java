package exceptions;

public class CourseFormatInvalidException extends Exception implements Message {
	
	public CourseFormatInvalidException() {
		super(INVALID_COURSE_FORMAT);
	}
}
