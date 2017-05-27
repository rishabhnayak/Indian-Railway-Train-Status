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

public class DivertedTrainsAdaptor extends ArrayAdapter<DivertedTrainClass>{


    public DivertedTrainsAdaptor(DivertedTrains context, ArrayList<DivertedTrainClass> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.diverted_trains_list_item, parent, false);
        }

        DivertedTrainClass currentAndroidFlavor = getItem(position);

        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        TextView trainType= (TextView) listItemView.findViewById(R.id.trainType);
        TextView divertedTo= (TextView) listItemView.findViewById(R.id.divertedTo);
        TextView divertedFrom= (TextView) listItemView.findViewById(R.id.divertedFrom);
        TextView startDate= (TextView) listItemView.findViewById(R.id.startDate);
        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);
        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);


        trainNo.setText(currentAndroidFlavor.getTrainNo());
        trainName.setText(currentAndroidFlavor.getTrainName());
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());
        trainDstn.setText(currentAndroidFlavor.getTrainDstn());
        startDate.setText(currentAndroidFlavor.getStartDate());
        divertedFrom.setText(currentAndroidFlavor.getDivertedFrom());
        divertedTo.setText(currentAndroidFlavor.getDivertedTo());
        trainType.setText(currentAndroidFlavor.getTrainType());

        return listItemView;
    }

}
