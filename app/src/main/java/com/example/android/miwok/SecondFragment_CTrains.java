package com.example.android.miwok;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class SecondFragment_CTrains extends Fragment {
    CancelledTrainsAdaptor_partially_Searchable Adapter1=null;
    Thread thread1;
    String origin = null;
    SharedPreferences sd = null;
    ListView listView1;
    ArrayList<CanceledTrainClass> words0;
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
    stnName_to_stnCode codeToName;


    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }


       Boolean oncreateCreated0=false;

    public SecondFragment_CTrains() {
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
        rootView = inflater.inflate(R.layout.fragment_first_ctrains, container, false);
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        loading = (LinearLayout) rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout) rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);

        listView1 = (ListView)rootView.findViewById(R.id.listview);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);
        codeToName = new stnName_to_stnCode(getActivity());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                System.out.println("task name:"+myobj.getTask_name());



                if(myobj.getResult().equals("success")) {
                    System.out.println("under success part....");

                    words0 = (ArrayList<CanceledTrainClass>) myobj.getCnsTrnList_partially();
                    System.out.println("size of list :"+words0.size()+"\n"+words0.get(1));
                    Adapter1 = new CancelledTrainsAdaptor_partially_Searchable(getActivity(),words0);
                    loading.setVisibility(View.GONE);
                    listView1 = (ListView) rootView.findViewById(R.id.listview);
                    disp_content.setVisibility(View.VISIBLE);
                    listView1.setVisibility(View.VISIBLE);
                    listView1.setAdapter(Adapter1);
                }else if(myobj.getResult().equals("error")){
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }

            }
        };
//        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                //    Log.d("############","Items " +  MoreItems[arg2] );
//                Object item = arg0.getItemAtPosition(arg2);
//                System.out.println("TBTS,All,listview ,on clk item:"+words.get(arg2).getTrainNo());
//                try {
//                    Intent i = new Intent(CanceledTrains.this, TrainSchdule.class);
//                    i.putExtra("train_name", words.get(arg2).getTrainName());
//                    i.putExtra("train_no", words.get(arg2).getTrainNo());
//                    i.putExtra("origin", "tbts_all");
//                    startActivity(i);
//
//                } catch (Exception e) {
//                    e.fillInStackTrace();
//
//                }
//
//            }
//        });
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              System.out.println("fragment,All,retrybutton");

                sd.edit().putBoolean("gotdnlddata", false).apply();
                sd.edit().putString("dnlddataTbts", "").apply();
                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
//                Worker worker = new Worker(getActivity(),"trn_bw_stns");
//                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter, null);
//
//                Thread thread0 = new Thread(worker);
//                if (dnlddata == null & !sd.getBoolean("gotdnlddata", false)) {
//
//                  System.out.println("fragment,All,retrybutton,if part(worker thread started)");
//
//                    thread0.start();
//                    sd.edit().putBoolean("gotdnlddata", true).apply();
//                } else {
//                  System.out.println("fragment,All,retrybutton,else part(worker thread not started)");
//
//                }
                Worker worker =new Worker(getActivity(),"canceledTrains");
                worker.Input_Details(sd,handler,codeToName);
                Thread thread =new Thread(worker);
                thread.start();
                System.out.println("thread state:"+thread.getState());
            }
        });

        oncreateCreated0=true;
        
        return rootView;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+ CanceledTrains.tabindex);
        if (isVisibleToUser && CanceledTrains.tabindex == 1) {

          System.out.println("first if ..........");
            Thread cheaker= new Thread("threadT0"){
                @Override
                public void run() {
                    if(getviewcheck()){
                      System.out.println("if part(getviewcheck=true)");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                       //       System.out.println("main thread :"+Thread.currentThread().getName());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                            if(Adapter1 !=null){

                                            }
                                            else if (!sd.getString("dnlddataCancelled", "").equals("")) {
                                                thread1 = new Thread(new Info_extractor("canceledTrains", handler,sd.getString("dnlddataCancelled","") , codeToName));
                                                thread1.start();
                                            }
                                            else
                                                if (sd.getString("dnlddataCancelled", "").equals("")) {
//                                                Worker worker = new Worker(getActivity(),"trn_bw_stns");
//                                                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter, null);
//                                                Thread thread0 = new Thread(worker);
//                                                thread0.start();
                                                Worker worker =new Worker(getActivity(),"canceledTrains");
                                                worker.Input_Details(sd,handler,codeToName);
                                                Thread thread =new Thread(worker);
                                                thread.start();
                                                System.out.println("thread state:"+thread.getState());
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
          System.out.println("else part of isVisibleToUser && tbts_test.tabindex :"+ CanceledTrains.tabindex);
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