package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
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
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.Mask;
import com.organize4event.organize.controllers.UserControll;
import com.organize4event.organize.enuns.UserTypeEnum;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class UserRegisterActivity extends BaseActivity implements Validator.ValidationListener{
    private Context context;
    private String message = "";
    private Date birthDate;

    private User user;
    private FirstAccess firstAccess;
    private UserType userType;

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

    //TODO: INSERIR GENERO NO CADASTRO DE USUÁRIO


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        context = UserRegisterActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));
        firstAccess = AppApplication.getFirstAccess();

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

    public void hideOrShowInfoIcon(EditText editText){
        if (editText.hasFocus()){
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_black_24dp_tint, 0);
            instanceInfo(editText);
        }
        else{
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.img_transparent_small, 0);
        }
    }

    public void instanceInfo(final EditText editText){
        if (editText.hasFocus()){
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            showDialogMessage(1, context.getString(R.string.app_name), message, new CustomDialogListener() {
                                @Override
                                public void positiveOnClick(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void negativeOnClidck(MaterialDialog dialog) {

                                }
                            });
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void selectGender(){
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

    public void saveUser(){
        try {
            birthDate = format.parse(txtBirthDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int term_accept = 0;
        if (user.isTerm_accept()){
            term_accept = 1;
        }

        user.setUser_type(userType);
        user.setFull_name(txtFullName.getText().toString());
        user.setCpf(txtCpf.getText().toString());
        user.setMail(txtMail.getText().toString());
        user.setBirth_date(birthDate);
        user.setPassword(txtPassword.getText().toString());

        new UserControll(context).saveUser(user, term_accept, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                user = (User) object;
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnFocusChange({R.id.txtCpf, R.id.txtBirthDate, R.id.txtPassword, R.id.txtPasswordConfirm})
    public void actionOnFocusChange(View view){
        switch (view.getId()){
            case R.id.txtCpf:
                message = context.getString(R.string.message_info_cpf);
                hideOrShowInfoIcon(txtCpf);
                break;
            case R.id.txtBirthDate:
                message = context.getString(R.string.message_info_birth_date);
                hideOrShowInfoIcon(txtBirthDate);
                break;
            case R.id.txtPassword:
                message = context.getString(R.string.message_info_password);
                hideOrShowInfoIcon(txtPassword);
                break;
            case R.id.txtPasswordConfirm:
                message = context.getString(R.string.message_info_password);
                hideOrShowInfoIcon(txtPasswordConfirm);
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
        int code_enum = UserTypeEnum.DEFAULT.getValue();
        new UserControll(context).getUserType(firstAccess.getLocale(), code_enum, new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                userType = (UserType) object;
                saveUser();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validateError(errors);
    }
}
