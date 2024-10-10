/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;
/**
 *
 * @author Arkar
 */

import java.io.*;
import java.util.Scanner;

/**
 * Class for viewing and managing the current player profile.
 */
public class ViewProfile {
    private Scanner scanner;
    private MainMenu mainMenu;
    private StartMenu startMenu;

    public ViewProfile(Scanner scanner, MainMenu mainMenu, StartMenu startMenu) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
        this.startMenu = startMenu;
    }

    // View and manage the current player profile
    public void displayProfile(String playerName) {
        File file = new File("./resources/" + playerName + "_profile.txt");
        if (file.exists()) {
            System.out.println("\n--- Player Profile ---");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading the profile file: " + e.getMessage());
            }
        } else {
            System.out.println("Profile for '" + playerName + "' does not exist.");
            return; // Exit if profile doesn't exist
        }

        System.out.println("\nEnter [x] to return to Main Menu                Enter [d] to Delete Profile");
        handleProfile(playerName, file);
    }

    // Handle the input for viewing or deleting the profile
    private void handleProfile(String playerName, File file) {
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("x")) {
            mainMenu.displayMenu(scanner);  // Go back to Main Menu
        } else if (input.equals("d")) {
            deleteProfile(playerName, file);
        } else {
            System.out.println("Please enter [x] to exit or [d] to delete the profile.");
            handleProfile(playerName, file);  // Recurse until valid input is provided
        }
    }

    // Validate and delete profile, then return to the Start Menu
    private void deleteProfile(String playerName, File file) {
        System.out.println("Are you sure you want to delete your profile? All your scores will be lost! (Y/N)");
        String response = scanner.nextLine().trim().toUpperCase();
        if (response.equals("Y")) {
            if (file.delete()) {
                System.out.println("Profile Deleted! Going back to Start Menu...");
                startMenu.displayMenu();  // Return to Start Menu after deletion
            } else {
                System.out.println("Failed to delete the profile. Please try again.");
                displayProfile(playerName);  // Redisplay profile options if deletion fails
            }
        } else {
            System.out.println("Profile deletion canceled.");
            displayProfile(playerName);  // Redisplay profile
        }
    }
}
