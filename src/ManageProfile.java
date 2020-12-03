package src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *Allows the user to edit their personal information and delete their account
 *
 * @author Ruth Baldwin
 * @version November 27, 2020
 */
public class ManageProfile {
    JButton editUsername; //edit username option
    JButton editPassword; //edit password option
    JButton editName; //edit name option
    JButton editEmail; //edit email option
    JButton editPhoneNumber; //edit phone number option
    JButton change; //the button that confirms the change to their information
    JButton deleteAccount; //delete account option

    JTextField newUsername; //the new username they choose
    JTextField newPassword; //the new password they choose
    JTextField newName; //the new name they choose
    JTextField newEmail; //the new email they choose
    JTextField newPhoneNumber; //the new phone number they choose

    JLabel cUsername; //the current username
    JLabel cPassword; //the current password
    JLabel cName; //the current name
    JLabel cEmail; //the current email
    JLabel cPhoneNumber; //the current phone number

    JFrame frame = new JFrame("Super Awesome Chat App");
    JPanel panel = new JPanel();
    Container content = frame.getContentPane();

    int selection = 0;

    Client client;
    User user;

    public ManageProfile(Client client) {
        this.client = client;
        user = client.getCurrentUser();
        System.out.println(user);
        System.out.println(user.getName());
    }

