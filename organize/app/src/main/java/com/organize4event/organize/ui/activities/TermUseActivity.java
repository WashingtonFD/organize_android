package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.controller.TermUseControll;
import com.organize4event.organize.listener.ControllResponseListener;
import com.organize4event.organize.listener.ToolbarListener;
import com.organize4event.organize.model.TermUse;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TermUseActivity extends BaseActivity {
    private Context context;
    private TermUse termUse;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.txtContent)
    TextView txtContent;

    @Bind(R.id.imgAccept)
    ImageView imgAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_use);
        ButterKnife.bind(this);

        context = TermUseActivity.this;
        imgAccept.setEnabled(false);

        configureToolbar(context, toolbar, context.getResources().getString(R.string.label_termo_uso), context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        getData();
    }

    public void getData(){
        showLoading();
        new TermUseControll(context).getTermUse(new ControllResponseListener() {
            @Override
            public void sucess(Object object) {
                hideLoading();
                termUse = (TermUse) object;
                txtTitle.setText(termUse.getTitle());
                txtContent.setText(termUse.getContent());
            }

            @Override
            public void fail(Error error) {
               if (isOline(context)){
                   hideLoading();
                   showToastMessage(context, error.getMessage());
               }
                else{
                   hideLoading();
                   showToastMessage(context, context.getResources().getString(R.string.error_message_conect));
               }
            }
        });
    }

    public void enableAccept(){

    }
}
