//package src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 * Gui for the user to log in
 *
 * @author Ruth Baldwin
 * @version November 27, 2020
 */

public class Login {
    JButton login; //the option to login
    JButton createUser; //the option to create a user
    JButton login1; //confirm login
    JButton create; //confirm create

    JFrame frame = new JFrame("Super Awesome Chat App");
    JPanel panel1 = new JPanel(); //login panel
    JPanel panel2 = new JPanel(); //user creation panel
    Container content = frame.getContentPane();

    JTextField username = new JTextField(15); //username the user inputs
    JTextField password = new JTextField(15); //password the user inputs
    JTextField email = new JTextField(15); //email the user inputs
    JTextField name = new JTextField(15); //name the user inputs
    JTextField phoneNumber = new JTextField(15); //phone number the user inputs

    Login newLogin;
    Client client = new Client(); //login or create user
    User user = new User(); //new user to be created

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
                panel1.add(username);
                panel1.add(passwordLabel);
                panel1.add(password);
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
                panel2.add(name);

                panel2.add(emailLabel);
                panel2.add(email);

                panel2.add(phoneNumberLabel);
                panel2.add(phoneNumber);

                panel2.add(usernameLabel);
                panel2.add(username);

                panel2.add(passwordLabel);
                panel2.add(password);

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
                try {
                    newLogin.loginUser(username.getText(), password.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                frame.dispose();
            }
            if (e.getSource() == create) {
                long usersPhoneNumber = Long.parseLong(phoneNumber.getText());
                try {
                    newLogin.userCreation(name.getText(), username.getText(), email.getText(), usersPhoneNumber, password.getText());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
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
            user = client.getCurrentUser();
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
        user = client.createAccount(usersUsername, usersPassword, usersName, usersEmail, usersPhoneNumber);
    }

    /**
     *
     * @return the user currently logged in
     */
    public User getUser() {
        return this.user;
    }
}
