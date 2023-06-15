package com.example.demo;

import com.example.demo.model.entity.User;
import com.example.demo.model.moduleHttp.UserOut;
import com.example.demo.repo.UserRepo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserRepo userData;

	@Test
	public void testAuthAll() throws JSONException {
		String firstName = "fpName";
		String secondName = "scName";
		String email = "noEmail@mail.ru";
		String password = "pass";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", firstName);
		jsonObject.put("secondName", secondName);
		jsonObject.put("email", email);
		jsonObject.put("password", password);



		given()
				.contentType(ContentType.JSON)
				.when()
				.post("/auth/registration")
				.then()
				.statusCode(200)
				.extract()
				.as(String.class);

		UserOut out = given()
				.contentType(ContentType.JSON)
				.when()
				.post("/auth/login")
				.then()
				.statusCode(200)
				.extract()
				.as(UserOut.class);


		assertEquals(out.getEmail(),email);
		assertEquals(out.getFirstName(),firstName);
		assertEquals(out.getSecondName(),secondName);

		User user = userData.findByEmail(email);
		userData.delete(user);


	}



}
