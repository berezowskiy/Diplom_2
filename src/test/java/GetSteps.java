import client.GetApiClient;
import model.Ingredient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GetSteps {

    private final GetApiClient getApiClient = new GetApiClient();
    @Step("Send GET request to api/ingredients")
    public Ingredient getIngredients() {
        return getApiClient.getIngredients();
    }
    @Step("Send GET request to api/orders with access token")
    public Response getUserOrdersWithAccessToken(String accessToken) {
        return getApiClient.getUserOrdersWithAccessToken(accessToken);
    }
    @Step("Send GET request to api/orders")
    public Response getUserOrders() {
        return getApiClient.getUserOrders();
    }

}
