package model;

import java.sql.*;

public class Student {
	
	private String id;
	private String lastName;
	private String firstName;
	private String course;
	private int yearLevel;
	private int unitsEnrolled;
	
	private Connection conn = getDBConnection();
	
	
	public Student() {
		this.id = null;
		this.lastName = null;
		this.firstName = null;
		this.course = null;
		this.yearLevel = 0;
		this.unitsEnrolled = 0;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getYearLevel() {
		return yearLevel;
	}
	public void setYearLevel(int yearLevel) {
		this.yearLevel = yearLevel;
	}
	public int getUnitsEnrolled() {
		return unitsEnrolled;
	}
	public void setUnitsEnrolled(int unitsEnrolled) {
		this.unitsEnrolled = unitsEnrolled;
	}
	
	
	// DATABASE CONFIG
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/";
	
	// ESTABLISH DATABASE CONNECTION
	private Connection getDBConnection() {
		
		Connection  conn = null;
		
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, "root", "");
			System.out.println("Connected database successfully...");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace(); // HANDLE JDBC ERRORS
		} catch (Exception e) {
			e.printStackTrace();	// HANDLE "Class.forName" ERRORS
		}
		
		System.out.print("CONNECTION STATUS: ");
			if(conn != null)
				System.out.print("VALID");
			else
				System.err.print("INVALID");
			
		return conn;
	}
	
	// CREATE TABLE
	public boolean createStudentTable() {
		Statement 	stmt = null;
		String 		SQL  = null;
		
		try {
			// Execute query
			System.out.println("Creating 'REGISTRATION' table...");
			stmt = this.conn.createStatement();
			
			SQL  =	"CREATE TABLE REGISTRATION ("		  +
					" id 			INTEGER not NULL,	" +
					" studId 		VARCHAR(9),			" +
					" firstName 	VARCHAR(255),		" +
					" lastName 		VARCHAR(255),		" +
					" course 		VARCHAR(5),			" +
					" yearLevel		INTEGER,			" +
					" unitsEnrolled INTEGER,			" +
					" PRIMARY KEY ( id )"				  +
					")";
			
			stmt.executeUpdate(SQL);
			return true;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return false;
	}
}
