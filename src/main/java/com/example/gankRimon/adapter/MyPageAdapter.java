package com.example.gankRimon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gankRimon.view.Fragment2;
import com.example.gankRimon.view.Fragment3;
import com.example.gankRimon.view.PrettyGirlFragment;

/**
 * Created by Rimon on 2016/5/27.
 */
//新建PageAdapter类继承FragmentStatePagerAdapter,构造方法中传入代表Tab个数的参数numOfTabs；
public class MyPageAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public MyPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PrettyGirlFragment prettyGirlFragment = new PrettyGirlFragment();
                return prettyGirlFragment;
            case 1:
                Fragment2 fragment2 = new Fragment2();
                return fragment2;
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}