package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sahu on 5/7/2017.
 */

public class Select_Station extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Station_name_ListView Adapter;
    Station_name_ListViewRecent RecentAdapter;
    SearchView editsearch;
    ArrayList<AnimalNames> recentSearch=new ArrayList<AnimalNames>();
    ArrayList<AnimalNames> countries;
    String origin=null;
    SaveRecentStationSearch s_r_t_s;
    ListView listView1,listViewRecentSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        s_r_t_s = new SaveRecentStationSearch(getApplicationContext());
        recentSearch=s_r_t_s.readrecent();
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


            Adapter = new Station_name_ListView(Select_Station.this,countries);
            listView1.setAdapter(Adapter);
            RecentAdapter =new Station_name_ListViewRecent(Select_Station.this,recentSearch);
            listViewRecentSearch.setAdapter(RecentAdapter);

            editsearch = (SearchView) findViewById(R.id.search);
            editsearch.setOnQueryTextListener(this);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                    System.out.println(countries.get(arg2).getAnimalName()+""+countries.get(arg2).getAnimalNo());
                    s_r_t_s.setValues(countries.get(arg2).getAnimalNo(),countries.get(arg2).getAnimalName());
                    s_r_t_s.execute("save");


try {
   if (origin.equals("src_stn")) {

        Intent i = new Intent(Select_Station.this, trn_bw_2_stn.class);
        i.putExtra("src_name", countries.get(arg2).getAnimalName());
        i.putExtra("src_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_Station.this.finish();
    } else if (origin.equals("dstn_stn")) {
        Intent i = new Intent(Select_Station.this, trn_bw_2_stn.class);
        i.putExtra("dstn_name", countries.get(arg2).getAnimalName());
        i.putExtra("dstn_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_Station.this.finish();

    } else if (origin.equals("stn_sts")) {
        Intent i = new Intent(Select_Station.this, Station_Status.class);
        i.putExtra("stn_name", countries.get(arg2).getAnimalName());
        i.putExtra("stn_code", countries.get(arg2).getAnimalNo());
        i.putExtra("origin", origin);
        startActivity(i);
        Select_Station.this.finish();
    } else {
        System.out.println("this fn is not working!!!!");
    }
}catch (Exception e){
    e.fillInStackTrace();
}
                }

            });

            listViewRecentSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                //    System.out.println(recentSearch.get(arg2).getAnimalName()+""+recentSearch.get(arg2).getAnimalNo());
                    s_r_t_s.setValues(recentSearch.get(arg2).getAnimalNo(),recentSearch.get(arg2).getAnimalName());
                    s_r_t_s.execute("save");


                    try {
                        if (origin.equals("src_stn")) {

                            Intent i = new Intent(Select_Station.this, trn_bw_2_stn.class);
                            i.putExtra("src_name", recentSearch.get(arg2).getAnimalName());
                            i.putExtra("src_code", recentSearch.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Station.this.finish();
                        } else if (origin.equals("dstn_stn")) {
                            Intent i = new Intent(Select_Station.this, trn_bw_2_stn.class);
                            i.putExtra("dstn_name", recentSearch.get(arg2).getAnimalName());
                            i.putExtra("dstn_code", recentSearch.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Station.this.finish();

                        } else if (origin.equals("stn_sts")) {
                            Intent i = new Intent(Select_Station.this, Station_Status.class);
                            i.putExtra("stn_name", recentSearch.get(arg2).getAnimalName());
                            i.putExtra("stn_code", recentSearch.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Station.this.finish();
                        } else {
                            System.out.println("this fn is not working!!!!");
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
                        //    Log.i("name :",country.animalNo);
                        } else if (name.equals("name")){
                            country.animalName = parser.nextText();
                        //    Log.i("capital :",country.animalName);
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
