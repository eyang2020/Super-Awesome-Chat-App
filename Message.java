/**
 * @author AJ Wheatley
 * @version 11/22/2020
 *
 * Project 5
 *
 * This class will act as representation of all messages sent from users
 *
 */

public class Message {
    private final String author; // the author of the message
    private String date; // the date that the message was sent
    private String time; // the time that the message was sent
    private String text; // the current text that was sent

    /**
     * @param author the name of the user who sent the message
     * @param date the date the message was sent
     * @param time the time the message was sent
     * @param text the corresponding text that was sent
     */

    public Message(String author , String date , String time , String text) {
        this.author = author;
        this.date = date;
        this.time = time;
        this.text = text;
    }

    // returns the user that sent the message
    public String getAuthor() {
        return author;
    }

    // returns the date the message was sent at
    public String getDate() {
        return date;
    }

    // returns the time the message was sent at
    public String getTime() {
        return time;
    }

    // returns the corresponding text sent by the user
    public String getText() {
        return text;
    }

    /**
     * This method sets the current message to a new message from the same user
     * @param date the date the message was sent
     * @param time time the time the message was sent
     * @param text text the corresponding text that was sent
     */
    public void setMessage(String date, String time, String text) {
        this.date = date;
        this.time = time;
        this.text = text;
    }
}
