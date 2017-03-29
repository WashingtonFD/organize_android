package com.organize4event.organize.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controllers.PlanControll;
import com.organize4event.organize.controllers.SettingsControll;
import com.organize4event.organize.enuns.SettingsEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.MultipleRecyclerViewListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.ui.activities.LoginActivity;
import com.organize4event.organize.ui.activities.PlanDetailActivity;
import com.organize4event.organize.ui.adapters.PlanAdapter;
import com.organize4event.organize.ui.adapters.SettingsAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsFragment extends BaseFragment{
    private Context context;
    private FirstAccess firstAccess;
    private User user;
    private ArrayList<UserSetting> userSettings;
    private ArrayList<Plan> plans;
    private SettingsAdapter adapter;
    private int checking = 0;

    @Bind(R.id.rcvListSettings)
    RecyclerView rcvListSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        firstAccess = AppApplication.getFirstAccess();
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

               if (userSettings.size() > 0) {
                   adapter = new SettingsAdapter(context, userSettings, rcvListSettings, new MultipleRecyclerViewListener() {
                       @Override
                       public void onClick(int position) {
                           UserSetting userSetting = userSettings.get(position);
                           SettingsEnum settingsEnum = SettingsEnum.values()[(userSetting.getSettings().getCode_enum()) -1];
                           switch (settingsEnum){
                               case PRIVACY:
                                   startPrivacySettings();
                                   break;
                               case BEST_DAY_FOR_PAYMENT:
                                   startDayPayment();
                                   break;
                               case OUR_PLANS:
                                   getPlans();
                                   break;
                               case TUTORIAL:
                                   startTutorial();
                                   break;
                               case EXIT:
                                   logoff();
                                   break;
                           }
                       }

                       @Override
                       public void onLongClick(int position) {

                       }

                       @Override
                       public void onChange(int position) {
                           if (userSettings.get(position).isChecking()){
                               userSettings.get(position).setChecking(false);
                               checking = 0;
                           }
                           else {
                               userSettings.get(position).setChecking(true);
                               checking = 1;
                           }

                           checkingUserSettings(userSettings.get(position), checking);
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

    public void getPlans(){
        new PlanControll(context).getPlan(firstAccess.getLocale(), new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                plans = (ArrayList<Plan>) object;
                startOurPlans(plans);
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });

    }

    public void checkingUserSettings(final UserSetting userSetting, int checking){
        new SettingsControll(context).checkingUserSettings(userSetting, checking, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {

            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void startPrivacySettings(){
        showToastMessage(context, "ABRIR ACTIVITY PRIVACY");
    }

    public void startDayPayment(){
        showToastMessage(context, "ABRIR ACTIVITY MELHOR DIA PAGAMENTO");
        //TODO: IMPLEMENTAR MELHOR DIA PAGAMENTO.
    }

    public void startOurPlans(final ArrayList<Plan> plans){
        String title = context.getString(R.string.label_our_plans);
        String message = context.getString(R.string.message_list_plan);

        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(R.layout.custom_dialog_list, false).show();
        RecyclerView rcvList = (RecyclerView) dialog.getCustomView().findViewById(R.id.rcvList);
        TextView dialog_title = (TextView) dialog.getCustomView().findViewById(R.id.txtTitle);
        TextView dialog_message = (TextView) dialog.getCustomView().findViewById(R.id.txtMessage);
        Button dialog_positive = (Button) dialog.getCustomView().findViewById(R.id.btnPositive);

        dialog_title.setText(title);
        dialog_message.setText(message);

        rcvList.setLayoutManager(new LinearLayoutManager(context));
        rcvList.setItemAnimator(new DefaultItemAnimator());

        PlanAdapter adapter = new PlanAdapter(context, plans, new RecyclerViewListener() {
            @Override
            public void onClick(int position) {
                Plan plan = plans.get(position);
                Intent intent = new Intent(context, PlanDetailActivity.class);
                intent.putExtra("plan", Parcels.wrap(Plan.class, plan));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        rcvList.setAdapter(adapter);

        dialog_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void startTutorial(){
        showToastMessage(context, "ABRIR TUTORIAL");
    }

    public void logoff(){
        PreferencesManager.saveUser(null);
        AppApplication.setUser(null);
        PreferencesManager.saveToken(null);
        AppApplication.setToken(null);
        startActivity(new Intent(context, LoginActivity.class));
        getActivity().finish();
    }
}
