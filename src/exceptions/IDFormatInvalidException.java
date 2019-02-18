package exceptions;

public class IDFormatInvalidException extends Exception implements Message{
	
	public IDFormatInvalidException() {
		super(INVALID_ID_FORMAT);
	}

}
