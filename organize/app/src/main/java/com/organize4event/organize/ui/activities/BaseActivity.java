package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.WaitDialog;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.utils.MessageUtils;
import com.organize4event.organize.utils.ViewUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    private WaitDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void showLoading() {
        hideLoading();

        if (waitDialog == null) {
            waitDialog = WaitDialog.newInstance();
            waitDialog.show(getFragmentManager(), waitDialog.getTag());
        }
    }

    public void hideLoading() {

        if (waitDialog != null) {
            try {
                waitDialog.dismissAllowingStateLoss();
                waitDialog = null;
            } catch (Exception ex) {
                Log.v("Error: ", ex.getMessage());
            }
        }
    }

    public void configureToolbar(Context context, Toolbar toolbar, String title, Drawable icon, boolean isHomeAsUpEnabled, final ToolbarListener listener) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.colorDestakText));
        icon.setColorFilter(context.getResources().getColor(R.color.colorDestakText), PorterDuff.Mode.SRC_IN);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (isHomeAsUpEnabled) {
                actionBar.setHomeAsUpIndicator(icon);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick();
                }
            });
        }
    }

    public void showErrorMessage(final Context context, Error error) {
        hideLoading();
        if (ViewUtils.isOline(context)) {
            MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.error_title), error.getMessage(), new CustomDialogListener() {
                @Override
                public void positiveOnClick(MaterialDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void negativeOnClick(MaterialDialog dialog) {

                }
            });
        } else {
            MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.error_title), context.getString(R.string.error_message_conect), new CustomDialogListener() {
                @Override
                public void positiveOnClick(MaterialDialog dialog) {
                    dialog.dismiss();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    onResume();
                }

                @Override
                public void negativeOnClick(MaterialDialog dialog) {

                }
            });
        }
    }
}
