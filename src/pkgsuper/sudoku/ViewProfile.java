package pkgsuper.sudoku;
/**
 * Author -Arkar
 */

import javax.swing.*;
import java.io.*;

/**
 * Class for viewing and managing the current player profile in the GUI.
 */
public class ViewProfile {

    // View and manage the current player profile using GUI
    public void displayProfile(String playerName) {
        File file = new File("./resources/" + playerName + "_profile.txt");
        if (file.exists()) {
            StringBuilder profileContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    profileContent.append(line).append("\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading the profile file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show profile content in a message dialog
            JOptionPane.showMessageDialog(null, profileContent.toString(),
                    "Player Profile: " + playerName, JOptionPane.INFORMATION_MESSAGE);

            // Ask the user if they want to return to the main menu or delete the profile
            handleProfile(playerName, file);
        } else {
            JOptionPane.showMessageDialog(null, "Profile for '" + playerName + "' does not exist.",
                    "Profile Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Handle the input for viewing or deleting the profile
    private void handleProfile(String playerName, File file) {
        Object[] options = {"Return to Main Menu", "Delete Profile"};
        int choice = JOptionPane.showOptionDialog(null,
                "What would you like to do?",
                "Manage Profile",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            // Return to main menu
            returnToMainMenu();
        } else if (choice == 1) {
            // Confirm and delete profile
            deleteProfile(playerName, file);
        }
    }

    // Validate and delete profile, then return to the main menu
    private void deleteProfile(String playerName, File file) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete your profile? All your scores will be lost!",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (file.delete()) {
                JOptionPane.showMessageDialog(null, "Profile Deleted! Returning to Main Menu...",
                        "Profile Deleted", JOptionPane.INFORMATION_MESSAGE);
                returnToMainMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete the profile. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                displayProfile(playerName);  // Redisplay profile options if deletion fails
            }
        } else {
            // User canceled the deletion
            displayProfile(playerName);  // Redisplay profile
        }
    }

    // Placeholder for returning to the main menu, you can link this to your actual main menu in the GUI
    private void returnToMainMenu() {
        // Implement returning to your main menu view
        JOptionPane.showMessageDialog(null, "Returning to Main Menu...",
                "Main Menu", JOptionPane.INFORMATION_MESSAGE);
    }
}
