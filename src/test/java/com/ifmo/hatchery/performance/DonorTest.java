package com.ifmo.hatchery.performance;

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
    public static Map<String, String> COOKIES;

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
