package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class FirstFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter1=null;
    Thread thread1;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview0;
    ArrayList<trn_bw_2_stn_Items_Class> words0;
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

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mRelativeLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;

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
        listview0 = (ListView) rootView.findViewById(R.id.listview);
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
                    words0 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                    Adapter1 = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words0);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listview0 = (ListView) rootView.findViewById(R.id.listview);
                    listview0.setAdapter(Adapter1);
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
        mContext = getContext();

        // Get the activity
        mActivity = getActivity();

        // Get the widgets reference from XML layout
        mRelativeLayout = (LinearLayout) rootView.findViewById(R.id.disp_content);

        listview0.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
                System.out.println("TBTS,All,listview ,on clk item:"+words0.get(arg2).getTrainNo());

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.popup_window,null);

                        mPopupWindow = new PopupWindow(
                                customView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT

                        );
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                // Removes default background.
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // Set an elevation value for popup window
                        // Call requires API level 21
                        if(Build.VERSION.SDK_INT>=21){
                            mPopupWindow.setElevation(5.0f);
                        }

                        // Get a reference for the custom view close button
                     //   ImageView closeButton = (ImageView) customView.findViewById(R.id.ib_close);
                        Button trn_sch=(Button) customView.findViewById(R.id.trn_rt);
                        Button trn_live=(Button) customView.findViewById(R.id.trn_live);
                        // Set a click listener for the popup window close button
                trn_sch.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent i = new Intent(getActivity(), TrainSchdule.class);
                            i.putExtra("train_name", words0.get(arg2).getTrainName());
                            i.putExtra("train_no", words0.get(arg2).getTrainNo());
                            i.putExtra("origin", "tbts_all");
                            startActivity(i);
                            mPopupWindow.dismiss();
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }
                });

                trn_live.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {

                            Intent i = new Intent(getActivity(), live_train_options.class);
                            i.putExtra("train_no",words0.get(arg2).getTrainNo());
                            i.putExtra("train_name", words0.get(arg2).getTrainName());
                            i.putExtra("origin","tbts_all");
                            startActivity(i);
                            mPopupWindow.dismiss();
                         } catch (Exception e) {
                             e.fillInStackTrace();
                        }
                    }

                });

//                closeButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        mPopupWindow.dismiss();
//                    }
//                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

            }
        });

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
      System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+ trn_bw_2_stn.tabindex);
        if (isVisibleToUser && trn_bw_2_stn.tabindex == 0) {

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
            System.out.println("else part of isVisibleToUser && tbts_test.tabindex :"+ trn_bw_2_stn.tabindex);
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