/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;

/**
 * 
 *In StartMenu players can enter their name, create a new profile, or exit the game.
 * 
 * @author Arkar
 */

import java.io.*;
import java.util.Scanner;

public class StartMenu {
    private Scanner scanner;
    private MainMenu mainMenu; // reference to MainMenu class

    public static String currentPlayerName = ""; // Store the name of current player, its memorize the player name across the game so player doesnt have to enter their name again

    // Initialization of the StartMenu and Main Menu reference
    public StartMenu(Scanner scanner, MainMenu mainMenu) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
    }

    // Main Menu Instance
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    //The start menu options and user input handler
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
                    if (confirmExit()) {
                        System.exit(0); // Exit the program
                    }
                    break;
                default:
                    System.out.println("\n####Enter 1, 2, 3, or x####");
            }
        }
    }

    // Method for players to enter their name , if they already created their profile before
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
        currentPlayerName = name; // Set to the current player name
        handleProfile(name); // Handle the player's profile
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

            File file = new File("./resources/" + name + "_profile.txt");
            if (file.exists()) {
                System.out.println("####Player name already exists!! Choose a different name.####");
            } else {
                if (createProfile(name)) {
                    mainMenu.displayMenu(scanner); // Transition to the main menu if the profile is created
                }
                break;
            }
        }
    }

    // confirm exit
    private boolean confirmExit() {
        System.out.println("\nAre you sure you want to exit? (Y/N)");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("Y");
    }

    // player's profile validation
    private void handleProfile(String name) {
        File file = new File("./resources/" + name + "_profile.txt");
        if (file.exists()) {
            System.out.println("####Going to Main Menu...####");
            mainMenu.displayMenu(scanner); // Pass the scanner to the main menu
        } else {
            System.out.println("####Player doesn’t exist! Do you want to create a new player? (Y/N)####");
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("Y")) {
                createNewPlayer();
            }
        }
    }

    // Profile File I/O 
    private boolean createProfile(String name) {
        File file = new File("./resources/" + name + "_profile.txt");
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Header
                writer.write(String.format(
                    "%-20s %-10s %-20s %-10s %-20s %-10s %-20s %-10s %-20s\n",
                    "Name", "Easy", "Easy_Best_Time", "Normal", "Normal_Best_Time",
                    "Hard", "Hard_Best_Time", "Test", "Test_Best_Time"
                ));
                // Initial Values for the new player
                writer.write(String.format(
                    "%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s\n",
                    name, 0, "--", 0, "--", 0, "--", 0, "--"
                ));
                System.out.println("####Player Created!!! Going to Main Menu...####");
                return true;
            } catch (IOException e) {
                System.out.println("###Failed to create profile: ###" + e.getMessage());
            }
        } else {
            System.out.println("####Player name already exists! Choose a different name!####");
        }
        return false;
    }
}







