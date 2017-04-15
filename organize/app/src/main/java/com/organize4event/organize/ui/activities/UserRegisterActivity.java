package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.Mask;
import com.organize4event.organize.controllers.FirstAccessControll;
import com.organize4event.organize.controllers.PrivacyControll;
import com.organize4event.organize.controllers.SettingsControll;
import com.organize4event.organize.controllers.TermUseControll;
import com.organize4event.organize.controllers.UserControll;
import com.organize4event.organize.enuns.UserTypeEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.models.Setting;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserPrivacy;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.models.UserTerm;
import com.organize4event.organize.models.UserType;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class UserRegisterActivity extends BaseActivity implements Validator.ValidationListener{
    private Context context;
    private String message = "";
    private String title = "";
    private Date birthDate;
    private int term_accept = 0;
    private int checking = 0;

    private User user;
    private FirstAccess firstAccess;
    private UserTerm userTerm;
    private UserType userType;

    private ArrayList<Privacy> privacies = new ArrayList<>();
    private ArrayList<UserPrivacy> userPrivacies = new ArrayList<>();
    private UserPrivacy userPrivacy;
    private ArrayList<Setting> settings = new ArrayList<>();
    private ArrayList<UserSetting> userSettings = new ArrayList<>();
    private UserSetting userSetting;

    Validator validator;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.contentImage)
    RelativeLayout contentImage;

    @Bind(R.id.imgProfile)
    ImageView imgProfile;

    @Bind(R.id.rgpListGender)
    RadioGroup rgpListGender;

    @Order(1)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Bind(R.id.txtFullName)
    EditText txtFullName;

    //TODO: CRIAR VALIDAÇÃO DE CPF
    @Order(2)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Bind(R.id.txtCpf)
    EditText txtCpf;

    @Order(3)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Email(sequence = 2, messageResId = R.string.validate_mail)
    @Bind(R.id.txtMail)
    EditText txtMail;

    // TODO: CRIAR VALIDAÇÃO DE DATA
    @Order(4)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Bind(R.id.txtBirthDate)
    EditText txtBirthDate;

    @Order(5)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Password(min = 6, sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC, messageResId = R.string.validate_password)
    @Bind(R.id.txtPassword)
    EditText txtPassword;

    @Order(6)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @ConfirmPassword(sequence = 2, messageResId = R.string.validate_password_confirm)
    @Bind(R.id.txtPasswordConfirm)
    EditText txtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        context = UserRegisterActivity.this;
        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
        user = firstAccess.getUser();
        userTerm = user.getUser_term();


        configureToolbar(context, toolbar, context.getString(R.string.label_register_user), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        validator = new Validator(context);
        validator.setValidationListener(this);

        txtCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, txtCpf));
        txtBirthDate.addTextChangedListener(Mask.insert(Mask.DATE_MASK, txtBirthDate));

        selectGender();
    }

    protected void selectGender(){
        rgpListGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.btnGenderFem:
                        user.setGender("F");
                        break;
                    case R.id.btnGenderMasc:
                        user.setGender("M");
                        break;
                }
            }
        });
    }

    protected void getUserType(){
        showLoading();
        final int code_user_type = UserTypeEnum.DEFAULT.getValue();
        new UserControll(context).getUserType(firstAccess.getLocale(), code_user_type, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                userType = (UserType)object;
                prepareUser();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void prepareUser(){
        try {
            birthDate = format.parse(txtBirthDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setUser_type(userType);
        user.setFull_name(txtFullName.getText().toString());
        user.setMail(txtMail.getText().toString());
        user.setPassword(txtPassword.getText().toString());
        user.setCpf(txtCpf.getText().toString());
        user.setBirth_date(birthDate);

        saveUser();
    }

    protected void saveUser(){
        new UserControll(context).saveUser(user, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                user = (User)object;
                saveFirstAccess();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected  void saveFirstAccess(){
        firstAccess.setUser(user);
        new FirstAccessControll(context).saveFirstAccess(firstAccess, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                saveUserTerm();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });

    }

    protected void saveUserTerm(){
        userTerm.setUser(user.getId());

        if (userTerm.isTerm_accept()){
            term_accept = 1;
        }

        new TermUseControll(context).saveUserTerm(userTerm, term_accept, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                userTerm = (UserTerm) object;
                getPrivacy();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);

            }
        });
    }

    protected void getPrivacy(){
        new PrivacyControll(context).getPrivacy(firstAccess.getLocale(), new ControllResponseListener() {
            @Override
            public void success(Object object) {
                privacies = (ArrayList<Privacy>) object;
                if (privacies.size() > 0){
                    for (Privacy privacy : privacies){
                        saveUserPrivacy(privacy);
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void saveUserPrivacy(Privacy privacy){

        userPrivacy = new UserPrivacy();
        userPrivacy.setUser(user.getId());
        userPrivacy.setPrivacy(privacy);
        userPrivacy.setChecking(privacy.isCheck_default());

        if (userPrivacy.isChecking()){
            checking = 1;
        }

        new PrivacyControll(context).saveUserPrivacy(userPrivacy, checking, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                userPrivacy = (UserPrivacy)object;
                userPrivacies.add(userPrivacy);

                if (userPrivacies.size() == privacies.size()){
                    getSettings();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void getSettings(){
        new SettingsControll(context).getSettings(firstAccess.getLocale(), new ControllResponseListener() {
            @Override
            public void success(Object object) {
                settings = (ArrayList<Setting>) object;
                if (settings.size() > 0){
                    for(Setting setting : settings){
                        saveUserSetting(setting);
                    }
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error,context);
            }
        });
    }

    public void saveUserSetting(final Setting setting){

        userSetting = new UserSetting();
        userSetting.setUser(user.getId());
        userSetting.setSetting(setting);
        userSetting.setChecking(setting.isCheck_default());

        if (userSetting.isChecking()){
            checking = 1;
        }

        new SettingsControll(context).saveUserSettings(userSetting, checking, new ControllResponseListener() {
            @Override
            public void success(Object object) {
                userSetting = (UserSetting) object;
                userSettings.add(userSetting);

                if (userSettings.size() == settings.size()){
                    hideLoading();
                    starLoginActivity();
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    protected void starLoginActivity(){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }

    @OnFocusChange({R.id.txtCpf, R.id.txtBirthDate, R.id.txtPassword, R.id.txtPasswordConfirm})
    public void actionOnFocusChange(View view){
        title = context.getString(R.string.app_name);
        switch (view.getId()){
            case R.id.txtCpf:
                message = context.getString(R.string.message_info_cpf);
                hideOrShowInfoIcon(title, message, txtCpf);
                break;
            case R.id.txtBirthDate:
                message = context.getString(R.string.message_info_birth_date);
                hideOrShowInfoIcon(title, message, txtBirthDate);
                break;
            case R.id.txtPassword:
                message = context.getString(R.string.message_info_password);
                hideOrShowInfoIcon(title, message, txtPassword);
                break;
            case R.id.txtPasswordConfirm:
                message = context.getString(R.string.message_info_password);
                hideOrShowInfoIcon(title, message, txtPasswordConfirm);
                break;
        }
    }

    @OnClick(R.id.imgAccept)
    public void actionUserRegister(){
        validator.validate();
    }

    @OnClick(R.id.contentImage)
    public void actionUploadImage(){
        showDialogMessage(1, "Inserir imagem", "Fazer o Upload de Imagem", new CustomDialogListener() {
            @Override
            public void positiveOnClick(MaterialDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void negativeOnClidck(MaterialDialog dialog) {

            }
        });
        //TODO: IMPLEMENTAR UPLOAD IMAGEM
    }

    @Override
    public void onValidationSucceeded() {
        getUserType();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validateError(errors);
    }

}
