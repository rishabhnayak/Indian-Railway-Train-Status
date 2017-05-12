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

public class live_train_options_Adaptor extends ArrayAdapter<live_train_options_Class>{


    public live_train_options_Adaptor(live_train context, ArrayList<live_train_options_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.live_train_status_options, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        live_train_options_Class currentAndroidFlavor = getItem(position);

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_name
        TextView startDate = (TextView) listItemView.findViewById(R.id.startDate);
        startDate.setText(currentAndroidFlavor.getStartDate());

        TextView curStn = (TextView) listItemView.findViewById(R.id.curStn);
        curStn.setText(currentAndroidFlavor.getCurStn());

        TextView lastUpdated = (TextView) listItemView.findViewById(R.id.lastUpdated);
        lastUpdated.setText(currentAndroidFlavor.getLastUpdated());

        TextView totalLateMins = (TextView) listItemView.findViewById(R.id.totalLateMins);
        totalLateMins.setText(currentAndroidFlavor.getTotalLateMins());

        TextView totalJourney = (TextView) listItemView.findViewById(R.id.totalJourney);
        totalJourney.setText(currentAndroidFlavor.getTotalJourney());
        return listItemView;
    }

}
