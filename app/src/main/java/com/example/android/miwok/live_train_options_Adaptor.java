package com.example.android.miwok;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Created by sahu on 5/3/2017.
 */

public class live_train_options_Adaptor extends ArrayAdapter<live_train_options_Class>{


    public live_train_options_Adaptor(live_train_options context, ArrayList<live_train_options_Class> words) {
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

        TextView totalLateMins = (TextView) listItemView.findViewById(R.id.LateTime);
        TextView lastUpdated = (TextView) listItemView.findViewById(R.id.lastUpdated);
        TextView startDate = (TextView) listItemView.findViewById(R.id.startDate);
         TextView Line1=(TextView)listItemView.findViewById(R.id.Line1);
        TextView Line2=(TextView)listItemView.findViewById(R.id.Line2);



      //System.out.println("item position :"+position);

      //System.out.println("Total Late Mins :"+currentAndroidFlavor.getTotalLateMins());


        startDate.setText("Start Date:"+currentAndroidFlavor.getStartDate());
        Line1.setText(currentAndroidFlavor.getLine1());
        Line2.setText(currentAndroidFlavor.getLine2());
        totalLateMins.setText(currentAndroidFlavor.getTotalLateMins());
        if(totalLateMins.getText() != null) {
          //System.out.println("if 1 total late mins");
          //System.out.println(totalLateMins.getText());
                if(currentAndroidFlavor.getTotalLateMins().startsWith("Late by")){
                    totalLateMins.setTextColor(Color.parseColor("#b71916"));
                }else{
                    totalLateMins.setTextColor(Color.parseColor("#689F38"));
                }
        }
        lastUpdated.setText(currentAndroidFlavor.getLastUpdated());


        return listItemView;
    }

}
