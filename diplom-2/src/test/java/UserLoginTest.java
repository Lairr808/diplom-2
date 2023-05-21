import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class UserLoginTest {
    private User user;
    private User userRandom;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        userRandom = UserGenerator.getEmailAndPasswordRandom();
    }

    @After
    public void cleanUp(){
        if (accessToken == null) return;
        userClient.delete(accessToken);
    }

    @Test
    public void userCanBeLogin(){ //авторизация
        ValidatableResponse response = userClient.create(user);//создай
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = response.extract().path("accessToken");//тащит токен
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void userCanNotLoginError(){ //авторизация некорректные логин и пароль
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(userRandom));//авторизация
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
    }


}
