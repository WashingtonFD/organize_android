package com.organize4event.organize.utils;


import android.annotation.SuppressLint;
import android.content.Context;

import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.controlers.NotificationControler;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.ui.activities.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    public static void insertNotification(final Context context, int user_id, String brief_description, String description, Date notification_date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.FULL_DATE_FORMAT);

        UserNotification userNotification = new UserNotification();
        userNotification.setUser(user_id);
        userNotification.setBrief_description(brief_description);
        userNotification.setDescription(description + " " + simpleDateFormat.format(notification_date));
        userNotification.setNotification_date(notification_date);

        new NotificationControler(context).saveUserNotification(userNotification, new ControlResponseListener() {
            @Override
            public void success(Object object) {
            }

            @Override
            public void fail(Error error) {
                new BaseActivity().showErrorMessage(context, error);
            }
        });
    }
}
