package edu.cs.byu.cs240.nrsmac.familymap.net.Request;


/**
 * A login request sent from the handler to the service
 */
public class LoginRequest {

    private final String password;
    private final String username;

    /**
     * Creates a new request.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
