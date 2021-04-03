package edu.cs.byu.cs240.nrsmac.familymap.net.Tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.cs.byu.cs240.nrsmac.familymap.model.DataCache;
import edu.cs.byu.cs240.nrsmac.familymap.net.Client;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.EventRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.PersonsRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.EventResponse;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.PersonsResponse;

public class PersonTask implements Runnable{
    private static final String TAG = "PersonTask";

    private final Handler messageHandler;
    private final String hostName;
    private final String port;
    private final PersonsRequest request;
    private String userPersonId;

    public PersonTask(Handler messageHandler, String hostName, String port, PersonsRequest request) {
        this.messageHandler = messageHandler;
        this.hostName = hostName;
        this.port = port;
        this.request = request;
    }


    @Override
    public void run() {
        Client client = new Client();
        PersonsResponse personsResponse = client.persons(hostName,port,request);
        userPersonId = personsResponse.getPersonID();
        Log.d(TAG, "PersonTask : " +personsResponse.isSuccess());
        EventRequest eventRequest = new EventRequest(request.getAuthtoken());
        EventResponse eventResponse = client.event(hostName,port,eventRequest);
        DataCache.instance().populate(personsResponse.getData(),eventResponse.getData());
        sendMessage(personsResponse);
    }

    private void sendMessage(PersonsResponse response) {
        Message message = Message.obtain();
        Bundle messageBundle = new Bundle();



        if (!response.isSuccess()){
            messageBundle.putString(TaskConstants.MESSAGE_KEY, response.getMessage());
        } else {
            messageBundle.putString(TaskConstants.MESSAGE_KEY, "");
            messageBundle.putString(TaskConstants.USERNAME_KEY, response.getAssociatedUsername());
            messageBundle.putString(TaskConstants.PERSON_ID_KEY, response.getPersonID());
            messageBundle.putString(TaskConstants.FIRST_NAME_KEY, response.getFirstName());
            messageBundle.putString(TaskConstants.LAST_NAME_KEY, response.getLastName());
            messageBundle.putString(TaskConstants.GENDER_KEY, response.getGender());
            messageBundle.putString(TaskConstants.FATHER_ID_KEY, response.getFatherID());
            messageBundle.putString(TaskConstants.MOTHER_ID_KEY, response.getMotherID());
            messageBundle.putString(TaskConstants.SPOUSE_ID_KEY, response.getSpouseID());
        }

        messageBundle.putBoolean(TaskConstants.SUCCESS, response.isSuccess());
        message.setData(messageBundle);
        messageHandler.sendMessage(message);

    }
}
