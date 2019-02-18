package exceptions;

public class IDSizeNotValidException extends Exception implements Message{
	
	public IDSizeNotValidException() {
		super(INVALID_ID_SIZE);
	}

}
