package com.ninza.hrm.api.project_UsingUtilitytest;

import static io.restassured.RestAssured.*; //static import
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass.ProjectPojo;
import com.ninza.hrm.constants.endpoints.IEndPoint;
import io.restassured.response.Response;

public class Project_UsingUtilityTest extends BaseAPIClass {
	
//	JavaUtility jLib = new JavaUtility();
//	FileUtility fLib = new FileUtility();
//	DataBaseUtility dbLib = new DataBaseUtility();
	
	
	ProjectPojo pObj;   //make global as used in 2nd Test case
	
	@Test
	public void addSingleProjectWithCreatedTest() throws Throwable {
		
		               //String BASEURI = fLib.getDataFromPropertiesFile("BaseUri");
		
		        String expSucMsg = "Successfully Added";
				String projectName ="Ironman_"+jLib.getRandomNumber();
				
	 //create an object to POJO class   ~ for json payload 
		 pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
		
	
		//Verify the ProjectName in API layer
		 Response resp = given()
				.spec(specReqObj)
		       .body(pObj)
		.when()
		   .post(IEndPoint.ADDProj);  //uri and end point not hardcoded
		
		resp.then()
		 .assertThat().statusCode(201)
		 .assertThat().time(Matchers.lessThan(3000L))  //time in float L
		 .spec(specRespObj)
		 .log().all();
		
		String actMsg = resp.jsonPath().get("msg");
		Assert.assertEquals(expSucMsg, actMsg);
	
        String actProjectName=resp.jsonPath().get("projectName");
        Assert.assertEquals(projectName, actProjectName);
        
        
        //Verify the ProjectName in DB layer
                                               // [we don't have access to DB so DB validation is not possible]

//     boolean flag = dbLib.executeQueryVerifyAndGetData("select * from project", 4, actProjectName);
//     Assert.assertTrue(flag, "Project in DB is not Verified");

        
	}

	
	@Test(dependsOnMethods = "addSingleProjectWithCreatedTest")
	public void addDuplicateProject() throws Throwable {
		
		String BASEURI = fLib.getDataFromPropertiesFile("BaseUri");
		
		     given()
		        .spec(specReqObj)
				.body(pObj)                   //using pojo object of the 1st test case
			.when()
				 .post(BASEURI+IEndPoint.ADDProj)
			.then()
				 .assertThat().statusCode(409)
				 .assertThat().time(Matchers.lessThan(300L))
				 .spec(specRespObj)
				 .log().all();
		 
	}
	
}



