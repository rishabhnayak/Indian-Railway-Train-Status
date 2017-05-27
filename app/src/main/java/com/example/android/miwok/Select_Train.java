package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by sahu on 5/7/2017.
 */

public class Select_Train extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Train_name_listView Adapter;
    Train_name_listViewRecent RecentAdapter;
    Intent i;
    SearchView editsearch;
    ArrayList<AnimalNames> countries;
    ArrayList<AnimalNames> recentSearch=new ArrayList<AnimalNames>();
    SharedPreferences sd=null;
    String value; String key;
    String origin=null;
    ListView listView1,listViewRecentSearch;

    SaveRecentTrainSearch s_r_t_s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_train);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        listView1 = (ListView) findViewById(R.id.listview);
        listViewRecentSearch= (ListView) findViewById(R.id.listviewRecentSearch);
        origin = getIntent().getStringExtra("origin");
        System.out.println("here is the intent :"+origin);

        s_r_t_s = new SaveRecentTrainSearch(getApplicationContext());

        recentSearch=s_r_t_s.readrecent();

        key = sd.getString("key","");
        value = sd.getString("pass","");

        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("train_no_names_full.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);


            countries = parseXML(parser);


            Adapter = new Train_name_listView(Select_Train.this,countries);
            listView1.setAdapter(Adapter);
            RecentAdapter =new Train_name_listViewRecent(Select_Train.this,recentSearch);
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
                   s_r_t_s.setValues(Integer.parseInt(countries.get(arg2).getAnimalNo()),countries.get(arg2).getAnimalName());
                    s_r_t_s.execute("save");

                    try {
                        if (origin.equals("trn_schedule")) {


                            i = new Intent(Select_Train.this, TrainSchdule.class);
                            i.putExtra("train_name", countries.get(arg2).getAnimalName());
                            i.putExtra("train_no", countries.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();

                        } else if (origin.equals("live_train_options")) {

                            i = new Intent(Select_Train.this, live_train_options.class);
                            i.putExtra("train_name", countries.get(arg2).getAnimalName());
                            i.putExtra("train_no", countries.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();


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
                    System.out.println(recentSearch.get(arg2).getAnimalName()+""+recentSearch.get(arg2).getAnimalNo());
                    s_r_t_s.setValues(Integer.parseInt(recentSearch.get(arg2).getAnimalNo()),recentSearch.get(arg2).getAnimalName());
                    s_r_t_s.execute("save");

                    try {
                        if (origin.equals("trn_schedule")) {


                            i = new Intent(Select_Train.this, TrainSchdule.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getAnimalName());
                            i.putExtra("train_no", recentSearch.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();

                        } else if (origin.equals("live_train_options")) {

                            i = new Intent(Select_Train.this, live_train_options.class);
                            i.putExtra("train_name", recentSearch.get(arg2).getAnimalName());
                            i.putExtra("train_no", recentSearch.get(arg2).getAnimalNo());
                            i.putExtra("origin", origin);
                            startActivity(i);
                            Select_Train.this.finish();


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
                    if (name.equals("train")){
                        country = new AnimalNames();
                        // country.id=parser.getAttributeValue(null,"id");
                    } else
                    if (country != null){
                        if (name.equals("number")){
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
                    if (name.equalsIgnoreCase("train") && country != null){
                        countries.add(country);
                    }
            }
            eventType = parser.next();
        }

        return countries;

    }
}
