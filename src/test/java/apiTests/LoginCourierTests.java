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

public class LoginCourierTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courierClient = new CourierClient();
    }

    @Test
    public void loginCourier(){

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
    public void loginCourierNotFound(){

        final String expectedBody = "{\"code\":404,\"message\":\"Учетная запись не найдена\"}";

        Response response = courierClient.login(credsFromCourier(randomCourier()));

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_NOT_FOUND, response.statusCode()),
                SC_NOT_FOUND, response.statusCode());
        assertEquals(String.format("Ожидается тело ответа - %s, фактически - %s", expectedBody, response.body().asString()),
                expectedBody, response.body().asString());
    }

    @Test
    public void loginCourierWithNullRequiredField(){

        final String expectedBody = "{\"code\":400,\"message\":\"Недостаточно данных для входа\"}";
        Courier courier = randomCourier();
        courier.setLogin(null);

        Response response = courierClient.login(credsFromCourier(courier));

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
