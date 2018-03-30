
package com.example.android.miwok.SeatAval.mainapp2.SeatAvailData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BkgCfg {

    @SerializedName("seniorCitizenApplicable")
    @Expose
    private Boolean seniorCitizenApplicable;
    @SerializedName("foodChoiceEnabled")
    @Expose
    private Boolean foodChoiceEnabled;
    @SerializedName("idRequired")
    @Expose
    private Boolean idRequired;
    @SerializedName("bedRollFlagEnabled")
    @Expose
    private Boolean bedRollFlagEnabled;
    @SerializedName("maxPassengers")
    @Expose
    private Integer maxPassengers;
    @SerializedName("maxInfants")
    @Expose
    private Integer maxInfants;
    @SerializedName("minNameLength")
    @Expose
    private Integer minNameLength;
    @SerializedName("maxNameLength")
    @Expose
    private Integer maxNameLength;
    @SerializedName("srctznAge")
    @Expose
    private Integer srctznAge;
    @SerializedName("srctnwAge")
    @Expose
    private Integer srctnwAge;
    @SerializedName("maxARPDays")
    @Expose
    private Integer maxARPDays;
    @SerializedName("maxRetentionDays")
    @Expose
    private Integer maxRetentionDays;
    @SerializedName("minPassengerAge")
    @Expose
    private Integer minPassengerAge;
    @SerializedName("maxPassengerAge")
    @Expose
    private Integer maxPassengerAge;
    @SerializedName("maxChildAge")
    @Expose
    private Integer maxChildAge;
    @SerializedName("minIdCardLength")
    @Expose
    private Integer minIdCardLength;
    @SerializedName("maxIdCardLength")
    @Expose
    private Integer maxIdCardLength;
    @SerializedName("minPassportLength")
    @Expose
    private Integer minPassportLength;
    @SerializedName("maxPassportLength")
    @Expose
    private Integer maxPassportLength;
    @SerializedName("lowerBerthApplicable")
    @Expose
    private Boolean lowerBerthApplicable;
    @SerializedName("newTimeTable")
    @Expose
    private Boolean newTimeTable;
    @SerializedName("childBerthMandatory")
    @Expose
    private Boolean childBerthMandatory;
    @SerializedName("validIdCardTypes")
    @Expose
    private List<String> validIdCardTypes = null;
    @SerializedName("applicableBerthTypes")
    @Expose
    private List<String> applicableBerthTypes = null;
    @SerializedName("suvidhaTrain")
    @Expose
    private Boolean suvidhaTrain;
    @SerializedName("specialTatkal")
    @Expose
    private Boolean specialTatkal;
    @SerializedName("atasEnable")
    @Expose
    private Boolean atasEnable;
    @SerializedName("gatimaanTrain")
    @Expose
    private Boolean gatimaanTrain;
    @SerializedName("travelInsuranceEnabled")
    @Expose
    private Boolean travelInsuranceEnabled;
    @SerializedName("travelInsuranceFareMsg")
    @Expose
    private String travelInsuranceFareMsg;
    @SerializedName("uidVerificationPsgnInputFlag")
    @Expose
    private Integer uidVerificationPsgnInputFlag;
    @SerializedName("uidVerificationMasterListFlag")
    @Expose
    private Integer uidVerificationMasterListFlag;
    @SerializedName("uidMandatoryFlag")
    @Expose
    private Integer uidMandatoryFlag;
    @SerializedName("gstDetailInputFlag")
    @Expose
    private Boolean gstDetailInputFlag;
    @SerializedName("gstinPattern")
    @Expose
    private String gstinPattern;
    @SerializedName("forgoConcession")
    @Expose
    private Boolean forgoConcession;

    public Boolean getSeniorCitizenApplicable() {
        return seniorCitizenApplicable;
    }

    public void setSeniorCitizenApplicable(Boolean seniorCitizenApplicable) {
        this.seniorCitizenApplicable = seniorCitizenApplicable;
    }

    public Boolean getFoodChoiceEnabled() {
        return foodChoiceEnabled;
    }

    public void setFoodChoiceEnabled(Boolean foodChoiceEnabled) {
        this.foodChoiceEnabled = foodChoiceEnabled;
    }

    public Boolean getIdRequired() {
        return idRequired;
    }

    public void setIdRequired(Boolean idRequired) {
        this.idRequired = idRequired;
    }

    public Boolean getBedRollFlagEnabled() {
        return bedRollFlagEnabled;
    }

    public void setBedRollFlagEnabled(Boolean bedRollFlagEnabled) {
        this.bedRollFlagEnabled = bedRollFlagEnabled;
    }

    public Integer getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(Integer maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Integer getMaxInfants() {
        return maxInfants;
    }

    public void setMaxInfants(Integer maxInfants) {
        this.maxInfants = maxInfants;
    }

    public Integer getMinNameLength() {
        return minNameLength;
    }

    public void setMinNameLength(Integer minNameLength) {
        this.minNameLength = minNameLength;
    }

    public Integer getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(Integer maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public Integer getSrctznAge() {
        return srctznAge;
    }

    public void setSrctznAge(Integer srctznAge) {
        this.srctznAge = srctznAge;
    }

    public Integer getSrctnwAge() {
        return srctnwAge;
    }

    public void setSrctnwAge(Integer srctnwAge) {
        this.srctnwAge = srctnwAge;
    }

    public Integer getMaxARPDays() {
        return maxARPDays;
    }

    public void setMaxARPDays(Integer maxARPDays) {
        this.maxARPDays = maxARPDays;
    }

    public Integer getMaxRetentionDays() {
        return maxRetentionDays;
    }

    public void setMaxRetentionDays(Integer maxRetentionDays) {
        this.maxRetentionDays = maxRetentionDays;
    }

    public Integer getMinPassengerAge() {
        return minPassengerAge;
    }

    public void setMinPassengerAge(Integer minPassengerAge) {
        this.minPassengerAge = minPassengerAge;
    }

    public Integer getMaxPassengerAge() {
        return maxPassengerAge;
    }

    public void setMaxPassengerAge(Integer maxPassengerAge) {
        this.maxPassengerAge = maxPassengerAge;
    }

    public Integer getMaxChildAge() {
        return maxChildAge;
    }

    public void setMaxChildAge(Integer maxChildAge) {
        this.maxChildAge = maxChildAge;
    }

    public Integer getMinIdCardLength() {
        return minIdCardLength;
    }

    public void setMinIdCardLength(Integer minIdCardLength) {
        this.minIdCardLength = minIdCardLength;
    }

    public Integer getMaxIdCardLength() {
        return maxIdCardLength;
    }

    public void setMaxIdCardLength(Integer maxIdCardLength) {
        this.maxIdCardLength = maxIdCardLength;
    }

    public Integer getMinPassportLength() {
        return minPassportLength;
    }

    public void setMinPassportLength(Integer minPassportLength) {
        this.minPassportLength = minPassportLength;
    }

    public Integer getMaxPassportLength() {
        return maxPassportLength;
    }

    public void setMaxPassportLength(Integer maxPassportLength) {
        this.maxPassportLength = maxPassportLength;
    }

    public Boolean getLowerBerthApplicable() {
        return lowerBerthApplicable;
    }

    public void setLowerBerthApplicable(Boolean lowerBerthApplicable) {
        this.lowerBerthApplicable = lowerBerthApplicable;
    }

    public Boolean getNewTimeTable() {
        return newTimeTable;
    }

    public void setNewTimeTable(Boolean newTimeTable) {
        this.newTimeTable = newTimeTable;
    }

    public Boolean getChildBerthMandatory() {
        return childBerthMandatory;
    }

    public void setChildBerthMandatory(Boolean childBerthMandatory) {
        this.childBerthMandatory = childBerthMandatory;
    }

    public List<String> getValidIdCardTypes() {
        return validIdCardTypes;
    }

    public void setValidIdCardTypes(List<String> validIdCardTypes) {
        this.validIdCardTypes = validIdCardTypes;
    }

    public List<String> getApplicableBerthTypes() {
        return applicableBerthTypes;
    }

    public void setApplicableBerthTypes(List<String> applicableBerthTypes) {
        this.applicableBerthTypes = applicableBerthTypes;
    }

    public Boolean getSuvidhaTrain() {
        return suvidhaTrain;
    }

    public void setSuvidhaTrain(Boolean suvidhaTrain) {
        this.suvidhaTrain = suvidhaTrain;
    }

    public Boolean getSpecialTatkal() {
        return specialTatkal;
    }

    public void setSpecialTatkal(Boolean specialTatkal) {
        this.specialTatkal = specialTatkal;
    }

    public Boolean getAtasEnable() {
        return atasEnable;
    }

    public void setAtasEnable(Boolean atasEnable) {
        this.atasEnable = atasEnable;
    }

    public Boolean getGatimaanTrain() {
        return gatimaanTrain;
    }

    public void setGatimaanTrain(Boolean gatimaanTrain) {
        this.gatimaanTrain = gatimaanTrain;
    }

    public Boolean getTravelInsuranceEnabled() {
        return travelInsuranceEnabled;
    }

    public void setTravelInsuranceEnabled(Boolean travelInsuranceEnabled) {
        this.travelInsuranceEnabled = travelInsuranceEnabled;
    }

    public String getTravelInsuranceFareMsg() {
        return travelInsuranceFareMsg;
    }

    public void setTravelInsuranceFareMsg(String travelInsuranceFareMsg) {
        this.travelInsuranceFareMsg = travelInsuranceFareMsg;
    }

    public Integer getUidVerificationPsgnInputFlag() {
        return uidVerificationPsgnInputFlag;
    }

    public void setUidVerificationPsgnInputFlag(Integer uidVerificationPsgnInputFlag) {
        this.uidVerificationPsgnInputFlag = uidVerificationPsgnInputFlag;
    }

    public Integer getUidVerificationMasterListFlag() {
        return uidVerificationMasterListFlag;
    }

    public void setUidVerificationMasterListFlag(Integer uidVerificationMasterListFlag) {
        this.uidVerificationMasterListFlag = uidVerificationMasterListFlag;
    }

    public Integer getUidMandatoryFlag() {
        return uidMandatoryFlag;
    }

    public void setUidMandatoryFlag(Integer uidMandatoryFlag) {
        this.uidMandatoryFlag = uidMandatoryFlag;
    }

    public Boolean getGstDetailInputFlag() {
        return gstDetailInputFlag;
    }

    public void setGstDetailInputFlag(Boolean gstDetailInputFlag) {
        this.gstDetailInputFlag = gstDetailInputFlag;
    }

    public String getGstinPattern() {
        return gstinPattern;
    }

    public void setGstinPattern(String gstinPattern) {
        this.gstinPattern = gstinPattern;
    }

    public Boolean getForgoConcession() {
        return forgoConcession;
    }

    public void setForgoConcession(Boolean forgoConcession) {
        this.forgoConcession = forgoConcession;
    }

}
