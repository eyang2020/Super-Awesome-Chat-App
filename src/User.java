//package src;

import java.io.*;
import java.util.ArrayList;


/**
 *
 * User class for messaging app
 *
 * @author Ruth Baldwin
 * @version November 22, 2020
 *
 */
public class User implements Serializable {
    private String name; //name of user
    private String username; //username of user
    private String email; //email of user
    private long phoneNumber; //phone number of user
    private String password; //password for users account
    private ArrayList<Group> groups; //groups the user is in

    /**
     *
     * @param name name of user
     * @param username username of user
     * @param email email of user
     * @param phoneNumber phone number of user
     * @param password password of user
     *
     * This constructor creates a new user, this will only happen once per user. The username
     * and password of the user created is stored on a file.
     */
    public User(String name, String username, String email, long phoneNumber, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        groups = new ArrayList<>();
    }

    public User() {}
    /**
     *
     * @param usernameToFind the username of the user looking to log in
     * @param passwordToCheck the password the user is entering to log in
     * @param userFileName the name of the file with the user information
     *
     * Constructor used to log in the user. Checks if username and password are correct. If username exists but password
     * is wrong it tells the user to enter a correct password
     */

    /**
     * Allows the user to change their name
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allows the user to change their email
     * @param email the new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Allows the user to change their username
     * this method also changes the username on file
     * @param newUsername the new username of the user
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    /**
     * Allows the user to change their password
     * @param newPassword the new password of the user
     */
    public void setPassword(String newPassword) {
        this.password = password;
    }

    /**
     * Allows the user to change their phone number
     * @param phoneNumber the new phone number of the user
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Adds a given group to the groups arraylist
     * @param group the group to be added to groups
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     *
     * @return the groups of this user
     */
    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    /**
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return the phone number of the user
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", groups=" + groups +
                '}';
    }
}


