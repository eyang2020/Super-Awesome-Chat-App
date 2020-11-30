import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

/**
 * ChatDriver
 * A GUI panel that a chat user interacts with.
 *
 * @author Camber Boles
 * @version 27 November 2020
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
     * A specific JLabel, corresponding with the current user,
     */
    JLabel usernameLabel;

    /**
     * A button the user can press to change account settings.
     */
    JButton userSettingsButton;

    /**
     * The current user of the chat.
     */
    private User clientUser;

    /**
     * The current client of the user.
     */
    private Client client;

    /**
     * The current group displayed on the chat pane.
     */
    private Group currentGroup;

    /**
     * Constructs a ChatDriver object based on the current user on the client-side program.
     *
     * @param client the client of the current user
     */
    public ChatDriver(Client client) {
        messageLabelStack = new Stack<>();
        this.client = client;
        clientUser = client.getCurrentUser();
        usernameLabel = new JLabel(clientUser.getUsername());
        // currentGroup = clientUser.getGroups().get(0);
        currentGroup = new Group("Test Group", new ArrayList<>());
        userSettingsButton = new JButton("Settings");
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
                chatPanel.add(usernameLabel);
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

        JPanel eastPanel = new JPanel();
        JList<User> userJList = new JList<>(currentGroup.getUsers().toArray(new User[0]));
        eastPanel.add(userJList);
        content.add(eastPanel, BorderLayout.EAST);

        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel(currentGroup.getGroupName()));
        northPanel.add(userSettingsButton);
        content.add(northPanel, BorderLayout.NORTH);

        frame.addWindowFocusListener(new WindowAdapter() {
            /**
             * Changes focus to the message text field within this frame.
             * From "How to Use the Focus Subsystem" on the Java Tutorials page.
             *
             * @param e the chat frame gaining focus
             */
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
    public void sendMessageToServer(String text){
        LocalDateTime dateTime = LocalDateTime.now();

        try {
            client.addMessage(new Message(clientUser, dateTime, text), currentGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        SwingUtilities.invokeLater(new ChatDriver(new Client("localhost", 4242)));
    }
}
