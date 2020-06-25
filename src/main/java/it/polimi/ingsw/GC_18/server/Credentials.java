package it.polimi.ingsw.GC_18.server;

/**
 * Holds the credentials (user name and password) of a client.
 */
public final class Credentials {

    private String username;
    private String password;

    /**
     * Instantiates a holder for the credentials of a client
     * @param username - the client's user name
     * @param password - the client's password
     */
    Credentials(String username, String password) {
        this.username=username;
        this.password=password;
    }

    /**
     * @return the user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user name
     * @param username - the user name to set
     */
    public void setUsername(String username) {
        this.username=username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password - the password to set
     */
    public void setPassword(String password) {
        this.password=password;
    }

}
