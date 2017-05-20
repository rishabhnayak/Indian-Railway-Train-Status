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

        // Get the {@link AndroidFlavor} object located at this position in the list
        DivertedTrainClass currentAndroidFlavor = getItem(position);

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_name
        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        trainNo.setText(currentAndroidFlavor.getTrainNo());

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_number
        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        trainName.setText(currentAndroidFlavor.getTrainName());

        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());

        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);
        trainDstn.setText(currentAndroidFlavor.getTrainDstn());


        TextView startDate= (TextView) listItemView.findViewById(R.id.startDate);
        startDate.setText(currentAndroidFlavor.getStartDate());

        TextView divertedFrom= (TextView) listItemView.findViewById(R.id.divertedFrom);
        divertedFrom.setText(currentAndroidFlavor.getDivertedFrom());

        TextView divertedTo= (TextView) listItemView.findViewById(R.id.divertedTo);
        divertedTo.setText(currentAndroidFlavor.getDivertedTo());

        TextView trainType= (TextView) listItemView.findViewById(R.id.trainType);
        trainType.setText(currentAndroidFlavor.getTrainType());


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
