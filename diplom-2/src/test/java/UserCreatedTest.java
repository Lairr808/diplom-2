import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class UserCreatedTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserGenerator.getDefault();
    }

    @After
    public void cleanUp(){
        if (accessToken == null) return;
        userClient.delete(accessToken);
    }

    @Test
    public void userCanBeCreated(){ //создание пользователя
        ValidatableResponse response = userClient.create(user);//создай
        accessToken = response.extract().path("accessToken");//тащит токен
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void userCanBeCreatedDouble(){ //создание пользователя, который уже существует
        ValidatableResponse response = userClient.create(user);
        ValidatableResponse responseDouble = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        int statusCode = responseDouble.extract().statusCode();
        assertEquals(SC_FORBIDDEN, statusCode);
    }

    @Test
    public void userWithoutPasswordError(){ //нельзя создать без какого то поля
        ValidatableResponse response = userClient.create(UserGenerator.getPasswordNull());
        accessToken = response.extract().path("accessToken");//тащит токен
        int statusCode = response.extract().statusCode();
        assertEquals(SC_FORBIDDEN, statusCode);
    }

}
