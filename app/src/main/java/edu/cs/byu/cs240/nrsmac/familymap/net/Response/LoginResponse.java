package edu.cs.byu.cs240.nrsmac.familymap.net.Response;


/**
 * A login response sent from the service to the handler
 */
public class LoginResponse {
    private String authtoken;
    private String username;
    private String personID;
    private final boolean success;
    private String message;

    public LoginResponse(String authtoken, String username, String personID, boolean success){
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }


    /**
     * @param message
     * @param success
     */
    public LoginResponse(String message, boolean success) {
        this.success = success;
        this.message = message;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
