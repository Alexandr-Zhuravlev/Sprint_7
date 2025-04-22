package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String API_V1_ORDERS = "/api/v1/orders";

    @Step("Создание заказа")
    public Response create(Order body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(API_V1_ORDERS);
    }

    @Step("Получение списка заказов")
    public Response get() {
        return given()
                .header("Content-type", "application/json")
                .get(API_V1_ORDERS);
    }
}
