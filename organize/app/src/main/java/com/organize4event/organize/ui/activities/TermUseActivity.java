package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controllers.TermUseControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.TermUse;
import com.organize4event.organize.models.User;

import org.parceler.Parcels;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermUseActivity extends BaseActivity {
    private Context context;
    private TermUse termUse;
    private User user;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.txtContent)
    TextView txtContent;

    @Bind(R.id.imgAccept)
    ImageView imgAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_use);
        ButterKnife.bind(this);

        context = TermUseActivity.this;
        user = AppApplication.getUser();

        if (user == null){
            user = new User();
        }

        boolean logged = PreferencesManager.isLogged();

        if (PreferencesManager.isLogged() && user.isTerm_accept()){
            imgAccept.setVisibility(View.GONE);
        }
        else{
            imgAccept.setVisibility(View.VISIBLE);
        }

        configureToolbar(context, toolbar, context.getString(R.string.label_term_use), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        getData();
    }

    public void getData(){
        showLoading();
        new TermUseControll(context).getTermUse(new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                hideLoading();
                termUse = (TermUse) object;
                txtTitle.setText(termUse.getTitle());
                txtContent.setText(termUse.getContent());
            }

            @Override
            public void fail(Error error) {
               if (isOline(context)){
                   hideLoading();
                   showToastMessage(context, error.getMessage());
               }
                else{
                   hideLoading();
                   showToastMessage(context, context.getString(R.string.error_message_conect));
               }
            }
        });
    }

    public void acceptTerm(){
        user.setTerm(termUse);
        user.setTerm_accept(true);
        user.setTerm_accept_date(new Date());

        Intent intent = new Intent(context, PlanIdentifierActivity.class);
        intent.putExtra("user", Parcels.wrap(User.class, user));
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.imgAccept)
    public void actionTermAccept(){
        acceptTerm();
    }
}
