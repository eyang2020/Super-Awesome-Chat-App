//package src;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * ClientSocket
 *
 * The client side of the messaging app
 *
 * No help
 *
 * @author Ian Blacklock B11
 * @version 11/23/20
 */
public class Client {
    int port;               //The port for the socket
    String host;            //The host for the socket
    Socket socket;          //The socket to connect the client and the server
    ObjectOutputStream out; //The output stream for sending objects to the server
    ObjectInputStream in;   //The input stream for getting objects from the server
    User currentUser = null;       //The user that this client represents

    public static void main(String[] args) throws IOException {
        Client client1 = new Client("localhost", 4242);
        client1.connectToServer();
        SwingUtilities.invokeLater(new Login(client1));
        client1.createGroup("Hello", new String[]{"Hi", "Bye"});
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        connectToServer();
    }

    /**
     * Connects the client to the server
     */
    public void connectToServer() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the info needed to create an account and sends it to the server
     * @param username the username for the account
     * @param password the password for the account\
     * @param name the name of the user
     * @param email the email for the account
     * @param phoneNumber the phone number for the account
     * @return the user that is created
     */
    public User createAccount(String username, String password, String name, String email, long phoneNumber) throws IOException {
        out.writeObject("createAccount");
        out.writeObject(username);
        out.writeObject(password);
        out.writeObject(name);
        out.writeObject(email);
        out.writeLong(phoneNumber);
        out.flush();
        try {
            currentUser = (User) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return currentUser;
    }

    /**
     * Gets the info needed to login and sends it to the server
     * @param username the username of the user
     * @param password the password of the user
     * @return a boolean telling if the login was successfully
     */
    public boolean login(String username, String password) throws IOException {
        boolean loggedin;
        out.writeObject("login");
        out.writeObject(username);
        out.writeObject(password);
        out.flush();
        loggedin = in.readBoolean();
        try {
            currentUser = (User) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loggedin;
    }

    /**
     *
     * @param groupName The name of the group
     * @param usernames The usernames of the people to be added to the group
     * @return Whether or not the group was created successfully
     */
    public boolean createGroup(String groupName, String[] usernames) throws IOException{
        boolean created;
        out.writeObject("createGroup");
        out.writeObject(groupName);
        out.writeObject(usernames);
        out.flush();
        created = in.readBoolean();
        return created;
    }

    /**
     * Adds the given message to the given group
     * @param message The message to be added
     * @param group The group the message is to be added to
     * @return Whether or not the message was created successfully
     */
    public boolean addMessage(Message message, Group group) throws IOException {
        boolean added = false;
        out.writeObject("addMessage");
        out.writeObject(message);
        out.writeObject(group);
        out.flush();
        added = in.readBoolean();
        return added;
    }

    /**
     * Returns the current user
     * @return The current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
