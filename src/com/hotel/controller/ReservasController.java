package com.hotel.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import com.hotel.model.ReservaModel;

public class ReservasController {
	
	private DatabaseController db = new DatabaseController();
	
	public void fillTable(DefaultTableModel model){
		try {
			Connection con = db.getConecction();
			Statement statement = con.createStatement();
			String consultaSQL = "SELECT * FROM Reserva";
			ResultSet resultSet = statement.executeQuery(consultaSQL);
			
			while (resultSet.next()) {
	            int id = resultSet.getInt("IdReserva");
	            String entrada = resultSet.getString("Entrada");
	            String salida = resultSet.getString("Salida");
	            String amount = resultSet.getString("Importe");
	            String pay = resultSet.getString("TipoPago");

	            model.addRow(new Object[]{id, entrada, salida, amount, pay});
	        }
			
			if (resultSet != null) resultSet.close();
	        if (statement != null) statement.close();
	        if (con != null) con.close();
		}catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Boolean registerReserva(ReservaModel model){
		try {
			String consultaSQL = "INSERT INTO Reserva (Entrada, Salida, Importe, TipoPago) VALUES (?, ?, ?, ?)";
			
			Connection con = db.getConecction();
			PreparedStatement preparedStatement = con.prepareStatement(consultaSQL);			
		    preparedStatement.setString(1, model.getCheckIn());
		    preparedStatement.setString(2, model.getCheckOut());
		    preparedStatement.setInt(3, model.getAmount());
		    preparedStatement.setString(4, model.getTypePay());
		    
		    int filasAfectadas = preparedStatement.executeUpdate();
		    if (filasAfectadas > 0) {
		    	con.close();
		    	return true;
		    }
		    
		    con.close();
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	public Boolean eliminarReserva(int id) {
		try {
			String sentenciaSQL = "DELETE FROM Reserva WHERE IdReserva = ?";
			
			Connection con = db.getConecction();
			PreparedStatement preparedStatement = con.prepareStatement(sentenciaSQL);	
            preparedStatement.setInt(1, id);
            
		    int filasAfectadas = preparedStatement.executeUpdate();
		    
		    if (filasAfectadas > 0) {
		    	con.close();
		    	return true;
		    }
		    
		    con.close();
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}

	public Boolean actualizarReserva(int id, ReservaModel model) {
		try {
	        String sentenciaSQL = "UPDATE Reserva SET Entrada=?, Salida=?, Importe=?, TipoPago=? WHERE IdReserva=?";
	        
	        Connection con = db.getConecction();
	        PreparedStatement preparedStatement = con.prepareStatement(sentenciaSQL);    
	        preparedStatement.setString(1, model.getCheckIn());
	        preparedStatement.setString(2, model.getCheckOut());
	        preparedStatement.setInt(3, model.getAmount());
	        preparedStatement.setString(4, model.getTypePay());
	        preparedStatement.setInt(5, id); // Usamos el ID como condición
	        
	        int filasAfectadas = preparedStatement.executeUpdate();
	        
	        if (filasAfectadas > 0) {
	            con.close();
	            return true;
	        }
	        
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return false;
	}
}
