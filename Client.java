import java.io.*;
import java.net.*;

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
    BufferedReader reader;  //The reader that connects to the server
    PrintWriter writer;     //The writer that connects to the Server

    public void connectToServer() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
