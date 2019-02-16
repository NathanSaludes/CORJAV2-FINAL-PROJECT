package exceptions;

public class StudentAlreadyExistingException extends Exception implements Message {
	
	public StudentAlreadyExistingException() {
		super(EXISTING_STUDENT);
	}
}
