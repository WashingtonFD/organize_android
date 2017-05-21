package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.SecurityQuestion;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSecurity;

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
    private UserSecurity userSecurity;
    private SecurityQuestion securityQuestionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_security);
        ButterKnife.bind(this);

        context = VerifySecurityActivity.this;
        user = Parcels.unwrap(getIntent().getExtras().getParcelable("user"));
        userSecurity = user.getUser_security();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_CODE_SELECT_SECURITYQUESTION) {
            securityQuestionSelected = (SecurityQuestion) data.getParcelableExtra("securityQuestion");
            selectSecurityQuestion.setText(securityQuestionSelected.getSecurity_question());
        }
    }
}
