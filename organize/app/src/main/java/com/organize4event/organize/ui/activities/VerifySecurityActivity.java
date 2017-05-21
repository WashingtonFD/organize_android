package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.organize4event.organize.R;
import com.organize4event.organize.controlers.UserSecurityControler;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.ErrorReturn;
import com.organize4event.organize.models.SecurityQuestion;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifySecurityActivity extends BaseActivity {
    private static final int RESULT_CODE_SELECT_SECURITYQUESTION = 1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.selectSecurityQuestion)
    EditText selectSecurityQuestion;
    @Bind(R.id.txtAnswer)
    EditText txtAnswer;
    @Bind(R.id.btnSend)
    Button btnSend;
    private Context context;
    private User user;
    private SecurityQuestion securityQuestionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_security);
        ButterKnife.bind(this);

        context = VerifySecurityActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));

        configureToolbar(context, toolbar, context.getString(R.string.label_verify_security), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        txtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtAnswer.getText().toString().trim().length() > 3) {
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.selectSecurityQuestion)
    public void actionSelectQuestion() {
        Intent intent = new Intent(context, SelectorSecurityQuestionActivity.class);
        intent.putExtra("user_id", user.getId());
        intent.putExtra("securityQuestion", Parcels.wrap(SecurityQuestion.class, securityQuestionSelected));
        startActivityForResult(intent, RESULT_CODE_SELECT_SECURITYQUESTION);
    }

    @OnClick(R.id.btnSend)
    public void actionSend(){
        String mail = user.getMail();
        int user_security_id = securityQuestionSelected.getId();
        String user_answer = txtAnswer.getText().toString();

        showLoading();
        new UserSecurityControler(context).sendMail(mail, user_security_id, user_answer, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                ErrorReturn errorReturn = (ErrorReturn)object;
                if (errorReturn.getCode() == 0){
                    showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.message_success_recovery_password), new CustomDialogListener() {
                        @Override
                        public void positiveOnClick(MaterialDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }

                        @Override
                        public void negativeOnClick(MaterialDialog dialog) {

                        }
                    });
                }
                else{
                    showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), errorReturn.getException(), new CustomDialogListener() {
                        @Override
                        public void positiveOnClick(MaterialDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }

                        @Override
                        public void negativeOnClick(MaterialDialog dialog) {

                        }
                    });
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnClick(R.id.txtIsNotSecurityData)
    public void actionIsNotSecurityData(){
        showDialogMessage(DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.message_not_security_data), new CustomDialogListener() {
            @Override
            public void positiveOnClick(MaterialDialog dialog) {
                dialog.dismiss();
                finish();
            }

            @Override
            public void negativeOnClick(MaterialDialog dialog) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_SELECT_SECURITYQUESTION) {
            securityQuestionSelected = Parcels.unwrap(data.getExtras().getParcelable("securityQuestion"));;
            selectSecurityQuestion.setText(securityQuestionSelected.getSecurity_question());
        }
    }
}
