import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ChatDriver
 * A GUI panel that a chat user interacts with.
 *
 * Resources used for help:
 * <ul>
 *     <li><a href="https://stackoverflow.com/questions/685521/multiline-text-in-jlabel">
 *         Multiline text in JLabel (Stack Overflow)
 *         </a></li>
 *     <li><a href="https://www.oracle.com/technical-resources/articles/javase/oconner-customlist-gd-aurev.html">
 *         Customize Your JList Display (Oracle)
 *         </a></li>
 * </ul>
 *
 * @author Camber Boles
 * @version 1 December 2020
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
     * An action listener for the edit and delete functionality.
     * Disables buttons if no selection; enables buttons and allows
     * button press action
     */
    ActionListener editDeleteListener;

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

        JList<Message> chatPanel = new JList<>();

        chatPanel.setModel(changeChatModel(0));

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

                // adds message to the group object
                currentGroup.addMessage(message);

                // Message display on GUI
                // todo: add edit/delete buttons
                ( (DefaultListModel<Message>) chatPanel.getModel()).addElement(message);

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

        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = groupJList.getSelectedIndex();

                if (index != -1) {
                    chatPanel.setModel(changeChatModel(index));
                }
            }
        });

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

        frame.setSize(1000, 600);
        frame.setLocation(60, 30);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Provides a ListModel for loading in messages when a group is selected.
     *
     * @param index The index of the selected group in the user's list of groups
     * @return A ListModel containing the messages of the selected group
     */
    public DefaultListModel<Message> changeChatModel(int index) {
        DefaultListModel<Message> messageListModel = new DefaultListModel<>();
        if (clientUser.getGroups() != null && clientUser.getGroups().size() > 0) {
            currentGroup = clientUser.getGroups().get(index);

            for (Message message : currentGroup.getMessages()) {
                messageListModel.addElement(message);
            }
        }

        return messageListModel;
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

        // when I commented this out it worked, i have no idea what's up
        // TODO: ian
//        try {
//            client.addMessage(message, currentGroup);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
