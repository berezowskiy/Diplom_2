import model.User;
import model.IngredientRequest;
import io.qameta.allure.Step;
import client.PostApiClient;
import io.restassured.response.Response;
import java.util.ArrayList;

public class PostSteps {

    private final PostApiClient postApiClient = new PostApiClient();
    @Step("Send POST request to api/auth/register")
    public Response createUser(User user) {
        return postApiClient.createUserResponse(user);
    }

    @Step("Send POST request to api/auth/login")
    public Response loginUser(User user) {
        return postApiClient.loginUser(user);
    }
    @Step("Send POST request to api/auth/login with access token")
    public Response loginUserWithAccessToken(User user, String accessToken) {
        return postApiClient.loginUserWithAccessToken(user, accessToken);
    }
    @Step("Send POST request to api/order")
    public Response createOrder(IngredientRequest ingredientRequest) {
        return postApiClient.createOrder(ingredientRequest);
    }

    @Step("Send POST request to api/order")
    public Response createOrder(String accessToken, ArrayList<String> ingredients) {
        IngredientRequest ingredientRequest = new IngredientRequest(accessToken, ingredients);

        return postApiClient.createOrder(ingredientRequest);
    }
    @Step("Send POST request to /api/order and get status code")
    public Integer getOrderCreationStatusCode(IngredientRequest ingredientRequest) {
        return postApiClient.createOrder(ingredientRequest).statusCode();
    }

}
