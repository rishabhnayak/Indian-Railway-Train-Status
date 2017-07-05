package com.example.android.miwok;

/**
 * Created by sahu on 5/6/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

class Train_name_listView extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<TrainDetailsObj> TrainDetailsObjList = null;

   private ArrayList<TrainDetailsObj> arraylist;

   public Train_name_listView(Context context, ArrayList<TrainDetailsObj> TrainDetailsObjList) {
       mContext = context;
       this.TrainDetailsObjList = TrainDetailsObjList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<TrainDetailsObj>();
       this.arraylist.addAll(TrainDetailsObjList);


   }

    public class ViewHolder {
        TextView name;
        TextView number;
        TextView srcName;
        TextView dstnName;


   }

   @Override
   public int getCount() {
       return TrainDetailsObjList.size();
   }

   @Override
   public TrainDetailsObj getItem(int position) {
       return TrainDetailsObjList.get(position);
   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   public View getView(final int position, View view, ViewGroup parent) {
       final ViewHolder holder;
       if (view == null) {
           holder = new ViewHolder();
           view = inflater.inflate(R.layout.train_objlist_view_items, null);
           // Locate the TextViews in listview_item.xml
           holder.name = (TextView) view.findViewById(R.id.name);
           holder.number = (TextView) view.findViewById(R.id.number);
           holder.srcName = (TextView) view.findViewById(R.id.srcName);
           holder.dstnName = (TextView) view.findViewById(R.id.dstnName);
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }
       // Set the results into TextViews
       holder.name.setText(TrainDetailsObjList.get(position).getTrnName());
       holder.number.setText(TrainDetailsObjList.get(position).getTrnNo());
       holder.srcName.setText(TrainDetailsObjList.get(position).getSrcName());
       holder.dstnName.setText(TrainDetailsObjList.get(position).getDstnName());

       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       TrainDetailsObjList.clear();
       ArrayList<TrainDetailsObj> arrayList2=new ArrayList<TrainDetailsObj>();


       if (charText.length() == 0) {
           TrainDetailsObjList.addAll(arraylist);
       }
       else {
           for (TrainDetailsObj wp : arraylist) {

             if (wp.getTrnName().toLowerCase(Locale.getDefault()).startsWith(charText) || wp.getTrnNo().toLowerCase(Locale.getDefault()).startsWith(charText) ) {

                     TrainDetailsObjList.add(wp);
               }
               else if(wp.getTrnName().toLowerCase(Locale.getDefault()).contains(" "+charText)){
                 arrayList2.add(wp);
                }
           }

           TrainDetailsObjList.addAll(arrayList2);
       }


       notifyDataSetChanged();
   }



}


class Train_name_listViewRecent extends BaseAdapter  {
    Context mContext;
    LayoutInflater inflater;
    private List<TrainDetailsObj> TrainDetailsObjListR = null;

    private ArrayList<TrainDetailsObj> arraylist;

    public Train_name_listViewRecent(Context context, ArrayList<TrainDetailsObj> TrainDetailsObjList) {
        mContext = context;
        this.TrainDetailsObjListR = TrainDetailsObjList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<TrainDetailsObj>();
        this.arraylist.addAll(TrainDetailsObjList);


    }


    public class ViewHolder {
        TextView name;
        TextView number;
        TextView srcName;
        TextView dstnName;


    }

    @Override
    public int getCount() {
        return TrainDetailsObjListR.size();
    }

    @Override
    public TrainDetailsObj getItem(int position) {
        return TrainDetailsObjListR.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.train_objlist_view_items, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.number = (TextView) view.findViewById(R.id.number);
            holder.srcName = (TextView) view.findViewById(R.id.srcName);
            holder.dstnName = (TextView) view.findViewById(R.id.dstnName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(TrainDetailsObjListR.get(position).getTrnName());
        holder.number.setText(TrainDetailsObjListR.get(position).getTrnNo());
        holder.srcName.setText(TrainDetailsObjListR.get(position).getSrcName());
        holder.dstnName.setText(TrainDetailsObjListR.get(position).getDstnName());
        return view;
    }
}