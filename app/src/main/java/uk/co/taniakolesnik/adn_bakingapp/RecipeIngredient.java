package uk.co.taniakolesnik.adn_bakingapp;

/**
 * Created by tetianakolesnik on 23/08/2018.
 */

public class RecipeIngredient {

    private int mQuantity;
    private String mMeasure;
    private int mName;

    private RecipeIngredient(int ingredient_quantity, String ingredient_measure, int ingredient_name) {
        this.mQuantity = ingredient_quantity;
        this.mMeasure = ingredient_measure;
        this.mName = ingredient_name;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public int getName() {
        return mName;
    }

    public void setName(int name) {
        this.mName = name;
    }

}
