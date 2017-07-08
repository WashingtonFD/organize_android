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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controlers.PlanControler;
import com.organize4event.organize.controlers.SettingsControler;
import com.organize4event.organize.enuns.SettingsEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.MultipleRecyclerViewListener;
import com.organize4event.organize.listeners.RecyclerViewListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.ui.activities.AboutActivity;
import com.organize4event.organize.ui.activities.PlanDetailActivity;
import com.organize4event.organize.ui.activities.PrivacyActivity;
import com.organize4event.organize.ui.activities.TermUseActivity;
import com.organize4event.organize.ui.adapters.PlanAdapter;
import com.organize4event.organize.ui.adapters.SettingsAdapter;
import com.organize4event.organize.utils.MessageUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsFragment extends BaseFragment {
    @Bind(R.id.listSettings)
    RecyclerView listSettings;
    private Context context;
    private FirstAccess firstAccess;
    private User user;
    private ArrayList<UserSetting> userSettings;
    private ArrayList<Plan> plans;
    private SettingsAdapter adapter;
    private int checking = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SETTINGS");
        FirebaseAnalytics.getInstance(getActivity()).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = getActivity();
        firstAccess = AppApplication.getFirstAccess();
        user = firstAccess.getUser();
        userSettings = user.getUser_settings();

        if (userSettings == null || userSettings.size() < 1) {
            getData();
        } else {
            loadAdapter();
        }

        listSettings.setLayoutManager(new LinearLayoutManager(context));
        listSettings.setItemAnimator(new DefaultItemAnimator());
        listSettings.setAdapter(adapter);

        return view;
    }

    protected void getData() {
        showLoading();
        new SettingsControler(context).getUserSettings(user.getId(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
               if (object != null){
                   hideLoading();
                   userSettings = (ArrayList<UserSetting>) object;
                   loadAdapter();
               }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void loadAdapter() {
        Collections.sort(userSettings);
        adapter = new SettingsAdapter(context, userSettings, listSettings, new MultipleRecyclerViewListener() {
            @Override
            public void onClick(int position) {
                UserSetting userSetting = userSettings.get(position);
                SettingsEnum settingsEnum = SettingsEnum.values()[(userSetting.getSetting().getCode_enum()) - 1];
                switch (settingsEnum) {
                    case PRIVACY:
                        startPrivacySettings();
                        break;
                    case BEST_DAY_FOR_PAYMENT:
                        startDayPayment();
                        break;
                    case OUR_PLANS:
                        getPlans();
                        break;
                    case TERM_USE:
                        startTermUse();
                        break;
                    case TUTORIAL:
                        startTutorial();
                        break;
                    case ABOUT:
                        startAbout();
                        break;
                }
            }

            @Override
            public void onLongClick(int position) {

            }

            @Override
            public void onChange(int position) {
                if (userSettings.get(position).isChecking()) {
                    userSettings.get(position).setChecking(false);
                    checking = 0;
                } else {
                    userSettings.get(position).setChecking(true);
                    checking = 1;
                }

                checkingUserSettings(userSettings.get(position), checking);
            }
        });
        listSettings.setAdapter(adapter);
    }

    public void getPlans() {
        new PlanControler(context).getPlan(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null){
                    plans = (ArrayList<Plan>) object;
                    startOurPlans(plans);
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });

    }

    public void checkingUserSettings(final UserSetting userSetting, int checking) {
        new SettingsControler(context).checkingUserSettings(userSetting, checking, new ControlResponseListener() {
            @Override
            public void success(Object object) {

            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    public void startPrivacySettings() {
        startActivity(new Intent(context, PrivacyActivity.class));
    }

    public void startDayPayment() {
        MessageUtils.showToastMessage(context, "ABRIR ACTIVITY MELHOR DIA PAGAMENTO");
        //TODO: IMPLEMENTAR MELHOR DIA PAGAMENTO.
    }

    public void startOurPlans(final ArrayList<Plan> plans) {
        String title = context.getString(R.string.label_our_plans);
        String message = context.getString(R.string.message_list_plan);

        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(R.layout.custom_dialog_list, false).show();
        RecyclerView rcvList = (RecyclerView) dialog.getCustomView().findViewById(R.id.dialogList);
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

    public void startTermUse() {
        Intent intent = new Intent(context, TermUseActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        intent.putExtra("readOnly", true);
        startActivity(intent);
    }

    public void startTutorial() {
        MessageUtils.showToastMessage(context, "ABRIR TUTORIAL");
    }

    public void startAbout() {
        startActivity(new Intent(context, AboutActivity.class));
    }
}
