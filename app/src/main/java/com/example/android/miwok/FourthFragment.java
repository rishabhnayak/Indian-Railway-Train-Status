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

public class FourthFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter;

    Thread thread1,thread2,thread3,thread4;
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
    LinearLayout disp_content,loading;
    Handler OnCreateHandler;
    String dnlddata=null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton,LiveRetryButton;
    Thread thread0 = null;
    FloatingActionButton fab;
    private static final String ARG_SECTION_NUMBER = "section_number";
    View rootView;
    Handler TBTSLiveHandler,handler;

    String Month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    String DayOfWeek[]={"","Sun","Mon","Tue","Wed","Thr","Fri","Sat"};

    TabLayout tabLayout;

    public FourthFragment() {
        // Required empty public constructor
    }
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_fourth, container, false);
        listview = (ListView) rootView.findViewById(R.id.listview);
        loading = (LinearLayout)rootView.findViewById(R.id.loading);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);
        disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        simpleDatePicker = (DatePicker) rootView.findViewById(R.id.simpleDatePicker);
        loading.setVisibility(View.INVISIBLE);
        datepickerlayout = (RelativeLayout) rootView.findViewById(R.id.datepickerlayout);

        //  tablelayout = (LinearLayout) rootView.findViewById(R.id.tablelayout);

        final Handler OnCreateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("inside oncreate handler.....");
                customObject myobj=(customObject) msg.obj;
                dnlddata= myobj.getResult();
                sd.edit().putString("dnlddataTbts",dnlddata).apply();

                thread1 = new Thread(new Info_extractor("trn_bw_stns", handler,"all",null,null,sd));
                thread1.start();

            }


        };



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
                    fab.setVisibility(View.VISIBLE);
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
                worker.Input_Details(sd, OnCreateHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));

                Thread thread0 = new Thread(worker);
                if(dnlddata==null & !sd.getBoolean("gotdnlddata",false)) {

                    System.out.println("thread0 state :"+thread0.getState());
                    thread0.start();
                    thread0.setName("downloaderTBTS");
                    sd.edit().putBoolean("gotdnlddata",true).apply();
                }
            }
        });

        submit = (Button) rootView.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() );
                String year = "" + simpleDatePicker.getYear();
                datepickerlayout.setVisibility(View.INVISIBLE);
                //tablelayout.setVisibility(View.VISIBLE);
                // display the values by using a toast
                loading.setVisibility(View.VISIBLE);

                simpleDatePicker.setCalendarViewShown(false);
                String []dateobj =new String []{day,month,year};

                Toast.makeText(getActivity().getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();
                thread4 = new Thread(new Info_extractor("trn_bw_stns", handler,"byDate",dateobj,null,sd));
                thread4.start();
            //    TabLayout.Tab tab3 = tabLayout.getTabAt(3);
            //    tab3.setText(day+" "+Month[Integer.parseInt(month)]);
                tbts_test.fourthTab.setText(day+" "+Month[Integer.parseInt(month)]);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.GONE);
                disp_content.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                datepickerlayout.setVisibility(View.VISIBLE);
             //   TabLayout.Tab tab3 = tabLayout.getTabAt(3);
              //  tab3.setIcon(R.drawable.cale);
                tbts_test.fourthTab.setIcon(R.drawable.cale);
            }

        });
        return  rootView;
    }



 
}