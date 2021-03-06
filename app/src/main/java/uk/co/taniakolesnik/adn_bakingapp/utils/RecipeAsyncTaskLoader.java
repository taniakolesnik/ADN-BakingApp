package uk.co.taniakolesnik.adn_bakingapp.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.taniakolesnik.adn_bakingapp.objects.Recipe;

/**
 * Created by tetianakolesnik on 27/08/2018.
 */

public class RecipeAsyncTaskLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String URL_YEAR_PATH = "2017";
    private static final String URL_MONTH_PATH = "May";
    private static final String URL_LIST_PATH = "59121517_baking";
    private List<Recipe> recipes;

    public RecipeAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onReset() {
        cancelLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {

        recipes = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        final Retrofit retrofit = builder.build();
        BakingGetListClient client = retrofit.create(BakingGetListClient.class);
        Call<List<Recipe>> call = client.getRecipesList(URL_YEAR_PATH, URL_MONTH_PATH, URL_LIST_PATH);
        try {
            recipes = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
