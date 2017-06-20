package com.example.android.miwok;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    TabLayout tabLayout;
    public PagerAdapter(FragmentManager fm, int NumOfTabs, TabLayout tabLayout) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabLayout=tabLayout;
    }
String []tabTitles={"All","Today","UpComing","Date"};
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
 
        switch (position) {
            case 0:
                FirstFragment tab1 = new FirstFragment();

                return tab1;
            case 1:
                SecondFragment tab2 = new SecondFragment();
                return tab2;
            case 2:
                ThirdFragment tab3 = new ThirdFragment();
                return tab3;
            case 3:
                FourthFragment tab4 = new FourthFragment();
                return tab4;
            default:
                return null;
        }
    }


 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}