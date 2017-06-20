package com.example.android.miwok;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class FirstFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter1=null;
    Thread thread1;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview;
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
    String data1;
    TabLayout tabLayout;

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

       Boolean oncreateCreated0=false;

    public FirstFragment() {
        // Required empty public constructor
    }
 String filter ="all";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("OnCreateView Page 1 : All Tab...");
        rootView = inflater.inflate(R.layout.fragment_first, container, false);
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        loading = (LinearLayout) rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout) rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview = (ListView) rootView.findViewById(R.id.listview);
        retryButton = (Button) rootView.findViewById(R.id.retryButton);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("fragment,All,handler");
                customObject myobj = (customObject) msg.obj;

                if (myobj.getResult().equals("success")) {
                    System.out.println("fragment,All,handler,if part(success)");

                    System.out.println(myobj.getResult());
                    words1 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                    Adapter1 = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words1);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listview = (ListView) rootView.findViewById(R.id.listview);
                    listview.setAdapter(Adapter1);
                } else if (myobj.getResult().equals("error")) {
                    System.out.println("fragment,All,handler,else if part(error)");

                    System.out.println(myobj.getResult());
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error", myobj.getErrorMsg());
                } else {
                    System.out.println("fragment,All,handler,else part(error)");

                }


            }
        };


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fragment,All,retrybutton");

                sd.edit().putBoolean("gotdnlddata", false).apply();
                sd.edit().putString("dnlddataTbts", "").apply();
                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                Worker worker = new Worker("trn_bw_stns");
                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter, null);

                Thread thread0 = new Thread(worker);
                if (dnlddata == null & !sd.getBoolean("gotdnlddata", false)) {

                    System.out.println("fragment,All,retrybutton,if part(worker thread started)");

                    thread0.start();
                    sd.edit().putBoolean("gotdnlddata", true).apply();
                } else {
                    System.out.println("fragment,All,retrybutton,else part(worker thread not started)");

                }
            }
        });

        oncreateCreated0=true;
        
        return rootView;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
      System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+tbts_test.tabindex);
        if (isVisibleToUser && tbts_test.tabindex == 0) {

            System.out.println("first if ..........");
            Thread cheaker= new Thread("threadT0"){
                @Override
                public void run() {
                    if(getviewcheck()){
                        System.out.println("if part(getviewcheck=true)");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("main thread :"+Thread.currentThread().getName());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                            if(Adapter1 !=null){

                                            }
                                              else if (!sd.getString("dnlddataTbts", "").equals("")) {
                                                thread1 = new Thread(new Info_extractor("trn_bw_stns", handler, "all", null, null, sd));
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
                        System.out.println(" unable to understand......");

                    }


                }
            };
//
            cheaker.start();
        }else{
            System.out.println("else part of isVisibleToUser && tbts_test.tabindex :"+tbts_test.tabindex);
        }
    }

    private Boolean getviewcheck() {
        Boolean giveback=false;
        System.out.println("under getviewcheck fn");
        while(oncreateCreated0 !=true){
            try {
                Thread.currentThread().sleep(20);
                System.out.println(Thread.currentThread().getName()+",whlie,sleep 100 ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(oncreateCreated0){
            System.out.println(Thread.currentThread().getName()+","+"getview() != null");
            giveback=true;
        }else if (!oncreateCreated0){
            System.out.println(Thread.currentThread().getName()+","+"getview() = null");
            giveback=false;
        }
        return giveback;
    }
}