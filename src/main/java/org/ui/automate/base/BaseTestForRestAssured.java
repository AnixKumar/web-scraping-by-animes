package org.ui.automate.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTestForRestAssured {
    private Properties config;
    private final HashMap<String, Object> headers = new HashMap<>();

    public void setupApi() throws IOException {
        config = new Properties();
        config.load(new FileInputStream("src/main/resources/config.properties"));

        RestAssured.baseURI = config.getProperty("rapidapi.baseuri");
        headers.put("Content-Type", "application/json");
        headers.put("x-rapidapi-host", config.getProperty("rapidapi.host"));
        headers.put("x-rapidapi-key", config.getProperty("rapidapi.key"));
    }

    public String callApiForTranslation(String body){
        return RestAssured.given()
                .headers(headers)
                .body(Map.of(
                        "from", "es",
                        "to", "en",
                        "text", body
                ))
                .when()
                .post("/text")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .get("trans");
    }
}
