package model;
import java.util.ArrayList;
public class IngredientRequest {

    private String authorization;
    private ArrayList<String> ingredients;

    public IngredientRequest(String authorization, ArrayList<String> ingredients) {
        this.authorization = authorization;
        this.ingredients = ingredients;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
