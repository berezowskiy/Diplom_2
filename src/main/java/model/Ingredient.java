package model;
import java.util.ArrayList;

public class Ingredient {

    private boolean success;
    private ArrayList<BurgerIngredient> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<BurgerIngredient> getData() {
        return data;
    }

    public void setData(ArrayList<BurgerIngredient> data) {
        this.data = data;
    }
}