    /**
     *Takes the button the user selects and lets them edit the information they want
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == editUsername) {
                System.out.println("test1");
                JLabel usernameLabel = new JLabel("New Username:");
                usernameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                change = new JButton("Change");
                change.addActionListener(actionListener);
                panel.add(change);
                newUsername = new JTextField(10);

                panel.add(usernameLabel);
                panel.add(newUsername);
                panel.add(change);

                frame.setVisible(true);
                selection = 1;
            }
            if (e.getSource() == editPassword) {

                JLabel passwordLabel = new JLabel("New Password:");
                passwordLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                change = new JButton("Change");
                change.addActionListener(actionListener);
                panel.add(change);
                newPassword = new JTextField(10);

                panel.add(passwordLabel);
                panel.add(newPassword);
                panel.add(change);

                frame.setVisible(true);
                selection = 2;
            }
            if (e.getSource() == editName) {

                JLabel nameLabel = new JLabel("New Name:");
                nameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                change = new JButton("Change");
                change.addActionListener(actionListener);
                panel.add(change);
                newName = new JTextField(10);

                panel.add(nameLabel);
                panel.add(newName);
                panel.add(change);

                frame.setVisible(true);
                selection = 3;
            }
            if (e.getSource() == editEmail) {
                JLabel emailLabel = new JLabel("New Email:");
                emailLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                change = new JButton("Change");
                change.addActionListener(actionListener);
                panel.add(change);
                newEmail = new JTextField(10);

                panel.add(emailLabel);
                panel.add(newEmail);
                panel.add(change);

                frame.setVisible(true);
                selection = 4;
            }
            if (e.getSource() == editPhoneNumber) {
                JLabel phoneNumberLabel = new JLabel("New Phone Number:");
                phoneNumberLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                change = new JButton("Change");
                change.addActionListener(actionListener);
                panel.add(change);
                newPhoneNumber = new JTextField(10);

                panel.add(phoneNumberLabel);
                panel.add(newPhoneNumber);
                panel.add(change);

                frame.setVisible(true);
                selection = 5;
            }
            if (e.getSource() == deleteAccount) {
                try {
                    throw new Exception("Uncomment");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                //Server.getUsers().remove(user);
                frame.dispose();
            }
            if (e.getSource() == change) {
                switch (selection) {
                    case 1:
                        if (newUsername.getText() != null) {
                            user.setUsername(newUsername.getText());
                            panel.remove(cUsername);
                            cUsername = new JLabel(user.getUsername());
                            panel.add(cUsername);
                            client.updateUser(user);
                        }
                        break;
                    case 2:
                        if (newPassword.getText() != null) {
                            user.setPassword(newPassword.getText());
                            panel.remove(cPassword);
                            cPassword = new JLabel(user.getPassword());
                            panel.add(cPassword);
                            client.updateUser(user);
                        }
                        break;
                    case 3:
                        if (newName.getText() != null) {
                            user.setName(newName.getText());
                            panel.remove(cName);
                            cName = new JLabel(user.getName());
                            panel.add(cName);
                            client.updateUser(user);
                        }
                        break;
                    case 4:
                        if (newEmail.getText() != null) {
                            user.setEmail(newEmail.getText());
                            panel.remove(cEmail);
                            cEmail = new JLabel(user.getEmail());
                            panel.add(cEmail);
                            client.updateUser(user);
                        }
                        break;
                    case 5:
                        if (newPhoneNumber.getText() != null) {
                            long number = Long.parseLong(newPhoneNumber.getText());
                            user.setPhoneNumber(number);
                            panel.remove(cPhoneNumber);
                            cPhoneNumber = new JLabel(String.valueOf(user.getPhoneNumber()));
                            panel.add(cPhoneNumber);
                            client.updateUser(user);
                        }
                        break;
                }
                frame.setVisible(true);
            }
        }
    };

    /**
     *
     * Shows the user options for changing their information or deleting their account, as well as
     * their current information
     */
    public void run() {
        content.setLayout(new GridLayout());
        frame.setSize(200, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cUsername = new JLabel("Username:" + user.getUsername());
        cUsername.setFont( new Font("Comic Sans", Font.PLAIN, 14));
        cPassword = new JLabel("Password:" + user.getPassword());
        cPassword.setFont( new Font("Comic Sans", Font.PLAIN, 14));
        cName = new JLabel("Name:" + user.getName());
        cName.setFont( new Font("Comic Sans", Font.PLAIN, 14));
        cEmail = new JLabel("Email:" + user.getEmail());
        cEmail.setFont( new Font("Comic Sans", Font.PLAIN, 14));
        cPhoneNumber = new JLabel("Phone Number:" + user.getPhoneNumber());
        cPhoneNumber.setFont( new Font("Comic Sans", Font.PLAIN, 14));

        editUsername = new JButton("Edit Username");
        editUsername.setBorder(new LineBorder(Color.BLACK));
        editUsername.setBackground(Color.decode("#C4E9E7"));
        editUsername.addActionListener(actionListener);

        editPassword = new JButton("Edit Password");
        editPassword.setBorder(new LineBorder(Color.BLACK));
        editPassword.setBackground(Color.decode("#C4E9E7"));
        editPassword.addActionListener(actionListener);

        editName = new JButton("Edit Name");
        editName.setBorder(new LineBorder(Color.BLACK));
        editName.setBackground(Color.decode("#C4E9E7"));
        editName.addActionListener(actionListener);

        editEmail = new JButton("Edit Email");
        editEmail.setBorder(new LineBorder(Color.BLACK));
        editEmail.setBackground(Color.decode("#C4E9E7"));
        editEmail.addActionListener(actionListener);

        editPhoneNumber = new JButton("Edit Phone Number");
        editPhoneNumber.setBorder(new LineBorder(Color.BLACK));
        editPhoneNumber.setBackground(Color.decode("#C4E9E7"));
        editPhoneNumber.addActionListener(actionListener);

        deleteAccount = new JButton("Delete Account");
        deleteAccount.setBorder(new LineBorder(Color.BLACK));
        deleteAccount.setBackground(Color.decode("#C4E9E7"));
        deleteAccount.addActionListener(actionListener);

        panel.add(cUsername);
        panel.add(editUsername);

        panel.add(cPassword);
        panel.add(editPassword);

        panel.add(cName);
        panel.add(editName);

        panel.add(cEmail);
        panel.add(editEmail);

        panel.add(cPhoneNumber);
        panel.add(editPhoneNumber);

        panel.add(deleteAccount);

        panel.setBackground(Color.decode("#98DE7B"));
        content.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    //public static void main(String[] args) {
      //  ManageProfile mP = new ManageProfile();
        //mP.run();
    //}
}
