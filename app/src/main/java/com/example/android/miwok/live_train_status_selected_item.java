package com.example.android.miwok;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sahu on 5/12/2017.
 */

public class live_train_status_selected_item extends AppCompatActivity {
    String result;
    String startDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);
       // live_train_status_selected_item.this.finish();


        startDate = getIntent().getStringExtra("startDate");

        result = getIntent().getStringExtra("result");
        Log.i("startDate",getIntent().getStringExtra("startDate"));
      Log.i("result",result.toString());

        try {
            JSONArray rakes= new JSONArray(result);
            System.out.println(rakes.getJSONObject(0));

            final ArrayList<live_train_Items_Class> words = new ArrayList<live_train_Items_Class>();
            words.add(new live_train_Items_Class( "stnCode","  schArrTime","  schDepTime","  actArr","  actDep","  dayCnt","  delayArr","  delayDep","  pfNo"));


        for (int i = 0; i < rakes.length(); i++) {
            JSONObject jsonpart = rakes.getJSONObject(i);


            JSONArray stations = jsonpart.getJSONArray("stations");
            for (int j = 0; j < stations.length(); j++) {
             //   System.out.println(stations.toString());

                 JSONObject jsonpart1 =stations.getJSONObject(j);
            String stnCode = jsonpart1.getString("stnCode");
            String actArr = jsonpart1.getString("actArr");
            String dayCnt = jsonpart1.getString("dayCnt");
            String schArrTime = jsonpart1.getString("schArrTime");
            String schDepTime = jsonpart1.getString("schDepTime");

                String delayArr = jsonpart1.getString("delayArr");
                String delayDep = jsonpart1.getString("delayDep");
                String pfNo = jsonpart1.getString("pfNo");
                String actDep = jsonpart1.getString("actDep");

//
            Log.i("stncode", stnCode);
            Log.i("actArr", actArr);
//            Log.i("lastUpdated", lastUpdated);
//            Log.i("totalLateMins", totalLateMins);
//            Log.i("totalJourney", totalJourney);

            live_train_Items_Class w = new live_train_Items_Class( stnCode,  schArrTime,  schDepTime,  actArr,  actDep,  dayCnt,  delayArr,  delayDep,  pfNo);
            words.add(w);
            }
        }
            live_train_ItemList_Adaptor Adapter = new live_train_ItemList_Adaptor(live_train_status_selected_item.this, words);
            ListView listView1 = (ListView) findViewById(R.id.listview1);
            listView1.setAdapter(Adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
