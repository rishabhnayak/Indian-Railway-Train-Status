package com.example.android.miwok;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class Select_Train extends AppCompatActivity {

    Train_name_listView Adapter;
    Train_name_listViewRecent RecentAdapter;
    Intent i;
    SearchView editsearch;
    ArrayList<TrainDetailsObj> countries;
    ArrayList<TrainDetailsObj> recentSearch=new ArrayList<TrainDetailsObj>();
    SharedPreferences sd=null;
    String value; String key;
    String origin=null;
    ListView listView1,listViewRecentSearch;
    stnName_to_stnCode codeToName;

    android.support.v7.widget.SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        listView1 = (ListView) findViewById(R.id.listview);
        listViewRecentSearch= (ListView) findViewById(R.id.listviewRecentSearch);
        origin = getIntent().getStringExtra("origin");
        System.out.println("here is the intent :"+origin);

//        SaveRecentTrainSearch  s_r_t_s = new SaveRecentTrainSearch(getApplicationContext());

//       recentSearch=MainActivity.s_r_t_s.readrecent();

        Gson gson = new Gson();
        if(!sd.getString("TrainSaver", "").equals("")) {
            String json1 = sd.getString("TrainSaver", "");
            // System.out.println("here is json 1" + json1);
            TrainSaverObject obj = gson.fromJson(json1, TrainSaverObject.class);
            recentSearch = obj.getList();
            Collections.reverse(recentSearch);
        }
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

          //  InputStream in_s = getApplicationContext().getAssets().open("train_no_names_full.xml");

            InputStream in_s = getApplicationContext().getAssets().open("trains_new_list.xml");

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            countries = parseXML(parser);


            Adapter = new Train_name_listView(Select_Train.this,countries,codeToName);
            listView1.setAdapter(Adapter);
            RecentAdapter =new Train_name_listViewRecent(Select_Train.this,recentSearch,codeToName);
            listViewRecentSearch.setAdapter(RecentAdapter);


            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                    System.out.println(countries.get(arg2).getTrnName()+""+countries.get(arg2).getTrnNo());
                   // SaveRecentTrainSearch  s_r_t_s1 = new SaveRecentTrainSearch(getApplicationContext());
                   System.out.println("origin is :"+origin);
                    try {
                        if (origin.equals("trn_schedule")) {


                                i = new Intent(Select_Train.this, TrainSchdule.class);
                                i.putExtra("train_name", countries.get(arg2).getTrnName());
                                i.putExtra("train_no", countries.get(arg2).getTrnNo());
                                i.putExtra("origin", origin);
                                startActivity(i);
                                Select_Train.this.finish();

                            }else if (origin.equals("main_act_trn_schedule")) {


                                i = new Intent(Select_Train.this, TrainSchdule.class);
                                i.putExtra("train_name", countries.get(arg2).getTrnName());
                                i.putExtra("train_no", countries.get(arg2).getTrnNo());
                                i.putExtra("origin", origin);
                                startActivity(i);
                                Select_Train.this.finish();

                            }else if (origin.equals("live_train_options")) {

                                i = new Intent(Select_Train.this, live_train_options.class);
                                i.putExtra("train_name", countries.get(arg2).getTrnName());
                                i.putExtra("train_no", countries.get(arg2).getTrnNo());
                                i.putExtra("origin", origin);
                                startActivity(i);
                                Select_Train.this.finish();


                            }  else if (origin.equals("main_act_live_train_options")) {

                                i = new Intent(Select_Train.this, live_train_options.class);
                                i.putExtra("train_name", countries.get(arg2).getTrnName());
                                i.putExtra("train_no", countries.get(arg2).getTrnNo());
                                i.putExtra("origin", origin);
                                startActivity(i);
                                Select_Train.this.finish();


                            }else {
                                System.out.println("this fn is not working!!!!");
                            }
                    }catch (Exception e){
                        e.fillInStackTrace();
                    }

                    try {
//                    MainActivity.s_r_t_s.setValues(Integer.parseInt(countries.get(arg2).getTrnNo()), countries.get(arg2).getTrnName());
//                    MainActivity.s_r_t_s.execute("save");
                       TrainDetailsObj t = new TrainDetailsObj(countries.get(arg2).getTrnName(),countries.get(arg2).getTrnNo(),codeToName.stnName_to_stnCode(countries.get(arg2).getSrcName()),codeToName.stnName_to_stnCode(countries.get(arg2).getDstnName()));
                        Thread thread =new Thread(new TrainSaver(sd,t));
                        thread.start();

                    }catch (Error e){
                        System.out.println("save fn error");
                    }
                }
            });

            listViewRecentSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Object item = arg0.getItemAtPosition(arg2);
                    System.out.println(recentSearch.get(arg2).getTrnName()+""+recentSearch.get(arg2).getTrnNo());
