import client.UserClient;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserApiTest {

    UserClient userClient = new UserClient();
    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String userName = faker.name().username();


    @Test
    @DisplayName("Check status code of api/auth/register")
    @Description("Basic test for api/auth/register endpoint")
    public void testUserCreation() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        userClient.createUser(user)
                .then().assertThat()
                .statusCode(200);

        String accessToken = userClient.loginUser(user).then().extract().path("accessToken");
        userClient.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/register with same login")
    @Description("Test for api/auth/register endpoint with same login")
    public void testUserCreationWithSameLogin() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        String accessToken = userClient.createUser(user).then().extract().path("accessToken");;
        userClient.createUser(user)
                .then().assertThat()
                .statusCode(403);
        userClient.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/register without name")
    @Description("Test for api/auth/register endpoint without name")
    public void testUserCreationWithoutName() throws JsonProcessingException {

        User user = new User(userEmail, userPassword, null);
        Response response = userClient.createUser(user);
        boolean success = response.then().extract().path("success");

        if(success) {
            // need to delete user if test failed and user created
            userClient.deleteUser(response.then().extract().path("accessToken"));
        } else {
            response.then().assertThat().statusCode(403);
        }

    }

    @Test
    @DisplayName("Check status code of api/auth/auth")
    @Description("Basic test for api/auth/login")
    public void testLoginWithAuthorization() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        String accessToken = userClient.createUser(user).then().extract().path("accessToken");

        userClient.loginUserWithAccessToken(user, accessToken)
                .then().assertThat().statusCode(200);

        userClient.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/auth")
    @Description("Test for api/auth/login with wrong email and password")
    public void testLoginWithWrongEmailAndPassword() throws JsonProcessingException {

        User user = new User(userEmail, userPassword, userName);
        String accessToken = userClient.createUser(user).then().extract().path("accessToken");

        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.name().username());

        userClient.loginUserWithAccessToken(user, accessToken).then().assertThat().statusCode(401);
        userClient.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/user without access token")
    @Description("Test for api/auth/user endpoint without access token")
    public void testUserEditing() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);
        userClient.editUser(user)
                .then().assertThat()
                .statusCode(401);

    }

    @Test
    @DisplayName("Check status code of api/auth/user with access token")
    @Description("Test for api/auth/user endpoint with access token")
    public void testUserEditionWithAccessToken() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);
        String accessToken = userClient.createUser(user).then().extract().path("accessToken");

        user.setName(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        userClient.editUserWithAccessToken(accessToken, user)
                .then().assertThat()
                .statusCode(200);

        userClient.deleteUser(accessToken);
    }
}
