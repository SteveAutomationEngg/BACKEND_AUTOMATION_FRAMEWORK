package com.ninza.hrm.api.projecttest;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.Driver;
import com.ninza.hrm.api.pojoclass.ProjectPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProjectTest {
	
	ProjectPojo pObj;   //make global as used in 2nd Test case
	
	@Test
	public void addSingleProjectWithCreatedTest() throws Throwable {
		
		
		 //create an object to POJO class   ~ for json payload 
		        Random random = new Random();
		        int ranNum =random.nextInt(5000);
		        
		        String expSucMsg = "Successfully Added";
				String projectName ="Ironman_"+ranNum; 
				
		 pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
		
		
		
		//Verify the ProjectName in API layer
		Response resp = given()
		.contentType(ContentType.JSON)
		.body(pObj)
		.when()
		.post("http://49.249.28.218:8091/addProject");
		
		resp.then()
		.assertThat().statusCode(201)
		.assertThat().time(Matchers.lessThan(3000L))  //time in float L
		.assertThat().contentType(ContentType.JSON)
		.log().all();
		
		String actMsg = resp.jsonPath().get("msg");
		Assert.assertEquals(expSucMsg, actMsg);
	
        String actProjectName=resp.jsonPath().get("projectName");
        Assert.assertEquals(projectName, actProjectName);
        
        
        //Verify the ProjectName in DB layer
                                               // [we don't have access to DB so DB validation is not possible]
     
//        Boolean flag = false;
//        Driver driverRef = new Driver();
//        DriverManager.registerDriver(driverRef);
//        Connection con = DriverManager.getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm","root@%","root");
//        ResultSet result =con.createStatement().executeQuery("select * from project");  //Project module have separate table in database
//                                                                                       //ProjectName is in 4th column of the table
//        while(result.next()) {
//        	if (result.getString(4).equals(projectName)) {
//        		flag = true;
//        		break;
//        	}
//        }
//        
//        con.close();
//        
//        Assert.assertTrue(flag, "Project in DB is not Verified");
        
	}

	@Test(dependsOnMethods = "addSingleProjectWithCreatedTest")
	public void addDuplicateProject() {
		
		        given()
				.contentType(ContentType.JSON)
				.body(pObj)                   //using pojo object of the 1st test case
				.when()
				 .post("http://49.249.28.218:8091/addProject")
				.then()
				 .assertThat().statusCode(409)
				 .assertThat().time(Matchers.lessThan(300L))
				 .assertThat().contentType(ContentType.JSON)
				 .log().all();
		 
	}
	
	
	
}



