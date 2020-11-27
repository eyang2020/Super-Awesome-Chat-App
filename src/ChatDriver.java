
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * ChatDriver
 * A GUI panel that a chat user interacts with.
 *
 * @author Camber Boles
 * @version 25 November 2020
 */
public class ChatDriver extends JComponent implements Runnable {

    /**
     * A text field where the user can send a chat message.
     */
    JTextField messageTextField;

    /**
     * A button that sends the user's message.
     */
    JButton sendMessageButton;


    /**
     * An ArrayList of labels for displaying the messages in this group.
     */
    Stack<JLabel> messageLabelStack;

    /**
     * A test user for testing the message log.
     * TODO: replace with a user-created profile
     */
    public final User USER = new User("Camber Boles", "boles2", "boles2@purdue.edu,",
            1234567890, "testPassword" );

    /**
     * The current user of the chat.
     */
    private User clientUser;

    /**
     * The current group displayed on the chat pane.
     * TODO: multiple groups????? whomst
     */
    private Group currentGroup;

    /**
     * Default constructor for ChatDriver. Initializes ArrayList of messageLabels.
     * Also initializes test user. For testing purposes only!
     * TODO: cease reliance on test user
     */
    public ChatDriver() {
        messageLabelStack = new Stack<>();
        clientUser = USER;
    }

    /**
     * Constructs a ChatDriver object based on the current user on the client-side program.
     *
     * @param user the current user
     */
    public ChatDriver(User user) {
        messageLabelStack = new Stack<>();
        clientUser = user;
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
        messageTextField = new JTextField(20);
        sendMessageButton = new JButton("Send");

        southPanel.add(messageTextField);
        southPanel.add(sendMessageButton);
        content.add(southPanel, BorderLayout.SOUTH);

        JPanel chatPanel = new JPanel(new GridLayout(0, 1, 5, 10));

        sendMessageButton.addActionListener(new ActionListener() {
            /**
             * Displays the message sent in the chat pane.
             * @param e the press of the send button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Message creation to send to server
                sendMessageToServer(messageTextField.getText());

                // Message display on GUI
                messageLabelStack.push(new JLabel(messageTextField.getText()));
                chatPanel.add(new JLabel(clientUser.getUsername()));
                chatPanel.add(messageLabelStack.peek());
                chatPanel.revalidate();
                messageTextField.setText("");
            }
        });

        JScrollPane centerPanel = new JScrollPane(chatPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // todo: make it actually autoscroll
        centerPanel.setAutoscrolls(true);

        content.add(centerPanel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel();
        // todo: make this a thing
        JList<Group> groupJList = new JList<>();
        westPanel.add(groupJList);
        content.add(westPanel, BorderLayout.WEST);

        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                messageTextField.requestFocusInWindow();
            }
        });

        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Called when sendMessage is pressed.
     * Creates a new Message object to be written to the server.
     *
     * @param text the text of the message
     * @return a Message object
     */
    public Message sendMessageToServer(String text) {
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        String time = DateFormat.getTimeInstance(DateFormat.FULL).format(new Date());

        return new Message(clientUser, date, time, text);
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
