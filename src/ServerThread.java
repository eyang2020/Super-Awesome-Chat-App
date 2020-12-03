import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
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
public class ServerThread implements Runnable{
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
        User currentUser = null;

        try{
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            out.writeObject(Server.getUsers());
            out.writeObject(Server.getGroups());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        OUTERLOOP:
        while (true) {
            try {
                input = (String) in.readObject();
                System.out.println(input);
                switch (input) {
                    case "createAccount" -> {
                        String username = (String) in.readObject();
                        String password = (String) in.readObject();
                        String name = (String) in.readObject();
                        String email = (String) in.readObject();
                        int userID = in.readInt();
                        long phoneNumber = in.readLong();
                        for (User user : Server.getUsers()) {
                            if (user.getUsername().equals(username)) {
                                out.writeObject(null);
                                out.flush();
                                break;
                            }
                        }
                        currentUser = new User(name, username, email, phoneNumber, password);
                        currentUser.setUserID(userID);
                        Server.getUsers().add(currentUser);
                        out.reset();
                        out.writeObject(currentUser);
                        out.flush();
                    }
                    case "login" -> {
                        String username = (String) in.readObject();
                        String password = (String) in.readObject();
                        for (User user : Server.getUsers()) {
                            if (user.getUsername().equals(username)) {
                                if (user.getPassword().equals(password)) {
                                    out.writeBoolean(true);
                                    currentUser = user;
                                    out.writeObject(currentUser);
                                    out.flush();
                                    continue OUTERLOOP;
                                }
                                //Add a way for the client to tell the reason for the failed login
                            }
                        }
                        System.out.println("spot1");
                        out.writeBoolean(false);
                        out.writeObject(currentUser);
                        out.flush();
                    }
                    case "createGroup" -> {
                        String groupName = (String) in.readObject();
                        String[] usernames = (String[]) in.readObject();
                        ArrayList<User> addedUsers = new ArrayList<>();
                        Group newGroup;
                        for (String username : usernames) {
                            for (User user : Server.getUsers()) {
                                if (user.getUsername().equals(username)) {
                                    addedUsers.add(user);
                                }
                            }
                        }
                        newGroup = new Group(groupName, addedUsers);
                        Server.getGroups().add(newGroup);
                        for (User user : addedUsers) {
                            user.addGroup(newGroup);
                        }
                        out.writeBoolean(true);
                        out.flush();
                    }
                    case "addMessage" -> {
                        Message message = (Message) in.readObject();
                        Group group = (Group) in.readObject();
                        group.addMessage(message);
                        assert out != null;
                        out.writeBoolean(true);
                        out.flush();
                    }
                    case "refresh" -> {
                        out.reset();
                        System.out.println(Server.getUsers());
                        out.writeObject(Server.getUsers());
                        System.out.println(Server.getGroups());
                        out.writeObject(Server.getGroups());
                        out.flush();
                    }
                    case "updateUser" -> {
                        User tempUser = (User) in.readObject();
                        for (User user : Server.getUsers()) {
                            System.out.println(user.getUsername());
                            System.out.println(tempUser.getUsername());
                            if (user.getUserID() == tempUser.getUserID()) {
                                user.setName(tempUser.getName());
                                user.setEmail(tempUser.getEmail());
                                user.setPassword(tempUser.getPassword());
                                user.setPhoneNumber(tempUser.getPhoneNumber());
                                user.setUsername(tempUser.getUsername());
                            }
                        }
                    }
                }
            } catch (EOFException | SocketException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
