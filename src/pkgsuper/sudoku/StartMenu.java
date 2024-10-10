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
 * In StartMenu, players can enter their name, create a new profile, or exit the game.
 */
public class StartMenu {
    private Scanner scanner;
    private MainMenu mainMenu; // reference to MainMenu class
    private ProfileManager profileManager;

    public static String currentPlayerName = ""; // Memorizes player name across the game

    // Constructor
    public StartMenu(Scanner scanner, MainMenu mainMenu, ProfileManager profileManager) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
        this.profileManager = profileManager;
    }

    // Set the Main Menu instance
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    // Display the Start Menu options
    public void displayMenu() {
        while (true) {
            System.out.println("\nWelcome to Super Sudoku!!");
            System.out.println("(1) Enter Player Name");
            System.out.println("(2) Create New Player");
            System.out.println("(3) Exit");

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "1":
                    enterPlayerName();
                    break;
                case "2":
                    createNewPlayer();
                    break;
                case "3":
                case "x": // x for exit
                    if (Utils.confirmExit(scanner)) {
                        System.exit(0); // Exit the program
                    }
                    break;
                default:
                    System.out.println("\n####Enter 1, 2, 3, or x####");
            }
        }
    }

    // Method for players to enter their name, if they already created their profile
    private void enterPlayerName() {
        System.out.println("\nEnter Your Name (max 20 chars):");
        System.out.println("\nEnter [x] to go back to the Start Menu");
        String name = scanner.nextLine().trim();
        if (name.equalsIgnoreCase("x")) {
            return; // Go back
        }
        if (name.length() > 20) {
            System.out.println("\n####Only 20 characters allowed in name!####");
            return;
        }

        if (profileManager.validateProfile(name)) {
            currentPlayerName = name;
            System.out.println("####Going to Main Menu...####");
            mainMenu.displayMenu(scanner);
        } else {
            System.out.println("####Player doesn’t exist! Do you want to create a new player? (Y/N)####");
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("Y")) {
                createNewPlayer();
            }
        }
    }

    // Method to create a new profile
    private void createNewPlayer() {
        while (true) {
            System.out.println("\nEnter Your Name for new profile (max 20 chars):");
            System.out.println("\nEnter [x] to go back to the Start Menu");
            String name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("x")) {
                return; // Go back to the Start Menu
            }
            if (name.length() > 20) {
                System.out.println("\n####Only 20 characters allowed in name!!####");
                continue;
            }

            if (profileManager.createProfile(name)) {
                mainMenu.displayMenu(scanner); // Transition to the main menu if the profile is created
                break;
            }
        }
    }
}
