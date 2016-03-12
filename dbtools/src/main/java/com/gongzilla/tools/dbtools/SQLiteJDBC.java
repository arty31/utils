package com.gongzilla.tools.dbtools;

import java.sql.*;
import java.text.SimpleDateFormat;

public class SQLiteJDBC
{		
	  public static void main( String args[] )
	  {
	    Connection c = null;
	    try {
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = getConnection(); 
	      
	      createTables(c); 
	      insertData(c);
	      
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	  }
	  
	  static Connection getConnection() throws SQLException {
	      Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");		  
          return c;
	  }
	  
	  
	  static void  createTables(Connection c) throws SQLException {
		  
	      Statement stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS ALARMS " +
	                   "(ID INT PRIMARY KEY     NOT NULL, " +
	    		       " TIMESTAMP      TEXT    NOT NULL, " +
	                   " SLOGAN         TEXT    NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
		  
		  
	  }
	  
	  
	  static void insertData (Connection c) throws SQLException {

		Statement stmt = c.createStatement();

		long now = System.currentTimeMillis();
		
		for (int i = 120; i >=0 ; i-- ) {
			int id = 1 + (120-i);
			String sql = "INSERT INTO ALARMS (ID,TIMESTAMP,SLOGAN,ADDRESS,SALARY) "
					+ "VALUES ("+id+", '" + getTimeAsString(now-i*(60*1000)) + 
					"','slogan"+id+"', 'California', 20000.00 );";
			System.out.println("stmt:"+sql);
					stmt.executeUpdate(sql);
					stmt.close();		
		}
		c.commit(); 
	  }
	  
	  
	  static String getTimeAsString(long timeInMs ) {
		  
		  return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timeInMs);
		  
		  
	  }
	  
	  
	  
}