package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.CustomValidate;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controlers.FirstAccessControler;
import com.organize4event.organize.controlers.TokenControler;
import com.organize4event.organize.controlers.UserControler;
import com.organize4event.organize.enuns.AccessPlatformEnum;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.enuns.LoginTypeEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSecurity;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener {
    @Bind(R.id.containerLoginEmail)
    RelativeLayout containerLoginEmail;
    @Bind(R.id.containerForgotPassword)
    RelativeLayout containerForgotPassword;
    @Order(1)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Email(sequence = 2, messageResId = R.string.validate_mail)
    @Bind(R.id.txtMail)
    EditText txtMail;
    @Order(2)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Password(min = 6, sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC, messageResId = R.string.validate_password)
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Bind(R.id.txtMailForgotPassword)
    EditText txtMailForgotPassword;
    @Bind(R.id.cbxKeepLogged)
    CheckBox cbxKeepLogged;

    @Bind(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @Bind(R.id.btnLoginEmail)
    Button btnLoginEmail;
    @Bind(R.id.btnLoginFacebook)
    Button btnLoginFacebook;

    private Context context;
    private int code_enum;
    private int code_enum_platform;
    private boolean keep_logged = false;
    private int keep_logged_int = 0;
    private Validator validator;
    private CustomValidate customValidate;
    private User user;
    private FirstAccess firstAccess;
    private Token token;
    private LoginType loginType;
    private AccessPlatform accessPlatform;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LOGIN");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = LoginActivity.this;
        code_enum_platform = AccessPlatformEnum.ANDROID.getValue();

        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
        user = firstAccess.getUser();
        token = user.getToken();

        if (token == null) {
            token = new Token();
        } else if (token.isKeep_logged()) {
            if (PreferencesManager.isHideWelcome()) {
                startActivity(new Intent(context, HomeActivity.class));
            } else {
                starWelcomeActivity();
            }
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        validator = new Validator(context);
        validator.setValidationListener(this);
    }

    @OnClick({R.id.btnLoginEmail, R.id.btnLoginFacebook, R.id.txtForgotPassword})
    public void actionOnClickLogin(View view) {
        switch (view.getId()) {
            case R.id.btnLoginEmail:
                btnLoginFacebook.setEnabled(false);
                txtForgotPassword.setEnabled(false);
                code_enum = LoginTypeEnum.EMAIL.getValue();
                getAccessPlatform();
                break;
            case R.id.btnLoginFacebook:
                btnLoginEmail.setEnabled(false);
                txtForgotPassword.setEnabled(false);
                code_enum = LoginTypeEnum.FACEBOOK.getValue();
                getAccessPlatform();
                break;
            case R.id.txtForgotPassword:
                btnLoginFacebook.setEnabled(false);
                btnLoginEmail.setEnabled(false);
                containerForgotPassword.setVisibility(View.VISIBLE);
                break;
            default:
                btnLoginFacebook.setEnabled(true);
                btnLoginEmail.setEnabled(true);
                txtForgotPassword.setEnabled(true);
        }
    }

    @OnCheckedChanged(R.id.cbxKeepLogged)
    public void actionKeepLogged() {
        keep_logged = cbxKeepLogged.isChecked();
        if (keep_logged) {
            keep_logged_int = 1;
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnCancel, R.id.btnForgotPasswordSend, R.id.btnForgotPasswordCancel, R.id.txtIsNotRegistered})
    public void actionButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                validator.validate();
                break;
            case R.id.btnCancel:
                containerLoginEmail.setVisibility(View.GONE);
                btnLoginFacebook.setEnabled(true);
                btnLoginEmail.setEnabled(true);
                txtForgotPassword.setEnabled(true);
                break;
            case R.id.btnForgotPasswordSend:
                forgotPassword();
                break;
            case R.id.btnForgotPasswordCancel:
                containerForgotPassword.setVisibility(View.GONE);
                btnLoginFacebook.setEnabled(true);
                btnLoginEmail.setEnabled(true);
                txtForgotPassword.setEnabled(true);
                break;
            case R.id.txtIsNotRegistered:
                starApresentationActivity();
                break;
        }
    }

    protected void loginEmail() {
        hideLoading();
        containerLoginEmail.setVisibility(View.VISIBLE);
    }

    protected void login() {
        showLoading();
        new TokenControler(context).login(txtMail.getText().toString(), txtPassword.getText().toString(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    hideLoading();
                    user = (User) object;
                    updateFirstAccess();
                }
            }

            @Override
            public void fail(Error error) {
                containerLoginEmail.setVisibility(View.GONE);
                showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), error.getMessage(), new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                        containerLoginEmail.setVisibility(View.VISIBLE);
                        hideLoading();
                    }

                    @Override
                    public void negativeOnClick(MaterialDialog dialog) {

                    }
                });
            }
        });
    }

    protected void saveToken() {
        token.setLogin_type(loginType);
        token.setAccess_platform(accessPlatform);
        token.setAccess_date(new Date());
        token.setKeep_logged(keep_logged);
        new TokenControler(context).saveToken(token, user.getId(), keep_logged_int, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                hideLoading();
                if (object != null) {
                    insertNotification(context, user.getId(), context.getString(R.string.notification_login_brief_description), context.getString(R.string.notification_login_description), new Date());

                    if (PreferencesManager.isHideWelcome()) {
                        startActivity(new Intent(context, HomeActivity.class));
                        finish();
                    } else {
                        starWelcomeActivity();
                    }
                }
            }

            @Override
            public void fail(Error error) {
                containerLoginEmail.setVisibility(View.GONE);
                returnErrorMessage(error, context);
            }
        });
    }

    protected void loginFacebook() {
        if (user.getId() > 0) {
            hideLoading();
            loginManager = LoginManager.getInstance();
            loginManager.logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    accessToken = loginResult.getAccessToken();
                    getUserFacebook();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    returnErrorMessage(new Error(error.getMessage()), context);
                }
            });
        } else {
            hideLoading();
            showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.message_not_facebook_login), new CustomDialogListener() {
                @Override
                public void positiveOnClick(MaterialDialog dialog) {
                    btnLoginEmail.setEnabled(true);
                    dialog.dismiss();
                }

                @Override
                public void negativeOnClick(MaterialDialog dialog) {

                }
            });
        }
    }

    protected void getUserFacebook() {
        showLoading();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String id = object.getString("id");
                    String picture_profile = "https://graph.facebook.com/" + id + "/picture?height=220&width=220";

                    User userFacebook = new User();

                    userFacebook.setFull_name(object.getString("name"));
                    userFacebook.setMail(object.getString("email"));
                    userFacebook.setProfile_picture(picture_profile);
                    getUserMail(userFacebook);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected void getUserMail(final User userFacebook) {
        new UserControler(context).getUserMail(userFacebook.getMail(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    User findUser = (User) object;
                    findUser.setFull_name(userFacebook.getFull_name());
                    findUser.setProfile_picture(userFacebook.getProfile_picture());

                    updateFacebookUser(findUser);
                }
            }

            @Override
            public void fail(Error error) {

                user.setFull_name(userFacebook.getFull_name());
                user.setMail(userFacebook.getMail());
                user.setProfile_picture(userFacebook.getProfile_picture());

                updateFacebookUser(user);
            }
        });
    }

    protected void updateFacebookUser(User findUser) {
        new UserControler(context).updateUserFacebook(findUser, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    user = (User) object;
                    if (user.getId() == firstAccess.getUser().getId()) {
                        saveToken();
                    } else {
                        updateFirstAccess();
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void updateFirstAccess() {
        firstAccess.setUser(user);
        new FirstAccessControler(context).updateUserFirstAccess(firstAccess, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    firstAccess = (FirstAccess) object;
                    PreferencesManager.saveFirstAccess(firstAccess);
                    AppApplication.setFirstAccess(firstAccess);

                    saveToken();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void forgotPassword() {
        customValidate = new CustomValidate();
        if (!customValidate.validadeEmail(txtMailForgotPassword.getText().toString())) {
            txtMailForgotPassword.setError(context.getString(R.string.validate_mail));
        } else {
            showLoading();
            new UserControler(context).getUserMail(txtMailForgotPassword.getText().toString().trim(), new ControlResponseListener() {
                @Override
                public void success(Object object) {
                    if (object != null) {
                        hideLoading();
                        User findUser = (User) object;
                        UserSecurity security = findUser.getUser_security();
                        if (security == null) {
                            showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.message_error_recovery_password), new CustomDialogListener() {
                                @Override
                                public void positiveOnClick(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void negativeOnClick(MaterialDialog dialog) {

                                }
                            });
                        } else {
                            containerForgotPassword.setVisibility(View.GONE);
                            starVerifySecurityActivity(findUser);
                        }
                    }
                }

                @Override
                public void fail(Error error) {
                    returnErrorMessage(error, context);
                }
            });
        }
    }

    protected void getAccessPlatform() {
        showLoading();
        new FirstAccessControler(context).getAccessPlatform(firstAccess.getLocale(), code_enum_platform, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    accessPlatform = (AccessPlatform) object;
                    getLoginType();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void getLoginType() {
        new TokenControler(context).getLoginType(firstAccess.getLocale(), code_enum, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    loginType = (LoginType) object;
                    if (loginType.getCode_enum() == LoginTypeEnum.EMAIL.getValue()) {
                        loginEmail();
                    } else if (loginType.getCode_enum() == LoginTypeEnum.FACEBOOK.getValue()) {
                        loginFacebook();
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        login();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validateError(errors);
    }

    protected void starWelcomeActivity() {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starApresentationActivity() {
        Intent intent = new Intent(context, ApresentationActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    protected void starVerifySecurityActivity(User findUser) {
        Intent intent = new Intent(context, VerifySecurityActivity.class);
        intent.putExtra("user", Parcels.wrap(FirstAccess.class, findUser));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
