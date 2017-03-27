package com.organize4event.organize.ui.adapters;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.enuns.SettingsEnum;
import com.organize4event.organize.listeners.MultipleRecyclerViewListener;
import com.organize4event.organize.models.UserSetting;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>{

    private Context context;
    private ArrayList<UserSetting> items;
    private RecyclerView recyclerView;
    private MultipleRecyclerViewListener listener;
    private int expandedPosition = -1;

    public SettingsAdapter(Context context, ArrayList<UserSetting> items, RecyclerView recyclerView, MultipleRecyclerViewListener listener) {
        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false);
        SettingsViewHolder viewHolder = new SettingsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SettingsViewHolder holder, final int position) {
        UserSetting userSetting = items.get(position);
        holder.txtSettingName.setText(userSetting.getSettings().getName());
        holder.txtSettingDescription.setText(userSetting.getSettings().getDescription());
        holder.swtChecking.setChecked(userSetting.isChecking());

        if (userSetting.getSettings().getCode_enum() == SettingsEnum.BEST_DAY_FOR_PAYMENT.getValue() || userSetting.getSettings().getCode_enum() == SettingsEnum.PRIVACY.getValue() || userSetting.getSettings().getCode_enum() == SettingsEnum.OUR_PLANS.getValue() || userSetting.getSettings().getCode_enum() == SettingsEnum.TUTORIAL.getValue() || userSetting.getSettings().getCode_enum() == SettingsEnum.EXIT.getValue()){
            holder.swtChecking.setVisibility(View.GONE);
            holder.rowContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
        else{
            isExpand(holder, position);
        }

        holder.swtChecking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onChange(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.rowContent)
        RelativeLayout rowContent;

        @Bind(R.id.txtSettingName)
        TextView txtSettingName;

        @Bind(R.id.txtSettingDescription)
        TextView txtSettingDescription;

        @Bind(R.id.swtChecking)
        SwitchCompat swtChecking;

        public SettingsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void isExpand(final SettingsViewHolder holder, final int position){
        final boolean isExpanded = position==expandedPosition;
        holder.txtSettingDescription.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
