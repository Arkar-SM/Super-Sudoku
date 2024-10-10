package pkgsuper.sudoku;
/**
 *
 * @author TahaBazyar
 */

import java.io.*;

/**
 * Manages player profiles, including creation, validation, and updating best times.
 */
public class ProfileManager {

    // Validate if the profile exists
    public boolean validateProfile(String playerName) {
        File profileFile = new File("./resources/" + playerName + "_profile.txt");
        return profileFile.exists();  // Check if the profile file exists
    }

    // Create a new player profile
    public boolean createProfile(String playerName) {
        File profileFile = new File("./resources/" + playerName + "_profile.txt");

        // Check if profile already exists
        if (!profileFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(profileFile))) {
                // Write header line
                writer.write(String.format(
                    "%-20s %-10s %-20s %-10s %-20s %-10s %-20s %-10s %-20s\n",
                    "Name", "Easy", "Easy_Best_Time", "Normal", "Normal_Best_Time",
                    "Hard", "Hard_Best_Time", "Test", "Test_Best_Time"
                ));
                
                // Write initial profile data with no matches and placeholder times
                writer.write(String.format(
                    "%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s\n",
                    playerName, 0, "--", 0, "--", 0, "--", 0, "--"
                ));
                
                System.out.println("Profile created successfully for " + playerName);
                return true;  // Return true if profile creation was successful
                
            } catch (IOException e) {
                System.out.println("Error creating profile: " + e.getMessage());
                return false;  // Return false if there was an IO exception
            }
        } else {
            System.out.println("Profile for " + playerName + " already exists.");
            return false;  // Return false if profile already exists
        }
    }

    // Update the best time for a player based on difficulty
    public void updateBestTime(String playerName, String difficulty, String elapsedTime) {
        File profileFile = new File("./resources/" + playerName + "_profile.txt");

        if (profileFile.exists()) {
            try {
                // Read the profile file and update the best time for the specified difficulty
                BufferedReader reader = new BufferedReader(new FileReader(profileFile));
                StringBuilder updatedProfile = new StringBuilder();
                String line;
                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        updatedProfile.append(line).append("\n");  // Keep the header line
                        firstLine = false;
                    } else if (line.startsWith(playerName)) {
                        String[] parts = line.split("\\s+");

                        // Update best time based on difficulty
                        int easyMatches = Integer.parseInt(parts[1]);
                        String easyBestTime = parts[2];
                        int normalMatches = Integer.parseInt(parts[3]);
                        String normalBestTime = parts[4];
                        int hardMatches = Integer.parseInt(parts[5]);
                        String hardBestTime = parts[6];
                        int testMatches = Integer.parseInt(parts[7]);
                        String testBestTime = parts[8];

                        switch (difficulty) {
                            case "Easy":
                                easyMatches++;
                                if (easyBestTime.equals("--") || isBetterTime(elapsedTime, easyBestTime)) {
                                    easyBestTime = elapsedTime;
                                }
                                break;
                            case "Normal":
                                normalMatches++;
                                if (normalBestTime.equals("--") || isBetterTime(elapsedTime, normalBestTime)) {
                                    normalBestTime = elapsedTime;
                                }
                                break;
                            case "Hard":
                                hardMatches++;
                                if (hardBestTime.equals("--") || isBetterTime(elapsedTime, hardBestTime)) {
                                    hardBestTime = elapsedTime;
                                }
                                break;
                            case "Test":
                                testMatches++;
                                if (testBestTime.equals("--") || isBetterTime(elapsedTime, testBestTime)) {
                                    testBestTime = elapsedTime;
                                }
                                break;
                        }

                        // Update the player's line in the profile
                        updatedProfile.append(String.format(
                            "%-20s %-10d %-20s %-10d %-20s %-10d %-20s %-10d %-20s\n",
                            playerName, easyMatches, easyBestTime, normalMatches, normalBestTime,
                            hardMatches, hardBestTime, testMatches, testBestTime
                        ));
                    } else {
                        updatedProfile.append(line).append("\n");
                    }
                }
                reader.close();

                // Write the updated profile back to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(profileFile));
                writer.write(updatedProfile.toString());
                writer.close();

            } catch (IOException e) {
                System.out.println("Error updating profile: " + e.getMessage());
            }
        } else {
            System.out.println("Profile for " + playerName + " does not exist.");
        }
    }

    // Helper method to compare times in MM:SS format
    private boolean isBetterTime(String newTime, String oldTime) {
        String[] newParts = newTime.split(":");
        String[] oldParts = oldTime.split(":");
        int newMinutes = Integer.parseInt(newParts[0]);
        int newSeconds = Integer.parseInt(newParts[1]);
        int oldMinutes = Integer.parseInt(oldParts[0]);
        int oldSeconds = Integer.parseInt(oldParts[1]);

        return (newMinutes < oldMinutes) || (newMinutes == oldMinutes && newSeconds < oldSeconds);
    }
}
