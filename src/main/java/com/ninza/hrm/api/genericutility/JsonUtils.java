package com.ninza.hrm.api.genericutility;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;


/**
 * @author MOHIT KUMAR
 * 
 * this is RestAssured utility
 */

public class JsonUtils {
	
	FileUtility fLib = new FileUtility();
	
	/**
	 * get the Jsondata from Json Body based on json complex xpath
	 * @param resp
	 * @param Xpath
	 * 
	 */
	public String getDataOnJsonPath(Response resp, String jsonXpath) throws Throwable{
	        List<Object>  list = JsonPath.read(resp.asString(), jsonXpath);
	        
	        return list.get(0).toString();	
	}
	
	
	/**
	 * get the xmldata from based on xml complex xpath
	 * @param resp
	 * @param xmlXpath
	 */
	public String getDataOnXpathPath(Response resp, String xmlXpath) {
		return  resp.xmlPath().get(xmlXpath);	
	}
	
	
	
	/**
	 * 
	 * @param resp
	 * @param jsonXpath
	 * @param expectedData
	 * =======> IMPORTANT METHOD <===========
	 */
	public boolean VerifyDataOnJsonPath(Response resp, String jsonXpath, String expectedData) {
		List<String> list = JsonPath.read(resp.asString(), jsonXpath);
		   boolean flag = false;
		   
		   for(String str : list) {
			   if(str.equals(expectedData)) {
				   System.out.println(expectedData + "is available == PASS");
				   flag =true;
			   }
		   }
		   if(flag == false) {
			   System.out.println(expectedData + "is available == PASS");
		   }
		   
		   return flag;
	}

	
      public String getAccessToken() throws Throwable {
    	  
    	Response resp = given()
    	 .formParam("client_id",fLib.getDataFromPropertiesFile("client_id"))
    	 .formParam("client_secret",fLib.getDataFromPropertiesFile("client_secret"))
    	 .formParam("grant_type", "client_credentials")
    	.when()
    	 .post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
    	 
    	resp.then().log().all();
    	
    	//capture data from the response
    	String token = resp.jsonPath().get("access_token");
    	
    	return token;
    	  
      }
}
