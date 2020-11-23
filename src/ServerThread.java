import java.io.*;
import java.net.*;

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
public class ServerThread implements Runnable {
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
    public void run() {
        String line;
        BufferedReader reader = null;
        PrintWriter writer = null;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new  PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){

        }
    }
}
