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
 * @version 30 November 2020
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
     * todo: delete (safely)
     */
    Stack<JLabel> messageLabelStack;

    /**
     * A button the user can press to change account settings.
     */
    JButton userSettingsButton;

    /**
     * A button the user can press to create a new group.
     */
    JButton createGroupButton;

    /**
     * A button for editing a message. Enabled when a message is selected.
     */
    JButton editMessageButton;

    /**
     * A button to delete a message. Enabled when a message is selected.
     */
    JButton deleteMessageButton;

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

        if (clientUser.getGroups() == null || clientUser.getGroups().size() == 0) {
            currentGroup = new Group("Create your first Group!", new ArrayList<>());
        } else {
            currentGroup = clientUser.getGroups().get(0);
        }

        userSettingsButton = new JButton("Settings");
        createGroupButton = new JButton("New Group");
        editMessageButton = new JButton("Edit");
        deleteMessageButton = new JButton("Delete");
    }

    /**
     * Sets up the layout of the main GUI.
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
        southPanel.add(editMessageButton);
        southPanel.add(deleteMessageButton);
        content.add(southPanel, BorderLayout.SOUTH);

        DefaultListModel<Message> messageListModel = new DefaultListModel<>();
        JList<Message> chatPanel = new JList<>(messageListModel);

        // Loading in existing messages todo: add this to a listener for the group JList
        if (currentGroup.getMessages() != null && currentGroup.getMessages().size() > 0) {
            for (Message message : currentGroup.getMessages()) {
                messageListModel.addElement(message);
            }
        }

        MessageRenderer renderer = new MessageRenderer();
        chatPanel.setCellRenderer(renderer);

        sendMessageButton.addActionListener(new ActionListener() {
            /**
             * Displays the message sent in the chat pane.
             * @param e the press of the send button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Message creation to send to server
                Message message = sendMessageToServer(messageTextField.getText());

                // Message display on GUI
                // todo: change to JList display, add edit/delete buttons
                messageListModel.addElement(message);

//                messageLabelStack.push(new JLabel(messageTextField.getText()));
//                JLabel userLabel = new JLabel(clientUser.getUsername());
//                userLabel.setLabelFor(messageLabelStack.peek());
//                userLabel.setForeground(new Color(125, 125, 125));
//                chatPanel.add(userLabel);
//                chatPanel.add(messageLabelStack.peek());

                // chatPanel.revalidate();
                messageTextField.setText("");
                messageTextField.requestFocusInWindow();
            }
        });

        JScrollPane centerPanel = new JScrollPane(chatPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // todo: make it actually autoscroll
        centerPanel.setAutoscrolls(true);

        content.add(centerPanel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel(new BorderLayout());
        DefaultListModel<Group> groupListModel = new DefaultListModel<>();
        JList<Group> groupJList = new JList<>(groupListModel);
        JScrollPane groupsPane = new JScrollPane(groupJList);
        westPanel.add(groupsPane, BorderLayout.CENTER);
        westPanel.add(createGroupButton, BorderLayout.SOUTH);
        content.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        DefaultListModel<User> userListModel = new DefaultListModel<>();
        JList<User> userJList = new JList<>(userListModel);
        JScrollPane usersPane = new JScrollPane(userJList);
        eastPanel.add(usersPane);
        content.add(eastPanel, BorderLayout.EAST);

        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel(currentGroup.getGroupName()));
        userSettingsButton.addActionListener(new ActionListener() {
            /**
             * Opens a new ManageProfile window when the settings button is clicked.
             * @param e the press of the user settings button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageProfile mp = new ManageProfile(client);
                mp.run();
            }
        });
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

        frame.setSize(800, 600);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Called when sendMessage is pressed.
     * Creates a new Message object to be written to the server.
     *
     * @param text the text of the message
     * @return a new Message object
     */
    public Message sendMessageToServer(String text){
        LocalDateTime dateTime = LocalDateTime.now();

        Message message = new Message(clientUser, dateTime, text);

        try {
            client.addMessage(message, currentGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * Runs the driver on the EventDispatcher thread for stability.
     * Will eventually be called by the client-side program, as opposed to
     * this program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4242);
        client.createAccount("boles2", "12345", "cam",
                "boles2@purdue.edu", Long.parseLong("1234567890"));
        SwingUtilities.invokeLater(new ChatDriver(client));
    }

    /**
     * Cell renderer class to display Messages properly in the chat pane.
     */
    static class MessageRenderer extends JLabel implements ListCellRenderer<Message> {

        /**
         * Renders messages in the message pane in a friendly way, with both username and text.
         *
         * @param list The JList structure
         * @param value The message to render
         * @param index The index of the specific message
         * @param isSelected Whether this message is selected
         * @param cellHasFocus Whether this cell has the focus
         * @return A JLabel style to apply to all elements in list
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends Message> list, Message value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            String username = value.getAuthor().getUsername();
            String text = value.getText();

            setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            setText(String.format("<html><span style='color: grey'>%s</span><br>%s</html>",
                    username, text));

            return this;
        }
    }
}
