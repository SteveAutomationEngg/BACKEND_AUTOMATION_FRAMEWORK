package com.ninza.hrm.api.genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.xdevapi.Result;


public class DataBaseUtility {

	 Connection con;

    
    FileUtility fLib = new FileUtility();  // to fetch the DbUrl, db UN , Dn pwd and remove hardcode
    
    
	public void getDbconnection(String url, String username, String password) throws Throwable {
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {}
	}
	
	
	
	public void getDbconnection() throws Throwable {
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			con = DriverManager.getConnection(fLib.getDataFromPropertiesFile("DBUrl"), 
					fLib.getDataFromPropertiesFile("Db_Username"),
					fLib.getDataFromPropertiesFile("DB_Password"));
			
		} catch (Exception e) {}
	}

	
	
	public void closeDbconnection() throws Throwable {
		try {
			con.close();	
		}
		catch (Exception e) {}
	}
	
	
	public ResultSet executeSelectQuery(String query) throws Throwable {
		
		ResultSet result= null;
		try{
			Statement stat = con.createStatement();
		    result = stat.executeQuery(query);
		}catch (Exception e) {}
		
		return result;
	}
	
	public int executeNonSelectQuery(String query) {

		int result= 0;
		try{
			Statement stat = con.createStatement();
		    result = stat.executeUpdate(query);
		}catch (Exception e) {}
		
		return result;
	}
	
	/**
	 * execute the query in DB and verify the Expected Data
	 * @param query
	 * @param columnIndex
	 * @param expectedData
	 * @return
	 * @throws Throwable
	 */
	
	public boolean  executeQueryVerifyAndGetData(String query,int columnIndex , String expectedData) throws Throwable{
		
		boolean flag = false;
		   ResultSet result= con.createStatement().executeQuery(query);
		   
		   while(result.next()) {
			   if(result.getString(columnIndex).equals(expectedData)) {
				   flag =true;
				   break;
			   }
		   }
		   
		   if(flag) {
			   System.out.println(expectedData + " =======> data verified in data base table");
			   return true;
		   }else {
			   System.out.println(expectedData + " =======> data not verified in data base table");
			   return false;
		   }
	}
	 
}
