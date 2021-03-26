package edu.cs.byu.cs240.nrsmac.familymap.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.cs.byu.cs240.nrsmac.familymap.model.Person;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.EventRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.LoginRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.PersonsRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Request.RegisterRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.EventResponse;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.LoginResponse;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.PersonsResponse;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.RegisterResponse;

import static edu.cs.byu.cs240.nrsmac.familymap.net.Codex.readString;
import static edu.cs.byu.cs240.nrsmac.familymap.net.Codex.writeString;

public class Client {

    public void main(String serverHost, String serverPort){
        LoginRequest request = new LoginRequest("nrsmac","Nrsmac12");
        login(serverHost, serverPort, request);
    }

    public LoginResponse login(String serverHost, String serverPort, LoginRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            String reqData =
                    "{" +
                            "\"username\": \"" + request.getUsername() + "\"" + ",\n"+
                            "\"password\": \"" + request.getPassword() + "\"" +
                            "}";
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            LoginResponse response;

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                System.out.println(respData);

                response = Json.deserialize(respData, LoginResponse.class);
            } else {
                response = new LoginResponse("Login failed!",false);
            }
            return response;
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
            return new LoginResponse("Login failed! "+ e.getMessage(),false);
        }
    }

    public PersonsResponse persons(String serverHost, String serverPort, PersonsRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
//            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", request.getAuthtoken());

            http.connect();

            PersonsResponse response;

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                System.out.println(respData);

                response = Json.deserialize(respData, PersonsResponse.class);
                Person userPerson = response.getData().get(0);

                response.setAssociatedUsername(userPerson.getAssociatedUsername());
                response.setPersonID(userPerson.getPersonID());
                response.setFirstName(userPerson.getFirstName());
                response.setLastName(userPerson.getLastName());
                response.setGender(userPerson.getGender());
                response.setFatherID(userPerson.getFatherID());
                response.setMotherID(userPerson.getMotherID());
                response.setSpouseID(userPerson.getSpouseID());

            } else {
                response = new PersonsResponse(false, "Getting people from current user failed!");
            }
            return response;
        }
        catch (Exception e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
            return new PersonsResponse(false,"Getting people from current user failed! "+ e.getMessage());
        }
    }


    public EventResponse event(String serverHost, String serverPort, EventRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
//            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", request.getAuthtoken());

            http.connect();

            EventResponse response;

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                System.out.println(respData);

                response = Json.deserialize(respData, EventResponse.class);


            } else {
                response = new EventResponse(false, "Getting people from current user failed!");
            }
            return response;
        }
        catch (Exception e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
            return new EventResponse(false,"Getting people from current user failed! "+ e.getMessage());
        }
    }

    public RegisterResponse register(String serverHost, String serverPort, RegisterRequest request) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            String reqData = Json.serialize(request);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData,reqBody);
            http.getOutputStream().close();

            RegisterResponse response;

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                System.out.println(respData);

                response = Json.deserialize(respData, RegisterResponse.class);

            } else {
                response = new RegisterResponse(false, "Register failed");
            }
            return response;
        }
        catch (Exception e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
            return new RegisterResponse(false,"Register failed "+ e.getMessage());
        }
    }

}
