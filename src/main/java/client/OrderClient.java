package client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Ingredient;
import model.IngredientRequest;

public class OrderClient extends BaseHttpClient{
    @Step("Send POST request to api/order")
    public Response createOrder(IngredientRequest ingredientRequest) { return doPostRequest(super.baseUrl + "/api/orders", ingredientRequest); }
    @Step("Send GET request to api/ingredients")
    public Ingredient getIngredients( ) { return doGetRequest(super.baseUrl + "/api/ingredients").body().as(Ingredient.class); }

    @Step("Send GET request to api/orders with access token")
    public Response getUserOrdersWithAccessToken(String accessToken) { return doGetRequestWithAccessToken(super.baseUrl + "/api/orders", accessToken); }

    @Step("Send GET request to api/orders")
    public Response getUserOrders() {
        return doGetRequest(super.baseUrl + "/api/orders");
    }

}
