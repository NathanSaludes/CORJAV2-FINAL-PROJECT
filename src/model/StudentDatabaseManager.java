package model;

import java.sql.*;

public class StudentDatabaseManager extends Database {
	
	private static 	String databaseTableName 	= null;	// USER PROVIDED DATABASE TABLE NAME
	private static	Connection conn 					= null; // DATABASE CONNECTION OBJECT
	
	
	// DATABASE CONFIGURATION
	private String JDBC_DRIVER	= null;
	private String DB_NAME		= null;
	private String DB_URL		= null;
	
	
	// CONSTRUCTOR
	public StudentDatabaseManager(
			String JDBC_DRIVER, 
			String DB_NAME, 
			String DB_URL, 
			String tableName
	){
		hr(1);
		// 1) print database configurations
		System.out.println("# DATABASE CONFIGURATION");
		System.out.println("JDBC_DRIVER: " + JDBC_DRIVER);
		System.out.println("DB_NAME: "	+ DB_NAME);
		System.out.println("DB_URL: "	+ DB_URL);
		
		// 2) initialize members
		databaseTableName = tableName;
		this.JDBC_DRIVER	= JDBC_DRIVER;
		this.DB_NAME		= DB_NAME;
		this.DB_URL			= DB_URL;
		
		hr(1);
		
		// 3) Establish a connection upon instantiation
		try {
			conn = getConnection();
			
			if(conn.isValid(5)) {
				createStudentTable();
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			// e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		
	}
	
	
	// GET DATABASE CONNECTION
	private Connection getConnection() {

		Connection databaseConnection = null;
		
		try {
			// REGISTER JDBC DRIVER
			Class.forName(JDBC_DRIVER);
				
			// OPEN A CONNECTION
			System.out.println("# Connecting to database...");
			databaseConnection = DriverManager.getConnection(DB_URL, "root", "");
			
			System.out.println("DATABASE CONNECTION STATUS: Valid");
			hr(1);
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("# CONNECTION ERROR: DRIVER\n");
			System.out.println(cnfe.getMessage() + "\n");
			hr(1);
			System.out.println("\nDATABASE CONNECTION STATUS: Invalid");
			// cnfe.printStackTrace();
			hr(1);
			
		} catch (SQLException sqle) {
			System.out.println("# CONNECTION ERROR: SQL\n");
			System.out.println(sqle.getMessage());
			hr(1);
			System.out.println("# Unable to access database.");
			System.out.println("DATABASE CONNECTION STATUS: Invalid");
			// sqle.printStackTrace();
			hr(1);
			
		} catch (Exception e) {
			System.out.println("CONNECTION ERROR: EXCEPTION\n");
			System.out.println(e.getMessage() + "\n");
			hr(1);
			System.out.println("\nDATABASE CONNECTION STATUS: Invalid");
			// e.printStackTrace();
			hr(1);
		}
		
		return databaseConnection;
	}
	
	// CREATE STUDENT TABLE
	private void createStudentTable() {
		Statement 	stmt = null;
		String 		SQL  = null;
		
		try {			
			// DELETE EXISTING TABLES FIRST
			deleteDuplicateTable();
			
			// SAMPLE OUTPUT: "Creating <DB_NAME> table"
			System.out.println("# Creating new table, \'" + databaseTableName.toLowerCase() + "\' ...");
			
			
			stmt = conn.createStatement();
			
			// SQL TO CREATE STUDENT TABLE
			SQL  =	"CREATE TABLE " + databaseTableName + " ("  +
					" id INTEGER NOT NULL AUTO_INCREMENT, " 	+
					" studId VARCHAR(9), " 		+
					" firstName VARCHAR(255), " +
					" lastName VARCHAR(255), " 	+
					" course VARCHAR(5), " 		+
					" yearLevel INTEGER, " 		+
					" unitsEnrolled INTEGER, " 	+
					" PRIMARY KEY ( id )" 		+
					")";
			
			stmt.executeUpdate(SQL);
			
			System.out.println("# Successfully created \'" + databaseTableName.toLowerCase() + "\' table!");
			hr(1);
			
		} catch (SQLException sqle) {
			System.out.println("# Failed to create a student record...");
			System.out.print("\nCREATE TABLE ERROR: SQL\n");
			System.out.println(sqle.getMessage());
			hr(1);
			// sqle.printStackTrace();
		} catch (Exception e) {
			System.out.print("\nCREATE TABLE ERROR: GENERAL\n");
			System.out.println(e.getMessage());
			hr(1);
			// e.printStackTrace();
		}
	} // create student table
		
	// DELETE DUPLICATE TABLES
	private void deleteDuplicateTable() {
		Statement stmt 	= null;
		String SQL 		= "DROP TABLE ";
		
		try {
			DatabaseMetaData dmbd = conn.getMetaData();
			ResultSet res = dmbd.getTables(null, null, "%", null); // RETURNS ALL TABLES
			stmt = conn.createStatement();
			
			// SCANS THE RETURNED RESULTS FROM DATABASE
			while(res.next()) {
				if(databaseTableName.equalsIgnoreCase(res.getString("TABLE_NAME"))) {
					System.out.println("# Found an existing \'" + databaseTableName.toLowerCase() + "\' table!");
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
	} // delete duplicate table

	// FUNDAMENTAL DATABASE OPERATIONS ==========================================================================================================================
	@Override
	public boolean insertRecord(Student student) {
		PreparedStatement pStmt = null;
		
		String SQL =	  "INSERT INTO " + databaseTableName + " (" 
						+ "studId, firstName, lastName, course, yearLevel, unitsEnrolled) "
						+ "values (?,?,?,?,?,?)";
		
        try {
            if (conn.isValid(5) && !conn.isClosed()) {
                pStmt = conn.prepareStatement(SQL);
                
                pStmt.setString	(1, student.getStudentId()		);
                pStmt.setString	(2, student.getFirstName()		);
                pStmt.setString	(3, student.getLastName()		);
                pStmt.setString	(4, student.getCourse()			);
                pStmt.setInt	(5, student.getYearLevel()		);
                pStmt.setInt	(6, student.getUnitsEnrolled()	);
                
                pStmt.executeUpdate();
                
                System.out.println("# Successfully created a new student record into the database!");
                return true;
            }
            
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
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
