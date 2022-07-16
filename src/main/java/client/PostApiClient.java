package client;
import model.User;
import model.UserWithTokens;
import model.IngredientRequest;
import io.restassured.response.Response;
public class PostApiClient extends BaseHttpClient {

    public UserWithTokens createUser(User user) {
        return doPostRequest(super.baseUrl + "/api/auth/register", user).body().as(UserWithTokens.class);
    }
    public Response createUserResponse(User user) {
        return doPostRequest(super.baseUrl + "/api/auth/register", user);
    }
    public Response loginUser(User user) {
        return doPostRequest(super.baseUrl + "/api/auth/login", user);
    }
    public Response loginUserWithAccessToken(User user, String accessToken) {
        return doPostRequestWithAccessToken(super.baseUrl + "/api/auth/login", accessToken, user);
    }
    public Response createOrder(IngredientRequest ingredientRequest) {
        return doPostRequest(super.baseUrl + "/api/orders", ingredientRequest);
    }
}
