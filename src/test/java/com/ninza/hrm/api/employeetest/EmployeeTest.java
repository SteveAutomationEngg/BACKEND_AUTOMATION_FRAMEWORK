package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.ninza.hrm.api.pojoclass.EmployeePOJO;
import com.ninza.hrm.api.pojoclass.ProjectPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmployeeTest {

	@Test
	public void addEmployeeToProject() {                 //Request Chaining in this Test case
		
		        Random random = new Random();
		        int ranNum =random.nextInt(5000);
		        
		        String projectName="Ironman_"+ranNum; //projectName
		        String userName = "user"+ranNum;      //EmployeeName
		        
		        
	//API -1 ===> ADD A PROJECT IN SIDE SERVER	
		        
		        //create an object to POJO class   ~ for json payload 
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
		

	   given()
		.contentType(ContentType.JSON)
		.body(pObj)
	.when()
		 .post("http://49.249.28.218:8091/addProject")
	.then()
		.log().all();
		
		
//API -2 ==>  ADD EMPLOYEE TO SAME PROJECT

		EmployeePOJO empObj = new EmployeePOJO("Architect", "20/11/2024", "mohitbaba@gmail.com",userName ,12, "1234567890", projectName,"ROLE_EMPLOYEE",userName);

		given()
		.contentType(ContentType.JSON)
		.body(empObj)
		.when()
		.post("http://49.249.28.218:8091/employees")
		.then()
		.assertThat().contentType(ContentType.JSON)
		.assertThat().statusCode(201)
		.and()
		.time(Matchers.lessThan(3000L))
		.log().all();
		
	//verify Emp Name in DB Layer
		
		       //Verify the EmployeeName in DB layer
              // [we don't have access to DB so DB validation is not possible]

//Boolean flag = false;
//Driver driverRef = new Driver();
//DriverManager.registerDriver(driverRef);
//Connection con = DriverManager.getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm","root@%","root");
//ResultSet result =con.createStatement().executeQuery("select * from employee");  //employee module have separate table in database
//                                                                                //employee is in 5th column of the table
//while(result.next()) {
//if (result.getString(5).equals(userName)) {
//flag = true;
//break;
//}
//}
//
//con.close();
//
//Assert.assertTrue(flag, "Employee in DB is not Verified");

	}	

	
	
	
	@Test
	public void addEmployeWithOutEmailTest() {
		  Random random = new Random();
	        int ranNum =random.nextInt(5000);
	        
	        String projectName="Ironman_"+ranNum; //projectName
	        String userName = "user"+ranNum;      //EmployeeName
	        
	        
//API -1 ===> ADD A PROJECT IN SIDE SERVER	
	        
	        //create an object to POJO class   ~ for json payload 
	ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Mohit", 0);
	

 given()
	.contentType(ContentType.JSON)
	.body(pObj)
.when()
	 .post("http://49.249.28.218:8091/addProject")
.then()
	.log().all();
	
	
//API -2 ==>  ADD EMPLOYEE TO SAME PROJECT

	EmployeePOJO empObj = new EmployeePOJO("Architect", "20/11/2024", "",userName ,12, "1234567890", projectName,"ROLE_EMPLOYEE",userName);
                                            //in empObj email :""
	given()
	.contentType(ContentType.JSON)
	.body(empObj)
	.when()
	.post("http://49.249.28.218:8091/employees")
	.then()
	.assertThat().contentType(ContentType.JSON)
	.assertThat().statusCode(500)
	.and()
	.time(Matchers.lessThan(300L))
	.log().all();
			
	}
}


