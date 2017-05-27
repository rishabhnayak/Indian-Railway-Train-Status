package com.example.android.miwok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sahu on 5/3/2017.
 */

public class RescheduledTrainsAdaptor extends ArrayAdapter<RescheduledTrainClass>{


    public RescheduledTrainsAdaptor(RescheduledTrains context, ArrayList<RescheduledTrainClass> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.rescheduled_trains_list_item, parent, false);
        }

        RescheduledTrainClass currentAndroidFlavor = getItem(position);

        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);
        TextView startDate= (TextView) listItemView.findViewById(R.id.startDate);
        TextView schTime= (TextView) listItemView.findViewById(R.id.scsTime);
        TextView reschTime= (TextView) listItemView.findViewById(R.id.resTime);
        TextView reschBy= (TextView) listItemView.findViewById(R.id.resBy);
        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);
        TextView newStartDate= (TextView) listItemView.findViewById(R.id.newStartDate);
        TextView trainType= (TextView) listItemView.findViewById(R.id.trainType);

        trainNo.setText(currentAndroidFlavor.getTrainNo());
        trainName.setText(currentAndroidFlavor.getTrainName());
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());
        startDate.setText(currentAndroidFlavor.getStartDate());
        schTime.setText(currentAndroidFlavor.getSchTime());
        reschTime.setText(currentAndroidFlavor.getReschTime());
        reschBy.setText(currentAndroidFlavor.getReschBy()+" min");
        trainDstn.setText(currentAndroidFlavor.getTrainDstn());
        newStartDate.setText(currentAndroidFlavor.getNewStartDate());
        trainType.setText(currentAndroidFlavor.getTrainType());
        return listItemView;
    }

}
