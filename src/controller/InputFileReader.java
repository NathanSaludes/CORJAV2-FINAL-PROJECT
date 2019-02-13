package controller;

import java.io.FileReader;

public class InputFileReader {
	
	private String fileName = null;
	private String path 	= null;

	// CONSTRUCTOR
	public InputFileReader(String path) {
		this.path 		= path;
		this.fileName 	= path.substring(path.lastIndexOf('\\') + 1);
		
		System.out.println("PATH: \"" + this.path + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"\n");
	}
	
	
	// READ INPUT FILE DATA
	/**
	   	public void readFile() throws FileNotFoundException, InterruptedException {
		File file = new File(this.path);		
		Scanner input = new Scanner(file);
		String line;
		
		System.out.println("Reading input file... \n");
		Thread.sleep(2000);
		
		while(input.hasNextLine()) {
			// TODO: readFile() functionality
			break;
			input.nextLine();
		}
		
		// PRINTS CONTENT OF INPUT FILE (For debugging purposes)
		new View().printInputFileData(input, this.fileName);
	}*/
	
	public void readFile() {
	}
	
	
}
