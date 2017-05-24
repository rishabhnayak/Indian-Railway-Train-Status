package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sahu on 5/20/2017.
 */

public class trn_bw2_stn_bgt extends trn_bw_2_stn.PlaceholderFragment implements Runnable {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public trn_bw2_stn_bgt() {
    }

    @Override
    public void run() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.getSelectedTabPosition();
        View rootView = inflater.inflate(R.layout.fragment_sub_page01, container, false);

        trn_bw2_stn_bgt = new trn_bw2_stn_bgt();
        key = sd.getString("key", "");
        value = sd.getString("pass", "");


        if (sd.getString("src_code", "") != "" & sd.getString("dstn_code", "") != "") {
            System.out.println("here is the data  :" + sd.getString("src_name", "") + "\n" + sd.getString("dstn_name", ""));

            listView1 = (ListView) rootView.findViewById(R.id.listview1);


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                getTrn_bw2_stn(sd.getString("src_code", ""), sd.getString("dstn_code", ""), "all", "");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                getTrn_bw2_stn(sd.getString("src_code", ""), sd.getString("dstn_code", ""), "today", "");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                getTrn_bw2_stn(sd.getString("src_code", ""), sd.getString("dstn_code", ""), "tomorrow", "");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                rootView = null;
                rootView = inflater.inflate(R.layout.fragment_sub_page02, container, false);
                listView1 = (ListView) rootView.findViewById(R.id.listview1);
                simpleDatePicker = (DatePicker) rootView.findViewById(R.id.simpleDatePicker);

                datepickerlayout = (RelativeLayout) rootView.findViewById(R.id.datepickerlayout);

                tablelayout = (LinearLayout) rootView.findViewById(R.id.tablelayout);

                submit = (Button) rootView.findViewById(R.id.submitButton);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the values for day of month , month and year from a date picker
                        String day = "" + simpleDatePicker.getDayOfMonth();
                        String month = "" + (simpleDatePicker.getMonth() + 1);
                        String year = "" + simpleDatePicker.getYear();
                        datepickerlayout.setVisibility(View.INVISIBLE);
                        tablelayout.setVisibility(View.VISIBLE);
                        // display the values by using a toast
                        simpleDatePicker.setCalendarViewShown(false);

                        getTrn_bw2_stn(sd.getString("src_code", ""), sd.getString("dstn_code", ""), "byDate", "" + year + "," + month + "," + day);

                        Toast.makeText(getActivity().getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Log.i(" Error in onCreateView", "error");
            }

        }

