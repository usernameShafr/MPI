package com.ifmo.hatchery.api;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.Map;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.init.DataInit.CUSTOMER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DISPATCHER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DONOR_DEFAULT_USERNAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AccessTests {
    @Test
    public void donorNotAccessToDashboard() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", DONOR_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/dashboard");
    }

    @Test
    public void donorNotAccessToOrders() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", DONOR_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/order");
    }

    @Test
    public void customerNotAccessToDashboard() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", CUSTOMER_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/dashboard");
    }

    @Test
    public void customerNotAccessToDonor() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", CUSTOMER_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/donor");
    }

    @Test
    public void dispatcherNotAccessToDonor() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", DISPATCHER_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/donor");
    }

    @Test
    public void dispatcherNotAccessToOrder() {
        Map<String, String> COOKIES = given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", DISPATCHER_DEFAULT_USERNAME)
                .formParam("password", DEFAULT_PASSWORD)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", BASE_URL))
                .when()
                .post("/login")
                .cookies();
        given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .when()
                .get("/order");
    }
}
