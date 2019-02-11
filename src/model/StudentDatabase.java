package model;

import java.sql.*;

public class StudentDatabase extends Database {
	
	private static 	String studentDatabaseTableName 	= null;	// DEFAULT STUDENT DATABASE TABLE NAME
	private 		Connection conn 					= null; // DATABASE CONNECTION OBJECT
	
	
	// DATABASE CONFIGURATION
	private static String JDBC_DRIVER 	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "saludes-se21-db";
	private static String DB_URL 		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	
	// CONSTRUCTOR
	public StudentDatabase(String tableName) {
		System.out.println("DATABASE: "	+ DB_NAME);
		System.out.println("DB_URL: "	+ DB_URL);
		
		studentDatabaseTableName = tableName;
		conn = getConnection();
	}
	
	
	// GET DATABASE CONNECTION
	@Override
	public Connection getConnection() {
		
		 // CONNECTION OBJECT
		Connection databaseConnection = null;
		
		try {
			// REGISTER JDBC DRIVER
			Class.forName(JDBC_DRIVER);
				
			// OPEN A CONNECTION
			System.out.println("\nCONNECTING TO DATABASE...");
			databaseConnection = DriverManager.getConnection(DB_URL, "root", "");
			
			System.out.println("STATUS: CONNECTED");
			System.out.println("------------------------------------------------------------------------------------");
				
		} catch (ClassNotFoundException cnfe) {
			System.out.print("\nERROR CLASS: ");
			System.out.println(cnfe.getMessage() + "\n");
			System.err.println(cnfe.getCause());
			// cnfe.printStackTrace();
			
		} catch (SQLException sqle) {
			System.out.print("\nERROR SQL: ");
			System.err.println(sqle.getMessage() + "\n");
			System.err.println(sqle.getCause());
			// sqle.printStackTrace();
			
		} catch (Exception e) {
			System.out.print("\nERROR EXCEPTION: ");
			System.err.println(e.getMessage() + "\n");
			System.err.println(e.getCause());
			// e.printStackTrace();
		}
		
		return databaseConnection;
		
	}


	// FUNDAMENTAL DATABASE OPERATIONS =========================================================================
	@Override
	public boolean insertRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean deleteRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public ResultSet readRecord() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// ==========================================================================================================

	
	// CREATE STUDENT TABLE
	public boolean createStudentTable() {
		Statement 	stmt = null;
		String 		SQL  = null;
		
		try {
			// SAMPLE OUTPUT: "Creating <DB_NAME> table"
			System.out.println("Creating table, " + studentDatabaseTableName + " ...");
			
			stmt = conn.createStatement();
			
			// SQL TO CREATE STUDENT TABLE
			SQL  =	"CREATE TABLE " + studentDatabaseTableName + " ("  +
					"id INTEGER not NULL, " +
					"studId VARCHAR(9), " +
					"firstName VARCHAR(255), " +
					"lastName VARCHAR(255), " +
					"course VARCHAR(5), " +
					"yearLevel INTEGER, " +
					"unitsEnrolled INTEGER, " +
					"PRIMARY KEY ( id )" +
					")";
			
			// System.out.println(SQL);
			
			stmt.executeUpdate(SQL);
			
			System.out.println("Successfully created " + studentDatabaseTableName + " table");
			
			return true;
			
		} catch (SQLException sqle) {
			System.out.print("\nERROR: ");
			System.err.println(sqle.getMessage() + "\n");
			System.err.println(sqle.getCause());
			sqle.printStackTrace();
			
		} catch (Exception e) {
			System.out.print("\nERROR: ");
			System.err.println(e.getMessage() + "\n");
			System.err.println(e.getCause());
			e.printStackTrace();
			
		} finally {
			// TODO: Close statement resources
		}
		
		return false;
	}
	
	
	// DELETE EXISTING TABLES WITH MATCHING NAMES (deletes table if it matches the provided name by the user, studentDatabaseTableName)
	public boolean deleteDuplicateTables() {		
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getTables(null, null, studentDatabaseTableName, null);
			
			// SCANS THE RETURNED RESULTS FROM DATABASE
			while(res.next()) {
				if(studentDatabaseTableName != null && studentDatabaseTableName.equals(res.getString("TABLE_NAME"))) { // returns TRUE if the name of an existing table matches the parameter given 
					return true;
				}
			}
			
		} catch (SQLException sqle) {
			// TODO: handle SQL exception
			sqle.printStackTrace();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}

	
	// CLOSE ALL OPENED RESOURCES
	@Override
	public boolean closeResources() {
		// TODO: Close all open resources
		return false;
	}

}
