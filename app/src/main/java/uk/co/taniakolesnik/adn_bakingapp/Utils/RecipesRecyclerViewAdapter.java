package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.R;
import uk.co.taniakolesnik.adn_bakingapp.Recipe;

/**
 * Created by tetianakolesnik on 24/08/2018.
 */

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Recipe> data;
    public RecipesRecyclerViewAdapter(ArrayList<Recipe> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipes_name) TextView nameView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciper_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(data.get(position).getName());
        Log.i("Adapter", "recipe is  " + data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
