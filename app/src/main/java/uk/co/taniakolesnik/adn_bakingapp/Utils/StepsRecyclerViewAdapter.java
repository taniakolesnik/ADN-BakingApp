package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.InstructionsActivity;
import uk.co.taniakolesnik.adn_bakingapp.R;
import uk.co.taniakolesnik.adn_bakingapp.Step;

/**
 * Created by tetianakolesnik on 26/08/2018.
 */

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Step> data;
    private Context context;

    public StepsRecyclerViewAdapter(Context context, ArrayList<Step> data) {
        this.data = data;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable{

        @BindView(R.id.step_short_description_textView)
        TextView short_description;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            Step step = data.get(getAdapterPosition());
            Intent intent = new Intent(context, InstructionsActivity.class);
            intent.putExtra(context.getResources().getString(R.string.step_bundle), step);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.short_description.setText(data.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}