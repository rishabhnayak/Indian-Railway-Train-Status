package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Comparator;

public class trn_bw_2_stn extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    String origin = null;
    SharedPreferences sd = null;

    ArrayList<trn_bw_2_stn_Items_Class> words=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_trn_bw2_stn);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

//            if (sd.getString("dstn_code", "") != "") {
//                dstn_stn.setText(sd.getString("dstn_name", ""));
//            } else {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin", "dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
           // }

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
        ProgressBar progressbar;
        TextView disp_msg;
        private static final String ARG_SECTION_NUMBER = "section_number";
        View rootView;




        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


boolean executed=false;
        Boolean threadcalled=false;

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


        handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        System.out.println("handler called.....inside fragment");
                        String data =String.valueOf(msg.arg1);
                        if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                            words1 = (ArrayList<trn_bw_2_stn_Items_Class>) msg.obj;
                            if(words1 != null) {

                            Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words1);
                                loading.setVisibility(View.GONE);
                                disp_content.setVisibility(View.VISIBLE);
                                listview = (ListView)rootView.findViewById(R.id.listview);
                                listview.setAdapter(Adapter);
                            }else{
                                System.out.println("words1 is null");
                            }
                        }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                            words2 = (ArrayList<trn_bw_2_stn_Items_Class>) msg.obj;
                            if(words2 != null) {
                                Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words2);
                                loading.setVisibility(View.GONE);
                                disp_content.setVisibility(View.VISIBLE);
                                listview = (ListView)rootView.findViewById(R.id.listview);
                                listview.setAdapter(Adapter);
                            }else{
                                System.out.println("words2 is null");
                            }
                        }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){


                            words3 = (ArrayList<trn_bw_2_stn_Items_Class>) msg.obj;
                            if(words3 != null) {
                                loading.setVisibility(View.GONE);
                                disp_content.setVisibility(View.VISIBLE);
                                Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words3);
                                listview = (ListView)rootView.findViewById(R.id.listview);
                                listview.setAdapter(Adapter);
                            }else{
                                System.out.println("words1 is null");
                            }
                        }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 4){


                            words4 = (ArrayList<trn_bw_2_stn_Items_Class>) msg.obj;
                            if(words4 != null) {
                                Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words4);
                                loading.setVisibility(View.GONE);
                                disp_content.setVisibility(View.VISIBLE);
                                listview = (ListView)rootView.findViewById(R.id.listview);
                                listview.setAdapter(Adapter);
                            }else{
                                System.out.println("words4 is null");
                            }
                        }else{
                            System.out.println("bhupesh tura ........");
                        }

                    }
                };


        if (sd.getString("src_code", "") != "" && sd.getString("dstn_code","") != "") {

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                thread1 = new Thread(new tbts_task(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),"all"));
                thread1.start();
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                thread2 = new Thread(new tbts_task(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),"today"));
                thread2.start();
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                thread3 = new Thread(new tbts_task(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),"tomorrow"));
                thread3.start();
            }else  if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {

                rootView = null;
                rootView = inflater.inflate(R.layout.fragment_sub_page04, container, false);
                listview = (ListView) rootView.findViewById(R.id.listview);
                loading = (LinearLayout)rootView.findViewById(R.id.loading);
                disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
                progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
                disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
                simpleDatePicker = (DatePicker) rootView.findViewById(R.id.simpleDatePicker);

                datepickerlayout = (RelativeLayout) rootView.findViewById(R.id.datepickerlayout);

              //  tablelayout = (LinearLayout) rootView.findViewById(R.id.tablelayout);

                submit = (Button) rootView.findViewById(R.id.submitButton);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get the values for day of month , month and year from a date picker
                        String day = "" + simpleDatePicker.getDayOfMonth();
                        String month = "" + (simpleDatePicker.getMonth() + 1);
                        String year = "" + simpleDatePicker.getYear();
                        datepickerlayout.setVisibility(View.INVISIBLE);
                        //tablelayout.setVisibility(View.VISIBLE);
                        // display the values by using a toast
                        loading.setVisibility(View.VISIBLE);
                        simpleDatePicker.setCalendarViewShown(false);
                        String []dateobj =new String []{day,month,year};

                        Toast.makeText(getActivity().getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();
                   //     rootView = inflater.inflate(R.layout.fragment_sub_page01, container, false);
//                        getTrn_bw2_stn(sd.getString("src_code", ""), sd.getString("dstn_code", ""), "byDate", "" + year + "," + month + "," + day);
                        thread4 = new Thread(new tbts_task(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),"byDate",dateobj));
                        thread4.start();



                    }
                });
            }
        }


        return rootView;
    }

        @Override
        public void onStart() {
            super.onStart();
//            Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words);
           
//            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
//System.out.println(" on start 1 ");
//                if(words1 != null) {
//                    listview = (ListView)rootView.findViewById(R.id.listview);
//                    listview.setAdapter(Adapter);
//                }
//            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
//                System.out.println(" on start 2 ");
//                if(words2 != null) {
//                    listview = (ListView)rootView.findViewById(R.id.listview);
//                    listview.setAdapter(Adapter);
//                }
//            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
//                System.out.println(" on start 3 ");
//                if(words3 != null) {
//                    listview = (ListView)rootView.findViewById(R.id.listview);
//                    listview.setAdapter(Adapter);
//                }
//            }else {
//                System.out.println(" on Resume 0 ");
//            }
        }

        @Override
        public void onResume() {
            super.onResume();
 //           listview.setAdapter(Adapter);
//            Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                System.out.println(" on resume 1 ");
                if(words1 != null) {
                    listview = (ListView)rootView.findViewById(R.id.listview);
                    listview.setAdapter(Adapter);
                }
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                System.out.println(" on resume 2 ");
                if(words2 != null) {
                    listview = (ListView)rootView.findViewById(R.id.listview);
                    listview.setAdapter(Adapter);
                }
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                System.out.println(" on resume 3 ");
                if(words3 != null) {
                    listview = (ListView)rootView.findViewById(R.id.listview);
                    listview.setAdapter(Adapter);
                }
            }else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                System.out.println(" on resume 4 ");
                if (words4 != null) {
                    listview = (ListView) rootView.findViewById(R.id.listview);
                    listview.setAdapter(Adapter);
                }
            }else {
                System.out.println(" on Resume 0 ");
            }
        }

        @Override
        public void onPause() {
            super.onPause();

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                System.out.println(" on Pause 1 ");

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

                System.out.println(" on Pause 2 ");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                System.out.println(" on Pause 3 ");
            }
        }

        @Override
        public void onStop() {
            super.onStop();


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                System.out.println(" on Stop 1 ");

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

                System.out.println(" on Stop 2 ");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                System.out.println(" on Stop 3 ");
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
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "All";
                case 1:

                    return "today";
                case 2:

                    return "tomorow";
                case 3:

                    return "Date";
            }
            return null;
        }
    }

    
    
    
}
