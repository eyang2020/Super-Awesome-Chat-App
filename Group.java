import java.util.*;

/**
 * Project 5 - Group
 * 
 * This class will act as a representation of a group of users. 
 * Together, they can chat among people in their group.
 * Users can also edit and delete their messages, and
 * also leave the group.
 * 
 * @author Evan Yang, 006 
 * @version November 21, 2020
*/
public class Group {
    private String groupName; // the name of the group chat.
    private ArrayList<User> users; // a collection of users that are part of the group.
    private ArrayList<Message> messages; // a collection of messages sent within the group.

    /**
     * @param groupName the given name of the group chat.
     * @param users an initial collection of users added into the group.
     */
    public Group(String groupName, ArrayList<User> users) {
        this.groupName = groupName;
        this.users = users;
    }

    // returns the current name of the group chat.
    public String getGroupName() {
        return groupName;
    }

    // returns the ArrayList of users within the group.
    public ArrayList<User> getUsers() {
        return users;
    }

    // returns the ArrayList of messages sent within the group.
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * @param groupName the new name of the group chat.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @param user a new user to be added to the Arraylist
     * of users.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * @param message a new message to be added to the ArrayList
     * of messages.
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * This method edits a message at the specified index
     * of the ArrayList of messages.
     * @param idx index of the message to be edited.
     * @param message the new, edited message.
     */
    public void editMessage(int idx, Message message) {
        messages.set(idx, message);
    }

    /**
     * This method deletes a message at the specified index
     * of the ArrayList of messages.
     * @param idx index of the message to be deleted.
     */
    public void deleteMessage(int idx) {
        messages.remove(idx);
    }

    /**
     * This method removes a specified user from the group.
     * @param user the user to be removed.
     */
    public void removeUser(User user) {
        users.remove(user);
    }
}