/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;
/**
 *
 * @author Arkar
 */
import java.util.Scanner;

/**
 * Utility class to handle common operations.
 */
public class Utils {

    // Method to confirm exit
    public static boolean confirmExit(Scanner scanner) {
        System.out.println("\nAre you sure you want to exit? (Y/N)");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y");
    }

    // Method to validate move format
    public static boolean validateMoveFormat(String move) {
        return move.matches("[a-i][0-9] [0-9]");
    }
}
