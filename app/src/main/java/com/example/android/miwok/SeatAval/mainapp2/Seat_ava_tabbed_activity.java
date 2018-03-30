package com.example.android.miwok.SeatAval.mainapp2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.SeatAval.mainapp2.Fragments.FiveFragment;
import com.example.android.miwok.SeatAval.mainapp2.Fragments.FourFragment;
import com.example.android.miwok.SeatAval.mainapp2.Fragments.OneFragment;
import com.example.android.miwok.SeatAval.mainapp2.Fragments.ThreeFragment;
import com.example.android.miwok.SeatAval.mainapp2.Fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class Seat_ava_tabbed_activity extends AppCompatActivity {
String a1,a2,a3,a4,a5,d,m,y,from_code,from_name,to_code,to_name,train_no,train_name,arr,dep,tt;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_tab_layout);
try {
    a1 = getIntent().getExtras().getString("1");
    a2 = getIntent().getExtras().getString("2");
    a3 = getIntent().getExtras().getString("3");
    a4 = getIntent().getExtras().getString("4");
    a5 = getIntent().getExtras().getString("5");
    d = getIntent().getExtras().getString("d");
    m = getIntent().getExtras().getString("m");
    y = getIntent().getExtras().getString("y");
    from_code = getIntent().getExtras().getString("from_code");
    to_code = getIntent().getExtras().getString("to_code");
    from_name = getIntent().getExtras().getString("from_name");
    to_name = getIntent().getExtras().getString("to_name");
    train_name = getIntent().getExtras().getString("train_name");
    train_no = getIntent().getExtras().getString("train_no");
    arr = getIntent().getExtras().getString("arr");
    dep = getIntent().getExtras().getString("dep");
    tt = getIntent().getExtras().getString("tt");

}catch (Exception e){
    System.out.println("Error...."+e.getMessage());
}
        TextView trainno= (TextView) findViewById(R.id.trainNumber);
        TextView trainname= (TextView) findViewById(R.id.trainName);
        TextView departuretime= (TextView) findViewById(R.id.departureTime);
        TextView arrivaltime= (TextView) findViewById(R.id.arrivalTime);
        TextView duration= (TextView) findViewById(R.id.duration);
        TextView src= (TextView) findViewById(R.id.trainSrc);
        TextView dst= (TextView) findViewById(R.id.trainDstn);
        trainno.setText(train_no);
        trainname.setText(train_name);
        departuretime.setText(dep);
        arrivaltime.setText(arr);
        duration.setText(tt);
        src.setText(to_name+"-"+to_code);
        dst.setText(from_name+"-"+from_code);

       // Toast.makeText(this, a1, Toast.LENGTH_SHORT).show();
        try {
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        }catch (Exception e){

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        try{    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


            if (a1!=null&&a2!=null&&a3!=null&&a4!=null&&a5!=null){
                adapter.addFrag(new OneFragment(a1, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a1);
                adapter.addFrag(new TwoFragment(a2, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a2);
                adapter.addFrag(new ThreeFragment(a3, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a3);
                adapter.addFrag(new FourFragment(a4, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no),a4);
                adapter.addFrag(new FiveFragment(a5, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no),a5);
            }
           else if (a1!=null&&a2!=null&&a3!=null&&a4!=null){
                adapter.addFrag(new OneFragment(a1, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a1);
                adapter.addFrag(new TwoFragment(a2, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a2);
                adapter.addFrag(new ThreeFragment(a3, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a3);
                adapter.addFrag(new FourFragment(a4, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no),a4);
            }
            else if (a1!=null&&a2!=null&&a3!=null){
                adapter.addFrag(new OneFragment(a1, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a1);
                adapter.addFrag(new TwoFragment(a2, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a2);
                adapter.addFrag(new ThreeFragment(a3, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a3);
            }
            else if (a1!=null&&a2!=null){
                adapter.addFrag(new OneFragment(a1, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a1);
                adapter.addFrag(new TwoFragment(a2, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a2);
            }
            else if (a1!=null){
                adapter.addFrag(new OneFragment(a1, d, m, y, from_code, from_name, to_code, to_name,train_name,train_no), a1);
            }

       viewPager.setAdapter(adapter);}
       catch (Exception e){

       }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
