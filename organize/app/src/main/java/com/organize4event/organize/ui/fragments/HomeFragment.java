package com.organize4event.organize.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment{
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "HOME");
        FirebaseAnalytics.getInstance(getActivity()).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = getActivity();
        getData();

        return view;
    }

    public void getData(){

    }
}
