package edu.cs.byu.cs240.nrsmac.familymap.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class DataCache {
    private static DataCache _instance;

    /**
     * Key is the person id, Person is the associated person
     */
    private HashMap<String, Person> persons;
    /**
     * Key is the event id, Person is the associated event
     */
    private HashMap<String, Event> events;
    /**
     * List of all events given a person id
     */
    private HashMap<String, List<Event>> personEvents;
    /**
     * All event types in cache
     */
    private HashSet<String> eventTypes;
    /**
     * Each event is correlated with an android color
     */
    private HashMap<String, Color> eventColors;
    /**
     * Returns a list of a person's children given their person id
     */
    private HashMap<String, List<Person>> childrenByPerson;
    /**
     * All maternal ancestors for signed in user
     */
    private TreeSet<String> maternalAncestors;
    /**
     * All paternal ancestors for signed in user
     */
    private TreeSet<String> paternalAncestors;

    public void setSyncSuccess(boolean syncSuccess) {
        this.syncSuccess = syncSuccess;
    }

    private boolean syncSuccess;

    /**
     * @return the singleton instance of the DataCache
     */
    public static DataCache instance(){
        if(_instance == null){
            _instance = new DataCache();
        }
        return _instance;
    }

    private DataCache() {
        this.syncSuccess = false;
        this.persons = new HashMap<>();
        this.events = new HashMap<>();
        this.personEvents = new HashMap<>();
        this.eventTypes = new HashSet<>();
        this.eventColors = new HashMap<>();
        this.childrenByPerson = new HashMap<>();
        this.maternalAncestors = new TreeSet<>();
        this.paternalAncestors = new TreeSet<>();
    }

    public void populate(ArrayList<Person> persons, ArrayList<Event> events){
        for (Person p: persons){
            this.persons.put(p.getPersonID(),p);

            //Populate personEvents
            ArrayList<Event> thisPersonEvents = new ArrayList<>();
            for(Event e:events){
                if(p.getPersonID().equals(e.getPersonID())){
                    thisPersonEvents.add(e);
                }
            }
            this.personEvents.put(p.getPersonID(),thisPersonEvents);

            //Populate childrenByPerson
            ArrayList<Person> thisPersonChildren = new ArrayList<>();
            for(Person child : persons){
                if(child.getFatherID() != null && child.getMotherID() != null ){
                    if(child.getFatherID().equals(p.getPersonID()) ||
                            child.getMotherID().equals(p.getPersonID()))
                    {
                        thisPersonChildren.add(child);
                    }
                }
            }
            this.childrenByPerson.put(p.getPersonID(), thisPersonChildren);

            //TODO Populate maternalAncestors
            //TODO Populate paternalAncestors
        }
        for (Event e : events){
            this.events.put(e.getEventID(),e);

            //Update event types
            this.eventTypes.add(e.getEventType());

        }

        //TODO populate event colors
    }

    public HashMap<String, Person> getPersons() {
        return persons;
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public HashMap<String, List<Event>> getPersonEvents() {
        return personEvents;
    }

    public HashSet<String> getEventTypes() {
        return eventTypes;
    }

    public HashMap<String, Color> getEventColors() {
        return eventColors;
    }

    public HashMap<String, List<Person>> getChildrenByPerson() {
        return childrenByPerson;
    }

    public TreeSet<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public TreeSet<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public boolean isSyncSuccess() {
        return syncSuccess;
    }
}
