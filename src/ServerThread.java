package src;

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
                if (!input.equals("updateCurrentUser")) {
                    System.out.println(input);
                }
                switch (input) {
                    case "createAccount" -> {
                        String username = (String) in.readObject();
                        String password = (String) in.readObject();
                        String name = (String) in.readObject();
                        String email = (String) in.readObject();
                        int userID = Server.getUserIDCounter();
                        long phoneNumber = in.readLong();
                        for (User user : Server.getUsers()) {
                            if (user.getUsername().equals(username)) {
                                out.reset();
                                out.writeBoolean(false);
                                out.flush();
                                break;
                            }
                        }
                        out.writeBoolean(true);
                        out.flush();
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
                        out.writeBoolean(false);
                        out.writeObject(currentUser);
                        out.flush();
                    }
                    case "createGroup" -> {
                        System.out.println("Yo");
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
                        System.out.println(Server.getGroups());
                        Server.getGroups().add(newGroup);
                        System.out.println(Server.getGroups());
                        for (User user : addedUsers) {
                            System.out.println("hello");
                            user.addGroup(newGroup);
                        }
                        out.writeBoolean(true);
                        out.flush();
                    }
                    case "addMessage" -> {
                        Message message = (Message) in.readObject();
                        Group group = (Group) in.readObject();
                        for (Group group1 : Server.getGroups()) {
                            if (group1.getGroupName().equals(group.getGroupName())) {
                                group1.addMessage(message);
                            }
                        }
                        assert out != null;
                        out.writeBoolean(true);
                        out.flush();
                    }
                    case "refresh" -> {
                        out.reset();
                        out.writeObject(Server.getUsers());
                        out.writeObject(Server.getGroups());
                        out.flush();
                    }
                    case "updateServerUser" -> {
                        User tempUser = (User) in.readObject();
                        for (User user : Server.getUsers()) {
                            if (user.getUserID() == tempUser.getUserID()) {
                                user.setName(tempUser.getName());
                                user.setEmail(tempUser.getEmail());
                                user.setPassword(tempUser.getPassword());
                                user.setPhoneNumber(tempUser.getPhoneNumber());
                                user.setUsername(tempUser.getUsername());
                            }
                        }
                    }
                    case "editMessage" -> {
                        boolean delete = in.readBoolean();
                        Message message = (Message) in.readObject();
                        String groupName = (String) in.readObject();
                        for (Group group : Server.getGroups()) {
                            if (group.getGroupName().equals(groupName)) {
                                for (Message message1 : group.getMessages()) {
                                    if (message1.equals(message)) {
                                        if (delete) {
                                            group.getMessages().remove(message1);
                                        } else {
                                            String newMessage = (String) in.readObject();
                                            message1.setMessage(LocalDateTime.now(), newMessage);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case "updateCurrentUser" -> {
                        int userID = in.readInt();
                        for (User user : Server.getUsers()) {
                            if (user.getUserID() == userID) {
                                out.reset();
                                out.writeObject(user);
                                out.flush();
                            }
                        }
                    }
                    case "deleteFromGroup" -> {
                        Group group = (Group) in.readObject();
                        User user = (User) in.readObject();
                        System.out.println("Ye");
                        for (Group group1 : Server.getGroups()) {
                            if (group.getGroupName().equals(group1.getGroupName())) {
                                for (User user1 : group1.getUsers()) {
                                    if (user1.getUserID() == user.getUserID()) {
                                        System.out.println(group1);
                                        group1.removeUser(user1);
                                        System.out.println(group1);
                                        break;
                                    }
                                }
                            }
                        }
                        for (User user1 : Server.getUsers()) {
                            if (user1.getUserID() == user.getUserID()) {
                                for (Group group1 : user1.getGroups()) {
                                    if (group.getGroupName().equals(group1.getGroupName())) {
                                        user1.getGroups().remove(group1);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (EOFException | SocketException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Server.writeUsersAndGroups("src/users.txt", "src/groups.txt");
        }
    }
}
