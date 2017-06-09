package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.UserSecurity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserAddressActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        ButterKnife.bind(this);
        context = UserAddressActivity.this;

        configureToolbar(context, toolbar, context.getString(R.string.label_menu_address), context.getResources().getDrawable(R.drawable.ic_arrow_back), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.address_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_edit);
        item.getIcon().setColorFilter(context.getResources().getColor(R.color.colorDestakText), PorterDuff.Mode.SRC_IN);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                showToastMessage(context, "Menu editar");
                return true;
            case R.id.menu_personal_data:
                finish();
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

    protected void startMenuActivity(Class activitStart) {
        startActivity(new Intent(context, activitStart));
        finish();
    }
}
