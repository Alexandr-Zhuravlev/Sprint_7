package apiTests;

import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.clients.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class OrderTest {


    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private OrderClient orderClient;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderClient = new OrderClient();
    }

    @Test
    public void getOrder(){

        Response response = orderClient.get();

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_OK, response.statusCode()),
                SC_OK, response.statusCode());
        Assert.assertFalse("Тело ответа отсутствует",
                JsonParser.parseString(response.body().asString()).getAsJsonObject().get("orders").getAsJsonArray().isEmpty());
    }
}
