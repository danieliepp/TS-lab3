package com.example.authentification.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coach implements Serializable {

    @SerializedName("_id")
    private String countryId;

    @SerializedName("coach_name")
    private String coachName;

    @SerializedName("coach_country")
    private String coachCountry;

    @SerializedName("coach_club")
    private String coachClub;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachCountry() {
        return coachCountry;
    }

    public void setCoachCountry(String coachCountry) {
        this.coachCountry = coachCountry;
    }

    public String getCoachClub() {
        return coachClub;
    }

    public void setCoachClub(String coachClub) {
        this.coachClub = coachClub;
    }

    public String toString() {
        return coachName + "|" + coachCountry;
    }
}
