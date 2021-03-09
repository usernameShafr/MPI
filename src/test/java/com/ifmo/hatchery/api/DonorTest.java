package com.ifmo.hatchery.api;

import com.ifmo.hatchery.model.system.BiomaterialType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.Map;

import static com.ifmo.hatchery.CommonConfiguration.BASE_URL;
import static com.ifmo.hatchery.init.DataInit.DEFAULT_PASSWORD;
import static com.ifmo.hatchery.init.DataInit.DONOR_DEFAULT_USERNAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DonorTest {
    private static Map<String, String> COOKIES;

    @BeforeAll
    public static void setUp() {
        COOKIES = given()
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
    }

    @Test
    public void sendBioMaterial() {
        given()
                .baseUri(baseURI)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("biomaterial", BiomaterialType.MALE)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_MOVED_TEMP)
                .header("Location", String.format("%s/donor", BASE_URL))
                .when()
                .post("/donor/giveBio");
    }

    @Test
    public void getDonorLK() {
        String body = given()
                .baseUri(baseURI)
                .cookies(COOKIES)
                .expect()
                .statusCode(HttpURLConnection.HTTP_OK)
                .when()
                .get("/donor")
                .getBody()
                .prettyPrint();
        assertNotNull(body);
        assertTrue(body.contains("Donor LK"));
    }
}
