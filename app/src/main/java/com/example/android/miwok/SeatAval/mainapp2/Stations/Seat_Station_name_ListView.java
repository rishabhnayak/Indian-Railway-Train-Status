package com.example.android.miwok.SeatAval.mainapp2.Stations;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.android.miwok.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

class Station_name_ListView extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<SeatAnimalNames> animalNamesList = null;

   private ArrayList<SeatAnimalNames> arraylist;

   public Station_name_ListView(Context context, List<SeatAnimalNames> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<SeatAnimalNames>();
       this.arraylist.addAll(animalNamesList);


   }



    public class ViewHolder {
       TextView name;
       TextView number;


   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public SeatAnimalNames getItem(int position) {
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
           view = inflater.inflate(R.layout.list_view_items, null);
           // Locate the TextViews in listview_item.xml
           holder.name = (TextView) view.findViewById(R.id.name);
           holder.number = (TextView) view.findViewById(R.id.number);
           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }
       // Set the results into TextViews
       holder.name.setText(animalNamesList.get(position).getAnimalName());
       holder.number.setText(animalNamesList.get(position).getAnimalNo());


       return view;
   }

   // Filter Class
   public void filter(String charText) {
       charText = charText.toLowerCase(Locale.getDefault());
       animalNamesList.clear();
       ArrayList<SeatAnimalNames> arrayList2=new ArrayList<SeatAnimalNames>();
       ArrayList<SeatAnimalNames> arrayList3=new ArrayList<SeatAnimalNames>();

       if (charText.length() == 0) {
           animalNamesList.addAll(arraylist);
       }
       else {
           for (SeatAnimalNames wp : arraylist) {
            //   String splited= wp.animalName.split(" ")[1];
               if(wp.getAnimalName().toLowerCase(Locale.getDefault()).equals(charText) || wp.getAnimalNo().toLowerCase(Locale.getDefault()).equals(charText)  ){
                   animalNamesList.add(wp);
               }
               else if (wp.getAnimalName().toLowerCase(Locale.getDefault()).startsWith(charText) || wp.getAnimalNo().toLowerCase(Locale.getDefault()).startsWith(charText) ) {

                      arrayList3.add(wp);
               }
               else if(wp.getAnimalName().toLowerCase(Locale.getDefault()).contains(" "+charText)){
                 arrayList2.add(wp);
                }
           }
           Collections.sort(animalNamesList,new mycomparater2(charText));
           Collections.sort(arrayList3,new mycomparater2(charText));
           Collections.sort(arrayList2,new mycomparater2(charText));
           Collections.sort(animalNamesList,new mycomparater(charText));
           Collections.sort(arrayList3,new mycomparater(charText));
           Collections.sort(arrayList2,new mycomparater(charText));

           animalNamesList.addAll(arrayList3);
           animalNamesList.addAll(arrayList2);
       }


       notifyDataSetChanged();
   }
    class mycomparater implements Comparator<SeatAnimalNames>{
private String charText;
    public mycomparater(String charText) {
        this.charText=charText;
    }

    @Override
    public int compare(SeatAnimalNames animalNames, SeatAnimalNames t1) {


          return  animalNames.animalName.length() - t1.animalName.length();


    }
}
    class mycomparater2 implements Comparator<SeatAnimalNames>{
        private String charText;
        public mycomparater2(String charText) {
            this.charText=charText;
        }

        @Override
        public int compare(SeatAnimalNames animalNames, SeatAnimalNames t1) {

            //return  animalNames.animalName.length() - t1.animalName.length();
            return  animalNames.animalNo.length() - t1.animalNo.length();


        }
    }


}

class Station_name_ListViewRecent extends BaseAdapter  {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<SeatAnimalNames> animalNamesList = null;

    private ArrayList<SeatAnimalNames> arraylist;

    public Station_name_ListViewRecent(Context context, List<SeatAnimalNames> animalNamesList) {
        mContext = context;
        this.animalNamesList = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SeatAnimalNames>();
        this.arraylist.addAll(animalNamesList);


    }



    public class ViewHolder {
        TextView name;
        TextView number;


    }

    @Override
    public int getCount() {
        return animalNamesList.size();
    }

    @Override
    public SeatAnimalNames getItem(int position) {
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
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.number = (TextView) view.findViewById(R.id.number);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(animalNamesList.get(position).getAnimalName());
        holder.number.setText(animalNamesList.get(position).getAnimalNo());


        return view;
    }



}


