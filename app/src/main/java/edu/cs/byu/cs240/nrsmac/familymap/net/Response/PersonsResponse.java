package edu.cs.byu.cs240.nrsmac.familymap.net.Response;


import java.util.ArrayList;

import edu.cs.byu.cs240.nrsmac.familymap.model.Person;

public class PersonsResponse {
    private String message;
    private ArrayList<Person> data;



    private boolean success;
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public PersonsResponse(ArrayList<Person> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public PersonsResponse(String associatedUsername,
                           String personID,
                           String firstName,
                           String lastName,
                           String gender,
                           String fatherID,
                           String motherID,
                           String spouseID,
                           boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    public PersonsResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public Person getPerson(String personID){
        Person person = null;
        if (data != null){
            for(Person p : data){
                if(p.getPersonID().equals(personID)){
                    person = p;
                }
            }
        }
        return person;
    }
}
