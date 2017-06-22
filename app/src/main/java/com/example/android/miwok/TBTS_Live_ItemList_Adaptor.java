package com.example.android.miwok;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sahu on 5/3/2017.
 */

public class TBTS_Live_ItemList_Adaptor extends ArrayAdapter<stn_status_Items_Class>{


    public TBTS_Live_ItemList_Adaptor(FragmentActivity context, ArrayList<stn_status_Items_Class> words) {
        super(context,0, words);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.station_status_list_item, parent, false);
        }

        stn_status_Items_Class currentAndroidFlavor = getItem(position);

        TextView trainNo = (TextView) listItemView.findViewById(R.id.trainNo);
        trainNo.setText(currentAndroidFlavor.getTrainNo());

        TextView trainName = (TextView) listItemView.findViewById(R.id.trainName);
        trainName.setText(currentAndroidFlavor.getTrainName());

        TextView trainSrc = (TextView) listItemView.findViewById(R.id.trainSrc);
        trainSrc.setText(currentAndroidFlavor.getTrainSrc());

        TextView trainDstn= (TextView) listItemView.findViewById(R.id.trainDstn);

        trainDstn.setText(currentAndroidFlavor.getTrainDstn());

        TextView schArr = (TextView) listItemView.findViewById(R.id.schArr);
        schArr.setText(currentAndroidFlavor.getSchArr());

        TextView schDep = (TextView) listItemView.findViewById(R.id.schDep);
        schDep.setText(currentAndroidFlavor.getSchDep());



        TextView actArr = (TextView) listItemView.findViewById(R.id.actArr);
        actArr.setText(currentAndroidFlavor.getActArr());

        TextView actDep = (TextView) listItemView.findViewById(R.id.actDep);
        actDep.setText(currentAndroidFlavor.getActDep());
        TextView actHalt = (TextView) listItemView.findViewById(R.id.actHalt);
        if(!currentAndroidFlavor.getActHalt().equals("Destination") && !currentAndroidFlavor.getActHalt().equals("Source") ) {
            actHalt.setText(currentAndroidFlavor.getActHalt()+" min");
        }else{
            actHalt.setText("-");
        }
        RelativeLayout pfNoLayout = (RelativeLayout)listItemView.findViewById(R.id.pfNoLayout);
        if(currentAndroidFlavor.getPfNo().equals("0")){
            pfNoLayout.setVisibility(View.GONE);
        }else {
            pfNoLayout.setVisibility(View.VISIBLE);
            TextView pfNO = (TextView) listItemView.findViewById(R.id.pfNo);
            pfNO.setText(currentAndroidFlavor.getPfNo());
        }

        TextView delayArr = (TextView) listItemView.findViewById(R.id.delayArr);
        delayArr.setText(currentAndroidFlavor.getDelayArr());
        if(!currentAndroidFlavor.getDelayArr().equals("Status :RIGHT TIME")) {
            delayArr.setTextColor(Color.parseColor("#dc0202"));
        }else if(currentAndroidFlavor.getDelayArr().equals("Status :RIGHT TIME")){
            delayArr.setTextColor(Color.parseColor("#689F38"));
        }

        TextView trainType = (TextView) listItemView.findViewById(R.id.trainType);
        trainType.setText(currentAndroidFlavor.getTrainType());

        return listItemView;
    }

}
