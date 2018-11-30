package com.dappley.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dappley.android.fragment.MeFragment;
import com.dappley.android.fragment.StepFragment;
import com.dappley.android.fragment.WalletFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {
    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new WalletFragment();
                break;
            case 1:
                fragment = new StepFragment();
                break;
            case 2:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
