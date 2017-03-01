package com.organize4event.organize.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.ui.fragments.BaseFragmentDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WaitDialog extends BaseFragmentDialog{
    private static final String MESSAGE = "message";

    @Bind(R.id.dwMessage)
    TextView dwMessage;

    public WaitDialog(){
        super();
    }

    public static WaitDialog newInstance(){
        return new WaitDialog();
    }

    public static WaitDialog newInstance(String message){
        WaitDialog fragment = new WaitDialog();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_wait, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null && !TextUtils.isEmpty(getArguments().getString(MESSAGE))){
            dwMessage.setText(MESSAGE);
        }
        else{
            dwMessage.setText(R.string.info_carregando);
        }
    }
}
