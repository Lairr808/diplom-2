import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class OrderReceivingTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp(){
        if (accessToken == null) return;
        userClient.delete(accessToken);
    }

    @Test
    public void orderReceivingIfUserAuthorized() {// получение заказа авторизованным пользователем
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = loginResponse.extract().path("accessToken");//тащит токен
        ValidatableResponse orderReceivingResponse = orderClient.receivingOrder(accessToken);//получить заказа
        int statusCode = orderReceivingResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void orderReceivingIfUserUnauthorized() {// получение заказа неавторизованным пользователем
        ValidatableResponse orderReceivingResponse = orderClient.receivingOrderWithoutAToken();//получить заказа
        int statusCode = orderReceivingResponse.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
    }
}
