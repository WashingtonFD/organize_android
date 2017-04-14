package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.controllers.TermUseControll;
import com.organize4event.organize.listeners.ControllResponseListener;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.TermUse;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserTerm;

import org.parceler.Parcels;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermUseActivity extends BaseActivity {
    private Context context;
    private TermUse termUse;
    private User user;
    private FirstAccess firstAccess;
    private UserTerm userTerm;

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
        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
        user = firstAccess.getUser();

        getTermUse();
    }

    protected void getTermUse(){
        new TermUseControll(context).getTermUse(new ControllResponseListener() {

            @Override
            public void sucess(Object object) {
                termUse = (TermUse)object;
                if (!termUse.is_new()) {
                    userTerm.setTerm(termUse);
                }
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    @OnClick(R.id.imgAccept)
    public void acceptTermAction(){
        userTerm.setTerm_accept(true);
        userTerm.setTerm_accept_date(new Date());

        user.setUser_term(userTerm);
        firstAccess.setUser(user);
        PreferencesManager.saveFirstAccess(firstAccess);

        startPlanIdentifierActivity();
    }

    protected void startPlanIdentifierActivity(){
        Intent intent = new Intent(context, PlanIdentifierActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }
}
