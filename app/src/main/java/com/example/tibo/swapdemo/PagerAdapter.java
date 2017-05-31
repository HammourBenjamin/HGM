package com.example.tibo.swapdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tibo on 31/05/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter{
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: return new FragmentOne();
            case 1: return new FragmentTwo();
            case 2: return new FragmentThree();
            default:break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
