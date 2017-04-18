package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.enuns.PrivacyEnum;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.UserPrivacy;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);

        context = PrivacyActivity.this;
        firstAccess = AppApplication.getFirstAccess();
        userPrivacies = firstAccess.getUser().getUser_privacy();

        configureToolbar(context, toolbar, context.getString(R.string.label_privacy), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        for (UserPrivacy userPrivacy : userPrivacies){
            if (userPrivacy.getPrivacy().getCode_enum() == PrivacyEnum.ALL.getValue()){
                btnAll.setChecked(userPrivacy.isChecking());
            }
            else if (userPrivacy.getPrivacy().getCode_enum() == PrivacyEnum.JUST_FRIENDS.getValue()){
                btnJustFriends.setChecked(userPrivacy.isChecking());
            }
            else{
                btnNoOne.setChecked(userPrivacy.isChecking());
            }
        }
    }

    protected void selectPrivacy(){
        rgpListPrivacy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int code_privacy;
                switch (checkedId){
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

    protected void setUserPrivacy(int code_enum){
        

    }
}
