package model;

import java.sql.*;

public abstract class Database {
	
	// FOR INSERTING RECORDS TO THE DATABASE
	public abstract boolean insertRecord(Student student);
	
	// FOR DELETING A SINGLE RECORD TO THE DATABASE
	public abstract boolean deleteRecord(String studId); // change this to deleteRecord(String id, String lastName) ???
	
	// FOR READING RECORDS FROM THE DATABASE
	public abstract ResultSet readRecord(String studIdOrLastName);
	
	// FOR CLOSING ALL OPENED RESOURCES
	public abstract boolean terminateConnection();
}
