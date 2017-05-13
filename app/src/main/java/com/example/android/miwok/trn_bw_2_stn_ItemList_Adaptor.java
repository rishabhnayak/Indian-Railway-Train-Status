package com.example.android.miwok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class trn_bw_2_stn_ItemList_Adaptor extends ArrayAdapter<trn_bw_2_stn_Items_Class>{


    public trn_bw_2_stn_ItemList_Adaptor(trn_bw_2_stn context, ArrayList<trn_bw_2_stn_Items_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.train_bw2_station_list_item, parent, false);
        }

        trn_bw_2_stn_Items_Class currentAndroidFlavor = getItem(position);

        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        trainNo.setText(currentAndroidFlavor.getTrainNo());

        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        trainName.setText(currentAndroidFlavor.getTrainName());


        return listItemView;
    }

}
