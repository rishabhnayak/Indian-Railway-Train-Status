
package com.example.android.miwok.PnrStatus.mainapp.PnrAllData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("boardingPoint")
    @Expose
    private String boardingPoint;
    @SerializedName("bookingFare")
    @Expose
    private Integer bookingFare;
    @SerializedName("chartStatus")
    @Expose
    private String chartStatus;
    @SerializedName("dateOfJourney")
    @Expose
    private String dateOfJourney;
    @SerializedName("destinationStation")
    @Expose
    private String destinationStation;
    @SerializedName("informationMessage")
    @Expose
    private List<String> informationMessage = null;
    @SerializedName("journeyClass")
    @Expose
    private String journeyClass;
    @SerializedName("numberOfpassenger")
    @Expose
    private Integer numberOfpassenger;
    @SerializedName("passengerList")
    @Expose
    private List<PassengerList> passengerList = null;
    @SerializedName("pnrNumber")
    @Expose
    private String pnrNumber;
    @SerializedName("reservationUpto")
    @Expose
    private String reservationUpto;
    @SerializedName("serverId")
    @Expose
    private String serverId;
    @SerializedName("sourceStation")
    @Expose
    private String sourceStation;
    @SerializedName("ticketFare")
    @Expose
    private Integer ticketFare;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("trainCancelStatus")
    @Expose
    private String trainCancelStatus;
    @SerializedName("trainName")
    @Expose
    private String trainName;
    @SerializedName("trainNumber")
    @Expose
    private String trainNumber;
    @SerializedName("quota")
    @Expose
    private String quota;
    @SerializedName("generatedTimeStamp")
    @Expose
    private GeneratedTimeStamp generatedTimeStamp;

    public String getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public Integer getBookingFare() {
        return bookingFare;
    }

    public void setBookingFare(Integer bookingFare) {
        this.bookingFare = bookingFare;
    }

    public String getChartStatus() {
        return chartStatus;
    }

    public void setChartStatus(String chartStatus) {
        this.chartStatus = chartStatus;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public List<String> getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(List<String> informationMessage) {
        this.informationMessage = informationMessage;
    }

    public String getJourneyClass() {
        return journeyClass;
    }

    public void setJourneyClass(String journeyClass) {
        this.journeyClass = journeyClass;
    }

    public Integer getNumberOfpassenger() {
        return numberOfpassenger;
    }

    public void setNumberOfpassenger(Integer numberOfpassenger) {
        this.numberOfpassenger = numberOfpassenger;
    }

    public List<PassengerList> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<PassengerList> passengerList) {
        this.passengerList = passengerList;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getReservationUpto() {
        return reservationUpto;
    }

    public void setReservationUpto(String reservationUpto) {
        this.reservationUpto = reservationUpto;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public Integer getTicketFare() {
        return ticketFare;
    }

    public void setTicketFare(Integer ticketFare) {
        this.ticketFare = ticketFare;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTrainCancelStatus() {
        return trainCancelStatus;
    }

    public void setTrainCancelStatus(String trainCancelStatus) {
        this.trainCancelStatus = trainCancelStatus;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public GeneratedTimeStamp getGeneratedTimeStamp() {
        return generatedTimeStamp;
    }

    public void setGeneratedTimeStamp(GeneratedTimeStamp generatedTimeStamp) {
        this.generatedTimeStamp = generatedTimeStamp;
    }

}
