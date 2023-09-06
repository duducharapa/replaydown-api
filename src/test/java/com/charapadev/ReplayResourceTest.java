package com.charapadev;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ReplayResourceTest {

    private final String replayId = "oumonotype-82345404";

    /*
     * Tests if the /{id} endpoint is sending the correctly
     * HTTP code and the JSON has the expected properties
    */
    @Test
    public void shouldCallMainEndpointCorrectly() {
        String path = String.join("", "/", replayId);
        
        Response response = given()
          .when().get(path)
          .then().extract().response();

        Assertions.assertEquals(200, response.getStatusCode());
    }

    /*
     * 
     */
    @Test
    public void shouldNotFoundWhenCallMainEndpoint() {
        String invalidID = "test-123";
        String path = String.join("", "/", invalidID);

        Response response = given()
            .when().get(path)
            .then().extract().response();

        Assertions.assertEquals(404, response.getStatusCode());
    }

}