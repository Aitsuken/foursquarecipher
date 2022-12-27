package pl.sydygaliev.java_journey.model;

/**
 * Message class for message things
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
public class Message {

    /**
     * stores message
     */
    private String message;

    public Message(String message) {
        this.message = message;
    }

    /**
     * message accessor
     *
     * @return stored message
     */
    public String getMessage() {
        return message;
    }

    /**
     * message mutator
     *
     * @param message to be stored
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * toString method for Message
     *
     * @return String representation of the Message
     */
    @Override
    public String toString() {
        return this.message;
    }
}
