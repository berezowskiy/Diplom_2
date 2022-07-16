package client;

import io.restassured.response.Response;
import model.Ingredient;

public class GetApiClient extends BaseHttpClient {

    public Ingredient getIngredients( ) {
        return doGetRequest(super.baseUrl + "/api/ingredients").body().as(Ingredient.class);
    }
    public Response getUserOrdersWithAccessToken(String accessToken) {
        return doGetRequestWithAccessToken(super.baseUrl + "/api/orders", accessToken);
    }
    public Response getUserOrders() {
        return doGetRequest(super.baseUrl + "/api/orders");
    }

}
