package src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 *Gui for the user to log in
 *
 * @author Ruth Baldwin
 * @version November 27, 2020
 */

public class Login {
    JButton login;
    JButton createUser;
    JButton login1;
    JButton create;

    JFrame frame = new JFrame("Super Awesome Chat App");
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    Container content = frame.getContentPane();

    JTextField username1 = new JTextField(15);
    JTextField password1 = new JTextField(15);
    JTextField email1 = new JTextField(15);
    JTextField name1 = new JTextField(15);
    JTextField phoneNumber1 = new JTextField(15);

    User user;
    Client client = new Client();
    Server server = new Server();

    /**
     * The action listener for the buttons on the login/user creation screens
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                frame.dispose();
                content.removeAll();

                frame.setSize(225, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel usernameLabel = new JLabel("Username:");
                usernameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                usernameLabel.setBounds(50, 100, 50, 100);

                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                passwordLabel.setBounds(50, 200, 50, 100);

                panel1.add(usernameLabel);
                panel1.add(username1);
                panel1.add(passwordLabel);
                panel1.add(password1);
                login1 = new JButton("Login");
                login1.addActionListener(actionListener);
                login1.setBorder(new LineBorder(Color.BLACK));
                login1.setBackground(Color.decode("#C4E9E7"));;
                panel1.add(login1);
                panel1.setBackground(Color.decode("#98DE7B"));
                content.add(panel1, BorderLayout.CENTER);

                frame.setVisible(true);

            }

            //This creates a new window for user creation
            if (e.getSource() == createUser) {
                frame.dispose();
                content.removeAll();

                frame.setSize(225, 320);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                nameLabel.setBounds(200, 100, frame.getWidth(), frame.getHeight());

                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                emailLabel.setBounds(200, 200, frame.getWidth(), frame.getHeight());

                JLabel phoneNumberLabel = new JLabel("Phone Number:");
                phoneNumberLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                phoneNumberLabel.setBounds(200, 300, frame.getWidth(), frame.getHeight());

                JLabel usernameLabel = new JLabel("Username:");
                usernameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                usernameLabel.setBounds(200, 400, frame.getWidth(), frame.getHeight());

                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                passwordLabel.setBounds(200, 500, frame.getWidth(), frame.getHeight());

                panel2.add(nameLabel);
                panel2.add(name1);

                panel2.add(emailLabel);
                panel2.add(email1);

                panel2.add(phoneNumberLabel);
                panel2.add(phoneNumber1);

                panel2.add(usernameLabel);
                panel2.add(username1);

                panel2.add(passwordLabel);
                panel2.add(password1);

                create = new JButton("Create User");
                create.addActionListener(actionListener);
                create.setBorder(new LineBorder(Color.BLACK));
                create.setBackground(Color.decode("#C4E9E7"));;
                panel2.add(create);
                panel2.setBackground(Color.decode("#98DE7B"));
                content.add(panel2, BorderLayout.CENTER);

                frame.setVisible(true);
            }

            if (e.getSource() == login1) {
                user.loginUser(username1.getText(), password1.getText());
                frame.dispose();
            }
            if (e.getSource() == create) {
                long usersPhoneNumber = Long.parseLong(phoneNumber1.getText());
                user.userCreation(name1.getText(), username1.getText(), email1.getText(), usersPhoneNumber, password1.getText());
                frame.dispose();
            }
        }
    };

    /**
     *
     * This does actually logs in the user and will access their account
     *
     * @param usernameToFind the username to be found on the file
     * @param passwordToCheck the password to be checked for the user
     */
    public void loginUser(String usernameToFind, String passwordToCheck) throws IOException {
        boolean success = client.login(usernameToFind, passwordToCheck);
        if (success == true) {
            for (int i = 0; i < server.getUsers().size(); i++) {
                if (server.getUsers().get(i).getUsername().equals(usernameToFind)
                        && server.getUsers().get(i).getUsername().equals(passwordToCheck)) {
                    user = server.getUsers().get(i);
                }
            }
        }
        //login user
    }

    /**
     *
     * @param usersName the name for the account
     * @param usersUsername the username for the account
     * @param usersEmail the email for the account
     * @param usersPhoneNumber the phone number for the account
     * @param usersPassword the password for the account
     */
    public void userCreation(String usersName, String usersUsername, String usersEmail, long usersPhoneNumber, String usersPassword) throws IOException {
        client.createAccount(usersUsername, usersPassword, usersName, usersEmail, usersPhoneNumber);
        User newUser = new User(usersName, usersUsername, usersEmail, usersPhoneNumber, usersPassword);
        //write users login info to the file
    }

    /**
     * Creation of the welcome screen
     */
    public void run () {
        user = new User();
        content.setLayout(new BorderLayout());
        login = new JButton("Login");
        login.setBorder(new LineBorder(Color.BLACK));
        login.setBackground(Color.decode("#C4E9E7"));
        login.addActionListener(actionListener);
        createUser = new JButton("Create an Account");
        createUser.setBorder(new LineBorder(Color.BLACK));
        createUser.setBackground(Color.decode("#C4E9E7"));
        createUser.addActionListener(actionListener);

        frame.setSize(200, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JLabel welcome = new JLabel("Welcome!");
        welcome.setFont( new Font("Comic Sans", Font.BOLD, 16));
        welcome.setBounds(200, 50, frame.getWidth(), frame.getHeight());
        JLabel option = new JLabel("Please select an option:");
        option.setFont( new Font("Comic Sans", Font.PLAIN, 14));
        welcome.setBounds(200, 100, frame.getWidth(), frame.getHeight());
        login.setBounds(200, 150, frame.getWidth(), frame.getHeight());
        createUser.setBounds(200, 200, frame.getWidth(), frame.getHeight());

        panel.add(welcome);
        panel.add(option);
        panel.add(login);
        panel.add(createUser);
        panel.setBackground(Color.decode("#98DE7B"));
        content.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    public User getUser() {
        return this.user;
    }
}
