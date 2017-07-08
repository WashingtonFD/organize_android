package com.organize4event.organize.utils;


import android.content.Context;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.organize4event.organize.R;
import com.organize4event.organize.controlers.UserControler;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;

import java.util.List;

public class ValidateUtils {

    public static void validateError(Context context, List<ValidationError> errors) {
        ValidationError currentError = errors.get(0);
        EditText editTextError = (EditText) currentError.getView();
        editTextError.requestFocus();
        editTextError.setError(currentError.getCollatedErrorMessage(context));
    }

    public static void sendValidateMail(final Context context, String mail) {
        new UserControler(context).validateUser(mail, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                return;
            }

            @Override
            public void fail(Error error) {
                MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.error_title), context.getString(R.string.error_send_mail_validate), new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void negativeOnClick(MaterialDialog dialog) {

                    }
                });
            }
        });
    }

}
