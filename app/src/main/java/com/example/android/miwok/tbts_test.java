package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class tbts_test extends AppCompatActivity {




  static  TabLayout.Tab fourthTab;
   static TabLayout.Tab secondTab;

    String origin = null;
    SharedPreferences sd = null;
    trn_bw_2_stn_ItemList_Adaptor Adapter;
    ArrayList<trn_bw_2_stn_Items_Class> words=null;
    
    ViewPager simpleViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tbts_test);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        // get the reference of ViewPager and TabLayout
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        // Create a new Tab named "First"
        TabLayout.Tab firstTab;
        firstTab = tabLayout.newTab();
        firstTab.setText("All"); // set the Text for the first Tab
   //     firstTab.setIcon(R.drawable.ic_launcher); // set an icon for the
        // first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
        // Create a new Tab named "Second"

        secondTab = tabLayout.newTab();
        secondTab.setText("Today"); // set the Text for the second Tab
     //   secondTab.setIcon(R.drawable.ic_launcher); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
        // Create a new Tab named "Third"
        TabLayout.Tab thirdTab;
        thirdTab = tabLayout.newTab();
        thirdTab.setText("Coming"); // set the Text for the first Tab
      //  thirdTab.setIcon(R.drawable.ic_launcher); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout


        fourthTab = tabLayout.newTab();
        fourthTab.setText("Date"); // set the Text for the first Tab
        //  thirdTab.setIcon(R.drawable.ic_launcher); // set an icon for the first tab
        tabLayout.addTab(fourthTab); // add  the tab at in the TabLayout


        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        simpleViewPager.setAdapter(adapter);
        // addOnPageChangeListener event change the tab on slide
        simpleViewPager.setCurrentItem(2);
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        TextView src_stn = (TextView) findViewById(R.id.src_stn);
        src_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tbts_test.this, Select_Station.class);
                i.putExtra("origin", "src_stn_test");
                startActivity(i);
                tbts_test.this.finish();
            }
        });
        TextView dstn_stn = (TextView) findViewById(R.id.dstn_stn);
        dstn_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(tbts_test.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn_test");
                startActivity(i);
                tbts_test.this.finish();

            }
        });

        origin = this.getIntent().getStringExtra("origin");
        if (origin.equals("main_act_src_stn_test")) {
            sd.edit().putString("src_name", this.getIntent().getStringExtra("src_name")).apply();
            sd.edit().putString("src_code", this.getIntent().getStringExtra("src_code")).apply();
            Log.i("src_name", sd.getString("src_name", ""));
            src_stn.setText(this.getIntent().getStringExtra("src_name"));


            Intent i = new Intent(tbts_test.this, Select_Station.class);
            i.putExtra("origin", "dstn_stn_test");
            startActivity(i);
            tbts_test.this.finish();


        }
        else if (origin.equals("src_stn_test")) {
            sd.edit().putString("src_name", this.getIntent().getStringExtra("src_name")).apply();
            sd.edit().putString("src_code", this.getIntent().getStringExtra("src_code")).apply();
            Log.i("src_name", sd.getString("src_name", ""));
            src_stn.setText(this.getIntent().getStringExtra("src_name"));

            if (sd.getString("dstn_code", "") != "") {
                dstn_stn.setText(sd.getString("dstn_name", ""));
            } else {
                Intent i = new Intent(tbts_test.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn_test");
                startActivity(i);
                tbts_test.this.finish();
            }

        } else if (origin.equals("dstn_stn_test")) {
            sd.edit().putString("dstn_name", this.getIntent().getStringExtra("dstn_name")).apply();
            sd.edit().putString("dstn_code", this.getIntent().getStringExtra("dstn_code")).apply();
            Log.i("dstn_name", sd.getString("dstn_name", ""));
            dstn_stn.setText(this.getIntent().getStringExtra("dstn_name"));

            if (sd.getString("src_code", "") != "") {
                src_stn.setText(sd.getString("src_name", ""));
            }


        } else if (origin.equals("main_activity")) {
            sd.edit().putString("src_name", "").apply();
            sd.edit().putString("src_code", "").apply();
            Log.i("src_name", sd.getString("src_name", ""));
            sd.edit().putString("dstn_name", "").apply();
            sd.edit().putString("dstn_code", "").apply();
            Log.i("dstn_name", sd.getString("dstn_name", ""));
            src_stn.setText("Source");
            dstn_stn.setText("Destination");
        }



        sd.edit().putBoolean("gotdnlddata",false).apply();
        sd.edit().putString("dnlddataTbts","").apply();



    }
}
