package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class FirstFragment extends Fragment {
    ArrayList<trn_bw_2_stn_Items_Class> words =new ArrayList<trn_bw_2_stn_Items_Class>();
    View rootView;
    trn_bw_2_stn_ItemList_Adaptor Adapter;
      Handler handler;
    Thread thread;
    SharedPreferences sd = null;
    ListView listView;
    public FirstFragment() {
        // Required empty public constructor
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sub_page01, container, false);
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        // Inflate the layout for this fragment

//        words.add(new trn_bw_2_stn_Items_Class("sNo","trainNo"," trainName"," runsFromStn"," src"," srcCode"," dstn"," dstnCode"," fromStn"," fromStnCode"," toStn"," toStnCode"," depAtFromStn"," arrAtToStn"," travelTime"," trainType"));
//        Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words);
//
//        listView = (ListView)getActivity().findViewById(R.id.listview);
//        listView.setVisibility(View.VISIBLE);
//        listView.setAdapter(Adapter);

        System.out.println("bharat mata ki jai");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //   super.handleMessage(msg);
                //       test.setText(msg.arg1);
                String data =String.valueOf(msg.arg1);
                // test.setText(data);
                words = (ArrayList<trn_bw_2_stn_Items_Class>) msg.obj;
                words.add(new trn_bw_2_stn_Items_Class("sNo","trainNo"," trainName"," runsFromStn"," src"," srcCode"," dstn"," dstnCode"," fromStn"," fromStnCode"," toStn"," toStnCode"," depAtFromStn"," arrAtToStn"," travelTime"," trainType"));
                Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words);

                listView = (ListView)getActivity().findViewById(R.id.listview);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(Adapter);
                System.out.println(words.toString());
                System.out.println(words.get(words.size()-1).getTrainName());

                System.out.println("here is the msg :" + msg.arg1);
            }
        };
//        thread = new Thread(new tbts_task(sd,handler));
//        thread.start();
//        TextView textView =(TextView)getActivity().findViewById(R.id.textview);
//        textView.setText("bhaibhai");

    }
}