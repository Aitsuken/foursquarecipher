package pl.sydygaliev.java_journey.Controller;

import java.util.Arrays;
import java.util.Scanner;
import pl.sydygaliev.java_journey.model.InitialHandlerAndStorer;
import pl.sydygaliev.java_journey.model.MessageModel;
import pl.sydygaliev.java_journey.model.MessageOperator;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.view.View;

/**
 *
 * @author Ulan Sydygaliev
 */
public class MainController {

    public static void main(String[] args) {
        View view = new View();

        view.printMsg("Hello! Please, type the message: ");
        InitialHandlerAndStorer initialDi = new InitialHandlerAndStorer();
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
                initialDi.setKeyword1(view.scanLine());
                if (initialDi.handleKeyword(initialDi.getKeyword1()) == null) {
                    view.printMsg("Criminal credentials, please, try again! ");
                } else {
                    break;
                }
            }
            while (true) {
                view.printMsg("Please, type the second keyword: ");
                initialDi.setKeyword2(view.scanLine());
                if (initialDi.handleKeyword(initialDi.getKeyword2()) == null) {
                    view.printMsg("Criminal credentials, please, try again! ");
                } else {
                    break;
                }
            }

        } else if ("1".equals(initialDi.getMode())) {
            initialDi.setKeyword1(initialDi.automaticKeywordSetter());
            initialDi.setKeyword2(initialDi.automaticKeywordSetter());
        }

        MessageModel msgModel = new MessageModel(
                initialDi.getMessage(), initialDi.getKeyword1(), initialDi.getKeyword2());
        view.printMessage("The encrypted message is: " + msgModel.getEncryptedMessage());
        view.printMessage("The decrypted and restored message is: " + msgModel.getDecryptedMessage());

    }

}
