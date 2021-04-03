

package edu.cs.byu.cs240.nrsmac.familymap.net.Tasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.cs.byu.cs240.nrsmac.familymap.net.Client;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.RegisterRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.RegisterResponse;

public class RegisterTask implements Runnable{
    private static final String TAG = "RegisterTask";

    private final Handler messageHandler;
    private final String hostName;
    private final String port;
    private final RegisterRequest request;
    private RegisterResponse response;

    public RegisterTask(Handler messageHandler, String hostName, String port, RegisterRequest request) {
        this.messageHandler = messageHandler;
        this.hostName = hostName;
        this.port = port;
        this.request = request;
        this.response = null;
    }


    @Override
    public void run() {
        Client client = new Client();
        response = client.register(hostName,port,request);
        Log.d(TAG, "Register: " + response.isSuccess());
        sendMessage(response);
    }

    private void sendMessage(RegisterResponse response) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        messageBundle.putBoolean(TaskConstants.SUCCESS, response.isSuccess());
        messageBundle.putString(TaskConstants.PERSON_ID_KEY, response.getPersonID());
        messageBundle.putString(TaskConstants.AUTH_TOKEN_KEY, response.getAuthtoken());
        messageBundle.putString(TaskConstants.USERNAME_KEY, response.getUsername());
        if (!response.isSuccess()) {
            messageBundle.putString(TaskConstants.MESSAGE_KEY, response.getMessage());
        } else {
            messageBundle.putString(TaskConstants.MESSAGE_KEY, "");
        }
        message.setData(messageBundle);
        messageHandler.sendMessage(message);
    }
}
