import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


/**
 *
 * User class for messaging app
 *
 * @author Ruth Baldwin
 * @version November 22, 2020
 *
 */
public class User {
    private String name; //name of user
    private String username; //username of user
    private String email; //email of user
    private long phoneNumber; //phone number of user
    private String password; //password for users account
    ArrayList<Group> groups; //groups the user is in

    /**
     *
     * @param name name of user
     * @param username username of user
     * @param email email of user
     * @param phoneNumber phone number of user
     * @param password password of user
     *
     * This constructor creates a new user, this will only happen once per user. The username
     * and password of the user created is stored on a file.
     */
    public User(String name, String username, String email, long phoneNumber, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        groups = new ArrayList<Group>();

    }
    public User() {}
    /**
     *
     * @param usernameToFind the username of the user looking to log in
     * @param passwordToCheck the password the user is entering to log in
     * @param userFileName the name of the file with the user information
     *
     * Constructor used to log in the user. Checks if username and password are correct. If username exists but password
     * is wrong it tells the user to enter a correct password
     */
//    public User(String usernameToFind, String passwordToCheck) { // constructor to log user in, userFile is constant
//        //access established file of users from driver class
//
//    }

    /**
     * Allows the user to change their name
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allows the user to change their email
     * @param email the new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Allows the user to change their username
     * this method also changes the username on file
     * @param newUsername the new username of the user
     */
    public void setUsername(String newUsername, String userFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            String[] userData;
            ArrayList<String> users = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                users.add(line);
            }
            for (int i = 0; i < users.size(); i++) {
                userData = users.get(i).split(" , ");
                if (userData[0].equals(this.username)) {
                    users.set(i, newUsername + " , " + userData[1]);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(userFileName);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            for (int j = 0; j < users.size(); j++) {
                printWriter.println(users.get(j));
            }
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.username = newUsername;
    }

    /**
     * Allows the user to change their password
     * this method also changes the password on file
     * @param newPassword the new password of the user
     */
    public void setPassword(String username, String newPassword, String userFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(userFileName))) {
            String line;
            String[] userData;
            ArrayList<String> users = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                users.add(line);
            }
            for (int i = 0; i < users.size(); i++) {
                userData = users.get(i).split(" , ");
                if (userData[0].equals(this.password)) {
                    users.set(i, userData[0] + " , " + newPassword);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(userFileName);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            for (int j = 0; j < users.size(); j++) {
                printWriter.println(users.get(j));
            }
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.password = password;
    }

    /**
     * Allows the user to change their phone number
     * @param phoneNumber the new phone number of the user
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    //Begin Login code

    JButton login;
    JButton createUser;
    JButton login1;
    JButton create;
    JFrame frame = new JFrame("Super Awesome Chat App");
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    Container content = frame.getContentPane();
    int curX;
    int curY;
    int oldX;
    int oldY;
    JTextField username1 = new JTextField(15);
    JTextField password1 = new JTextField(15);
    JTextField email1 = new JTextField(15);
    JTextField name1 = new JTextField(15);
    JTextField phoneNumber1 = new JTextField(15);

    User user;

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
    public void loginUser(String usernameToFind, String passwordToCheck) {
        try (BufferedReader br = new BufferedReader(new FileReader("userFileName"))) {
            String line;
            String[] userData;
            while ((line = br.readLine()) != null) {
                userData = line.split(" , "); //separate the username and password on the file as "username , password"
                if (usernameToFind.equals(userData[0]) && passwordToCheck.equals(userData[1])) {
                    //successful login
                } else if (usernameToFind.equals(userData[0]) && !passwordToCheck.equals(userData[1])) {
                    //incorrect password
                } else {
                    //username not found
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
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
    public void userCreation(String usersName, String usersUsername, String usersEmail, long usersPhoneNumber, String usersPassword) {
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

    }

    /**
     * Returns the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Adds a given group to the groups arraylist
     * @param group the group to be added to groups
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     *
     * Runs the welcome screen
     */
    public static void main(String[] args) {
        User user1 = new User();
        user1.run();
    }
}


