package uk.co.taniakolesnik.adn_bakingapp;

import java.util.List;

/**
 * Created by tetianakolesnik on 23/08/2018.
 */

public class Recipe{

    private int id;
    private String name;
    private List<RecipeIngredient> ingredients;
    private List<RecipeStep> steps;


    private Recipe(int id, String name, List<RecipeIngredient> recipeIngredientsList, List<RecipeStep> recipeStepsList) {
        this.id = id;
        this.name = name;
        ingredients = recipeIngredientsList;
        steps = recipeStepsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<RecipeIngredient> getRecipeIngredientsList() {
        return ingredients;
    }

    public void setRecipeIngredientsList(List<RecipeIngredient> recipeIngredientsList) {
        ingredients = recipeIngredientsList;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
