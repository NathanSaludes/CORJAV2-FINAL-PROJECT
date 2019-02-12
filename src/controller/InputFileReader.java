package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import view.View;

public class InputFileReader {
	
	private String path 	= null;
	private String fileName = null;

	// CONSTRUCTOR
	public InputFileReader(String path) {
		this.path 		= path;
		this.fileName 	= path.substring(path.lastIndexOf('\\') + 1);
		
		System.out.println("PATH: \"" + this.path + "\"");
		System.out.println("FILE NAME: \"" + this.fileName + "\"\n");
	}
	
	
	// READ INPUT FILE DATA
	public void readFile() throws FileNotFoundException, InterruptedException {
		File file = new File(this.path);		
		Scanner input = new Scanner(file);
		String line;
		
		System.out.println("Reading input file... \n");
		Thread.sleep(2000);
		
		while(input.hasNextLine()) {
			// TODO: readFile() functionality
			input.nextLine();
		}
		
		// PRINTS CONTENT OF INPUT FILE (For debugging purposes)
		new View().printInputFileData(input, this.fileName);
	}
	
	
}
