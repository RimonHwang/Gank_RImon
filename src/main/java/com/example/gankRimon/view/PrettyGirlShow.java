package com.example.gankRimon.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gankRimon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rimon on 2016/5/29.
 */
public class PrettyGirlShow extends Activity {
    @BindView(R.id.girlShow)
    ImageView girlShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prettygirl_show);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        String url=intent.getExtras().getString("url");
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(girlShow);
    }
}