//                    SaveRecentTrainSearch  s_r_t_s1 = new SaveRecentTrainSearch(getApplicationContext());
//                    s_r_t_s1.setValues(Integer.parseInt(recentSearch.get(arg2).getTrnNo()),recentSearch.get(arg2).getTrnName());
//                    s_r_t_s1.execute("save");
                    try {
                        if (origin.equals("trn_schedule")) {


                            i = new Intent(Select_Train.this, TrainSchdule.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getTrnName());
                            i.putExtra("train_no", recentSearch.get(arg2).getTrnNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();

                        }else if (origin.equals("main_act_trn_schedule")) {


                            i = new Intent(Select_Train.this, TrainSchdule.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getTrnName());
                            i.putExtra("train_no", recentSearch.get(arg2).getTrnNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();

                        }else if (origin.equals("live_train_options")) {

                            i = new Intent(Select_Train.this, live_train_options.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getTrnName());
                            i.putExtra("train_no", recentSearch.get(arg2).getTrnNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();


                        }  else if (origin.equals("main_act_live_train_options")) {

                            i = new Intent(Select_Train.this, live_train_options.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getTrnName());
                            i.putExtra("train_no", recentSearch.get(arg2).getTrnNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                           Select_Train.this.finish();


                        }else {
                            System.out.println("this fn is not working!!!!");
                        }

                        try {
//                    MainActivity.s_r_t_s.setValues(Integer.parseInt(countries.get(arg2).getTrnNo()), countries.get(arg2).getTrnName());
//                    MainActivity.s_r_t_s.execute("save");
                            TrainDetailsObj t = new TrainDetailsObj(recentSearch.get(arg2).getTrnName(),recentSearch.get(arg2).getTrnNo(),recentSearch.get(arg2).getSrcName(),recentSearch.get(arg2).getDstnName());
                            Thread thread =new Thread(new TrainSaver(sd,t));
                            thread.start();

                        }catch (Error e){
                            System.out.println("save fn error");
                        }
                    }catch (Exception e){
                        e.fillInStackTrace();
                    }
                }
            });
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item =menu.findItem(R.id.listsearch);


        searchView = (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search Train....");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            boolean list1visible=false;
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                if(!text.equals("") && !list1visible){
                    listViewRecentSearch.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
                    Adapter.filter(text);
                    list1visible=true;
                    // System.out.println("part 1");
                }else if(text.equals("")){
                    listViewRecentSearch.setVisibility(View.VISIBLE);
                    listView1.setVisibility(View.GONE);
                    list1visible=false;
                    // System.out.println("part 2");
                }else{
                    //  System.out.println("part 3");
                    Adapter.filter(text);
                }

                System.out.println("here is filter text :"+text);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    private ArrayList<TrainDetailsObj> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<TrainDetailsObj> countries = null;
        int eventType = parser.getEventType();
        TrainDetailsObj country = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("train")){
                        country = new TrainDetailsObj();
                        // country.id=parser.getAttributeValue(null,"id");
                    } else
                    if (country != null){
                        if(name.equals("srcName")){
                            country.setSrcName(parser.nextText());
                        }else if(name.equals("dstnName")){
                            country.setDstnName(parser.nextText());
                        } else if (name.equals("trnName")){
                            country.setTrnName(parser.nextText());
                            //    Log.i("capital :",country.animalName);
                        } else if (name.equals("trnNo")){
                            country.setTrnNo(parser.nextText());
                        //    Log.i("name :",country.animalNo);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("train") && country != null){
                        countries.add(country);
                    }
            }
            eventType = parser.next();
        }

        return countries;

    }
}
