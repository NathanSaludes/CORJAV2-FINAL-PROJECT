package model;

import java.sql.*;

import view.View;

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
		conn = getConnection();
		
		
		// 4) if connection is valid, create a new student table
		if(hasValidConnection()) {
			createStudentTable();			
		}
	}
	
	
	// GET DATABASE CONNECTION
	private Connection getConnection() {
		Connection databaseConnection = null;
		
		try {
			
			// register jdbc driver
			Class.forName(JDBC_DRIVER);
				
			// open a connection
			System.out.println("# Connecting to database...");
			databaseConnection = DriverManager.getConnection(DB_URL, "root", "");
			
			System.out.println("DATABASE CONNECTION STATUS: Valid");
			hr(1);
			
			return databaseConnection;
			
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
			// 1) delete existing tables
			deleteDuplicateTable();
			
			// SAMPLE OUTPUT: "Creating <DB_NAME> table"
			System.out.println("# Creating new table, \'" + databaseTableName.toLowerCase() + "\' ...");
			
			// 2) initialize statement object
			stmt = conn.createStatement();
			
			// 3) initialize create table SQL
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
			
			// 4) execute create table
			stmt.executeUpdate(SQL);
			
			System.out.println("# Successfully created \'" + databaseTableName.toLowerCase() + "\' table!");
			hr(1);
			
			// 5) close resources
			stmt.close();
			
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
			// 1) get database meta data
			DatabaseMetaData dmbd = conn.getMetaData();
			
			// 2) get all tables from the database
			ResultSet res = dmbd.getTables(null, null, "%", null);
			
			// 3) initialize statement
			stmt = conn.createStatement();
			
			// 4) scan results
			while(res.next()) {
				if(databaseTableName.equalsIgnoreCase(res.getString("TABLE_NAME"))) {
					System.out.println("# Found an existing \'" + databaseTableName.toLowerCase() + "\' table!");
					
					// 5) update SQL
					SQL = SQL.concat(res.getString("TABLE_NAME"));
					
					// 6) Execute SQL update
					stmt.executeUpdate(SQL);
					
					System.out.println("# Dropped \'" + res.getString("TABLE_NAME").toLowerCase() + "\' table...");
					System.out.println("#");
				}
			}
			
			// close resources
			res.close();
			stmt.close();
			
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
		
		// 1) initialize prepared statement SQL
		String SQL =	  "INSERT INTO " + databaseTableName + " (" 
						+ "studId, firstName, lastName, course, yearLevel, unitsEnrolled) "
						+ "values (?,?,?,?,?,?)";
		
        try {
        	// 2) validate connection
            if (hasValidConnection()) {
            	
            	// 3) initialize prepared statement object
                pStmt = conn.prepareStatement(SQL);
                
                // set required parameter values from the prepared statement
                pStmt.setString	(1, student.getStudentId()		);
                pStmt.setString	(2, student.getFirstName()		);
                pStmt.setString	(3, student.getLastName()		);
                pStmt.setString	(4, student.getCourse()			);
                pStmt.setInt	(5, student.getYearLevel()		);
                pStmt.setInt	(6, student.getUnitsEnrolled()	);
                
                // 4) execute update
                if(pStmt.executeUpdate() == 1) {
                	System.out.println("# Successfully inserted a new student record into the database!");
                	return true;
                }
                
                // 5) close resources
                pStmt.close();
            }
            
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            System.out.println("# Failed to insert student record into the database.");
        }
        
        return false;
	}

	@Override
	public boolean deleteRecord(String studId) {
		// delete via studentId
		try {
			PreparedStatement pStmt = null;			
			String SQL = "DELETE FROM " + databaseTableName	+ "WHERE studId = ?";
			
			pStmt = conn.prepareStatement(SQL);
			
			// set the required parameter value
			pStmt.setString(1, studId);
			
			pStmt.executeUpdate();
			
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
	
	public void listAllByCourse(String course) {
		// for command R, search by course 
		
		PreparedStatement pStmt = null;
		ResultSet res 			= null;
		String SQL				= null;
		
		try {
			if (hasValidConnection()) {
				
               //----- course counter -----//

                System.out.println("\nCreating a report for all " + course + " students:");
                
             // count the number of total students
                SQL = "SELECT COUNT(*) AS courseCounter FROM " + databaseTableName + " WHERE course =?";
                pStmt = conn.prepareStatement(SQL);
                pStmt.setString(1, course);
                res = pStmt.executeQuery();
                
                System.out.println("\nTotal number of " + course + " students: " + res.getInt("courseCounter"));
                
                System.out.println("\n\nList of students enrolled in " + course);
                hr(2);
				
				// print all students
				SQL = "SELECT * FROM " + databaseTableName + " WHERE course =?";				
                pStmt = conn.prepareStatement(SQL);
                pStmt.setString(1, course);
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
                
               
                // close resources
               pStmt.close();
               res.close();
            }
			
		} catch(SQLException sqle) {
			System.out.println("listAllStudentsByCourse Exception");
			System.out.println("SQL ERROR: " + sqle.getMessage());
		} catch(Exception e) {
			System.out.println("ListAllStudentsByCourse Exception");
			System.out.println("ERROR: " + e.getMessage());
		}
		
		
	}
	
	public void listAll() {
		
		PreparedStatement pStmt = null;
		ResultSet res 			= null;
		String SQL				= null;
		
		try {
			if (hasValidConnection()) {
				
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
                SQL = "SELECT COUNT(*) AS totalStudents FROM " + databaseTableName;
                pStmt = conn.prepareStatement(SQL);
                res = pStmt.executeQuery();
                
                if(res.next()) {
                	System.out.println("Total Students Enrolled: " + res.getInt("totalStudents"));
                	System.out.println();
                }
                
                
                // count the number of students in each course
                SQL = "SELECT COUNT(*) AS courseCount FROM " + databaseTableName + " WHERE course=?";
                String stmtParam = null;
                
                for(int counter=1; counter <= 3; counter++) {
                	switch(counter) {
                	case 1:
                		stmtParam = "CS";
                		break;
                	case 2:
                		stmtParam = "GD";
                		break;
                	case 3:
                		stmtParam = "WD";
                		break;
            		default: //skip
            			break;
                	}
                	                	
                	pStmt = conn.prepareStatement(SQL);
                	pStmt.setString(1, stmtParam);
                	res = pStmt.executeQuery();
                	
                	if(res.next()) {
                		System.out.println("Total Students in " + stmtParam + ": " + res.getInt("courseCount"));
                	}
                }
                
                hr(1);
               
                // close resources
               pStmt.close();
               res.close();
            }
			
		} catch(SQLException sqle) {
			System.out.println("listAllStudents Exception");
			System.out.println("SQL ERROR: " + sqle.getMessage());
		} catch(Exception e) {
			System.out.println("ListAllStudents Exception");
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	public void clearTable() {
		
		Statement stmt  = null;
		String SQL = "DELETE FROM " + databaseTableName;
		
		try {
			stmt = conn.createStatement();
			
			System.out.println("# Deleting all records...Please wait.");
			stmt.executeUpdate(SQL);
			System.out.println("# Successfully deleted ALL records");
			
			hr(1);
		} catch(SQLException sqle) {
			System.out.println("SQL ERROR: " + sqle.getMessage());
			// sqle.printStackTrace();
		}
		
		
	}
	// ==========================================================================================================================================================
	

	// CLOSE ALL OPENED RESOURCES
	@Override
	public boolean terminateConnection() {
		try {
			// 1) validate connection
			if(!conn.isClosed() && conn.isValid(5)) {
				// 2) close database connection
				System.out.println("# Closing database connection...");
				conn.close();
				System.out.println("# DB Closed");
				View.quitCommandMessage();
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
		} catch (Exception e) {
			// skip
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
		case 3:
			System.out.println("___________________________________________________________________"
					+ "__________________________________________________________________________________________________________________________");
			break;
		default:
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
					+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			break;
		}
	}
}
