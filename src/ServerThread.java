import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ServerThread
 *
 * A thread run by the server to host multiple clients
 *
 * No help
 *
 * @author Ian Blacklock B11
 * @version 11/23/20
 */
public class ServerThread extends Server implements Runnable{
    private Socket socket;          //The socket that connects the client and the server

    /**
     *
     * @param socket The socket that connects the client and the server
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * The method that is run when ever a client connects to the server
     */
    public void run(){
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        String input;
        User currentUser;

        try{
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input = (String) in.readObject();
            switch (input) {
                case "createAccount" -> {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String name = (String) in.readObject();
                    String email = (String) in.readObject();
                    long phoneNumber = in.readLong();
                    for (User user : users) {
                        if(user.getUsername().equals(username)) {
                            out.writeBoolean(false);
                            out.flush();
                            break;
                        }
                    }
                    currentUser = new User(name, username, email, phoneNumber, password);
                    users.add(currentUser);
                    out.writeBoolean(true);
                    out.flush();
                }
                case "login" -> {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    for (User user : users) {
                        if(user.getUsername().equals(username)) {
                            if(user.getPassword().equals(password)) {
                                out.writeBoolean(true);
                                currentUser = user;
                                out.flush();
                                break;
                            }
                            //Add a way for the client to tell the reason for the failed login
                        }
                    }
                    out.writeBoolean(false);
                    out.flush();
                }
                case "createGroup" -> {
                    String groupName = (String) in.readObject();
                    String[] usernames = (String[]) in.readObject();
                    ArrayList<User> addedUsers = new ArrayList<>();
                    Group newGroup;
                    for (String username : usernames) {
                        for (User user : users) {
                            if(user.getUsername().equals(username)) {
                                addedUsers.add(user);
                            }
                        }
                    }
                    newGroup = new Group(groupName, addedUsers);
                    groups.add(newGroup);
                    for (User user : addedUsers) {
                        user.addGroup(newGroup);
                    }
                    out.writeBoolean(true);
                    out.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
