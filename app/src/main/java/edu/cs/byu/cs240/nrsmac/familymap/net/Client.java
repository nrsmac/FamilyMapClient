package edu.cs.byu.cs240.nrsmac.familymap.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.cs.byu.cs240.nrsmac.familymap.net.Request.LoginRequest;
import edu.cs.byu.cs240.nrsmac.familymap.net.Response.LoginResponse;

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
                            "\"username\": \"" + request.getUsername() + "\"" +
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

                response = new LoginResponse(null,null,null,true);
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


    /*
    The readString method shows how to read a String from an InputStream.
*/
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
