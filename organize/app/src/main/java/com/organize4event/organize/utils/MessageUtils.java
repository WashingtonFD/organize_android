package com.organize4event.organize.utils;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.organize4event.organize.R;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.listeners.CustomDialogListener;

public class MessageUtils {

    public static void showDialogMessage(Context context, DialogTypeEnum type, String title, String message, final CustomDialogListener listener) {
        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(R.layout.custom_dialog, false).show();

        TextView dialog_title = (TextView) dialog.getCustomView().findViewById(R.id.txtTitle);
        TextView dialog_message = (TextView) dialog.getCustomView().findViewById(R.id.txtMessage);
        Button dialog_positive = (Button) dialog.getCustomView().findViewById(R.id.btnPositive);
        Button dialog_negative = (Button) dialog.getCustomView().findViewById(R.id.btnNegative);
        RelativeLayout divider = (RelativeLayout) dialog.getCustomView().findViewById(R.id.divider);

        dialog_title.setText(title);
        dialog_message.setText(message);

        if (type == DialogTypeEnum.POSITIVE_AND_NEGATIVE) {
            dialog_negative.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else if (type == DialogTypeEnum.CAMARA_AND_GALERY) {
            dialog_positive.setText(context.getString(R.string.label_galery));
            dialog_negative.setText(context.getString(R.string.label_camara));
            dialog_negative.setAlpha(1);
            dialog_negative.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else if (type == DialogTypeEnum.VALIDATE_EMAIL) {
            dialog_positive.setText("Ok");
            dialog_negative.setAlpha(1);
            dialog_negative.setText(context.getString(R.string.label_send_mail_validate));
            dialog_negative.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else {
            dialog_negative.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }


        dialog_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.positiveOnClick(dialog);
            }
        });

        dialog_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.negativeOnClick(dialog);
            }
        });
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
