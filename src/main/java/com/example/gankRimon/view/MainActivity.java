package com.example.gankRimon.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.gankRimon.R;
import com.example.gankRimon.adapter.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    PageAdapter adapter;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTabs();//初始化Tabs指示器
        adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //创建页面适配器PageAdpter
        viewPager.setAdapter(adapter);
        //TabLayoutOnPageChangeListener 实现了
        //ViewPager.OnPageChangeListener接口，可作为addOnPageChangeListener(）的参数，关联Tabs和Viewpager的位置变化；
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.ic_favorite_red_a700_18dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_account_circle_red_a700_18dp);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_build_red_a700_18dp);

                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.ic_favorite_white_18dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_account_circle_white_18dp);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_build_white_18dp);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });


    }

    private void initTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("美女").setIcon(R.drawable
                .ic_favorite_red_a700_18dp));
        tabLayout.addTab(tabLayout.newTab().setText("按钮").setIcon(R.drawable
                .ic_account_circle_white_18dp));
        tabLayout.addTab(tabLayout.newTab().setText("输入框").setIcon(R.drawable.ic_build_white_18dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//使得三个Tab适配屏幕宽度
    }


}
