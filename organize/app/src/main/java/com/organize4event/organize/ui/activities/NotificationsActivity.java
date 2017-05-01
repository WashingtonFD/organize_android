package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.NotificationControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.MultipleRecyclerViewListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity {
    private Context context;
    private FirstAccess firstAccess;
    private User user;
    private ArrayList<UserNotification> userNotifications;
    private NotificationAdapter adapter;
    private int is_read = 0;

    @Bind(R.id.rcvListNotification)
    RecyclerView rcvListNotification;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.imgClear)
    ImageView imgClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        context = NotificationsActivity.this;
        firstAccess = AppApplication.getFirstAccess();
        user = firstAccess.getUser();
        userNotifications = user.getUser_notifications();


        configureToolbar(context, toolbar, context.getString(R.string.label_notifications), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });



        if (userNotifications == null || userNotifications.size() < 1) {
            getData();
        } else {
            loadAdapter();
        }

        rcvListNotification.setLayoutManager(new LinearLayoutManager(context));
        rcvListNotification.setItemAnimator(new DefaultItemAnimator());
        rcvListNotification.setAdapter(adapter);

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(UserNotification userNotification : userNotifications){

                    is_read = 1;
                    readUserNotification(userNotification, is_read);
                }
            }
        });




    }

    public void getData(){
        showLoading();
            new NotificationControll(context).getUserNotification(user.getId(), new ControllResponseListener() {
                @Override
                public void success(Object object) {
                    hideLoading();
                    userNotifications = (ArrayList<UserNotification>) object;
                    loadAdapter();
                }

                @Override
                public void fail(Error error) {

                    returnErrorMessage(error, context);

                }
            });

    }

    protected void loadAdapter() {
        Collections.reverse(userNotifications);
        adapter = new NotificationAdapter(context, userNotifications, rcvListNotification, new RecyclerViewListener() {
            @Override
            public void onClick(int position) {
                UserNotification userNotification = userNotifications.get(position);
                    is_read = 1;

                    readUserNotification(userNotification, is_read);


            }

        });

        rcvListNotification.setAdapter(adapter);

    }

    public void readUserNotification(final UserNotification userNotification, int is_read) {
        new NotificationControll(context).readUserNotification(userNotification, is_read, new ControllResponseListener() {
            @Override
            public void success(Object object) {

            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }
}
