package com.example.gankRimon.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.gankRimon.R;
import com.example.gankRimon.adapter.MyPageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    MyPageAdapter adapter;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        initTabs();//初始化Tabs指示器

        adapter = new MyPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());//创建页面适配器PageAdpter
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

                RecyclerView prettyGirlView = (RecyclerView) findViewById(R.id.prettyGirlView);
                CardView cardView = (CardView) findViewById(R.id.cardView);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) cardView.getLayoutParams();

                if (prettyGirlView != null) {
                    prettyGirlView.scrollToPosition(0);//回到顶部
                }

                Log.e("padding", String.valueOf(prettyGirlView.getChildAt(0).getTop()) + "+" + String.valueOf
                        (layoutParams.topMargin + prettyGirlView.getPaddingTop()));

                int firstChildPosition = prettyGirlView.getChildAdapterPosition(prettyGirlView.getChildAt(0));
                Log.e("位置", String.valueOf(firstChildPosition));//当前显示的第一个cardView的位置。

                if (firstChildPosition == 0 && prettyGirlView.getChildAt(0).getTop() == prettyGirlView.getPaddingTop()
                        + layoutParams.topMargin) {
                    //加入双击刷新功能
                    PrettyGirlFragment prettyGirlFragment = (PrettyGirlFragment) adapter.instantiateItem(viewPager, 0);
                    prettyGirlFragment.getDatas("福利", 20, 1);
                }


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
