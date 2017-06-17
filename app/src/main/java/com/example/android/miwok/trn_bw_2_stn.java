package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class trn_bw_2_stn extends AppCompatActivity {

    Handler OnCreatehandler;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Handler handler;
    protected String dnlddata=null;
    String origin = null;
    SharedPreferences sd = null;
    trn_bw_2_stn_ItemList_Adaptor Adapter;
    ArrayList<trn_bw_2_stn_Items_Class> words=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trn_bw2_stn);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        String Month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String DayOfWeek[]={"","Sun","Mon","Tue","Wed","Thr","Fri","Sat"};
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Date date= new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        tab1.setText("Today\n"+DayOfWeek[cal.get(Calendar.DAY_OF_WEEK)]+","+cal.get(Calendar.DAY_OF_MONTH)+" "+Month[cal.get(Calendar.MONTH)]);

        TabLayout.Tab tab3 = tabLayout.getTabAt(3);
        tab3.setIcon(R.drawable.cale);
        Log.i("current tab", String.valueOf(tabLayout.getSelectedTabPosition()));

        TextView src_stn = (TextView) findViewById(R.id.src_stn);
        src_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "src_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
            }
        });
        TextView dstn_stn = (TextView) findViewById(R.id.dstn_stn);
        dstn_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();

            }
        });

        origin = this.getIntent().getStringExtra("origin");
        if (origin.equals("main_act_src_stn")) {
            sd.edit().putString("src_name", this.getIntent().getStringExtra("src_name")).apply();
            sd.edit().putString("src_code", this.getIntent().getStringExtra("src_code")).apply();
            Log.i("src_name", sd.getString("src_name", ""));
            src_stn.setText(this.getIntent().getStringExtra("src_name"));


                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();


        }
        else if (origin.equals("src_stn")) {
            sd.edit().putString("src_name", this.getIntent().getStringExtra("src_name")).apply();
            sd.edit().putString("src_code", this.getIntent().getStringExtra("src_code")).apply();
            Log.i("src_name", sd.getString("src_name", ""));
            src_stn.setText(this.getIntent().getStringExtra("src_name"));

            if (sd.getString("dstn_code", "") != "") {
                dstn_stn.setText(sd.getString("dstn_name", ""));
            } else {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
            }

        } else if (origin.equals("dstn_stn")) {
            sd.edit().putString("dstn_name", this.getIntent().getStringExtra("dstn_name")).apply();
            sd.edit().putString("dstn_code", this.getIntent().getStringExtra("dstn_code")).apply();
            Log.i("dstn_name", sd.getString("dstn_name", ""));
            dstn_stn.setText(this.getIntent().getStringExtra("dstn_name"));

            if (sd.getString("src_code", "") != "") {
                src_stn.setText(sd.getString("src_name", ""));
            }


        } else if (origin.equals("main_activity")) {
            sd.edit().putString("src_name", "").apply();
            sd.edit().putString("src_code", "").apply();
            Log.i("src_name", sd.getString("src_name", ""));
            sd.edit().putString("dstn_name", "").apply();
            sd.edit().putString("dstn_code", "").apply();
            Log.i("dstn_name", sd.getString("dstn_name", ""));
            src_stn.setText("Source");
            dstn_stn.setText("Destination");
        }



            sd.edit().putBoolean("gotdnlddata",false).apply();
            sd.edit().putString("dnlddataTbts","").apply();






    }



    public static class PlaceholderFragment extends Fragment {

Handler handler;
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
        Handler TBTSLiveHandler;

        String Month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String DayOfWeek[]={"","Sun","Mon","Tue","Wed","Thr","Fri","Sat"};

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }




       private Boolean TBTSLiveOpen=false;
        public void RetryTask(View view) {
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
        public void LiveRetryTask(View view) {
            progressbar.setVisibility(View.VISIBLE);
            disp_msg.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
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



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.getSelectedTabPosition();
        rootView = inflater.inflate(R.layout.fragment_sub_page01, container, false);
        loading = (LinearLayout)rootView.findViewById(R.id.loading);
        disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        listview = (ListView) rootView.findViewById(R.id.listview);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);



        final Handler OnCreateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("inside oncreate handler.....");
                customObject myobj=(customObject) msg.obj;
                dnlddata= myobj.getResult();
                sd.edit().putString("dnlddataTbts",dnlddata).apply();


                if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                    System.out.println("under page 1");

                    thread1 = new Thread(new Info_extractor("trn_bw_stns", handler,"all",null,null,sd));
                    thread1.start();
                }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                    System.out.println("under page 2");

                    thread2 = new Thread(new Info_extractor("trn_bw_stns", handler,"today",null,null,sd));
                    thread2.start();

                }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                    System.out.println("under page 3");
