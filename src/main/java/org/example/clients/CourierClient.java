package org.example.clients;

import io.qameta.allure.Step;
import org.example.models.Courier;
import org.example.models.CourierCreds;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String API_V1_COURIER = "api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "api/v1/courier/login";

    @Step("Создание учетной записи курьера")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    @Step("Авторизация курьера с логином и паролем")
    public Response login(CourierCreds courierCreds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreds)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    @Step("Удаление учетной записи курьера")
    public Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(API_V1_COURIER + "/" + id);
    }
}