package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.PrivacyControll;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.enuns.PrivacyEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.UserPrivacy;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyActivity extends BaseActivity {

    private Context context;
    private FirstAccess firstAccess;
    private ArrayList<UserPrivacy> userPrivacies;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rgpListPrivacy)
    RadioGroup rgpListPrivacy;
    @Bind(R.id.btnAll)
    RadioButton btnAll;
    @Bind(R.id.btnJustFriends)
    RadioButton btnJustFriends;
    @Bind(R.id.btnNoOne)
    RadioButton btnNoOne;

    private String infoAll, infoFriends, infoNone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "PRIVACY");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = PrivacyActivity.this;
        firstAccess = AppApplication.getFirstAccess();
        userPrivacies = firstAccess.getUser().getUser_privacy();

        configureToolbar(context, toolbar, context.getString(R.string.label_privacy), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        for (UserPrivacy userPrivacy : userPrivacies) {
            if (userPrivacy.getPrivacy().getCode_enum() == PrivacyEnum.ALL.getValue()) {
                btnAll.setChecked(userPrivacy.isChecking());
                infoAll = userPrivacy.getPrivacy().getDescription();
            } else if (userPrivacy.getPrivacy().getCode_enum() == PrivacyEnum.JUST_FRIENDS.getValue()) {
                btnJustFriends.setChecked(userPrivacy.isChecking());
                infoFriends = userPrivacy.getPrivacy().getDescription();
            } else {
                btnNoOne.setChecked(userPrivacy.isChecking());
                infoNone = userPrivacy.getPrivacy().getDescription();
            }
        }

        selectPrivacy();
    }

    protected void selectPrivacy() {
        rgpListPrivacy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int code_privacy;
                switch (checkedId) {
                    case R.id.btnAll:
                        code_privacy = PrivacyEnum.ALL.getValue();
                        setUserPrivacy(code_privacy);
                        break;
                    case R.id.btnJustFriends:
                        code_privacy = PrivacyEnum.JUST_FRIENDS.getValue();
                        setUserPrivacy(code_privacy);
                        break;
                    case R.id.btnNoOne:
                        code_privacy = PrivacyEnum.NO_ONE.getValue();
                        setUserPrivacy(code_privacy);
                        break;
                }
            }
        });
    }

    protected void setUserPrivacy(int code_enum) {
        int checking = 0;
        for (UserPrivacy userPrivacy : userPrivacies) {
            userPrivacy.setChecking(false);
            if (userPrivacy.getPrivacy().getCode_enum() == code_enum) {
                userPrivacy.setChecking(true);
                checking = 1;
            }

            new PrivacyControll(context).privacyChecking(userPrivacy, checking, new ControllResponseListener() {
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

    @OnClick({R.id.btnInfoAll, R.id.btnInfoJustFriends, R.id.btnInfoNoOne})
    public void actionInfoPrivacy(View view) {
        switch (view.getId()) {
            case R.id.btnInfoAll:
                showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), infoAll, new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void negativeOnClidck(MaterialDialog dialog) {

                    }
                });
                break;
            case R.id.btnInfoJustFriends:
                showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), infoFriends, new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void negativeOnClidck(MaterialDialog dialog) {

                    }
                });
                break;
            case R.id.btnInfoNoOne:
                showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), infoNone, new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void negativeOnClidck(MaterialDialog dialog) {

                    }
                });
                break;
        }
    }
}
