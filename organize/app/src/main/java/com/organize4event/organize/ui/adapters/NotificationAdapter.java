package com.organize4event.organize.ui.adapters;


import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.organize4event.organize.R;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.UserNotification;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private ArrayList<UserNotification> items;
    private RecyclerView recyclerView;
    private RecyclerViewListener listener;
    private int expandedPosition = -1;

    public NotificationAdapter(Context context, ArrayList<UserNotification> items, RecyclerView recyclerView, RecyclerViewListener listener) {
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

        if (userNotification.is_read()) {
            holder.rowContent.setBackgroundColor(context.getResources().getColor(R.color.colorTransparent));
            holder.txtNotificationBriefDescription.setTextColor(context.getResources().getColor(R.color.colorHintDefault));
            holder.txtNotificationDescription.setTextColor(context.getResources().getColor(R.color.colorHintDefault));
            holder.txtNotificationTime.setTextColor(context.getResources().getColor(R.color.colorHintDefault));
            holder.imgClock.setColorFilter(context.getResources().getColor(R.color.colorHintDefault));
        }

        holder.txtNotificationBriefDescription.setText(userNotification.getBrief_description());
        holder.txtNotificationDescription.setText(userNotification.getDescription());
        holder.txtNotificationTime.setReferenceTime(userNotification.getNotification_date().getTime());

        isExpand(holder, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void isExpand(final NotificationViewHolder holder, final int position) {
        final boolean isExpanded = position == expandedPosition;
        holder.txtNotificationDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
                expandedPosition = isExpanded ? -1 : position;
                TransitionManager.beginDelayedTransition(recyclerView);
                refreshPositionLayout(position);
            }
        });
    }

    public void refreshPositionLayout(int position) {
        UserNotification userNotification = items.get(position);
        userNotification.setIs_read(true);
        notifyItemChanged(position);
    }

    public void refreshAllLayout() {
        notifyDataSetChanged();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rowContent)
        RelativeLayout rowContent;

        @Bind(R.id.txtNotificationBriefDescription)
        TextView txtNotificationBriefDescription;

        @Bind(R.id.txtNotificationDescription)
        TextView txtNotificationDescription;

        @Bind(R.id.txtNotificationTime)
        RelativeTimeTextView txtNotificationTime;

        @Bind(R.id.imgClock)
        ImageView imgClock;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
