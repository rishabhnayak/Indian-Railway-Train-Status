package com.example.android.miwok;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


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
        LinearLayout SubItemsContainer=(LinearLayout)listItemView.findViewById(R.id.SubItemsContainer);
        TextView sNo = (TextView) listItemView.findViewById(R.id.sNo);
        TextView pfNo = (TextView) listItemView.findViewById(R.id.pfNo);
        TextView delayArr = (TextView) listItemView.findViewById(R.id.delayArr);
        TextView actDep = (TextView) listItemView.findViewById(R.id.actDep);
        TextView actArr = (TextView) listItemView.findViewById(R.id.actArr);
        TextView schDepTime = (TextView) listItemView.findViewById(R.id.schDepTime);
        TextView schArrTime = (TextView) listItemView.findViewById(R.id.schArrTime);
        TextView stnCode = (TextView) listItemView.findViewById(R.id.stnCode);
//      TextView dayCnt  = (TextView) listItemView.findViewById(R.id.dayCnt );
//      TextView delayDep = (TextView) listItemView.findViewById(R.id.delayDep);

       SubItemsContainer.setBackgroundColor(currentAndroidFlavor.getContainerColor());
        stnCode.setText(currentAndroidFlavor.getStnCode());
        schArrTime.setText(currentAndroidFlavor.getSchArrTime());
        schDepTime.setText(currentAndroidFlavor.getSchDepTime());
        actArr.setText(currentAndroidFlavor.getActArr());
        actDep.setText(currentAndroidFlavor.getActDep());
        delayArr.setText(currentAndroidFlavor.getDelayArr());
        if(delayArr.getText() != null) {
            if(currentAndroidFlavor.getDelayArr().startsWith("Late by")){
                delayArr.setTextColor(Color.parseColor("#b71916"));

            }else{
                delayArr.setTextColor(Color.parseColor("#689F38"));

            }
        }


        pfNo.setText(currentAndroidFlavor.getPfNo());
        sNo.setText(currentAndroidFlavor.getsNo());




        return listItemView;
    }

}
