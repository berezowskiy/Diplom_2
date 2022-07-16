import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PatchApiTest {

    private final PostSteps postSteps = new PostSteps();
    private final PatchSteps patchSteps = new PatchSteps();
    private final DeleteSteps deleteSteps = new DeleteSteps();
    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String userName = faker.name().username();

    @Test
    @DisplayName("Check status code of api/auth/user without access token")
    @Description("Test for api/auth/user endpoint without access token")
    public void testUserEdition() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);
        patchSteps.editUser(user)
                .then().assertThat()
                .statusCode(401);

    }

    @Test
    @DisplayName("Check status code of api/auth/user with access token")
    @Description("Test for api/auth/user endpoint with access token")
    public void testUserEditionWithAccessToken() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);
        String accessToken = postSteps.createUser(user).then().extract().path("accessToken");

        user.setName(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        patchSteps.editUserWithAccessToken(accessToken, user)
                .then().assertThat()
                .statusCode(200);

        deleteSteps.deleteUser(accessToken);
    }
}
