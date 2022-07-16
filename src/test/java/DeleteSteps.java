import client.DeleteApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DeleteSteps {

    private final DeleteApiClient deleteApiClient = new DeleteApiClient();

    @Step("Send DELETE request to api/auth/user with access token")
    public Response deleteUser(String accessToken) { return deleteApiClient.deleteUser(accessToken); }
}

