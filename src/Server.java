import java.io.*;
import java.net.*;

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
    private ArrayList<Group> groups;        //A collection of all of the groups for the messaging app
    private ArrayList<User> users;          //A collection of all of the users for the messaging app
    private static int port = 4242;         //The port of the server
    ServerSocket serverSocket;              //The socket used to connect the server and client

    public static void main(String[] args) throws IOException{
        Server server = new Server();
        while (true) {
            server.acceptor();
        }
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
}

