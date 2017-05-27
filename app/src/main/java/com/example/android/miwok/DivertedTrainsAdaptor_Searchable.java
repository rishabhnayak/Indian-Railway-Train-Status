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

class DivertedTrainsAdaptor_Searchable extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<DivertedTrainClass> animalNamesList = null;

   private ArrayList<DivertedTrainClass> arraylist;

   public DivertedTrainsAdaptor_Searchable(Context context, List<DivertedTrainClass> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<DivertedTrainClass>();
       this.arraylist.addAll(animalNamesList);


   }

    public class ViewHolder {
//       TextView name;
//       TextView number;

        TextView trainNo ;
        TextView trainName ;
        TextView trainType;
        TextView divertedTo;
        TextView divertedFrom;
        TextView startDate;
        TextView trainDstn;
        TextView trainSrc ;

   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public DivertedTrainClass getItem(int position) {
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
           view = inflater.inflate(R.layout.diverted_trains_list_item, null);

           holder.trainNo = (TextView) view.findViewById(R.id.trainNo);
           holder.trainName = (TextView) view.findViewById(R.id.trainName);
           holder.trainType= (TextView) view.findViewById(R.id.trainType);
           holder.divertedTo= (TextView) view.findViewById(R.id.divertedTo);
           holder.divertedFrom= (TextView) view.findViewById(R.id.divertedFrom);
           holder.startDate= (TextView) view.findViewById(R.id.startDate);
           holder.trainDstn= (TextView) view.findViewById(R.id.trainDstn);
           holder.trainSrc = (TextView) view.findViewById(R.id.trainSrc);
           
           
           
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }


        holder.trainNo.setText(animalNamesList.get(position).getTrainNo());
        holder.trainName.setText(animalNamesList.get(position).getTrainName());
        holder.trainSrc.setText(animalNamesList.get(position).getTrainSrc());
        holder.trainDstn.setText(animalNamesList.get(position).getTrainDstn());
        holder.startDate.setText(animalNamesList.get(position).getStartDate());
        holder.divertedFrom.setText(animalNamesList.get(position).getDivertedFrom());
        holder.divertedTo.setText(animalNamesList.get(position).getDivertedTo());
        holder.trainType.setText(animalNamesList.get(position).getTrainType());
       
       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       animalNamesList.clear();
       ArrayList<DivertedTrainClass> arrayList2=new ArrayList<DivertedTrainClass>();


       if (charText.length() == 0) {
           animalNamesList.addAll(arraylist);
       }
       else {
           for (DivertedTrainClass wp : arraylist) {

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


