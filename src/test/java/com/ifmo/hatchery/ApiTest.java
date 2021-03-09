package com.ifmo.hatchery;

import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;

public class ApiTest {
    private static final String API_URL = "http://localhost:8080";

    @Test
    public void loginTest() {
        given()
                .baseUri(API_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "usernameDonor")
                .formParam("password", "password")
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/home", API_URL))
                .when()
                .post("/login");
    }

    @Test
    public void loginErrorTest() {
        given()
                .baseUri(API_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "wrongUsername")
                .formParam("password", "wrongPassword")
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/login?error", API_URL))
                .when()
                .post("/login");
    }
}
