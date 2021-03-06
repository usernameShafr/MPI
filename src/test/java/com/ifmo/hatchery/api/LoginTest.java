package com.ifmo.hatchery.api;

import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.init.DataInit.CUSTOMER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static io.restassured.RestAssured.given;

public class LoginTest {
    @Test
    public void loginTest() {
        Map<String, String> cookies = given()
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
        Assertions.assertNotNull(cookies);
        Assertions.assertFalse(cookies.isEmpty());
        Assertions.assertNotNull(cookies.get("JSESSIONID"));
    }

    @Test
    public void loginErrorTest() {
        given()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", "wrongUsername")
                .formParam("password", "wrongPassword")
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/login?error", BASE_URL))
                .when()
                .post("/login");
    }
}
