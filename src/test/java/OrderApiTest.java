import client.OrderClient;
import client.UserClient;
import model.Ingredient;
import model.IngredientRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

public class OrderApiTest {

    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    static Faker faker = new Faker();
    static String userEmail = faker.internet().emailAddress();
    static String userPassword = faker.internet().password();
    static String userName = faker.name().username();

    @Test
    @DisplayName("Check status code of api/ingredients")
    @Description("Basic test for api/ingredients")
    public void testGetIngredients() throws JsonProcessingException {

        Ingredient ingredient = orderClient.getIngredients();
        assertTrue(ingredient.getData().size() > 1);

    }

    @Test
    @DisplayName("Check status code of api/orders without access token")
    @Description("Test for api/orders without access token")
    public void testGetOrders() throws JsonProcessingException {

        orderClient.getUserOrders()
                .then()
                .assertThat().statusCode(401);

    }

    @Test
    @DisplayName("Check status code of api/orders")
    @Description("Basic test for api/orders")
    public void testGetOrdersWithAccessToken() throws JsonProcessingException {
        User user = new User(userEmail, userPassword, userName);
        String accessToken = userClient.createUser(user).then().extract().path("accessToken");

        Ingredient ingredient = orderClient.getIngredients();
        ArrayList<BurgerIngredient> burgerIngredientList = ingredient.getData();
        ArrayList<String> ingredientList = new ArrayList<String>();

        //loop to add random ingredients to ingredientList
        for (int i = 0; i < 3; i++)
        {
            // generating the index using Math.random()
            int index = (int)(Math.random() * burgerIngredientList.size());

            ingredientList.add(burgerIngredientList.get(index).get_id());
        }

        IngredientRequest ingredientRequest = new IngredientRequest(accessToken, ingredientList);

        orderClient.createOrder(ingredientRequest);
        orderClient.getUserOrdersWithAccessToken(accessToken)
                .then()
                .assertThat().statusCode(200);

        userClient.deleteUser(accessToken);

    }
}
