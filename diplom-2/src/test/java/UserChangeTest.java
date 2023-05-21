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

public class UserChangeTest {
    private User user;
    private User userChange;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        userChange = UserGenerator.getNewData();
    }

    @After
    public void cleanUp(){
        if (accessToken == null) return;
        userClient.delete(accessToken);
    }

    @Test
    public void userAuthorizedCanChange(){ //изменение данных авторизованного пользователя
        userClient.create(user);//создай
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));//авторизация
        accessToken = loginResponse.extract().path("accessToken");//тащит токен
        ValidatableResponse changeResponse = userClient.change(userChange, accessToken);//изменения
        int statusCode = changeResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    public void userUnauthorizedCanNotChangeError(){ //невозможность изменить данные неавторизованного пользователя
        ValidatableResponse changeResponse = userClient.changeWithoutAToken(userChange);//изменения
        int statusCode = changeResponse.extract().statusCode();
        assertEquals(SC_UNAUTHORIZED, statusCode);
    }
}
