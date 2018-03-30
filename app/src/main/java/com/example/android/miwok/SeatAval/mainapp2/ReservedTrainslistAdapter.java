package com.example.android.miwok.SeatAval.mainapp2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.SeatAval.mainapp2.ReservedTrainData.TrainBtwnStnsList;


/**
 * Created by RAJA on 27-12-2017.
 */

public class ReservedTrainslistAdapter extends RecyclerView.Adapter<ReservedTrainslistAdapter.ReservedViewHolder>{
    private RecyclerView mListener;
    private Context context;
    private TrainBtwnStnsList[] data;
    String d,m,y,from_code,from_name,to_code,to_name,train_no,train_name,arr,dep,tt;
    public ReservedTrainslistAdapter(Context context, TrainBtwnStnsList[] data, String d, String m, String y, String to_name, String to_code, String from_name, String from_code){
        this.context=context;
        this.data=data;
        this.d=d;
        this.m=m;
        this.y=y;
        this.from_code=from_code;
        this.from_name=from_name;
        this.to_code=to_code;
        this.to_name=to_name;
    }

    @Override
    public ReservedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.seat_reserved_train_list_item_layout,parent,false);
        return new ReservedViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ReservedViewHolder holder, final int position) {
        final TrainBtwnStnsList trainBtwnStnsList=data[position];

   try {
       holder.trainName.setText(trainBtwnStnsList.getTrainName());
       holder.trainNumber.setText(trainBtwnStnsList.getTrainNumber());
//        holder.fromStnCode.setText(String.valueOf(trainBtwnStnsList.getFromStnCode()));
//        holder.toStnCode.setText(trainBtwnStnsList.getToStnCode());
       holder.arrivalTime.setText(trainBtwnStnsList.getArrivalTime());
       holder.departureTime.setText(trainBtwnStnsList.getDepartureTime());
//        holder.distance.setText(String.valueOf(trainBtwnStnsList.getDistance()));
       holder.duration.setText(trainBtwnStnsList.getDuration());
//        holder.runningMon.setText(trainBtwnStnsList.getRunningMon());
//        holder.runningTue.setText(trainBtwnStnsList.getRunningTue());
//        holder.runningWed.setText(trainBtwnStnsList.getRunningWed());
//        holder.runningThu.setText(trainBtwnStnsList.getRunningThu());
//        holder.runningFri.setText(trainBtwnStnsList.getRunningFri());
//        holder.runningSat.setText(trainBtwnStnsList.getRunningSat());
//        holder.runningSun.setText(trainBtwnStnsList.getRunningSun());


   }catch (Exception e){

   }
        try {

            if (trainBtwnStnsList.getAvlClasses().get(0)==null){

            }
            else {
                holder.avlClasses.setText(trainBtwnStnsList.getAvlClasses().get(0));
                holder.avlClasses.setVisibility(View.VISIBLE);
            }

            if (trainBtwnStnsList.getAvlClasses().get(1)==null){

            }
            else {
                holder.avlClasses1.setText(trainBtwnStnsList.getAvlClasses().get(1));
                holder.avlClasses1.setVisibility(View.VISIBLE);
            }

            if (trainBtwnStnsList.getAvlClasses().get(2)==null){

            }
            else {
                holder.avlClasses2.setVisibility(View.VISIBLE);
                holder.avlClasses2.setText(trainBtwnStnsList.getAvlClasses().get(2));
            }

            if (trainBtwnStnsList.getAvlClasses().get(3)==null){

            }
            else {
                holder.avlClasses3.setText(trainBtwnStnsList.getAvlClasses().get(3));
                holder.avlClasses3.setVisibility(View.VISIBLE);
            }

            if (trainBtwnStnsList.getAvlClasses().get(4)==null){

            }
            else {
                holder.avlClasses4.setText(trainBtwnStnsList.getAvlClasses().get(4));
                holder.avlClasses4.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){

        }

        holder.checkAval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Seat_ava_tabbed_activity.class);


                System.out.println("yhi h...................................................."+trainBtwnStnsList.getTrainNumber()+" - "+trainBtwnStnsList.getTrainName());


                try {

                    if (trainBtwnStnsList.getAvlClasses().get(0)==null){

                    }
                    else {
                        intent.putExtra("1", trainBtwnStnsList.getAvlClasses().get(0));
                    }

                    if (trainBtwnStnsList.getAvlClasses().get(1)==null){

                    }
                    else {
                        intent.putExtra("2", trainBtwnStnsList.getAvlClasses().get(1));
                    }

                    if (trainBtwnStnsList.getAvlClasses().get(2)==null){

                    }
                    else {
                        intent.putExtra("3", trainBtwnStnsList.getAvlClasses().get(2));
                    }

                    if (trainBtwnStnsList.getAvlClasses().get(3)==null){

                    }
                    else {
                        intent.putExtra("4", trainBtwnStnsList.getAvlClasses().get(3));
                    }

                    if (trainBtwnStnsList.getAvlClasses().get(4)==null){

                    }
                    else {
                        intent.putExtra("5", trainBtwnStnsList.getAvlClasses().get(4));
                    }

                }catch (Exception e){

                }
                arr=trainBtwnStnsList.getArrivalTime();
                dep=trainBtwnStnsList.getDepartureTime();
                tt=trainBtwnStnsList.getDuration();
                train_no= trainBtwnStnsList.getTrainNumber();
                train_name=trainBtwnStnsList.getTrainName();
                intent.putExtra("d",d);
                intent.putExtra("m",m);
                intent.putExtra("y",y);
                intent.putExtra("from_code",from_code);
                intent.putExtra("to_code",to_code);
                intent.putExtra("from_name",from_name);
                intent.putExtra("to_name",to_name);
                intent.putExtra("train_no",train_no);
                intent.putExtra("train_name",train_name);
                intent.putExtra("arr",arr);
                intent.putExtra("dep",dep);
                intent.putExtra("tt",tt);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount()
    {
       return data.length;
    }


    public class ReservedViewHolder extends RecyclerView.ViewHolder{
        TextView trainName,trainNumber,fromStnCode,toStnCode,arrivalTime,departureTime,distance,duration,runningMon,runningTue,runningWed,runningThu,runningFri,runningSat,runningSun;
        TextView avlClasses,avlClasses1,avlClasses2,avlClasses3,avlClasses4,checkAval;
        private TabLayout tabLayout;
        private ViewPager viewPager;
        public ReservedViewHolder(final View itemView) {
            super(itemView);
              context = itemView.getContext();

            try{  trainName=(TextView) itemView.findViewById(R.id.trainName);
              trainNumber=(TextView) itemView.findViewById(R.id.trainNumber);
//            fromStnCode=(TextView) itemView.findViewById(R.id.fromStnCode);
//            toStnCode=(TextView) itemView.findViewById(R.id.toStnCode);
              arrivalTime=(TextView) itemView.findViewById(R.id.arrivalTime);
              departureTime=(TextView) itemView.findViewById(R.id.departureTime);
//            distance=(TextView) itemView.findViewById(R.id.distance);
              duration=(TextView) itemView.findViewById(R.id.duration);
//            runningMon=(TextView) itemView.findViewById(R.id.runningMon);
//            runningTue=(TextView) itemView.findViewById(R.id.runningTue);
//            runningWed=(TextView) itemView.findViewById(R.id.runningWed);
//            runningThu=(TextView) itemView.findViewById(R.id.runningThu);
//            runningFri=(TextView) itemView.findViewById(R.id.runningFri);
//            runningSat=(TextView) itemView.findViewById(R.id.runningSat);
//            runningSun=(TextView) itemView.findViewById(R.id.runningSun);
              checkAval= (TextView) itemView.findViewById(R.id.checkAval);
            try {
                avlClasses = (TextView) itemView.findViewById(R.id.avlClasses);
                avlClasses1 = (TextView) itemView.findViewById(R.id.avlClasses1);
                avlClasses2 = (TextView) itemView.findViewById(R.id.avlClasses2);
                avlClasses3 = (TextView) itemView.findViewById(R.id.avlClasses3);
                avlClasses4 = (TextView) itemView.findViewById(R.id.avlClasses4);
                }
                catch (Exception e){
            }
        }catch (Exception e){

            }
        }
    }
}