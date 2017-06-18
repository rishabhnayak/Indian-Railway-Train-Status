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
    trn_bw_2_stn_ItemList_Adaptor Adapter;
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

        rootView=inflater.inflate(R.layout.fragment_first, container, false);
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        loading = (LinearLayout)rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview = (ListView) rootView.findViewById(R.id.listview);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("handler called.....inside fragment");
                customObject myobj =(customObject)msg.obj;
                System.out.println("yes got the output");

                    System.out.println("yes got the output 1");
                    if(myobj.getResult().equals("success")) {

                        System.out.println(myobj.getResult());
                        words1 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                        Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words1);
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview = (ListView) rootView.findViewById(R.id.listview);
                        listview.setAdapter(Adapter);
                    }else if(myobj.getResult().equals("error")){
                        System.out.println(myobj.getResult());
                        progressbar.setVisibility(View.GONE);
                        disp_msg.setVisibility(View.VISIBLE);
                        retryButton.setVisibility(View.VISIBLE);
                        disp_msg.setText(myobj.getErrorMsg());
                        Log.e("error",myobj.getErrorMsg());
                    }else{
                        System.out.println("inside handler...dont know error");
                    }


            }
        };



        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.edit().putBoolean("gotdnlddata",false).apply();
                sd.edit().putString("dnlddataTbts","").apply();
                progressbar.setVisibility(View.VISIBLE);
                disp_msg.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                Worker worker =new Worker("trn_bw_stns");
                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),filter,null);

                Thread thread0 = new Thread(worker);
                if(dnlddata==null & !sd.getBoolean("gotdnlddata",false)) {

                    System.out.println("thread0 state :"+thread0.getState());
                    thread0.start();
                    thread0.setName("downloaderTBTS");
                    sd.edit().putBoolean("gotdnlddata",true).apply();
                }
            }
        });



         return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(sd.getString("dnlddataTbts","").equals("")) {
            Worker worker = new Worker("trn_bw_stns");
            worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter,null);

            Thread thread0 = new Thread(worker);


            System.out.println("thread0 state :" + thread0.getState());
            thread0.start();
            sd.edit().putBoolean("gotdnlddata", true).apply();
        }else {
            thread1 = new Thread(new Info_extractor("trn_bw_stns", handler, "all", null, thread0, sd));
            thread1.start();
        }
    }
}