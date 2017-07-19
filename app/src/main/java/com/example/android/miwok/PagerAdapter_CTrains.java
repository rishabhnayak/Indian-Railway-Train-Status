package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_CTrains extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter_CTrains(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public Fragment getItem(int position) {
 
        switch (position) {
            case 0:
                FirstFragment_CTrains tab1 = new FirstFragment_CTrains();

                return tab1;
            case 1:
                SecondFragment_CTrains tab2 = new SecondFragment_CTrains();
                return tab2;

            default:
                return null;
        }
    }


 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}