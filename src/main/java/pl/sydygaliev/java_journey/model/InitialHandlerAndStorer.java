package pl.sydygaliev.java_journey.model;

import java.util.Random;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.model.exception.UnexpectedModeException;
import pl.sydygaliev.java_journey.model.exception.WrongFormatException;

/**
 * This class is used to store data from the user and check validity. Controller
 * isn't responsible for storing data that's why this class was implemented.
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
public class InitialHandlerAndStorer {

    /**
     * Field to store a message for the crypting operations
     */
    private String message;
    /**
     * Field to store the first keyword for the FourSquareCipher
     */
    private String keyword1;
    /**
     * Field to store the second keyword for the FourSquareCipher
     */
    private String keyword2;
    /**
     * Field to store the encryption mode determiner given by the user
     * manual/automatic
     */
    private String mode;

    /**
     * Field that is used to handle args
     */
    private ModeEnum argsMode;

    /**
     * Empty constructor just to initiate the object
     */
    public InitialHandlerAndStorer() {

    }

    /**
     * This method is used to handle message, which is, checking characters
     *
     * @param message message to be encrypted, used for checking
     * @return message if it's valid null if it didn't pass the validity check
     *
     */
    public String handleMessage(String message) throws WrongFormatException, NonAlphaNumericException {

        if (message.length() == 0) {
            throw new WrongFormatException();
        }
        for (int i = 0; i < message.length(); i++) {
            if (!((message.charAt(i) <= 122 && message.charAt(i) >= 97)
                    || (message.charAt(i) <= 90 && message.charAt(i) >= 63)
                    || (message.charAt(i) <= 57 && message.charAt(i) >= 32)) || message.charAt(i) == 63) {
                throw new NonAlphaNumericException();
            }
        }

        return message;
    }

    /**
     * The method that is used to handle given keyword. If keyword has illegal
     * values, it returns null.
     *
     * @param keyword is used for keyword validation
     * @throws WrongFormatException when format has undesirable symbols
     * @return keyword with given value or null if keyword has unacceptable
     * characters
     *
     */
    public String handleKeyword(String keyword) throws WrongFormatException {
        if (keyword.length() == 0) {
            throw new WrongFormatException();
        }
        for (int i = 0; i < keyword.length(); i++) {

            if (keyword.charAt(i) > 122 || keyword.charAt(i) < 32) {
                throw new WrongFormatException();
            }
        }

        return keyword;
    }

    /**
     * This method is used for handling mode selected by the user. If mode
     * equals to 0 or 1 it passes the validity check and sets the mode field
     * with presented values. Otherwise, it returns null
     *
     * @param mode parameter that was selected by the user
     * @throws UnexpectedModeException when given mode is illegal
     */
    public void handleMode(String mode) throws UnexpectedModeException{
        if ("1".equals(mode) || "0".equals(mode)) {
            this.mode = mode;
        } else {
            throw new UnexpectedModeException();
        }
    }

    /**
     * This method is used to generate keywords if the mode selected by the user
     * is automatic. Keyword has 10 unique "randomly" created characters.
     *
     * @return automatically generated keyword
     */
    public String automaticKeywordSetter() {
        StringBuilder keyword = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            char letter = (char) (97 + rand.nextInt(26));
            int count = 0;
            for (int j = 0; j < keyword.length(); j++) {
                if (letter == keyword.charAt(j)) {
                    count++;
                }
            }
            if (count == 0) {
                keyword.append(letter);
            } else {
                i--;
            }
        }
        return keyword.toString();
    }

    /**
     * Handles args approach
     *
     * @param args varargs of Strings
     * @return
     */
    public ModeEnum argsHandler(String... args) {
        ModeEnum noArgs = ModeEnum.NONARGS;
        if (args.length == 0) {
            return ModeEnum.NOARGS_ATALL;
        }
        if (args.length != 1 && args.length != 3 && args.length > 0) {
            return noArgs;
        }
        try {
            this.message = handleMessage(args[0]);
        } catch (WrongFormatException | NonAlphaNumericException e) {
            return noArgs;
        }
        if (args.length == 1) {
            setKeyword1(automaticKeywordSetter());
            setKeyword2(automaticKeywordSetter());
        } else if (args.length == 3) {
            try {
                keyword1 = handleKeyword(args[1]);
                keyword2 = handleKeyword(args[2]);
            } catch (WrongFormatException e) {
                return ModeEnum.NONARGS;
            }
        }
        return ModeEnum.ARGS;
    }

    /**
     * Acessor method to get the message
     *
     * @return message stored in the object
     */
    public String getMessage() {
        return message;
    }

    /**
     * Mutator method to change message. Calls "handleMessage" method and
     * assigns methods's return to message field
     *
     * @param message message, that was given by the user through args or
     * console gui
     */
    public void setMessage(String message) {
        this.message = message;

    }

    /**
     * Acessor method to retrive keyword1. Calls "handleMessage" method and
     * assigns methods's return to message field
     *
     * @return keyword1 field
     */
    public String getKeyword1() {
        return keyword1;
    }

    /**
     * Mutator method which is used to set keyword1
     *
     * @param keyword1 provided keyword by the user that goes through validity
     * check
     */
    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    /**
     * Acessor method which is used to retrieve keyword2
     *
     * @return keyword2 second keyword
     */
    public String getKeyword2() {
        return keyword2;
    }

    /**
     * Mutator method which is used to set keyword2
     *
     * @param keyword2 provided keyword by the user that goes through validity
     * check
     */
    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    /**
     * Acessor method to get the mode
     *
     * @return mode stored in the object
     */
    public String getMode() {
        return this.mode;
    }
}
