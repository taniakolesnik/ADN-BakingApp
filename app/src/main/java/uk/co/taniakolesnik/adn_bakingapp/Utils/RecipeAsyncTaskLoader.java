package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;

/**
 * Created by tetianakolesnik on 27/08/2018.
 */

public class RecipeAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<List<Recipe>> {

    private static final String TAG = RecipeAsyncTaskLoader.class.getSimpleName();
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String URL_YEAR_PATH = "2017";
    private static final String URL_MONTH_PATH = "May";
    private static final String URL_LIST_PATH = "59121517_baking";
    private List<Recipe> recipes;

    public RecipeAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public List<Recipe> loadInBackground() {

            recipes = new ArrayList<>();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            final Retrofit retrofit = builder.build();
            BakingGetListClient client = retrofit.create(BakingGetListClient.class);
            Call<List<Recipe>> call = client.getRecipesList(URL_YEAR_PATH, URL_MONTH_PATH, URL_LIST_PATH);
        try {
            recipes = call.execute().body();
            Log.i(TAG, "loadInBackground execute");
        } catch (IOException e) {
            e.printStackTrace();
        }
            return recipes;
        }

}
