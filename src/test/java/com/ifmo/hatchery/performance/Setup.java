package com.ifmo.hatchery.performance;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.init.DataInit.CUSTOMER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DONOR_DEFAULT_USERNAME;
import static io.restassured.RestAssured.given;

public class Setup {

    @Test
    public static void setUp() {
        DonorTest.COOKIES = given()
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

        OrderTest.COOKIES = given()
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
    }
}
