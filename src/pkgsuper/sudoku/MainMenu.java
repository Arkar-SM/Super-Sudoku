package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */


import java.util.Scanner;

/**
 * For displaying Main Menu.
 * It offers playing the game, viewing the tutorial, viewing profiles, Hall of Fame, or exiting the game.
 */
public class MainMenu {
    private StartMenu startMenu; // Reference to the StartMenu
    private ProfileManager profileManager = new ProfileManager(); // Managing profiles

    // Constructor for the MainMenu
    public MainMenu(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    // Display the Main Menu options
    public void displayMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("(1) Play Game");
            System.out.println("(2) Tutorial");
            System.out.println("(3) View Profile");
            System.out.println("(4) Hall of Fame");
            System.out.println("(5) Exit");
            System.out.println("Enter [x] to go back to the Start Menu");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    // Start the game
                    displayGameModes(scanner);
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
                        System.out.println("No current player profile is loaded.");
                    }
                    break;
                case "4":
                    // Display Hall of Fame
                    new HallOfFame().displayHallOfFame(scanner);
                    break;
                case "5":
                    // Confirm exit
                    if (Utils.confirmExit(scanner)) {
                        System.out.println("Exiting the game.");
                        System.exit(0);
                    }
                    break;
                case "x":
                    // Return to start menu
                    System.out.println("Returning to the Start Menu...");
                    startMenu.displayMenu();
                    return;
                default:
                    System.out.println("Invalid input. Please select a valid option.");
                    break;
            }
        }
    }

    // Display and select game modes
    private void displayGameModes(Scanner scanner) {
        System.out.println("\nPlease choose your mode:");
        System.out.println("(1) Easy");
        System.out.println("(2) Normal");
        System.out.println("(3) Hard");
        System.out.println("(4) Test");

        String modeInput = scanner.nextLine().trim().toLowerCase();
        switch (modeInput) {
            case "1":
                new GameMode(scanner, GameMode.Difficulty.EASY).startGame();
                break;
            case "2":
                new GameMode(scanner, GameMode.Difficulty.NORMAL).startGame();
                break;
            case "3":
                new GameMode(scanner, GameMode.Difficulty.HARD).startGame();
                break;
            case "4":
                new GameMode(scanner, GameMode.Difficulty.TEST).startGame();
                break;
            default:
                System.out.println("Invalid input. Returning to Main Menu.");
                break;
        }
    }
}
