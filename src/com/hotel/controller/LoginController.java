package com.hotel.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

	private DatabaseController db = new DatabaseController();
	
	public Boolean validarCredenciales(String email, String password) {
		try {
		    Connection con = db.getConecction();
		    if(con != null) {
		        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
		        
		        PreparedStatement pre_statement = con.prepareStatement(query);
		        pre_statement.setString(1, email);
		        pre_statement.setString(2, password);
		        
		        ResultSet resultset = pre_statement.executeQuery();
  
		        if (resultset.next()) { 
		        	con.close();
		            return true;
		        }
		        
		        con.close();
		    }
		} catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	
}
