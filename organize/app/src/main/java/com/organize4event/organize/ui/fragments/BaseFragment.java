package com.organize4event.organize.ui.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.organize4event.organize.ui.activities.BaseActivity;

public class BaseFragment extends Fragment {

    public void showLoading() {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.showLoading();
    }

    public void hideLoading() {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.hideLoading();
    }

    public void showErrorMessage(Context context, Error error) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.showErrorMessage(context, error);
    }
}
