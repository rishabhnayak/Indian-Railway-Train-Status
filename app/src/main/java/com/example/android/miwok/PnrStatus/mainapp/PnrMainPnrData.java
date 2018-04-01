package com.example.android.miwok.PnrStatus.mainapp;


import com.example.android.miwok.PnrStatus.mainapp.PnrAllData.PnrData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RAJA on 24-12-2017.
 */

public class PnrMainPnrData {
  public String savedData,boardingPoint1, bookingFare1, chartStatus1, dateOfJourney1, destinationStation1, journeyClass1, numberOfpassenger1, pnrNumber1, reservationUpto1, sourceStation1, ticketFare1, trainName1, trainNumber1, quota1,timeStamp1;
    JSONArray array = null;
    public void setResponse(String savedData){
        this.savedData=savedData;
    }
    public void setMainlist() {

        JSONObject object = null;
        try {
            object = new JSONObject(savedData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject obj = null;
        try {
            obj = object.getJSONObject("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            array = obj.getJSONArray("passengerList");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        GsonBuilder gsonbuilder = new GsonBuilder();
        Gson gson = gsonbuilder.create();

        PnrData maindata = gson.fromJson(savedData, PnrData.class);
        boardingPoint1 = maindata.getMessage().getBoardingPoint();
        chartStatus1 = maindata.getMessage().getChartStatus();
        dateOfJourney1 = maindata.getMessage().getDateOfJourney();
        destinationStation1 = maindata.getMessage().getDestinationStation();
        journeyClass1 = maindata.getMessage().getJourneyClass();
        numberOfpassenger1 = String.valueOf(maindata.getMessage().getNumberOfpassenger());
        pnrNumber1 = maindata.getMessage().getPnrNumber();
        reservationUpto1 = maindata.getMessage().getReservationUpto();
        sourceStation1 = maindata.getMessage().getSourceStation();
        ticketFare1 = String.valueOf(maindata.getMessage().getTicketFare());
        trainName1 = maindata.getMessage().getTrainName();
        trainNumber1 = maindata.getMessage().getTrainNumber();
        quota1 = maindata.getMessage().getQuota();
        timeStamp1 = maindata.getMessage().getTimeStamp();
    }
}
