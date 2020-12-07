import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * ServerSocket
 *
 * The server side of the messaging app
 *
 * No help
 *
 * @author Ian Blacklock B11
 * @version 11/23/20
 */
public class Server {
    static private ArrayList<Group> groups;        //A collection of all of the groups for the messaging app
    static private ArrayList<User> users;          //A collection of all of the users for the messaging app
    private static int port = 4242;         //The port of the server
    ServerSocket serverSocket;              //The socket used to connect the server and client
    private static int userIDCounter;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            server.acceptor();
        }
    }

    public Server() {
        groups = new ArrayList<>();
        users = new ArrayList<>();
        userIDCounter = 0;
        readInUsersAndGroups("users.txt", "groups.txt");
    }

    /**
     * A method that is constantly checking for new clients to connect to the server
     */
    public void acceptor() {
        ServerThread thread;
        try {
            thread = new ServerThread(serverSocket.accept());
            Thread t = new Thread(thread);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads in the users and groups arraylist from two files
     * @param usersFilename The filename where the users arraylist is stored
     * @param groupsFilename The filename where the groups arraylist is stored
     */
    public void readInUsersAndGroups(String usersFilename, String groupsFilename) {
        try {
            users.clear();
            groups.clear();
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(usersFilename));
            } catch (EOFException e) {
                return;
            }
            try {
                users = (ArrayList<User>) in.readObject();

            } catch (EOFException e) {
                e.printStackTrace();
            }
            in.close();

            in = new ObjectInputStream(new FileInputStream(groupsFilename));
            try {
                groups = (ArrayList<Group>) in.readObject();

            } catch (EOFException e) {
                e.printStackTrace();
            }
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            userIDCounter++;
            user.setUserID(userIDCounter);
        }
    }

    /**
     * Writes the users and groups arraylist to two files
     * @param usersFilename The filename where the users arraylist is stored
     * @param groupsFilename The filename where the groups arraylist is stored
     */
    public static void writeUsersAndGroups(String usersFilename, String groupsFilename) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(usersFilename));
            out.flush();

            out.writeObject(users);

            out.close();

            out = new ObjectOutputStream(new FileOutputStream(groupsFilename));
            out.flush();

            out.writeObject(groups);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the users arraylist
     * @return The users arraylist
     */
    public static ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Returns the groups arraylist
     * @return The groups arraylist
     */
    public static ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     *  Returns the userIDCounter
     * @return The userIDCounter
     */
    public static int getUserIDCounter() {
        userIDCounter++;
        return userIDCounter;
    }
}

