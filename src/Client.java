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
    ObjectOutputStream out;
    ObjectInputStream in;

    public static void main(String[] args) throws IOException {
        Client client1 = new Client();
        client1.host = "localhost";
        client1.port = 4242;
        System.out.println("que");
        client1.connectToServer();
        System.out.println("hello");
        client1.createGroup("Hello", new String[]{"Hi", "Bye"});
    }

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
            System.out.println("hello1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(String username, String password, String name, String email, long phoneNumber) throws IOException {
        boolean created;
        out.writeObject("createAccount");
        out.writeObject(username);
        out.writeObject(password);
        out.writeObject(name);
        out.writeObject(email);
        out.writeLong(phoneNumber);
        out.flush();
        created = in.readBoolean();
        return created;
    }

    public boolean login(String username, String password) throws IOException {
        boolean logedin;
        out.writeObject("login");
        out.writeObject(username);
        out.writeObject(password);
        out.flush();
        logedin = in.readBoolean();
        return logedin;
    }

    public boolean createGroup(String groupName, String[] usernames) throws IOException{
        boolean created;
        out.writeObject("createGroup");
        out.writeObject(groupName);
        out.writeObject(usernames);
        out.flush();
        created = in.readBoolean();
        return created;
    }

    public boolean addMessage(LocalDateTime dateTime, String username, String message, String groupname) throws IOException{
        boolean added;
        out.writeObject("addMessage");
        out.writeObject(dateTime);
        out.writeObject(username);
        out.writeObject(message);
        out.writeObject(groupname);
        out.flush();
        added = in.readBoolean();
        return added;
    }
}
