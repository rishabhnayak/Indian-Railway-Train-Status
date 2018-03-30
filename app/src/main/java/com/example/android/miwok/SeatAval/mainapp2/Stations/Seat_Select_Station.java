package com.example.android.miwok.SeatAval.mainapp2.Stations;

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
import android.widget.Toast;


import com.example.android.miwok.R;
import com.example.android.miwok.SeatAval.mainapp2.ReservedTrainDataEntry;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Seat_Select_Station extends AppCompatActivity {

    Station_name_ListView Adapter;
    Station_name_ListViewRecent RecentAdapter;
    SearchView editsearch;
    ArrayList<SeatAnimalNames> recentSearch=new ArrayList<SeatAnimalNames>();
    ArrayList<SeatAnimalNames> countries;
    String origin=null;

    ListView listView1,listViewRecentSearch;
    SharedPreferences sd;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item =menu.findItem(R.id.listsearch);

        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setIconified(false);
//        if(origin.equals("main_act_src_stn")|| origin.equals("src_stn")) {
//            searchView.setQueryHint(" FROM Station ");
//        }else if(origin.equals("dstn_stn")){
//            searchView.setQueryHint(" TO Station ");
//        }else if(origin.equals("main_act_stn_sts") ||origin.equals("stn_sts")){
            searchView.setQueryHint(" Search Station.... ");
//        }else if(origin.equals("stn_sts_towards")){
//            searchView.setQueryHint(" Towards Station.... ");
//        }
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            boolean list1visible=false;
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                try {
                    if (!text.equals("") && !list1visible) {
                        listViewRecentSearch.setVisibility(View.GONE);
                        listView1.setVisibility(View.VISIBLE);
                        Adapter.filter(text);
                        list1visible = true;
                        // System.out.println("part 1");
                    } else if (text.equals("")) {
                        listViewRecentSearch.setVisibility(View.VISIBLE);
                        listView1.setVisibility(View.GONE);
                        list1visible = false;
                        // System.out.println("part 2");
                    } else {
                        System.out.println("part 3");
                        Adapter.filter(text);
                    }
                }catch (Exception e){
                    e.fillInStackTrace();
                }
           System.out.println("here is filter text :"+text);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_activity_select_station);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        final String test=getIntent().getExtras().getString("test");
        Toast.makeText(Seat_Select_Station.this, test, Toast.LENGTH_SHORT).show();


        Gson gson = new Gson();
        if(!sd.getString("SeatStationSaver", "").equals("")) {
            String json1 = sd.getString("SeatStationSaver", "");
            SeatStationSaverObject obj = gson.fromJson(json1, SeatStationSaverObject.class);
            recentSearch = obj.getList();
            Collections.reverse(recentSearch);
        }
        origin = getIntent().getStringExtra("origin");
   System.out.println("here is the intent :"+origin);
        listView1 = (ListView) findViewById(R.id.listview);
        listViewRecentSearch= (ListView) findViewById(R.id.listviewRecentSearch);
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("stations_code_name.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            countries = parseXML(parser);


            Adapter = new Station_name_ListView(Seat_Select_Station.this,countries);
            listView1.setAdapter(Adapter);
            RecentAdapter =new Station_name_ListViewRecent(Seat_Select_Station.this,recentSearch);
            listViewRecentSearch.setAdapter(RecentAdapter);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
          
                    Object item = arg0.getItemAtPosition(arg2);
               System.out.println(countries.get(arg2).getAnimalName()+""+countries.get(arg2).getAnimalNo());



try {
    Intent i = new Intent(Seat_Select_Station.this, ReservedTrainDataEntry.class);
    i.putExtra("towards_stn_name", countries.get(arg2).getAnimalName());
    i.putExtra("towards_stn_code", countries.get(arg2).getAnimalNo());
    i.putExtra("origin", origin);
    i.putExtra("working", test);
    startActivity(i);
       Seat_Select_Station.this.finish();

}catch (Exception e){
    e.fillInStackTrace();
}

                    try {
                   System.out.println("single station search history ............");
                        SeatAnimalNames t = new SeatAnimalNames(countries.get(arg2).getAnimalName(),countries.get(arg2).getAnimalNo());
                        Thread thread =new Thread(new SeatStationSaver(sd,t));
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

                    try {
                        Intent i = new Intent(Seat_Select_Station.this, ReservedTrainDataEntry.class);
                        i.putExtra("towards_stn_name", recentSearch.get(arg2).getAnimalName());
                        i.putExtra("towards_stn_code", recentSearch.get(arg2).getAnimalNo());
                        i.putExtra("origin", origin);
                        i.putExtra("working", test);
                        startActivity(i);
                        Seat_Select_Station.this.finish();

                    }catch (Exception e){
                        e.fillInStackTrace();
                    }

                    SeatAnimalNames t = new SeatAnimalNames(recentSearch.get(arg2).getAnimalName(),recentSearch.get(arg2).getAnimalNo());
                    Thread thread =new Thread(new SeatStationSaver(sd,t));
                    thread.start();
                }

            });
            
            
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private ArrayList<SeatAnimalNames> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<SeatAnimalNames> countries = null;
        int eventType = parser.getEventType();
        SeatAnimalNames country = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("stn")){
                        country = new SeatAnimalNames();
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
