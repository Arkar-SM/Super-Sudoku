/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */
import java.util.Scanner;

/**
 * Class for displaying the game tutorial.
 */
public class Tutorial {
    private Scanner scanner;

    public Tutorial(Scanner scanner) {
        this.scanner = scanner;
    }

    // Display the tutorial
    public void displayTutorial() {
        while (true) {
            System.out.println("##########################################################################################################");
            System.out.println("\n--- Tutorial ---\n");
            System.out.println("Welcome to Super Sudoku!\n");
            System.out.println("Game Objective:\n");
            System.out.println("Fill every empty cell in the 9x9 grid with numbers 1 to 9.");
            System.out.println("Each row, column, and 3x3 block must contain each number exactly once.\n");
            System.out.println("How to Play:\n");
            System.out.println("Input Numbers: To place a number, type the column letter, row number, and the number you want to enter.");
            System.out.println("Example: a3 5 (places 5 in cell a3)");
            System.out.println("To delete: b5 0 (removes the number in cell b5)\n");
            System.out.println("Game Modes:\n");
            System.out.println("Easy Mode (45 Clues) \nNormal Mode(35 Clues) \nHard Mode(25 Clues)\n");
            System.out.println("Achievements:\n");
            System.out.println("All modes' results are recorded in your Profile and Hall of Fame.");
            System.out.println("Top players are ranked by the shortest completion times, ranked by Hard Mode, Normal Mode, and Easy Mode accordingly!");
            System.out.println("Give your best to beat the top players!\n");
            System.out.println("##########################################################################################################");
            System.out.println("\n\nEnter [x] to return to Main Menu");

            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("x")) {
                break;  // return to main menu
            } else {
                System.out.println("Press [x] if you wish to return to the Main Menu");
            }
        }
    }
}
