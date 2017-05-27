package com.example.android.miwok;

/**
 * Created by sahu on 5/6/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class RescheduledTrainsAdaptor_Searchable extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<RescheduledTrainClass> animalNamesList = null;

   private ArrayList<RescheduledTrainClass> arraylist;

   public RescheduledTrainsAdaptor_Searchable(Context context, List<RescheduledTrainClass> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<RescheduledTrainClass>();
       this.arraylist.addAll(animalNamesList);


   }

    public class ViewHolder {
//       TextView name;
//       TextView number;

        TextView trainNo;
        TextView trainName;
        TextView trainSrc ;
        TextView startDate;
        TextView schTime;
        TextView reschTime;
        TextView reschBy;
        TextView trainDstn;
        TextView newStartDate;
        TextView trainType;

   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public RescheduledTrainClass getItem(int position) {
       return animalNamesList.get(position);
   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   public View getView(final int position, View view, ViewGroup parent) {
       final ViewHolder holder;
       if (view == null) {
           holder = new ViewHolder();
           view = inflater.inflate(R.layout.rescheduled_trains_list_item, null);
           // Locate the TextViews in listview_item.xml
//           holder.name = (TextView) view.findViewById(R.id.name);
//           holder.number = (TextView) view.findViewById(R.id.number);

           holder.trainNo = (TextView) view.findViewById(R.id.trainNo);
           holder.trainName = (TextView) view.findViewById(R.id.trainName);
           holder.trainSrc = (TextView) view.findViewById(R.id.trainSrc);
           holder.startDate= (TextView) view.findViewById(R.id.startDate);
           holder.schTime= (TextView) view.findViewById(R.id.scsTime);
           holder.reschTime= (TextView) view.findViewById(R.id.resTime);
           holder.reschBy= (TextView) view.findViewById(R.id.resBy);
           holder.trainDstn= (TextView) view.findViewById(R.id.trainDstn);
           holder.newStartDate= (TextView) view.findViewById(R.id.newStartDate);
           holder.trainType= (TextView) view.findViewById(R.id.trainType);
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }
       // Set the results into TextViews
//       holder.name.setText(animalNamesList.get(position).getAnimalName());
//       holder.number.setText(animalNamesList.get(position).getAnimalNo());

        holder.trainNo.setText(animalNamesList.get(position).getTrainNo());
        holder.trainName.setText(animalNamesList.get(position).getTrainName());
        holder.trainSrc.setText(animalNamesList.get(position).getTrainSrc());
        holder.startDate.setText(animalNamesList.get(position).getStartDate());
        holder.schTime.setText(animalNamesList.get(position).getSchTime());
        holder.reschTime.setText(animalNamesList.get(position).getReschTime());
        holder.reschBy.setText(animalNamesList.get(position).getReschBy());
        holder.trainDstn.setText(animalNamesList.get(position).getTrainDstn());
        holder.newStartDate.setText(animalNamesList.get(position).getNewStartDate());
        holder.trainType.setText(animalNamesList.get(position).getTrainType());
       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       animalNamesList.clear();
       ArrayList<RescheduledTrainClass> arrayList2=new ArrayList<RescheduledTrainClass>();


       if (charText.length() == 0) {
           animalNamesList.addAll(arraylist);
       }
       else {
           for (RescheduledTrainClass wp : arraylist) {

             if (wp.getTrainName().toLowerCase(Locale.getDefault()).startsWith(charText) || wp.getTrainNo().toLowerCase(Locale.getDefault()).startsWith(charText) ) {

                     animalNamesList.add(wp);
               }
               else if(wp.getTrainName().toLowerCase(Locale.getDefault()).contains(" "+charText)){
                 arrayList2.add(wp);
                }
           }

           animalNamesList.addAll(arrayList2);
       }


       notifyDataSetChanged();
   }



}


