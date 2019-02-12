package model;

import java.sql.*;

public class StudentDatabase extends Database {
	
	private static 	String studentDatabaseTableName 	= null;	// DEFAULT STUDENT DATABASE TABLE NAME
	private static	Connection conn 					= null; // DATABASE CONNECTION OBJECT
	
	
	// DATABASE CONFIGURATION
	private static String JDBC_DRIVER 	= "com.mysql.jdbc.Driver";
	private static String DB_NAME		= "saludes-se21-db";
	private static String DB_URL 		= "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	
	// CONSTRUCTOR
	public StudentDatabase(String tableName) {
		hr(1);
		System.out.println("# DATABASE CONFIGURATION");
		System.out.println("DB_NAME: "	+ DB_NAME);
		System.out.println("DB_URL: "	+ DB_URL);
		
		studentDatabaseTableName = tableName;
		
		// Establish a connection upon instantiation
		conn = getConnection();
		
		// Create a new table
		createStudentTable();
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
			hr(1);
			System.out.println("# Connecting to database...");
			databaseConnection = DriverManager.getConnection(DB_URL, "root", "");
			
			System.out.println("DATABASE CONNECTION STATUS: Valid");
			hr(1);
				
		} catch (ClassNotFoundException cnfe) {
			System.out.print("\nDRIVER ERROR: ");
			System.out.println(cnfe.getMessage() + "\n");
			System.out.println("CAUSE: " + cnfe.getCause());
			hr(1);
			System.out.println("\nDATABASE CONNECTION STATUS: Invalid");
			// cnfe.printStackTrace();
			hr(1);
			
		} catch (SQLException sqle) {
			System.out.print("\nSQL ERROR: ");
			System.out.println(sqle.getMessage() + "\n");
			System.out.println("CAUSE: " + sqle.getCause());
			hr(1);
			System.out.println("DATABASE CONNECTION STATUS: Invalid");
			// sqle.printStackTrace();
			hr(1);
			
		} catch (Exception e) {
			System.out.print("\nERROR: ");
			System.out.println(e.getMessage() + "\n");
			System.out.println("CAUSE: " + e.getCause());
			hr(1);
			System.out.println("\nDATABASE CONNECTION STATUS: Invalid");
			// e.printStackTrace();
			hr(1);
		}
		
		return databaseConnection;
		
	}


	// FUNDAMENTAL DATABASE OPERATIONS ==========================================================================================================================
	@Override
	public boolean insertRecord() {
		// TODO: insertRecord()
		return false;
	}

	
	@Override
	public boolean deleteRecord() {
		// TODO: deleteRecord()
		return false;
	}

	
	@Override
	public ResultSet readRecord() {
		// TODO: readRecord()
		return null;
	}
	
	
	// ==========================================================================================================================================================
	// CREATE STUDENT TABLE
	public void createStudentTable() {
		Statement 	stmt = null;
		String 		SQL  = null;
		
		try {			
			// DELETE EXISTING TABLES FIRST
			deleteDuplicateTable();
			
			// SAMPLE OUTPUT: "Creating <DB_NAME> table"
			System.out.println("# Creating new table, \'" + studentDatabaseTableName.toLowerCase() + "\' ...");
			
			
			stmt = conn.createStatement();
			
			// SQL TO CREATE STUDENT TABLE
			SQL  =	"CREATE TABLE " + studentDatabaseTableName + " ("  +
					" id INTEGER not NULL, " 	+
					" studId VARCHAR(9), " 		+
					" firstName VARCHAR(255), " +
					" lastName VARCHAR(255), " 	+
					" course VARCHAR(5), " 		+
					" yearLevel INTEGER, " 		+
					" unitsEnrolled INTEGER, " 	+
					" PRIMARY KEY ( id )" 		+
					")";
			
			// System.out.println(SQL);
			
			stmt.executeUpdate(SQL);
			
			System.out.println("# Successfully created \'" + studentDatabaseTableName.toLowerCase() + "\' table!");
			
		} catch (SQLException sqle) {
			System.out.print("\nSQL ERROR: ");
			System.out.println(sqle.getMessage() + "\n");
			System.out.println("CAUSE: " + sqle.getCause());
			sqle.printStackTrace();
			hr(1);
		} catch (Exception e) {
			System.out.print("\nERROR: ");
			System.out.println(e.getMessage() + "\n");
			System.out.println("CAUSE: " + e.getCause());
			e.printStackTrace();
			hr(1);
		} finally {
			hr(1);
		}
	}
	
	
	// DELETE EXISTING TABLES WITH MATCHING NAMES (deletes table if it matches the provided name by the user, studentDatabaseTableName)
	public void deleteDuplicateTable() {
		Statement stmt 	= null;
		String SQL 		= "DROP TABLE ";
		
		try {
			DatabaseMetaData dmbd = conn.getMetaData();
			ResultSet res = dmbd.getTables(null, null, "%", null); // RETURNS ALL TABLES
			stmt = conn.createStatement();
			
			// SCANS THE RETURNED RESULTS FROM DATABASE
			while(res.next()) {
				if(studentDatabaseTableName.equalsIgnoreCase(res.getString("TABLE_NAME"))) {
					System.out.println("# Found an existing \'" + studentDatabaseTableName.toLowerCase() + "\' table!");
					SQL = SQL.concat(res.getString("TABLE_NAME"));
					
					// EXECUTE DROP TABLE QUERY
					stmt.executeUpdate(SQL);
					
					System.out.println("# Dropped \'" + res.getString("TABLE_NAME").toLowerCase() + "\' table...");
					System.out.println("#");
				}
			}
			
		} catch (SQLException sqle) {
			// TODO: deleteDuplicateTable() - handle SQL exception
			System.out.print("\nSQL ERROR: ");
			System.out.println(sqle.getMessage() + "\n");
			System.out.println("CAUSE: " + sqle.getCause());
			sqle.printStackTrace();
			hr(1);
		} catch (Exception e) {
			System.out.print("\nERROR: ");
			System.out.println(e.getMessage() + "\n");
			System.out.println("CAUSE: " + e.getCause());
			e.printStackTrace();
			hr(1);
		}
	}

	
	// CLOSE ALL OPENED RESOURCES
	@Override
	public boolean closeResources() {
		try {
			if(!conn.isClosed() && conn.isValid(0)) {
				conn.close();				
				System.out.println("# Closing db connection...");
				return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	// PRINTS HORIZONTAL RULE
	public static void hr(int style) {
		switch(style) {
		case 1:
			System.out.println("-------------------------------------------------------------------"
					+ "--------------------------------------------------------------------------------------------------------------------------");
			break;
		case 2:
			System.out.println("==================================================================="
					+ "======================================================================================================");
			break;
		default:
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
					+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			break;
		}
	}
}
