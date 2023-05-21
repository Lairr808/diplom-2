package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.example.Client.getSpec;

public class UserClient {
    private static final String PATH = "/api/auth/register";
    private static final String INF_PATH = "/api/auth/user";
    private static final String LOGIN_PATH = "/api/auth/login";

    public ValidatableResponse create(User user){//создать
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH)
                .then().log().all();
    }

    public ValidatableResponse delete(String accessToken) {//удалить
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body("")
                .when()
                .delete(INF_PATH)
                .then().log().all();
    }

    public ValidatableResponse login(UserCredentials credentials) {//войти
        return given().log().all()
                .spec(getSpec())
                .body(credentials) //тело запроса
                .when()
                .post(LOGIN_PATH)
                .then().log().all();
    }

    public ValidatableResponse change(User user, String accessToken) {//изменить
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(INF_PATH)
                .then().log().all();
    }

    public ValidatableResponse changeWithoutAToken(User user) {//изменить
        return given().log().all()
                .spec(getSpec())
                .body(user)
                .when()
                .patch(INF_PATH)
                .then().log().all();
    }
}
