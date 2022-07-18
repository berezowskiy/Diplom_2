package client;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class BaseHttpClient {
    private final String JSON = "application/json";
    protected final String baseUrl = "https://stellarburgers.nomoreparties.site";

    protected Response doPostRequest(String uri, Object body) {
        return given().header("Content-type", JSON).body(body).post(uri);
    }

    protected Response doPostRequestWithAccessToken(String uri, String accessToken, Object body) {
        return given().headers("Authorization", "Bearer " + accessToken,"Content-type", JSON).body(body).post(uri);
    }

    protected Response doGetRequest(String uri) {
        return given().header("Content-type", JSON).get(uri);
    }

    protected Response doGetRequestWithAccessToken(String uri, String accessToken) {
        return given().headers("Authorization", accessToken,"Content-type", JSON).when().get(uri);
    }

    protected Response doPatchRequest(String uri, Object body) {
        return given().header("Content-type", JSON).body(body).when().patch(uri);
    }

    protected Response doPatchRequestWithAccessToken(String uri, String accessToken, Object body) {
        return given().headers("Authorization", accessToken,"Content-type", JSON).body(body).when().patch(uri);
    }

    protected Response doDeleteRequestWithAccessToken(String uri, String accessToken) {
        return given().headers("Authorization", accessToken,"Content-type", JSON).when().delete(uri);
    }

}
