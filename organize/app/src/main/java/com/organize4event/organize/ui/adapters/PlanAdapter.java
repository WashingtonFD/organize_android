package com.organize4event.organize.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.Plan;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private Context context;
    private ArrayList<Plan> items;
    private RecyclerViewListener listener;

    public PlanAdapter(Context context, ArrayList<Plan> items, RecyclerViewListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);
        PlanViewHolder viewHolder = new PlanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, final int position) {
        Plan plan = items.get(position);
        holder.txtName.setText(plan.getName());
        holder.rowContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.rowContent)
        RelativeLayout rowContent;

        @Bind(R.id.txtName)
        TextView txtName;

        public PlanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
