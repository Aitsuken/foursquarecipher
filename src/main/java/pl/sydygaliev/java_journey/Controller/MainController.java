package pl.sydygaliev.java_journey.Controller;

import java.util.Arrays;
import java.util.Scanner;
import pl.sydygaliev.java_journey.model.InitialHandlerAndStorer;
import pl.sydygaliev.java_journey.model.MessageModel;
import pl.sydygaliev.java_journey.model.MessageOperator;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.model.exception.WrongFormatException;
import pl.sydygaliev.java_journey.view.View;

/**
 * Main and sole controller which is used to communicate with other modules
 *
 * @author Ulan Sydygaliev
 * @version f1
 */
public class MainController {

    /**
     * Main method which initializes the project. It can work with arguments or
     * with command-line input
     *
     * @param args first arg - message, second and third(both optional) for
     * manual mode keyword1 and keyword2 for manual mode
     */
    public static void main(String[] args) {
        View view = new View();
        int counterOfErrors = 0;
        InitialHandlerAndStorer initialDi = new InitialHandlerAndStorer();
        switch (args.length) {
            case 1:
                view.printMessage("Automatic mode with provided arguments commenced");
                initialDi.setMessage(args[0]);
                if (initialDi.getMessage() == null) {
                    view.printMessage("Provided arguments were illegal");
                    counterOfErrors++;
                } else {

                    initialDi.setKeyword1(initialDi.automaticKeywordSetter());
                    initialDi.setKeyword2(initialDi.automaticKeywordSetter());
                }
                break;
            case 3:
                view.printMessage("Manual mmode with provided keyword arguments");

                initialDi.setMessage(args[0]);
                if (initialDi.getMessage() == null) {
                    view.printMessage("Provided message cannot be accepted");
                    counterOfErrors++;
                }

                try {
                    initialDi.setKeyword1(initialDi.handleKeyword((args[1])));
                } catch (WrongFormatException e) {
                    view.printMsg("Provided fisrt keyword is unacceptable!");
                    counterOfErrors++;
                }

                initialDi.setKeyword2(args[2]);
                try {
                    initialDi.setKeyword2(initialDi.handleKeyword((args[2])));
                } catch (WrongFormatException e) {
                    view.printMsg("Provided second keyword is unacceptable!");
                    counterOfErrors++;
                }
                break;
            default:
                view.printMsg("No-arg mode! Please, type the message: ");

                while (true) {
                    initialDi.setMessage(view.scanLine());
                    if (initialDi.getMessage() == null) {
                        view.printMsg("Incorrect credentials! Please, try again: ");
                    } else {
                        break;
                    }
                }

                view.printMessage("Alright, now selectd keyword mode, ");
                view.printMsg("0 for manual, 1 for automatic: ");

                while (true) {
                    initialDi.handleMode(view.scanLine());

                    if (initialDi.getMode() == null) {
                        view.printMsg("You have mistyped, just type \"0\" or \"1\": ");
                    } else {
                        break;
                    }
                }
                if ("0".equals(initialDi.getMode())) {
                    while (true) {
                        view.printMsg("Please, type the first keyword: ");
                        try {
                            initialDi.setKeyword1(initialDi.handleKeyword(view.scanLine()));
                        } catch (WrongFormatException e) {
                            view.printMsg("Criminal credentials, please, try again! ");
                        }
                        if (initialDi.getKeyword1() != null) {
                            break;
                        }
                    }
                    view.printMsg("Please, type the second keyword: ");
                    while (true) {
                        try {
                            initialDi.setKeyword2(initialDi.handleKeyword(view.scanLine()));
                        } catch (WrongFormatException e) {
                            view.printMsg("Criminal credentials, please, try again! ");
                        }
                        if (initialDi.getKeyword2() != null) {
                            break;
                        }
                    }
                } else if ("1".equals(initialDi.getMode())) {
                    initialDi.setKeyword1(initialDi.automaticKeywordSetter());
                    initialDi.setKeyword2(initialDi.automaticKeywordSetter());
                }

                break;
        }

        if (counterOfErrors < 1) {
            MessageModel msgModel = new MessageModel(
                    initialDi.getMessage(), initialDi.getKeyword1(), initialDi.getKeyword2());
            view.printMessage("The encrypted message is: " + msgModel.getEncryptedMessage());
            view.printMessage("The decrypted and restored message is: " + msgModel.getDecryptedMessage());
        } else {
            view.printMessage("Please, restart the program!");
        }

    }

}
