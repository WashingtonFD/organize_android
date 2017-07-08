package com.organize4event.organize.utils;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.organize4event.organize.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static void selectDate(Context context, final EditText editText, int mode) {
        final SimpleDateFormat simpleDateFormat;
        final DatePickerDialog startDatePickerDialog;
        Calendar birthCalendarStart;
        Calendar birthCalendarFinish;
        final Calendar startCalendar;
        Calendar finishCalendar;

        simpleDateFormat = new SimpleDateFormat(context.getString(R.string.date_format));

        birthCalendarStart = GregorianCalendar.getInstance();
        birthCalendarFinish = GregorianCalendar.getInstance();
        startCalendar = GregorianCalendar.getInstance();
        finishCalendar = GregorianCalendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(context, null, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

        finishCalendar.add(Calendar.MONTH, 6);
        birthCalendarStart.add(Calendar.YEAR, -100);
        birthCalendarFinish.add(Calendar.YEAR, -10);

        switch (mode) {
            case 1:
                startDatePickerDialog.getDatePicker().setMinDate(birthCalendarStart.getTimeInMillis());
                startDatePickerDialog.getDatePicker().setMaxDate(birthCalendarFinish.getTimeInMillis());

                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCalendar.set(startDatePickerDialog.getDatePicker().getYear(), startDatePickerDialog.getDatePicker().getMonth(), startDatePickerDialog.getDatePicker().getDayOfMonth());
                        editText.setText(simpleDateFormat.format(startCalendar.getTime()));
                    }
                });
                startDatePickerDialog.setTitle(context.getString(R.string.label_birth_date));
                startDatePickerDialog.show();
                break;

            case 2:
                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

                startDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCalendar.set(startDatePickerDialog.getDatePicker().getYear(), startDatePickerDialog.getDatePicker().getMonth(), startDatePickerDialog.getDatePicker().getDayOfMonth());
                        editText.setText(simpleDateFormat.format(startCalendar.getTime()));
                    }
                });
                startDatePickerDialog.setTitle(context.getString(R.string.label_final_date));
                startDatePickerDialog.show();

                break;
        }
    }

}