//                    thread3 = new Thread(new Info_extractor("trn_bw_stns", handler,"tomorrow",null,null,sd));
//                    thread3.start();

                }

            }


        };



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("handler called.....inside fragment");
                customObject myobj =(customObject)msg.obj;
                System.out.println("yes got the output");
                if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
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

                }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                    System.out.println("yes got the output 2");

                    if(myobj.getResult().equals("success")) {
                        words2 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                        Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words2);
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview = (ListView) rootView.findViewById(R.id.listview);
                        listview.setAdapter(Adapter);
                    }else if(myobj.getResult().equals("error")){
                        progressbar.setVisibility(View.GONE);
                        disp_msg.setVisibility(View.VISIBLE);
                        retryButton.setVisibility(View.VISIBLE);
                        disp_msg.setText(myobj.getErrorMsg());
                        Log.e("error",myobj.getErrorMsg());
                    }else{
                        System.out.println("inside handler...dont know error");
                    }

                }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){


                }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                    System.out.println("yes got the output 4");

                    if(myobj.getResult().equals("success")) {
                        words4 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                        Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words4);
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview.setAdapter(Adapter);
                        fab.setVisibility(View.VISIBLE);
                    }else if(myobj.getResult().equals("error")){
                        progressbar.setVisibility(View.GONE);
                        disp_msg.setVisibility(View.VISIBLE);
                        retryButton.setVisibility(View.VISIBLE);
                        disp_msg.setText(myobj.getErrorMsg());
                        Log.e("error",myobj.getErrorMsg());
                    }else{
                        System.out.println("inside handler...dont know error");
                    }

                }else{
                    System.out.println("bhupesh tura ........");
                }

            }
        };





        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            System.out.println("under page 1");
            Worker worker =new Worker("trn_bw_stns");
            worker.Input_Details(sd, OnCreateHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
            thread0 = new Thread(worker);
            if(dnlddata==null & !sd.getBoolean("gotdnlddata",false) & !thread0.getState().equals("RUNNABLE")) {
                thread0.start();
                thread0.setName("downloaderTBTS");
                sd.edit().putBoolean("gotdnlddata",true).apply();
            }

        }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
            Worker worker =new Worker("trn_bw_stns");
            worker.Input_Details(sd, OnCreateHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""));
            thread0 = new Thread(worker);
            if(dnlddata==null & !sd.getBoolean("gotdnlddata",false) & !thread0.getState().equals("RUNNABLE")) {
                thread0.start();
                thread0.setName("downloaderTBTS");
                sd.edit().putBoolean("gotdnlddata",true).apply();
            }

        }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
            rootView = inflater.inflate(R.layout.fragment_sub_page02, container, false);
            loading = (LinearLayout)rootView.findViewById(R.id.loading);
            disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
            progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
            listview = (ListView) rootView.findViewById(R.id.listview);
            LiveRetryButton =(Button)rootView.findViewById(R.id.LiveRetryButton);

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
                        threadu.start();
                    } else {
                        System.out.println("error inside page 3!!!!");
                    }
                }
            });

        }else  if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
            System.out.println("under page 4");

            rootView = null;
            rootView = inflater.inflate(R.layout.fragment_sub_page04, container, false);
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
                    TabLayout.Tab tab3 = tabLayout.getTabAt(3);
                    tab3.setText(day+" "+Month[Integer.parseInt(month)]);

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    datepickerlayout.setVisibility(View.VISIBLE);
                    TabLayout.Tab tab3 = tabLayout.getTabAt(3);
                    tab3.setIcon(R.drawable.cale);
                }

            });
        }

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

        TBTSLiveHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("inside TBTSLIVE handler.....");

                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success")) {
                    ArrayList<stn_status_Items_Class> words = (ArrayList<stn_status_Items_Class>) myobj.getStnsts();
                    TBTS_Live_ItemList_Adaptor Adapter = new TBTS_Live_ItemList_Adaptor(getActivity(),words);
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                        System.out.println("under page 3");
                        loading.setVisibility(View.GONE);
                        disp_content.setVisibility(View.VISIBLE);
                        listview.setAdapter(Adapter);

                    }
                }else if(myobj.getResult().equals("error")){
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    LiveRetryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }
            }


        };


        return rootView;
    }



        @Override
        public void onResume() {
            super.onResume();




            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                System.out.println("under page 1");

                    thread1 =new Thread(new Info_extractor("trn_bw_stns", handler, "all", null, thread0, sd));
                    thread1.start();

            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                    thread2 = new Thread(new Info_extractor("trn_bw_stns", handler, "today", null, thread0, sd));
                    thread2.start();

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {

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


    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return PlaceholderFragment.newInstance(i + 1);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "All";
                case 1:

                    return "Today";
                case 2:

                    return "Coming";
                case 3:

                    return "";
            }
            return null;
        }
    }

    
    
    
}
