package exceptions;

public class StudentNotFoundException extends Exception implements Message {
	
	public StudentNotFoundException() {
		super(EXISTING_STUDENT);
	}
}

