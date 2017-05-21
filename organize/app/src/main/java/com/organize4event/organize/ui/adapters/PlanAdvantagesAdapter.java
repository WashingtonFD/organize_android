package com.organize4event.organize.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.models.PlanAdvantage;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanAdvantagesAdapter extends RecyclerView.Adapter<PlanAdvantagesAdapter.PlanAdvantagesViewHolder> {
    private Context context;
    private ArrayList<PlanAdvantage> items;

    public PlanAdvantagesAdapter(Context context, ArrayList<PlanAdvantage> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public PlanAdvantagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan_advantage, parent, false);
        PlanAdvantagesViewHolder viewHolder = new PlanAdvantagesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlanAdvantagesViewHolder holder, int position) {
        PlanAdvantage planAdvantage = items.get(position);
        holder.txtAdvantege.setText(planAdvantage.getAdvantage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PlanAdvantagesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtAdvantege)
        TextView txtAdvantege;

        public PlanAdvantagesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
