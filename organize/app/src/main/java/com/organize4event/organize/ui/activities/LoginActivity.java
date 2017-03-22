package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controllers.FirstAccessControll;
import com.organize4event.organize.controllers.TokenControll;
import com.organize4event.organize.enuns.AccessPlatformEnum;
import com.organize4event.organize.enuns.LoginTypeEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener{
    private Context context;
    private int code_enum;
    private int code_enum_platform;
    private boolean keep_logged = false;
    private int keep_logged_int = 0;

    private Validator validator;

    private User user;
    private FirstAccess firstAccess;
    private Token token;
    private LoginType loginType;
    private AccessPlatform accessPlatform;

    @Bind(R.id.containerLoginEmail)
    RelativeLayout containerLoginEmail;

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

    @Bind(R.id.cbxKeepLogged)
    CheckBox cbxKeepLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = LoginActivity.this;
        code_enum_platform = AccessPlatformEnum.ANDROID.getValue();

        user = AppApplication.getUser();
        firstAccess = AppApplication.getFirstAccess();
        token = AppApplication.getToken();

        if (token == null){
            token = new Token();
        }
        else if(token.isKeep_logged()){
            startActivity(new Intent(context, WelcomeActivity.class));
        }

        validator = new Validator(context);
        validator.setValidationListener(this);
    }

    @OnClick({R.id.btnLoginEmail, R.id.btnLoginFacebook, R.id.txtForgotPassword})
    public void actionOnClickLogin(View view){
        switch (view.getId()){
            case R.id.btnLoginEmail:
                code_enum = LoginTypeEnum.EMAIL.getValue();
                getAccessPlatform();
                break;
            case R.id.btnLoginFacebook:
                code_enum = LoginTypeEnum.FACEBOOK.getValue();
                getAccessPlatform();
                break;
            case R.id.txtForgotPassword:
                forgotPassword();
                break;
        }
    }

    @OnCheckedChanged(R.id.cbxKeepLogged)
    public void actionKeepLogged(){
        keep_logged = cbxKeepLogged.isChecked();
        if (keep_logged){
            keep_logged_int = 1;
        }
    }

    @OnClick(R.id.btnLogin)
    public void actionLogin(){
        validator.validate();
    }

    public void loginEmail(){
        hideLoading();
        containerLoginEmail.setVisibility(View.VISIBLE);
        token.setFirstAccess(firstAccess);
        token.setLogin_type(loginType);
        token.setAccess_platform(accessPlatform);
        token.setAccess_date(new Date());
        token.setKeep_logged(keep_logged);
    }

    public void login(){
        showLoading();
        new TokenControll(context).login(txtMail.getText().toString(), txtPassword.getText().toString(), new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                user = (User) object;
                token.setUser(user);
                saveToken();
            }

            @Override
            public void fail(Error error) {
                hideLoading();
                containerLoginEmail.setVisibility(View.GONE);
                returnErrorMessage(error, context);
            }
        });
    }

    public void saveToken(){
        new TokenControll(context).saveToken(token, keep_logged_int, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                hideLoading();
                token = (Token) object;
                startActivity(new Intent(context, WelcomeActivity.class));
                finish();
            }

            @Override
            public void fail(Error error) {
                hideLoading();
                containerLoginEmail.setVisibility(View.GONE);
                returnErrorMessage(error, context);
            }
        });
    }

    public void loginFacebook(){
        hideLoading();
        //TODO: IMPLEMENTAR LOGIN COM FACEBOOK
    }

    public void forgotPassword(){
        showDialogMessage(context, "", "Esqueci minha senha");
        // TODO: IMPLEMENTAR ESQUECI SENHA
    }

    public void getAccessPlatform(){
        showLoading();
        new FirstAccessControll(context).getAccessPlatform(firstAccess.getLocale(), code_enum_platform, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                accessPlatform = (AccessPlatform) object;
                getLoginType();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void getLoginType(){
        new TokenControll(context).getLoginType(firstAccess.getLocale(), code_enum, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                loginType = (LoginType) object;
                if (loginType.getCode_enum() == LoginTypeEnum.EMAIL.getValue()){
                    loginEmail();
                }
                else if (loginType.getCode_enum() == LoginTypeEnum.FACEBOOK.getValue()){
                    loginFacebook();
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
}
