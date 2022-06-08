package com.example.EagleActivities;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class EagleActivitiesApplicationTests {
	private String baseUrl = "/api/activities";

	@Test
	void allEntPointTest() {
		when().
				get(baseUrl).
		then().
				statusCode(200);
	}

	@Test
	void storeNewActivityEndPointTest(){
		JSONObject activityJsonObject = null;
		try {
			activityJsonObject = new JSONObject()
					.put("type","Sondage")
					.put("description", "Activite de test")
					.put("startDate","2022-05-24T05:06:18")
					.put("endDate", "2022-05-24T05:06:18");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		given().
				contentType("application/json").
				body(activityJsonObject.toString()).
		when().
				post(baseUrl).
		then().
				statusCode(201);
	}

	@Test
	void findByIdEndPointTest() {
		given().
				contentType("application/json").
		when().
				get(baseUrl+"/"+1).
		then().
				statusCode(200).
				body("type",is("Operation sql")).
				body("description", is("Activite pour la methode find")).
				body("startDate",is("2022-09-05T05:06:00")).
				body("endDate", is("2022-10-05T05:06:00"));
	}

	@Test
	void replaceActivityEntPointTest() {

		JSONObject activityJsonObject = null;
		try {
			activityJsonObject = new JSONObject()
					.put("type","Sondage")
					.put("description", "Test de satisfactifaction des activites du groupe")
					.put("startDate","2022-05-24T05:06:00")
					.put("endDate", "2022-06-25T05:06:00");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		given().
				contentType("application/json").
				body(activityJsonObject.toString()).
		when().
				put(baseUrl+"/"+3).
	then().
				statusCode(201).
				body("type",is("Sondage")).
				body("description", is("Test de satisfactifaction des activites du groupe")).
				body("startDate",is("2022-05-24T05:06:00")).
				body("endDate", is("2022-06-25T05:06:00"));
	}

	@Test
	void deleteActivityEntPointTest() {

		given().
				contentType("application/json").
		when().
				delete(baseUrl+"/"+3).
		then().
				statusCode(204);
	}
}
