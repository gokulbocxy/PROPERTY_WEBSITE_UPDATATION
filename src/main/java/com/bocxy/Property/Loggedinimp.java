package com.bocxy.Property;

import java.sql.*;

public class Loggedinimp {
	
	    String myUrl ="jdbc:mysql://localhost:3306/property_db";
	    String root="root";
	    String pswd="yazhini1998";
	
	public String Updateloggedin(String username) throws Exception
	{
		
		
			 Connection conc = DriverManager.getConnection(myUrl, root, pswd);
			 
			 String query =  "UPDATE property_db.registeredusers SET loggedin = CASE loggedin  WHEN "+"'"+0+"'" +" THEN "+"'"+1+"'" +" ELSE "+"'"+2+"'" +" END  where username = "+"'"+username+"'";
			 Statement stm = conc.createStatement();
			 int rset=stm.executeUpdate(query);
			 System.out.println(username);
			return username;
			 
		
			
	}

	public boolean Updateloggedout(String username) throws Exception
	{
		try {
			Connection conc = DriverManager.getConnection(myUrl, root, pswd);
			String query = "UPDATE property_db.registeredusers SET loggedin = 0 WHERE username = ?";
			PreparedStatement pstmt = conc.prepareStatement(query);
			pstmt.setString(1, username);
			int rowsUpdated = pstmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}





}