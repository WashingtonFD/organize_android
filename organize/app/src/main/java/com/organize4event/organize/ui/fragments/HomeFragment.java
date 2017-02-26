package com.organize4event.organize.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organize4event.organize.R;

/**
 * Created by marcelamelo on 25/02/17.
 */

public class HomeFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}
