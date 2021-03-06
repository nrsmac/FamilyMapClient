package edu.cs.byu.cs240.nrsmac.familymap.net.Response;


import java.util.ArrayList;

import edu.cs.byu.cs240.nrsmac.familymap.model.Event;

public class EventResponse {
    private ArrayList<Event> data;
    private boolean success;
    private String message;
    private String associatedUsername;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public EventResponse(ArrayList<Event> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public EventResponse(String associatedUsername,
                         String eventID,
                         String personID,
                         double latitude,
                         double longitude,
                         String country,
                         String city,
                         String eventType,
                         int year,
                         boolean success){

        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    public EventResponse(boolean success,String message){
        this.message = message;
        this.success = success;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
