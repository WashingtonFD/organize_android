package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.organize4event.organize.R;
import com.organize4event.organize.controllers.NotificationControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity {
    private Context context;
    private ArrayList<UserNotification> userNotifications;
    private NotificationAdapter adapter;
    private int is_read = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.contentNoData)
    RelativeLayout contentNoData;

    @Bind(R.id.containerContent)
    RelativeLayout containerContent;

    @Bind(R.id.listNotification)
    RecyclerView listNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        context = NotificationsActivity.this;
        userNotifications = getIntent().getParcelableArrayListExtra("userNotifications");

        configureToolbar(context, toolbar, context.getString(R.string.label_notifications), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        listNotification.setLayoutManager(new LinearLayoutManager(context));
        listNotification.setItemAnimator(new DefaultItemAnimator());

        if (userNotifications.size() > 0){
            containerContent.setVisibility(View.VISIBLE);
            contentNoData.setVisibility(View.GONE);
            loadAdapter();
        }
        else{
            containerContent.setVisibility(View.GONE);
            contentNoData.setVisibility(View.VISIBLE);
        }
    }

    protected void loadAdapter() {
        Collections.sort(userNotifications);
        adapter = new NotificationAdapter(context, userNotifications, listNotification, new RecyclerViewListener() {
            @Override
            public void onClick(int position) {
                UserNotification userNotification = userNotifications.get(position);
                is_read = 1;
                readUserNotification(userNotification, is_read, false);
            }
        });
        listNotification.setAdapter(adapter);
    }

    public void readUserNotification(final UserNotification userNotification, int is_read, final boolean clearAll) {
        new NotificationControll(context).readUserNotification(userNotification, is_read, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                if (clearAll){
                    adapter.refreshAllLayout();
                }


            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnClick(R.id.imgClear)
    public void actionClearNotifications() {
        for (UserNotification userNotification : userNotifications) {
            is_read = 1;
            readUserNotification(userNotification, is_read, true);
        }
    }
}
