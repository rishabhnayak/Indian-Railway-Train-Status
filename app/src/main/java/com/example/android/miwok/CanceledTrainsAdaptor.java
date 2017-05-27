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

public class CanceledTrainsAdaptor extends ArrayAdapter<CanceledTrainClass>{


    public CanceledTrainsAdaptor(CanceledTrains context, ArrayList<CanceledTrainClass> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.canceled_trains_list_item, parent, false);
        }

    // Get the {@link AndroidFlavor} object located at this position in the list
    CanceledTrainClass currentAndroidFlavor = getItem(position);
        TextView trainType= (TextView) listItemView.findViewById(R.id.trainType);
        TextView startDate= (TextView) listItemView.findViewById(R.id.startDate);
        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);
        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);
        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);


        trainNo.setText(currentAndroidFlavor.getTrainNo());
        trainName.setText(currentAndroidFlavor.getTrainName());
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());
        trainDstn.setText(currentAndroidFlavor.getTrainDstn());
        startDate.setText(currentAndroidFlavor.getStartDate());
        trainType.setText(currentAndroidFlavor.getTrainType());

        return listItemView;
}

}
