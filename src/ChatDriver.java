import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
 * @version 5 December 2020
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
     * A button the user can press to delete themselves from a group
     */
    JButton deleteGroupButton;

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
     * The JList that displays the chat messages.
     */
    JList<Message> chatPanel = new JList<>();

    /**
     * The JList that displays the groups.
     */
    JList<String> groupJList = new JList<>();

    /**
     * The jlist for users
     */
    JList<String> userJList = new JList<>();

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
            client.createGroup("Create your first Group!", new String[]{client.getCurrentUser().getUsername()});
            client.updateCurrentUser();
            currentGroup = client.getCurrentUser().getGroups().get(0);
        } else {
            currentGroup = clientUser.getGroups().get(0);
        }

        userSettingsButton = new JButton("Settings");
        deleteGroupButton = new JButton("Delete Group");
        createGroupButton = new JButton("New Group");
        editMessageButton = new JButton("Edit");
        deleteMessageButton = new JButton("Delete");
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(() -> {
            try {
                refreshMessages();
                int temp = groupJList.getSelectedIndex();
                groupJList.setModel(changeGroupModel());
                groupJList.setSelectedIndex(temp);
                userJList.setModel(changeUserModel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
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

        groupJList.setModel(changeGroupModel());
        groupJList.setSelectedIndex(0);

        chatPanel.setModel(changeChatModel(0));
        chatPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final int[] editDeleteMessageIndex = new int[1];

        chatPanel.addListSelectionListener(new ListSelectionListener() {
            /**
             * ListSelectionListener for editing/deleting messages.
             * @param e the selection of messages in the list.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = e.getFirstIndex();
                Message messageToEditDelete = currentGroup.getMessages().get(index);

                if (index != -1 && messageToEditDelete.getAuthor().getUsername()
                        .equals(client.getCurrentUser().getUsername())) {
                    editDeleteMessageIndex[0] = index;
                    String text = currentGroup.getMessages().get(index).getText();
                    messageTextField.setText(text);
                    sendMessageButton.setEnabled(false);
                    editMessageButton.setEnabled(true);
                    deleteMessageButton.setEnabled(true);
                } else {
                    editMessageButton.setEnabled(false);
                    deleteMessageButton.setEnabled(false);
                    sendMessageButton.setEnabled(true);
                    messageTextField.setText("");
                }
            }
        });

        MessageRenderer renderer = new MessageRenderer();
        chatPanel.setCellRenderer(renderer);

        client.updateCurrentUser();
        int index = 0;
        for (int i = 0; i < clientUser.getGroups().size(); i++) {
            if (clientUser.getGroups().get(i).getGroupName().equals(currentGroup.getGroupName())) {
                index = i;
            }
        }
        chatPanel.setModel(changeChatModel(index));

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
                ( (DefaultListModel<Message>) chatPanel.getModel()).addElement(message);

                messageTextField.setText("");
                messageTextField.requestFocusInWindow();
            }
        });


        editDeleteListener = new ActionListener() {
            /**
             * ActionListener for the editing and deleting buttons.
             * @param e press of one of the two buttons
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = editDeleteMessageIndex[0];
                Message messageToEditDelete = currentGroup.getMessages().get(index);

                if (e.getSource() == editMessageButton) {
                    String text = messageTextField.getText();
                    client.editMessage(messageToEditDelete, text, currentGroup, false);

                    chatPanel.setSelectedIndex(-1);
                    editMessageButton.setEnabled(false);
                    deleteMessageButton.setEnabled(false);
                    sendMessageButton.setEnabled(true);
                    messageTextField.setText("");
                } else if (e.getSource() == deleteMessageButton) {
                    client.editMessage(messageToEditDelete, "", currentGroup, true);

                    index = 0;
                    for (int i = 0; i < clientUser.getGroups().size(); i++) {
                        if (clientUser.getGroups().get(i).getGroupName().equals(currentGroup.getGroupName())) {
                            index = i;
                        }
                    }
                    chatPanel.setModel(changeChatModel(index));

                    chatPanel.setSelectedIndex(-1);
                    editMessageButton.setEnabled(false);
                    deleteMessageButton.setEnabled(false);
                    sendMessageButton.setEnabled(true);
                    messageTextField.setText("");
                }
            }
        };

        JScrollPane centerPanel = new JScrollPane(chatPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        centerPanel.setAutoscrolls(true);

        content.add(centerPanel, BorderLayout.CENTER);


        JPanel westPanel = new JPanel(new BorderLayout());

        editMessageButton.addActionListener(editDeleteListener);
        deleteMessageButton.addActionListener(editDeleteListener);

        JScrollPane groupsPane = new JScrollPane(groupJList);
        westPanel.add(groupsPane, BorderLayout.CENTER);
        westPanel.add(createGroupButton, BorderLayout.SOUTH);
        content.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();

        userJList = new JList<>(changeUserModel());

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

        deleteGroupButton.addActionListener(new ActionListener() {
            /**
             * Deletes that user from the group
             * @param e the press of the delete group button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                client.updateCurrentUser();
                currentGroup = clientUser.getGroups().get(groupJList.getSelectedIndex());
                client.deleteFromGroup(currentGroup);
                groupJList.setSelectedIndex(0);
            }
        });

        northPanel.add(userSettingsButton);
        northPanel.add(deleteGroupButton);
        content.add(northPanel, BorderLayout.NORTH);

        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.addListSelectionListener(new ListSelectionListener() {
            /**
             * When a new group is selected, this updates the chat pane with
             * the group's messages and the user pane with the group's users.
             *
             * @param e the selection of a new group
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = groupJList.getSelectedIndex();

                if (index != -1) {
                    currentGroup = clientUser.getGroups().get(index);
                    chatPanel.setModel(changeChatModel(index));
                    ((JLabel) northPanel.getComponent(0)).setText(currentGroup.getGroupName());
                }
            }
        });

        createGroupButton.addActionListener(new ActionListener() {
            /**
             * Prompts the user to create a new group.
             *
             * @param e the click of the New Group button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> users = new ArrayList<>();
                Group group;
                int input;

                users.add(clientUser.getUsername());

                String name = JOptionPane.showInputDialog("Name of the group?");

                JComboBox<String> userJComboBox = new JComboBox<>();
                DefaultComboBoxModel<String> userListModel = new DefaultComboBoxModel<>();
                String[] usernames = client.getAllUsers();
                for (String username : usernames) {
                    userListModel.addElement(username);
                }
                userJComboBox.setModel(userListModel);

                JLabel comboBoxLabel = new JLabel("Select a user and OK to add to group.");
                comboBoxLabel.setLabelFor(userJComboBox);

                JPanel panel = new JPanel();
                panel.add(comboBoxLabel);
                panel.add(userJComboBox);

                do {
                    input = JOptionPane.showOptionDialog(null,
                            panel,
                            "New Group",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Add", "Create Group"},
                            "Add");

                    if (input == 0) {
                        if (!users.contains(userJComboBox.getSelectedItem())) {
                            users.add((String) userJComboBox.getSelectedItem());
                        }
                    }
                } while (input != 1);

                client.createGroup(name, users.toArray(new String[0]));

                groupJList.setModel(changeGroupModel());
            }
        });

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
            client.updateCurrentUser();

            currentGroup = clientUser.getGroups().get(index);

            messageListModel.addAll(currentGroup.getMessages());
        }

        return messageListModel;
    }

    /**
     * Loads Users from the current group into the east panel.
     *
     * @return a list model containing the users in the group
     */
    public DefaultListModel<String> changeUserModel() {
        DefaultListModel<String> userListModel = new DefaultListModel<>();

        client.updateCurrentUser();

        if (groupJList.getSelectedIndex() == -1) {
            if (!(clientUser.getGroups().size() == 0)) {
                currentGroup = clientUser.getGroups().get(0);
            }
        } else {
            currentGroup = clientUser.getGroups().get(groupJList.getSelectedIndex());
        }

        for (User user : currentGroup.getUsers()) {
            userListModel.addElement(user.getName());
        }

        return userListModel;
    }

    /**
     * Loads Groups the user is in into the west panel.
     *
     * @return a list model containing the groups
     */
    public DefaultListModel<String> changeGroupModel() {
        DefaultListModel<String> groupListModel = new DefaultListModel<>();

        for (Group group : clientUser.getGroups()) {
            groupListModel.addElement(group.getGroupName());
        }

        return groupListModel;
    }

    /**
     * Called when sendMessage is pressed.
     * Creates a new Message object to be written to the server.
     *
     * @param text the text of the message
     * @return a new Message object
     */
    public Message sendMessageToServer(String text) {
        LocalDateTime dateTime = LocalDateTime.now();


        Message message = new Message(clientUser, dateTime, text);

        client.addMessage(message, currentGroup);


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
        client.createAccount("fren", "12345", "cam",
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
            setText(String.format("<html><span style='color: grey;'>%s</span><br>%s</html>",
                    username, text));

            if (cellHasFocus) {
                setOpaque(true);
                setBackground(new Color(135, 183, 213));
            } else {
                setBackground(Color.WHITE);
            }

            // Adds margins
            setBorder(new EmptyBorder(10, 10, 3, 10));

            if (isSelected) {
                setOpaque(true);
                setBackground(new Color(135, 183, 213));
            }

            return this;
        }
    }

    /**
     * Refreshes the messages on the chat panel
     */
    public void refreshMessages() {
        client.updateCurrentUser();

        if (groupJList.getSelectedIndex() == -1) {
            chatPanel.setModel(changeChatModel(0));
        } else {
            chatPanel.setModel(changeChatModel(groupJList.getSelectedIndex()));
        }
    }
}
