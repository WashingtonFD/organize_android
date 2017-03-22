package com.organize4event.organize.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.organize4event.organize.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialog extends BaseActivity {

    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.txtMessage)
    TextView txtMessage;

    private String title = "";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        message = bundle.getString("message");

        txtTitle.setText(title);
        txtMessage.setText(message);
    }

    @OnClick(R.id.btnPositive)
    public void actionCloseDialog(){
        finish();
    }
}
