package com.organize4event.organize.ui.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.organize4event.organize.ui.activities.BaseActivity;

public class BaseFragment extends Fragment {

    public void showLoading(){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.showLoading();
    }

    public void hideLoading(){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.hideLoading();
    }

    public void hideKeyboard(Context context, View view){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.hideKeyboard(context, view);
    }

    public boolean isOline(Context context){
        BaseActivity activity = (BaseActivity) getActivity();
        return activity.isOline(context);
    }

    public void selectDate(Context context, EditText editText, int mode){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.selectDate(context, editText, mode);
    }

    public void showDialogMessage(String title, String message){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.showDialogMessage(title, message);
    }

    public void showToastMessage(Context context, String message){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.showToastMessage(context, message);
    }

    public void returnErrorMessage(Error error, Context context){
        BaseActivity activity = (BaseActivity) getActivity();
        activity.returnErrorMessage(error, context);
    }

}
