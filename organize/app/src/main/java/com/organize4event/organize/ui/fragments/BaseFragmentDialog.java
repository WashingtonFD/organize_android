package com.organize4event.organize.ui.fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by marcelamelo on 20/02/17.
 */

public class BaseFragmentDialog extends DialogFragment{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public void show(FragmentManager manager, String tag){
        try{
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(this, tag);
            transaction.commitAllowingStateLoss();
        }
        catch (Exception ex){
            Log.v("Error: ", ex.getMessage());
        }
    }
}
