import javax.swing.*;
import java.io.IOException;

/**
 * ClientDriver
 * 
 * To be run on the client and used to execute the methods needed for the client
 * 
 * No help
 *
 * @author Ian Blacklock B11
 * @version 11/30/20
 */
public class ClientDriver {

    /**
     * Runs the client side of the chat app
     * @param args Not important
     */
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4242);
        client.createAccount("RedJyve", "12345", "Ian", "iblacklo@purdue.edu", Long.parseLong("9258859123"));
        //Run login
        SwingUtilities.invokeLater(new ChatDriver(client));
    }
}
