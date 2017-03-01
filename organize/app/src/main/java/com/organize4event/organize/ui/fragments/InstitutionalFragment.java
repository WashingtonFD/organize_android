package com.organize4event.organize.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organize4event.organize.R;
import com.organize4event.organize.ui.activities.PlanDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marcelamelo on 28/02/17.
 */

public class InstitutionalFragment extends BaseFragment {
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_institutional, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        getData();

        return view;
    }

    @OnClick(R.id.detalhedoplano)
    public void actionDetalhe(){
        startActivity(new Intent(context, PlanDetailActivity.class));
    }

    public void getData(){
        //TODO: Wireframe - tela 05 - Luciano
    }
}
