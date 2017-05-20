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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  static  SharedPreferences sd;
Boolean gotthekey=false;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading.. Please wait...", true);
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
        gotthekey=false;

        key_pass_generator key_pass_generator=new key_pass_generator(sd,dialog);
        key_pass_generator.start();
    }







    public void DivertedTrains_Activity(View view) {

        Intent i = new Intent(this, DivertedTrains.class);
        startActivity(i);
    }

    public void Phrases_Activity(View view) {
        Intent i = new Intent(this, PhrasesActivity.class);
        startActivity(i);
    }




}
