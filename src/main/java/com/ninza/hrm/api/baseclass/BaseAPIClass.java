package com.ninza.hrm.api.baseclass;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;
import static  io.restassured.RestAssured.*;            //static import
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * @author MOHIT KUMAR
 * 
 * This Base class will take care of DB config, report Config and object creation of all Utitlies class
 *
 * and also used for RequestSpecBuilder & ResponseSpecBuilder class which is used to configure common REQUEST confiuration 
 * & common  RESPONSE Validation configuration 
 * so, No need to do it inside API testScripts.
 */

public class BaseAPIClass {

	// create Object to all Utitlity (it will be inhertied to all Testng class)
	public JavaUtility jLib = new JavaUtility();               //public as used in other package
	public FileUtility fLib = new FileUtility();
	public DataBaseUtility dbLib = new DataBaseUtility();
	
	public static RequestSpecification specReqObj;   //static as used in different classes when we run Testng.xml 
	public static ResponseSpecification specRespObj;
	
	@BeforeSuite
	public void configBS() throws Throwable {
		
		dbLib.getDbconnection();
		System.out.println(" =======> Connect To DB <====== ");
		System.out.println("====> Report Configuration <===");
		
	//RequestSpecBuilder
		RequestSpecBuilder reqbuilder = new RequestSpecBuilder();
		reqbuilder.setContentType(ContentType.JSON);
		//reqbuilder.setAuth(basic("username", "password"));
		//reqbuilder.addHeader("", "");
		reqbuilder.setBaseUri(fLib.getDataFromPropertiesFile("BaseUri")); //no need to pass BaseURi in testScripts
		specReqObj= reqbuilder.build();                   // returns RequestSpecification Interface object
		
	//ResponseSpecBuilder
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
	    specRespObj = resBuilder.build();                // returns ResponseSpecification Interface object
	
	}
	
	@AfterSuite
	public void configAS()  throws Throwable {
		
		dbLib.closeDbconnection();
		System.out.println(" ========> Diconnect To DB <===== ");
		System.out.println("==========> Report Backup <======");
	}
	
}
