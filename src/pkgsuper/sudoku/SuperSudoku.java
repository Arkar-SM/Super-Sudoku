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
 * Main Class to initialize the game.
 * It starts by displaying the Start Menu.
 */
public class SuperSudoku {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        ProfileManager profileManager = new ProfileManager(); // Manage profiles

        StartMenu startMenu = new StartMenu(scanner, null, profileManager); // Initialize StartMenu
        MainMenu mainMenu = new MainMenu(startMenu); // Initialize MainMenu

        startMenu.setMainMenu(mainMenu); // Link StartMenu with MainMenu

        startMenu.displayMenu(); // Start the game by displaying the StartMenu
    }
}
