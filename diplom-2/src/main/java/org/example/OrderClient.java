package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.Client.getSpec;

public class OrderClient {
    private static final String PATH = "/api/orders";

    public ValidatableResponse createOrder(Order order){
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then().log().all();
    }

    public ValidatableResponse receivingOrder(String accessToken){
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(PATH)
                .then().log().all();
    }

    public ValidatableResponse receivingOrderWithoutAToken(){
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then().log().all();
    }
}
