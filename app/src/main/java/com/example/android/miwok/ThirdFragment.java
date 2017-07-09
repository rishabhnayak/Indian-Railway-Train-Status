package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {
    TBTS_Live_ItemList_Adaptor Adapter3=null;

    Thread  thread4;
    String value;
    String key;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview2;
    DatePicker simpleDatePicker;
    Button submit;
    ArrayList<stn_status_Items_Class> words3;

    LinearLayout disp_content, loading;
    Handler OnCreateHandler;
    String dnlddata = null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton, LiveRetryButton;

    FloatingActionButton fab;

    View rootView;
    Handler TBTSLiveHandler;
    Boolean oncreateCreated2=false;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //System.out.println("OnCreateView Page 3 : Coming Tab...");
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_third, container, false);
        loading = (LinearLayout) rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout) rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview2 = (ListView) rootView.findViewById(R.id.listview);
        TBTSLiveHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              //System.out.println("");
              //System.out.println("fragment,coming,TBTSLiveHandler");

                customObject myobj = (customObject) msg.obj;
                if (myobj.getResult().equals("success") && getActivity() !=null) {

                    words3 = (ArrayList<stn_status_Items_Class>) myobj.getStnsts();
                    Adapter3 = new TBTS_Live_ItemList_Adaptor(getActivity(), words3);

                      //System.out.println("fragment,coming,TBTSLiveHandler,success");
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview2.setAdapter(Adapter3);

                } else if (myobj.getResult().equals("error")) {
                  //System.out.println("fragment,coming,TBTSLiveHandler,error");

                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    LiveRetryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error", myobj.getErrorMsg());
                }
            }


        };
        LiveRetryButton = (Button) rootView.findViewById(R.id.LiveRetryButton);

        LiveRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //System.out.println("fragment,coming,LiveRetryButton on click");

                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                LiveRetryButton.setVisibility(View.GONE);
                Worker worker1 = new Worker("tbts_upcoming");
                worker1.Input_Details(sd, TBTSLiveHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
                loading.setVisibility(View.VISIBLE);
                disp_content.setVisibility(View.INVISIBLE);
                Thread threadu = new Thread(worker1);
                if (!threadu.getState().equals("RUNNABLE") || !threadu.getState().equals("WAITING")) {
                  //System.out.println("fragment,coming,LiveRetryButton ,if part(worker thread restart)");
                    threadu.start();
                } else {
                  //System.out.println("fragment,coming,LiveRetryButton ,else part(worker thread not restarted error)");
                }
            }
        });


        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Object item = arg0.getItemAtPosition(arg2);
              //System.out.println(words3.get(arg2).getTrainNo() + " : "+words3.get(arg2).getStartDate());

                try {

                    Intent i = new Intent(getActivity(), live_train_status_selected_item.class);

                    i.putExtra("trainNo",words3.get(arg2).getTrainNo());
                    i.putExtra("trainName",words3.get(arg2).getTrainName());
                    i.putExtra("startDate",words3.get(arg2).getStartDate());
                    i.putExtra("origin","train_bw_2_stn_upcoming");
                    startActivity(i);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            }
        });


        oncreateCreated2=true;
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
      //System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+ trn_bw_2_stn.tabindex);
        if (isVisibleToUser && trn_bw_2_stn.tabindex == 2) {

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
                                        if(Adapter3 !=null) {

                                        }else {
                                            Worker worker1 = new Worker("tbts_upcoming");
                                            worker1.Input_Details(sd, TBTSLiveHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
                                            loading.setVisibility(View.VISIBLE);
                                            disp_content.setVisibility(View.INVISIBLE);
                                            Thread threadu = new Thread(worker1);
                                          //System.out.println("fragment,coming,worker defined,if part(worker thread start)");
                                            threadu.start();
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
        while(oncreateCreated2 !=true){
            try {
                Thread.currentThread().sleep(20);
              //System.out.println(Thread.currentThread().getName()+",whlie,sleep 100 ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(oncreateCreated2){
          //System.out.println(Thread.currentThread().getName()+","+"getview() != null");
            giveback=true;
        }else if (!oncreateCreated2){
          //System.out.println(Thread.currentThread().getName()+","+"getview() = null");
            giveback=false;
        }
        return giveback;
    }





}