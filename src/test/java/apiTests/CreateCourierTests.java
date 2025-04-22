package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.clients.CourierClient;
import org.example.models.Courier;
import org.example.models.CourierLoginResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.example.generators.CourierGenerator.randomCourier;
import static org.example.models.CourierCreds.credsFromCourier;
import static org.junit.Assert.assertEquals;

public class CreateCourierTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courierClient = new CourierClient();
    }

    @Test
    public void createCourier(){

        final String expectedBody = "{\"ok\":true}";
        Courier courier = randomCourier();

        Response response = courierClient.create(courier);

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_CREATED, response.statusCode()),
                SC_CREATED, response.statusCode());

        assertEquals(String.format("Ожидается тело ответа - %s, фактически - %s", expectedBody, response.body().asString()),
                expectedBody, response.body().asString());

        Response loginResponse = courierClient.login(credsFromCourier(courier));

        assertEquals(String.format("Ожидается код ответа при авторизации - %s, фактически - %s", SC_CREATED, loginResponse.statusCode()),
                SC_OK, loginResponse.statusCode());

        id = loginResponse.as(CourierLoginResponse.class).getId();
    }

    @Test
    public void createAnExistingCourier(){

        final String expectedBody = "{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}";
        Courier courier = randomCourier();

        Response response = courierClient.create(courier);

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_CREATED, response.statusCode()),
                SC_CREATED, response.statusCode());

        Response response2 = courierClient.create(courier);

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_CONFLICT, response2.statusCode()),
                SC_CONFLICT, response2.statusCode());
        assertEquals(String.format("Ожидается тело ответа - %s, фактически - %s", expectedBody, response2.body().asString()),
                expectedBody, response2.body().asString());

        Response loginResponse = courierClient.login(credsFromCourier(courier));

        id = loginResponse.as(CourierLoginResponse.class).getId();
    }

    @Test
    public void createCourierWithNullRequiredField(){

        final String expectedBody = "{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}";
        Courier courier = randomCourier();
        courier.setLogin(null);

        Response response = courierClient.create(courier);

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_BAD_REQUEST, response.statusCode()),
                SC_BAD_REQUEST, response.statusCode());
        assertEquals(String.format("Ожидается тело ответа - %s, фактически - %s", expectedBody, response.body().asString()),
                expectedBody, response.body().asString());
    }

    @After
    public void afterTest() {
        courierClient.delete(id);
    }
}
