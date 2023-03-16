package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPI {
  
	@Test(priority=1)
	public void test_AllVideoGames()
	{
	   given()
	   .when()
		 .get("http://localhost:8080/app/videogames")
		 
	   .then()
		 .statusCode(200);
	}
	@Test(priority=2)
	public void test_NewVideoGame()
	{
		HashMap<String,String> data=new HashMap<String,String>();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2023-03-16T11:28:47.698Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
	Response res=
		  given()
		     .contentType("application/json")
		     .body(data)
		  
		  .when()
		     .post("http://localhost:8080/app/videogames")
		    
		  .then()
		     .statusCode(200)
		     .log().body()
		     .extract().response();
	
		 String jsonString=res.asString();
		 Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);	    
	  }
	
	 @Test(priority=3)
	 public void test_getVideoGame()
	 {
		given()
		.when()
		  .get("http://localhost:8080/app/videogames/100")
		.then()
		   .statusCode(200)
		   .log().body()
		   .body("videoGame.id", equalTo("100"))
		   .body("videoGame.name", equalTo("Spider-Man"));
	 }
	 
	 @Test(priority=4)
	 public void test_UpdateVideoGame()
	 {
			HashMap<String,String> data=new HashMap<String,String>();
			data.put("id", "100");
			data.put("name", "PacMan");
			data.put("releaseDate", "2023-03-16T11:28:47.698Z");
			data.put("reviewScore", "4");
			data.put("category", "Adventure");
			data.put("rating", "Universal");
			
		given()
		  .contentType("application/json")
		  .body(data)
		.when()
		  .put("http://localhost:8080/app/videogames/100")
		.then()
		   .statusCode(200)
		   .log().body()
		   .body("videoGame.id", equalTo("100"))
		   .body("videoGame.name", equalTo("PacMan"));
	 }
	 
	 @Test(priority=5)
	 public void test_DeleteVideoGame() throws InterruptedException
	 {
	 Response res=
		 given()
		 .when()
		   .delete("http://localhost:8080/app/videogames/100")
		 .then()
		     .statusCode(200)
		     .log().body()
		     .extract().response();
	 Thread.sleep(3000);
     String jsonString=res.asString();
	 Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
	 }

}
