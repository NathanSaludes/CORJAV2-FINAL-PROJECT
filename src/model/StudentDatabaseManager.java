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
					" studId VARCHAR(15), " 		+
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
                
                if(pStmt.executeUpdate() == 1) {
                	System.out.println("# Successfully inserted a new student record into the database!");                	
                }
                
                
                return true;
            }
            
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            System.out.println("# Failed to insert student record into the database.");
        }
        
        return false;
	}

	@Override
	public boolean deleteRecord(String studId) {
		// TODO: deleteRecord()
		// delete via studentId
		
		try {
			PreparedStatement pStmt = null;
			
			String SQL =	  "DELETE FROM " + databaseTableName 
							+ "WHERE studId = ?";
			
			pStmt = conn.prepareStatement(SQL);
			
			// set the required parameter value
			pStmt.setString(1, studId);
			
			pStmt.executeUpdate();
			//ResultSet rs = pStmt.executeQuery(SQL);
			
		}catch(SQLException se){
		//Handle errors for JDBC
		//se.printStackTrace();
		System.err.println(se.getMessage());
			
		}catch(Exception e){
		 //Handle errors for Class.forName
		 //e.printStackTrace();
		System.err.println(e.getMessage());
		}
		
		return false;
	}

	@Override
	public ResultSet readRecord(String studIdOrLastName) {
		/*Statement stmt 	= null;
		String SQL 		= "SELECT * FROM " + databaseTableName;*/
		
		//student search via studentID or studentLastName
		
		try {
			
			PreparedStatement pStmt = null;
			
			String SQL =	  "SELECT FROM " + databaseTableName 
							+ "WHERE studId = ?";
			
			pStmt = conn.prepareStatement(SQL);
			
			pStmt.setString(1, studIdOrLastName);
			
			pStmt.executeUpdate();
			
			System.out.println("\nPlease wait . . . searching for student record " + studIdOrLastName);
			
			//If resultset = null, print("Record not found") ??
			
		}catch(SQLException se){
			//Handle errors for JDBC
			//se.printStackTrace();
			System.err.println(se.getMessage());
				
		}catch(Exception e){
			 //Handle errors for Class.forName
			 //e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	public void listAll() {
		PreparedStatement pStmt = null;
		ResultSet res 			= null;
		String SQL				= null;
		
		try {
			if (conn.isValid(5) && !conn.isClosed()) {
				
				// print all students
				SQL = "SELECT * FROM " + databaseTableName;				
                pStmt = conn.prepareStatement(SQL);
                res = pStmt.executeQuery();
                
                for(int counter=1; res.next(); counter++) {
                	System.out.println("\n[" + counter + "]");
                	System.out.println("ID: " + res.getString("studId"));
                	System.out.println("Name: " + res.getString("lastName")+", "+res.getString("firstName"));
                	System.out.println("Course: " + res.getString("course"));
                	System.out.println("Year Level: " + res.getString("yearLevel"));
                	System.out.println("Units Enrolled: " + res.getString("unitsEnrolled"));
                }
                
                hr(1);
                
                // count the number of total students
                SQL = "SELECT COUNT(*) as totalStudents FROM " + databaseTableName;
                pStmt = conn.prepareStatement(SQL);
                res = pStmt.executeQuery();
                
                if(res.next()) {
                	System.out.println("Total Students Enrolled: " + res.getInt("totalStudents"));
                }
                
                // count total number of cs students
                SQL = "SELECT COUNT(*) as CS_STUDENTS FROM " + databaseTableName + " WHERE course='BS CS'";
                pStmt = conn.prepareStatement(SQL);
                res = pStmt.executeQuery();
                
                if(res.next()) {
                	System.out.println("Total Students in CS: " + res.getInt("CS_STUDENTS"));
                }
                
                // count total number of IT students
                
                // count total number of IS students
                
                
            }
			
		} catch(SQLException sqle) {
			System.out.println("listAllStudents Exception");
			System.out.println("SQL ERROR: " + sqle.getMessage());
		} catch(Exception e) {
			System.out.println("ListAllStudents Exception");
			System.out.println("ERROR: " + e.getMessage());
		}
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
	
	// checks if student database manager has a valid connection
	public boolean hasValidConnection() {
		try {
			if(conn.isValid(5)) {
				return true;
			}
		}catch (Exception e) {
			System.out.println("ERROR: Unable to connect to the database. Please check your database configurations.");
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
