import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

/**
 * ChatDriver
 * A GUI panel that a chat user interacts with.
 *
 * @author Camber Boles
 * @version 23 November 2020
 */
public class ChatDriver extends JComponent implements Runnable {

    /**
     * A text field where the user can send a chat message.
     */
    JTextField message;

    /**
     * A button that sends the user's message.
     */
    JButton sendMessage;


    /**
     * An ArrayList of labels for displaying the messages in this group.
     */
    Stack<JLabel> messageLabels;

    /**
     * A test user for testing the message log.
     * TODO: replace with a user-created profile
     */
    public final User user;

    /**
     * Constructor for ChatDriver. Initializes ArrayList of messageLabels.
     * Also initializes test user.
     * TODO: cease reliance on test user
     */
    public ChatDriver() {
        messageLabels = new Stack<>();
        user = new User("Camber Boles", "boles2", "boles2@purdue.edu,",
                1234567890, "testPassword" );
    }

    /**
     * Sets up the layout of the main GUI.
     * TODO: move handling of sending message to server
     */
    @Override
    public void run() {
        JFrame frame = new JFrame("Chat");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        message = new JTextField("Send message...", 20);
        sendMessage = new JButton("Send");

        southPanel.add(message);
        southPanel.add(sendMessage);
        content.add(southPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 1));

        // TODO: make the messages actually show up
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabels.push(new JLabel(message.getText()));
                centerPanel.add(messageLabels.peek());
            }
        });

        content.add(centerPanel);

        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Runs the driver on the EventDispatcher thread for stability.
     * Will eventually be called by the client-side program, as opposed to
     * this program.
     * TODO: call method from client side as opposed to command line
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ChatDriver());
    }
}
