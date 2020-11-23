
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 *
 * User class for messaging app
 *
 * @author Ruth Baldwin
 * @version November 22, 2020
 *
 */
public class User {
    private String name; //name of user
    private String username; //username of user
    private String email; //email of user
    private long phoneNumber; //phone number of user
    private String password; //password for users account
    ArrayList<Group> groups; //groups the user is in

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
        groups = new ArrayList<Group>();

    }

    /**
     *
     * @param usernameToFind the username of the user looking to log in
     * @param passwordToCheck the password the user is entering to log in
     * @param userFileName the name of the file with the user information
     *
     * Constructor used to log in the user. Checks if username and password are correct. If username exists but password
     * is wrong it tells the user to enter a correct password
     */
    public User(String usernameToFind, String passwordToCheck, String userFileName) { // constructor to log user in, userFile is constant
        //access established file of users from driver class
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            String[] userData;
            while ((line = br.readLine()) != null) {
                userData = line.split(" , "); //separate the username and password on the file as "username , password"
                if (usernameToFind.equals(userData[0]) && passwordToCheck.equals(userData[1])) {
                    //successful login
                } else if (usernameToFind.equals(userData[0]) && !passwordToCheck.equals(userData[1])) {
                    //incorrect password
                } else {
                    //username not found
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public void setUsername(String newUsername, String userFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            String[] userData;
            ArrayList<String> users = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                users.add(line);
            }
            for (int i = 0; i < users.size(); i++) {
                userData = users.get(i).split(" , ");
                if (userData[0].equals(this.username)) {
                    users.set(i, newUsername + " , " + userData[1]);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(userFileName);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            for (int j = 0; j < users.size(); j++) {
                printWriter.println(users.get(j));
            }
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.username = newUsername;
    }

    /**
     * Allows the user to change their password
     * this method also changes the password on file
     * @param newPassword the new password of the user
     */
    public void setPassword(String username, String newPassword, String userFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            String[] userData;
            ArrayList<String> users = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                users.add(line);
            }
            for (int i = 0; i < users.size(); i++) {
                userData = users.get(i).split(" , ");
                if (userData[0].equals(this.password)) {
                    users.set(i, userData[0] + " , " + newPassword);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(userFileName);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            for (int j = 0; j < users.size(); j++) {
                printWriter.println(users.get(j));
            }
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.password = password;
    }

    /**
     * Allows the user to change their phone number
     * @param phoneNumber the new phone number of the user
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}


