import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrderCreatedTest {

    private User user;
    private Order order;
    private Order orderNullIngredients;
    private Order orderIncorrectIngredients;
    private UserClient userClient;
    private String accessToken;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        orderClient = new OrderClient();
        order = OrderGenerator.getDefaultIngredients();
        orderNullIngredients = OrderGenerator.getNullIngredients();
        orderIncorrectIngredients = OrderGenerator.getHashRandom();
    }

    @After
    public void cleanUp(){
        if (accessToken == null) return;
        userClient.delete(accessToken);
    }

    @Test
    public void orderCreationIfUserAuthorized(){// создание заказа авторизованным пользователем с ингредиентом
        ValidatableResponse response = userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = loginResponse.extract().path("accessToken");//тащит токен
        ValidatableResponse orderCreateResponse = orderClient.createOrder(order);//создание заказа
        int statusCode = orderCreateResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void orderCreationIfUserUnauthorized(){ //создание заказа неавторизованным пользователем с ингредиентом
        ValidatableResponse orderCreateResponse = orderClient.createOrder(order);//создание заказа
        int statusCode = orderCreateResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void orderCreationIfUserAuthorizedWithoutIngredient(){// создание заказа авторизованным пользователем без ингредиента
        ValidatableResponse response = userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = loginResponse.extract().path("accessToken");//тащит токен
        ValidatableResponse orderCreateResponse = orderClient.createOrder(orderNullIngredients);//создание заказа
        int statusCode = orderCreateResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void orderCreationIfUserAuthorizedIncorrectIngredient(){// создание заказа авторизованным пользователем некорректный хеш ингредиента
        ValidatableResponse response = userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = loginResponse.extract().path("accessToken");//тащит токен
        ValidatableResponse orderCreateResponse = orderClient.createOrder(orderIncorrectIngredients);//создание заказа
        int statusCode = orderCreateResponse.extract().statusCode();
        assertEquals(SC_INTERNAL_SERVER_ERROR, statusCode);
    }
}
