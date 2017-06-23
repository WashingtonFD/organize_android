package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controlers.NotificationControler;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contentNoData)
    RelativeLayout contentNoData;
    @Bind(R.id.containerContent)
    RelativeLayout containerContent;
    @Bind(R.id.listNotification)
    RecyclerView listNotification;
    private Context context;
    private ArrayList<UserNotification> userNotifications;
    private ArrayList<UserNotification> activeNotificatios;
    private NotificationAdapter adapter;
    private int is_read = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "NOTIFICATION");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = NotificationsActivity.this;
        userNotifications = getIntent().getParcelableArrayListExtra("userNotifications");
        user = AppApplication.getFirstAccess().getUser();

        configureToolbar(context, toolbar, context.getString(R.string.label_notifications), context.getResources().getDrawable(R.drawable.ic_arrow_back), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        listNotification.setLayoutManager(new LinearLayoutManager(context));
        listNotification.setItemAnimator(new DefaultItemAnimator());

        if (userNotifications.size() > 0) {
            containerContent.setVisibility(View.VISIBLE);
            contentNoData.setVisibility(View.GONE);
            for (UserNotification userNotification : userNotifications) {
                if (userNotification.is_active()) {
                    activeNotificatios.add(userNotification);
                }
            }
            loadAdapter();
        } else {
            containerContent.setVisibility(View.GONE);
            contentNoData.setVisibility(View.VISIBLE);
        }
    }

    protected void loadAdapter() {
        Collections.sort(userNotifications);
        adapter = new NotificationAdapter(context, activeNotificatios, listNotification, new RecyclerViewListener() {
            @Override
            public void onClick(int position) {
                UserNotification userNotification = activeNotificatios.get(position);
                is_read = 1;
                readUserNotification(userNotification, is_read);
            }
        });
        listNotification.setAdapter(adapter);
    }

    public void readUserNotification(final UserNotification userNotification, int is_read) {
        new NotificationControler(context).readUserNotification(userNotification, is_read, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                return;
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }


    public void readAllNotification() {
        showLoading();
        new NotificationControler(context).readAllNotification(user.getId(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                userNotifications.clear();
                adapter.refreshAllLayout();
                listNotification.clearAnimation();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnClick(R.id.imgClear)
    public void actionClearNotifications() {
        readAllNotification();
    }
}
