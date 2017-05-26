/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
  static  SharedPreferences sd;
Boolean gotthekey=false;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dialog = ProgressDialog.show(MainActivity.this, "",
//                "Loading.. Please wait...", true);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_APPEND);

        sd.edit().putString("lastcall","0").apply();
        setContentView(R.layout.activity_main);
        TextView numbers= (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CanceledTrains.class);
                startActivity(i);
            }
        });
        TextView RescheduledTrains= (TextView) findViewById(R.id.RescheduledTrains);
        RescheduledTrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.android.miwok.RescheduledTrains.class);
                startActivity(i);
            }
        });
        TextView train_route= (TextView) findViewById(R.id.train_route);
        train_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TrainSchdule.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });

        TextView stn_sts= (TextView) findViewById(R.id.stn_sts);
        stn_sts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Station_Status.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });

        TextView trn_bw2_stn= (TextView) findViewById(R.id.trn_bw2_stn);
        trn_bw2_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent i = new Intent(MainActivity.this, trn_bw_2_stn.class);
                Intent i = new Intent(MainActivity.this, Select_Station.class);
                i.putExtra("origin","src_stn");
                startActivity(i);
            }
        });
        TextView live_train= (TextView) findViewById(R.id.live_train);
        live_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, live_train_options.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });





        LinearLayout cancel= (LinearLayout) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CanceledTrains.class);
                startActivity(i);
            }
        });
        LinearLayout RescheduledTrain= (LinearLayout) findViewById(R.id.RescheduledTrain);
        RescheduledTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, com.example.android.miwok.RescheduledTrains.class);
                startActivity(i);
            }
        });
        LinearLayout train_rout= (LinearLayout) findViewById(R.id.train_rout);
        train_rout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TrainSchdule.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });

        LinearLayout stn_st= (LinearLayout) findViewById(R.id.stn_st);
        stn_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Station_Status.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });

        LinearLayout trn_bw2_st= (LinearLayout) findViewById(R.id.trn_bw2_st);
        trn_bw2_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent i = new Intent(MainActivity.this, trn_bw_2_stn.class);
                Intent i = new Intent(MainActivity.this, Select_Station.class);
                i.putExtra("origin","src_stn");
                startActivity(i);
            }
        });
        LinearLayout live_trai= (LinearLayout) findViewById(R.id.live_trai);
        live_trai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, live_train_options.class);
                i.putExtra("origin","main_activity");
                startActivity(i);
            }
        });


//         stnName_to_stnCode codeToName=new stnName_to_stnCode(getApplicationContext());
//        System.out.println("here is value from countries :"+codeToName.stnName_to_stnCode("R"));



        gotthekey=false;

        key_pass_generator key_pass_generator=new key_pass_generator(sd,dialog);
        key_pass_generator.start();
    }


    class stnComp implements Comparator<AnimalNames> {


        @Override
        public int compare(AnimalNames animalNames, AnimalNames t1) {
           return animalNames.getAnimalNo().compareTo(t1.getAnimalNo());
        }
    }




    public void DivertedTrains_Activity(View view) {

        Intent i = new Intent(this, DivertedTrains.class);
        startActivity(i);
    }

    public void Phrases_Activity(View view) {
        Intent i = new Intent(this, PhrasesActivity.class);
        startActivity(i);
    }

//share...............
    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "share google play link";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share Google Play Link"));
                return true;

            case R.id.rate:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp"));
                startActivity(intent);
            return true;

//            case R.id.feedback:
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/html");
//                i.putExtra(Intent.EXTRA_EMAIL, "9644790733kamlesh@gmail.com");
//                i.putExtra(Intent.EXTRA_SUBJECT, "Your Valuable Feedback...");
//                i.putExtra(Intent.EXTRA_CC, "");
//                startActivity(Intent.createChooser(i, "Send Feedback"));
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
