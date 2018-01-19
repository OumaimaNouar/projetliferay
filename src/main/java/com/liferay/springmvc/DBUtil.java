package com.liferay.springmvc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
	Connection connection = null;
	 Statement smt = null;
	 public DBUtil()
	 {
	 System.out.println("hi Postgre");
	 try
	 {
	 Class.forName("org.postgresql.Driver");
	 String url = "jdbc:postgresql://localhost:5433";
	 connection = DriverManager.getConnection(url,"postgres","root");
	 if(connection.equals(null))
	 {
	 System.out.println("connection was failed"); 
	 }
	 else
	 {
	 System.out.println("connected successfully"); 
	 }
	 }
	 catch(Exception exception)
	 {
	 exception.printStackTrace(); 
	 }
	 
	 }
	public void Execute(String sql) {
		// TODO Auto-generated method stub
		 try {
			 smt = connection.createStatement();
			 smt.execute(sql);
			 } catch (SQLException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
	}
}
