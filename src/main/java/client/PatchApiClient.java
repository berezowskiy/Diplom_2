package client;
import model.User;
import io.restassured.response.Response;

public class PatchApiClient extends BaseHttpClient {

    public Response editUser(User user) {
        return doPatchRequest(super.baseUrl + "/api/auth/user", user);
    }
    public Response editUserWithAccessToken(String accessToken, User user) {
        return doPatchRequestWithAccessToken(super.baseUrl + "/api/auth/user",accessToken, user);
    }
}
