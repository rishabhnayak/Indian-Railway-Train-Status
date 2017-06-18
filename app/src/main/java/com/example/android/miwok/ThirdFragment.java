package com.example.android.miwok;

import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter;

    Thread thread1, thread2, thread3, thread4;
    String receiveddata = null;
    RelativeLayout datepickerlayout;
    LinearLayout tablelayout;
    String value;
    String key;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview;
    DatePicker simpleDatePicker;
    Button submit;
    ArrayList<trn_bw_2_stn_Items_Class> words1;
    ArrayList<trn_bw_2_stn_Items_Class> words2;
    ArrayList<trn_bw_2_stn_Items_Class> words3;
    ArrayList<trn_bw_2_stn_Items_Class> words4;
    LinearLayout disp_content, loading;
    Handler OnCreateHandler;
    String dnlddata = null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton, LiveRetryButton;
    Thread thread0 = null;
    FloatingActionButton fab;
    private static final String ARG_SECTION_NUMBER = "section_number";
    View rootView;
    Handler TBTSLiveHandler;

    String Month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String DayOfWeek[] = {"", "Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"};


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
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_third, container, false);
        loading = (LinearLayout) rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout) rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview = (ListView) rootView.findViewById(R.id.listview);
        TBTSLiveHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("inside TBTSLIVE handler.....");

                customObject myobj = (customObject) msg.obj;
                if (myobj.getResult().equals("success")) {
                    ArrayList<stn_status_Items_Class> words = (ArrayList<stn_status_Items_Class>) myobj.getStnsts();
                    TBTS_Live_ItemList_Adaptor Adapter = new TBTS_Live_ItemList_Adaptor(getActivity(), words);

                        System.out.println("under page 3");
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview.setAdapter(Adapter);

                } else if (myobj.getResult().equals("error")) {
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
                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                LiveRetryButton.setVisibility(View.GONE);
                Worker worker1 = new Worker("tbts_upcoming");
                worker1.Input_Details(sd, TBTSLiveHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
                loading.setVisibility(View.VISIBLE);
                disp_content.setVisibility(View.INVISIBLE);
                Thread threadu = new Thread(worker1);
                if (!threadu.getState().equals("RUNNABLE") || !threadu.getState().equals("WAITING")) {
                    System.out.println("threadu state :" + threadu.getState());
                    threadu.setPriority(Thread.MAX_PRIORITY);
                    threadu.start();
                } else {
                    System.out.println("error inside page 3!!!!");
                }
            }
        });

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        Worker worker1 = new Worker("tbts_upcoming");
        worker1.Input_Details(sd, TBTSLiveHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
        loading.setVisibility(View.VISIBLE);
        disp_content.setVisibility(View.INVISIBLE);
        Thread threadu = new Thread(worker1);
        if (!threadu.getState().equals("RUNNABLE") || !threadu.getState().equals("WAITING")) {
            System.out.println("threadu state :" + threadu.getState());
            threadu.start();
        } else {
            System.out.println("error inside page 3!!!!");

        }
    }
}