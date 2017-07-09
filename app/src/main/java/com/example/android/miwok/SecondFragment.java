package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class SecondFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter2=null;
    Thread thread1;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview1;
    ArrayList<trn_bw_2_stn_Items_Class> words1;
    LinearLayout disp_content,loading;
    Handler OnCreateHandler;
    String dnlddata=null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton;
    Thread thread0 = null;
    View rootView;
    Handler handler;
    private boolean isViewShown = false;
    String filter="today";
    public SecondFragment() {
        // Required empty public constructor
    }
       private Boolean oncreateCreated1=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //System.out.println("OnCreateView Page 2 :Today Tab...");
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_second, container, false);

        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
//        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
//        tabLayout.getSelectedTabPosition();

        loading = (LinearLayout)rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview1 = (ListView) rootView.findViewById(R.id.listview);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              //System.out.println("fragment,Today,handler");
                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success") && getActivity() !=null) {
                  //System.out.println("fragment,Today,handler,if part(success)");

                  //System.out.println(myobj.getResult());
                    words1 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                    Adapter2 = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words1);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listview1 = (ListView) rootView.findViewById(R.id.listview);
                    listview1.setAdapter(Adapter2);
                }else if(myobj.getResult().equals("error")){
                  //System.out.println("fragment,Today,handler,else if part (error)");

                  //System.out.println(myobj.getResult());
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }else{
                  //System.out.println("fragment,Today,handler,else part (unknown error)");

                }


            }
        };

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
              //System.out.println(words1.get(arg2).getTrainNo() + " : ");

                String Date= ""+ trn_bw_2_stn.cal.get(Calendar.DAY_OF_MONTH);
                String Month= trn_bw_2_stn.Month[trn_bw_2_stn.cal.get(Calendar.MONTH)];
                String Year =""+trn_bw_2_stn.cal.get(Calendar.YEAR);
                String journeyDate=Date +" "+Month+" "+Year;
              //System.out.println("Today tab,listview1,onclick ,journeyDate:"+journeyDate);
                try {

                    Intent i = new Intent(getActivity(), live_train_status_selected_item.class);

                    i.putExtra("trainNo",words1.get(arg2).getTrainNo());
                    i.putExtra("trainName",words1.get(arg2).getTrainName());
                    i.putExtra("fromStn",sd.getString("src_code",""));
                    i.putExtra("journeyDate",journeyDate);
                    i.putExtra("origin","train_bw_2_stn_today");
                    startActivity(i);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //System.out.println("fragment,Today,retrybutton");

                sd.edit().putBoolean("gotdnlddata",false).apply();
                sd.edit().putString("dnlddataTbts","").apply();
                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                Worker worker =new Worker("trn_bw_stns");
                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),filter,null);

                Thread thread0 = new Thread(worker);
                if(dnlddata==null & !sd.getBoolean("gotdnlddata",false)) {
                  //System.out.println("fragment,Today,retrybutton,if part(worker thread started)");

                  //System.out.println("thread0 state :"+thread0.getState());
                    thread0.start();

                    sd.edit().putBoolean("gotdnlddata",true).apply();
                }
            }
        });


        oncreateCreated1=true;
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
      //System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+ trn_bw_2_stn.tabindex);

            if (isVisibleToUser && trn_bw_2_stn.tabindex == 1) {

             //System.out.println("first if ..........");
               Thread cheaker= new Thread("threadT1"){
                   @Override
                   public void run() {
                         if(getviewcheck()){
                           //System.out.println("if part(getviewcheck=true)");
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {

                                   //System.out.println("main thread :"+Thread.currentThread().getName());
                                     getActivity().runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             if(Adapter2 !=null){

                                             }else
                                             if (!sd.getString("dnlddataTbts", "").equals("")) {
                                                thread1 = new Thread(new Info_extractor("trn_bw_stns", handler, "today", null, null, sd));
                                                thread1.start();
                                            } else if (sd.getString("dnlddataTbts", "").equals("")) {
                                                Worker worker = new Worker("trn_bw_stns");
                                                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter, null);
                                                Thread thread0 = new Thread(worker);
                                                thread0.start();
                                                sd.edit().putBoolean("gotdnlddata", true).apply();
                                            }
                                         }
                                     });
                                 }
                             });
                         }else{
                           //System.out.println(" unable to understand......");

                         }


                   }
               };
//
                cheaker.start();
            }else{
              //System.out.println("else part of isVisibleToUser && tbts_test.tabindex :"+ trn_bw_2_stn.tabindex);

            }
        }

    private Boolean getviewcheck() {
        Boolean giveback=false;
      //System.out.println("under getviewcheck fn");
        while(oncreateCreated1 !=true){
            try {
                Thread.currentThread().sleep(20);
              //System.out.println(Thread.currentThread().getName()+",whlie,sleep 100 ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(oncreateCreated1){
          //System.out.println(Thread.currentThread().getName()+","+"getview() != null");
            giveback=true;
        }else if (!oncreateCreated1){
          //System.out.println(Thread.currentThread().getName()+","+"getview() = null");
            giveback=false;
        }
        return giveback;
    }

}