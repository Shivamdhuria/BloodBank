package com.example.android.bloodbank.main.query;

public class RequestModel {


    public String name;
    public String bloodRequired;
    public long epochTime;
    public String placeOfCampaign;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestModel(String name, String bloodRequired, long epochTime, String placeOfCampaign){


        this.name=name;
        this.bloodRequired=bloodRequired;
        this.epochTime=epochTime;
        this.placeOfCampaign=placeOfCampaign;


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
