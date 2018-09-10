package uk.co.taniakolesnik.adn_bakingapp.ui;

import android.content.Context;
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
import uk.co.taniakolesnik.adn_bakingapp.InstructionsListFragment;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;
import uk.co.taniakolesnik.adn_bakingapp.R;

/**
 * Created by tetianakolesnik on 26/08/2018.
 */

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Step> data;
    private Context context;

    public StepsRecyclerViewAdapter(Context context, ArrayList<Step> data) {
        this.data = data;
        this.context = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable {

        @BindView(R.id.step_short_description_textView)
        TextView short_description;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            InstructionsListFragment.mStepClickListener.onStepSelected(getAdapterPosition(), data);
        }
    }
}