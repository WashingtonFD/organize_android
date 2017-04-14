package com.organize4event.organize.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.models.FirstAccess;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApresentationActivity extends BaseActivity {

    private Context context;
    private FirstAccess firstAccess;

    @Bind(R.id.txtSite)
    TextView txtSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_apresentation);
        ButterKnife.bind(this);

        context = ApresentationActivity.this;
        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
    }

    @OnClick({R.id.txtTermUse, R.id.txtSite, R.id.imgBack, R.id.txtIsRegistered})
    public void actionOnClickView(View view){
        switch (view.getId()){
            case R.id.txtTermUse:
                starTermUseActivity();
                break;
            case R.id.txtSite:
                Uri site = Uri.parse("http://organize4event.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, site);
                startActivity(intent);
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtIsRegistered:
                startActivity(new Intent(context, LoginActivity.class));
                finish();
        }
    }

    protected void starTermUseActivity(){
        Intent intent = new Intent(context, TermUseActivity.class);
        intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
        startActivity(intent);
        finish();
    }
}
