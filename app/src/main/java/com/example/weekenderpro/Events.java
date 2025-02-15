package com.example.weekenderpro;

public class Events {
    public String imageUri;
    public  String eventPrice;
    public String eventType;
    public String eventTime;
    private String key;
    private String ID;
    private String eventDate;
    private String eventTitle;
    private String eventVenue;
    private String eventDescription;
    public String eventContacts;
    public  String ratings;

    public Events() {
    }

    public Events(String imageUri, String eventPrice, String eventType, String eventTime, String key, String ID, String eventDate, String eventTitle, String eventVenue, String eventDescription, String eventContacts, String ratings) {
        this.imageUri = imageUri;
        this.eventPrice = eventPrice;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.key = key;
        this.ID = ID;
        this.eventDate = eventDate;
        this.eventTitle = eventTitle;
        this.eventVenue = eventVenue;
        this.eventDescription = eventDescription;
        this.eventContacts = eventContacts;
        this.ratings = ratings;
    }

    public Events(String mEventLocation, String mEventName, String mEventRating, String mEventPrice, String mEventDate, String mEventContacts, String mEventDescription, String mEventTime, String mEventType, String sImage) {
        this.imageUri = sImage;
        this.eventPrice = mEventPrice;
        this.eventType = mEventType;
        this.eventTime = mEventTime;
        this.eventDate = mEventDate;
        this.eventTitle = mEventName;
        this.eventVenue = mEventLocation;
        this.eventDescription = mEventDescription;
        this.eventContacts = mEventContacts;
        this.ratings = mEventRating;
    }


    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getEventContacts() {
        return eventContacts;
    }

    public void setEventContacts(String eventContacts) {
        this.eventContacts = eventContacts;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
