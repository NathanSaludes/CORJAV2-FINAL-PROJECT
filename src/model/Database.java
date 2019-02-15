package model;

import java.sql.*;

public abstract class Database {
	
	// FOR INSERTING RECORDS TO THE DATABASE
	public abstract boolean insertRecord(Student student);
	
	// FOR DELETING A SINGLE RECORD TO THE DATABASE
	public abstract boolean deleteRecord();
	
	// FOR READING RECORDS FROM THE DATABASE
	public abstract ResultSet readRecord();
	
	// FOR CLOSING ALL OPENED RESOURCES
	public abstract boolean terminateConnection();
}