        return rootView;
    }

    void getTrn_bw2_stn(String src_code, String dstn_code, String fltr, String dateobj) {
        try {
            key_pass_generator key_pass_generator=new key_pass_generator();
            key_pass_generator.start();
            try {
                key_pass_generator.join();
                System.out.println("joined the thread :"+key_pass_generator.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            key = sd.getString("key","");
            value = sd.getString("pass","");
           DownloadTask task = new DownloadTask();
            // task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&"+key+"="+value);

            if(receiveddata== null) {
                receiveddata = task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrnBwStns&stn1=" + src_code + "&stn2=" + dstn_code + "&trainType=ALL&" + key + "=" + value).get();
                System.out.println("Calling request for"+src_code+" to "+dstn_code);
                data_filter_task(receiveddata,fltr,dateobj);
                Log.i("receiveddata",receiveddata);
            }  else{
                data_filter_task(receiveddata,fltr,dateobj);
            }

        } catch (Exception e) {
            Log.e("in getTrai_bw2_stn", e.toString());
        }


    }

    void data_filter_task(String result,String filter,String dateobj){
        try {

            int count=0;
            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Log.i("here is the result:", result.toString());


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                //  String group = localObject1.group();
                result = result.replace(localObject1.group(0), "");
                //  System.out.println(group);
            }


            System.out.println(result);
            final ArrayList<trn_bw_2_stn_Items_Class> words = new ArrayList<trn_bw_2_stn_Items_Class>();
//
            JSONObject jsonObject = new JSONObject(result);



            JSONObject trains = jsonObject.getJSONObject("trains");
            JSONArray arr = trains.getJSONArray("direct");
//
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonpart = arr.getJSONObject(i);


                String trainNo = jsonpart.getString("trainNo");
                String trainName = jsonpart.getString("trainName");

                String runsFromStn = jsonpart.getString("runsFromStn");
                String src = jsonpart.getString("src");
                String srcCode = jsonpart.getString("srcCode");
                String dstn = jsonpart.getString("dstn");
                String dstnCode = jsonpart.getString("dstnCode");
                String fromStn = jsonpart.getString("fromStn");

                String fromStnCode = jsonpart.getString("fromStnCode");
                String toStn = jsonpart.getString("toStn");
                String toStnCode = jsonpart.getString("toStnCode");
                String depAtFromStn = jsonpart.getString("depAtFromStn");
                String arrAtToStn = jsonpart.getString("arrAtToStn");
                String travelTime = jsonpart.getString("travelTime");
                String trainType = jsonpart.getString("trainType");
                String sNo;



                if(filter.equals("today")) {

                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else
                    if (runDays.contains(dayfinderClass("today",""))) {
                        sNo = String.valueOf(++count);
                        System.out.println("yeh this train will  come today");
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("tomorrow")) {

                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(dayfinderClass("tomorrow",""))) {
                        System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("byDate")) {
                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(dayfinderClass("byDate",dateobj))) {
                        System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else {
                    sNo = String.valueOf(++count);
                    trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                    words.add(w);
                }

            }
//
            trn_bw_2_stn_ItemList_Adaptor Adapter = new trn_bw_2_stn_ItemList_Adaptor((trn_bw_2_stn) getActivity(), words);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                    System.out.println(words.get(arg2).getDepAtFromStn() + "");

                    try {

                        Intent i = new Intent(getActivity(), live_train_status_selected_item.class);
                        i.putExtra("journeyDate","16 May 2017");
                        i.putExtra("trainNo",words.get(arg2).getTrainNo());
                        i.putExtra("fromStn",words.get(arg2).getFromStnCode());
                        i.putExtra("origin","train_bw_2_stn");
                        startActivity(i);

                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }

                }
            });

            listView1.setAdapter(Adapter);


        } catch (Exception e) {
            Log.e("error3", e.toString());
        }
    }

    String dayfinderClass(String TodayorTomorrow,String dateobj){
        String dayofweekval="";
        if(TodayorTomorrow=="today") {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};

            dayofweekval = myStringArray[dayofweek];
        }else if(TodayorTomorrow=="tomorrow"){
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval = myStringArray[dayofweek+1];
        }else if(TodayorTomorrow=="byDate"){
            String [] Dateobj=dateobj.split(",");
            System.out.println(Dateobj[1]+"\n"+Dateobj[2]+"\n"+dateobj);
            Calendar cal = new GregorianCalendar(Integer.parseInt(Dateobj[0]), Integer.parseInt(Dateobj[1]), Integer.parseInt(Dateobj[2]));
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval=myStringArray[dayofweek];

        }
        return String.valueOf(dayofweekval);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;

            try {
                HttpURLConnection E = null;
                url = new URL(urls[0]);
                E = (HttpURLConnection) url.openConnection();
                String str2 = sd.getString("cookie", "");
                str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                E.setRequestProperty("Method", "GET");
                E.setConnectTimeout(20000);
                E.setReadTimeout(30000);
                E.setDoInput(true);
                E.connect();

                if (E.getResponseCode() != 200) {
                    System.out.println("respose code is not 200");
                } else {
                    System.out.println("Jai hind : " + E.getResponseCode());
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));


                String inputLine = null;

                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }

                return result;
            } catch (Exception e) {
                Log.e("error http get:", e.toString());
            }


            return null;
        }


    }
}
