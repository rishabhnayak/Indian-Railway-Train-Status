package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CanceledTrains extends AppCompatActivity {
SharedPreferences sd=null;

    static int tabindex=-1;
    String value; String key;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    LinearLayout loading;
    ArrayList<CanceledTrainClass> words=new ArrayList<CanceledTrainClass>();
    Handler handler;
    Button retryButton;
    stnName_to_stnCode codeToName;
    static TabLayout tabLayout;
    static TabLayout.Tab secondTab;
    PagerAdapter_CTrains adapter;
    ViewPager simpleViewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_plus_refresh,menu);
        MenuItem item =menu.findItem(R.id.listsearch);
        MenuItem refresh =menu.findItem(R.id.refresh);

        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                System.out.println("here is filter text :" + text);
                try {
                       if(tabindex==0) {
                           FirstFragment_CTrains.Adapter1.filter(text);
                       }else if(tabindex==1){
                           SecondFragment_CTrains.Adapter2.filter(text);
                       }
                        //Adapter2.filter(text);
                    System.out.println("here is filter text :" + text);
                }catch (Exception e){
                    e.fillInStackTrace();
                    System.out.println("error : "+e);
                }
                return false;
            }
        });

        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
        setContentView(R.layout.activity_canceled_trains);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_APPEND);
        sd.edit().putString("dnlddataCancelled", "").apply();
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        tabLayout.setupWithViewPager(simpleViewPager);
                    TabLayout.Tab firstTab;
                    firstTab = tabLayout.newTab();

                    tabLayout.addTab(firstTab);
                    secondTab = tabLayout.newTab();

                    tabLayout.addTab(secondTab);


                    adapter = new PagerAdapter_CTrains
                            (getSupportFragmentManager(), tabLayout.getTabCount());
                    simpleViewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).setText("Fully ");
        tabLayout.getTabAt(1).setText("Partially");

        simpleViewPager.setCurrentItem(0);
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabindex=0;

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
