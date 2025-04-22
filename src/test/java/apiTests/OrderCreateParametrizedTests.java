package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.clients.OrderClient;
import org.example.models.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.example.generators.OrderGenerator.generateRandomOrder;


@RunWith(Parameterized.class)
public class OrderCreateParametrizedTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private OrderClient orderClient;

    @Parameterized.Parameter
    public String color;


    @Parameterized.Parameters
    public static Object[] color() {
        return new Object[] {
                "BLACK",
                "GREY",
                "BLACK, GREY",
                ""
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        orderClient = new OrderClient();
    }

    @Test
    public void createOrder() {

        final String expectedField = "track";
        Order body = generateRandomOrder(color.split(","));

        Response response = orderClient.create(body);

        assertEquals(String.format("Ожидается код ответа - %s, фактически - %s", SC_CREATED, response.statusCode()),
                SC_CREATED, response.statusCode());
        Assert.assertTrue("TRACK не найден", response.body().asString().contains(expectedField));
    }
}
