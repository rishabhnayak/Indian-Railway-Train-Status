package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DivertedTrains extends AppCompatActivity {
    SharedPreferences sd=null;
    String value; String key;

    DivertedTrainsAdaptor_Searchable Adapter;
    LinearLayout loading;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    Handler handler;
    Button retryButton;
    ArrayList<DivertedTrainClass> words=new ArrayList<DivertedTrainClass>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item =menu.findItem(R.id.listsearch);

        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                Adapter.filter(text);
                System.out.println("here is filter text :"+text);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diverted_trains);
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
             System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                 System.out.println("task name:"+myobj.getTask_name());
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<DivertedTrainClass>) myobj.getDvtTrnList();
                    Adapter = new DivertedTrainsAdaptor_Searchable(DivertedTrains.this, words);
                    loading.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
                    listView1.setAdapter(Adapter);
                }else if(myobj.getResult().equals("error")){
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }

            }
        };
          Worker worker =new Worker("divertedTrains");
        worker.Input_Details(sd,handler);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());

      //  getDivertedTrains();
    }
    void getDivertedTrains() {
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
            task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllDivertedTrains&" + key+ "=" + value);
        } catch (Exception e) {
            Log.e("error 1", e.toString());
        }
    }

    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("divertedTrains");
        worker.Input_Details(sd,handler);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());

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
                String str2=sd.getString("cookie","");
                str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                E.setRequestProperty("Method", "GET");
                E.setConnectTimeout(5000);
                E.setReadTimeout(15000);
                E.setDoInput(true);
                E.connect();

                if (E.getResponseCode() != 200) {
                    System.out.println("respose code is not 200");
                } else {
                    System.out.println("Jai hind : " + E.getResponseCode());
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));
                String inputLine =null;
                while ((inputLine=in.readLine()) != null) {
                    result +=inputLine;
                }

                return result;
            }catch (Exception e){
                Log.e("error http get:",e.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {


                String[] rs = result.split("=", 2);
                result = rs[1].trim();

                Log.i("here is the result:", result.toString());

                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

                while (localObject1.find()) {

                    result = result.replace(localObject1.group(0), "");

                }
                ArrayList<DivertedTrainClass> words=new ArrayList<DivertedTrainClass>();
                //words.add(new DivertedTrainClass("trainNo","trainName","trainSrc","trainDst","startDate","divertedFrom","divertedTo"));

                JSONObject jsonObject = new JSONObject(result);


                JSONArray arr = jsonObject.getJSONArray("trains");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);
                    String trainNo = "";
                    String trainName = "";
                    String trainSrc= "";
                    String trainDstn ="";
                    String startDate="";
                    String divertedFrom="";
                    String divertedTo="";



                    trainNo = jsonpart.getString("trainNo");
                    trainName = jsonpart.getString("trainName");
                    trainSrc =jsonpart.getString("trainSrc");
                    trainDstn =jsonpart.getString("trainDstn");

                    startDate =jsonpart.getString("startDate");
                    divertedFrom =jsonpart.getString("divertedFrom");
                    divertedTo =jsonpart.getString("divertedTo");
                    String trainType=jsonpart.getString("trainType");

                    DivertedTrainClass w = new DivertedTrainClass(trainNo,trainName,trainSrc,trainDstn,trainType,startDate,divertedFrom,divertedTo);
                    words.add(w);
                }

                Adapter = new DivertedTrainsAdaptor_Searchable(DivertedTrains.this,words);


                loading.setVisibility(View.GONE);
                listView1.setVisibility(View.VISIBLE);

                listView1.setAdapter(Adapter);


                //   resultTextView.setText(result.toString());
            } catch (Exception e) {
                //    resultTextView.setText("could not find weather");
                progressbar.setVisibility(View.GONE);
                disp_msg.setVisibility(View.VISIBLE);
                disp_msg.setText(e.toString());
                Log.e("error3",e.toString());

            }

        }
    }




}
