package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Staff extends User implements Serializable{

	public Staff() {
		super();
	}

	public Staff(String id, String name, String password) {
		super(id, name, password);
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public static boolean login(String username, String password) throws SQLException{
		log.trace("Staff login attempted...");
		
		// Connect to DB
		Connection con = DBConnect.getConnection();
		// Query staff table
		PreparedStatement checkStaff = con.prepareStatement("SELECT * FROM staff WHERE name = '" + username + "' AND password = '" + password + "'");
		// Variable to execute query
		ResultSet result = checkStaff.executeQuery();
		
		
		// Check for user occurrence in staff table:
		int count = 0;
		while(result.next())
			++count;
		if(count == 1){
			log.trace("Staff login successful.");
			return true;
		}
		
		// Close Connection
		con.close();	
		return false;
	}
	
}
