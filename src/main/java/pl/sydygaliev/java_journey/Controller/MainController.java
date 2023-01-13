package pl.sydygaliev.java_journey.Controller;

import java.util.Arrays;
import java.util.Scanner;
import pl.sydygaliev.java_journey.model.InitialHandlerAndStorer;
import pl.sydygaliev.java_journey.model.MessageModel;
import pl.sydygaliev.java_journey.model.MessageOperator;
import pl.sydygaliev.java_journey.model.ModeEnum;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.model.exception.UnexpectedModeException;
import pl.sydygaliev.java_journey.model.exception.WrongFormatException;
import pl.sydygaliev.java_journey.view.View;

/**
 * Main and sole controller which is used to communicate with other modules
 *
 * @author Ulan Sydygaliev
 * @version f2
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

        InitialHandlerAndStorer initialDi = new InitialHandlerAndStorer();
        ModeEnum argsOrNo = initialDi.argsHandler(args);
        if (argsOrNo == ModeEnum.NOARGS_ATALL) {
            view.printMessage("Args were not provided");
            view.printMessage("Commencing operation STRIX!(non-args mode)");
        } else if (argsOrNo == ModeEnum.NONARGS) {
            view.printMessage("Provided args cannot be accepted");
            view.printMessage("Commencing non-args mode!");
        } else {
            view.printMessage("Provided arguments were accepted");
            view.printMessage("Starting the ARGS mode!");
        }

        if (argsOrNo != ModeEnum.ARGS) {
            initialDi = new InitialHandlerAndStorer();
            while (true) {
                try {
                    view.printMsg("Please, type the message: ");
                    initialDi.setMessage(initialDi.handleMessage(view.scanLine()));
                    view.printMessage("Alright, now selectd keyword mode, ");
                    view.printMsg("0 for manual, 1 for automatic: ");
                    initialDi.handleMode(view.scanLine());
                    if ("0".equals(initialDi.getMode())) {
                        view.printMsg("Please, type the first keyword: ");
                        initialDi.setKeyword1(initialDi.handleKeyword(view.scanLine()));
                        view.printMsg("Please, type the second keyword: ");
                        initialDi.setKeyword2(initialDi.handleKeyword(view.scanLine()));
                        view.printMessage("Manual mode has been commenced!");
                    } else if ("1".equals(initialDi.getMode())) {
                        view.printMessage("Automatic mode was selected, let's go!");
                        initialDi.setKeyword1(initialDi.automaticKeywordSetter());
                        initialDi.setKeyword2(initialDi.automaticKeywordSetter());
                    }
                    break;
                } catch (WrongFormatException | NonAlphaNumericException e) {
                    view.printMessage("Message/keyword format was wrong...");

                    view.printMessage("Please, try again");
                } catch (UnexpectedModeException e) {
                    view.printMessage("The selected mode was not recognized, try again!");
                    view.printMessage("just type \"0\" or \"1\" ");
                }

            }

        }
        MessageModel msgModel = new MessageModel(
                initialDi.getMessage(), initialDi.getKeyword1(), initialDi.getKeyword2());
        view.printMessage("The encrypted message is: " + msgModel.getEncryptedMessage());
        view.printMessage("The decrypted and restored message is: " + msgModel.getDecryptedMessage());


    }

}
