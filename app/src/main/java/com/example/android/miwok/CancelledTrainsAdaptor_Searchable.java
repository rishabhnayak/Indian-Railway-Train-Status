package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CancelledTrainsAdaptor_Searchable extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<CanceledTrainClass> animalNamesList = null;

   private ArrayList<CanceledTrainClass> arraylist;

   public CancelledTrainsAdaptor_Searchable(Context context, List<CanceledTrainClass> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<CanceledTrainClass>();
       this.arraylist.addAll(animalNamesList);


   }

    public class ViewHolder {


        TextView trainType;
        TextView startDate;
        TextView trainName ;
        TextView trainSrc ;
        TextView trainDstn;
        TextView trainNo ;

   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public CanceledTrainClass getItem(int position) {
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
           view = inflater.inflate(R.layout.canceled_trains_list_item, null);

           holder.trainType= (TextView) view.findViewById(R.id.trainType);
           holder.startDate= (TextView) view.findViewById(R.id.startDate);
           holder.trainName = (TextView) view.findViewById(R.id.trainName);
           holder.trainSrc = (TextView) view.findViewById(R.id.trainSrc);
           holder.trainDstn= (TextView) view.findViewById(R.id.trainDstn);
           holder.trainNo = (TextView) view.findViewById(R.id.trainNo);
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }

        holder.trainNo.setText(animalNamesList.get(position).getTrainNo());
        holder.trainName.setText(animalNamesList.get(position).getTrainName());
        holder.trainSrc.setText(animalNamesList.get(position).getTrainSrc());
        holder.trainDstn.setText(animalNamesList.get(position).getTrainDstn());
        holder.startDate.setText(animalNamesList.get(position).getStartDate());
        holder.trainType.setText(animalNamesList.get(position).getTrainType());
       
       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       animalNamesList.clear();
       ArrayList<CanceledTrainClass> arrayList2=new ArrayList<CanceledTrainClass>();


       if (charText.length() == 0) {
           animalNamesList.addAll(arraylist);
       }
       else {
           for (CanceledTrainClass wp : arraylist) {

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


