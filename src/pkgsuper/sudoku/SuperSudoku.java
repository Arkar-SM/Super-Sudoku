package pkgsuper.sudoku;

/**
 * Main Class
 * Initialize Start Menu and Main Menu
 * Start by displaying the Start Menu.
 * 
 * @author Taha Bazyar
 */

import java.util.Scanner;

public class SuperSudoku {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object

        // with a reference to MainMenu as null
        StartMenu startMenu = new StartMenu(scanner, null);
        
        // Initialize MainMenu
        MainMenu mainMenu = new MainMenu(startMenu);

        // link start menu with main menu
        startMenu.setMainMenu(mainMenu);

        // display start menu , begin the game
        startMenu.displayMenu();
    }
}
