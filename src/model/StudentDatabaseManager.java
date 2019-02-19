package model;

import java.sql.*;

import exceptions.StudentNotFoundException;
import view.View;

public class StudentDatabaseManager {
	
	private static 	String DB_TABLENAME 				= null;	// USER PROVIDED DATABASE TABLE NAME
	private static	Connection conn 					= null; // DATABASE CONNECTION OBJECT
	
	
	// DATABASE CONFIGURATION
	private String JDBC_DRIVER			= null;
	private String DB_NAME				= null;
	private static String DB_URL		= null;
	private Student[] studs;
	
	// CONSTRUCTOR
	public StudentDatabaseManager(
			String JDBC_DRIVER, 
			String DB_NAME, 
			String DB_URL, 
			String tableName
	){
		DB_TABLENAME 					= tableName;
		this.JDBC_DRIVER				= JDBC_DRIVER;
		this.DB_NAME					= DB_NAME;
		StudentDatabaseManager.DB_URL	= DB_URL;
	}


	public String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public String getDB_URL() {
		return DB_URL;
	}

	// INITIALIZE CONNECTION
	public String initializeConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, "root", "");
			return "Connected";
			
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			return "Driver Error";
		} catch (SQLException e) {
			// e.printStackTrace();
			return "SQL Error";
		}
	}
	
	// GET DATABASE CONNECTION
	public void connectToDatabase() {
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, "root", "");
			
		} catch (ClassNotFoundException cnfe) {
			// cnfe.printStackTrace();
			hr(1);
			
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			hr(1);
			
		}
	}
	
	// CREATE DATABASE
	public boolean createDatabase(String databaseName) {
		
		Statement stmt = null;
		String SQL				= null;
		DatabaseMetaData dbmd	= null;
		ResultSet res			= null;
		
		// CHECK IF THERE IS EXISTING DATABASE NAME
		try {			
			dbmd = conn.getMetaData();
			res = dbmd.getCatalogs();
			DB_URL = DB_URL + databaseName;
			
			// check if there is existing database else, create a new database
			if(!databaseExists(res)) {
				SQL = "CREATE DATABASE `" + databaseName + "`";	
				stmt = conn.createStatement();	
				stmt.executeUpdate(SQL);
				connectToDatabase();
			} else {
				connectToDatabase();
			}
			
			return true;
			
		} catch (SQLException e) {
//			e.printStackTrace();
			return false;
		}
	}
	
	private boolean databaseExists(ResultSet res) throws SQLException{
		while(res.next()) {
			if(res.getString("TABLE_CAT").equalsIgnoreCase(DB_NAME)) {
				return true;
			}
		}
		
		return false;
	}

	// CREATE STUDENT TABLE
	public boolean createStudentTable() {
		Statement 	stmt = null;
		String 		SQL  = null;
		
		try {			
			stmt = conn.createStatement();
			
			// 3) initialize create table SQL
			SQL  =	"CREATE TABLE " + DB_TABLENAME + " ("  +
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
			// 5) close resources
			stmt.close();
			
			return true;
			
		} catch (SQLException sqle) {
			// sqle.printStackTrace();
			return false;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	} // create student table
		
	// DELETE DUPLICATE TABLES
	public int deleteDuplicateTable() {
		Statement stmt 	= null;
		String SQL 		= "DROP TABLE ";
		
		try {
			DatabaseMetaData dmbd = conn.getMetaData();
			ResultSet res = dmbd.getTables(null, null, "%", null);
			stmt = conn.createStatement();
			
			while(res.next()) {
				if(DB_TABLENAME.equalsIgnoreCase(res.getString("TABLE_NAME"))) {
					SQL = SQL.concat(res.getString("TABLE_NAME"));
					stmt.executeUpdate(SQL);
					return 1;
				}
			}
			
			res.close();
			stmt.close();
			
			return 0;
			
		} catch (SQLException sqle) {	
			// sqle.printStackTrace();
			return -1;
		} catch (Exception e) {
			// e.printStackTrace();
			return -1;
		}
	} // delete duplicate table


	// FUNDAMENTAL DATABASE OPERATIONS ==========================================================================================================================
	public boolean insertStudentRecord(Student student) {
		PreparedStatement pStmt = null;
		
		// 1) initialize prepared statement SQL
		String SQL =	  "INSERT INTO " + DB_TABLENAME + " (" 
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
                	return true;
                }
                
                // 5) close resources
                pStmt.close();
            }
            
        } catch (SQLException sqle) {
            // sqle.printStackTrace();
            return false;
        }
        
        return false;
	}

	public boolean deleteStudentRecord(String studId) {
		PreparedStatement pStmt = null;
		String SQL 				= null;
		
		try {
			SQL = "DELETE FROM " + DB_TABLENAME + " WHERE studId=?";
			
			pStmt = conn.prepareStatement(SQL);
			pStmt.setString(1, studId);		
			
			pStmt.executeUpdate();
			pStmt.close();
			
			return true;
			
		} catch(SQLException se){
			//se.printStackTrace();
			return false;
		
		} catch(Exception e){
			//e.printStackTrace();
			return false;
		}
	}

	public Student searchStudentRecord(String studIdOrLastName) throws StudentNotFoundException, SQLException {
		PreparedStatement pStmt = null;
		ResultSet res			= null;
		String SQL = "SELECT * FROM " + DB_TABLENAME + " WHERE studId=? OR lastName=?";
		Student stud = null;
		
			pStmt = conn.prepareStatement(SQL);
			
			pStmt.setString(1, studIdOrLastName);
			pStmt.setString(2, studIdOrLastName);
			
			res = pStmt.executeQuery();
			
			// returns true if a record exists
			if (res.next()) {				
				stud = new Student(
					res.getString("studId"),
					res.getString("lastName"),
					res.getString("firstName"),
					res.getString("course"),
					res.getInt("yearLevel"),
					res.getInt("unitsEnrolled")
				);
				
				return stud;
			} else {
				throw new StudentNotFoundException();
			}
			
	}
	
	public boolean searchStudentRecordById(String studentId) throws StudentNotFoundException {
		PreparedStatement pStmt = null;
		ResultSet res = null;
		String SQL = "SELECT * FROM " + DB_TABLENAME + " WHERE studId=?";

		try {
			pStmt = conn.prepareStatement(SQL);
			pStmt.setString(1, studentId);
			
			res = pStmt.executeQuery();
			
			if(res.next()) {
				return true;
			} else {
				throw new StudentNotFoundException();
			}
			
		} catch (SQLException sqle) {
			return false;
		}
			
	}
	
	public Object[] getAllStudents() {

		PreparedStatement pStmt = null;
		ResultSet res 			= null;
		Object[] courses		= new Object[3]; 
		Student[] SE			= null;
		Student[] GD			= null;
		Student[] WD			= null;
		int numberOfRows = 0;

		String SQL1				= "SELECT COUNT(*) AS courseCount FROM " + DB_TABLENAME + " WHERE course=?";
		String SQL2				= "SELECT * FROM " + DB_TABLENAME + " WHERE course=? ORDER BY lastName";

		// check if valid connection
		if(hasValidConnection()) {
			try {
				for(int counter=1; counter <= 3; counter++) {
					pStmt = conn.prepareStatement(SQL1);
					
					switch(counter) {
						case 1:
							pStmt.setString(1, "SE");
							res = pStmt.executeQuery();
							
							if(res.next()) {
								numberOfRows = res.getInt("courseCount");								
							}
							
							if(numberOfRows > 0) {
								SE = new Student[numberOfRows];
								
								pStmt = conn.prepareStatement(SQL2);
								pStmt.setString(1, "SE");
								res = pStmt.executeQuery();
								
								for(int i=0; res.next(); ++i) {
									SE[i] = new Student(
											res.getString("studId"),res.getString("lastName"),
											res.getString("firstName"),res.getString("course"),
											res.getInt("yearLevel"),res.getInt("unitsEnrolled")
									);
								}
								
								courses[0] = SE;
								
							} else {
								courses[0] = null;
							}
							
							break;
							
							
							
						case 2:
							pStmt.setString(1, "GD");
							res = pStmt.executeQuery();
							
							if(res.next()) {
								numberOfRows = res.getInt("courseCount");								
							}
							
							if(numberOfRows > 0) {
								GD = new Student[numberOfRows];

								pStmt = conn.prepareStatement(SQL2);
								pStmt.setString(1, "GD");
								res = pStmt.executeQuery();
								
								for(int i=0; res.next(); ++i) {
									GD[i] = new Student(
											res.getString("studId"),res.getString("lastName"),
											res.getString("firstName"),res.getString("course"),
											res.getInt("yearLevel"),res.getInt("unitsEnrolled")
									);
								}
								
								courses[1] = GD;
							} else {
								courses[1] = null;
							}
							
							break;
							
							
							
							
						case 3:
							pStmt.setString(1, "WD");
							res = pStmt.executeQuery();

							if(res.next()) {
								numberOfRows = res.getInt("courseCount");								
							}
							
							if(numberOfRows > 0) {
								WD = new Student[numberOfRows];
								
								pStmt = conn.prepareStatement(SQL2);
								pStmt.setString(1, "WD");
								res = pStmt.executeQuery();
								
								for(int i=0; res.next(); ++i) {
									WD[i] = new Student(
											res.getString("studId"),res.getString("lastName"),
											res.getString("firstName"),res.getString("course"),
											res.getInt("yearLevel"),res.getInt("unitsEnrolled")
									);
								}
								
								courses[2] = WD;
								
							} else {
								courses[2] = null;
							}
							
							break;
					}
				}
				
				pStmt.close();
				res.close();
				
				return courses;
			
			} catch (SQLException sqle) {
				// sqle.printStackTrace();
				return null;
			} catch (Exception e) {
				// e.printStackTrace();
				return null;
			}
		} // if statement
		
		return null;
	} // Generate Report Method
	
	public Student[] getAllStudentsByCourse(String course) {
		PreparedStatement pStmt = null;
		ResultSet res			= null;
		int numberOfRows 		= 0;
		
		
		
		if (hasValidConnection()) {
			try {
				String SQL1 = "SELECT COUNT(*) AS rows FROM " + DB_TABLENAME + " WHERE course=?";
				pStmt = conn.prepareStatement(SQL1);
				pStmt.setString(1, course);
				res = pStmt.executeQuery();
				
				if(res.next()) {
					numberOfRows = res.getInt("rows");
				}
				
				if(numberOfRows < 1) {
					return null;
				} else {
					Student[] studentArr = new Student[numberOfRows]; // create an array of Student of type Student Object
					
					String SQL2 = "SELECT * FROM " + DB_TABLENAME + " WHERE course=?";
					pStmt = conn.prepareStatement(SQL2);
					pStmt.setString(1, course);
					res = pStmt.executeQuery();
					
					if (res.next()) {
						res.beforeFirst(); //reset cursor
						
						for (int counter = 0; res.next(); ++counter) {
							studentArr[counter] = new Student(res.getString("studId"), res.getString("lastName"),
									res.getString("firstName"), res.getString("course"), res.getInt("yearLevel"),
									res.getInt("unitsEnrolled"));
						}
						
						return studentArr;
						
					} else {
						return null;
					}
					
				}
				
				
			} catch (SQLException sqle) {
				// sqle.printStackTrace();
				return null;
			}
		}
		
		return null; // when invalid connection
	}
		
	public boolean clearStudentTable() {
		Statement stmt  = null;
		String SQL = "DELETE FROM " + DB_TABLENAME;
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			return true;
		} catch(SQLException sqle) {
			return false;
		}
	}

	
	
	// ==========================================================================================================================================================
	// checks if student database manager has a valid connection
	public boolean hasValidConnection() {
		try {
			if(conn.isValid(5)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
		
	// CLOSE ALL OPENED RESOURCES
	public boolean terminateConnection() {
		try {
			
			// 1) validate connection
			if(!conn.isClosed() && conn.isValid(5)) {
				System.out.println("# Closing database connection...");
				
				// 2) close database connection
				conn.close();
				
				System.out.println("# DB Closed");
			
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
