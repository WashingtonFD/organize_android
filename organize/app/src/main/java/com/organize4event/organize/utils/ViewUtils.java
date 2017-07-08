package com.organize4event.organize.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.organize4event.organize.R;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.listeners.CustomDialogListener;

public class ViewUtils {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isOline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void hideOrShowInfoIcon(Context context, String title, String message, EditText editText) {
        if (editText.hasFocus()) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_tint, 0);
            instanceInfo(context, title, message, editText);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.img_transparent_small, 0);
        }
    }

    private static void instanceInfo(final Context context, final String title, final String message, final EditText editText) {
        if (editText.hasFocus()) {
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, title, message, new CustomDialogListener() {
                                @Override
                                public void positiveOnClick(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void negativeOnClick(MaterialDialog dialog) {

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
}
