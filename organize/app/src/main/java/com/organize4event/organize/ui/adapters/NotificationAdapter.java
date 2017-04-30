package com.organize4event.organize.ui.adapters;


import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.MultipleRecyclerViewListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserNotification;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

        private Context context;
        private ArrayList<UserNotification> items;
        private RecyclerView recyclerView;
        private MultipleRecyclerViewListener listener;
        private int expandedPosition = -1;

    public NotificationAdapter(Context context, ArrayList<UserNotification> items, RecyclerView recyclerView, MultipleRecyclerViewListener listener) {
        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        NotificationViewHolder viewHolder = new NotificationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder holder, final int position) {
        UserNotification userNotification = items.get(position);
        holder.txtNotificationBriefDescription.setText(userNotification.getBrief_description());
        holder.txtNotificationDescription.setText(userNotification.getDescription());

                isExpand(holder, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {


            @Bind(R.id.rowContent)
            RelativeLayout rowContent;

            @Bind(R.id.txtNotificationBriefDescription)
            TextView txtNotificationBriefDescription;

            @Bind(R.id.txtNotificationDescription)
            TextView txtNotificationDescription;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

        public void isExpand(final NotificationViewHolder holder, final int position){
        final boolean isExpanded = position==expandedPosition;
        holder.txtNotificationDescription.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyItemChanged(position);
            }
        });
    }

}
