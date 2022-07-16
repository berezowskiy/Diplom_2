import client.PatchApiClient;
import model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PatchSteps {

    private final PatchApiClient patchApiClient = new PatchApiClient();
    @Step("Send PATCH request to api/auth/user")
    public Response editUser(User user) {
        return patchApiClient.editUser(user);
    }
    @Step("Send PATCH request to api/auth/user with access token")
    public Response editUserWithAccessToken(String accessToken, User user) {
        return patchApiClient.editUserWithAccessToken(accessToken, user);
    }

}
