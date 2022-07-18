import client.OrderClient;
import client.UserClient;
import model.BurgerIngredient;
import model.Ingredient;
import model.IngredientRequest;
import model.User;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PostOrderParameterizedTest {

    private static final UserClient userClient = new UserClient();
    private static final OrderClient orderClient = new OrderClient();
    private final IngredientRequest ingredientRequest;
    private final int expected;
    static Faker faker = new Faker();
    static String userEmail = faker.internet().emailAddress();
    static String userPassword = faker.internet().password();
    static String userName = faker.name().username();
    static ArrayList<String> wrongIngredients = new ArrayList<String>(
            Arrays.asList("61c0c", "61c0c", "61c0c5a"));


    public PostOrderParameterizedTest(IngredientRequest ingredientRequest, int expected) {
        this.ingredientRequest = ingredientRequest;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getResponseData() {
        User user = new User(userEmail, userPassword, userName);
        String accessToken = userClient.createUser(user).then().extract().path("accessToken");

        Ingredient ingredient = orderClient.getIngredients();
        ArrayList<BurgerIngredient> burgerIngredientList = ingredient.getData();
        ArrayList<String> ingredientList = new ArrayList<String>();

        // loop to add random ingredients to ingredientList
        for (int i = 0; i < 3; i++)
        {
            // generating the index using Math.random()
            int index = (int)(Math.random() * burgerIngredientList.size());

            ingredientList.add(burgerIngredientList.get(index).get_id());
        }

        return new Object[][] {
                {new IngredientRequest(accessToken, ingredientList), 200},
                {new IngredientRequest(null, ingredientList), 200},
                {new IngredientRequest(null, null), 400},
                {new IngredientRequest(accessToken, wrongIngredients), 500},
        };
    }

    @Test
    @DisplayName("Check status code of /api/order")
    @Description("Parameterized test for /api/order endpoint")
    public void shouldBeOrderCreated() {
        int actual = orderClient.createOrder(ingredientRequest).statusCode();
        assertEquals(expected, actual);
    }

}
