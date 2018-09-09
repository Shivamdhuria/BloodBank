package com.elixer.bloodbank.query;

public class RequestModel {


    public String name;
    public String bloodRequired;
    public long epochTime;
    public String placeOfCampaign;
    Boolean status;

    public RequestModel()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public RequestModel(String name, String bloodRequired, long epochTime, String placeOfCampaign,Boolean status){


        this.name=name;
        this.bloodRequired=bloodRequired;
        this.epochTime=epochTime;
        this.placeOfCampaign=placeOfCampaign;
        this.status=status;


    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }






    public String getBloodRequired() {
        return bloodRequired;
    }

    public void setBloodRequired(String bloodRequired) {
        this.bloodRequired = bloodRequired;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    public String getPlaceOfCampaign() {
        return placeOfCampaign;
    }

    public void setPlaceOfCampaign(String placeOfCampaign) {
        this.placeOfCampaign = placeOfCampaign;
    }
}
