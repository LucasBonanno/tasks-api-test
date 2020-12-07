package br.cls.com.tasks.apitest;

import static io.restassured.RestAssured.given;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		given()
			.body("{\"task\": \"Teste Via API\",\"dueDate\": \"2021-01-01\"}")
			.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		given()
			.body("{\"task\": \"Teste Via API\",\"dueDate\": \"2010-01-01\"}")
			.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}
