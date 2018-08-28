package uk.co.taniakolesnik.adn_bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.DetailsActivity;
import uk.co.taniakolesnik.adn_bakingapp.R;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;

/**
 * Created by tetianakolesnik on 24/08/2018.
 */

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> data;
    private Context context;

    public RecipesRecyclerViewAdapter(Context context, List<Recipe> data) {
        this.context = context;
        this.data = data;
    }

    public void updateData(List<Recipe> recipes){
        Log.i("Adapter", "recipes " + recipes.toString());
        this.data = recipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable{

        @BindView(R.id.recipes_name) TextView nameView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = data.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(context.getResources().getString(R.string.recipe_bundle), recipe);
            context.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciper_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
