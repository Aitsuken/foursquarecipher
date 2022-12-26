package pl.sydygaliev.java_journey.model;

/**
 * MessageModel class is used to store messages and keyword for further message
 * operations. It also used to store encrypted and decrypted messages.
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
public class MessageModel {

    /**
     * message field to store message for the cipher
     */
    private String message;

    /**
     * stores encrypted message
     */
    private String encryptedMessage;
    /**
     * stores decrypted message
     */
    private String decryptedMessage;

    /**
     * stores the state of original message
     */
    private String originalMessageKeys;
    /**
     * tunes message so it can go through the ciphering process without problems
     */
    private String tunedMessage;
    /**
     * message operator instance which will be used for cipher operations
     */
    private MessageOperator messageOperator;

    /**
     * Instantiates fields and fixes the message. Fixed message is valid for
     * encryption. Generates encrypted and decrypted messages
     *
     * @param message provided message for operations
     * @param keyword1 used for encryption in message Operator
     * @param keyword2 used for encryption in message Operator
     */
    public MessageModel(String message, String keyword1, String keyword2) {

        this.messageOperator = new MessageOperator(keyword1, keyword2);

        this.tunedMessage = tuneMessage(message);

        this.encryptedMessage = messageOperator.encryptMessage(tunedMessage);
        this.decryptedMessage = messageOperator.decryptMessage(encryptedMessage);
    }

    /**
     * Is used for message tuning.
     *
     * @param message data is to be tuned
     * @return a tuned message that has valid English letters for the cipher
     * operations
     */
    private String tuneMessage(String message) {
        char[] messageArray = message.toCharArray();
        StringBuilder originalMessageKeys = new StringBuilder();

        for (int i = 0; i < messageArray.length; i++) {
            char currentLetter = messageArray[i];
            if (currentLetter == 'q' || currentLetter == 'Q') {
                originalMessageKeys.append(currentLetter);
            } else if (currentLetter >= 65 && currentLetter <= 90) {
                originalMessageKeys.append('0');

            } else if (currentLetter >= 97 && currentLetter <= 122) {
                originalMessageKeys.append('1');

            } else {
                originalMessageKeys.append(currentLetter);
            }
        }

        StringBuilder tunedMessage = new StringBuilder();
        message = message.toLowerCase();

        for (int i = 0; i < message.length(); i++) {
            char currentLetter = message.charAt(i);
            if (currentLetter != 'q'
                    && (originalMessageKeys.charAt(i) == '0'
                    || originalMessageKeys.charAt(i) == '1')) {
                tunedMessage.append(currentLetter);
            }

        }
        if (tunedMessage.length() % 2 != 0) {
            originalMessageKeys.setCharAt(
                    originalMessageKeys.length() - 1, tunedMessage.charAt(tunedMessage.length() - 1));
            tunedMessage.deleteCharAt(tunedMessage.length() - 1);
        }

        this.originalMessageKeys = originalMessageKeys.toString();

        return tunedMessage.toString();
    }

    /**
     * Performs message restoration. It's initial state based on
     * decryptedMessage and originalMessageKeys
     *
     * @param decryptedMessage used to restore original message
     * @return restored message
     */
    private String restoreMessage(String decryptedMessage) {
        StringBuilder restoredMessage = new StringBuilder();

        int nonTunedCounter = 0;
        for (int i = 0; i < originalMessageKeys.length(); i++) {
            char currentLetter = originalMessageKeys.charAt(i);
            switch (currentLetter) {
                case '0' ->
                    restoredMessage.append((char) (decryptedMessage.charAt(i - nonTunedCounter) - 32));
                case '1' ->
                    restoredMessage.append(decryptedMessage.charAt(i - nonTunedCounter));
                default -> {
                    restoredMessage.append(originalMessageKeys.charAt(i));
                    nonTunedCounter++;
                }
            }
        }

        return restoredMessage.toString();
    }

    /**
     * Accessor to get encrypted message
     *
     * @return encrypted message field
     */
    public String getEncryptedMessage() {
        return this.encryptedMessage;
    }

    /**
     * Accessor to get decrypted message
     *
     * @return decrypted message field
     */
    public String getDecryptedMessage() {
        return restoreMessage(this.decryptedMessage);
    }

    /**
     * Accessor to get message
     *
     * @return message field
     */
    public String getMessage() {
        return this.message;
    }

}
