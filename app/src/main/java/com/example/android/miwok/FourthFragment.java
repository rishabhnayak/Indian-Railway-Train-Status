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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FourthFragment extends Fragment {
    trn_bw_2_stn_ItemList_Adaptor Adapter;

    Thread thread4;
    RelativeLayout datepickerlayout;
    String origin = null;
    SharedPreferences sd = null;
    ListView listview3;
    DatePicker simpleDatePicker;
    Button submit;
    ArrayList<trn_bw_2_stn_Items_Class> words4;
    LinearLayout disp_content,loading;

    String dnlddata=null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton;
    FloatingActionButton fab;
    View rootView;
    Handler handler;

    Date date= new Date();
    Calendar cal= Calendar.getInstance();
    String filter="byDate";
    TabLayout tabLayout=trn_bw_2_stn.tabLayout;
    String []dateobj;
    public FourthFragment() {
        // Required empty public constructor
    }
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


        sd = getActivity().getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_fourth, container, false);
        listview3 = (ListView) rootView.findViewById(R.id.listview);
        loading = (LinearLayout)rootView.findViewById(R.id.loading);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        retryButton =(Button)rootView.findViewById(R.id.retryButton);
        disp_content = (LinearLayout)rootView.findViewById(R.id.disp_content);
        progressbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        disp_msg = (TextView) rootView.findViewById(R.id.disp_msg);
        simpleDatePicker = (DatePicker) rootView.findViewById(R.id.simpleDatePicker);
        loading.setVisibility(View.INVISIBLE);
        datepickerlayout = (RelativeLayout) rootView.findViewById(R.id.datepickerlayout);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              //System.out.println("handler called.....inside fragment");
                customObject myobj =(customObject)msg.obj;
              //System.out.println("yes got the output");

              //System.out.println("yes got the output byDate page");
                if(myobj.getResult().equals("success") && getActivity() !=null) {
                    words4 = (ArrayList<trn_bw_2_stn_Items_Class>) myobj.getTBTS();
                    Adapter = new trn_bw_2_stn_ItemList_Adaptor(getActivity(), words4);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listview3.setAdapter(Adapter);
                    fab.setVisibility(View.VISIBLE);
                }else if(myobj.getResult().equals("error")){
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }else{
                  //System.out.println("inside handler...dont know error");
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
                worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),filter,dateobj);

                Thread thread0 = new Thread(worker);
                if(dnlddata==null & !sd.getBoolean("gotdnlddata",false)) {

                  //System.out.println("thread0 state :"+thread0.getState());
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
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() );
                String year = "" + simpleDatePicker.getYear();
                datepickerlayout.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                simpleDatePicker.setCalendarViewShown(false);
                dateobj = new String []{day,month,year};
                cal.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
                Toast.makeText(getActivity().getApplicationContext(), day + "-" + month + "-" + year, Toast.LENGTH_LONG).show();
                tabLayout.getTabAt(3).setIcon(null);
                tabLayout.getTabAt(3).setText(trn_bw_2_stn.DayOfWeek[cal.get(Calendar.DAY_OF_WEEK)]+","+day+" "+trn_bw_2_stn.Month[Integer.parseInt(month)]);


                if(sd.getString("dnlddataTbts","").equals("")) {
                    Worker worker = new Worker("trn_bw_stns");
                    worker.Input_Details(sd, handler, sd.getString("src_code", ""), sd.getString("dstn_code", ""), filter,dateobj);

                    Thread thread0 = new Thread(worker);
                  //System.out.println("thread0 state :" + thread0.getState());
                    thread0.start();
                    thread0.setName("downloaderTBTS");
                    sd.edit().putBoolean("gotdnlddata", true).apply();
                }else{
                    thread4 = new Thread(new Info_extractor("trn_bw_stns", handler,filter,dateobj,null,sd));
                    thread4.start();
                }
            }
        });
        mContext = getContext();

        // Get the activity
        mActivity = getActivity();

        // Get the widgets reference from XML layout
        mRelativeLayout = (LinearLayout) rootView.findViewById(R.id.disp_content);

        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
              //System.out.println("TBTS,All,listview ,on clk item:"+words4.get(arg2).getTrainNo());


                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View customView = inflater.inflate(R.layout.popup_window,null);

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
            //    ImageView closeButton = (ImageView) customView.findViewById(R.id.ib_close);
                Button trn_sch=(Button) customView.findViewById(R.id.trn_rt);
                Button trn_live=(Button) customView.findViewById(R.id.trn_live);
                // Set a click listener for the popup window close button
                trn_sch.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent i = new Intent(getActivity(), TrainSchdule.class);
                            i.putExtra("train_name", words4.get(arg2).getTrainName());
                            i.putExtra("train_no", words4.get(arg2).getTrainNo());
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
                            i.putExtra("train_no",words4.get(arg2).getTrainNo());
                            i.putExtra("train_name", words4.get(arg2).getTrainName());
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
//                });;

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);


//                try {
//
//                    Intent i = new Intent(getActivity(), live_train_options.class);
//                    i.putExtra("train_no",words4.get(arg2).getTrainNo());
//                    i.putExtra("train_name", words4.get(arg2).getTrainName());
//                    i.putExtra("origin","tbts_date");
//                    startActivity(i);
//
//                } catch (Exception e) {
//                    e.fillInStackTrace();
//                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.GONE);
                disp_content.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                datepickerlayout.setVisibility(View.VISIBLE);
                tabLayout.getTabAt(3).setText("");
                tabLayout.getTabAt(3).setIcon(R.drawable.cale);
            }

        });
        return  rootView;
    }



 
}