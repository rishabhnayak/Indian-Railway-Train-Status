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

public class live_train_selected_Item_Adaptor extends ArrayAdapter<live_train_selected_Item_Class>{


    public live_train_selected_Item_Adaptor(live_train_status_selected_item context, ArrayList<live_train_selected_Item_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.live_train_status_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        live_train_selected_Item_Class currentAndroidFlavor = getItem(position);

        // Find the TextView in the Canceled_Trains_list_itemTrains_list_item.xml layout with the ID version_name
        TextView stnCode = (TextView) listItemView.findViewById(R.id.stnCode);
        stnCode.setText(currentAndroidFlavor.getStnCode());

        TextView schArrTime = (TextView) listItemView.findViewById(R.id.schArrTime);
        schArrTime.setText(currentAndroidFlavor.getSchArrTime());

        TextView schDepTime = (TextView) listItemView.findViewById(R.id.schDepTime);
        schDepTime.setText(currentAndroidFlavor.getSchDepTime());

        TextView actArr = (TextView) listItemView.findViewById(R.id.actArr);
        actArr.setText(currentAndroidFlavor.getActArr());

        TextView actDep = (TextView) listItemView.findViewById(R.id.actDep);
        actDep.setText(currentAndroidFlavor.getActDep());

        TextView dayCnt  = (TextView) listItemView.findViewById(R.id.dayCnt );
        dayCnt .setText(currentAndroidFlavor.getDayCnt());

        TextView delayArr = (TextView) listItemView.findViewById(R.id.delayArr);
        delayArr.setText(currentAndroidFlavor.getDelayArr()+" min");

        TextView delayDep = (TextView) listItemView.findViewById(R.id.delayDep);
        delayDep.setText(currentAndroidFlavor.getDelayDep()+" min");


        TextView pfNo = (TextView) listItemView.findViewById(R.id.pfNo);
        pfNo.setText(currentAndroidFlavor.getPfNo());

        return listItemView;
    }

}
