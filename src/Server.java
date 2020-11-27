import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    static public ArrayList<Group> groups;        //A collection of all of the groups for the messaging app
    static public ArrayList<User> users;          //A collection of all of the users for the messaging app
    private static int port = 4242;         //The port of the server
    ServerSocket serverSocket;              //The socket used to connect the server and client

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        while (true) {
            server.acceptor();
        }
    }

    public Server() {
        groups = new ArrayList<>();
        users = new ArrayList<>();
    }

    /**
     * A method that is constantly checking for new clients to connect to the server
     */
    public void acceptor() {
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            ServerThread thread;
            try{
                thread = new ServerThread(serverSocket.accept());
                Thread t = new Thread(thread);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readInUsersAndGroups(String usersFilename, String groupsFilename) {
        try {
            users.clear();
            groups.clear();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(usersFilename));
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
    }

    public void writeUsersAndGroups(String usersFilename, String groupsFilename) {
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
}

