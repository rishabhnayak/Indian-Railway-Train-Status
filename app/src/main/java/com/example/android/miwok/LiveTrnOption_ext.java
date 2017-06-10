package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class LiveTrnOption_ext {
    public LiveTrnOption_ext(String dnld_data, Handler info_ext_handler) {
        ArrayList<live_train_options_Class> words = new ArrayList<live_train_options_Class>();
        String dnld_data1=dnld_data;
        String StartDate;
        try {

            System.out.println(dnld_data);
            String[] rs = dnld_data.split("=", 2);
            dnld_data = rs[1].trim();


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);
            Log.i("here is the dnld_data:", dnld_data.toString());
            while (localObject1.find()) {

                dnld_data = dnld_data.replace(localObject1.group(0), "");

            }

            JSONArray jsonArray = new JSONArray(dnld_data);


            JSONObject resobj = (JSONObject) jsonArray.get(0);
            System.out.println(resobj);
            final JSONArray rakes = resobj.getJSONArray("rakes");
            System.out.println(rakes);
            System.out.println(rakes.getJSONObject(0));
            for (int i = 0; i < rakes.length(); i++) {
                JSONObject jsonpart = rakes.getJSONObject(i);
                String startDate = jsonpart.getString("startDate");
                String curStn = jsonpart.getString("curStn");
                String lastUpdated = jsonpart.getString("lastUpdated");
                String totalLateMins = jsonpart.getString("totalLateMins");
                String totalJourney = jsonpart.getString("totalJourney");
                live_train_options_Class w = new live_train_options_Class(startDate, curStn, totalLateMins, lastUpdated, totalJourney);
                words.add(w);
            }
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setLiveTrnOption(words);
            obj.setDnlddata(dnld_data1);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
