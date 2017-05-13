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

public class TrainRoute_ItemList_Adaptor extends ArrayAdapter<TrainRoute_Items_Class>{


    public TrainRoute_ItemList_Adaptor(TrainRoute context, ArrayList<TrainRoute_Items_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trains_route_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        TrainRoute_Items_Class currentAndroidFlavor = getItem(position);

        TextView sNo = (TextView) listItemView.findViewById(R.id.sNo);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        sNo.setText(currentAndroidFlavor.getsNo());
        // Find thTextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_name
        TextView stnCode = (TextView) listItemView.findViewById(R.id.stnCode);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        stnCode.setText(currentAndroidFlavor.getSrcCode());

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_number
        TextView arrTime = (TextView) listItemView.findViewById(R.id.arrTime);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        arrTime.setText(currentAndroidFlavor.getArrTime());

        TextView depTime = (TextView) listItemView.findViewById(R.id.depTime);
        depTime.setText(currentAndroidFlavor.getDepTime());

        TextView distance= (TextView) listItemView.findViewById(R.id.distance);
        distance.setText(currentAndroidFlavor.getDistance());
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        TextView dayCnt= (TextView) listItemView.findViewById(R.id.dayCnt);
        dayCnt.setText(currentAndroidFlavor.getDayCnt());

        return listItemView;
    }

}
