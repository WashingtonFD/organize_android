package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.organize4event.organize.R;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

public class UserRegisterActivity extends BaseActivity implements Validator.ValidationListener{
    private Context context;
    private String message = "";
    private User user;

    Validator validator;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.contentImage)
    RelativeLayout contentImage;

    @Bind(R.id.imgProfile)
    ImageView imgProfile;

    @Bind(R.id.txtFullName)
    EditText txtFullName;

    @Bind(R.id.txtCpf)
    EditText txtCpf;

    @Bind(R.id.txtMail)
    EditText txtMail;

    @Bind(R.id.txtBirthDate)
    EditText txtBirthDate;

    @Bind(R.id.txtPassword)
    EditText txtPassword;

    @Bind(R.id.txtConfirmPassword)
    EditText txtConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        context = UserRegisterActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_register_user), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        validator = new Validator(context);
        validator.setValidationListener(this);
    }

    public void hideOrShowInfoIcon(EditText editText){
        if (editText.hasFocus()){
            editText.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_info_black_24dp_tint), null);
            //instanceInfo(editText);
        }
        else{
            editText.setCompoundDrawables(null, null, null, null);
        }
    }

    public void instanceInfo(final EditText editText){
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDialogMessage(context.getResources().getString(R.string.app_name), message);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @OnFocusChange({R.id.txtCpf, R.id.txtBirthDate, R.id.txtPassword, R.id.txtConfirmPassword})
    public void actionOnFocusChange(View view){
        switch (view.getId()){
            case R.id.txtCpf:
                message = context.getResources().getString(R.string.message_info_cpf);
                hideOrShowInfoIcon(txtCpf);
                break;
            case R.id.txtBirthDate:
                message = context.getResources().getString(R.string.message_info_birth_date);
                hideOrShowInfoIcon(txtBirthDate);
                break;
            case R.id.txtPassword:
                message = context.getResources().getString(R.string.message_info_password);
                hideOrShowInfoIcon(txtPassword);
                break;
            case R.id.txtConfirmPassword:
                message = context.getResources().getString(R.string.message_info_password);
                hideOrShowInfoIcon(txtConfirmPassword);
                break;
        }
    }


    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
