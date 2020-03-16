package com.example.authentification.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Participant implements Serializable {

    private Participant(final Builder builder) {
        this.coachName = builder.coachName;
        this.club = builder.club;
        this.participantCountry = builder.participantCountry;
        this.participantName = builder.participantName;
        this.sex = builder.sex;
        this.age = builder.age;
        this.weight = builder.weight;
        this.birthday = builder.birthday;
    }

    @SerializedName("coach_id")
    private String coachId;

    @SerializedName("coach")
    private String coachName;

    @SerializedName("club")
    private String club;

    @SerializedName("country")
    private String participantCountry;

    @SerializedName("id")
    private String participant_id;

    @SerializedName("participant_id")
    private String participantID;

    @SerializedName("name")
    private String participantName;

    @SerializedName("gender")
    private String sex;

    @SerializedName("age")
    private String age;

    @SerializedName("weight")
    private String weight;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("photo")
    private String photo;

    @SerializedName("issue")
    private String issue;

    @SerializedName("coach_country")
    private String coachCountry;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("country_id")
    private String countryId;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("participant_country_full")
    private String participantCountryFull;

    @SerializedName("participant_first_name")
    private String participantFirstName;

    @SerializedName("participant_last_name")
    private String participantLastName;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getParticipantCountry() {
        return participantCountry;
    }

    public void setParticipantCountry(String participantCountry) {
        this.participantCountry = participantCountry;
    }

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getCoachCountry() {
        return coachCountry;
    }

    public void setCoachCountry(String coachCountry) {
        this.coachCountry = coachCountry;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParticipantCountryFull() {
        return participantCountryFull;
    }

    public void setParticipantCountryFull(String participantCountryFull) {
        this.participantCountryFull = participantCountryFull;
    }

    public String getParticipantFirstName() {
        return participantFirstName;
    }

    public void setParticipantFirstName(String participantFirstName) {
        this.participantFirstName = participantFirstName;
    }

    public String getParticipantLastName() {
        return participantLastName;
    }

    public void setParticipantLastName(String participantLastName) {
        this.participantLastName = participantLastName;
    }

    public static class Builder {
        private String participantName;
        private String coachName;
        private String club;
        private String participantCountry;
        private String sex;
        private String age;
        private String weight;
        private String birthday;

        public Builder setParticipantName(final String participantName) {
            this.participantName = participantName;
            return this;
        }

        public Builder setCoachName(final String coachName) {
            this.coachName = coachName;
            return this;
        }

        public Builder setClub(final String club) {
            this.club = club;
            return this;
        }

        public Builder setParticipantCountry(final String participantCountry) {
            this.participantCountry = participantCountry;
            return this;
        }

        public Builder setSex(final String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setAge(final String age) {
            this.age = age;
            return this;
        }

        public Builder setWeight(final String weight) {
            this.weight = weight;
            return this;
        }

        public Builder setBirthday(final String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Participant create() {
            return new Participant(this);
        }
    }
}
