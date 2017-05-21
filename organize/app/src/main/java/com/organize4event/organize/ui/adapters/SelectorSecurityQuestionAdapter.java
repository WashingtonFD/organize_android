package com.organize4event.organize.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.SecurityQuestion;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectorSecurityQuestionAdapter extends RecyclerView.Adapter<SelectorSecurityQuestionAdapter.ListViewHolder> {

    private Context context;
    private ArrayList<SecurityQuestion> items;
    private SecurityQuestion itemSelected;
    private RecyclerViewListener listener;

    public SelectorSecurityQuestionAdapter(Context context, ArrayList<SecurityQuestion> items, SecurityQuestion itemSelected, RecyclerViewListener listener) {
        this.context = context;
        this.items = items;
        this.itemSelected = itemSelected;
        this.listener = listener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selector, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        SecurityQuestion securityQuestion = items.get(position);
        holder.txtItemName.setText(securityQuestion.getSecurity_question());

        if (securityQuestion.equals(itemSelected)) {
            holder.imgAccept.setVisibility(View.VISIBLE);
        } else {
            holder.imgAccept.setVisibility(View.GONE);
        }

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

    public class ListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowContent)
        RelativeLayout rowContent;

        @Bind(R.id.txtItemName)
        TextView txtItemName;
        @Bind(R.id.imgAccept)
        ImageView imgAccept;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
