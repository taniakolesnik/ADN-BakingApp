package uk.co.taniakolesnik.adn_bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.taniakolesnik.adn_bakingapp.Utils.BakingGetListClient;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String URL_YEAR_PATH = "2017";
    private static final String URL_MONTH_PATH = "May";
    private static final String URL_LIST_PATH = "59121517_baking";

    @BindView(R.id.recipes_recyclerView) RecyclerView recyclerView;

    private ArrayList<Recipe> recipes;
    private RecipesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getRecipesList();

    }

    private void getRecipesList() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        BakingGetListClient client = retrofit.create(BakingGetListClient.class);
        Call<List<Recipe>> call = client.getRecipesList(URL_YEAR_PATH, URL_MONTH_PATH, URL_LIST_PATH);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = (ArrayList<Recipe>) response.body();
                adapter = new RecipesRecyclerViewAdapter(recipes);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("MainActivity", "onFailure ");
            }
        });
    }

}
