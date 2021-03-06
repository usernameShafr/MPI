package com.ifmo.hatchery.api;

import com.ifmo.hatchery.model.system.Caste;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.Map;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.init.DataInit.CUSTOMER_DEFAULT_USERNAME;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private static Map<String, String> COOKIES;

    @BeforeAll
    public static void setUp() {
        COOKIES = given()
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


    @Test
    public void createOrder() {
        given()
                .baseUri(baseURI)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("caste", Caste.ALPHA)
                .formParam("skills", 1)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/order", BASE_URL))
                .when()
                .post("/order/createOrder");
    }

    @Test
    public void getOrders() {
         String body = given()
                 .baseUri(baseURI)
                 .cookies(COOKIES)
                 .expect()
                 .statusCode(HttpURLConnection.HTTP_OK)
                 .when()
                 .get("/order")
                 .getBody()
                 .prettyPrint();
         assertNotNull(body);
         assertTrue(body.contains("My orders"));
    }
}
