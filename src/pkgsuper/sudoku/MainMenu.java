package pkgsuper.sudoku;

/**
 * For displaying Main Menu
 * Its for Playing the game, Viewing Tutorial , Viewing their Profile, 
 * viewing Hall of Fame, or exit the game.
 * 
 * @author Taha Bazyar
 */
import java.util.Scanner;

public class MainMenu {
    private StartMenu startMenu; // Reference to the StartMenu

    // constructor for the MainMenu with a reference to the StartMenu
    public MainMenu(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    // Display the main menu options
    public void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("(1) Play Game");
            System.out.println("(2) Tutorial");
            System.out.println("(3) View Profile");
            System.out.println("(4) Hall of Fame");
            System.out.println("(5) Exit");
            System.out.println("Enter [x] to go back to the Start Menu");

            // convert the input to lowercase for consistency
            String choice = scanner.nextLine().trim().toLowerCase();

            // Handle the player's choice
            switch (choice) {
                case "1":
                    // Start the game, pass the scanner
                    new PlayGame(scanner, StartMenu.currentPlayerName).startGame();
                    break;
                case "2":
                    // Display the tutorial
                    new Tutorial(scanner).displayTutorial();
                    break;
                case "3":
                    // View the current player's profile
                    if (!StartMenu.currentPlayerName.isEmpty()) {
                    new ViewProfile(scanner, this, startMenu).displayProfile(StartMenu.currentPlayerName);
                    } else {
                    System.out.println("No current player profile is loaded."); // handle the case where no player is loaded
                    }
                    break;
                case "4":
                    // Display Hall of Fame
                    new HallOfFame().displayHallOfFame(scanner);
                    break;
                case "5":
                    // Confirm exit 
                    if (confirmExit(scanner)) {
                        System.out.println("Exiting the game.");
                        System.exit(0);
                    }
                    break;
                case "x":
                    // return to start menu
                    System.out.println("Returning to the Start Menu...");
                    startMenu.displayMenu(); // start menu display method
                    return; // Exit the main menu loop
                default:
                    // Handle invalid input
                    System.out.println("Invalid input. Please select a valid option.");
                    break;
            }
        }
    }

    //confirm exit
    private static boolean confirmExit(Scanner scanner) {
        System.out.println("\nAre you sure you want to exit? (Y/N)");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y");
    }
}
