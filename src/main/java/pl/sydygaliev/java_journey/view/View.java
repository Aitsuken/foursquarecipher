package pl.sydygaliev.java_journey.view;

import java.util.Scanner;

/**
 * This class is used to implement view concept. View class is responsible to
 * get data from the user and show data to the user
 *
 * @author Kloody
 * @version f2
 */
public class View {

    /**
     * Scanner field which will be used to get data from the user
     */
    private Scanner scan;

    /**
     * View constructor to instantiate scan field
     */
    public View() {
        scan = new Scanner(System.in);
    }

    /**
     * Prints out message provided to the method with jumping to next line 
     * @param message value to be printed to the console
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints out message provided to the method without jumping to next line
     * @param message value to be printed to the console
     */
    public void printMsg(String message) {
        System.out.print(message);
    }

    /**
     * Scans whole line typed by the user
     * @return string that stores scanned data
     */
    public String scanLine() {
        return scan.nextLine();
    }

     /**
     * Scans next value, separated by space typed by the user
     * @return string that stores scanned data
     */
    public String scanNext() {
        return scan.next();
    }
}
