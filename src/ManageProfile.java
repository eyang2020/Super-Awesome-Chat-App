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
    JButton editUsername;
    JButton editPassword;
    JButton editName;
    JButton editEmail;
    JButton editPhoneNumber;
    JButton change;

    JTextField newUsername;
    JTextField newPassword;
    JTextField newName;
    JTextField newEmail;
    JTextField newPhoneNumber;

    JFrame frame = new JFrame("Super Awesome Chat App");
    JPanel panel = new JPanel();
    Container content = frame.getContentPane();

    int selection = 0;

    Login loggedIn = new Login();

    /**
     *Takes the button the user selects and lets them edit the information they want
     */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == editUsername) {
                content.setLayout(new BorderLayout());
                frame.setSize(225, 360);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel usernameLabel = new JLabel("New Username:");
                usernameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                usernameLabel.setBounds(50, 100, 50, 100);
                change = new JButton("Change");
                panel.add(change);
                newUsername = new JTextField();

                panel.add(usernameLabel);
                panel.add(newUsername);
                panel.add(change);

                frame.setVisible(true);
                selection = 1;
            }
            if (e.getSource() == editPassword) {
                content.setLayout(new BorderLayout());
                frame.setSize(225, 360);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel passwordLabel = new JLabel("New Password:");
                passwordLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                passwordLabel.setBounds(50, 100, 50, 100);
                change = new JButton("Change");
                panel.add(change);
                newPassword = new JTextField();

                panel.add(passwordLabel);
                panel.add(newPassword);
                panel.add(change);

                frame.setVisible(true);
                selection = 2;
            }
            if (e.getSource() == editName) {
                content.setLayout(new BorderLayout());
                frame.setSize(225, 360);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel nameLabel = new JLabel("New Name:");
                nameLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                nameLabel.setBounds(50, 100, 50, 100);
                change = new JButton("Change");
                panel.add(change);
                newName = new JTextField();

                panel.add(nameLabel);
                panel.add(newName);
                panel.add(change);

                frame.setVisible(true);
                selection = 3;
            }
            if (e.getSource() == editEmail) {
                content.setLayout(new BorderLayout());
                frame.setSize(225, 360);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel emailLabel = new JLabel("New Email:");
                emailLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                emailLabel.setBounds(50, 100, 50, 100);
                change = new JButton("Change");
                panel.add(change);
                newEmail = new JTextField();

                panel.add(emailLabel);
                panel.add(newEmail);
                panel.add(change);

                frame.setVisible(true);
                selection = 4;
            }
            if (e.getSource() == editPhoneNumber) {
                content.setLayout(new BorderLayout());
                frame.setSize(225, 360);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JLabel phoneNumberLabel = new JLabel("New Phone Number:");
                phoneNumberLabel.setFont( new Font("Comic Sans", Font.PLAIN, 14));
                phoneNumberLabel.setBounds(50, 100, 50, 100);
                change = new JButton("Change");
                panel.add(change);
                newPhoneNumber = new JTextField();

                panel.add(phoneNumberLabel);
                panel.add(newPhoneNumber);
                panel.add(change);

                frame.setVisible(true);
                selection = 5;
            }
            if (e.getSource() == change) {
                switch (selection) {
                    case 1:
                        loggedIn.getUser().setUsername(newUsername.getText());
                        break;
                    case 2:
                        loggedIn.getUser().setPassword(newPassword.getText());
                        break;
                    case 3:
                        loggedIn.getUser().setName(newName.getText());
                        break;
                    case 4:
                        loggedIn.getUser().setEmail(newEmail.getText());
                        break;
                    case 5:
                        long number = Long.parseLong(newPhoneNumber.getText());
                        loggedIn.getUser().setPhoneNumber(number);
                        break;
                }
            }
        }
    };

    public void run() {
        content.setLayout(new BorderLayout());
        frame.setSize(225, 320);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        panel.add(editUsername);
        panel.add(editPassword);
        panel.add(editName);
        panel.add(editEmail);
        panel.add(editPhoneNumber);
        panel.setBackground(Color.decode("#98DE7B"));
        content.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
