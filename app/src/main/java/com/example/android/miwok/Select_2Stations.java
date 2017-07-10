package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Select_2Stations extends AppCompatActivity {

    Station_name_ListView Adapter;
 //   Station_name_ListViewRecent RecentAdapter;
    FromToStns_name_ListViewRecent Recent2StnsAdapter;
    SearchView editsearch;
   // ArrayList<AnimalNames> recentSearch=new ArrayList<AnimalNames>();
    ArrayList<TwoStnsClass> recent2stnSearch=new ArrayList<TwoStnsClass>();
    ArrayList<AnimalNames> countries;
    String origin=null;

    ListView listView1,listViewRecentSearch,listView2stnRecent;
    SharedPreferences sd;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item =menu.findItem(R.id.listsearch);

        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setIconified(false);
        if(origin.equals("main_act_src_stn")|| origin.equals("src_stn")) {
            searchView.setQueryHint(" FROM Station ");
        }else if(origin.equals("dstn_stn")){
            searchView.setQueryHint(" TO Station ");
        }else if(origin.equals("main_act_stn_sts") ||origin.equals("stn_sts")){
            searchView.setQueryHint(" Search Station.... ");
        }
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
                  //  listViewRecentSearch.setVisibility(View.GONE);
                    listView2stnRecent.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
                    Adapter.filter(text);
                    list1visible=true;
                    // //System.out.println("part 1");
                }else if(text.equals("")){
                  //  listViewRecentSearch.setVisibility(View.VISIBLE);
                    listView2stnRecent.setVisibility(View.VISIBLE);
                    listView1.setVisibility(View.GONE);
                    list1visible=false;
                    // //System.out.println("part 2");
                }else{
                    ////System.out.println("part 3");
                    Adapter.filter(text);
                }

              //System.out.println("here is filter text :"+text);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_2stns);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        if(!sd.getString("TwoStnsSaver", "").equals("")) {
            String json1 = sd.getString("TwoStnsSaver", "");
            TwoStnsSaverObject obj = gson.fromJson(json1, TwoStnsSaverObject.class);
           recent2stnSearch = obj.getList();
            Collections.reverse(recent2stnSearch);
        }
        origin = getIntent().getStringExtra("origin");
      //System.out.println("here is the intent :"+origin);
        listView1 = (ListView) findViewById(R.id.listview);
       // listViewRecentSearch= (ListView) findViewById(R.id.listviewRecentSearch);
        listView2stnRecent=(ListView)findViewById(R.id.listviewRecent2stnSearch);

        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("stations_code_name.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            countries = parseXML(parser);


            Adapter = new Station_name_ListView(Select_2Stations.this,countries);
            listView1.setAdapter(Adapter);
           // RecentAdapter =new Station_name_ListViewRecent(Select_2Stations.this,recentSearch);
          //  listViewRecentSearch.setAdapter(RecentAdapter);
            Recent2StnsAdapter =new FromToStns_name_ListViewRecent(Select_2Stations.this,recent2stnSearch);
            listView2stnRecent.setAdapter(Recent2StnsAdapter);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
          
                    Object item = arg0.getItemAtPosition(arg2);
                  //System.out.println(countries.get(arg2).getAnimalName()+""+countries.get(arg2).getAnimalNo());



try {if (origin.equals("main_act_src_stn")) {

    Intent i = new Intent(Select_2Stations.this, trn_bw_2_stn.class);
    i.putExtra("src_name", countries.get(arg2).getAnimalName());
    i.putExtra("src_code", countries.get(arg2).getAnimalNo());
    i.putExtra("origin", origin);
    startActivity(i);
    Select_2Stations.this.finish();
}
else
   if (origin.equals("src_stn")) {

        Intent i = new Intent(Select_2Stations.this, trn_bw_2_stn.class);
        i.putExtra("src_name", countries.get(arg2).getAnimalName());
        i.putExtra("src_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_2Stations.this.finish();
    } else if (origin.equals("dstn_stn")) {
        Intent i = new Intent(Select_2Stations.this, trn_bw_2_stn.class);
        i.putExtra("dstn_name", countries.get(arg2).getAnimalName());
        i.putExtra("dstn_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_2Stations.this.finish();

    }  else if (origin.equals("stn_sts")) {
        Intent i = new Intent(Select_2Stations.this, Station_Status.class);
        i.putExtra("stn_name", countries.get(arg2).getAnimalName());
        i.putExtra("stn_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_2Stations.this.finish();
    }else if (origin.equals("main_act_stn_sts")) {
       Intent i = new Intent(Select_2Stations.this, Station_Status.class);
       i.putExtra("stn_name", countries.get(arg2).getAnimalName());
       i.putExtra("stn_code", countries.get(arg2).getAnimalNo());
       i.putExtra("origin", origin);
       startActivity(i);
       Select_2Stations.this.finish();
   } else {
      //System.out.println("this fn is not working!!!!");
    }
}catch (Exception e){
    e.fillInStackTrace();
}

                    try {

                        AnimalNames t = new AnimalNames(countries.get(arg2).getAnimalName(),countries.get(arg2).getAnimalNo());
                        Thread thread =new Thread(new StationSaver(sd,t));
                        thread.start();

                    }catch (Error e){
                      //System.out.println("save fn error");
                    }   

                }

            });

           listView2stnRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Object item = arg0.getItemAtPosition(arg2);

                    Intent i = new Intent(Select_2Stations.this, trn_bw_2_stn.class);
                    i.putExtra("src_name", recent2stnSearch.get(arg2).getFromStnName());
                    i.putExtra("src_code", recent2stnSearch.get(arg2).getFromStnCode());
                    i.putExtra("dstn_name", recent2stnSearch.get(arg2).getToStnName());
                    i.putExtra("dstn_code", recent2stnSearch.get(arg2).getToStnCode());
                    i.putExtra("origin", "search_2stn_onClk");
                    startActivity(i);
                    Select_2Stations.this.finish();
                }

            });
            
            
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private ArrayList<AnimalNames> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<AnimalNames> countries = null;
        int eventType = parser.getEventType();
        AnimalNames country = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("stn")){
                        country = new AnimalNames();
                        // country.id=parser.getAttributeValue(null,"id");
                    } else
                    if (country != null){
                        if (name.equals("code")){
                            country.animalNo = parser.nextText();
                        //   // Log.i("name :",country.animalNo);
                        } else if (name.equals("name")){
                            country.animalName = parser.nextText();
                        //   // Log.i("capital :",country.animalName);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("stn") && country != null){
                        countries.add(country);
                    }
            }
            eventType = parser.next();
        }

        return countries;

    }
}
