/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.sydygaliev.java_journey.view;

import java.util.Scanner;

/**
 *
 * @author Kloody
 */
public class View {

    Scanner scan;

    public View() {
        scan = new Scanner(System.in);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printMsg(String message) {
        System.out.print(message);
    }

    public String scanLine() {
        return scan.nextLine();
    }

    public String scanNext() {
        return scan.next();
    }
}
