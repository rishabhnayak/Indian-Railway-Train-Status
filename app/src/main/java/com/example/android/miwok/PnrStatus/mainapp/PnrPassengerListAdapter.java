package com.example.android.miwok.PnrStatus.mainapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.miwok.PnrStatus.mainapp.PnrAllData.PassengerList;
import com.example.android.miwok.R;


/**
 * Created by RAJA on 18-12-2017.
 */

public class PnrPassengerListAdapter extends RecyclerView.Adapter<PnrPassengerListAdapter.PnrViewHolder>{

    private Context context;
    private PassengerList[] data;
    public PnrPassengerListAdapter(Context context, PassengerList[] data){
        this.context=context;
        this.data=data;
    }

    @Override
    public PnrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.pnr_pass_list_layout,parent,false);
        return new PnrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PnrViewHolder holder, int position) {
        PassengerList passengerList=data[position];
//        holder.bookingCoachId.setText(passengerList.getBookingCoachId());
//        holder.passengerSerialNumber.setText(passengerList.getPassengerSerialNumber());
//        holder.passengerNationality.setText(passengerList.getPassengerNationality());
//        holder.passengerCoachPosition.setText(passengerList.getPassengerCoachPosition());
//        holder.passengerQuota.setText(passengerList.getPassengerQuota());
//        holder.bookingBerthCode.setText(passengerList.getBookingBerthCode());
//        holder.bookingBerthNo.setText(passengerList.getBookingBerthNo());
//        holder.bookingCoachId.setText(passengerList.getBookingCoachId());
//        holder.bookingStatus.setText(passengerList.getBookingStatus());
        holder.bookingStatusDetails.setText(passengerList.getBookingStatusDetails());
//
//        holder.concessionOpted.setText((passengerList.getConcessionOpted()));
//        if(passengerList.getConcessionOpted()=="true"){
//            holder.concessionOpted.setText("Yes");
//        }
//        else {
//            holder.concessionOpted.setText("No");
//        }
//
//        holder.currentBerthCode.setText((passengerList.getCurrentBerthCode()));
//        holder.currentStatusDetails.setText((passengerList.getCurrentStatusDetails()));
//        holder.currentStatusIndex.setText((passengerList.getCurrentStatusIndex()));
//        holder.currentCoachId.setText((passengerList.getCurrentCoachId()));
//        holder.currentStatus.setText((passengerList.getCurrentStatus()));
//        holder.currentBerthNo.setText((passengerList.getCurrentBerthNo()));
//        holder.childBerthFlag.setText((passengerList.getChildBerthFlag()));
//        holder.passengerIcardFlag.setText((passengerList.getPassengerIcardFlag()));


    }

   @Override
    public int getItemCount() {
        try{return data.length;}
        catch (Exception e){

        }
        return 0;
    }

    public class PnrViewHolder extends RecyclerView.ViewHolder {



        TextView bookingCoachId,bookingBerthCode,bookingBerthNo,bookingStatus
                ,bookingStatusDetails,bookingStatusIndex,childBerthFlag,concessionOpted
                ,currentBerthCode,currentBerthNo,currentCoachId,currentStatus,currentStatusDetails
                ,currentStatusIndex,passengerIcardFlag,passengerQuota,passengerCoachPosition
                ,passengerNationality,passengerSerialNumber;
        public PnrViewHolder(View itemView) {
            super(itemView);
//            bookingCoachId=(TextView) itemView.findViewById(R.id.bookingCoachId);
//            bookingBerthCode=(TextView) itemView.findViewById(R.id.bookingBerthCode);
//            bookingBerthNo=(TextView) itemView.findViewById(R.id.bookingBerthNo);
//            bookingStatus=(TextView) itemView.findViewById(R.id.bookingStatus);
            bookingStatusDetails=(TextView) itemView.findViewById(R.id.bookingStatusDetails);
//            bookingStatusIndex=(TextView) itemView.findViewById(R.id.bookingStatusIndex);
//            childBerthFlag=(TextView) itemView.findViewById(R.id.childBerthFlag);
//            concessionOpted=(TextView) itemView.findViewById(R.id.concessionOpted);
//            currentBerthCode=(TextView) itemView.findViewById(R.id.currentBerthCode);
//            currentBerthNo=(TextView) itemView.findViewById(R.id.currentBerthNo);
//            currentCoachId=(TextView) itemView.findViewById(R.id.currentCoachId);
//            currentStatus=(TextView) itemView.findViewById(R.id.currentStatus);
//            currentStatusDetails=(TextView) itemView.findViewById(R.id.currentStatusDetails);
//            currentStatusIndex=(TextView) itemView.findViewById(R.id.currentStatusIndex);
//            passengerIcardFlag=(TextView) itemView.findViewById(R.id.passengerIcardFlag);
//            passengerQuota=(TextView) itemView.findViewById(R.id.passengerQuota);
//            passengerCoachPosition=(TextView) itemView.findViewById(R.id.passengerCoachPosition);
//            passengerNationality=(TextView) itemView.findViewById(R.id.passengerNationality);
//            passengerSerialNumber=(TextView) itemView.findViewById(R.id.passengerSerialNumber);
        }
    }
}
