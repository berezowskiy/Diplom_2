package client;
import model.User;
import io.restassured.response.Response;

public class DeleteApiClient extends BaseHttpClient{

    public Response deleteUser(String accessToken) {
        return doDeleteRequestWithAccessToken(super.baseUrl + "/api/auth/user",accessToken);
    }
}
