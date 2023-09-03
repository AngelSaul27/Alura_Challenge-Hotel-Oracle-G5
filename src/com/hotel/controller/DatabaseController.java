package com.hotel.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseController {
	
	private DataSource datasource;
	
	public DatabaseController(){
		var poolDataSource = new ComboPooledDataSource();
		poolDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/alurahotel?useTimeZone=true&serverTimeZone=UTC");
		poolDataSource.setUser("root");
		poolDataSource.setPassword("CPSsCOINDKM27@");
		poolDataSource.setMaxConnectionAge(1000);
		
		this.datasource = poolDataSource;
	}
	
	
	public Connection getConecction() throws SQLException{
		return this.datasource.getConnection();
	}
}
