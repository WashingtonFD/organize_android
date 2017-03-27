package com.organize4event.organize.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.SettingsControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.SwitchChangeListener;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.ui.adapters.SettingsAdapter;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsFragment extends BaseFragment{
    private Context context;
    private User user;
    private ArrayList<UserSetting> userSettings;
    private SettingsAdapter adapter;

    @Bind(R.id.rcvListSettings)
    RecyclerView rcvListSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        user = AppApplication.getUser();

        rcvListSettings.setLayoutManager(new LinearLayoutManager(context));
        rcvListSettings.setItemAnimator(new DefaultItemAnimator());
        rcvListSettings.setAdapter(adapter);

        getData();

        return view;
    }

    public void getData(){
        showLoading();
        new SettingsControll(context).getUserSettings(user.getId(), new ControllResponseListener() {
           @Override
           public void sucess(Object object) {
               hideLoading();
               userSettings = (ArrayList<UserSetting>) object;
               Collections.sort(userSettings);

               if (userSettings.size() > 0){
                   adapter = new SettingsAdapter(context, userSettings, rcvListSettings, new SwitchChangeListener() {
                       @Override
                       public void onChange(int position) {

                       }
                   });
               }
               rcvListSettings.setAdapter(adapter);
           }

           @Override
           public void fail(Error error) {
               hideLoading();
               returnErrorMessage(error, context);
           }
       });
    }
}
