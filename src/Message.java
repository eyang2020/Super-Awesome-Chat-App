import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author AJ Wheatley
 * @version 11/22/2020
 *
 * Project 5
 *
 * This class will act as representation of all messages sent from users
 *
 */

public class Message implements Serializable {
    private final User author; // the author of the message
    private LocalDateTime dateTime; // the date and time that the message was sent
    private String text; // the current text that was sent

    /**
     * @param author the name of the user who sent the message
     * @param dateTime the date and time the message was sent
     * @param text the corresponding text that was sent
     */

    public Message(User author , LocalDateTime dateTime , String text) {
        this.author = author;
        this.dateTime = dateTime;
        this.text = text;
    }

    // returns the user that sent the message
    public User getAuthor() {
        return author;
    }

    // returns the date and time the message was sent at
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // returns the corresponding text sent by the user
    public String getText() {
        return text;
    }

    /**
     * This method sets the current message to a new message from the same user
     * @param dateTime the date and time the message was sent
     * @param text text the corresponding text that was sent
     */
    
    public void setMessage(LocalDateTime dateTime, String text) {
        this.dateTime = dateTime;
        this.text = text;
    }
}
