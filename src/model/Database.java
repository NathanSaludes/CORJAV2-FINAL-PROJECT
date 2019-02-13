package model;

import java.sql.*;

public abstract class Database {
	
	// DB CONFIGURATION
	private static String JDBC_DRIVER 	= null;
	private static String DB_NAME 		= null;
	private static String DB_URL 		= null;
	
	
	// FOR ESTABLISHING A CONNECTION TO THE DATABASE
	public abstract Connection getConnection();
	
	// FOR INSERTING RECORDS TO THE DATABASE
	public abstract boolean insertRecord(Student student);
	
	// FOR DELETING A SINGLE RECORD TO THE DATABASE
	public abstract boolean deleteRecord();
	
	// FOR READING RECORDS FROM THE DATABASE
	public abstract ResultSet readRecord();
	
	// FOR CLOSING ALL OPENED RESOURCES
	public abstract boolean closeResources();
}
