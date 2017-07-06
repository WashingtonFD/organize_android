package com.organize4event.organize.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.CircleTransform;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controlers.FirstAccessControler;
import com.organize4event.organize.controlers.NotificationControler;
import com.organize4event.organize.controlers.TokenControler;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.enuns.SettingsEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.UserNotification;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.models.UserValidate;
import com.organize4event.organize.ui.fragments.HomeFragment;
import com.organize4event.organize.ui.fragments.InstitutionalFragment;
import com.organize4event.organize.ui.fragments.PersonalDataFragment;
import com.organize4event.organize.ui.fragments.SettingsFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.organize4event.organize.R.id.containerContent;

public class HomeActivity extends BaseActivity {
    Class fragmentClass;
    String TAG_FRAGMENT = "HOME";
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imgNotification)
    ImageView imgNotification;
    @Bind(R.id.txtUserName)
    TextView txtUserName;
    @Bind(R.id.txtUserMail)
    TextView txtUserMail;
    @Bind(R.id.imgUserAvatar)
    ImageView imgUserAvatar;
    private Context context;
    private boolean showMenu = false;
    private String device_id;
    private FirstAccess firstAccess;
    private Token token;
    private ArrayList<UserNotification> userNotifications = new ArrayList<>();
    private ArrayList<UserSetting> userSettings = new ArrayList<>();
    private UserSetting userSettingNotification;
    private UserValidate userValidate;

    public boolean editMode = false;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        context = HomeActivity.this;
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        setupToolbar(context.getString(R.string.label_nav_home));
        if (!PreferencesManager.isLogged()) {
            PreferencesManager.setIsLogged();
        }

        fragmentClass = HomeFragment.class;
        try {
            Log.v("instance HOME", "Home fragment instance");
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(containerContent, fragment, TAG_FRAGMENT).commit();
        } catch (Exception e) {
            Log.v("instance HOME ERROR", "Home fragment instance error");
            e.printStackTrace();
        }
    }

    protected void setupToolbar(String title) {
        configureToolbar(context, toolbar, title, context.getResources().getDrawable(R.drawable.ic_menu), true, new ToolbarListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    protected void getData() {
        new FirstAccessControler(context).getFirstAccess(device_id, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    firstAccess = (FirstAccess) object;
                    token = firstAccess.getUser().getToken();
                    userValidate = firstAccess.getUser().getUser_validate();
                    txtUserName.setText(firstAccess.getUser().getFull_name());
                    txtUserMail.setText(firstAccess.getUser().getMail());
                    Glide.with(context).load(firstAccess.getUser().getProfile_picture()).centerCrop().transform(new CircleTransform(context)).crossFade().into(imgUserAvatar);
                    userSettings = firstAccess.getUser().getUser_settings();
                    for (UserSetting userSetting : userSettings) {
                        if (userSetting.getSetting().getCode_enum() == SettingsEnum.NOTIFICATIONS.getValue()) {
                            userSettingNotification = userSetting;
                        }
                    }

                    if (!userValidate.is_valid()) {
                        showDialogMessage(DialogTypeEnum.VALIDATE_EMAIL, context.getString(R.string.app_name), context.getString(R.string.message_not_validate_email), new CustomDialogListener() {
                            @Override
                            public void positiveOnClick(MaterialDialog dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void negativeOnClick(MaterialDialog dialog) {
                                sendValidateMail(context, firstAccess.getUser().getMail());
                                dialog.dismiss();
                            }
                        });
                    }

                    if (userSettingNotification != null && userSettingNotification.isChecking()) {
                        getNotifications();
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void getNotifications() {
        new NotificationControler(context).getUserNotifications(firstAccess.getUser().getId(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    userNotifications = (ArrayList<UserNotification>) object;
                    firstAccess.getUser().setUser_notifications(userNotifications);
                    PreferencesManager.saveFirstAccess(firstAccess);
                    AppApplication.setFirstAccess(firstAccess);
                    assetIconNotification();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void assetIconNotification() {
        if (userNotifications.size() > 0) {
            imgNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications_on));
            imgNotification.setColorFilter(context.getResources().getColor(R.color.colorTransparent));
        } else {
            imgNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notifications));
            imgNotification.setColorFilter(context.getResources().getColor(R.color.colorDestakText));
        }
    }

    protected void logout() {
        saveToken();
    }

    protected void saveToken() {
        Token newToken = new Token();
        newToken.setLogin_type(token.getLogin_type());
        newToken.setAccess_platform(token.getAccess_platform());
        newToken.setAccess_date(new Date());
        newToken.setKeep_logged(false);
        new TokenControler(context).saveToken(newToken, firstAccess.getUser().getId(), 0, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    token = (Token) object;
                    firstAccess.getUser().setToken(token);
                    PreferencesManager.saveFirstAccess(firstAccess);
                    starLoginActivity();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnClick(R.id.imgNotification)
    public void actionOpenNotifications() {
        Intent intent = new Intent(context, NotificationsActivity.class);
        intent.putParcelableArrayListExtra("userNotifications", userNotifications);
        startActivity(intent);
    }

    @OnClick({R.id.userContainer, R.id.homeContainer, R.id.eventContainer, R.id.sheduleContainer, R.id.partnerContainer, R.id.paymentContainer, R.id.purchaseContainer, R.id.settingsContainer, R.id.institutionalContainer, R.id.loggoutContainer})
    public void actionMenuSwitch(View view) {
        switch (view.getId()) {
            case R.id.userContainer:
                instanceViewFragment(PersonalDataFragment.class, context.getString(R.string.label_nav_user), false, true);
                TAG_FRAGMENT = "PERSONAL_DATA";
                break;
            case R.id.homeContainer:
                instanceViewFragment(HomeFragment.class, context.getString(R.string.label_nav_home), true, false);
                TAG_FRAGMENT = "HOME";
                break;
            case R.id.eventContainer:
                break;
            case R.id.sheduleContainer:
                break;
            case R.id.partnerContainer:
                break;
            case R.id.paymentContainer:
                break;
            case R.id.purchaseContainer:
                break;
            case R.id.settingsContainer:
                instanceViewFragment(SettingsFragment.class, context.getString(R.string.label_nav_settings), true, false);
                TAG_FRAGMENT = "SETTINGS";
                break;
            case R.id.institutionalContainer:
                instanceViewFragment(InstitutionalFragment.class, context.getString(R.string.label_nav_institutional), true, false);
                TAG_FRAGMENT = "INSTITUTIONAL";
                break;
            case R.id.loggoutContainer:
                logout();
                break;
            default:
                instanceViewFragment(HomeFragment.class, context.getString(R.string.label_nav_home), true, false);
                break;
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(containerContent, fragment, TAG_FRAGMENT).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        drawerLayout.closeDrawers();
    }

    protected void instanceViewFragment(Class showFragment, String title, boolean showNotificate, boolean showMore) {
        showMenu = showMore;
        setupToolbar(title);
        fragmentClass = showFragment;
        if (showNotificate) {
            imgNotification.setVisibility(View.VISIBLE);
        } else {
            imgNotification.setVisibility(View.GONE);
        }
    }

    protected void starLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personal_data_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_edit);
        item.getIcon().setColorFilter(context.getResources().getColor(R.color.colorDestakText), PorterDuff.Mode.SRC_IN);

        return showMenu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editMode = true;
                verifyEditPersonalData();
                return true;
            case R.id.menu_address:
                startMenuActivity(UserAddressActivity.class);
                return true;
            case R.id.menu_contact:
                startMenuActivity(UserContactActivity.class);
                return true;
            case R.id.menu_academic:
                startMenuActivity(UserAcademicDataActivity.class);
                return true;
            case R.id.menu_social_network:
                startMenuActivity(UserSocialNetworkActivity.class);
                return true;
            case R.id.menu_security:
                startMenuActivity(UserSecurityActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void verifyEditPersonalData() {
        PersonalDataFragment personalDataFragment = (PersonalDataFragment) getSupportFragmentManager().findFragmentByTag("PERSONAL_DATA");
        personalDataFragment.verifyModeEdit();
    }

    protected void startMenuActivity(Class activitStart) {
        startActivity(new Intent(context, activitStart));
    }
}
