package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class trn_bw_2_stn extends AppCompatActivity {


    String from_stn_name, to_stn_code,from_stn_code,to_stn_name;
    static int tabindex=-1;
    static  TabLayout.Tab fourthTab;
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    String origin = null;
    SharedPreferences sd = null;
    trn_bw_2_stn_ItemList_Adaptor Adapter;
    ArrayList<trn_bw_2_stn_Items_Class> words=null;
    static String[] Month={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    static String[] DayOfWeek={"","Sun","Mon","Tue","Wed","Thr","Fri","Sat"};
    ViewPager simpleViewPager;
    static TabLayout tabLayout;
    Date date= new Date();
    static Calendar cal= Calendar.getInstance();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.refesh,menu);
        MenuItem item =menu.findItem(R.id.refresh);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                recreate();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trn_bw2_stn);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        tabLayout.setupWithViewPager(simpleViewPager);

        TabLayout.Tab firstTab;
        firstTab = tabLayout.newTab();

        tabLayout.addTab(firstTab);
        secondTab = tabLayout.newTab();

        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab;
        thirdTab = tabLayout.newTab();

        tabLayout.addTab(thirdTab);



        fourthTab = tabLayout.newTab();
        fourthTab.setText("Date");

        tabLayout.addTab(fourthTab);

       

        TextView swap=(TextView)findViewById(R.id.swap);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("swap button clicked");

                sd.edit().putBoolean("swap_clked",true).apply();
                sd.edit().putString("temp_toStn_name",sd.getString("dstn_name","")).apply();
                sd.edit().putString("temp_fromStn_name",sd.getString("src_name","")).apply();
                sd.edit().putString("temp_toStn_code",sd.getString("dstn_code","")).apply();
                sd.edit().putString("temp_fromStn_code",sd.getString("src_code","")).apply();
//                System.out.println("from stn "+sd.getString("src_name", ""));
//                System.out.println("from stn code"+sd.getString("src_code", ""));
//                System.out.println("to stn "+sd.getString("dstn_name", ""));
//                System.out.println("to stn code"+sd.getString("dstn_code", ""));
//
//                System.out.println();
//                System.out.println("temp_from stn "+sd.getString("temp_fromStn_name", ""));
//                System.out.println("temp from stn code"+sd.getString("temp_fromStn_code", ""));
//                System.out.println("temp to stn "+sd.getString("temp_toStn_name", ""));
//                System.out.println("temp to stn code"+sd.getString("temp_toStn_code", ""));
                    recreate();
            }
        });

        TextView src_stn = (TextView) findViewById(R.id.src_stn);
        src_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "src_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
            }
        });
        TextView dstn_stn = (TextView) findViewById(R.id.dstn_stn);
        dstn_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();

            }
        });


        origin = this.getIntent().getStringExtra("origin");


        if(sd.getBoolean("swap_clked",true)){
             sd.edit().putString("src_name", sd.getString("temp_toStn_name","")).apply();
             sd.edit().putString("src_code", sd.getString("temp_toStn_code","")).apply();
             Log.i("src_name", sd.getString("src_name", ""));
             src_stn.setText(sd.getString("src_name", ""));
             sd.edit().putString("dstn_name",sd.getString("temp_fromStn_name","")).apply();
             sd.edit().putString("dstn_code", sd.getString("temp_fromStn_code","")).apply();
             Log.i("dstn_name", sd.getString("dstn_name", ""));
             dstn_stn.setText(sd.getString("dstn_name", ""));
            sd.edit().putBoolean("swap_clked",false).apply();
            System.out.println("under swap clked ,true");
            System.out.println("from stn "+sd.getString("src_name", ""));
            System.out.println("to stn "+sd.getString("dstn_name", ""));
            System.out.println("from stn code"+sd.getString("src_code", ""));
            System.out.println("to stn code"+sd.getString("dstn_code", ""));
         }else{
            if (origin.equals("main_act_src_stn")) {
                sd.edit().putString("src_name", getIntent().getStringExtra("src_name")).apply();
                sd.edit().putString("src_code", getIntent().getStringExtra("src_code")).apply();
                Log.i("src_name", sd.getString("src_name", ""));
                src_stn.setText(this.getIntent().getStringExtra("src_name"));


                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
            }
            else
            if (origin.equals("src_stn")) {
                sd.edit().putString("src_name", getIntent().getStringExtra("src_name")).apply();
                sd.edit().putString("src_code", getIntent().getStringExtra("src_code")).apply();
                Log.i("src_name", sd.getString("src_name", ""));
                src_stn.setText(this.getIntent().getStringExtra("src_name"));

                if (sd.getString("dstn_code", "") != "") {
                    dstn_stn.setText(sd.getString("dstn_name", ""));
                } else {
                    Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                    i.putExtra("origin", "dstn_stn");
                    startActivity(i);
                    trn_bw_2_stn.this.finish();
                }

            } else if (origin.equals("dstn_stn")) {
                sd.edit().putString("dstn_name", getIntent().getStringExtra("dstn_name")).apply();
                sd.edit().putString("dstn_code", getIntent().getStringExtra("dstn_code")).apply();
                Log.i("dstn_name", sd.getString("dstn_name", ""));
                dstn_stn.setText(this.getIntent().getStringExtra("dstn_name"));

                if (sd.getString("src_code", "") != "") {
                    src_stn.setText(sd.getString("src_name", ""));
                }
            }
        }
        sd.edit().putBoolean("gotdnlddata",false).apply();
        sd.edit().putString("dnlddataTbts","").apply();

        if(!sd.getString("src_code","").equals("") && !sd.getString("dstn_code","").equals("")) {


            adapter = new PagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());
            simpleViewPager.setAdapter(adapter);

            cal.setTime(date);
            tabLayout.getTabAt(0).setText("All");
            tabLayout.getTabAt(1).setText("Today\n"+DayOfWeek[cal.get(Calendar.DAY_OF_WEEK)]+","+cal.get(Calendar.DAY_OF_MONTH)+" "+Month[cal.get(Calendar.MONTH)]);
            tabLayout.getTabAt(2).setText("UpComing");
            tabLayout.getTabAt(3).setIcon(R.drawable.cale);
            simpleViewPager.setCurrentItem(1);
            simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

             tabindex=1;

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    System.out.println("selected tab :"+tab.getPosition());
                    tabindex=tab.getPosition();
                    simpleViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    System.out.println("Reselected tab :"+tab.getPosition());
                    tabindex=tab.getPosition();
                    simpleViewPager.setCurrentItem(tab.getPosition());
                }
            });

        }

        }


}
