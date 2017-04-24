package com.zino.translatermobilizationmvp.screens.history;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zino.translatermobilizationmvp.screens.history.favorites.view.FavoritesFragment;
import com.zino.translatermobilizationmvp.screens.history.history.view.HistoryFragment;



public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;
    private Fragment[] fragments;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
        fragments = new Fragment[this.numOfTabs];

        fragments[0] = new HistoryFragment();
        fragments[1] = new FavoritesFragment();
        fragments[0].setTargetFragment(fragments[1], 1);
        fragments[1].setTargetFragment(fragments[0], 2);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
