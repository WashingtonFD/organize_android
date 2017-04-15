package com.organize4event.organize.ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.WaitDialog;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity  extends AppCompatActivity{
    private WaitDialog waitDialog;

    SimpleDateFormat simpleDateFormat;
    DatePickerDialog startDatePickerDialog;
    Calendar birthCalendarStart;
    Calendar birthCalendarFinish;
    Calendar startCalendar;
    Calendar finishCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void showLoading(){
        hideLoading();

        if(waitDialog == null){
            waitDialog = WaitDialog.newInstance();
            waitDialog.show(getFragmentManager(), waitDialog.getTag());
        }
    }

    public void hideLoading(){

        if (waitDialog != null){
            try{
                waitDialog.dismissAllowingStateLoss();
                waitDialog = null;
            }
            catch (Exception ex){
                Log.v("Error: ", ex.getMessage());
            }
        }
    }

    public void configureToolbar(Context context, Toolbar toolbar, String title, Drawable icon, boolean isHomeAsUpEnabled, final ToolbarListener listener){
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.colorDestakText));
        icon.setColorFilter(context.getResources().getColor(R.color.colorDestakText), PorterDuff.Mode.SRC_IN);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
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

    public void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isOline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }

    public void showDialogMessage(int type, String title, String message, final CustomDialogListener listener){
        final MaterialDialog dialog = new MaterialDialog.Builder(this).customView(R.layout.custom_dialog, false).show();

        TextView dialog_title = (TextView) dialog.getCustomView().findViewById(R.id.txtTitle);
        TextView dialog_message = (TextView) dialog.getCustomView().findViewById(R.id.txtMessage);
        Button dialog_positive = (Button) dialog.getCustomView().findViewById(R.id.btnPositive);
        Button dialog_negative = (Button) dialog.getCustomView().findViewById(R.id.btnNegative);
        RelativeLayout divider = (RelativeLayout) dialog.getCustomView().findViewById(R.id.divider);

        dialog_title.setText(title);
        dialog_message.setText(message);

        if(type != 1){
            dialog_negative.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }
        else{
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
                listener.negativeOnClidck(dialog);
            }
        });
    }

    public void showToastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void selectDate(Context context, final EditText editText, int mode){
        simpleDateFormat = new SimpleDateFormat(context.getString(R.string.date_format));

        birthCalendarStart = GregorianCalendar.getInstance();
        birthCalendarFinish = GregorianCalendar.getInstance();
        startCalendar = GregorianCalendar.getInstance();
        finishCalendar = GregorianCalendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(context, null, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        finishCalendar.add(Calendar.MONTH, 6);
        birthCalendarStart.add(Calendar.YEAR, -100);
        birthCalendarFinish.add(Calendar.YEAR, -10);

        switch (mode){
            case 1:
                startDatePickerDialog.getDatePicker().setMinDate(birthCalendarStart.getTimeInMillis());
                startDatePickerDialog.getDatePicker().setMaxDate(birthCalendarFinish.getTimeInMillis());

                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startCalendar.set(startDatePickerDialog.getDatePicker().getYear(), startDatePickerDialog.getDatePicker().getMonth(), startDatePickerDialog.getDatePicker().getDayOfMonth());
                        editText.setText(simpleDateFormat.format(startCalendar.getTime()));
                    }
                });
                startDatePickerDialog.setTitle(context.getString(R.string.label_birth_date));
                startDatePickerDialog.show();
                break;

            case 2:
                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startCalendar.set(startDatePickerDialog.getDatePicker().getYear(), startDatePickerDialog.getDatePicker().getMonth(), startDatePickerDialog.getDatePicker().getDayOfMonth());
                        editText.setText(simpleDateFormat.format(startCalendar.getTime()));
                    }
                });
                startDatePickerDialog.setTitle(context.getString(R.string.label_start_date));
                startDatePickerDialog.show();

                break;

            case 3:
                startDatePickerDialog.getDatePicker().setMinDate(startCalendar.getTimeInMillis());
                startDatePickerDialog.getDatePicker().setMaxDate(finishCalendar.getTimeInMillis());

                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startCalendar.set(startDatePickerDialog.getDatePicker().getYear(), startDatePickerDialog.getDatePicker().getMonth(), startDatePickerDialog.getDatePicker().getDayOfMonth());
                        editText.setText(simpleDateFormat.format(startCalendar.getTime()));
                    }
                });
                startDatePickerDialog.setTitle(context.getString(R.string.label_final_date));
                startDatePickerDialog.show();

                break;
        }
    }

    public void returnErrorMessage(Error error, Context context){
        hideLoading();
        if(isOline(context)){
            showDialogMessage(1, context.getString(R.string.error_title), error.getMessage(), new CustomDialogListener() {
                @Override
                public void positiveOnClick(MaterialDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void negativeOnClidck(MaterialDialog dialog) {

                }
            });
        }
        else {
            showDialogMessage(1, context.getString(R.string.error_title), context.getString(R.string.error_message_conect), new CustomDialogListener() {
                @Override
                public void positiveOnClick(MaterialDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void negativeOnClidck(MaterialDialog dialog) {

                }
            });
        }
    }

    public void validateError(List<ValidationError> errors){
        ValidationError currentError = errors.get(0);
        EditText editTextError = (EditText)currentError.getView();
        editTextError.requestFocus();
        editTextError.setError(currentError.getCollatedErrorMessage(this));
    }

    public void hideOrShowInfoIcon(String title, String message, EditText editText){
        if (editText.hasFocus()){
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_info_black_24dp_tint, 0);
            instanceInfo(title, message, editText);
        }
        else{
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.img_transparent_small, 0);
        }
    }

    public void instanceInfo(final String title, final String message, final EditText editText){
        if (editText.hasFocus()){
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            showDialogMessage(1, title, message, new CustomDialogListener() {
                                @Override
                                public void positiveOnClick(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void negativeOnClidck(MaterialDialog dialog) {

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
