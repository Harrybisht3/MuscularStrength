package app.android.muscularstrength.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.android.muscularstrength.fragment.FirstFragment;
import app.android.muscularstrength.fragment.FourthFragment;
import app.android.muscularstrength.fragment.SecondFragment;
import app.android.muscularstrength.fragment.ThirdFragment;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return new FirstFragment();
            case 1: return new SecondFragment();
            case 2: return new ThirdFragment();
            case 3: return new FourthFragment();
            default: return new FirstFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}