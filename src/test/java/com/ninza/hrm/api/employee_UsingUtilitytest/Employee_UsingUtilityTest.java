package com.ninza.hrm.api.employee_UsingUtilitytest;

import static io.restassured.RestAssured.*; //static import
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass.EmployeePOJO;
import com.ninza.hrm.api.pojoclass.ProjectPojo;
import com.ninza.hrm.constants.endpoints.IEndPoint;


public class Employee_UsingUtilityTest extends BaseAPIClass {
	
//	JavaUtility jLib = new JavaUtility();
//	FileUtility fLib = new FileUtility();
//	DataBaseUtility dbLib = new DataBaseUtility();
	
	
	@Test
	public void addEmployeeToProject() throws Throwable {                 //Request Chaining in this Test case
		
		                //String BASEURI = fLib.getDataFromPropertiesFile("BaseUri");
		        
		        String projectName="Ironman_"+jLib.getRandomNumber(); //projectName
		        String userName = "user"+jLib.getRandomNumber();      //EmployeeName
		        
		        
	//API -1 ===> ADD A PROJECT IN SIDE SERVER	
		        
		        //create an object to POJO class   ~ for json payload 
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
		

	   given()
	   .spec(specReqObj)
		.body(pObj)
	.when()
		 .post(IEndPoint.ADDProj)
	.then()
	    .spec(specRespObj)
		.log().all();
		
		
//API -2 ==>  ADD EMPLOYEE TO SAME PROJECT

		EmployeePOJO empObj = new EmployeePOJO("Architect", "20/11/2024", "mohitbaba@gmail.com",userName ,12, "1234567890", projectName,"ROLE_EMPLOYEE",userName);

		given()
		 .spec(specReqObj)
		 .body(empObj)
		.when()
		 .post(IEndPoint.ADDEmp)
		.then()
		 .assertThat().statusCode(201)
		 .and()
		 .time(Matchers.lessThan(3000L))
		 .spec(specRespObj)
		 .log().all();
		
		
		
		 //Verify the EmployeeName in DB layer
		
                                          // [we don't have access to DB so DB validation is not possible]
                   
//boolean flag = dbLib.executeQueryVerifyAndGetData("select * from employee", 5, userName);
//Assert.assertTrue(flag, "Employee in DB is not Verified");

	}	

	
	
	
	@Test
	public void addEmployeWithOutEmailTest() throws Throwable {
		  
		//String BASEURI = fLib.getDataFromPropertiesFile("BaseUri");
	        
	        String projectName="Ironman_"+jLib.getRandomNumber(); //projectName
	        String userName = "user"+jLib.getRandomNumber();      //EmployeeName
	        
	        
//API -1 ===> ADD A PROJECT IN SIDE SERVER	
	        
	        //create an object to POJO class   ~ for json payload 
	ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
	

 given()
    .spec(specReqObj)
	.body(pObj)
.when()
	 .post(IEndPoint.ADDProj)
.then()
    .spec(specRespObj)
	.log().all();
	
	
//API -2 ==>  ADD EMPLOYEE TO SAME PROJECT

	EmployeePOJO empObj = new EmployeePOJO("Architect", "20/11/2024", "",userName ,12, "1234567890", projectName,"ROLE_EMPLOYEE",userName);
                                            //in empObj email :""
	given()
	 .spec(specReqObj)
	 .body(empObj)
	.when()
	 .post(IEndPoint.ADDEmp)
	.then()
	  .assertThat().statusCode(500)
	  .and()
	   .time(Matchers.lessThan(300L))
	   .spec(specRespObj)
	   .log().all();
			
	}
}


