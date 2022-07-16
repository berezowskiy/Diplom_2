import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PostApiTest    {

    private final PostSteps postSteps = new PostSteps();
    private final DeleteSteps deleteSteps = new DeleteSteps();
    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String userName = faker.name().username();


    @Test
    @DisplayName("Check status code of api/auth/register")
    @Description("Basic test for api/auth/register endpoint")
    public void testUserCreation() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        postSteps.createUser(user)
                .then().assertThat()
                .statusCode(200);

        String accessToken = postSteps.loginUser(user).then().extract().path("accessToken");
        deleteSteps.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/register with same login")
    @Description("Test for api/auth/register endpoint with same login")
    public void testUserCreationWithSameLogin() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        String accessToken = postSteps.createUser(user).then().extract().path("accessToken");;
        postSteps.createUser(user)
                .then().assertThat()
                .statusCode(403);
        deleteSteps.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/register without name")
    @Description("Test for api/auth/register endpoint without name")
    public void testUserCreationWithoutName() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, null);

        postSteps.createUser(user)
                .then().assertThat()
                .statusCode(403);

    }


    @Test
    @DisplayName("Check status code of api/auth/auth")
    @Description("Basic test for api/auth/login")
    public void testLoginWithAuthorization() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        String accessToken = postSteps.createUser(user).then().extract().path("accessToken");

        postSteps.loginUserWithAccessToken(user, accessToken)
                .then().assertThat().statusCode(200);

        deleteSteps.deleteUser(accessToken);

    }

    @Test
    @DisplayName("Check status code of api/auth/auth")
    @Description("Test for api/auth/login with wrong email and password")
    public void testLoginWithWrongEmailAndPassword() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);

        String accessToken = postSteps.createUser(user).then().extract().path("accessToken");
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.name().username());

        postSteps.loginUserWithAccessToken(user, accessToken)
                .then().assertThat().statusCode(401);

        deleteSteps.deleteUser(accessToken);

    }
}
