package uk.co.taniakolesnik.adn_bakingapp.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uk.co.taniakolesnik.adn_bakingapp.Recipe;

/**
 * Created by tetianakolesnik on 23/08/2018.
 */

public interface BakingGetListClient {

   // e.g. https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    @GET("/topher/{year}/{month}/{list}/baking.json")
    Call<List<Recipe>> getRecipesList(@Path("year") String year, @Path("month") String month, @Path("list") String list);

}
