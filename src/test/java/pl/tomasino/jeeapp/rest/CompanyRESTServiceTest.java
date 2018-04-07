package pl.tomasino.jeeapp.rest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import io.restassured.RestAssured;
import pl.tomasino.jeeapp.MyTestWatcher;

import static org.hamcrest.Matchers.*;

public class CompanyRESTServiceTest {

	@Rule
	public TestWatcher watchman = new MyTestWatcher();

	@Before
	public void setUp() {
		RestAssured.baseURI = "http://192.168.233.175:8080/";
		RestAssured.basePath = "/jeeapp/rest";
	}

	@Test
	public void companiesRESTServiceReturnsJSONWithValue() {
		RestAssured.given().when().get("/companies").then().body("results", hasSize(greaterThan(0)));
	}

}
