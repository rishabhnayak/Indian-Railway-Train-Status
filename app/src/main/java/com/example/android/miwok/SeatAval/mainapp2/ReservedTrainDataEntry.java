package com.example.android.miwok.SeatAval.mainapp2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.miwok.MainActivity;
import com.example.android.miwok.R;
import com.example.android.miwok.SeatAval.mainapp2.Stations.Seat_Select_Station;


public class ReservedTrainDataEntry extends AppCompatActivity {
    StringBuffer response;
    int year,day,month;
    TextView departureDate,stn_src,stn_dstn;
    String name,code,test,test2,restoredName1,restoredCode1,restoredName2,restoredCode2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_activity_reserved_train_data_entry);
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        restoredName1 = prefs.getString("name1",null);
        restoredCode1 = prefs.getString("code1",null);
        restoredName2 = prefs.getString("name2",null);
        restoredCode2 = prefs.getString("code2",null);
//
//        //adding BufferedReader
//        try {
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(getApplicationContext().getAssets().open("rtbs.json")));
//            response=new StringBuffer();
//            String line;
//            while ((line=buffer.readLine())!=null){
//                response.append(line);
//                response.append("\n");
//            }
//        } catch (IOException e) {
//        }
//        //data is in string form:json format assingned in responsed
//        String responsed=response.toString();
//        System.out.println(responsed);
//
        final DatePicker dp= (DatePicker) findViewById(R.id.datepicker);
        dp.setMinDate(System.currentTimeMillis()-1000);
          day=dp.getDayOfMonth();
          month=dp.getMonth()+1;
          year=dp.getYear();

        departureDate= (TextView) findViewById(R.id.ddate);
        departureDate.setText(day+"/"+month+"/"+year);

        findViewById(R.id.datepicker).setVisibility(View.GONE);

        CardView ddbutton= (CardView) findViewById(R.id.ddbutton);

        ddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.datepicker).setVisibility(View.VISIBLE);
                findViewById(R.id.ddbutton).setVisibility(View.GONE);
                findViewById(R.id.to_from).setVisibility(View.GONE);
                findViewById(R.id.cardview).setVisibility(View.VISIBLE);
                findViewById(R.id.button_search).setVisibility(View.GONE);
            }
        });

       ImageButton su= (ImageButton) findViewById(R.id.submitButton);
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.ddbutton).setVisibility(View.VISIBLE);
                findViewById(R.id.cardview).setVisibility(View.GONE);
                findViewById(R.id.to_from).setVisibility(View.VISIBLE);
                findViewById(R.id.button_search).setVisibility(View.VISIBLE);
                findViewById(R.id.datepicker).setVisibility(View.GONE);
                day=dp.getDayOfMonth();
                month=dp.getMonth()+1;
                year=dp.getYear();
                departureDate= (TextView) findViewById(R.id.ddate);
                departureDate.setText(day+"/"+month+"/"+year);
               // Toast.makeText(ReservedTrainDataEntry.this,String.valueOf(day), Toast.LENGTH_SHORT).show();
            }
        });


            Button button = (Button) findViewById(R.id.button_search);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (restoredCode1!=null&&restoredCode2!=null) {
                 //   Toast.makeText(ReservedTrainDataEntry.this, "working", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ReservedTrain.class);

                    if (day < 10) {
                        intent.putExtra("day", "0" + String.valueOf(day));
                    } else {
                        intent.putExtra("day", String.valueOf(day));
                    }
                    if (month < 10) {
                        intent.putExtra("month", "0" + String.valueOf(month));
                    }
                    intent.putExtra("year", String.valueOf(year));
                    intent.putExtra("to_name", restoredName1);
                    intent.putExtra("to_code", restoredCode1);
                    intent.putExtra("from_name", restoredName2);
                    intent.putExtra("from_code", restoredCode2);
                    startActivity(intent);
                }else {
                   Toast.makeText(ReservedTrainDataEntry.this, "Please Enter Station's", Toast.LENGTH_SHORT).show();
                }
                }
            });

        System.out.println("............................."+day+year+month);

         stn_src=(TextView)findViewById(R.id.stn_src);
         if (restoredCode1==null){

         }
         else {
             stn_src.setText(restoredName1 + "-" + restoredCode1);
         }

        stn_src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test="to";
                Intent intent=new Intent(getApplicationContext(), Seat_Select_Station.class);
                intent.putExtra("test",test);
                startActivity(intent);
            }
        });
        stn_dstn=(TextView)findViewById(R.id.stn_dstn);
        if (restoredCode2==null){

        }else{
            stn_dstn.setText(restoredName2 + "-" + restoredCode2);
        }

        stn_dstn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test="from";
                Intent intent=new Intent(getApplicationContext(), Seat_Select_Station.class);
                intent.putExtra("test",test);
                startActivity(intent);
            }
        });



        try{
            test2=getIntent().getExtras().getString("working");
        //    Toast.makeText(this, test2, Toast.LENGTH_SHORT).show();

            System.out.println("comming.........................................." + name + "-" + code);

            switch (test2){
                case "to":{
                //    Toast.makeText(this, test2, Toast.LENGTH_SHORT).show();
                    name = getIntent().getExtras().getString("towards_stn_name");
                    code = getIntent().getExtras().getString("towards_stn_code");
                    editor.putString("name1", name);
                    editor.putString("code1", code);
                    editor.apply();
                    restoredName1 = prefs.getString("name1",null);
                    restoredCode1 = prefs.getString("code1",null);
                    stn_src.setText(restoredName1 + "-" + restoredCode1);
                    break;
                }
                case "from":{
                    name = getIntent().getExtras().getString("towards_stn_name");
                    code = getIntent().getExtras().getString("towards_stn_code");
                    editor.putString("name2", name);
                    editor.putString("code2", code);
                    editor.apply();
                    restoredName2 = prefs.getString("name2",null);
                    restoredCode2 = prefs.getString("code2",null);
                    stn_dstn.setText(restoredName2 + "-" + restoredCode2);
                    break;
                }
                default:{
               //     Toast.makeText(this, "fuck", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }catch (Exception e){

        }

    }
    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//        System.exit(0);
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
