package com.example.gankRimon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gankRimon.R;
import com.example.gankRimon.model.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rimon on 2016/5/29.
 */
public class PrettyGirlShow extends AppCompatActivity {


    List<Result> resultList;
    @BindView(R.id.showGirl)
    ViewPager showGirl;

    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prettygirl_show);
        ButterKnife.bind(this);

        //接受来自PrettyGirlAdapter的resultList。
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("position");
        resultList = intent.getExtras().getParcelableArrayList("resultList");
        Glide.with(this)
                .load(resultList.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(girlShow);
        //TODO:使用viewpager显示原图
        pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return resultList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

        };
    }
}
